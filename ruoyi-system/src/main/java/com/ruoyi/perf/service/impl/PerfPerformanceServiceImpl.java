package com.ruoyi.perf.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.enums.PerformanceStatus;
import com.ruoyi.common.enums.PerformanceStep;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.perf.domain.PerfPerformance;
import com.ruoyi.perf.domain.PerfPerformanceContent;
import com.ruoyi.perf.domain.PerfPerformanceContentScore;
import com.ruoyi.perf.domain.PerfTemplateItem;
import com.ruoyi.perf.domain.dto.PerfPerformanceAuditDTO;
import com.ruoyi.perf.domain.dto.PerfPerformanceSaveDTO;
import com.ruoyi.perf.domain.vo.PerfPerformanceVO;
import com.ruoyi.perf.domain.vo.PerformanceContentItemVO;
import com.ruoyi.perf.mapper.PerfPerformanceContentMapper;
import com.ruoyi.perf.mapper.PerfPerformanceContentScoreMapper;
import com.ruoyi.perf.mapper.PerfPerformanceMapper;
import com.ruoyi.perf.service.IPerfPerformanceContentService;
import com.ruoyi.perf.service.IPerfPerformanceService;
import com.ruoyi.perf.service.IPerfTemplateItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 绩效实例Service业务层处理
 * 
 * @author liudahua
 * @date 2025-11-26
 */
@Service
public class PerfPerformanceServiceImpl extends ServiceImpl<PerfPerformanceMapper,PerfPerformance> implements IPerfPerformanceService
{
    @Autowired
    private PerfPerformanceMapper perfPerformanceMapper;

    @Autowired
    private IPerfTemplateItemService perfTemplateItemService;

    @Autowired
    private IPerfPerformanceContentService perfPerformanceContentService;

    @Autowired
    private PerfPerformanceContentScoreMapper perfPerformanceContentScoreMapper;

    /**
     * 查询绩效实例
     * 
     * @param id 绩效实例主键
     * @return 绩效实例
     */
    @Override
    public PerfPerformanceVO selectPerfPerformanceById(Long id)
    {
        PerfPerformanceVO perfPerformanceVO = perfPerformanceMapper.selectPerfPerformanceById(id);
        if(perfPerformanceVO == null){
            return null;
        }
        if(!perfPerformanceVO.getStatus().equals(PerformanceStatus.DRAFT.getCode()) &&
                !perfPerformanceVO.getStatus().equals(PerformanceStatus.PENDING_SUBMISSION.getCode())){
            List<PerformanceContentItemVO> perfContentVOS = perfPerformanceContentService.selectPerfContentVOList(perfPerformanceVO.getId());
            perfPerformanceVO.setPerfContentVOList(perfContentVOS);
        }

        return perfPerformanceVO;
    }

    /**
     * 查询绩效实例列表
     * 
     * @param perfPerformance 绩效实例
     * @return 绩效实例
     */
    @Override
    public List<PerfPerformanceVO> selectPerfPerformanceList(PerfPerformance perfPerformance)
    {
        return perfPerformanceMapper.selectPerfPerformanceList(perfPerformance);
    }

    /**
     * 新增绩效实例
     * 
     * @param saveDTO 绩效实例
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertPerfPerformance(PerfPerformanceSaveDTO saveDTO)
    {
        saveDTO.setPerformanceNo(IdUtils.generateUniquePerformanceNo());
        saveDTO.setStatus(PerformanceStatus.PENDING_SUBMISSION.getCode());
        saveDTO.setCurrentStep(PerformanceStep.DRAFT.getStepValue());
        PerfPerformance perfPerformance = new PerfPerformance();
        BeanUtils.copyProperties(saveDTO, perfPerformance);
        perfPerformance.setCreateTime(DateUtils.getNowDate());
        perfPerformance.setCreateBy(SecurityUtils.getUserId().toString());
        boolean save = this.save(perfPerformance);
        if(!save){
            throw new RuntimeException("保存失败");
        }
        List<PerfTemplateItem> list = perfTemplateItemService.list(Wrappers.lambdaQuery(PerfTemplateItem.class)
                .eq(PerfTemplateItem::getTemplateId, saveDTO.getTemplateId()));
        if(list.isEmpty()){
            throw new RuntimeException("该模版没有指标");
        }

        List<Long> itemIds = list.stream().map(PerfTemplateItem::getId).collect(Collectors.toList());

        return perfPerformanceContentService.saveBatchByItemIds(perfPerformance.getId(), itemIds) ? 1 : 0;
    }

    /**
     * 修改绩效实例
     * 
     * @param saveDTO 绩效实例
     * @return 结果
     */
    @Override
    public int updatePerfPerformance(PerfPerformanceSaveDTO saveDTO)
    {
        // 先查询原始记录
        PerfPerformance perf = this.getById(saveDTO.getId());
        
        // 检查记录是否存在
        if (perf == null) {
            throw new RuntimeException("绩效记录不存在");
        }
        if(!perf.getStatus().equals(PerformanceStatus.PENDING_SUBMISSION.getCode()) &&
                !perf.getStatus().equals(PerformanceStatus.DRAFT.getCode())){
            throw new RuntimeException("该状态下不能修改");
        }

        // 检查是否需要重新生成绩效内容项（当模板ID发生变化时）
        boolean needRegenerateContent = false;
        if (!perf.getTemplateId().equals(saveDTO.getTemplateId())) {
            needRegenerateContent = true;
        }
        
        // 只更新允许用户修改的字段，保护敏感字段不被修改
        PerfPerformance perfPerformance = new PerfPerformance();
        BeanUtils.copyProperties(saveDTO, perfPerformance);
        perfPerformance.setId(perf.getId());

        // 设置更新时间和更新人
        perfPerformance.setUpdateTime(DateUtils.getNowDate());
        perfPerformance.setUpdateBy(SecurityUtils.getUserId().toString());
        
        // 更新绩效实例
        boolean updateResult = this.updateById(perfPerformance);
        
        // 如果模板ID发生变化，需要重新生成绩效内容项
        if (updateResult && needRegenerateContent) {
            // 删除原有的绩效内容项
            perfPerformanceContentService.remove(Wrappers.lambdaQuery(PerfPerformanceContent.class)
                    .eq(PerfPerformanceContent::getPerformanceId, perf.getId()));
            
            // 重新生成新的绩效内容项
            List<PerfTemplateItem> list = perfTemplateItemService.list(Wrappers.lambdaQuery(PerfTemplateItem.class)
                    .eq(PerfTemplateItem::getTemplateId, saveDTO.getTemplateId()));
            if(list.isEmpty()){
                throw new RuntimeException("该模板没有指标");
            }
            
            List<Long> itemIds = list.stream().map(PerfTemplateItem::getId).collect(Collectors.toList());
            perfPerformanceContentService.saveBatchByItemIds(perf.getId(), itemIds);
        }
        
        return updateResult ? 1 : 0;
    }

    /**
     * 批量删除绩效实例
     * 
     * @param ids 需要删除的绩效实例主键
     * @return 结果
     */
    @Override
    public int deletePerfPerformanceByIds(Long[] ids)
    {
        return perfPerformanceMapper.deletePerfPerformanceByIds(ids);
    }

    /**
     * 删除绩效实例信息
     * 
     * @param id 绩效实例主键
     * @return 结果
     */
    @Override
    public int deletePerfPerformanceById(Long id)
    {
        return perfPerformanceMapper.deletePerfPerformanceById(id);
    }

    @Override
    public int insertMyPerfPerformance(PerfPerformanceSaveDTO saveDTO) {
        saveDTO.setUserId(SecurityUtils.getUserId());
        saveDTO.setDeptId(SecurityUtils.getDeptId());
        return this.insertPerfPerformance(saveDTO);
    }

    @Override
    public int updateMyPerfPerformance(PerfPerformanceSaveDTO saveDTO) {
        saveDTO.setUserId(SecurityUtils.getUserId());
        saveDTO.setDeptId(SecurityUtils.getDeptId());
        return this.updatePerfPerformance(saveDTO);
    }

    @Override
    public Boolean auditPerfPerformance(PerfPerformanceAuditDTO auditDTO) {
        PerfPerformance perfPerformance = this.getById(auditDTO.getId());
        if(perfPerformance == null){
            throw new RuntimeException("绩效实例不存在");
        }

        if(!perfPerformance.getStatus().equals(PerformanceStatus.PENDING_AUDIT.getCode()) &&
                !perfPerformance.getStatus().equals(PerformanceStatus.PENDING_SCORE.getCode())){
            throw new RuntimeException("该状态不允许操作");
        }

        if(auditDTO.getStatus().equals(PerformanceStatus.CONFIRMED.getCode())){
            perfPerformance.setStatus(PerformanceStatus.CONFIRMED.getCode());
            perfPerformance.setConfirmTime(DateUtils.getNowDate());
            perfPerformance.setUpdateTime(DateUtils.getNowDate());

            return this.updateById(perfPerformance);
        }else if(auditDTO.getStatus().equals(PerformanceStatus.REJECTED.getCode())){
            if(auditDTO.getRejectReason() == null){
                throw new RuntimeException("请填写驳回理由");
            }
            perfPerformance.setStatus(PerformanceStatus.REJECTED.getCode());
            perfPerformance.setRejectReason(auditDTO.getRejectReason());
            perfPerformance.setUpdateTime(DateUtils.getNowDate());

            // 驳回后需要删除评分
            perfPerformanceContentScoreMapper.delete(Wrappers.lambdaQuery(PerfPerformanceContentScore.class)
                    .eq(PerfPerformanceContentScore::getPerformanceId, perfPerformance.getId()));
            return this.updateById(perfPerformance);
        }

        return Boolean.FALSE;
    }
}

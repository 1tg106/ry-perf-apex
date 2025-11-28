package com.ruoyi.perf.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.enums.PerformanceStatus;
import com.ruoyi.common.enums.PerformanceStep;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.perf.domain.PerfTemplateItem;
import com.ruoyi.perf.domain.dto.PerfPerformanceSaveDTO;
import com.ruoyi.perf.domain.vo.PerfPerformanceVO;
import com.ruoyi.perf.service.IPerfPerformanceContentService;
import com.ruoyi.perf.service.IPerfTemplateItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.perf.mapper.PerfPerformanceMapper;
import com.ruoyi.perf.domain.PerfPerformance;
import com.ruoyi.perf.service.IPerfPerformanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 查询绩效实例
     * 
     * @param id 绩效实例主键
     * @return 绩效实例
     */
    @Override
    public PerfPerformanceVO selectPerfPerformanceById(Long id)
    {
        return perfPerformanceMapper.selectPerfPerformanceById(id);
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
        PerfPerformance perf = this.getById(saveDTO.getId());
        PerfPerformance perfPerformance = new PerfPerformance();
        BeanUtils.copyProperties(saveDTO, perfPerformance);
        perfPerformance.setUpdateTime(DateUtils.getNowDate());
        perfPerformance.setUpdateBy(SecurityUtils.getUserId().toString());
        perfPerformance.setId(perf.getId());
        return this.updateById(perfPerformance)? 1 : 0;
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
}

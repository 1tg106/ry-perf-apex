package com.ruoyi.perf.service.impl;

import java.util.Collections;
import java.util.List;

import com.ruoyi.common.enums.PerfAppealStatus;
import com.ruoyi.common.enums.PerformanceStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.perf.domain.PerfPerformance;
import com.ruoyi.perf.domain.dto.PerfAppealAddDTO;
import com.ruoyi.perf.domain.dto.PerfAppealHandleDTO;
import com.ruoyi.perf.domain.vo.PerfAppealVO;
import com.ruoyi.perf.mapper.PerfPerformanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.perf.mapper.PerfAppealMapper;
import com.ruoyi.perf.domain.PerfAppeal;
import com.ruoyi.perf.service.IPerfAppealService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * 绩效申诉Service业务层处理
 * 
 * @author liudahua
 * @date 2025-11-26
 */
@Service
public class PerfAppealServiceImpl extends ServiceImpl<PerfAppealMapper,PerfAppeal> implements IPerfAppealService
{
    @Autowired
    private PerfAppealMapper perfAppealMapper;

    @Autowired
    private PerfPerformanceMapper perfPerformanceMapper;

    /**
     * 查询绩效申诉
     * 
     * @param id 绩效申诉主键
     * @return 绩效申诉
     */
    @Override
    public PerfAppealVO selectPerfAppealById(Long id)
    {
        return perfAppealMapper.selectPerfAppealById(id);
    }

    /**
     * 查询绩效申诉列表
     * 
     * @param perfAppeal 绩效申诉
     * @return 绩效申诉
     */
    @Override
    public List<PerfAppeal> selectPerfAppealList(PerfAppeal perfAppeal)
    {
        return perfAppealMapper.selectPerfAppealList(perfAppeal);
    }

    @Override
    public List<PerfAppealVO> selectRelevancePerfAppealList(PerfAppeal perfAppeal) {
        return perfAppealMapper.selectRelevancePerfAppealList(perfAppeal);
    }

    /**
     * 新增绩效申诉
     * 
     * @param addDTO 绩效申诉
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertPerfAppeal(PerfAppealAddDTO addDTO)
    {
        PerfPerformance perfPerformance = perfPerformanceMapper.selectById(addDTO.getPerformanceId());
        if(perfPerformance == null){
            // 绩效实例不存在
            throw new RuntimeException("绩效实例不存在");
        }

        if(!perfPerformance.getStatus().equals(PerformanceStatus.CONFIRMED.getCode()) &&
                !perfPerformance.getStatus().equals(PerformanceStatus.REJECTED.getCode())){
            // 绩效实例状态错误
            throw new RuntimeException("该状态不可申述");
        }

        perfPerformance.setStatus(PerformanceStatus.APPEAL.getCode());
        perfPerformance.setUpdateTime(DateUtils.getNowDate());
        perfPerformanceMapper.updateById(perfPerformance);

        PerfAppeal perfAppeal = new PerfAppeal();
        perfAppeal.setPerformanceId(addDTO.getPerformanceId());
        perfAppeal.setAppealReason(addDTO.getAppealReason());
        perfAppeal.setAppealStatus(PerfAppealStatus.PENDING.getCode());
        perfAppeal.setAppealTime(DateUtils.getNowDate());
        perfAppeal.setCreateBy(SecurityUtils.getUserId().toString());
        perfAppeal.setCreateTime(DateUtils.getNowDate());
        return perfAppealMapper.insertPerfAppeal(perfAppeal);
    }

    /**
     * 修改绩效申诉
     * 
     * @param perfAppeal 绩效申诉
     * @return 结果
     */
    @Override
    public int updatePerfAppeal(PerfAppeal perfAppeal)
    {
        perfAppeal.setUpdateTime(DateUtils.getNowDate());
        return perfAppealMapper.updatePerfAppeal(perfAppeal);
    }

    /**
     * 批量删除绩效申诉
     * 
     * @param ids 需要删除的绩效申诉主键
     * @return 结果
     */
    @Override
    public int deletePerfAppealByIds(Long[] ids)
    {
        return perfAppealMapper.deletePerfAppealByIds(ids);
    }

    /**
     * 删除绩效申诉信息
     * 
     * @param id 绩效申诉主键
     * @return 结果
     */
    @Override
    public int deletePerfAppealById(Long id)
    {
        return perfAppealMapper.deletePerfAppealById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean handlePerfAppeal(PerfAppealHandleDTO handleDTO) {
        if(handleDTO.getId() == null){
            throw new RuntimeException("申诉ID不能为空");
        }
        PerfAppeal perfAppeal = this.getById(handleDTO.getId());
        if(perfAppeal == null){
            throw new RuntimeException("申诉不存在");
        }
        if(!perfAppeal.getAppealStatus().equals(PerfAppealStatus.PENDING.getCode())){
            throw new RuntimeException("申诉状态错误");
        }

        perfAppeal.setAppealStatus(PerfAppealStatus.RESOLVED.getCode());
        perfAppeal.setProcessTime(DateUtils.getNowDate());
        perfAppeal.setProcessComment(handleDTO.getProcessComment());
        perfAppeal.setProcessorId(SecurityUtils.getUserId());

        // 获取绩效实例
        PerfPerformance perfPerformance = perfPerformanceMapper.selectById(perfAppeal.getPerformanceId());
        perfPerformance.setStatus(PerformanceStatus.CONFIRMED.getCode());
        perfPerformance.setUpdateTime(DateUtils.getNowDate());

        // 是否调整分数
        if(handleDTO.getIfAdjustScore() != null && handleDTO.getIfAdjustScore() == 1){
            perfAppeal.setAdjustScore(handleDTO.getAdjustScore());
            perfAppeal.setIfAdjustScore(1);
            perfPerformance.setFinalScore(handleDTO.getAdjustScore());
        }
        perfPerformanceMapper.updateById(perfPerformance);

        return this.updateById(perfAppeal);
    }
}

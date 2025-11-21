package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PerfPerformanceMapper;
import com.ruoyi.system.domain.PerfPerformance;
import com.ruoyi.system.service.IPerfPerformanceService;

/**
 * 绩效实例Service业务层处理
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
@Service
public class PerfPerformanceServiceImpl implements IPerfPerformanceService 
{
    @Autowired
    private PerfPerformanceMapper perfPerformanceMapper;

    /**
     * 查询绩效实例
     * 
     * @param performanceId 绩效实例主键
     * @return 绩效实例
     */
    @Override
    public PerfPerformance selectPerfPerformanceByPerformanceId(Long performanceId)
    {
        return perfPerformanceMapper.selectPerfPerformanceByPerformanceId(performanceId);
    }

    /**
     * 查询绩效实例列表
     * 
     * @param perfPerformance 绩效实例
     * @return 绩效实例
     */
    @Override
    public List<PerfPerformance> selectPerfPerformanceList(PerfPerformance perfPerformance)
    {
        return perfPerformanceMapper.selectPerfPerformanceList(perfPerformance);
    }

    /**
     * 新增绩效实例
     * 
     * @param perfPerformance 绩效实例
     * @return 结果
     */
    @Override
    public int insertPerfPerformance(PerfPerformance perfPerformance)
    {
        perfPerformance.setCreateTime(DateUtils.getNowDate());
        return perfPerformanceMapper.insertPerfPerformance(perfPerformance);
    }

    /**
     * 修改绩效实例
     * 
     * @param perfPerformance 绩效实例
     * @return 结果
     */
    @Override
    public int updatePerfPerformance(PerfPerformance perfPerformance)
    {
        perfPerformance.setUpdateTime(DateUtils.getNowDate());
        return perfPerformanceMapper.updatePerfPerformance(perfPerformance);
    }

    /**
     * 批量删除绩效实例
     * 
     * @param performanceIds 需要删除的绩效实例主键
     * @return 结果
     */
    @Override
    public int deletePerfPerformanceByPerformanceIds(Long[] performanceIds)
    {
        return perfPerformanceMapper.deletePerfPerformanceByPerformanceIds(performanceIds);
    }

    /**
     * 删除绩效实例信息
     * 
     * @param performanceId 绩效实例主键
     * @return 结果
     */
    @Override
    public int deletePerfPerformanceByPerformanceId(Long performanceId)
    {
        return perfPerformanceMapper.deletePerfPerformanceByPerformanceId(performanceId);
    }
}

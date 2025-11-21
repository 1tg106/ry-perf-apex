package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.PerfPerformance;

/**
 * 绩效实例Service接口
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
public interface IPerfPerformanceService 
{
    /**
     * 查询绩效实例
     * 
     * @param performanceId 绩效实例主键
     * @return 绩效实例
     */
    public PerfPerformance selectPerfPerformanceByPerformanceId(Long performanceId);

    /**
     * 查询绩效实例列表
     * 
     * @param perfPerformance 绩效实例
     * @return 绩效实例集合
     */
    public List<PerfPerformance> selectPerfPerformanceList(PerfPerformance perfPerformance);

    /**
     * 新增绩效实例
     * 
     * @param perfPerformance 绩效实例
     * @return 结果
     */
    public int insertPerfPerformance(PerfPerformance perfPerformance);

    /**
     * 修改绩效实例
     * 
     * @param perfPerformance 绩效实例
     * @return 结果
     */
    public int updatePerfPerformance(PerfPerformance perfPerformance);

    /**
     * 批量删除绩效实例
     * 
     * @param performanceIds 需要删除的绩效实例主键集合
     * @return 结果
     */
    public int deletePerfPerformanceByPerformanceIds(Long[] performanceIds);

    /**
     * 删除绩效实例信息
     * 
     * @param performanceId 绩效实例主键
     * @return 结果
     */
    public int deletePerfPerformanceByPerformanceId(Long performanceId);
}

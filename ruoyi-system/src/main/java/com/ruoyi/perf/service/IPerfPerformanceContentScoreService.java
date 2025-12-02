package com.ruoyi.perf.service;

import java.util.List;

import com.ruoyi.perf.domain.PerfPerformanceContent;
import com.ruoyi.perf.domain.PerfPerformanceContentScore;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 绩效指标评分Service接口
 * 
 * @author liudahua
 * @date 2025-12-02
 */
public interface IPerfPerformanceContentScoreService extends IService<PerfPerformanceContentScore>

{
    /**
     * 查询绩效指标评分
     * 
     * @param id 绩效指标评分主键
     * @return 绩效指标评分
     */
    public PerfPerformanceContentScore selectPerfPerformanceContentScoreById(Long id);

    /**
     * 查询绩效指标评分列表
     * 
     * @param perfPerformanceContentScore 绩效指标评分
     * @return 绩效指标评分集合
     */
    public List<PerfPerformanceContentScore> selectPerfPerformanceContentScoreList(PerfPerformanceContentScore perfPerformanceContentScore);

    /**
     * 新增绩效指标评分
     * 
     * @param perfPerformanceContentScore 绩效指标评分
     * @return 结果
     */
    public int insertPerfPerformanceContentScore(PerfPerformanceContentScore perfPerformanceContentScore);

    /**
     * 修改绩效指标评分
     * 
     * @param perfPerformanceContentScore 绩效指标评分
     * @return 结果
     */
    public int updatePerfPerformanceContentScore(PerfPerformanceContentScore perfPerformanceContentScore);

    /**
     * 批量删除绩效指标评分
     * 
     * @param ids 需要删除的绩效指标评分主键集合
     * @return 结果
     */
    public int deletePerfPerformanceContentScoreByIds(Long[] ids);

    /**
     * 删除绩效指标评分信息
     * 
     * @param id 绩效指标评分主键
     * @return 结果
     */
    public int deletePerfPerformanceContentScoreById(Long id);

    /**
     *  新增绩效指标评分
     * @return
     */
    public Boolean savePerfPerformanceContentScore(List<PerfPerformanceContent> perfPerformanceContents, List<Long> itemIds);
}

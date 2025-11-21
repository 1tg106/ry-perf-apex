package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.PerfScoreFlow;

/**
 * 评分流程Service接口
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
public interface IPerfScoreFlowService 
{
    /**
     * 查询评分流程
     * 
     * @param flowId 评分流程主键
     * @return 评分流程
     */
    public PerfScoreFlow selectPerfScoreFlowByFlowId(Long flowId);

    /**
     * 查询评分流程列表
     * 
     * @param perfScoreFlow 评分流程
     * @return 评分流程集合
     */
    public List<PerfScoreFlow> selectPerfScoreFlowList(PerfScoreFlow perfScoreFlow);

    /**
     * 新增评分流程
     * 
     * @param perfScoreFlow 评分流程
     * @return 结果
     */
    public int insertPerfScoreFlow(PerfScoreFlow perfScoreFlow);

    /**
     * 修改评分流程
     * 
     * @param perfScoreFlow 评分流程
     * @return 结果
     */
    public int updatePerfScoreFlow(PerfScoreFlow perfScoreFlow);

    /**
     * 批量删除评分流程
     * 
     * @param flowIds 需要删除的评分流程主键集合
     * @return 结果
     */
    public int deletePerfScoreFlowByFlowIds(Long[] flowIds);

    /**
     * 删除评分流程信息
     * 
     * @param flowId 评分流程主键
     * @return 结果
     */
    public int deletePerfScoreFlowByFlowId(Long flowId);
}

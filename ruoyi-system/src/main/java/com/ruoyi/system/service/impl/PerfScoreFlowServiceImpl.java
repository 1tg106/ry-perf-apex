package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PerfScoreFlowMapper;
import com.ruoyi.system.domain.PerfScoreFlow;
import com.ruoyi.system.service.IPerfScoreFlowService;

/**
 * 评分流程Service业务层处理
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
@Service
public class PerfScoreFlowServiceImpl implements IPerfScoreFlowService 
{
    @Autowired
    private PerfScoreFlowMapper perfScoreFlowMapper;

    /**
     * 查询评分流程
     * 
     * @param flowId 评分流程主键
     * @return 评分流程
     */
    @Override
    public PerfScoreFlow selectPerfScoreFlowByFlowId(Long flowId)
    {
        return perfScoreFlowMapper.selectPerfScoreFlowByFlowId(flowId);
    }

    /**
     * 查询评分流程列表
     * 
     * @param perfScoreFlow 评分流程
     * @return 评分流程
     */
    @Override
    public List<PerfScoreFlow> selectPerfScoreFlowList(PerfScoreFlow perfScoreFlow)
    {
        return perfScoreFlowMapper.selectPerfScoreFlowList(perfScoreFlow);
    }

    /**
     * 新增评分流程
     * 
     * @param perfScoreFlow 评分流程
     * @return 结果
     */
    @Override
    public int insertPerfScoreFlow(PerfScoreFlow perfScoreFlow)
    {
        perfScoreFlow.setCreateTime(DateUtils.getNowDate());
        return perfScoreFlowMapper.insertPerfScoreFlow(perfScoreFlow);
    }

    /**
     * 修改评分流程
     * 
     * @param perfScoreFlow 评分流程
     * @return 结果
     */
    @Override
    public int updatePerfScoreFlow(PerfScoreFlow perfScoreFlow)
    {
        perfScoreFlow.setUpdateTime(DateUtils.getNowDate());
        return perfScoreFlowMapper.updatePerfScoreFlow(perfScoreFlow);
    }

    /**
     * 批量删除评分流程
     * 
     * @param flowIds 需要删除的评分流程主键
     * @return 结果
     */
    @Override
    public int deletePerfScoreFlowByFlowIds(Long[] flowIds)
    {
        return perfScoreFlowMapper.deletePerfScoreFlowByFlowIds(flowIds);
    }

    /**
     * 删除评分流程信息
     * 
     * @param flowId 评分流程主键
     * @return 结果
     */
    @Override
    public int deletePerfScoreFlowByFlowId(Long flowId)
    {
        return perfScoreFlowMapper.deletePerfScoreFlowByFlowId(flowId);
    }
}

package com.ruoyi.perf.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.perf.mapper.PerfScoreFlowMapper;
import com.ruoyi.perf.domain.PerfScoreFlow;
import com.ruoyi.perf.service.IPerfScoreFlowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 评分流程Service业务层处理
 * 
 * @author liudahua
 * @date 2025-11-26
 */
@Service
public class PerfScoreFlowServiceImpl extends ServiceImpl<PerfScoreFlowMapper,PerfScoreFlow> implements IPerfScoreFlowService
{
    @Autowired
    private PerfScoreFlowMapper perfScoreFlowMapper;

    /**
     * 查询评分流程
     * 
     * @param id 评分流程主键
     * @return 评分流程
     */
    @Override
    public PerfScoreFlow selectPerfScoreFlowById(Long id)
    {
        return perfScoreFlowMapper.selectPerfScoreFlowById(id);
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
     * @param ids 需要删除的评分流程主键
     * @return 结果
     */
    @Override
    public int deletePerfScoreFlowByIds(Long[] ids)
    {
        return perfScoreFlowMapper.deletePerfScoreFlowByIds(ids);
    }

    /**
     * 删除评分流程信息
     * 
     * @param id 评分流程主键
     * @return 结果
     */
    @Override
    public int deletePerfScoreFlowById(Long id)
    {
        return perfScoreFlowMapper.deletePerfScoreFlowById(id);
    }
}

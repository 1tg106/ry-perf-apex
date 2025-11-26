package com.ruoyi.perf.mapper;

import java.util.List;
import com.ruoyi.perf.domain.PerfScoreFlow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 评分流程Mapper接口
 * 
 * @author liudahua
 * @date 2025-11-26
 */
public interface PerfScoreFlowMapper extends BaseMapper<PerfScoreFlow>
{
    /**
     * 查询评分流程
     * 
     * @param id 评分流程主键
     * @return 评分流程
     */
    public PerfScoreFlow selectPerfScoreFlowById(Long id);

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
     * 删除评分流程
     * 
     * @param id 评分流程主键
     * @return 结果
     */
    public int deletePerfScoreFlowById(Long id);

    /**
     * 批量删除评分流程
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePerfScoreFlowByIds(Long[] ids);
}

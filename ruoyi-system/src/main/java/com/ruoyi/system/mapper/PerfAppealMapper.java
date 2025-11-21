package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.PerfAppeal;

/**
 * 绩效申诉Mapper接口
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
public interface PerfAppealMapper 
{
    /**
     * 查询绩效申诉
     * 
     * @param appealId 绩效申诉主键
     * @return 绩效申诉
     */
    public PerfAppeal selectPerfAppealByAppealId(Long appealId);

    /**
     * 查询绩效申诉列表
     * 
     * @param perfAppeal 绩效申诉
     * @return 绩效申诉集合
     */
    public List<PerfAppeal> selectPerfAppealList(PerfAppeal perfAppeal);

    /**
     * 新增绩效申诉
     * 
     * @param perfAppeal 绩效申诉
     * @return 结果
     */
    public int insertPerfAppeal(PerfAppeal perfAppeal);

    /**
     * 修改绩效申诉
     * 
     * @param perfAppeal 绩效申诉
     * @return 结果
     */
    public int updatePerfAppeal(PerfAppeal perfAppeal);

    /**
     * 删除绩效申诉
     * 
     * @param appealId 绩效申诉主键
     * @return 结果
     */
    public int deletePerfAppealByAppealId(Long appealId);

    /**
     * 批量删除绩效申诉
     * 
     * @param appealIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePerfAppealByAppealIds(Long[] appealIds);
}

package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.PerfPerformanceContent;

/**
 * 绩效内容Mapper接口
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
public interface PerfPerformanceContentMapper 
{
    /**
     * 查询绩效内容
     * 
     * @param contentId 绩效内容主键
     * @return 绩效内容
     */
    public PerfPerformanceContent selectPerfPerformanceContentByContentId(Long contentId);

    /**
     * 查询绩效内容列表
     * 
     * @param perfPerformanceContent 绩效内容
     * @return 绩效内容集合
     */
    public List<PerfPerformanceContent> selectPerfPerformanceContentList(PerfPerformanceContent perfPerformanceContent);

    /**
     * 新增绩效内容
     * 
     * @param perfPerformanceContent 绩效内容
     * @return 结果
     */
    public int insertPerfPerformanceContent(PerfPerformanceContent perfPerformanceContent);

    /**
     * 修改绩效内容
     * 
     * @param perfPerformanceContent 绩效内容
     * @return 结果
     */
    public int updatePerfPerformanceContent(PerfPerformanceContent perfPerformanceContent);

    /**
     * 删除绩效内容
     * 
     * @param contentId 绩效内容主键
     * @return 结果
     */
    public int deletePerfPerformanceContentByContentId(Long contentId);

    /**
     * 批量删除绩效内容
     * 
     * @param contentIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePerfPerformanceContentByContentIds(Long[] contentIds);
}

package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PerfPerformanceContentMapper;
import com.ruoyi.system.domain.PerfPerformanceContent;
import com.ruoyi.system.service.IPerfPerformanceContentService;

/**
 * 绩效内容Service业务层处理
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
@Service
public class PerfPerformanceContentServiceImpl implements IPerfPerformanceContentService 
{
    @Autowired
    private PerfPerformanceContentMapper perfPerformanceContentMapper;

    /**
     * 查询绩效内容
     * 
     * @param contentId 绩效内容主键
     * @return 绩效内容
     */
    @Override
    public PerfPerformanceContent selectPerfPerformanceContentByContentId(Long contentId)
    {
        return perfPerformanceContentMapper.selectPerfPerformanceContentByContentId(contentId);
    }

    /**
     * 查询绩效内容列表
     * 
     * @param perfPerformanceContent 绩效内容
     * @return 绩效内容
     */
    @Override
    public List<PerfPerformanceContent> selectPerfPerformanceContentList(PerfPerformanceContent perfPerformanceContent)
    {
        return perfPerformanceContentMapper.selectPerfPerformanceContentList(perfPerformanceContent);
    }

    /**
     * 新增绩效内容
     * 
     * @param perfPerformanceContent 绩效内容
     * @return 结果
     */
    @Override
    public int insertPerfPerformanceContent(PerfPerformanceContent perfPerformanceContent)
    {
        perfPerformanceContent.setCreateTime(DateUtils.getNowDate());
        return perfPerformanceContentMapper.insertPerfPerformanceContent(perfPerformanceContent);
    }

    /**
     * 修改绩效内容
     * 
     * @param perfPerformanceContent 绩效内容
     * @return 结果
     */
    @Override
    public int updatePerfPerformanceContent(PerfPerformanceContent perfPerformanceContent)
    {
        perfPerformanceContent.setUpdateTime(DateUtils.getNowDate());
        return perfPerformanceContentMapper.updatePerfPerformanceContent(perfPerformanceContent);
    }

    /**
     * 批量删除绩效内容
     * 
     * @param contentIds 需要删除的绩效内容主键
     * @return 结果
     */
    @Override
    public int deletePerfPerformanceContentByContentIds(Long[] contentIds)
    {
        return perfPerformanceContentMapper.deletePerfPerformanceContentByContentIds(contentIds);
    }

    /**
     * 删除绩效内容信息
     * 
     * @param contentId 绩效内容主键
     * @return 结果
     */
    @Override
    public int deletePerfPerformanceContentByContentId(Long contentId)
    {
        return perfPerformanceContentMapper.deletePerfPerformanceContentByContentId(contentId);
    }
}

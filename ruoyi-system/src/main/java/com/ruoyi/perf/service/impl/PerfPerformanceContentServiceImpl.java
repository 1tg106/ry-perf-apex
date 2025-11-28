package com.ruoyi.perf.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.perf.mapper.PerfPerformanceContentMapper;
import com.ruoyi.perf.domain.PerfPerformanceContent;
import com.ruoyi.perf.service.IPerfPerformanceContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 绩效内容Service业务层处理
 * 
 * @author liudahua
 * @date 2025-11-26
 */
@Service
public class PerfPerformanceContentServiceImpl extends ServiceImpl<PerfPerformanceContentMapper,PerfPerformanceContent> implements IPerfPerformanceContentService
{
    @Autowired
    private PerfPerformanceContentMapper perfPerformanceContentMapper;

    /**
     * 查询绩效内容
     * 
     * @param id 绩效内容主键
     * @return 绩效内容
     */
    @Override
    public PerfPerformanceContent selectPerfPerformanceContentById(Long id)
    {
        return perfPerformanceContentMapper.selectPerfPerformanceContentById(id);
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
     * @param ids 需要删除的绩效内容主键
     * @return 结果
     */
    @Override
    public int deletePerfPerformanceContentByIds(Long[] ids)
    {
        return perfPerformanceContentMapper.deletePerfPerformanceContentByIds(ids);
    }

    /**
     * 删除绩效内容信息
     * 
     * @param id 绩效内容主键
     * @return 结果
     */
    @Override
    public int deletePerfPerformanceContentById(Long id)
    {
        return perfPerformanceContentMapper.deletePerfPerformanceContentById(id);
    }

    @Override
    public Boolean saveBatchByItemIds(Long performanceId, List<Long> itemIds) {
        if(itemIds == null || itemIds.size() == 0){
            throw new RuntimeException("该模版没有指标");
        }
        List<PerfPerformanceContent> contentList = new ArrayList<>();
        for (Long itemId : itemIds){
            PerfPerformanceContent content = new PerfPerformanceContent();
            content.setPerformanceId(performanceId);
            content.setItemId(itemId);
            contentList.add(content);
        }
        return this.saveBatch(contentList);
    }
}

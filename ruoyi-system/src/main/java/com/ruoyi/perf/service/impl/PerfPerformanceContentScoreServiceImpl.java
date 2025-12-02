package com.ruoyi.perf.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.perf.domain.PerfPerformanceContent;
import com.ruoyi.perf.domain.PerfTemplateItem;
import com.ruoyi.perf.service.IPerfTemplateItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.perf.mapper.PerfPerformanceContentScoreMapper;
import com.ruoyi.perf.domain.PerfPerformanceContentScore;
import com.ruoyi.perf.service.IPerfPerformanceContentScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 绩效指标评分Service业务层处理
 * 
 * @author liudahua
 * @date 2025-12-02
 */
@Service
public class PerfPerformanceContentScoreServiceImpl extends ServiceImpl<PerfPerformanceContentScoreMapper,PerfPerformanceContentScore> implements IPerfPerformanceContentScoreService
{
    @Autowired
    private PerfPerformanceContentScoreMapper perfPerformanceContentScoreMapper;

    @Autowired
    private IPerfTemplateItemService perfTemplateItemService;

    /**
     * 查询绩效指标评分
     * 
     * @param id 绩效指标评分主键
     * @return 绩效指标评分
     */
    @Override
    public PerfPerformanceContentScore selectPerfPerformanceContentScoreById(Long id)
    {
        return perfPerformanceContentScoreMapper.selectPerfPerformanceContentScoreById(id);
    }

    /**
     * 查询绩效指标评分列表
     * 
     * @param perfPerformanceContentScore 绩效指标评分
     * @return 绩效指标评分
     */
    @Override
    public List<PerfPerformanceContentScore> selectPerfPerformanceContentScoreList(PerfPerformanceContentScore perfPerformanceContentScore)
    {
        return perfPerformanceContentScoreMapper.selectPerfPerformanceContentScoreList(perfPerformanceContentScore);
    }

    /**
     * 新增绩效指标评分
     * 
     * @param perfPerformanceContentScore 绩效指标评分
     * @return 结果
     */
    @Override
    public int insertPerfPerformanceContentScore(PerfPerformanceContentScore perfPerformanceContentScore)
    {
        perfPerformanceContentScore.setCreateTime(DateUtils.getNowDate());
        return perfPerformanceContentScoreMapper.insertPerfPerformanceContentScore(perfPerformanceContentScore);
    }

    /**
     * 修改绩效指标评分
     * 
     * @param perfPerformanceContentScore 绩效指标评分
     * @return 结果
     */
    @Override
    public int updatePerfPerformanceContentScore(PerfPerformanceContentScore perfPerformanceContentScore)
    {
        perfPerformanceContentScore.setUpdateTime(DateUtils.getNowDate());
        return perfPerformanceContentScoreMapper.updatePerfPerformanceContentScore(perfPerformanceContentScore);
    }

    /**
     * 批量删除绩效指标评分
     * 
     * @param ids 需要删除的绩效指标评分主键
     * @return 结果
     */
    @Override
    public int deletePerfPerformanceContentScoreByIds(Long[] ids)
    {
        return perfPerformanceContentScoreMapper.deletePerfPerformanceContentScoreByIds(ids);
    }

    /**
     * 删除绩效指标评分信息
     * 
     * @param id 绩效指标评分主键
     * @return 结果
     */
    @Override
    public int deletePerfPerformanceContentScoreById(Long id)
    {
        return perfPerformanceContentScoreMapper.deletePerfPerformanceContentScoreById(id);
    }

    @Override
    public Boolean savePerfPerformanceContentScore(List<PerfPerformanceContent> perfPerformanceContents, List<Long> itemIds) {
        // 获取对应的指标项
        List<PerfTemplateItem> list = perfTemplateItemService.list(Wrappers.lambdaQuery(PerfTemplateItem.class)
                .in(PerfTemplateItem::getId, itemIds));
        if(list.isEmpty()){
            throw new RuntimeException("没有找到对应的指标项");
        }
        List<PerfPerformanceContentScore> scores = new ArrayList<>();
        for (PerfPerformanceContent perfPerformanceContent : perfPerformanceContents){
            for (PerfTemplateItem item : list){
                // 判断当前指标项是否需要创建评分人评分
                if(item.getId().equals(perfPerformanceContent.getItemId())){
                    // 默认评分人
                    if(StringUtils.isEmpty(item.getScoreUserIds())){
                        PerfPerformanceContentScore score = new PerfPerformanceContentScore();
                        score.setPerformanceId(perfPerformanceContent.getPerformanceId());
                        score.setContentId(perfPerformanceContent.getId());
                        score.setScoreUserId(item.getDefaultScoreId());
                        score.setItemId(item.getId());
                        score.setIfScore(0);
                        score.setCreateTime(DateUtils.getNowDate());
                        score.setCreateBy(SecurityUtils.getUserId().toString());
                        scores.add(score);
                        continue;
                    }

                    // 多个评分人
                    List<Long> scoreUserIds = Arrays.stream(item.getScoreUserIds().split(","))
                            .map(String::trim) // 去除前后空格
                            .map(Long::parseLong)
                            .collect(Collectors.toList());
                    for (Long scoreUserId : scoreUserIds){
                        PerfPerformanceContentScore score = new PerfPerformanceContentScore();
                        score.setPerformanceId(perfPerformanceContent.getPerformanceId());
                        score.setContentId(perfPerformanceContent.getId());
                        score.setScoreUserId(scoreUserId);
                        score.setItemId(item.getId());
                        score.setIfScore(0);
                        score.setCreateTime(DateUtils.getNowDate());
                        score.setCreateBy(SecurityUtils.getUserId().toString());
                        scores.add(score);
                    }
                }
            }
        }
        return this.saveBatch(scores);
    }
}

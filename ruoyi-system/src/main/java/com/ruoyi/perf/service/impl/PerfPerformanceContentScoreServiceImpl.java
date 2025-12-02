package com.ruoyi.perf.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
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
}

package com.ruoyi.perf.service.impl;

import java.util.Collections;
import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.perf.domain.PerfPerformance;
import com.ruoyi.perf.domain.vo.PerfInterviewVO;
import com.ruoyi.perf.mapper.PerfPerformanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.perf.mapper.PerfInterviewMapper;
import com.ruoyi.perf.domain.PerfInterview;
import com.ruoyi.perf.service.IPerfInterviewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import static com.ruoyi.common.utils.SecurityUtils.getUserId;

/**
 * 绩效面谈Service业务层处理
 * 
 * @author liudahua
 * @date 2025-11-26
 */
@Service
public class PerfInterviewServiceImpl extends ServiceImpl<PerfInterviewMapper,PerfInterview> implements IPerfInterviewService
{
    @Autowired
    private PerfInterviewMapper perfInterviewMapper;

    @Autowired
    private PerfPerformanceMapper perfPerformanceMapper;

    /**
     * 查询绩效面谈
     * 
     * @param id 绩效面谈主键
     * @return 绩效面谈
     */
    @Override
    public PerfInterview selectPerfInterviewById(Long id)
    {
        return perfInterviewMapper.selectPerfInterviewById(id);
    }

    /**
     * 查询绩效面谈列表
     * 
     * @param perfInterview 绩效面谈
     * @return 绩效面谈
     */
    @Override
    public List<PerfInterview> selectPerfInterviewList(PerfInterview perfInterview)
    {
        return perfInterviewMapper.selectPerfInterviewList(perfInterview);
    }

    @Override
    public List<PerfInterviewVO> selectPerfInterviewListVO(PerfInterview perfInterview) {
        return perfInterviewMapper.selectPerfInterviewListVO(perfInterview);
    }

    @Override
    public PerfInterviewVO selectPerfInterviewVOById(Long id) {
        return perfInterviewMapper.selectPerfInterviewVOById(id);
    }

    /**
     * 新增绩效面谈
     * 
     * @param perfInterview 绩效面谈
     * @return 结果
     */
    @Override
    public int insertPerfInterview(PerfInterview perfInterview)
    {
        if(perfInterview.getPerformanceId() == null){
            throw new RuntimeException("绩效id不能为空");
        }

        Long performanceCount = perfPerformanceMapper.selectCount(Wrappers.lambdaQuery(PerfPerformance.class)
                .eq(PerfPerformance::getId,perfInterview.getPerformanceId()));
        if(performanceCount == 0){
            throw new RuntimeException("绩效不存在");
        }

        Long count = perfInterviewMapper.selectCount(Wrappers.lambdaQuery(PerfInterview.class)
                .eq(PerfInterview::getPerformanceId, perfInterview.getPerformanceId()));
        if(count > 0){
            throw new RuntimeException("该绩效已面谈");
        }

        perfInterview.setCreateTime(DateUtils.getNowDate());
        perfInterview.setCreateBy(getUserId().toString());
        return perfInterviewMapper.insertPerfInterview(perfInterview);
    }

    /**
     * 修改绩效面谈
     * 
     * @param perfInterview 绩效面谈
     * @return 结果
     */
    @Override
    public int updatePerfInterview(PerfInterview perfInterview)
    {
        if(perfInterview.getId() == null){
            throw new RuntimeException("id不能为空");
        }
        Long count = perfInterviewMapper.selectCount(Wrappers.lambdaQuery(PerfInterview.class)
                .eq(PerfInterview::getIfInterview, 1));
        if(count > 0){
            throw new RuntimeException("该绩效已面谈");
        }
        perfInterview.setIfInterview(perfInterview.getIfInterview());
        perfInterview.setFeedback(perfInterview.getFeedback());
        perfInterview.setImprovements(perfInterview.getImprovements());
        perfInterview.setActionPlan(perfInterview.getActionPlan());
        perfInterview.setKeyPoints(perfInterview.getKeyPoints());
        perfInterview.setStrengths(perfInterview.getStrengths());
        perfInterview.setUpdateTime(DateUtils.getNowDate());
        perfInterview.setUpdateBy(getUserId().toString());
        return perfInterviewMapper.updateById(perfInterview);
    }

    /**
     * 批量删除绩效面谈
     * 
     * @param ids 需要删除的绩效面谈主键
     * @return 结果
     */
    @Override
    public int deletePerfInterviewByIds(Long[] ids)
    {
        return perfInterviewMapper.deletePerfInterviewByIds(ids);
    }

    /**
     * 删除绩效面谈信息
     * 
     * @param id 绩效面谈主键
     * @return 结果
     */
    @Override
    public int deletePerfInterviewById(Long id)
    {
        return perfInterviewMapper.deletePerfInterviewById(id);
    }
}

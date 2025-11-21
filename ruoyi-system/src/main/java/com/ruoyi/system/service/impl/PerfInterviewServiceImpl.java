package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PerfInterviewMapper;
import com.ruoyi.system.domain.PerfInterview;
import com.ruoyi.system.service.IPerfInterviewService;

/**
 * 绩效面谈Service业务层处理
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
@Service
public class PerfInterviewServiceImpl implements IPerfInterviewService 
{
    @Autowired
    private PerfInterviewMapper perfInterviewMapper;

    /**
     * 查询绩效面谈
     * 
     * @param interviewId 绩效面谈主键
     * @return 绩效面谈
     */
    @Override
    public PerfInterview selectPerfInterviewByInterviewId(Long interviewId)
    {
        return perfInterviewMapper.selectPerfInterviewByInterviewId(interviewId);
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

    /**
     * 新增绩效面谈
     * 
     * @param perfInterview 绩效面谈
     * @return 结果
     */
    @Override
    public int insertPerfInterview(PerfInterview perfInterview)
    {
        perfInterview.setCreateTime(DateUtils.getNowDate());
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
        perfInterview.setUpdateTime(DateUtils.getNowDate());
        return perfInterviewMapper.updatePerfInterview(perfInterview);
    }

    /**
     * 批量删除绩效面谈
     * 
     * @param interviewIds 需要删除的绩效面谈主键
     * @return 结果
     */
    @Override
    public int deletePerfInterviewByInterviewIds(Long[] interviewIds)
    {
        return perfInterviewMapper.deletePerfInterviewByInterviewIds(interviewIds);
    }

    /**
     * 删除绩效面谈信息
     * 
     * @param interviewId 绩效面谈主键
     * @return 结果
     */
    @Override
    public int deletePerfInterviewByInterviewId(Long interviewId)
    {
        return perfInterviewMapper.deletePerfInterviewByInterviewId(interviewId);
    }
}

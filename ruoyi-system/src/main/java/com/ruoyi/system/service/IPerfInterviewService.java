package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.PerfInterview;

/**
 * 绩效面谈Service接口
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
public interface IPerfInterviewService 
{
    /**
     * 查询绩效面谈
     * 
     * @param interviewId 绩效面谈主键
     * @return 绩效面谈
     */
    public PerfInterview selectPerfInterviewByInterviewId(Long interviewId);

    /**
     * 查询绩效面谈列表
     * 
     * @param perfInterview 绩效面谈
     * @return 绩效面谈集合
     */
    public List<PerfInterview> selectPerfInterviewList(PerfInterview perfInterview);

    /**
     * 新增绩效面谈
     * 
     * @param perfInterview 绩效面谈
     * @return 结果
     */
    public int insertPerfInterview(PerfInterview perfInterview);

    /**
     * 修改绩效面谈
     * 
     * @param perfInterview 绩效面谈
     * @return 结果
     */
    public int updatePerfInterview(PerfInterview perfInterview);

    /**
     * 批量删除绩效面谈
     * 
     * @param interviewIds 需要删除的绩效面谈主键集合
     * @return 结果
     */
    public int deletePerfInterviewByInterviewIds(Long[] interviewIds);

    /**
     * 删除绩效面谈信息
     * 
     * @param interviewId 绩效面谈主键
     * @return 结果
     */
    public int deletePerfInterviewByInterviewId(Long interviewId);
}

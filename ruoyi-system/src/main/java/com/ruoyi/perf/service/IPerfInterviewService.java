package com.ruoyi.perf.service;

import java.util.List;
import com.ruoyi.perf.domain.PerfInterview;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 绩效面谈Service接口
 * 
 * @author liudahua
 * @date 2025-11-26
 */
public interface IPerfInterviewService extends IService<PerfInterview>

{
    /**
     * 查询绩效面谈
     * 
     * @param id 绩效面谈主键
     * @return 绩效面谈
     */
    public PerfInterview selectPerfInterviewById(Long id);

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
     * @param ids 需要删除的绩效面谈主键集合
     * @return 结果
     */
    public int deletePerfInterviewByIds(Long[] ids);

    /**
     * 删除绩效面谈信息
     * 
     * @param id 绩效面谈主键
     * @return 结果
     */
    public int deletePerfInterviewById(Long id);
}

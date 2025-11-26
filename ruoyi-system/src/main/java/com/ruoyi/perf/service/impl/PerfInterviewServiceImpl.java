package com.ruoyi.perf.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.perf.mapper.PerfInterviewMapper;
import com.ruoyi.perf.domain.PerfInterview;
import com.ruoyi.perf.service.IPerfInterviewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

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

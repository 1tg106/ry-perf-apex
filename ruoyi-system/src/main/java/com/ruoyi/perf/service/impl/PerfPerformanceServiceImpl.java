package com.ruoyi.perf.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.perf.mapper.PerfPerformanceMapper;
import com.ruoyi.perf.domain.PerfPerformance;
import com.ruoyi.perf.service.IPerfPerformanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 绩效实例Service业务层处理
 * 
 * @author liudahua
 * @date 2025-11-26
 */
@Service
public class PerfPerformanceServiceImpl extends ServiceImpl<PerfPerformanceMapper,PerfPerformance> implements IPerfPerformanceService
{
    @Autowired
    private PerfPerformanceMapper perfPerformanceMapper;

    /**
     * 查询绩效实例
     * 
     * @param id 绩效实例主键
     * @return 绩效实例
     */
    @Override
    public PerfPerformance selectPerfPerformanceById(Long id)
    {
        return perfPerformanceMapper.selectPerfPerformanceById(id);
    }

    /**
     * 查询绩效实例列表
     * 
     * @param perfPerformance 绩效实例
     * @return 绩效实例
     */
    @Override
    public List<PerfPerformance> selectPerfPerformanceList(PerfPerformance perfPerformance)
    {
        return perfPerformanceMapper.selectPerfPerformanceList(perfPerformance);
    }

    /**
     * 新增绩效实例
     * 
     * @param perfPerformance 绩效实例
     * @return 结果
     */
    @Override
    public int insertPerfPerformance(PerfPerformance perfPerformance)
    {
        perfPerformance.setCreateTime(DateUtils.getNowDate());
        return perfPerformanceMapper.insertPerfPerformance(perfPerformance);
    }

    /**
     * 修改绩效实例
     * 
     * @param perfPerformance 绩效实例
     * @return 结果
     */
    @Override
    public int updatePerfPerformance(PerfPerformance perfPerformance)
    {
        perfPerformance.setUpdateTime(DateUtils.getNowDate());
        return perfPerformanceMapper.updatePerfPerformance(perfPerformance);
    }

    /**
     * 批量删除绩效实例
     * 
     * @param ids 需要删除的绩效实例主键
     * @return 结果
     */
    @Override
    public int deletePerfPerformanceByIds(Long[] ids)
    {
        return perfPerformanceMapper.deletePerfPerformanceByIds(ids);
    }

    /**
     * 删除绩效实例信息
     * 
     * @param id 绩效实例主键
     * @return 结果
     */
    @Override
    public int deletePerfPerformanceById(Long id)
    {
        return perfPerformanceMapper.deletePerfPerformanceById(id);
    }
}

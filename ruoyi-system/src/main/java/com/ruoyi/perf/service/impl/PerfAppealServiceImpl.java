package com.ruoyi.perf.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.perf.mapper.PerfAppealMapper;
import com.ruoyi.perf.domain.PerfAppeal;
import com.ruoyi.perf.service.IPerfAppealService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 绩效申诉Service业务层处理
 * 
 * @author liudahua
 * @date 2025-11-26
 */
@Service
public class PerfAppealServiceImpl extends ServiceImpl<PerfAppealMapper,PerfAppeal> implements IPerfAppealService
{
    @Autowired
    private PerfAppealMapper perfAppealMapper;

    /**
     * 查询绩效申诉
     * 
     * @param id 绩效申诉主键
     * @return 绩效申诉
     */
    @Override
    public PerfAppeal selectPerfAppealById(Long id)
    {
        return perfAppealMapper.selectPerfAppealById(id);
    }

    /**
     * 查询绩效申诉列表
     * 
     * @param perfAppeal 绩效申诉
     * @return 绩效申诉
     */
    @Override
    public List<PerfAppeal> selectPerfAppealList(PerfAppeal perfAppeal)
    {
        return perfAppealMapper.selectPerfAppealList(perfAppeal);
    }

    /**
     * 新增绩效申诉
     * 
     * @param perfAppeal 绩效申诉
     * @return 结果
     */
    @Override
    public int insertPerfAppeal(PerfAppeal perfAppeal)
    {
        perfAppeal.setCreateTime(DateUtils.getNowDate());
        return perfAppealMapper.insertPerfAppeal(perfAppeal);
    }

    /**
     * 修改绩效申诉
     * 
     * @param perfAppeal 绩效申诉
     * @return 结果
     */
    @Override
    public int updatePerfAppeal(PerfAppeal perfAppeal)
    {
        perfAppeal.setUpdateTime(DateUtils.getNowDate());
        return perfAppealMapper.updatePerfAppeal(perfAppeal);
    }

    /**
     * 批量删除绩效申诉
     * 
     * @param ids 需要删除的绩效申诉主键
     * @return 结果
     */
    @Override
    public int deletePerfAppealByIds(Long[] ids)
    {
        return perfAppealMapper.deletePerfAppealByIds(ids);
    }

    /**
     * 删除绩效申诉信息
     * 
     * @param id 绩效申诉主键
     * @return 结果
     */
    @Override
    public int deletePerfAppealById(Long id)
    {
        return perfAppealMapper.deletePerfAppealById(id);
    }
}

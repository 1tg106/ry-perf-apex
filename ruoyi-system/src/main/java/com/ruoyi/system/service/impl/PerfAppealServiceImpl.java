package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PerfAppealMapper;
import com.ruoyi.system.domain.PerfAppeal;
import com.ruoyi.system.service.IPerfAppealService;

/**
 * 绩效申诉Service业务层处理
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
@Service
public class PerfAppealServiceImpl implements IPerfAppealService 
{
    @Autowired
    private PerfAppealMapper perfAppealMapper;

    /**
     * 查询绩效申诉
     * 
     * @param appealId 绩效申诉主键
     * @return 绩效申诉
     */
    @Override
    public PerfAppeal selectPerfAppealByAppealId(Long appealId)
    {
        return perfAppealMapper.selectPerfAppealByAppealId(appealId);
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
     * @param appealIds 需要删除的绩效申诉主键
     * @return 结果
     */
    @Override
    public int deletePerfAppealByAppealIds(Long[] appealIds)
    {
        return perfAppealMapper.deletePerfAppealByAppealIds(appealIds);
    }

    /**
     * 删除绩效申诉信息
     * 
     * @param appealId 绩效申诉主键
     * @return 结果
     */
    @Override
    public int deletePerfAppealByAppealId(Long appealId)
    {
        return perfAppealMapper.deletePerfAppealByAppealId(appealId);
    }
}

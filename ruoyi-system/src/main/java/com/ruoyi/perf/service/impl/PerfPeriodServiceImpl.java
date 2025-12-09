package com.ruoyi.perf.service.impl;

import java.util.Collections;
import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.enums.PerfPeriodEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.perf.domain.vo.CommonChooseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.perf.mapper.PerfPeriodMapper;
import com.ruoyi.perf.domain.PerfPeriod;
import com.ruoyi.perf.service.IPerfPeriodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 绩效周期Service业务层处理
 * 
 * @author liudahua
 * @date 2025-11-25
 */
@Service
public class PerfPeriodServiceImpl extends ServiceImpl<PerfPeriodMapper,PerfPeriod> implements IPerfPeriodService
{
    @Autowired
    private PerfPeriodMapper perfPeriodMapper;

    /**
     * 查询绩效周期
     * 
     * @param periodId 绩效周期主键
     * @return 绩效周期
     */
    @Override
    public PerfPeriod selectPerfPeriodByPeriodId(Long periodId)
    {
        return perfPeriodMapper.selectPerfPeriodByPeriodId(periodId);
    }

    /**
     * 查询绩效周期列表
     * 
     * @param perfPeriod 绩效周期
     * @return 绩效周期
     */
    @Override
    public List<PerfPeriod> selectPerfPeriodList(PerfPeriod perfPeriod)
    {
        return perfPeriodMapper.selectPerfPeriodList(perfPeriod);
    }

    /**
     * 新增绩效周期
     * 
     * @param perfPeriod 绩效周期
     * @return 结果
     */
    @Override
    public int insertPerfPeriod(PerfPeriod perfPeriod)
    {
        perfPeriod.setCreateTime(DateUtils.getNowDate());
        perfPeriod.setCreateBy(SecurityUtils.getUserId().toString());
        return perfPeriodMapper.insertPerfPeriod(perfPeriod);
    }

    /**
     * 修改绩效周期
     * 
     * @param perfPeriod 绩效周期
     * @return 结果
     */
    @Override
    public int updatePerfPeriod(PerfPeriod perfPeriod)
    {
        perfPeriod.setUpdateTime(DateUtils.getNowDate());
        perfPeriod.setUpdateBy(SecurityUtils.getUserId().toString());
        return perfPeriodMapper.updatePerfPeriod(perfPeriod);
    }

    /**
     * 批量删除绩效周期
     * 
     * @param periodIds 需要删除的绩效周期主键
     * @return 结果
     */
    @Override
    public int deletePerfPeriodByPeriodIds(Long[] periodIds)
    {
        return perfPeriodMapper.deletePerfPeriodByPeriodIds(periodIds);
    }

    /**
     * 删除绩效周期信息
     * 
     * @param periodId 绩效周期主键
     * @return 结果
     */
    @Override
    public int deletePerfPeriodByPeriodId(Long periodId)
    {
        return perfPeriodMapper.deletePerfPeriodByPeriodId(periodId);
    }

    @Override
    public List<CommonChooseVO> getPerfChooseList() {
        return perfPeriodMapper.getPerfChooseList();
    }

    @Override
    public Long getPeriodStatCount() {
        return this.count(Wrappers.lambdaQuery(PerfPeriod.class).eq(PerfPeriod::getStatus, PerfPeriodEnum.UNDERWAY.getCode()));
    }
}

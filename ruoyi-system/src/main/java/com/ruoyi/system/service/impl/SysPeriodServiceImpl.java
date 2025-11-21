package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.vo.SysPeriodVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysPeriodMapper;
import com.ruoyi.system.domain.SysPeriod;
import com.ruoyi.system.service.ISysPeriodService;

/**
 * 绩效周期Service业务层处理
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
@Service
public class SysPeriodServiceImpl extends ServiceImpl<SysPeriodMapper, SysPeriod> implements ISysPeriodService
{
    @Autowired
    private SysPeriodMapper sysPeriodMapper;

    /**
     * 查询绩效周期
     * 
     * @param periodId 绩效周期主键
     * @return 绩效周期
     */
    @Override
    public SysPeriod selectSysPeriodByPeriodId(Long periodId)
    {
        return sysPeriodMapper.selectSysPeriodByPeriodId(periodId);
    }

    /**
     * 查询绩效周期列表
     * 
     * @param sysPeriod 绩效周期
     * @return 绩效周期
     */
    @Override
    public List<SysPeriodVO> selectSysPeriodList(SysPeriod sysPeriod)
    {
        List<SysPeriod> sysPeriods = sysPeriodMapper.selectSysPeriodList(sysPeriod);
        return sysPeriods.stream().map(period -> {
            SysPeriodVO vo = new SysPeriodVO();
            BeanUtils.copyProperties(period, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 新增绩效周期
     * 
     * @param sysPeriod 绩效周期
     * @return 结果
     */
    @Override
    public int insertSysPeriod(SysPeriod sysPeriod)
    {
        sysPeriod.setCreateTime(DateUtils.getNowDate());
        return sysPeriodMapper.insertSysPeriod(sysPeriod);
    }

    /**
     * 修改绩效周期
     * 
     * @param sysPeriod 绩效周期
     * @return 结果
     */
    @Override
    public int updateSysPeriod(SysPeriod sysPeriod)
    {
        sysPeriod.setUpdateTime(DateUtils.getNowDate());
        return sysPeriodMapper.updateSysPeriod(sysPeriod);
    }

    /**
     * 批量删除绩效周期
     * 
     * @param periodIds 需要删除的绩效周期主键
     * @return 结果
     */
    @Override
    public int deleteSysPeriodByPeriodIds(Long[] periodIds)
    {
        return sysPeriodMapper.deleteSysPeriodByPeriodIds(periodIds);
    }

    /**
     * 删除绩效周期信息
     * 
     * @param periodId 绩效周期主键
     * @return 结果
     */
    @Override
    public int deleteSysPeriodByPeriodId(Long periodId)
    {
        SysPeriod sysPeriod = sysPeriodMapper.selectById(periodId);
        return sysPeriodMapper.deleteById(periodId);
    }
}

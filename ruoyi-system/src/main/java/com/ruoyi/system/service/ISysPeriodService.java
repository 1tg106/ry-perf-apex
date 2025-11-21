package com.ruoyi.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.SysPeriod;
import com.ruoyi.system.vo.SysPeriodVO;

/**
 * 绩效周期Service接口
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
public interface ISysPeriodService extends IService<SysPeriod>
{
    /**
     * 查询绩效周期
     * 
     * @param periodId 绩效周期主键
     * @return 绩效周期
     */
    public SysPeriod selectSysPeriodByPeriodId(Long periodId);

    /**
     * 查询绩效周期列表
     * 
     * @param sysPeriod 绩效周期
     * @return 绩效周期集合
     */
    public List<SysPeriodVO> selectSysPeriodList(SysPeriod sysPeriod);

    /**
     * 新增绩效周期
     * 
     * @param sysPeriod 绩效周期
     * @return 结果
     */
    public int insertSysPeriod(SysPeriod sysPeriod);

    /**
     * 修改绩效周期
     * 
     * @param sysPeriod 绩效周期
     * @return 结果
     */
    public int updateSysPeriod(SysPeriod sysPeriod);

    /**
     * 批量删除绩效周期
     * 
     * @param periodIds 需要删除的绩效周期主键集合
     * @return 结果
     */
    public int deleteSysPeriodByPeriodIds(Long[] periodIds);

    /**
     * 删除绩效周期信息
     * 
     * @param periodId 绩效周期主键
     * @return 结果
     */
    public int deleteSysPeriodByPeriodId(Long periodId);
}

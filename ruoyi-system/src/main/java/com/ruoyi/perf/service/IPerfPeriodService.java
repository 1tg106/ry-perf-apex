package com.ruoyi.perf.service;

import java.util.List;
import com.ruoyi.perf.domain.PerfPeriod;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.perf.domain.vo.CommonChooseVO;

/**
 * 绩效周期Service接口
 * 
 * @author liudahua
 * @date 2025-11-25
 */
public interface IPerfPeriodService extends IService<PerfPeriod>

{
    /**
     * 查询绩效周期
     * 
     * @param periodId 绩效周期主键
     * @return 绩效周期
     */
    public PerfPeriod selectPerfPeriodByPeriodId(Long periodId);

    /**
     * 查询绩效周期列表
     * 
     * @param perfPeriod 绩效周期
     * @return 绩效周期集合
     */
    public List<PerfPeriod> selectPerfPeriodList(PerfPeriod perfPeriod);

    /**
     * 新增绩效周期
     * 
     * @param perfPeriod 绩效周期
     * @return 结果
     */
    public int insertPerfPeriod(PerfPeriod perfPeriod);

    /**
     * 修改绩效周期
     * 
     * @param perfPeriod 绩效周期
     * @return 结果
     */
    public int updatePerfPeriod(PerfPeriod perfPeriod);

    /**
     * 批量删除绩效周期
     * 
     * @param periodIds 需要删除的绩效周期主键集合
     * @return 结果
     */
    public int deletePerfPeriodByPeriodIds(Long[] periodIds);

    /**
     * 删除绩效周期信息
     * 
     * @param periodId 绩效周期主键
     * @return 结果
     */
    public int deletePerfPeriodByPeriodId(Long periodId);

    /**
     * 获取周期选择列表
     * @return
     */
    public List<CommonChooseVO> getPerfChooseList();
}

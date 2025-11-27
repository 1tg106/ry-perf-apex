package com.ruoyi.perf.mapper;

import java.util.List;
import com.ruoyi.perf.domain.PerfPeriod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.perf.domain.vo.CommonChooseVO;

/**
 * 绩效周期Mapper接口
 * 
 * @author liudahua
 * @date 2025-11-25
 */
public interface PerfPeriodMapper extends BaseMapper<PerfPeriod>
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
     * 删除绩效周期
     * 
     * @param periodId 绩效周期主键
     * @return 结果
     */
    public int deletePerfPeriodByPeriodId(Long periodId);

    /**
     * 批量删除绩效周期
     * 
     * @param periodIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePerfPeriodByPeriodIds(Long[] periodIds);

    /**
     * 获取绩效周期列表
     * @return
     */
    public List<CommonChooseVO> getPerfChooseList();
}

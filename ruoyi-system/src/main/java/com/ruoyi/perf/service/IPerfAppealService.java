package com.ruoyi.perf.service;

import java.util.List;
import com.ruoyi.perf.domain.PerfAppeal;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 绩效申诉Service接口
 * 
 * @author liudahua
 * @date 2025-11-26
 */
public interface IPerfAppealService extends IService<PerfAppeal>

{
    /**
     * 查询绩效申诉
     * 
     * @param id 绩效申诉主键
     * @return 绩效申诉
     */
    public PerfAppeal selectPerfAppealById(Long id);

    /**
     * 查询绩效申诉列表
     * 
     * @param perfAppeal 绩效申诉
     * @return 绩效申诉集合
     */
    public List<PerfAppeal> selectPerfAppealList(PerfAppeal perfAppeal);

    /**
     * 新增绩效申诉
     * 
     * @param perfAppeal 绩效申诉
     * @return 结果
     */
    public int insertPerfAppeal(PerfAppeal perfAppeal);

    /**
     * 修改绩效申诉
     * 
     * @param perfAppeal 绩效申诉
     * @return 结果
     */
    public int updatePerfAppeal(PerfAppeal perfAppeal);

    /**
     * 批量删除绩效申诉
     * 
     * @param ids 需要删除的绩效申诉主键集合
     * @return 结果
     */
    public int deletePerfAppealByIds(Long[] ids);

    /**
     * 删除绩效申诉信息
     * 
     * @param id 绩效申诉主键
     * @return 结果
     */
    public int deletePerfAppealById(Long id);
}

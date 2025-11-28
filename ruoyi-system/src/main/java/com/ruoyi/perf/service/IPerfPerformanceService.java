package com.ruoyi.perf.service;

import java.util.List;
import com.ruoyi.perf.domain.PerfPerformance;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.perf.domain.dto.PerfPerformanceSaveDTO;
import com.ruoyi.perf.domain.vo.PerfPerformanceVO;

/**
 * 绩效实例Service接口
 * 
 * @author liudahua
 * @date 2025-11-26
 */
public interface IPerfPerformanceService extends IService<PerfPerformance>

{
    /**
     * 查询绩效实例
     * 
     * @param id 绩效实例主键
     * @return 绩效实例
     */
    public PerfPerformanceVO selectPerfPerformanceById(Long id);

    /**
     * 查询绩效实例列表
     * 
     * @param perfPerformance 绩效实例
     * @return 绩效实例集合
     */
    public List<PerfPerformanceVO> selectPerfPerformanceList(PerfPerformance perfPerformance);

    /**
     * 新增绩效实例
     * 
     * @param saveDTO 绩效实例
     * @return 结果
     */
    public int insertPerfPerformance(PerfPerformanceSaveDTO saveDTO);

    /**
     * 修改绩效实例
     * 
     * @param saveDTO 绩效实例
     * @return 结果
     */
    public int updatePerfPerformance(PerfPerformanceSaveDTO saveDTO);

    /**
     * 批量删除绩效实例
     * 
     * @param ids 需要删除的绩效实例主键集合
     * @return 结果
     */
    public int deletePerfPerformanceByIds(Long[] ids);

    /**
     * 删除绩效实例信息
     * 
     * @param id 绩效实例主键
     * @return 结果
     */
    public int deletePerfPerformanceById(Long id);

    /**
     * 新增我的绩效实例
     *
     * @param saveDTO 绩效实例
     * @return 结果
     */
    public int insertMyPerfPerformance(PerfPerformanceSaveDTO saveDTO);

    /**
     * 修改我的绩效实例
     *
     * @param saveDTO 绩效实例
     * @return 结果
     */
    public int updateMyPerfPerformance(PerfPerformanceSaveDTO saveDTO);
}

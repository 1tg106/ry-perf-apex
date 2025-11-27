package com.ruoyi.perf.mapper;

import java.util.List;
import com.ruoyi.perf.domain.PerfPerformance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.perf.domain.vo.PerfPerformanceVO;

/**
 * 绩效实例Mapper接口
 * 
 * @author liudahua
 * @date 2025-11-26
 */
public interface PerfPerformanceMapper extends BaseMapper<PerfPerformance>
{
    /**
     * 查询绩效实例
     * 
     * @param id 绩效实例主键
     * @return 绩效实例
     */
    public PerfPerformance selectPerfPerformanceById(Long id);

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
     * @param perfPerformance 绩效实例
     * @return 结果
     */
    public int insertPerfPerformance(PerfPerformance perfPerformance);

    /**
     * 修改绩效实例
     * 
     * @param perfPerformance 绩效实例
     * @return 结果
     */
    public int updatePerfPerformance(PerfPerformance perfPerformance);

    /**
     * 删除绩效实例
     * 
     * @param id 绩效实例主键
     * @return 结果
     */
    public int deletePerfPerformanceById(Long id);

    /**
     * 批量删除绩效实例
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePerfPerformanceByIds(Long[] ids);
}

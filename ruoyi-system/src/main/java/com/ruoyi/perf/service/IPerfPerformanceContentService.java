package com.ruoyi.perf.service;

import java.util.List;
import com.ruoyi.perf.domain.PerfPerformanceContent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.perf.domain.dto.PerfContentBatchUpdateDTO;
import com.ruoyi.perf.domain.vo.PerfContentVO;
import com.ruoyi.perf.domain.vo.PerformanceContentItemVO;

/**
 * 绩效内容Service接口
 * 
 * @author liudahua
 * @date 2025-11-26
 */
public interface IPerfPerformanceContentService extends IService<PerfPerformanceContent>

{
    /**
     * 查询绩效内容
     * 
     * @param id 绩效内容主键
     * @return 绩效内容
     */
    public PerfPerformanceContent selectPerfPerformanceContentById(Long id);

    /**
     * 查询绩效内容列表
     * 
     * @param perfPerformanceContent 绩效内容
     * @return 绩效内容集合
     */
    public List<PerfPerformanceContent> selectPerfPerformanceContentList(PerfPerformanceContent perfPerformanceContent);

    /**
     * 新增绩效内容
     * 
     * @param perfPerformanceContent 绩效内容
     * @return 结果
     */
    public int insertPerfPerformanceContent(PerfPerformanceContent perfPerformanceContent);

    /**
     * 修改绩效内容
     * 
     * @param perfPerformanceContent 绩效内容
     * @return 结果
     */
    public int updatePerfPerformanceContent(PerfPerformanceContent perfPerformanceContent);

    /**
     * 批量更新绩效内容
     * 
     * @param updateDTO 批量更新参数
     * @return 结果
     */
    public int updatePerfPerformanceContentBatch(PerfContentBatchUpdateDTO updateDTO);

    /**
     * 批量删除绩效内容
     * 
     * @param ids 需要删除的绩效内容主键集合
     * @return 结果
     */
    public int deletePerfPerformanceContentByIds(Long[] ids);

    /**
     * 删除绩效内容信息
     * 
     * @param id 绩效内容主键
     * @return 结果
     */
    public int deletePerfPerformanceContentById(Long id);

    /**
     * 批量保存绩效内容
     * @param performanceId
     * @param itemIds
     */
    public Boolean saveBatchByItemIds(Long performanceId, List<Long> itemIds);

    /**
     * 查询绩效内容列表
     *
     * @param performanceId 绩效ID
     * @return 绩效内容集合
     */
    public List<PerformanceContentItemVO> selectPerformanceContentItemVOList(Long performanceId);

    /**
     * 获取绩效内容列表
     * @param performanceId
     */
    public List<PerfContentVO> selectPerfContentVOList(Long performanceId);
}

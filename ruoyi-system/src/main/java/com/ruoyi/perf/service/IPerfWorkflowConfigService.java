package com.ruoyi.perf.service;

import java.util.List;
import com.ruoyi.perf.domain.PerfWorkflowConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 绩效流程配置Service接口
 * 
 * @author liudahua
 * @date 2025-11-26
 */
public interface IPerfWorkflowConfigService extends IService<PerfWorkflowConfig>

{
    /**
     * 查询绩效流程配置
     * 
     * @param id 绩效流程配置主键
     * @return 绩效流程配置
     */
    public PerfWorkflowConfig selectPerfWorkflowConfigById(Long id);

    /**
     * 查询绩效流程配置列表
     * 
     * @param perfWorkflowConfig 绩效流程配置
     * @return 绩效流程配置集合
     */
    public List<PerfWorkflowConfig> selectPerfWorkflowConfigList(PerfWorkflowConfig perfWorkflowConfig);

    /**
     * 新增绩效流程配置
     * 
     * @param perfWorkflowConfig 绩效流程配置
     * @return 结果
     */
    public int insertPerfWorkflowConfig(PerfWorkflowConfig perfWorkflowConfig);

    /**
     * 修改绩效流程配置
     * 
     * @param perfWorkflowConfig 绩效流程配置
     * @return 结果
     */
    public int updatePerfWorkflowConfig(PerfWorkflowConfig perfWorkflowConfig);

    /**
     * 批量删除绩效流程配置
     * 
     * @param ids 需要删除的绩效流程配置主键集合
     * @return 结果
     */
    public int deletePerfWorkflowConfigByIds(Long[] ids);

    /**
     * 删除绩效流程配置信息
     * 
     * @param id 绩效流程配置主键
     * @return 结果
     */
    public int deletePerfWorkflowConfigById(Long id);
}

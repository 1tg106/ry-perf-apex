package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysWorkflowConfig;

/**
 * 绩效流程配置Mapper接口
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
public interface SysWorkflowConfigMapper 
{
    /**
     * 查询绩效流程配置
     * 
     * @param configId 绩效流程配置主键
     * @return 绩效流程配置
     */
    public SysWorkflowConfig selectSysWorkflowConfigByConfigId(Long configId);

    /**
     * 查询绩效流程配置列表
     * 
     * @param sysWorkflowConfig 绩效流程配置
     * @return 绩效流程配置集合
     */
    public List<SysWorkflowConfig> selectSysWorkflowConfigList(SysWorkflowConfig sysWorkflowConfig);

    /**
     * 新增绩效流程配置
     * 
     * @param sysWorkflowConfig 绩效流程配置
     * @return 结果
     */
    public int insertSysWorkflowConfig(SysWorkflowConfig sysWorkflowConfig);

    /**
     * 修改绩效流程配置
     * 
     * @param sysWorkflowConfig 绩效流程配置
     * @return 结果
     */
    public int updateSysWorkflowConfig(SysWorkflowConfig sysWorkflowConfig);

    /**
     * 删除绩效流程配置
     * 
     * @param configId 绩效流程配置主键
     * @return 结果
     */
    public int deleteSysWorkflowConfigByConfigId(Long configId);

    /**
     * 批量删除绩效流程配置
     * 
     * @param configIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysWorkflowConfigByConfigIds(Long[] configIds);
}

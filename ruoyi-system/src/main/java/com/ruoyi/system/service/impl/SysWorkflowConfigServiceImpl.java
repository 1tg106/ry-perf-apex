package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysWorkflowConfigMapper;
import com.ruoyi.system.domain.SysWorkflowConfig;
import com.ruoyi.system.service.ISysWorkflowConfigService;

/**
 * 绩效流程配置Service业务层处理
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
@Service
public class SysWorkflowConfigServiceImpl implements ISysWorkflowConfigService 
{
    @Autowired
    private SysWorkflowConfigMapper sysWorkflowConfigMapper;

    /**
     * 查询绩效流程配置
     * 
     * @param configId 绩效流程配置主键
     * @return 绩效流程配置
     */
    @Override
    public SysWorkflowConfig selectSysWorkflowConfigByConfigId(Long configId)
    {
        return sysWorkflowConfigMapper.selectSysWorkflowConfigByConfigId(configId);
    }

    /**
     * 查询绩效流程配置列表
     * 
     * @param sysWorkflowConfig 绩效流程配置
     * @return 绩效流程配置
     */
    @Override
    public List<SysWorkflowConfig> selectSysWorkflowConfigList(SysWorkflowConfig sysWorkflowConfig)
    {
        return sysWorkflowConfigMapper.selectSysWorkflowConfigList(sysWorkflowConfig);
    }

    /**
     * 新增绩效流程配置
     * 
     * @param sysWorkflowConfig 绩效流程配置
     * @return 结果
     */
    @Override
    public int insertSysWorkflowConfig(SysWorkflowConfig sysWorkflowConfig)
    {
        sysWorkflowConfig.setCreateTime(DateUtils.getNowDate());
        return sysWorkflowConfigMapper.insertSysWorkflowConfig(sysWorkflowConfig);
    }

    /**
     * 修改绩效流程配置
     * 
     * @param sysWorkflowConfig 绩效流程配置
     * @return 结果
     */
    @Override
    public int updateSysWorkflowConfig(SysWorkflowConfig sysWorkflowConfig)
    {
        sysWorkflowConfig.setUpdateTime(DateUtils.getNowDate());
        return sysWorkflowConfigMapper.updateSysWorkflowConfig(sysWorkflowConfig);
    }

    /**
     * 批量删除绩效流程配置
     * 
     * @param configIds 需要删除的绩效流程配置主键
     * @return 结果
     */
    @Override
    public int deleteSysWorkflowConfigByConfigIds(Long[] configIds)
    {
        return sysWorkflowConfigMapper.deleteSysWorkflowConfigByConfigIds(configIds);
    }

    /**
     * 删除绩效流程配置信息
     * 
     * @param configId 绩效流程配置主键
     * @return 结果
     */
    @Override
    public int deleteSysWorkflowConfigByConfigId(Long configId)
    {
        return sysWorkflowConfigMapper.deleteSysWorkflowConfigByConfigId(configId);
    }
}

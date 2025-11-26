package com.ruoyi.perf.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.perf.mapper.PerfWorkflowConfigMapper;
import com.ruoyi.perf.domain.PerfWorkflowConfig;
import com.ruoyi.perf.service.IPerfWorkflowConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 绩效流程配置Service业务层处理
 * 
 * @author liudahua
 * @date 2025-11-26
 */
@Service
public class PerfWorkflowConfigServiceImpl extends ServiceImpl<PerfWorkflowConfigMapper,PerfWorkflowConfig> implements IPerfWorkflowConfigService
{
    @Autowired
    private PerfWorkflowConfigMapper perfWorkflowConfigMapper;

    /**
     * 查询绩效流程配置
     * 
     * @param id 绩效流程配置主键
     * @return 绩效流程配置
     */
    @Override
    public PerfWorkflowConfig selectPerfWorkflowConfigById(Long id)
    {
        return perfWorkflowConfigMapper.selectPerfWorkflowConfigById(id);
    }

    /**
     * 查询绩效流程配置列表
     * 
     * @param perfWorkflowConfig 绩效流程配置
     * @return 绩效流程配置
     */
    @Override
    public List<PerfWorkflowConfig> selectPerfWorkflowConfigList(PerfWorkflowConfig perfWorkflowConfig)
    {
        return perfWorkflowConfigMapper.selectPerfWorkflowConfigList(perfWorkflowConfig);
    }

    /**
     * 新增绩效流程配置
     * 
     * @param perfWorkflowConfig 绩效流程配置
     * @return 结果
     */
    @Override
    public int insertPerfWorkflowConfig(PerfWorkflowConfig perfWorkflowConfig)
    {
        perfWorkflowConfig.setCreateTime(DateUtils.getNowDate());
        return perfWorkflowConfigMapper.insertPerfWorkflowConfig(perfWorkflowConfig);
    }

    /**
     * 修改绩效流程配置
     * 
     * @param perfWorkflowConfig 绩效流程配置
     * @return 结果
     */
    @Override
    public int updatePerfWorkflowConfig(PerfWorkflowConfig perfWorkflowConfig)
    {
        perfWorkflowConfig.setUpdateTime(DateUtils.getNowDate());
        return perfWorkflowConfigMapper.updatePerfWorkflowConfig(perfWorkflowConfig);
    }

    /**
     * 批量删除绩效流程配置
     * 
     * @param ids 需要删除的绩效流程配置主键
     * @return 结果
     */
    @Override
    public int deletePerfWorkflowConfigByIds(Long[] ids)
    {
        return perfWorkflowConfigMapper.deletePerfWorkflowConfigByIds(ids);
    }

    /**
     * 删除绩效流程配置信息
     * 
     * @param id 绩效流程配置主键
     * @return 结果
     */
    @Override
    public int deletePerfWorkflowConfigById(Long id)
    {
        return perfWorkflowConfigMapper.deletePerfWorkflowConfigById(id);
    }
}

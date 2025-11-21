package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 绩效流程配置对象 sys_workflow_config
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
public class SysWorkflowConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 配置ID */
    private Long configId;

    /** 配置名称 */
    @Excel(name = "配置名称")
    private String configName;

    /** 模板ID */
    @Excel(name = "模板ID")
    private Long templateId;

    /** 适用部门ID */
    @Excel(name = "适用部门ID")
    private Long deptId;

    /** 流程步骤配置 */
    @Excel(name = "流程步骤配置")
    private String workflowSteps;

    /** 状态(0:正常 1:停用) */
    @Excel(name = "状态(0:正常 1:停用)")
    private String status;

    /** 删除标志（0:存在 1:删除） */
    private String delFlag;

    public void setConfigId(Long configId) 
    {
        this.configId = configId;
    }

    public Long getConfigId() 
    {
        return configId;
    }

    public void setConfigName(String configName) 
    {
        this.configName = configName;
    }

    public String getConfigName() 
    {
        return configName;
    }

    public void setTemplateId(Long templateId) 
    {
        this.templateId = templateId;
    }

    public Long getTemplateId() 
    {
        return templateId;
    }

    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }

    public void setWorkflowSteps(String workflowSteps) 
    {
        this.workflowSteps = workflowSteps;
    }

    public String getWorkflowSteps() 
    {
        return workflowSteps;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("configId", getConfigId())
            .append("configName", getConfigName())
            .append("templateId", getTemplateId())
            .append("deptId", getDeptId())
            .append("workflowSteps", getWorkflowSteps())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

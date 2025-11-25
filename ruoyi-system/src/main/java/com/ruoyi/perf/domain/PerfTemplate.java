package com.ruoyi.perf.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 绩效模板对象 perf_template
 * 
 * @author liudahua
 * @date 2025-11-25
 */
public class PerfTemplate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 模板ID */
    private Long templateId;

    /** 模板名称 */
    @Excel(name = "模板名称")
    private String templateName;

    /** 模板类型(KPI:关键指标, OKR:目标与成果, COMPETENCY:能力素质) */
    @Excel(name = "模板类型(KPI:关键指标, OKR:目标与成果, COMPETENCY:能力素质)")
    private String templateType;

    /** 适用部门ID */
    @Excel(name = "适用部门ID")
    private Long deptId;

    /** 适用岗位IDS */
    @Excel(name = "适用岗位IDS")
    private String postIds;

    /** 状态(0:正常 1:停用) */
    @Excel(name = "状态(0:正常 1:停用)")
    private String status;

    /** 删除标志（0:存在 1:删除） */
    private String delFlag;

    public void setTemplateId(Long templateId) 
    {
        this.templateId = templateId;
    }

    public Long getTemplateId() 
    {
        return templateId;
    }

    public void setTemplateName(String templateName) 
    {
        this.templateName = templateName;
    }

    public String getTemplateName() 
    {
        return templateName;
    }

    public void setTemplateType(String templateType) 
    {
        this.templateType = templateType;
    }

    public String getTemplateType() 
    {
        return templateType;
    }

    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }

    public void setPostIds(String postIds) 
    {
        this.postIds = postIds;
    }

    public String getPostIds() 
    {
        return postIds;
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
            .append("templateId", getTemplateId())
            .append("templateName", getTemplateName())
            .append("templateType", getTemplateType())
            .append("deptId", getDeptId())
            .append("postIds", getPostIds())
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

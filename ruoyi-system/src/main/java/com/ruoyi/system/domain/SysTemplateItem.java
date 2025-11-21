package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 模板指标对象 sys_template_item
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
public class SysTemplateItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 指标项ID */
    private Long itemId;

    /** 模板ID */
    @Excel(name = "模板ID")
    private Long templateId;

    /** 父指标ID */
    @Excel(name = "父指标ID")
    private Long parentId;

    /** 指标名称 */
    @Excel(name = "指标名称")
    private String itemName;

    /** 指标类型(OBJECTIVE:目标, KEY_RESULT:关键成果, COMPETENCY:能力项) */
    @Excel(name = "指标类型(OBJECTIVE:目标, KEY_RESULT:关键成果, COMPETENCY:能力项)")
    private String itemType;

    /** 权重(0-100) */
    @Excel(name = "权重(0-100)")
    private BigDecimal weight;

    /** 评分标准描述 */
    @Excel(name = "评分标准描述")
    private String scoreStandard;

    /** 最低分 */
    @Excel(name = "最低分")
    private BigDecimal minScore;

    /** 最高分 */
    @Excel(name = "最高分")
    private BigDecimal maxScore;

    /** 排序 */
    @Excel(name = "排序")
    private Long sortOrder;

    /** 删除标志（0:存在 1:删除） */
    private String delFlag;

    public void setItemId(Long itemId) 
    {
        this.itemId = itemId;
    }

    public Long getItemId() 
    {
        return itemId;
    }

    public void setTemplateId(Long templateId) 
    {
        this.templateId = templateId;
    }

    public Long getTemplateId() 
    {
        return templateId;
    }

    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }

    public void setItemName(String itemName) 
    {
        this.itemName = itemName;
    }

    public String getItemName() 
    {
        return itemName;
    }

    public void setItemType(String itemType) 
    {
        this.itemType = itemType;
    }

    public String getItemType() 
    {
        return itemType;
    }

    public void setWeight(BigDecimal weight) 
    {
        this.weight = weight;
    }

    public BigDecimal getWeight() 
    {
        return weight;
    }

    public void setScoreStandard(String scoreStandard) 
    {
        this.scoreStandard = scoreStandard;
    }

    public String getScoreStandard() 
    {
        return scoreStandard;
    }

    public void setMinScore(BigDecimal minScore) 
    {
        this.minScore = minScore;
    }

    public BigDecimal getMinScore() 
    {
        return minScore;
    }

    public void setMaxScore(BigDecimal maxScore) 
    {
        this.maxScore = maxScore;
    }

    public BigDecimal getMaxScore() 
    {
        return maxScore;
    }

    public void setSortOrder(Long sortOrder) 
    {
        this.sortOrder = sortOrder;
    }

    public Long getSortOrder() 
    {
        return sortOrder;
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
            .append("itemId", getItemId())
            .append("templateId", getTemplateId())
            .append("parentId", getParentId())
            .append("itemName", getItemName())
            .append("itemType", getItemType())
            .append("weight", getWeight())
            .append("scoreStandard", getScoreStandard())
            .append("minScore", getMinScore())
            .append("maxScore", getMaxScore())
            .append("sortOrder", getSortOrder())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 绩效内容对象 perf_performance_content
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
public class PerfPerformanceContent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 内容ID */
    private Long contentId;

    /** 绩效ID */
    @Excel(name = "绩效ID")
    private Long performanceId;

    /** 指标项ID */
    @Excel(name = "指标项ID")
    private Long itemId;

    /** 自评目标 */
    @Excel(name = "自评目标")
    private String selfTarget;

    /** 自评成果 */
    @Excel(name = "自评成果")
    private String selfResult;

    /** 自评分数 */
    @Excel(name = "自评分数")
    private BigDecimal selfScore;

    /** 自评评语 */
    @Excel(name = "自评评语")
    private String selfComment;

    /** 最终评分 */
    @Excel(name = "最终评分")
    private BigDecimal finalScore;

    /** 最终评语 */
    @Excel(name = "最终评语")
    private String finalComment;

    /** 排序 */
    @Excel(name = "排序")
    private Long sortOrder;

    /** 删除标志（0:存在 1:删除） */
    private String delFlag;

    public void setContentId(Long contentId) 
    {
        this.contentId = contentId;
    }

    public Long getContentId() 
    {
        return contentId;
    }

    public void setPerformanceId(Long performanceId) 
    {
        this.performanceId = performanceId;
    }

    public Long getPerformanceId() 
    {
        return performanceId;
    }

    public void setItemId(Long itemId) 
    {
        this.itemId = itemId;
    }

    public Long getItemId() 
    {
        return itemId;
    }

    public void setSelfTarget(String selfTarget) 
    {
        this.selfTarget = selfTarget;
    }

    public String getSelfTarget() 
    {
        return selfTarget;
    }

    public void setSelfResult(String selfResult) 
    {
        this.selfResult = selfResult;
    }

    public String getSelfResult() 
    {
        return selfResult;
    }

    public void setSelfScore(BigDecimal selfScore) 
    {
        this.selfScore = selfScore;
    }

    public BigDecimal getSelfScore() 
    {
        return selfScore;
    }

    public void setSelfComment(String selfComment) 
    {
        this.selfComment = selfComment;
    }

    public String getSelfComment() 
    {
        return selfComment;
    }

    public void setFinalScore(BigDecimal finalScore) 
    {
        this.finalScore = finalScore;
    }

    public BigDecimal getFinalScore() 
    {
        return finalScore;
    }

    public void setFinalComment(String finalComment) 
    {
        this.finalComment = finalComment;
    }

    public String getFinalComment() 
    {
        return finalComment;
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
            .append("contentId", getContentId())
            .append("performanceId", getPerformanceId())
            .append("itemId", getItemId())
            .append("selfTarget", getSelfTarget())
            .append("selfResult", getSelfResult())
            .append("selfScore", getSelfScore())
            .append("selfComment", getSelfComment())
            .append("finalScore", getFinalScore())
            .append("finalComment", getFinalComment())
            .append("sortOrder", getSortOrder())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

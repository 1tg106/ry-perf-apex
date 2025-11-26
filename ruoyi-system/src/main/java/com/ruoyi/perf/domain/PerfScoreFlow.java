package com.ruoyi.perf.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 评分流程对象 perf_score_flow
 * 
 * @author liudahua
 * @date 2025-11-26
 */
public class PerfScoreFlow extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 流程ID */
    private Long id;

    /** 绩效ID */
    @Excel(name = "绩效ID")
    private Long performanceId;

    /** 步骤序号 */
    @Excel(name = "步骤序号")
    private Long step;

    /** 步骤名称 */
    @Excel(name = "步骤名称")
    private String stepName;

    /** 评分人ID */
    @Excel(name = "评分人ID")
    private Long scorerId;

    /** 评分人类型(DIRECT_LEADER:直属上级, DEPT_HEAD:部门负责人, HR:HR) */
    @Excel(name = "评分人类型(DIRECT_LEADER:直属上级, DEPT_HEAD:部门负责人, HR:HR)")
    private String scorerType;

    /** 评分 */
    @Excel(name = "评分")
    private BigDecimal score;

    /** 评语 */
    @Excel(name = "评语")
    private String comment;

    /** 状态(PENDING:待处理, SCORED:已评分, SKIPPED:已跳过) */
    @Excel(name = "状态(PENDING:待处理, SCORED:已评分, SKIPPED:已跳过)")
    private String status;

    /** 提交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "提交时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date submitTime;

    /** 删除标志（0:存在 1:删除） */
    private String delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setPerformanceId(Long performanceId) 
    {
        this.performanceId = performanceId;
    }

    public Long getPerformanceId() 
    {
        return performanceId;
    }

    public void setStep(Long step) 
    {
        this.step = step;
    }

    public Long getStep() 
    {
        return step;
    }

    public void setStepName(String stepName) 
    {
        this.stepName = stepName;
    }

    public String getStepName() 
    {
        return stepName;
    }

    public void setScorerId(Long scorerId) 
    {
        this.scorerId = scorerId;
    }

    public Long getScorerId() 
    {
        return scorerId;
    }

    public void setScorerType(String scorerType) 
    {
        this.scorerType = scorerType;
    }

    public String getScorerType() 
    {
        return scorerType;
    }

    public void setScore(BigDecimal score) 
    {
        this.score = score;
    }

    public BigDecimal getScore() 
    {
        return score;
    }

    public void setComment(String comment) 
    {
        this.comment = comment;
    }

    public String getComment() 
    {
        return comment;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setSubmitTime(Date submitTime) 
    {
        this.submitTime = submitTime;
    }

    public Date getSubmitTime() 
    {
        return submitTime;
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
            .append("id", getId())
            .append("performanceId", getPerformanceId())
            .append("step", getStep())
            .append("stepName", getStepName())
            .append("scorerId", getScorerId())
            .append("scorerType", getScorerType())
            .append("score", getScore())
            .append("comment", getComment())
            .append("status", getStatus())
            .append("submitTime", getSubmitTime())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

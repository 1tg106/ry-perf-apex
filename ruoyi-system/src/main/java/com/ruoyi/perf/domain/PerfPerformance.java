package com.ruoyi.perf.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 绩效实例对象 perf_performance
 * 
 * @author liudahua
 * @date 2025-11-26
 */
public class PerfPerformance extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 绩效ID */
    private Long id;

    /** 绩效编号 */
    @Excel(name = "绩效编号")
    private String performanceNo;

    /** 绩效周期ID */
    @Excel(name = "绩效周期ID")
    private Long periodId;

    /** 模板ID */
    @Excel(name = "模板ID")
    private Long templateId;

    /** 员工ID */
    @Excel(name = "员工ID")
    private Long userId;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    /** 自评总分 */
    @Excel(name = "自评总分")
    private BigDecimal selfScore;

    /** 最终得分 */
    @Excel(name = "最终得分")
    private BigDecimal finalScore;

    /** 当前步骤 */
    @Excel(name = "当前步骤")
    private Long currentStep;

    /** 状态(DRAFT:草稿, PENDING_SCORE:待评分, PENDING_HR:待HR确认, CONFIRMED:已确认, REJECTED:已驳回, APPEAL:申诉中) */
    @Excel(name = "状态(DRAFT:草稿, PENDING_SCORE:待评分, PENDING_HR:待HR确认, CONFIRMED:已确认, REJECTED:已驳回, APPEAL:申诉中)")
    private String status;

    /** 驳回原因 */
    @Excel(name = "驳回原因")
    private String rejectReason;

    /** 提交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "提交时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date submitTime;

    /** 确认时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "确认时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date confirmTime;

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

    public void setPerformanceNo(String performanceNo) 
    {
        this.performanceNo = performanceNo;
    }

    public String getPerformanceNo() 
    {
        return performanceNo;
    }

    public void setPeriodId(Long periodId) 
    {
        this.periodId = periodId;
    }

    public Long getPeriodId() 
    {
        return periodId;
    }

    public void setTemplateId(Long templateId) 
    {
        this.templateId = templateId;
    }

    public Long getTemplateId() 
    {
        return templateId;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }

    public void setSelfScore(BigDecimal selfScore) 
    {
        this.selfScore = selfScore;
    }

    public BigDecimal getSelfScore() 
    {
        return selfScore;
    }

    public void setFinalScore(BigDecimal finalScore) 
    {
        this.finalScore = finalScore;
    }

    public BigDecimal getFinalScore() 
    {
        return finalScore;
    }

    public void setCurrentStep(Long currentStep) 
    {
        this.currentStep = currentStep;
    }

    public Long getCurrentStep() 
    {
        return currentStep;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setRejectReason(String rejectReason) 
    {
        this.rejectReason = rejectReason;
    }

    public String getRejectReason() 
    {
        return rejectReason;
    }

    public void setSubmitTime(Date submitTime) 
    {
        this.submitTime = submitTime;
    }

    public Date getSubmitTime() 
    {
        return submitTime;
    }

    public void setConfirmTime(Date confirmTime) 
    {
        this.confirmTime = confirmTime;
    }

    public Date getConfirmTime() 
    {
        return confirmTime;
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
            .append("performanceNo", getPerformanceNo())
            .append("periodId", getPeriodId())
            .append("templateId", getTemplateId())
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .append("selfScore", getSelfScore())
            .append("finalScore", getFinalScore())
            .append("currentStep", getCurrentStep())
            .append("status", getStatus())
            .append("rejectReason", getRejectReason())
            .append("submitTime", getSubmitTime())
            .append("confirmTime", getConfirmTime())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

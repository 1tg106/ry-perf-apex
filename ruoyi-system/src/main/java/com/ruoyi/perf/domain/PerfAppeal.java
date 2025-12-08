package com.ruoyi.perf.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 绩效申诉对象 perf_appeal
 * 
 * @author liudahua
 * @date 2025-11-26
 */
public class PerfAppeal extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 申诉ID */
    private Long id;

    /** 绩效ID */
    @Excel(name = "绩效ID")
    private Long performanceId;

    /** 申诉理由 */
    @Excel(name = "申诉理由")
    private String appealReason;

    /** 申诉状态(PENDING:待处理, RESOLVED:已处理, REJECTED:已驳回) */
    @Excel(name = "申诉状态(PENDING:待处理, RESOLVED:已处理, REJECTED:已驳回)")
    private String appealStatus;

    /** 处理人ID */
    @Excel(name = "处理人ID")
    private Long processorId;

    /** 处理意见 */
    @Excel(name = "处理意见")
    private String processComment;

    /** 处理结果 */
    @Excel(name = "处理结果")
    private String processResult;

    /** 调整分数 */
    @Excel(name = "调整分数")
    private BigDecimal adjustScore;

    /** 是否调整分数(0:否 1:是) */
    @Excel(name = "是否调整分数(0:否 1:是)")
    private Integer ifAdjustScore;

    /** 申诉时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申诉时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date appealTime;

    /** 处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date processTime;

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

    public void setAppealReason(String appealReason) 
    {
        this.appealReason = appealReason;
    }

    public String getAppealReason() 
    {
        return appealReason;
    }

    public void setAppealStatus(String appealStatus) 
    {
        this.appealStatus = appealStatus;
    }

    public String getAppealStatus() 
    {
        return appealStatus;
    }

    public void setProcessorId(Long processorId) 
    {
        this.processorId = processorId;
    }

    public Long getProcessorId() 
    {
        return processorId;
    }

    public void setProcessComment(String processComment) 
    {
        this.processComment = processComment;
    }

    public String getProcessComment() 
    {
        return processComment;
    }

    public void setProcessResult(String processResult) 
    {
        this.processResult = processResult;
    }

    public String getProcessResult() 
    {
        return processResult;
    }

    public void setAdjustScore(BigDecimal adjustScore) 
    {
        this.adjustScore = adjustScore;
    }

    public BigDecimal getAdjustScore() 
    {
        return adjustScore;
    }

    public void setIfAdjustScore(Integer ifAdjustScore)
    {
        this.ifAdjustScore = ifAdjustScore;
    }

    public Integer getIfAdjustScore()
    {
        return ifAdjustScore;
    }

    public void setAppealTime(Date appealTime) 
    {
        this.appealTime = appealTime;
    }

    public Date getAppealTime() 
    {
        return appealTime;
    }

    public void setProcessTime(Date processTime) 
    {
        this.processTime = processTime;
    }

    public Date getProcessTime() 
    {
        return processTime;
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
            .append("appealReason", getAppealReason())
            .append("appealStatus", getAppealStatus())
            .append("processorId", getProcessorId())
            .append("processComment", getProcessComment())
            .append("processResult", getProcessResult())
            .append("adjustScore", getAdjustScore())
            .append("appealTime", getAppealTime())
            .append("processTime", getProcessTime())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

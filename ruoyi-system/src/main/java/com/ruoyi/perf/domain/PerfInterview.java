package com.ruoyi.perf.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 绩效面谈对象 perf_interview
 * 
 * @author liudahua
 * @date 2025-11-26
 */
public class PerfInterview extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 面谈ID */
    private Long id;

    /** 绩效ID */
    @Excel(name = "绩效ID")
    private Long performanceId;

    /** 面谈人ID */
    @Excel(name = "面谈人ID")
    private Long interviewerId;

    /** 被面谈人ID */
    @Excel(name = "被面谈人ID")
    private Long intervieweeId;

    /** 面谈时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "面谈时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date interviewTime;

    /** 面谈要点 */
    @Excel(name = "面谈要点")
    private String keyPoints;

    /** 优点与成绩 */
    @Excel(name = "优点与成绩")
    private String strengths;

    /** 待改进点 */
    @Excel(name = "待改进点")
    private String improvements;

    /** 行动计划 */
    @Excel(name = "行动计划")
    private String actionPlan;

    /** 员工反馈 */
    @Excel(name = "员工反馈")
    private String feedback;

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

    public void setInterviewerId(Long interviewerId) 
    {
        this.interviewerId = interviewerId;
    }

    public Long getInterviewerId() 
    {
        return interviewerId;
    }

    public void setIntervieweeId(Long intervieweeId) 
    {
        this.intervieweeId = intervieweeId;
    }

    public Long getIntervieweeId() 
    {
        return intervieweeId;
    }

    public void setInterviewTime(Date interviewTime) 
    {
        this.interviewTime = interviewTime;
    }

    public Date getInterviewTime() 
    {
        return interviewTime;
    }

    public void setKeyPoints(String keyPoints) 
    {
        this.keyPoints = keyPoints;
    }

    public String getKeyPoints() 
    {
        return keyPoints;
    }

    public void setStrengths(String strengths) 
    {
        this.strengths = strengths;
    }

    public String getStrengths() 
    {
        return strengths;
    }

    public void setImprovements(String improvements) 
    {
        this.improvements = improvements;
    }

    public String getImprovements() 
    {
        return improvements;
    }

    public void setActionPlan(String actionPlan) 
    {
        this.actionPlan = actionPlan;
    }

    public String getActionPlan() 
    {
        return actionPlan;
    }

    public void setFeedback(String feedback) 
    {
        this.feedback = feedback;
    }

    public String getFeedback() 
    {
        return feedback;
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
            .append("interviewerId", getInterviewerId())
            .append("intervieweeId", getIntervieweeId())
            .append("interviewTime", getInterviewTime())
            .append("keyPoints", getKeyPoints())
            .append("strengths", getStrengths())
            .append("improvements", getImprovements())
            .append("actionPlan", getActionPlan())
            .append("feedback", getFeedback())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

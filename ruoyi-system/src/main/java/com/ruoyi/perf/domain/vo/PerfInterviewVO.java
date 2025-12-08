package com.ruoyi.perf.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * 绩效面谈对象 perf_interview
 * 
 * @author liudahua
 * @date 2025-11-26
 */
@Data
public class PerfInterviewVO implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 面谈ID */
    private Long id;

    /** 绩效ID */
    @Excel(name = "绩效ID")
    private Long performanceId;

    private String periodName;

    /** 面谈人ID */
    @Excel(name = "面谈人ID")
    private Long interviewerId;

    @Excel(name = "面谈人")
    private String interviewerName;

    /** 被面谈人ID */
    @Excel(name = "被面谈人ID")
    private Long intervieweeId;

    @Excel(name = "被面谈人")
    private String intervieweeName;

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

    /** 是否已面谈：0-否 1-是 */
    private Integer ifInterview;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}

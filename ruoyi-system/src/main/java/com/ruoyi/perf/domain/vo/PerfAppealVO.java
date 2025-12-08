package com.ruoyi.perf.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 绩效申诉对象 perf_appeal
 * 
 * @author liudahua
 * @date 2025-11-26
 */
@Data
public class PerfAppealVO implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 申诉ID */
    private Long id;

    /** 绩效ID */
    @Excel(name = "绩效ID")
    private Long performanceId;

    @Excel(name = "绩效编号")
    private String performanceNo;

    /** 考核周期 */
    private String periodName;

    /** 申请人ID */
    private Long userId;

    /** 申请人 */
    private String nickName;

    /** 申诉理由 */
    @Excel(name = "申诉理由")
    private String appealReason;

    /** 申诉状态(PENDING:待处理, RESOLVED:已处理, REJECTED:已驳回) */
    @Excel(name = "申诉状态(PENDING:待处理, RESOLVED:已处理, REJECTED:已驳回)")
    private String appealStatus;

    /** 处理人ID */
    @Excel(name = "处理人ID")
    private Long processorId;

    /** 处理人 */
    private String handleNickName;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "申诉时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date appealTime;

    /** 处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date processTime;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}

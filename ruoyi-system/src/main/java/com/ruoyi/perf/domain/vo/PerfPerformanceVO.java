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
 *
 * @author liudahua
 * @date 2025-11-26
 */
@Data
public class PerfPerformanceVO implements Serializable
{
    /** 绩效ID */
    private Long id;

    /** 绩效编号 */
    @Excel(name = "绩效编号")
    private String performanceNo;

    /** 绩效周期ID */
    @Excel(name = "绩效周期ID")
    private Long periodId;

    @Excel(name = "绩效周期")
    private String periodName;

    /** 模板ID */
    @Excel(name = "模板ID")
    private Long templateId;

    @Excel(name = "模板名称")
    private String templateName;

    /** 员工ID */
    @Excel(name = "员工ID")
    private Long userId;

    @Excel(name = "员工名称")
    private String nickName;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    @Excel(name = "部门名称")
    private String deptName;

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

    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

}

package com.ruoyi.perf.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 绩效实例对象 perf_performance
 * 
 * @author liudahua
 * @date 2025-11-26
 */
@Data
public class PerfPerformanceSaveDTO implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 绩效ID */
    private Long id;

    /** 绩效编号 */
    private String performanceNo;

    /** 绩效周期ID */
    @NotNull(message = "绩效周期不能为空")
    private Long periodId;

    /** 模板ID */
    @NotNull(message = "模板不能为空")
    private Long templateId;

    /** 员工ID */
    @NotNull(message = "员工不能为空")
    private Long userId;

    /** 部门ID */
    @NotNull(message = "部门不能为空")
    private Long deptId;

    /** 自评总分 */
    private BigDecimal selfScore;

    /** 最终得分 */
    private BigDecimal finalScore;

    /** 当前步骤(当前步骤：0-草稿状态 / 员工自评中 1-待直属上级评分 2-待部门负责人评分  3-待HR确认 4-已完成所有步骤) */
    private Integer currentStep;

    /** 状态(DRAFT:草稿, PENDING_SUBMISSION: 待提交, PENDING_SCORE:待评分, PENDING_HR:待HR确认, CONFIRMED:已确认, REJECTED:已驳回, APPEAL:申诉中) */
    private String status;

    /** 驳回原因 */
    private String rejectReason;

    /** 提交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date submitTime;

    /** 确认时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date confirmTime;

}

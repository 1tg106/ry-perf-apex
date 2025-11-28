package com.ruoyi.perf.domain;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 绩效内容对象 perf_performance_content
 * 
 * @author liudahua
 * @date 2025-11-26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PerfPerformanceContent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 内容ID */
    private Long id;

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
}

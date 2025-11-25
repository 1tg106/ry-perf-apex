package com.ruoyi.perf.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 绩效周期对象 perf_period
 * 
 * @author liudahua
 * @date 2025-11-25
 */
@Data
public class PerfPeriod extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 绩效周期ID */
    private Long periodId;

    /** 周期名称(如：2024年Q1) */
    @Excel(name = "周期名称(如：2024年Q1)")
    private String periodName;

    /** 周期类型(MONTH:月度, QUARTER:季度, HALF_YEAR:半年度, YEAR:年度) */
    @Excel(name = "周期类型(MONTH:月度, QUARTER:季度, HALF_YEAR:半年度, YEAR:年度)")
    private String periodType;

    /** 提交截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "提交截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date submitDeadline;

    /** 评分截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "评分截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date scoreDeadline;

    /** 状态(0:准备中 1:进行中 2:已结束) */
    @Excel(name = "状态(0:准备中 1:进行中 2:已结束)")
    private String status;

}

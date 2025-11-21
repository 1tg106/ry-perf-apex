package com.ruoyi.system.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 绩效周期视图对象 sys_period
 * 
 * @author 柒喜
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysPeriodVO extends BaseEntity
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

    /** 开始日期 */
    private Date startDate;

    /** 结束日期 */
    private Date endDate;

    /** 提交截止时间 */
    private Date submitDeadline;

    /** 评分截止时间 */
    private Date scoreDeadline;

    /** 状态(0:准备中 1:进行中 2:已结束) */
    @Excel(name = "状态(0:准备中 1:进行中 2:已结束)")
    private String status;

}
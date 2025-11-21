package com.ruoyi.system.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 绩效周期对象 sys_period
 *
 * @author 柒喜
 * @date 2025-11-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_period")
public class SysPeriod extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 绩效周期ID */
    @TableId(type = IdType.AUTO)
    private Long periodId;

    /** 周期名称(如：2024年Q1) */
    @Excel(name = "周期名称(如：2024年Q1)")
    private String periodName;

    /** 周期类型(MONTH:月度, QUARTER:季度, HALF_YEAR:半年度, YEAR:年度) */
    @Excel(name = "周期类型(MONTH:月度, QUARTER:季度, HALF_YEAR:半年度, YEAR:年度)")
    private String periodType;

    /** 开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

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

    /** 删除标志（0:存在 1:删除） */
    @TableLogic
    private String delFlag;
}
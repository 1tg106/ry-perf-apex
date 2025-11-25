package com.ruoyi.perf.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    /** 删除标志（0:存在 1:删除） */
    private String delFlag;

    public void setPeriodId(Long periodId) 
    {
        this.periodId = periodId;
    }

    public Long getPeriodId() 
    {
        return periodId;
    }

    public void setPeriodName(String periodName) 
    {
        this.periodName = periodName;
    }

    public String getPeriodName() 
    {
        return periodName;
    }

    public void setPeriodType(String periodType) 
    {
        this.periodType = periodType;
    }

    public String getPeriodType() 
    {
        return periodType;
    }

    public void setSubmitDeadline(Date submitDeadline) 
    {
        this.submitDeadline = submitDeadline;
    }

    public Date getSubmitDeadline() 
    {
        return submitDeadline;
    }

    public void setScoreDeadline(Date scoreDeadline) 
    {
        this.scoreDeadline = scoreDeadline;
    }

    public Date getScoreDeadline() 
    {
        return scoreDeadline;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
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
            .append("periodId", getPeriodId())
            .append("periodName", getPeriodName())
            .append("periodType", getPeriodType())
            .append("submitDeadline", getSubmitDeadline())
            .append("scoreDeadline", getScoreDeadline())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

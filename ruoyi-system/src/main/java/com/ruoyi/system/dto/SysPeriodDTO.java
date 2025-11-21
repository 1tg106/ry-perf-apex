package com.ruoyi.system.dto;

import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 绩效周期DTO对象 sys_period
 * 
 * @author ruoyi
 */
public class SysPeriodDTO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 绩效周期ID */
    private Long periodId;

    /** 周期名称(如：2024年Q1) */
    private String periodName;

    /** 周期类型(MONTH:月度, QUARTER:季度, HALF_YEAR:半年度, YEAR:年度) */
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
    private String status;

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

    public void setStartDate(Date startDate) 
    {
        this.startDate = startDate;
    }

    public Date getStartDate() 
    {
        return startDate;
    }

    public void setEndDate(Date endDate) 
    {
        this.endDate = endDate;
    }

    public Date getEndDate() 
    {
        return endDate;
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
}
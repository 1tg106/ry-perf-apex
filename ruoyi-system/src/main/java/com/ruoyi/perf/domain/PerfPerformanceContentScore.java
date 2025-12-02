package com.ruoyi.perf.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 绩效指标评分对象 perf_performance_content_score
 * 
 * @author liudahua
 * @date 2025-12-02
 */
public class PerfPerformanceContentScore extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 绩效ID */
    private Long id;

    /** 绩效内容id */
    @Excel(name = "绩效内容id")
    private Long contentId;

    /** 评分人ID */
    @Excel(name = "评分人ID")
    private Long scoreUserId;

    /** 得分 */
    @Excel(name = "得分")
    private BigDecimal score;

    /** 删除标志（0:存在 1:删除） */
    private String delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setContentId(Long contentId) 
    {
        this.contentId = contentId;
    }

    public Long getContentId() 
    {
        return contentId;
    }

    public void setScoreUserId(Long scoreUserId) 
    {
        this.scoreUserId = scoreUserId;
    }

    public Long getScoreUserId() 
    {
        return scoreUserId;
    }

    public void setScore(BigDecimal score) 
    {
        this.score = score;
    }

    public BigDecimal getScore() 
    {
        return score;
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
            .append("id", getId())
            .append("contentId", getContentId())
            .append("scoreUserId", getScoreUserId())
            .append("score", getScore())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

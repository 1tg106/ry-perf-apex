package com.ruoyi.perf.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 绩效指标评分对象 vo
 * 
 * @author liudahua
 * @date 2025-12-02
 */
@Data
public class PerfScoreItemVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 绩效ID */
    private Long id;

    /** 绩效id */
    @Excel(name = "绩效id")
    private Long performanceId;

    /** 绩效内容id */
    @Excel(name = "绩效内容id")
    private Long contentId;

    /** 模版指标id */
    @Excel(name = "模版指标id")
    private Long itemId;

    /** 评分人ID */
    @Excel(name = "评分人ID")
    private Long scoreUserId;

    /** 得分 */
    @Excel(name = "得分")
    private BigDecimal score;

    /** 是否评分: 0-否 1-是 */
    @Excel(name = "是否评分")
    private Integer ifScore;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 备注 */
    private String remark;

    /** 自评目标 */
    private String selfTarget;

    /** 自评成果 */
    private String selfResult;

    /** 自评分数 */
    private BigDecimal selfScore;

    /** 自评评语 */
    private String selfComment;

    /** 指标名称 */
    private String itemName;

    /** 指标类型(OBJECTIVE:目标, KEY_RESULT:关键成果, COMPETENCY:能力项) */
    private String itemType;

    /** 权重(0-100) */
    private BigDecimal weight;

    /** 评分标准描述 */
    private String scoreStandard;

    /** 最低分 */
    private BigDecimal minScore;

    /** 最高分 */
    private BigDecimal maxScore;
}

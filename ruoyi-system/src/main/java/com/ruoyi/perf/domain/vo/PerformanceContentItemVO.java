package com.ruoyi.perf.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 绩效内容对象 performanceContentVO
 * 
 * @author liudahua
 * @date 2025-11-26
 */
@Data
public class PerformanceContentItemVO implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 内容ID */
    private Long id;

    /** 绩效ID */
    private Long performanceId;

    /** 指标项ID */
    private Long itemId;

    /** 自评目标 */
    private String selfTarget;

    /** 自评成果 */
    private String selfResult;

    /** 自评分数 */
    private BigDecimal selfScore;

    /** 自评评语 */
    private String selfComment;

    /** 最终评分 */
    private BigDecimal finalScore;

    /** 最终评语 */
    private String finalComment;

    /** 排序 */
    private Long sortOrder;

    /** 模板ID */
    private Long templateId;

    /** 指标名称 */
    private String itemName;

    /** 指标类型(OBJECTIVE:目标, KEY_RESULT:关键成果, COMPETENCY:能力项) */
    private String itemType;

    /** 父指标ID */
    private Long parentId;

    /** 权重(0-100) */
    private BigDecimal weight;

    /** 评分标准描述 */
    private String scoreStandard;

    /** 最低分 */
    private BigDecimal minScore;

    /** 最高分 */
    private BigDecimal maxScore;

    /** 子指标项 */
    private List<PerformanceContentItemVO> children;

}

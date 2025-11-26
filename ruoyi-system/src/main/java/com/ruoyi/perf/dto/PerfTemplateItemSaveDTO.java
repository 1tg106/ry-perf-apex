package com.ruoyi.perf.dto;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 模板指标对象 perf_template_item
 * 
 * @author liudahua
 * @date 2025-11-25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PerfTemplateItemSaveDTO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 指标项ID */
    private Long id;

    /** 模板ID */
    private Long templateId;

    /** 父指标ID */
    private Long parentId;

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

    /** 排序 */
    private Long sortOrder;

}

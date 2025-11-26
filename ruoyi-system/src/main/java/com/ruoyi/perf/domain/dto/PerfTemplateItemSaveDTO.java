package com.ruoyi.perf.domain.dto;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "模板ID不能为空")
    private Long templateId;

    /** 父指标ID */
    private Long parentId;

    /** 指标名称 */
    @NotBlank(message = "指标名称不能为空")
    private String itemName;

    /** 指标类型(OBJECTIVE:目标, KEY_RESULT:关键成果, COMPETENCY:能力项) */
    @NotBlank(message = "指标类型不能为空")
    private String itemType;

    /** 权重(0-100) */
    @NotNull(message = "权重不能为空")
    private BigDecimal weight;

    /** 评分标准描述 */
    private String scoreStandard;

    /** 最低分 */
    @NotNull(message = "最低分不能为空")
    private BigDecimal minScore;

    /** 最高分 */
    @NotNull(message = "最高分不能为空")
    private BigDecimal maxScore;

    /** 排序 */
    private Long sortOrder;

}

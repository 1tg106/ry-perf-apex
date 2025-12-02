package com.ruoyi.perf.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 模板指标对象 perf_template_item
 * 
 * @author liudahua
 * @date 2025-11-25
 */
@Data
public class PerfTemplateItemVO implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 指标项ID */
    private Long id;

    /** 模板ID */
    @Excel(name = "模板ID")
    private Long templateId;

    /** 父指标ID */
    @Excel(name = "父指标ID")
    private Long parentId;

    /** 评分人IDs */
    private List<Long> scoreUserIds;

    /** 指标名称 */
    @Excel(name = "指标名称")
    private String itemName;

    /** 指标类型(OBJECTIVE:目标, KEY_RESULT:关键成果, COMPETENCY:能力项) */
    @Excel(name = "指标类型(OBJECTIVE:目标, KEY_RESULT:关键成果, COMPETENCY:能力项)")
    private String itemType;

    /** 权重(0-100) */
    @Excel(name = "权重(0-100)")
    private BigDecimal weight;

    /** 评分标准描述 */
    @Excel(name = "评分标准描述")
    private String scoreStandard;

    /** 最低分 */
    @Excel(name = "最低分")
    private BigDecimal minScore;

    /** 最高分 */
    @Excel(name = "最高分")
    private BigDecimal maxScore;

    /** 排序 */
    @Excel(name = "排序")
    private Long sortOrder;

    /** 删除标志（0:存在 1:删除） */
    private String delFlag;

    private String templateName;

}

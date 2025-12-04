package com.ruoyi.perf.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author liudahua
 * @date 2025-11-26
 */
@Data
public class PerfContentVO implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 内容ID */
    private Long id;

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

    /** 创建人 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 绩效内容评分列表 */
    private List<PerfContentScoreVO> perfContentScoreVOList;

}

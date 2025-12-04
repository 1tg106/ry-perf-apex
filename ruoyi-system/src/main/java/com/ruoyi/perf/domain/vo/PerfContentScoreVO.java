package com.ruoyi.perf.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author liudahua
 * @date 2025-11-26
 */
@Data
public class PerfContentScoreVO implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 内容ID */
    private Long id;

    /** 绩效内容id */
    private Long contentId;

    /** 模版指标id */
    private Long itemId;

    /** 评分人ID */
    private Long scoreUserId;

    /** 评分人名称 */
    private String scoreUserName;

    /** 得分 */
    private BigDecimal score;

    /** 是否评分: 0-否 1-是 */
    private Integer ifScore;

    /** 备注 */
    private String remark;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateTime;
}

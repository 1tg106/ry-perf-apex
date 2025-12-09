package com.ruoyi.perf.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PerformanceDistributeVO {

    /**
     * 类型：1-优秀(90+) 2-良好(80-89) 3-合格(70-79) 4-需改进(<70)
     */
    private Integer scoreLevel;

    /**
     * 分数
     */
    private String score;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 百分比
     */
    private BigDecimal percentage;

}

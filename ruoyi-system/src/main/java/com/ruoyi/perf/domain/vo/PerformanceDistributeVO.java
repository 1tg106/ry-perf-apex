package com.ruoyi.perf.domain.vo;

import lombok.Data;

@Data
public class PerformanceDistributeVO {

    /**
     * 类型：excellent-优秀 good-良好 standard-合格 bad-需改进
     */
    private String type;

    /**
     * 分数
     */
    private String score;

    /**
     * 数量
     */
    private Integer count;

}

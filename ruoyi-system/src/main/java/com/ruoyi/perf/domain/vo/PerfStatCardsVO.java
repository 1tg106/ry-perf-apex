package com.ruoyi.perf.domain.vo;

import lombok.Data;

@Data
public class PerfStatCardsVO {

    /**
     * 进行中周期数量
     */
    private Long periodCount;

    /**
     * 进行中周期数量
     */
    private Long finishPerformanceCount;

    /**
     * 申诉数量
     */
    private Long appealCount;

    /**
     * 待面谈数量
     */
    private Long interviewCount;

}

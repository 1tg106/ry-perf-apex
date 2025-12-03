package com.ruoyi.perf.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PerfScoreDTO extends PageDTO{

    // 是否已评分
    private Integer ifScore;
    
}

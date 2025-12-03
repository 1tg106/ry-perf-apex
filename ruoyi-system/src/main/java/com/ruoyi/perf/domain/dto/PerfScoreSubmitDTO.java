package com.ruoyi.perf.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
public class PerfScoreSubmitDTO {

    private Long id;

    private BigDecimal score;

    private String remark;
}

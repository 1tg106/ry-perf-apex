package com.ruoyi.perf.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PerfAppealAddDTO {

    /** 绩效ID */
    @NotNull(message = "绩效ID不能为空")
    private Long performanceId;

    /** 申诉理由 */
    @NotNull(message = "申诉理由不能为空")
    private String appealReason;
}

package com.ruoyi.perf.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class PerfPerformanceAuditDTO {

    private Long id;

    /** 状态(CONFIRMED:确认, REJECTED:驳回) */
    @NotNull(message = "状态不能为空")
    private String status;

    /** 驳回原因 */
    private String rejectReason;
}

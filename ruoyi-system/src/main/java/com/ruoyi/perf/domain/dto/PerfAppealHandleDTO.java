package com.ruoyi.perf.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class PerfAppealHandleDTO {

    /** 绩效ID */
    @NotNull(message = "绩效ID不能为空")
    private Long id;

    /** 处理意见 */
    @NotNull(message = "处理意见")
    private String processComment;

    /** 调整分数 */
    private BigDecimal adjustScore;

    /** 是否调整分数(0:否 1:是) */
    private Integer ifAdjustScore;
}

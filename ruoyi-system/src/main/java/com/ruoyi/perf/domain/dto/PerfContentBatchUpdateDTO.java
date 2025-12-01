package com.ruoyi.perf.domain.dto;

import com.ruoyi.perf.domain.PerfPerformanceContent;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 绩效内容批量更新DTO
 * 
 * @author liudahua
 * @date 2025-11-26
 */
@Data
public class PerfContentBatchUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 绩效内容列表 */
    private List<PerfPerformanceContent> contentList;

    /** 绩效ID */
    @NotNull(message = "绩效ID不能为空")
    private Long performanceId;

    /** 绩效状态(提交时使用) */
    @NotNull(message = "绩效状态不能为空")
    private String performanceStatus;

    /** 绩效步骤(提交时使用) */
    private Long performanceStep;
}
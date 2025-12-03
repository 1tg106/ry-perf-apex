package com.ruoyi.common.enums;

/**
 * Performance Step Enum
 */
public enum PerformanceStep {

    DRAFT(0, "草稿/自评中"),
    PENDING_DEPT_HEAD_SCORE(1, "待评分"),
    PENDING_HR_CONFIRMATION(2, "待HR确认"),
    PENDING_APPEAL(3, "申诉中"),
    PENDING_APPEAL_COMPLETED(4, "申诉完成"),
    PENDING_REJECTED(5, "已驳回"),
    COMPLETED(6, "已完成");

    private final Integer stepValue;
    private final String description;

    PerformanceStep(Integer stepValue, String description) {
        this.stepValue = stepValue;
        this.description = description;
    }

    public Integer getStepValue() {
        return stepValue;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Get PerfStep by step value
     */
    public static PerformanceStep fromStepValue(Long stepValue) {
        for (PerformanceStep step : PerformanceStep.values()) {
            if (step.getStepValue().equals(stepValue)) {
                return step;
            }
        }
        throw new IllegalArgumentException("Invalid step value: " + stepValue);
    }
}

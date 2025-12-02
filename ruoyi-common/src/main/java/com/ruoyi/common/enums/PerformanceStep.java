package com.ruoyi.common.enums;

/**
 * Performance Step Enum
 */
public enum PerformanceStep {

    DRAFT(0, "草稿/自评中"),
    PENDING_SUPERVISOR_SCORE(1, "待上级评分"),
    PENDING_DEPT_HEAD_SCORE(2, "待负责人评分"),
    PENDING_HR_CONFIRMATION(3, "待HR确认"),
    COMPLETED(4, "已完成");

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

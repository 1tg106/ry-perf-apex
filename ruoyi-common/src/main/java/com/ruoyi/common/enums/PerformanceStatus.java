package com.ruoyi.common.enums;

/**
 * Performance Status Enum
 */
public enum PerformanceStatus {

    DRAFT("DRAFT","草稿"),
    PENDING_SUBMISSION("PENDING_SUBMISSION","待提交"),
    PENDING_SCORE("PENDING_SCORE","待评分"),
    PENDING_AUDIT("PENDING_AUDIT","待审核人确认"),
    CONFIRMED("CONFIRMED","已确认"),
    REJECTED("REJECTED","已驳回"),
    CANCELLATION("CANCELLATION","已作废"),
    APPEAL("APPEAL","申诉中");

    private final String code;
    private final String desc;

    PerformanceStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static PerformanceStatus getByCode(String code) {
        for (PerformanceStatus status : PerformanceStatus.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

}


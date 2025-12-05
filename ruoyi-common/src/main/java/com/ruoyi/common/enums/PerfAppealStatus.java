package com.ruoyi.common.enums;

public enum PerfAppealStatus {

    PENDING("PENDING","待处理"),
    RESOLVED("RESOLVED","已处理"),
    REJECTED("REJECTED","已驳回"),
    ;

    private final String code;
    private final String desc;

    PerfAppealStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static PerfAppealStatus getByCode(String code) {
        for (PerfAppealStatus status : PerfAppealStatus.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}

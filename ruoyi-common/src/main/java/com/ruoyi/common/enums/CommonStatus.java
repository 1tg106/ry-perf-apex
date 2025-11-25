package com.ruoyi.common.enums;

public enum CommonStatus {
    /**
     * 正常
     */
    NORMAL("0", "正常"),
    /**
     * 禁用
     */
    DISABLE("1", "禁用");

    private String code;
    private String info;

    CommonStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

}

package com.ruoyi.common.enums;

/**
 * 用户状态
 * 
 * @author liudahua
 */
public enum PerfPeriodEnum
{
    // 周期类型
    MONTH("MONTH", "月度"),
    QUARTER("QUARTER", "季度"),
    HALF_YEAR("HALF_YEAR", "半年度"),
    YEAR("YEAR", "年度"),

    // 状态
    PREPARE("0", "准备中"),
    UNDERWAY("1", "进行中"),
    FINISHED("2", "已结束"),


    ;

    private final String code;
    private final String info;

    PerfPeriodEnum(String code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public String getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}

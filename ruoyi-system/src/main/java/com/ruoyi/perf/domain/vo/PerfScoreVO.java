package com.ruoyi.perf.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 绩效指标评分对象 vo
 * 
 * @author liudahua
 * @date 2025-12-02
 */
@Data
public class PerfScoreVO extends PerfPerformanceVO {

    @Excel(name = "绩效指标评分列表")
    private List<PerfScoreItemVO> perfScoreItemVOList;
}

package com.ruoyi.perf.domain.vo;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

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

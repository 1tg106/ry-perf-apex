package com.ruoyi.perf.domain.vo;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PerfTemplateVO implements Serializable {

    private Long id;

    /** 模板名称 */
    @Excel(name = "模板名称")
    private String templateName;

    /** 模板类型(KPI:关键指标, OKR:目标与成果, COMPETENCY:能力素质) */
    @Excel(name = "模板类型")
    private String templateType;

    /** 适用部门ID */
    private Long deptId;

    /** 适用部门名称 */
    @Excel(name = "适用部门名称")
    private String deptName;

    /** 适用岗位IDS */
    private List<Long> postIds;

    /** 适用岗位名称 */
    @Excel(name = "适用岗位名称")
    private List<String> postNames;

    /** 状态(0:正常 1:停用) */
    private String status;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;
}

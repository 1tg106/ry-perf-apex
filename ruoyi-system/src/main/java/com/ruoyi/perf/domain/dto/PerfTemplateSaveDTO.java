package com.ruoyi.perf.domain.dto;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PerfTemplateSaveDTO implements Serializable {

    private Long id;

    /** 模板名称 */
    private String templateName;

    /** 模板类型(KPI:关键指标, OKR:目标与成果, COMPETENCY:能力素质) */
    private String templateType;

    /** 适用部门ID */
    private Long deptId;

    /** 适用岗位IDS */
    private List<Integer> postIds;

    /** 状态(0:正常 1:停用) */
    private String status;

    /** 备注 */
    private String remark;
}

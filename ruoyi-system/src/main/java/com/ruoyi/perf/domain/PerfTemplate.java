package com.ruoyi.perf.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 绩效模板对象 perf_template
 * 
 * @author liudahua
 * @date 2025-11-25
 */
@Data
public class PerfTemplate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 模板ID */
    private Long id;

    /** 模板名称 */
    @Excel(name = "模板名称")
    private String templateName;

    /** 模板类型(KPI:关键指标, OKR:目标与成果, COMPETENCY:能力素质) */
    @Excel(name = "模板类型(KPI:关键指标, OKR:目标与成果, COMPETENCY:能力素质)")
    private String templateType;

    /** 默认评分人ID */
    @Excel(name = "默认评分人ID")
    private Long defaultScoreId;

    /** 适用部门ID */
    @Excel(name = "适用部门ID")
    private Long deptId;

    /** 适用岗位IDS */
    @Excel(name = "适用岗位IDS")
    private String postIds;

    /** 状态(0:正常 1:停用) */
    @Excel(name = "状态(0:正常 1:停用)")
    private String status;

    /** 删除标志（0:存在 1:删除） */
    private String delFlag;

}

package com.ruoyi.web.controller.performance;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.PerfScoreFlow;
import com.ruoyi.system.service.IPerfScoreFlowService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 评分流程Controller
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
@RestController
@RequestMapping("/system/flow")
public class PerfScoreFlowController extends BaseController
{
    @Autowired
    private IPerfScoreFlowService perfScoreFlowService;

    /**
     * 查询评分流程列表
     */
    @PreAuthorize("@ss.hasPermi('system:flow:list')")
    @GetMapping("/list")
    public TableDataInfo list(PerfScoreFlow perfScoreFlow)
    {
        startPage();
        List<PerfScoreFlow> list = perfScoreFlowService.selectPerfScoreFlowList(perfScoreFlow);
        return getDataTable(list);
    }

    /**
     * 导出评分流程列表
     */
    @PreAuthorize("@ss.hasPermi('system:flow:export')")
    @Log(title = "评分流程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PerfScoreFlow perfScoreFlow)
    {
        List<PerfScoreFlow> list = perfScoreFlowService.selectPerfScoreFlowList(perfScoreFlow);
        ExcelUtil<PerfScoreFlow> util = new ExcelUtil<PerfScoreFlow>(PerfScoreFlow.class);
        util.exportExcel(response, list, "评分流程数据");
    }

    /**
     * 获取评分流程详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:flow:query')")
    @GetMapping(value = "/{flowId}")
    public AjaxResult getInfo(@PathVariable("flowId") Long flowId)
    {
        return success(perfScoreFlowService.selectPerfScoreFlowByFlowId(flowId));
    }

    /**
     * 新增评分流程
     */
    @PreAuthorize("@ss.hasPermi('system:flow:add')")
    @Log(title = "评分流程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PerfScoreFlow perfScoreFlow)
    {
        return toAjax(perfScoreFlowService.insertPerfScoreFlow(perfScoreFlow));
    }

    /**
     * 修改评分流程
     */
    @PreAuthorize("@ss.hasPermi('system:flow:edit')")
    @Log(title = "评分流程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PerfScoreFlow perfScoreFlow)
    {
        return toAjax(perfScoreFlowService.updatePerfScoreFlow(perfScoreFlow));
    }

    /**
     * 删除评分流程
     */
    @PreAuthorize("@ss.hasPermi('system:flow:remove')")
    @Log(title = "评分流程", businessType = BusinessType.DELETE)
	@DeleteMapping("/{flowIds}")
    public AjaxResult remove(@PathVariable Long[] flowIds)
    {
        return toAjax(perfScoreFlowService.deletePerfScoreFlowByFlowIds(flowIds));
    }
}

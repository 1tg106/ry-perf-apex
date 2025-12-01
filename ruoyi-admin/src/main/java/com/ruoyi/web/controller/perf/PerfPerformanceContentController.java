package com.ruoyi.web.controller.perf;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ruoyi.perf.domain.dto.PerfContentBatchUpdateDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.perf.domain.PerfPerformanceContent;
import com.ruoyi.perf.service.IPerfPerformanceContentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 绩效内容Controller
 * 
 * @author liudahua
 * @date 2025-11-26
 */
@RestController
@RequestMapping("/perf/content")
public class PerfPerformanceContentController extends BaseController
{
    @Autowired
    private IPerfPerformanceContentService perfPerformanceContentService;

    /**
     * 查询绩效内容列表
     */
    @PreAuthorize("@ss.hasPermi('perf:content:list')")
    @GetMapping("/list")
    public TableDataInfo list(PerfPerformanceContent perfPerformanceContent)
    {
        startPage();
        List<PerfPerformanceContent> list = perfPerformanceContentService.selectPerfPerformanceContentList(perfPerformanceContent);
        return getDataTable(list);
    }

    /**
     * 导出绩效内容列表
     */
    @PreAuthorize("@ss.hasPermi('perf:content:export')")
    @Log(title = "绩效内容", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PerfPerformanceContent perfPerformanceContent)
    {
        List<PerfPerformanceContent> list = perfPerformanceContentService.selectPerfPerformanceContentList(perfPerformanceContent);
        ExcelUtil<PerfPerformanceContent> util = new ExcelUtil<PerfPerformanceContent>(PerfPerformanceContent.class);
        util.exportExcel(response, list, "绩效内容数据");
    }

    /**
     * 获取绩效内容详细信息
     */
    @PreAuthorize("@ss.hasPermi('perf:content:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(perfPerformanceContentService.selectPerfPerformanceContentById(id));
    }

    /**
     * 新增绩效内容
     */
    @PreAuthorize("@ss.hasPermi('perf:content:add')")
    @Log(title = "绩效内容", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PerfPerformanceContent perfPerformanceContent)
    {
        return toAjax(perfPerformanceContentService.insertPerfPerformanceContent(perfPerformanceContent));
    }

    /**
     * 修改绩效内容
     */
    @PreAuthorize("@ss.hasPermi('perf:content:edit')")
    @Log(title = "绩效内容", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PerfPerformanceContent perfPerformanceContent)
    {
        return toAjax(perfPerformanceContentService.updatePerfPerformanceContent(perfPerformanceContent));
    }

    /**
     * 批量更新绩效内容
     */
    @PreAuthorize("@ss.hasPermi('perf:content:edit')")
    @Log(title = "绩效内容", businessType = BusinessType.UPDATE)
    @PutMapping("/batch")
    public AjaxResult editBatch(@Valid @RequestBody PerfContentBatchUpdateDTO updateDTO)
    {
        return toAjax(perfPerformanceContentService.updatePerfPerformanceContentBatch(updateDTO));
    }

    /**
     * 删除绩效内容
     */
    @PreAuthorize("@ss.hasPermi('perf:content:remove')")
    @Log(title = "绩效内容", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(perfPerformanceContentService.deletePerfPerformanceContentByIds(ids));
    }

    /**
     * 查询绩效内容列表
     */
    @GetMapping(value = "getContentListByPerformanceId")
    public AjaxResult selectPerformanceContentItemVOList(@RequestParam("performanceId") Long performanceId)
    {
        return success(perfPerformanceContentService.selectPerformanceContentItemVOList(performanceId));
    }
}

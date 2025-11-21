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
import com.ruoyi.system.domain.PerfPerformanceContent;
import com.ruoyi.system.service.IPerfPerformanceContentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 绩效内容Controller
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
@RestController
@RequestMapping("/system/content")
public class PerfPerformanceContentController extends BaseController
{
    @Autowired
    private IPerfPerformanceContentService perfPerformanceContentService;

    /**
     * 查询绩效内容列表
     */
    @PreAuthorize("@ss.hasPermi('system:content:list')")
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
    @PreAuthorize("@ss.hasPermi('system:content:export')")
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
    @PreAuthorize("@ss.hasPermi('system:content:query')")
    @GetMapping(value = "/{contentId}")
    public AjaxResult getInfo(@PathVariable("contentId") Long contentId)
    {
        return success(perfPerformanceContentService.selectPerfPerformanceContentByContentId(contentId));
    }

    /**
     * 新增绩效内容
     */
    @PreAuthorize("@ss.hasPermi('system:content:add')")
    @Log(title = "绩效内容", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PerfPerformanceContent perfPerformanceContent)
    {
        return toAjax(perfPerformanceContentService.insertPerfPerformanceContent(perfPerformanceContent));
    }

    /**
     * 修改绩效内容
     */
    @PreAuthorize("@ss.hasPermi('system:content:edit')")
    @Log(title = "绩效内容", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PerfPerformanceContent perfPerformanceContent)
    {
        return toAjax(perfPerformanceContentService.updatePerfPerformanceContent(perfPerformanceContent));
    }

    /**
     * 删除绩效内容
     */
    @PreAuthorize("@ss.hasPermi('system:content:remove')")
    @Log(title = "绩效内容", businessType = BusinessType.DELETE)
	@DeleteMapping("/{contentIds}")
    public AjaxResult remove(@PathVariable Long[] contentIds)
    {
        return toAjax(perfPerformanceContentService.deletePerfPerformanceContentByContentIds(contentIds));
    }
}

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
import com.ruoyi.system.domain.PerfAppeal;
import com.ruoyi.system.service.IPerfAppealService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 绩效申诉Controller
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
@RestController
@RequestMapping("/system/appeal")
public class PerfAppealController extends BaseController
{
    @Autowired
    private IPerfAppealService perfAppealService;

    /**
     * 查询绩效申诉列表
     */
    @PreAuthorize("@ss.hasPermi('system:appeal:list')")
    @GetMapping("/list")
    public TableDataInfo list(PerfAppeal perfAppeal)
    {
        startPage();
        List<PerfAppeal> list = perfAppealService.selectPerfAppealList(perfAppeal);
        return getDataTable(list);
    }

    /**
     * 导出绩效申诉列表
     */
    @PreAuthorize("@ss.hasPermi('system:appeal:export')")
    @Log(title = "绩效申诉", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PerfAppeal perfAppeal)
    {
        List<PerfAppeal> list = perfAppealService.selectPerfAppealList(perfAppeal);
        ExcelUtil<PerfAppeal> util = new ExcelUtil<PerfAppeal>(PerfAppeal.class);
        util.exportExcel(response, list, "绩效申诉数据");
    }

    /**
     * 获取绩效申诉详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:appeal:query')")
    @GetMapping(value = "/{appealId}")
    public AjaxResult getInfo(@PathVariable("appealId") Long appealId)
    {
        return success(perfAppealService.selectPerfAppealByAppealId(appealId));
    }

    /**
     * 新增绩效申诉
     */
    @PreAuthorize("@ss.hasPermi('system:appeal:add')")
    @Log(title = "绩效申诉", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PerfAppeal perfAppeal)
    {
        return toAjax(perfAppealService.insertPerfAppeal(perfAppeal));
    }

    /**
     * 修改绩效申诉
     */
    @PreAuthorize("@ss.hasPermi('system:appeal:edit')")
    @Log(title = "绩效申诉", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PerfAppeal perfAppeal)
    {
        return toAjax(perfAppealService.updatePerfAppeal(perfAppeal));
    }

    /**
     * 删除绩效申诉
     */
    @PreAuthorize("@ss.hasPermi('system:appeal:remove')")
    @Log(title = "绩效申诉", businessType = BusinessType.DELETE)
	@DeleteMapping("/{appealIds}")
    public AjaxResult remove(@PathVariable Long[] appealIds)
    {
        return toAjax(perfAppealService.deletePerfAppealByAppealIds(appealIds));
    }
}

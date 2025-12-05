package com.ruoyi.web.controller.perf;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ruoyi.perf.domain.dto.PerfAppealAddDTO;
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
import com.ruoyi.perf.domain.PerfAppeal;
import com.ruoyi.perf.service.IPerfAppealService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 绩效申诉Controller
 * 
 * @author liudahua
 * @date 2025-11-26
 */
@RestController
@RequestMapping("/perf/appeal")
public class PerfAppealController extends BaseController
{
    @Autowired
    private IPerfAppealService perfAppealService;

    /**
     * 查询绩效申诉列表
     */
    @PreAuthorize("@ss.hasPermi('perf:appeal:list')")
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
    @PreAuthorize("@ss.hasPermi('perf:appeal:export')")
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
    @PreAuthorize("@ss.hasPermi('perf:appeal:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(perfAppealService.selectPerfAppealById(id));
    }

    /**
     * 新增绩效申诉
     */
    @Log(title = "绩效申诉", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Valid @RequestBody PerfAppealAddDTO addDTO)
    {
        return toAjax(perfAppealService.insertPerfAppeal(addDTO));
    }

    /**
     * 修改绩效申诉
     */
    @PreAuthorize("@ss.hasPermi('perf:appeal:edit')")
    @Log(title = "绩效申诉", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PerfAppeal perfAppeal)
    {
        return toAjax(perfAppealService.updatePerfAppeal(perfAppeal));
    }

    /**
     * 删除绩效申诉
     */
    @PreAuthorize("@ss.hasPermi('perf:appeal:remove')")
    @Log(title = "绩效申诉", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(perfAppealService.deletePerfAppealByIds(ids));
    }
}

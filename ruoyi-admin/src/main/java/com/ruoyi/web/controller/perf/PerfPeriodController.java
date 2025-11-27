package com.ruoyi.web.controller.perf;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.perf.domain.PerfPeriod;
import com.ruoyi.perf.domain.vo.CommonChooseVO;
import com.ruoyi.perf.service.IPerfPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 绩效周期Controller
 * 
 * @author liudahua
 * @date 2025-11-25
 */
@RestController
@RequestMapping("/perf/period")
public class PerfPeriodController extends BaseController
{
    @Autowired
    private IPerfPeriodService perfPeriodService;

    /**
     * 查询绩效周期列表
     */
    @PreAuthorize("@ss.hasPermi('perf:period:list')")
    @GetMapping("/list")
    public TableDataInfo list(PerfPeriod perfPeriod)
    {
        startPage();
        List<PerfPeriod> list = perfPeriodService.selectPerfPeriodList(perfPeriod);
        return getDataTable(list);
    }

    /**
     * 导出绩效周期列表
     */
    @PreAuthorize("@ss.hasPermi('perf:period:export')")
    @Log(title = "绩效周期", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PerfPeriod perfPeriod)
    {
        List<PerfPeriod> list = perfPeriodService.selectPerfPeriodList(perfPeriod);
        ExcelUtil<PerfPeriod> util = new ExcelUtil<PerfPeriod>(PerfPeriod.class);
        util.exportExcel(response, list, "绩效周期数据");
    }

    /**
     * 获取绩效周期详细信息
     */
    @PreAuthorize("@ss.hasPermi('perf:period:query')")
    @GetMapping(value = "/{periodId}")
    public AjaxResult getInfo(@PathVariable("periodId") Long periodId)
    {
        return success(perfPeriodService.selectPerfPeriodByPeriodId(periodId));
    }

    /**
     * 新增绩效周期
     */
    @PreAuthorize("@ss.hasPermi('perf:period:add')")
    @Log(title = "绩效周期", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PerfPeriod perfPeriod)
    {
        return toAjax(perfPeriodService.insertPerfPeriod(perfPeriod));
    }

    /**
     * 修改绩效周期
     */
    @PreAuthorize("@ss.hasPermi('perf:period:edit')")
    @Log(title = "绩效周期", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PerfPeriod perfPeriod)
    {
        return toAjax(perfPeriodService.updatePerfPeriod(perfPeriod));
    }

    /**
     * 删除绩效周期
     */
    @PreAuthorize("@ss.hasPermi('perf:period:remove')")
    @Log(title = "绩效周期", businessType = BusinessType.DELETE)
	@DeleteMapping("/{periodIds}")
    public AjaxResult remove(@PathVariable Long[] periodIds)
    {
        return toAjax(perfPeriodService.deletePerfPeriodByPeriodIds(periodIds));
    }

    /**
     * 获取模板选择列表
     * @return
     */
    @GetMapping("/getPerfChooseList")
    public AjaxResult getCommonChooseList()
    {
        List<CommonChooseVO> list = perfPeriodService.getPerfChooseList();
        return success(list);
    }
}

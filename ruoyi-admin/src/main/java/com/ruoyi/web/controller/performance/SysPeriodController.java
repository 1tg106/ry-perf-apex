package com.ruoyi.web.controller.performance;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.vo.SysPeriodVO;
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
import com.ruoyi.system.domain.SysPeriod;
import com.ruoyi.system.service.ISysPeriodService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 绩效周期Controller
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
@RestController
@RequestMapping("/system/period")
public class SysPeriodController extends BaseController
{
    @Autowired
    private ISysPeriodService sysPeriodService;

    /**
     * 查询绩效周期列表
     */
    @PreAuthorize("@ss.hasPermi('system:period:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysPeriod sysPeriod)
    {
        startPage();
        List<SysPeriodVO> list = sysPeriodService.selectSysPeriodList(sysPeriod);
        return getDataTable(list);
    }

    /**
     * 导出绩效周期列表
     */
    @PreAuthorize("@ss.hasPermi('system:period:export')")
    @Log(title = "绩效周期", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysPeriod sysPeriod)
    {
//        List<SysPeriod> list = sysPeriodService.selectSysPeriodList(sysPeriod);
//        ExcelUtil<SysPeriod> util = new ExcelUtil<SysPeriod>(SysPeriod.class);
//        util.exportExcel(response, list, "绩效周期数据");
    }

    /**
     * 获取绩效周期详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:period:query')")
    @GetMapping(value = "/{periodId}")
    public AjaxResult getInfo(@PathVariable("periodId") Long periodId)
    {
        return success(sysPeriodService.selectSysPeriodByPeriodId(periodId));
    }

    /**
     * 新增绩效周期
     */
    @PreAuthorize("@ss.hasPermi('system:period:add')")
    @Log(title = "绩效周期", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysPeriod sysPeriod)
    {
        return toAjax(sysPeriodService.insertSysPeriod(sysPeriod));
    }

    /**
     * 修改绩效周期
     */
    @PreAuthorize("@ss.hasPermi('system:period:edit')")
    @Log(title = "绩效周期", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysPeriod sysPeriod)
    {
        return toAjax(sysPeriodService.updateSysPeriod(sysPeriod));
    }

    /**
     * 删除绩效周期
     */
    @PreAuthorize("@ss.hasPermi('system:period:remove')")
    @Log(title = "绩效周期", businessType = BusinessType.DELETE)
	@DeleteMapping("/{periodIds}")
    public AjaxResult remove(@PathVariable Long[] periodIds)
    {
        return toAjax(sysPeriodService.deleteSysPeriodByPeriodIds(periodIds));
    }

    /**
     * 删除绩效周期
     */
    @Log(title = "绩效周期", businessType = BusinessType.DELETE)
    @DeleteMapping("/{periodId}")
    public AjaxResult removeById(@PathVariable Long periodId)
    {
        return toAjax(sysPeriodService.deleteSysPeriodByPeriodId(periodId));
    }
}

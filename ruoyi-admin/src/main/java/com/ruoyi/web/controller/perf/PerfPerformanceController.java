package com.ruoyi.web.controller.perf;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.perf.domain.dto.PerfPerformanceSaveDTO;
import com.ruoyi.perf.domain.vo.PerfPerformanceVO;
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
import com.ruoyi.perf.domain.PerfPerformance;
import com.ruoyi.perf.service.IPerfPerformanceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 绩效实例Controller
 * 
 * @author liudahua
 * @date 2025-11-26
 */
@RestController
@RequestMapping("/perf/performance")
public class PerfPerformanceController extends BaseController
{
    @Autowired
    private IPerfPerformanceService perfPerformanceService;

    /**
     * 查询绩效实例列表
     */
    @PreAuthorize("@ss.hasPermi('perf:performance:list')")
    @GetMapping("/list")
    public TableDataInfo list(PerfPerformance perfPerformance)
    {
        startPage();
        List<PerfPerformanceVO> list = perfPerformanceService.selectPerfPerformanceList(perfPerformance);
        return getDataTable(list);
    }

    /**
     * 查询当前用户绩效列表
     */
    @PreAuthorize("@ss.hasPermi('perf:performance:list')")
    @GetMapping("/myList")
    public TableDataInfo myList(PerfPerformance perfPerformance)
    {
        startPage();
        // 设置当前用户ID作为查询条件
        perfPerformance.setUserId(SecurityUtils.getUserId());
        List<PerfPerformanceVO> list = perfPerformanceService.selectPerfPerformanceList(perfPerformance);
        return getDataTable(list);
    }

    /**
     * 导出绩效实例列表
     */
    @PreAuthorize("@ss.hasPermi('perf:performance:export')")
    @Log(title = "绩效实例", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PerfPerformance perfPerformance)
    {
        List<PerfPerformanceVO> list = perfPerformanceService.selectPerfPerformanceList(perfPerformance);
        ExcelUtil<PerfPerformanceVO> util = new ExcelUtil<PerfPerformanceVO>(PerfPerformanceVO.class);
        util.exportExcel(response, list, "绩效实例数据");
    }

    /**
     * 获取绩效实例详细信息
     */
    @PreAuthorize("@ss.hasPermi('perf:performance:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(perfPerformanceService.selectPerfPerformanceById(id));
    }

    /**
     * 新增绩效实例
     */
    @PreAuthorize("@ss.hasPermi('perf:performance:add')")
    @Log(title = "绩效实例", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PerfPerformanceSaveDTO saveDTO)
    {
        return toAjax(perfPerformanceService.insertPerfPerformance(saveDTO));
    }

    /**
     * 修改绩效实例
     */
    @PreAuthorize("@ss.hasPermi('perf:performance:edit')")
    @Log(title = "绩效实例", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PerfPerformanceSaveDTO saveDTO)
    {
        return toAjax(perfPerformanceService.updatePerfPerformance(saveDTO));
    }

    /**
     * 删除绩效实例
     */
    @PreAuthorize("@ss.hasPermi('perf:performance:remove')")
    @Log(title = "绩效实例", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(perfPerformanceService.deletePerfPerformanceByIds(ids));
    }
}

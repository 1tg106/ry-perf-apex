package com.ruoyi.web.controller.perf;

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
import com.ruoyi.perf.domain.PerfWorkflowConfig;
import com.ruoyi.perf.service.IPerfWorkflowConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 绩效流程配置Controller
 * 
 * @author liudahua
 * @date 2025-11-26
 */
@RestController
@RequestMapping("/perf/flowConfig")
public class PerfWorkflowConfigController extends BaseController
{
    @Autowired
    private IPerfWorkflowConfigService perfWorkflowConfigService;

    /**
     * 查询绩效流程配置列表
     */
    @PreAuthorize("@ss.hasPermi('perf:flowConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(PerfWorkflowConfig perfWorkflowConfig)
    {
        startPage();
        List<PerfWorkflowConfig> list = perfWorkflowConfigService.selectPerfWorkflowConfigList(perfWorkflowConfig);
        return getDataTable(list);
    }

    /**
     * 导出绩效流程配置列表
     */
    @PreAuthorize("@ss.hasPermi('perf:flowConfig:export')")
    @Log(title = "绩效流程配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PerfWorkflowConfig perfWorkflowConfig)
    {
        List<PerfWorkflowConfig> list = perfWorkflowConfigService.selectPerfWorkflowConfigList(perfWorkflowConfig);
        ExcelUtil<PerfWorkflowConfig> util = new ExcelUtil<PerfWorkflowConfig>(PerfWorkflowConfig.class);
        util.exportExcel(response, list, "绩效流程配置数据");
    }

    /**
     * 获取绩效流程配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('perf:flowConfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(perfWorkflowConfigService.selectPerfWorkflowConfigById(id));
    }

    /**
     * 新增绩效流程配置
     */
    @PreAuthorize("@ss.hasPermi('perf:flowConfig:add')")
    @Log(title = "绩效流程配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PerfWorkflowConfig perfWorkflowConfig)
    {
        return toAjax(perfWorkflowConfigService.insertPerfWorkflowConfig(perfWorkflowConfig));
    }

    /**
     * 修改绩效流程配置
     */
    @PreAuthorize("@ss.hasPermi('perf:flowConfig:edit')")
    @Log(title = "绩效流程配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PerfWorkflowConfig perfWorkflowConfig)
    {
        return toAjax(perfWorkflowConfigService.updatePerfWorkflowConfig(perfWorkflowConfig));
    }

    /**
     * 删除绩效流程配置
     */
    @PreAuthorize("@ss.hasPermi('perf:flowConfig:remove')")
    @Log(title = "绩效流程配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(perfWorkflowConfigService.deletePerfWorkflowConfigByIds(ids));
    }
}

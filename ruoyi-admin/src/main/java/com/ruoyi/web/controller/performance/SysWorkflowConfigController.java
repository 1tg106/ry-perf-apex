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
import com.ruoyi.system.domain.SysWorkflowConfig;
import com.ruoyi.system.service.ISysWorkflowConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 绩效流程配置Controller
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
@RestController
@RequestMapping("/system/workflowConfig")
public class SysWorkflowConfigController extends BaseController
{
    @Autowired
    private ISysWorkflowConfigService sysWorkflowConfigService;

    /**
     * 查询绩效流程配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysWorkflowConfig sysWorkflowConfig)
    {
        startPage();
        List<SysWorkflowConfig> list = sysWorkflowConfigService.selectSysWorkflowConfigList(sysWorkflowConfig);
        return getDataTable(list);
    }

    /**
     * 导出绩效流程配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:config:export')")
    @Log(title = "绩效流程配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysWorkflowConfig sysWorkflowConfig)
    {
        List<SysWorkflowConfig> list = sysWorkflowConfigService.selectSysWorkflowConfigList(sysWorkflowConfig);
        ExcelUtil<SysWorkflowConfig> util = new ExcelUtil<SysWorkflowConfig>(SysWorkflowConfig.class);
        util.exportExcel(response, list, "绩效流程配置数据");
    }

    /**
     * 获取绩效流程配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:config:query')")
    @GetMapping(value = "/{configId}")
    public AjaxResult getInfo(@PathVariable("configId") Long configId)
    {
        return success(sysWorkflowConfigService.selectSysWorkflowConfigByConfigId(configId));
    }

    /**
     * 新增绩效流程配置
     */
    @PreAuthorize("@ss.hasPermi('system:config:add')")
    @Log(title = "绩效流程配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysWorkflowConfig sysWorkflowConfig)
    {
        return toAjax(sysWorkflowConfigService.insertSysWorkflowConfig(sysWorkflowConfig));
    }

    /**
     * 修改绩效流程配置
     */
    @PreAuthorize("@ss.hasPermi('system:config:edit')")
    @Log(title = "绩效流程配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysWorkflowConfig sysWorkflowConfig)
    {
        return toAjax(sysWorkflowConfigService.updateSysWorkflowConfig(sysWorkflowConfig));
    }

    /**
     * 删除绩效流程配置
     */
    @PreAuthorize("@ss.hasPermi('system:config:remove')")
    @Log(title = "绩效流程配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{configIds}")
    public AjaxResult remove(@PathVariable Long[] configIds)
    {
        return toAjax(sysWorkflowConfigService.deleteSysWorkflowConfigByConfigIds(configIds));
    }
}

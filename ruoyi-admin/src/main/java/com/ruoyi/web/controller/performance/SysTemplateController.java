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
import com.ruoyi.system.domain.SysTemplate;
import com.ruoyi.system.service.ISysTemplateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 绩效模板Controller
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
@RestController
@RequestMapping("/system/template")
public class SysTemplateController extends BaseController
{
    @Autowired
    private ISysTemplateService sysTemplateService;

    /**
     * 查询绩效模板列表
     */
    @PreAuthorize("@ss.hasPermi('system:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysTemplate sysTemplate)
    {
        startPage();
        List<SysTemplate> list = sysTemplateService.selectSysTemplateList(sysTemplate);
        return getDataTable(list);
    }

    /**
     * 导出绩效模板列表
     */
    @PreAuthorize("@ss.hasPermi('system:template:export')")
    @Log(title = "绩效模板", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysTemplate sysTemplate)
    {
        List<SysTemplate> list = sysTemplateService.selectSysTemplateList(sysTemplate);
        ExcelUtil<SysTemplate> util = new ExcelUtil<SysTemplate>(SysTemplate.class);
        util.exportExcel(response, list, "绩效模板数据");
    }

    /**
     * 获取绩效模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:template:query')")
    @GetMapping(value = "/{templateId}")
    public AjaxResult getInfo(@PathVariable("templateId") Long templateId)
    {
        return success(sysTemplateService.selectSysTemplateByTemplateId(templateId));
    }

    /**
     * 新增绩效模板
     */
    @PreAuthorize("@ss.hasPermi('system:template:add')")
    @Log(title = "绩效模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysTemplate sysTemplate)
    {
        return toAjax(sysTemplateService.insertSysTemplate(sysTemplate));
    }

    /**
     * 修改绩效模板
     */
    @PreAuthorize("@ss.hasPermi('system:template:edit')")
    @Log(title = "绩效模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysTemplate sysTemplate)
    {
        return toAjax(sysTemplateService.updateSysTemplate(sysTemplate));
    }

    /**
     * 删除绩效模板
     */
    @PreAuthorize("@ss.hasPermi('system:template:remove')")
    @Log(title = "绩效模板", businessType = BusinessType.DELETE)
	@DeleteMapping("/{templateIds}")
    public AjaxResult remove(@PathVariable Long[] templateIds)
    {
        return toAjax(sysTemplateService.deleteSysTemplateByTemplateIds(templateIds));
    }
}

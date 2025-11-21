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
import com.ruoyi.system.domain.SysTemplateItem;
import com.ruoyi.system.service.ISysTemplateItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 模板指标Controller
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
@RestController
@RequestMapping("/system/item")
public class SysTemplateItemController extends BaseController
{
    @Autowired
    private ISysTemplateItemService sysTemplateItemService;

    /**
     * 查询模板指标列表
     */
    @PreAuthorize("@ss.hasPermi('system:item:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysTemplateItem sysTemplateItem)
    {
        startPage();
        List<SysTemplateItem> list = sysTemplateItemService.selectSysTemplateItemList(sysTemplateItem);
        return getDataTable(list);
    }

    /**
     * 导出模板指标列表
     */
    @PreAuthorize("@ss.hasPermi('system:item:export')")
    @Log(title = "模板指标", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysTemplateItem sysTemplateItem)
    {
        List<SysTemplateItem> list = sysTemplateItemService.selectSysTemplateItemList(sysTemplateItem);
        ExcelUtil<SysTemplateItem> util = new ExcelUtil<SysTemplateItem>(SysTemplateItem.class);
        util.exportExcel(response, list, "模板指标数据");
    }

    /**
     * 获取模板指标详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:item:query')")
    @GetMapping(value = "/{itemId}")
    public AjaxResult getInfo(@PathVariable("itemId") Long itemId)
    {
        return success(sysTemplateItemService.selectSysTemplateItemByItemId(itemId));
    }

    /**
     * 新增模板指标
     */
    @PreAuthorize("@ss.hasPermi('system:item:add')")
    @Log(title = "模板指标", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysTemplateItem sysTemplateItem)
    {
        return toAjax(sysTemplateItemService.insertSysTemplateItem(sysTemplateItem));
    }

    /**
     * 修改模板指标
     */
    @PreAuthorize("@ss.hasPermi('system:item:edit')")
    @Log(title = "模板指标", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysTemplateItem sysTemplateItem)
    {
        return toAjax(sysTemplateItemService.updateSysTemplateItem(sysTemplateItem));
    }

    /**
     * 删除模板指标
     */
    @PreAuthorize("@ss.hasPermi('system:item:remove')")
    @Log(title = "模板指标", businessType = BusinessType.DELETE)
	@DeleteMapping("/{itemIds}")
    public AjaxResult remove(@PathVariable Long[] itemIds)
    {
        return toAjax(sysTemplateItemService.deleteSysTemplateItemByItemIds(itemIds));
    }
}

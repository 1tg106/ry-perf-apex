package com.ruoyi.web.controller.perf;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ruoyi.perf.dto.PerfTemplateItemSaveDTO;
import com.ruoyi.perf.vo.CommonChooseVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
import com.ruoyi.perf.domain.PerfTemplateItem;
import com.ruoyi.perf.service.IPerfTemplateItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 模板指标Controller
 * 
 * @author liudahua
 * @date 2025-11-25
 */
@RestController
@RequestMapping("/perf/templateItem")
public class PerfTemplateItemController extends BaseController
{
    @Autowired
    private IPerfTemplateItemService perfTemplateItemService;

    /**
     * 查询模板指标列表
     */
    @PreAuthorize("@ss.hasPermi('perf:templateItem:list')")
    @GetMapping("/list")
    public TableDataInfo list(PerfTemplateItem perfTemplateItem)
    {
        startPage();
        List<PerfTemplateItem> list = perfTemplateItemService.selectPerfTemplateItemList(perfTemplateItem);
        return getDataTable(list);
    }

    /**
     * 导出模板指标列表
     */
    @PreAuthorize("@ss.hasPermi('perf:templateItem:export')")
    @Log(title = "模板指标", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PerfTemplateItem perfTemplateItem)
    {
        List<PerfTemplateItem> list = perfTemplateItemService.selectPerfTemplateItemList(perfTemplateItem);
        ExcelUtil<PerfTemplateItem> util = new ExcelUtil<PerfTemplateItem>(PerfTemplateItem.class);
        util.exportExcel(response, list, "模板指标数据");
    }

    /**
     * 获取模板指标详细信息
     */
    @PreAuthorize("@ss.hasPermi('perf:templateItem:query')")
    @GetMapping(value = "/{itemId}")
    public AjaxResult getInfo(@PathVariable("itemId") Long itemId)
    {
        return success(perfTemplateItemService.selectPerfTemplateItemByItemId(itemId));
    }

    /**
     * 新增模板指标
     */
    @PreAuthorize("@ss.hasPermi('perf:templateItem:add')")
    @Log(title = "模板指标", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Valid @RequestBody PerfTemplateItemSaveDTO itemSaveDTO)
    {
        return toAjax(perfTemplateItemService.insertPerfTemplateItem(itemSaveDTO));
    }

    /**
     * 修改模板指标
     */
    @PreAuthorize("@ss.hasPermi('perf:templateItem:edit')")
    @Log(title = "模板指标", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Valid @RequestBody PerfTemplateItemSaveDTO itemSaveDTO)
    {
        return toAjax(perfTemplateItemService.updatePerfTemplateItem(itemSaveDTO));
    }

    /**
     * 删除模板指标
     */
    @PreAuthorize("@ss.hasPermi('perf:templateItem:remove')")
    @Log(title = "模板指标", businessType = BusinessType.DELETE)
	@DeleteMapping("/{itemIds}")
    public AjaxResult remove(@PathVariable Long[] itemIds)
    {
        return toAjax(perfTemplateItemService.deletePerfTemplateItemByItemIds(itemIds));
    }

    /**
     * 获取模板指标列表
     * @return
     */
    @PreAuthorize("@ss.hasPermi('perf:templateItem:list')")
    @GetMapping("/getItemChooseList")
    public AjaxResult getCommonChooseList()
    {
        List<CommonChooseVO> list = perfTemplateItemService.getCommonChooseList();
        return success(list);
    }

    /**
     * 复制指标
     */
    @PreAuthorize("@ss.hasPermi('perf:templateItem:add')")
    @Log(title = "模板指标", businessType = BusinessType.INSERT)
    @PostMapping("/copyTemplateItem/{id}")
    public AjaxResult copyTemplateItem(@PathVariable("id") Long id)
    {
        return success(perfTemplateItemService.copyTemplateItem(id));
    }
}

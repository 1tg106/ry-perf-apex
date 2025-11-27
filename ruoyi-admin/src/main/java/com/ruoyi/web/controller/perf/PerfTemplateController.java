package com.ruoyi.web.controller.perf;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.perf.domain.dto.PerfTemplateSaveDTO;
import com.ruoyi.perf.domain.vo.CommonChooseVO;
import com.ruoyi.perf.domain.vo.PerfTemplateVO;
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
import com.ruoyi.perf.domain.PerfTemplate;
import com.ruoyi.perf.service.IPerfTemplateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 绩效模板Controller
 * 
 * @author liudahua
 * @date 2025-11-25
 */
@RestController
@RequestMapping("/perf/template")
public class PerfTemplateController extends BaseController
{
    @Autowired
    private IPerfTemplateService perfTemplateService;

    /**
     * 查询绩效模板列表
     */
    @PreAuthorize("@ss.hasPermi('perf:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(PerfTemplate perfTemplate)
    {
        startPage();
        List<PerfTemplateVO> list = perfTemplateService.selectPerfTemplateList(perfTemplate);
        return getDataTable(list);
    }

    /**
     * 导出绩效模板列表
     */
    @PreAuthorize("@ss.hasPermi('perf:template:export')")
    @Log(title = "绩效模板", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PerfTemplate perfTemplate)
    {
        List<PerfTemplateVO> list = perfTemplateService.selectPerfTemplateList(perfTemplate);
        ExcelUtil<PerfTemplateVO> util = new ExcelUtil<PerfTemplateVO>(PerfTemplateVO.class);
        util.exportExcel(response, list, "绩效模板数据");
    }

    /**
     * 获取绩效模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('perf:template:query')")
    @GetMapping(value = "/{templateId}")
    public AjaxResult getInfo(@PathVariable("templateId") Long templateId)
    {
        return success(perfTemplateService.selectPerfTemplateByTemplateId(templateId));
    }

    /**
     * 新增绩效模板
     */
    @PreAuthorize("@ss.hasPermi('perf:template:add')")
    @Log(title = "绩效模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PerfTemplateSaveDTO perfTemplate)
    {
        return toAjax(perfTemplateService.insertPerfTemplate(perfTemplate));
    }

    /**
     * 修改绩效模板
     */
    @PreAuthorize("@ss.hasPermi('perf:template:edit')")
    @Log(title = "绩效模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PerfTemplateSaveDTO perfTemplate)
    {
        return toAjax(perfTemplateService.updatePerfTemplate(perfTemplate));
    }

    /**
     * 删除绩效模板
     */
    @PreAuthorize("@ss.hasPermi('perf:template:remove')")
    @Log(title = "绩效模板", businessType = BusinessType.DELETE)
	@DeleteMapping("/{templateIds}")
    public AjaxResult remove(@PathVariable Long[] templateIds)
    {
        return toAjax(perfTemplateService.deletePerfTemplateByTemplateIds(templateIds));
    }

    /**
     * 获取模板选择列表
     * @return
     */
    @GetMapping("/getTemplateChooseList")
    public AjaxResult getCommonChooseList()
    {
        List<CommonChooseVO> list = perfTemplateService.getCommonChooseList();
        return success(list);
    }
}

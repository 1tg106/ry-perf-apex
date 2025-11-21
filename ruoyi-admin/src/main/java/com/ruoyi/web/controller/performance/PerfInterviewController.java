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
import com.ruoyi.system.domain.PerfInterview;
import com.ruoyi.system.service.IPerfInterviewService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 绩效面谈Controller
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
@RestController
@RequestMapping("/system/interview")
public class PerfInterviewController extends BaseController
{
    @Autowired
    private IPerfInterviewService perfInterviewService;

    /**
     * 查询绩效面谈列表
     */
    @PreAuthorize("@ss.hasPermi('system:interview:list')")
    @GetMapping("/list")
    public TableDataInfo list(PerfInterview perfInterview)
    {
        startPage();
        List<PerfInterview> list = perfInterviewService.selectPerfInterviewList(perfInterview);
        return getDataTable(list);
    }

    /**
     * 导出绩效面谈列表
     */
    @PreAuthorize("@ss.hasPermi('system:interview:export')")
    @Log(title = "绩效面谈", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PerfInterview perfInterview)
    {
        List<PerfInterview> list = perfInterviewService.selectPerfInterviewList(perfInterview);
        ExcelUtil<PerfInterview> util = new ExcelUtil<PerfInterview>(PerfInterview.class);
        util.exportExcel(response, list, "绩效面谈数据");
    }

    /**
     * 获取绩效面谈详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:interview:query')")
    @GetMapping(value = "/{interviewId}")
    public AjaxResult getInfo(@PathVariable("interviewId") Long interviewId)
    {
        return success(perfInterviewService.selectPerfInterviewByInterviewId(interviewId));
    }

    /**
     * 新增绩效面谈
     */
    @PreAuthorize("@ss.hasPermi('system:interview:add')")
    @Log(title = "绩效面谈", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PerfInterview perfInterview)
    {
        return toAjax(perfInterviewService.insertPerfInterview(perfInterview));
    }

    /**
     * 修改绩效面谈
     */
    @PreAuthorize("@ss.hasPermi('system:interview:edit')")
    @Log(title = "绩效面谈", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PerfInterview perfInterview)
    {
        return toAjax(perfInterviewService.updatePerfInterview(perfInterview));
    }

    /**
     * 删除绩效面谈
     */
    @PreAuthorize("@ss.hasPermi('system:interview:remove')")
    @Log(title = "绩效面谈", businessType = BusinessType.DELETE)
	@DeleteMapping("/{interviewIds}")
    public AjaxResult remove(@PathVariable Long[] interviewIds)
    {
        return toAjax(perfInterviewService.deletePerfInterviewByInterviewIds(interviewIds));
    }
}

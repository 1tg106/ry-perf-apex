package com.ruoyi.web.controller.perf;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.perf.domain.vo.PerfInterviewVO;
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
import com.ruoyi.perf.domain.PerfInterview;
import com.ruoyi.perf.service.IPerfInterviewService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 绩效面谈Controller
 * 
 * @author liudahua
 * @date 2025-11-26
 */
@RestController
@RequestMapping("/perf/interview")
public class PerfInterviewController extends BaseController
{
    @Autowired
    private IPerfInterviewService perfInterviewService;

    /**
     * 查询绩效面谈列表
     */
    @PreAuthorize("@ss.hasPermi('perf:interview:list')")
    @GetMapping("/list")
    public TableDataInfo list(PerfInterview perfInterview)
    {
        startPage();
        List<PerfInterview> list = perfInterviewService.selectPerfInterviewList(perfInterview);
        return getDataTable(list);
    }

    /**
     * 关联查询绩效面谈列表
     */
    @PreAuthorize("@ss.hasPermi('perf:interview:list')")
    @GetMapping("/relevanceList")
    public TableDataInfo selectPerfInterviewListVO(PerfInterview perfInterview)
    {
        startPage();
        List<PerfInterviewVO> list = perfInterviewService.selectPerfInterviewListVO(perfInterview);
        return getDataTable(list);
    }

    /**
     * 关联查询绩效面谈详情
     */
    @PreAuthorize("@ss.hasPermi('perf:interview:query')")
    @GetMapping(value = "/relevance/{id}")
    public AjaxResult selectPerfInterviewVOById(@PathVariable("id") Long id)
    {
        return success(perfInterviewService.selectPerfInterviewVOById(id));
    }

    /**
     * 导出绩效面谈列表
     */
    @PreAuthorize("@ss.hasPermi('perf:interview:export')")
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
    @PreAuthorize("@ss.hasPermi('perf:interview:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(perfInterviewService.selectPerfInterviewById(id));
    }

    /**
     * 新增绩效面谈
     */
    @PreAuthorize("@ss.hasPermi('perf:interview:add')")
    @Log(title = "绩效面谈", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PerfInterview perfInterview)
    {
        return toAjax(perfInterviewService.insertPerfInterview(perfInterview));
    }

    /**
     * 修改绩效面谈
     */
    @PreAuthorize("@ss.hasPermi('perf:interview:edit')")
    @Log(title = "绩效面谈", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PerfInterview perfInterview)
    {
        return toAjax(perfInterviewService.updatePerfInterview(perfInterview));
    }

    /**
     * 删除绩效面谈
     */
    @PreAuthorize("@ss.hasPermi('perf:interview:remove')")
    @Log(title = "绩效面谈", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(perfInterviewService.deletePerfInterviewByIds(ids));
    }
}

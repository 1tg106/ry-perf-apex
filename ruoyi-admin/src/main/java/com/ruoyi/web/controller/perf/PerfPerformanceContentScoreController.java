package com.ruoyi.web.controller.perf;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.perf.domain.dto.PerfScoreDTO;
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
import com.ruoyi.perf.domain.PerfPerformanceContentScore;
import com.ruoyi.perf.service.IPerfPerformanceContentScoreService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 绩效指标评分Controller
 * 
 * @author liudahua
 * @date 2025-12-02
 */
@RestController
@RequestMapping("/perf/score")
public class PerfPerformanceContentScoreController extends BaseController
{
    @Autowired
    private IPerfPerformanceContentScoreService perfPerformanceContentScoreService;

    /**
     * 查询根据用户id获取待评分列表
     */
    @GetMapping("/list")
    public TableDataInfo list(PerfScoreDTO perfScoreDTO)
    {
        startPage();
        List<PerfPerformanceVO> list = perfPerformanceContentScoreService.getWaitScorePerformanceList(perfScoreDTO);
        return getDataTable(list);
    }

    /**
     * 获取绩效指标评分详细信息
     */
    @GetMapping(value = "/{performanceId}")
    public AjaxResult getInfo(@PathVariable("performanceId") Long performanceId)
    {
        return success(perfPerformanceContentScoreService.getPerformanceScoreDetailByPerformanceId(performanceId));
    }

}

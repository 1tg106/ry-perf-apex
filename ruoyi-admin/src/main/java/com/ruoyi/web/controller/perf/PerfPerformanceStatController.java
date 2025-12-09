package com.ruoyi.web.controller.perf;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.PerfPeriodEnum;
import com.ruoyi.perf.domain.PerfAppeal;
import com.ruoyi.perf.domain.PerfInterview;
import com.ruoyi.perf.domain.PerfPeriod;
import com.ruoyi.perf.domain.dto.PerfScoreDTO;
import com.ruoyi.perf.domain.vo.PerfAppealVO;
import com.ruoyi.perf.domain.vo.PerfInterviewVO;
import com.ruoyi.perf.domain.vo.PerfPerformanceVO;
import com.ruoyi.perf.domain.vo.PerfStatCardsVO;
import com.ruoyi.perf.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 绩效实例Controller
 * 
 * @author liudahua
 * @date 2025-11-26
 */
@RestController
@RequestMapping("/perf/stat")
public class PerfPerformanceStatController extends BaseController
{
    @Autowired
    private IPerfPerformanceService perfPerformanceService;

    @Autowired
    private IPerfPeriodService perfPeriodService;

    @Autowired
    private IPerfAppealService perfAppealService;

    @Autowired
    private IPerfInterviewService perfInterviewService;

    @Autowired
    private IPerfPerformanceContentScoreService perfPerformanceContentScoreService;

    /**
     * 统计卡片
     */
    @GetMapping("/statCards")
    public AjaxResult getStatCards()
    {
        PerfStatCardsVO perfStatCards = new PerfStatCardsVO();
        perfStatCards.setPeriodCount(perfPeriodService.getPeriodStatCount());
        perfStatCards.setFinishPerformanceCount(perfPerformanceService.getFinishPerformanceStatCount());
        perfStatCards.setAppealCount(perfAppealService.getPerfAppealStatCount());
        perfStatCards.setInterviewCount(perfInterviewService.getPerfInterviewStatCount());
        return success(perfStatCards);
    }

    /**
     * 获取绩效评分分布
     */
    @GetMapping("/getPerformanceScoreDistributeList")
    public AjaxResult getPerformanceScoreDistributeList()
    {
        return success(perfPerformanceService.getPerformanceScoreDistributeList());
    }

    /**
     * 查询根据用户id获取待评分列表
     */
    @GetMapping("/waitMyScorePerformanceList")
    public TableDataInfo waitMyScorePerformanceList(PerfScoreDTO perfScoreDTO)
    {
        startPage();
        List<PerfPerformanceVO> list = perfPerformanceContentScoreService.getWaitScorePerformanceList(perfScoreDTO);
        return getDataTable(list);
    }

    /**
     * 查询绩效周期概览
     */
    @GetMapping("/getPerfPeriodList")
    public TableDataInfo getPerfPeriodList(PerfPeriod perfPeriod)
    {
        startPage();
        perfPeriod.setStatus(PerfPeriodEnum.UNDERWAY.getCode());
        List<PerfPeriod> list = perfPeriodService.selectPerfPeriodList(perfPeriod);
        return getDataTable(list);
    }

    /**
     * 查询绩效申诉列表
     */
    @GetMapping("/getPerfAppealList")
    public TableDataInfo getPerfAppealList(PerfAppeal perfAppeal)
    {
        startPage();
        List<PerfAppealVO> list = perfAppealService.selectRelevancePerfAppealList(perfAppeal);
        return getDataTable(list);
    }

    /**
     * 查询绩效面谈列表
     */
    @GetMapping("/getPerfInterviewList")
    public TableDataInfo getPerfInterviewList(PerfInterview perfInterview)
    {
        startPage();
        List<PerfInterviewVO> list = perfInterviewService.selectPerfInterviewListVO(perfInterview);
        return getDataTable(list);
    }


}
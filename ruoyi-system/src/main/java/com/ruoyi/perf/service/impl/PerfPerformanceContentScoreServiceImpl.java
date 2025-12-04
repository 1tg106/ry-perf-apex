package com.ruoyi.perf.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.enums.PerformanceStatus;
import com.ruoyi.common.enums.PerformanceStep;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.perf.domain.PerfPerformance;
import com.ruoyi.perf.domain.PerfPerformanceContent;
import com.ruoyi.perf.domain.PerfTemplateItem;
import com.ruoyi.perf.domain.dto.PerfScoreDTO;
import com.ruoyi.perf.domain.dto.PerfScoreSubmitDTO;
import com.ruoyi.perf.domain.vo.PerfContentScoreVO;
import com.ruoyi.perf.domain.vo.PerfPerformanceVO;
import com.ruoyi.perf.domain.vo.PerfScoreItemVO;
import com.ruoyi.perf.domain.vo.PerfScoreVO;
import com.ruoyi.perf.mapper.PerfPerformanceMapper;
import com.ruoyi.perf.service.IPerfTemplateItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.perf.mapper.PerfPerformanceContentScoreMapper;
import com.ruoyi.perf.domain.PerfPerformanceContentScore;
import com.ruoyi.perf.service.IPerfPerformanceContentScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 绩效指标评分Service业务层处理
 * 
 * @author liudahua
 * @date 2025-12-02
 */
@Service
public class PerfPerformanceContentScoreServiceImpl extends ServiceImpl<PerfPerformanceContentScoreMapper,PerfPerformanceContentScore> implements IPerfPerformanceContentScoreService
{
    @Autowired
    private PerfPerformanceContentScoreMapper perfPerformanceContentScoreMapper;

    @Autowired
    private IPerfTemplateItemService perfTemplateItemService;

    @Autowired
    private PerfPerformanceMapper perfPerformanceMapper;

    /**
     * 查询绩效指标评分
     * 
     * @param id 绩效指标评分主键
     * @return 绩效指标评分
     */
    @Override
    public PerfPerformanceContentScore selectPerfPerformanceContentScoreById(Long id)
    {
        return perfPerformanceContentScoreMapper.selectPerfPerformanceContentScoreById(id);
    }

    /**
     * 查询绩效指标评分列表
     * 
     * @param perfPerformanceContentScore 绩效指标评分
     * @return 绩效指标评分
     */
    @Override
    public List<PerfPerformanceContentScore> selectPerfPerformanceContentScoreList(PerfPerformanceContentScore perfPerformanceContentScore)
    {
        return perfPerformanceContentScoreMapper.selectPerfPerformanceContentScoreList(perfPerformanceContentScore);
    }

    /**
     * 新增绩效指标评分
     * 
     * @param perfPerformanceContentScore 绩效指标评分
     * @return 结果
     */
    @Override
    public int insertPerfPerformanceContentScore(PerfPerformanceContentScore perfPerformanceContentScore)
    {
        perfPerformanceContentScore.setCreateTime(DateUtils.getNowDate());
        return perfPerformanceContentScoreMapper.insertPerfPerformanceContentScore(perfPerformanceContentScore);
    }

    /**
     * 修改绩效指标评分
     * 
     * @param perfPerformanceContentScore 绩效指标评分
     * @return 结果
     */
    @Override
    public int updatePerfPerformanceContentScore(PerfPerformanceContentScore perfPerformanceContentScore)
    {
        perfPerformanceContentScore.setUpdateTime(DateUtils.getNowDate());
        return perfPerformanceContentScoreMapper.updatePerfPerformanceContentScore(perfPerformanceContentScore);
    }

    /**
     * 批量删除绩效指标评分
     * 
     * @param ids 需要删除的绩效指标评分主键
     * @return 结果
     */
    @Override
    public int deletePerfPerformanceContentScoreByIds(Long[] ids)
    {
        return perfPerformanceContentScoreMapper.deletePerfPerformanceContentScoreByIds(ids);
    }

    /**
     * 删除绩效指标评分信息
     * 
     * @param id 绩效指标评分主键
     * @return 结果
     */
    @Override
    public int deletePerfPerformanceContentScoreById(Long id)
    {
        return perfPerformanceContentScoreMapper.deletePerfPerformanceContentScoreById(id);
    }

    @Override
    public Boolean savePerfPerformanceContentScore(List<PerfPerformanceContent> perfPerformanceContents, List<Long> itemIds) {
        // 获取对应的指标项
        List<PerfTemplateItem> list = perfTemplateItemService.list(Wrappers.lambdaQuery(PerfTemplateItem.class)
                .in(PerfTemplateItem::getId, itemIds));
        if(list.isEmpty()){
            throw new RuntimeException("没有找到对应的指标项");
        }
        List<PerfPerformanceContentScore> scores = new ArrayList<>();

        List<Long> performanceIds = new ArrayList<>();
        for (PerfPerformanceContent perfPerformanceContent : perfPerformanceContents){
            for (PerfTemplateItem item : list){
                // 判断当前指标项是否需要创建评分人评分
                if(item.getId().equals(perfPerformanceContent.getItemId())){
                    // 默认评分人
                    if(StringUtils.isEmpty(item.getScoreUserIds())){
                        PerfPerformanceContentScore score = new PerfPerformanceContentScore();
                        score.setPerformanceId(perfPerformanceContent.getPerformanceId());
                        score.setContentId(perfPerformanceContent.getId());
                        score.setScoreUserId(item.getDefaultScoreId());
                        score.setItemId(item.getId());
                        score.setIfScore(0);
                        score.setCreateTime(DateUtils.getNowDate());
                        score.setCreateBy(SecurityUtils.getUserId().toString());
                        scores.add(score);
                        continue;
                    }

                    // 多个评分人
                    List<Long> scoreUserIds = Arrays.stream(item.getScoreUserIds().split(","))
                            .map(String::trim) // 去除前后空格
                            .map(Long::parseLong)
                            .collect(Collectors.toList());
                    for (Long scoreUserId : scoreUserIds){
                        PerfPerformanceContentScore score = new PerfPerformanceContentScore();
                        score.setPerformanceId(perfPerformanceContent.getPerformanceId());
                        score.setContentId(perfPerformanceContent.getId());
                        score.setScoreUserId(scoreUserId);
                        score.setItemId(item.getId());
                        score.setIfScore(0);
                        score.setCreateTime(DateUtils.getNowDate());
                        score.setCreateBy(SecurityUtils.getUserId().toString());
                        scores.add(score);
                    }
                }
            }
            performanceIds.add(perfPerformanceContent.getPerformanceId());
        }
        // 删除
        perfPerformanceContentScoreMapper.delete(Wrappers.lambdaQuery(PerfPerformanceContentScore.class)
                .in(PerfPerformanceContentScore::getPerformanceId, performanceIds));
        return this.saveBatch(scores);
    }

    @Override
    public List<PerfPerformanceVO> getWaitScorePerformanceList(PerfScoreDTO perfScoreDTO) {
        // 获取待评分列表
        List<PerfPerformanceContentScore> scoreUserList = perfPerformanceContentScoreMapper.getWaitScoreUserList(SecurityUtils.getUserId(), perfScoreDTO);
        if(scoreUserList ==  null || scoreUserList.isEmpty()){
            return Collections.emptyList();
        }

        // 根据待评分列表获取待评分的绩效实例
        List<Long> performanceIds = scoreUserList.stream().map(PerfPerformanceContentScore::getPerformanceId).collect(Collectors.toList());
        return perfPerformanceMapper.selectPerfPerformanceByIdsList(performanceIds);
    }

    @Override
    public PerfScoreVO getPerformanceScoreDetailByPerformanceId(Long performanceId) {
        PerfPerformanceVO perfPerformanceVO = perfPerformanceMapper.selectPerfPerformanceVOById(performanceId);
        if(perfPerformanceVO == null){
            throw new RuntimeException("绩效实例不存在");
        }
        PerfScoreVO perfScoreVO = new PerfScoreVO();
        BeanUtils.copyProperties(perfPerformanceVO, perfScoreVO);
        List<PerfScoreItemVO> scoreItemVOList = perfPerformanceContentScoreMapper.getPerformanceScoreDetailByPerformanceId(performanceId, SecurityUtils.getUserId());
        perfScoreVO.setPerfScoreItemVOList(scoreItemVOList);
        return perfScoreVO;
    }

    @Override
    public Boolean submitScoreBatch(List<PerfScoreSubmitDTO> perfScoreSubmitDTOS) {
        if(perfScoreSubmitDTOS.isEmpty()){
            throw new RuntimeException("提交的数据为空");
        }

        // 获取对应的数据
        List<Long> ids = perfScoreSubmitDTOS.stream().map(PerfScoreSubmitDTO::getId).collect(Collectors.toList());
        List<PerfPerformanceContentScore> list = this.list(Wrappers.lambdaQuery(PerfPerformanceContentScore.class)
                .in(PerfPerformanceContentScore::getId, ids));
        if(list.isEmpty()){
            throw new RuntimeException("没有找到对应的数据");
        }

        // 获取对应的绩效实例
        Long performanceId = list.get(0).getPerformanceId();
        PerfPerformance perfPerformance = perfPerformanceMapper.selectById(performanceId);
        if(perfPerformance == null){
            throw new RuntimeException("绩效实例不存在");
        }
        if(!perfPerformance.getStatus().equals(PerformanceStatus.PENDING_SCORE.getCode())){
            throw new RuntimeException("该状态不允许操作");
        }

        // 操作数据
        BigDecimal finalScore = BigDecimal.ZERO;
        for (PerfScoreSubmitDTO perfScoreSubmitDTO : perfScoreSubmitDTOS){
            if(perfScoreSubmitDTO.getId() == null){
                throw new RuntimeException("提交数据id不能为空");
            }
            if(perfScoreSubmitDTO.getScore() == null){
                throw new RuntimeException("提交数据分数不能为空");
            }
            finalScore = finalScore.add(perfScoreSubmitDTO.getScore());
            for (PerfPerformanceContentScore score : list){
                if(score.getId().equals(perfScoreSubmitDTO.getId())){
                    score.setScore(perfScoreSubmitDTO.getScore());
                    score.setIfScore(1);
                    score.setRemark(perfScoreSubmitDTO.getRemark());
                    score.setUpdateTime(DateUtils.getNowDate());
                    score.setUpdateBy(SecurityUtils.getUserId().toString());
                }
            }
        }
        boolean updateBatch = this.updateBatchById(list);
        if(updateBatch){
            // 判断是否所有评分人已评分
            long count = this.count(Wrappers.lambdaQuery(PerfPerformanceContentScore.class)
                    .eq(PerfPerformanceContentScore::getPerformanceId, performanceId)
                    .eq(PerfPerformanceContentScore::getIfScore, 0));
            if(count == 0){
                // 绩效实例状态改为待HR确认
                perfPerformance.setStatus(PerformanceStatus.PENDING_HR.getCode());
                perfPerformance.setCurrentStep(Long.valueOf(PerformanceStep.PENDING_HR_CONFIRMATION.getStepValue()));
                perfPerformance.setUpdateTime(DateUtils.getNowDate());
                perfPerformance.setFinalScore(finalScore);
                perfPerformanceMapper.updateById(perfPerformance);
            }
        }
        return updateBatch;
    }

    @Override
    public List<PerfContentScoreVO> getPerformanceScoreByPerformanceIdList(Long performanceId) {
        return perfPerformanceContentScoreMapper.getPerformanceScoreByPerformanceIdList(performanceId);
    }
}

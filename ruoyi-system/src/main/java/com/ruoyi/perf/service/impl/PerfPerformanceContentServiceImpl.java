package com.ruoyi.perf.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.enums.PerformanceStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.perf.domain.PerfPerformance;
import com.ruoyi.perf.domain.PerfPerformanceContent;
import com.ruoyi.perf.domain.PerfTemplateItem;
import com.ruoyi.perf.domain.dto.PerfContentBatchUpdateDTO;
import com.ruoyi.perf.domain.vo.PerformanceContentItemVO;
import com.ruoyi.perf.mapper.PerfPerformanceContentMapper;
import com.ruoyi.perf.mapper.PerfPerformanceMapper;
import com.ruoyi.perf.service.IPerfPerformanceContentScoreService;
import com.ruoyi.perf.service.IPerfPerformanceContentService;
import com.ruoyi.perf.service.IPerfTemplateItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 绩效内容Service业务层处理
 * 
 * @author liudahua
 * @date 2025-11-26
 */
@Service
public class PerfPerformanceContentServiceImpl extends ServiceImpl<PerfPerformanceContentMapper,PerfPerformanceContent> implements IPerfPerformanceContentService
{
    @Autowired
    private PerfPerformanceContentMapper perfPerformanceContentMapper;

    @Autowired
    private PerfPerformanceMapper perfPerformanceMapper;

    @Autowired
    private IPerfTemplateItemService perfTemplateItemService;

    @Autowired
    private IPerfPerformanceContentScoreService perfPerformanceContentScoreService;

    /**
     * 查询绩效内容
     * 
     * @param id 绩效内容主键
     * @return 绩效内容
     */
    @Override
    public PerfPerformanceContent selectPerfPerformanceContentById(Long id)
    {
        return perfPerformanceContentMapper.selectPerfPerformanceContentById(id);
    }

    /**
     * 查询绩效内容列表
     * 
     * @param perfPerformanceContent 绩效内容
     * @return 绩效内容
     */
    @Override
    public List<PerfPerformanceContent> selectPerfPerformanceContentList(PerfPerformanceContent perfPerformanceContent)
    {
        return perfPerformanceContentMapper.selectPerfPerformanceContentList(perfPerformanceContent);
    }

    /**
     * 新增绩效内容
     * 
     * @param perfPerformanceContent 绩效内容
     * @return 结果
     */
    @Override
    public int insertPerfPerformanceContent(PerfPerformanceContent perfPerformanceContent)
    {
        perfPerformanceContent.setCreateTime(DateUtils.getNowDate());
        return perfPerformanceContentMapper.insertPerfPerformanceContent(perfPerformanceContent);
    }

    /**
     * 修改绩效内容
     * 
     * @param perfPerformanceContent 绩效内容
     * @return 结果
     */
    @Override
    public int updatePerfPerformanceContent(PerfPerformanceContent perfPerformanceContent)
    {
        perfPerformanceContent.setUpdateTime(DateUtils.getNowDate());
        return perfPerformanceContentMapper.updatePerfPerformanceContent(perfPerformanceContent);
    }

    /**
     * 批量更新绩效内容
     * 
     * @param updateDTO 批量更新参数
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePerfPerformanceContentBatch(PerfContentBatchUpdateDTO updateDTO) {
        List<PerfPerformanceContent> perfPerformanceContents = updateDTO.getContentList();

        List<Long> itemIds = new ArrayList<>();
        // 设置更新时间
        for (PerfPerformanceContent perfPerformanceContent : perfPerformanceContents) {
            perfPerformanceContent.setUpdateTime(DateUtils.getNowDate());
            perfPerformanceContent.setUpdateBy(SecurityUtils.getUserId().toString());
            itemIds.add(perfPerformanceContent.getItemId());
        }
        
        // 批量更新绩效内容
        boolean result = this.updateBatchById(perfPerformanceContents);
        
        // 如果是提交操作，还需要更新绩效实例状态
        if (PerformanceStatus.PENDING_SCORE.getCode().equals(updateDTO.getPerformanceStatus())) {
            PerfPerformance perfPerformance = perfPerformanceMapper.selectById(updateDTO.getPerformanceId());
            perfPerformance.setStatus(PerformanceStatus.PENDING_SCORE.getCode());
            perfPerformance.setCurrentStep(updateDTO.getPerformanceStep());
            perfPerformance.setUpdateTime(DateUtils.getNowDate());
            perfPerformance.setUpdateBy(SecurityUtils.getUserId().toString());
            perfPerformanceMapper.updateById(perfPerformance);

            // 保存绩效评分项
            perfPerformanceContentScoreService.savePerfPerformanceContentScore(perfPerformanceContents, itemIds);
        }
        
        return result ? perfPerformanceContents.size() : 0;
    }

    /**
     * 批量删除绩效内容
     * 
     * @param ids 需要删除的绩效内容主键
     * @return 结果
     */
    @Override
    public int deletePerfPerformanceContentByIds(Long[] ids)
    {
        return perfPerformanceContentMapper.deletePerfPerformanceContentByIds(ids);
    }

    /**
     * 删除绩效内容信息
     * 
     * @param id 绩效内容主键
     * @return 结果
     */
    @Override
    public int deletePerfPerformanceContentById(Long id)
    {
        return perfPerformanceContentMapper.deletePerfPerformanceContentById(id);
    }

    @Override
    public Boolean saveBatchByItemIds(Long performanceId, List<Long> itemIds) {
        if(itemIds == null || itemIds.size() == 0){
            throw new RuntimeException("该模版没有指标");
        }
        List<PerfPerformanceContent> contentList = new ArrayList<>();
        for (Long itemId : itemIds){
            PerfPerformanceContent content = new PerfPerformanceContent();
            content.setPerformanceId(performanceId);
            content.setItemId(itemId);
            content.setCreateTime(DateUtils.getNowDate());
            content.setCreateBy(SecurityUtils.getUserId().toString());
            contentList.add(content);
        }
        return this.saveBatch(contentList);
    }

    @Override
    public List<PerformanceContentItemVO> selectPerformanceContentItemVOList(Long performanceId) {
        List<PerformanceContentItemVO> itemVOS = perfPerformanceContentMapper.selectPerformanceContentItemVOList(performanceId);

        // 构建树形结构
        return buildPerformanceTree(itemVOS);
    }

    /**
     * 构建绩效指标树形结构
     */
    private List<PerformanceContentItemVO> buildPerformanceTree(List<PerformanceContentItemVO> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        Map<Long, PerformanceContentItemVO> nodeMap = new HashMap<>();
        List<PerformanceContentItemVO> rootNodes = new ArrayList<>();

        // 第一遍遍历：初始化所有节点
        for (PerformanceContentItemVO item : list) {
            item.setChildren(new ArrayList<>());
            nodeMap.put(item.getItemId(), item);
        }

        // 第二遍遍历：构建树形结构
        for (PerformanceContentItemVO item : list) {
            Long parentId = item.getParentId();

            if (parentId == null || parentId == 0 || !nodeMap.containsKey(parentId)) {
                rootNodes.add(item);
            } else {
                PerformanceContentItemVO parent = nodeMap.get(parentId);
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(item);
            }
        }

        // 排序
        sortPerformanceTree(rootNodes);

        return rootNodes;
    }

    /**
     * 递归排序树形结构
     */
    private void sortPerformanceTree(List<PerformanceContentItemVO> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return;
        }

        nodes.sort(Comparator.comparing(PerformanceContentItemVO::getSortOrder,
                Comparator.nullsLast(Comparator.naturalOrder())));

        for (PerformanceContentItemVO node : nodes) {
            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                sortPerformanceTree(node.getChildren());
            }
        }
    }
}

package com.ruoyi.perf.service.impl;

import java.util.*;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.perf.domain.vo.PerformanceContentItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.perf.mapper.PerfPerformanceContentMapper;
import com.ruoyi.perf.domain.PerfPerformanceContent;
import com.ruoyi.perf.service.IPerfPerformanceContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

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

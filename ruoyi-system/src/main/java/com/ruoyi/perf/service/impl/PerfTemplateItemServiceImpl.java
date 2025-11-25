package com.ruoyi.perf.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.perf.mapper.PerfTemplateItemMapper;
import com.ruoyi.perf.domain.PerfTemplateItem;
import com.ruoyi.perf.service.IPerfTemplateItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 模板指标Service业务层处理
 * 
 * @author liudahua
 * @date 2025-11-25
 */
@Service
public class PerfTemplateItemServiceImpl extends ServiceImpl<PerfTemplateItemMapper,PerfTemplateItem> implements IPerfTemplateItemService
{
    @Autowired
    private PerfTemplateItemMapper perfTemplateItemMapper;

    /**
     * 查询模板指标
     * 
     * @param itemId 模板指标主键
     * @return 模板指标
     */
    @Override
    public PerfTemplateItem selectPerfTemplateItemByItemId(Long itemId)
    {
        return perfTemplateItemMapper.selectPerfTemplateItemByItemId(itemId);
    }

    /**
     * 查询模板指标列表
     * 
     * @param perfTemplateItem 模板指标
     * @return 模板指标
     */
    @Override
    public List<PerfTemplateItem> selectPerfTemplateItemList(PerfTemplateItem perfTemplateItem)
    {
        return perfTemplateItemMapper.selectPerfTemplateItemList(perfTemplateItem);
    }

    /**
     * 新增模板指标
     * 
     * @param perfTemplateItem 模板指标
     * @return 结果
     */
    @Override
    public int insertPerfTemplateItem(PerfTemplateItem perfTemplateItem)
    {
        perfTemplateItem.setCreateTime(DateUtils.getNowDate());
        return perfTemplateItemMapper.insertPerfTemplateItem(perfTemplateItem);
    }

    /**
     * 修改模板指标
     * 
     * @param perfTemplateItem 模板指标
     * @return 结果
     */
    @Override
    public int updatePerfTemplateItem(PerfTemplateItem perfTemplateItem)
    {
        perfTemplateItem.setUpdateTime(DateUtils.getNowDate());
        return perfTemplateItemMapper.updatePerfTemplateItem(perfTemplateItem);
    }

    /**
     * 批量删除模板指标
     * 
     * @param itemIds 需要删除的模板指标主键
     * @return 结果
     */
    @Override
    public int deletePerfTemplateItemByItemIds(Long[] itemIds)
    {
        return perfTemplateItemMapper.deletePerfTemplateItemByItemIds(itemIds);
    }

    /**
     * 删除模板指标信息
     * 
     * @param itemId 模板指标主键
     * @return 结果
     */
    @Override
    public int deletePerfTemplateItemByItemId(Long itemId)
    {
        return perfTemplateItemMapper.deletePerfTemplateItemByItemId(itemId);
    }
}

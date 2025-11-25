package com.ruoyi.perf.mapper;

import java.util.List;
import com.ruoyi.perf.domain.PerfTemplateItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 模板指标Mapper接口
 * 
 * @author liudahua
 * @date 2025-11-25
 */
public interface PerfTemplateItemMapper extends BaseMapper<PerfTemplateItem>
{
    /**
     * 查询模板指标
     * 
     * @param itemId 模板指标主键
     * @return 模板指标
     */
    public PerfTemplateItem selectPerfTemplateItemByItemId(Long itemId);

    /**
     * 查询模板指标列表
     * 
     * @param perfTemplateItem 模板指标
     * @return 模板指标集合
     */
    public List<PerfTemplateItem> selectPerfTemplateItemList(PerfTemplateItem perfTemplateItem);

    /**
     * 新增模板指标
     * 
     * @param perfTemplateItem 模板指标
     * @return 结果
     */
    public int insertPerfTemplateItem(PerfTemplateItem perfTemplateItem);

    /**
     * 修改模板指标
     * 
     * @param perfTemplateItem 模板指标
     * @return 结果
     */
    public int updatePerfTemplateItem(PerfTemplateItem perfTemplateItem);

    /**
     * 删除模板指标
     * 
     * @param itemId 模板指标主键
     * @return 结果
     */
    public int deletePerfTemplateItemByItemId(Long itemId);

    /**
     * 批量删除模板指标
     * 
     * @param itemIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePerfTemplateItemByItemIds(Long[] itemIds);
}

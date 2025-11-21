package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysTemplateItem;

/**
 * 模板指标Mapper接口
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
public interface SysTemplateItemMapper 
{
    /**
     * 查询模板指标
     * 
     * @param itemId 模板指标主键
     * @return 模板指标
     */
    public SysTemplateItem selectSysTemplateItemByItemId(Long itemId);

    /**
     * 查询模板指标列表
     * 
     * @param sysTemplateItem 模板指标
     * @return 模板指标集合
     */
    public List<SysTemplateItem> selectSysTemplateItemList(SysTemplateItem sysTemplateItem);

    /**
     * 新增模板指标
     * 
     * @param sysTemplateItem 模板指标
     * @return 结果
     */
    public int insertSysTemplateItem(SysTemplateItem sysTemplateItem);

    /**
     * 修改模板指标
     * 
     * @param sysTemplateItem 模板指标
     * @return 结果
     */
    public int updateSysTemplateItem(SysTemplateItem sysTemplateItem);

    /**
     * 删除模板指标
     * 
     * @param itemId 模板指标主键
     * @return 结果
     */
    public int deleteSysTemplateItemByItemId(Long itemId);

    /**
     * 批量删除模板指标
     * 
     * @param itemIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysTemplateItemByItemIds(Long[] itemIds);
}

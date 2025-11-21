package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysTemplateItemMapper;
import com.ruoyi.system.domain.SysTemplateItem;
import com.ruoyi.system.service.ISysTemplateItemService;

/**
 * 模板指标Service业务层处理
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
@Service
public class SysTemplateItemServiceImpl implements ISysTemplateItemService 
{
    @Autowired
    private SysTemplateItemMapper sysTemplateItemMapper;

    /**
     * 查询模板指标
     * 
     * @param itemId 模板指标主键
     * @return 模板指标
     */
    @Override
    public SysTemplateItem selectSysTemplateItemByItemId(Long itemId)
    {
        return sysTemplateItemMapper.selectSysTemplateItemByItemId(itemId);
    }

    /**
     * 查询模板指标列表
     * 
     * @param sysTemplateItem 模板指标
     * @return 模板指标
     */
    @Override
    public List<SysTemplateItem> selectSysTemplateItemList(SysTemplateItem sysTemplateItem)
    {
        return sysTemplateItemMapper.selectSysTemplateItemList(sysTemplateItem);
    }

    /**
     * 新增模板指标
     * 
     * @param sysTemplateItem 模板指标
     * @return 结果
     */
    @Override
    public int insertSysTemplateItem(SysTemplateItem sysTemplateItem)
    {
        sysTemplateItem.setCreateTime(DateUtils.getNowDate());
        return sysTemplateItemMapper.insertSysTemplateItem(sysTemplateItem);
    }

    /**
     * 修改模板指标
     * 
     * @param sysTemplateItem 模板指标
     * @return 结果
     */
    @Override
    public int updateSysTemplateItem(SysTemplateItem sysTemplateItem)
    {
        sysTemplateItem.setUpdateTime(DateUtils.getNowDate());
        return sysTemplateItemMapper.updateSysTemplateItem(sysTemplateItem);
    }

    /**
     * 批量删除模板指标
     * 
     * @param itemIds 需要删除的模板指标主键
     * @return 结果
     */
    @Override
    public int deleteSysTemplateItemByItemIds(Long[] itemIds)
    {
        return sysTemplateItemMapper.deleteSysTemplateItemByItemIds(itemIds);
    }

    /**
     * 删除模板指标信息
     * 
     * @param itemId 模板指标主键
     * @return 结果
     */
    @Override
    public int deleteSysTemplateItemByItemId(Long itemId)
    {
        return sysTemplateItemMapper.deleteSysTemplateItemByItemId(itemId);
    }
}

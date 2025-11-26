package com.ruoyi.perf.service;

import java.util.List;
import com.ruoyi.perf.domain.PerfTemplateItem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.perf.dto.PerfTemplateItemSaveDTO;
import com.ruoyi.perf.vo.CommonChooseVO;

/**
 * 模板指标Service接口
 * 
 * @author liudahua
 * @date 2025-11-25
 */
public interface IPerfTemplateItemService extends IService<PerfTemplateItem>

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
     * @param itemSaveDTO 模板指标
     * @return 结果
     */
    public int insertPerfTemplateItem(PerfTemplateItemSaveDTO itemSaveDTO);

    /**
     * 修改模板指标
     * 
     * @param itemSaveDTO 模板指标
     * @return 结果
     */
    public int updatePerfTemplateItem(PerfTemplateItemSaveDTO itemSaveDTO);

    /**
     * 批量删除模板指标
     * 
     * @param itemIds 需要删除的模板指标主键集合
     * @return 结果
     */
    public int deletePerfTemplateItemByItemIds(Long[] itemIds);

    /**
     * 删除模板指标信息
     * 
     * @param itemId 模板指标主键
     * @return 结果
     */
    public int deletePerfTemplateItemByItemId(Long itemId);

    /**
     * 选择列表
     * @return
     */
    public List<CommonChooseVO> getCommonChooseList();

    /**
     * 复制模板指标
     * @param id
     * @return
     */
    public int copyTemplateItem(Long id);
}

package com.ruoyi.perf.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.perf.domain.PerfTemplate;
import com.ruoyi.perf.domain.dto.PerfTemplateItemSaveDTO;
import com.ruoyi.perf.domain.vo.CommonChooseVO;
import com.ruoyi.perf.domain.vo.PerfTemplateItemVO;
import com.ruoyi.perf.mapper.PerfTemplateMapper;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private PerfTemplateMapper perfTemplateMapper;

    /**
     * 查询模板指标
     * 
     * @param itemId 模板指标主键
     * @return 模板指标
     */
    @Override
    public PerfTemplateItemVO selectPerfTemplateItemByItemId(Long itemId)
    {
        PerfTemplateItem templateItem = perfTemplateItemMapper.selectPerfTemplateItemByItemId(itemId);
        if(templateItem == null){
            return null;
        }
        PerfTemplateItemVO itemVO = new PerfTemplateItemVO();
        BeanUtils.copyProperties(templateItem, itemVO);
        if(!StringUtils.isEmpty(templateItem.getScoreUserIds())){
            List<Long> scoreUserIds = Arrays.stream(templateItem.getScoreUserIds().split(","))
                    .map(String::trim) // 去除前后空格
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            itemVO.setScoreUserIds(scoreUserIds);
        }
        return itemVO;
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
     * @param itemSaveDTO 模板指标
     * @return 结果
     */
    @Override
    public int insertPerfTemplateItem(PerfTemplateItemSaveDTO itemSaveDTO)
    {
        PerfTemplate template = perfTemplateMapper.selectById(itemSaveDTO.getTemplateId());
        if(template == null){
            throw new RuntimeException("模板不存在");
        }
        PerfTemplateItem item = new PerfTemplateItem();
        BeanUtils.copyProperties(itemSaveDTO, item);
        item.setCreateTime(DateUtils.getNowDate());
        item.setCreateBy(SecurityUtils.getUserId().toString());
        item.setDefaultScoreId(template.getDefaultScoreId());
        if(!itemSaveDTO.getScoreUserIds().isEmpty()){
            item.setScoreUserIds(StringUtils.join(itemSaveDTO.getScoreUserIds(), ","));
        }

        return this.save(item)? 1 : 0;
    }

    /**
     * 修改模板指标
     * 
     * @param itemSaveDTO 模板指标
     * @return 结果
     */
    @Override
    public int updatePerfTemplateItem(PerfTemplateItemSaveDTO itemSaveDTO)
    {
        if(itemSaveDTO.getId() == null){
            throw new RuntimeException("指标id不能为空");
        }
        PerfTemplateItem templateItem = this.getById(itemSaveDTO.getId());
        if(templateItem == null){
            throw new RuntimeException("指标不存在");
        }
        PerfTemplate template = perfTemplateMapper.selectById(itemSaveDTO.getTemplateId());
        if(template == null){
            throw new RuntimeException("模板不存在");
        }
        PerfTemplateItem item = new PerfTemplateItem();
        BeanUtils.copyProperties(itemSaveDTO, item);
        item.setUpdateTime(DateUtils.getNowDate());
        item.setUpdateBy(SecurityUtils.getUserId().toString());
        item.setId(templateItem.getId());
        item.setDefaultScoreId(template.getDefaultScoreId());
        if(item.getParentId() == null){
            item.setParentId(0l);
        }
        if(itemSaveDTO.getScoreUserIds() == null || !itemSaveDTO.getScoreUserIds().isEmpty()){
            item.setScoreUserIds(StringUtils.join(itemSaveDTO.getScoreUserIds(), ","));
        }
        return this.updateById(item)? 1 : 0;
    }

    private void verificationPerfTemplate(PerfTemplateItemSaveDTO itemSaveDTO){
        if(StringUtils.isEmpty(itemSaveDTO.getItemName())){
            throw new RuntimeException("请填写指标名称");
        }
        if(StringUtils.isEmpty(itemSaveDTO.getItemType())){
            throw new RuntimeException("请选择指标类型");
        }
        if(itemSaveDTO.getWeight() == null){
            throw new RuntimeException("请填写权重");
        }
        if(itemSaveDTO.getMaxScore() == null){
            throw new RuntimeException("请填写权重");
        }
        if(itemSaveDTO.getMinScore() == null){
            throw new RuntimeException("请填写权重");
        }
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

    @Override
    public List<CommonChooseVO> getCommonChooseList() {
        List<PerfTemplateItem> list = this.list(Wrappers.lambdaQuery(PerfTemplateItem.class).eq(PerfTemplateItem::getParentId, 0));
        if (!list.isEmpty()) {
            return list.stream().map(item -> {
                CommonChooseVO vo = new CommonChooseVO();
                vo.setLabel(item.getItemName());
                vo.setValue(item.getId());
                return vo;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public int copyTemplateItem(Long id) {
        PerfTemplateItem templateItem = this.getById(id);
        if (templateItem ==  null){
            throw new RuntimeException("未找到该指标");
        }
        PerfTemplateItem item = new PerfTemplateItem();
        BeanUtils.copyProperties(templateItem, item);
        item.setId(null);
        item.setUpdateBy(null);
        item.setUpdateTime(null);
        item.setCreateTime(DateUtils.getNowDate());
        item.setCreateBy(SecurityUtils.getUserId().toString());
        return this.save(item)? 1 : 0;
    }
}

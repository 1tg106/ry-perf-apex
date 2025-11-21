package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysTemplateMapper;
import com.ruoyi.system.domain.SysTemplate;
import com.ruoyi.system.service.ISysTemplateService;

/**
 * 绩效模板Service业务层处理
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
@Service
public class SysTemplateServiceImpl implements ISysTemplateService 
{
    @Autowired
    private SysTemplateMapper sysTemplateMapper;

    /**
     * 查询绩效模板
     * 
     * @param templateId 绩效模板主键
     * @return 绩效模板
     */
    @Override
    public SysTemplate selectSysTemplateByTemplateId(Long templateId)
    {
        return sysTemplateMapper.selectSysTemplateByTemplateId(templateId);
    }

    /**
     * 查询绩效模板列表
     * 
     * @param sysTemplate 绩效模板
     * @return 绩效模板
     */
    @Override
    public List<SysTemplate> selectSysTemplateList(SysTemplate sysTemplate)
    {
        return sysTemplateMapper.selectSysTemplateList(sysTemplate);
    }

    /**
     * 新增绩效模板
     * 
     * @param sysTemplate 绩效模板
     * @return 结果
     */
    @Override
    public int insertSysTemplate(SysTemplate sysTemplate)
    {
        sysTemplate.setCreateTime(DateUtils.getNowDate());
        return sysTemplateMapper.insertSysTemplate(sysTemplate);
    }

    /**
     * 修改绩效模板
     * 
     * @param sysTemplate 绩效模板
     * @return 结果
     */
    @Override
    public int updateSysTemplate(SysTemplate sysTemplate)
    {
        sysTemplate.setUpdateTime(DateUtils.getNowDate());
        return sysTemplateMapper.updateSysTemplate(sysTemplate);
    }

    /**
     * 批量删除绩效模板
     * 
     * @param templateIds 需要删除的绩效模板主键
     * @return 结果
     */
    @Override
    public int deleteSysTemplateByTemplateIds(Long[] templateIds)
    {
        return sysTemplateMapper.deleteSysTemplateByTemplateIds(templateIds);
    }

    /**
     * 删除绩效模板信息
     * 
     * @param templateId 绩效模板主键
     * @return 结果
     */
    @Override
    public int deleteSysTemplateByTemplateId(Long templateId)
    {
        return sysTemplateMapper.deleteSysTemplateByTemplateId(templateId);
    }
}

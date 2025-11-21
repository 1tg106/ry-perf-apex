package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysTemplate;

/**
 * 绩效模板Mapper接口
 * 
 * @author 柒喜
 * @date 2025-11-21
 */
public interface SysTemplateMapper 
{
    /**
     * 查询绩效模板
     * 
     * @param templateId 绩效模板主键
     * @return 绩效模板
     */
    public SysTemplate selectSysTemplateByTemplateId(Long templateId);

    /**
     * 查询绩效模板列表
     * 
     * @param sysTemplate 绩效模板
     * @return 绩效模板集合
     */
    public List<SysTemplate> selectSysTemplateList(SysTemplate sysTemplate);

    /**
     * 新增绩效模板
     * 
     * @param sysTemplate 绩效模板
     * @return 结果
     */
    public int insertSysTemplate(SysTemplate sysTemplate);

    /**
     * 修改绩效模板
     * 
     * @param sysTemplate 绩效模板
     * @return 结果
     */
    public int updateSysTemplate(SysTemplate sysTemplate);

    /**
     * 删除绩效模板
     * 
     * @param templateId 绩效模板主键
     * @return 结果
     */
    public int deleteSysTemplateByTemplateId(Long templateId);

    /**
     * 批量删除绩效模板
     * 
     * @param templateIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysTemplateByTemplateIds(Long[] templateIds);
}

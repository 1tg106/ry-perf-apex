package com.ruoyi.perf.service;

import java.util.List;
import com.ruoyi.perf.domain.PerfTemplate;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 绩效模板Service接口
 * 
 * @author liudahua
 * @date 2025-11-25
 */
public interface IPerfTemplateService extends IService<PerfTemplate>

{
    /**
     * 查询绩效模板
     * 
     * @param templateId 绩效模板主键
     * @return 绩效模板
     */
    public PerfTemplate selectPerfTemplateByTemplateId(Long templateId);

    /**
     * 查询绩效模板列表
     * 
     * @param perfTemplate 绩效模板
     * @return 绩效模板集合
     */
    public List<PerfTemplate> selectPerfTemplateList(PerfTemplate perfTemplate);

    /**
     * 新增绩效模板
     * 
     * @param perfTemplate 绩效模板
     * @return 结果
     */
    public int insertPerfTemplate(PerfTemplate perfTemplate);

    /**
     * 修改绩效模板
     * 
     * @param perfTemplate 绩效模板
     * @return 结果
     */
    public int updatePerfTemplate(PerfTemplate perfTemplate);

    /**
     * 批量删除绩效模板
     * 
     * @param templateIds 需要删除的绩效模板主键集合
     * @return 结果
     */
    public int deletePerfTemplateByTemplateIds(Long[] templateIds);

    /**
     * 删除绩效模板信息
     * 
     * @param templateId 绩效模板主键
     * @return 结果
     */
    public int deletePerfTemplateByTemplateId(Long templateId);
}

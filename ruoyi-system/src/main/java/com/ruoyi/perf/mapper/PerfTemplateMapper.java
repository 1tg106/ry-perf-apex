package com.ruoyi.perf.mapper;

import java.util.List;
import com.ruoyi.perf.domain.PerfTemplate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 绩效模板Mapper接口
 * 
 * @author liudahua
 * @date 2025-11-25
 */
public interface PerfTemplateMapper extends BaseMapper<PerfTemplate>
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
     * 删除绩效模板
     * 
     * @param templateId 绩效模板主键
     * @return 结果
     */
    public int deletePerfTemplateByTemplateId(Long templateId);

    /**
     * 批量删除绩效模板
     * 
     * @param templateIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePerfTemplateByTemplateIds(Long[] templateIds);
}

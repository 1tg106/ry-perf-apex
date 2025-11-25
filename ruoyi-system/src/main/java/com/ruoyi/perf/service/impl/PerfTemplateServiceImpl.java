package com.ruoyi.perf.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.perf.mapper.PerfTemplateMapper;
import com.ruoyi.perf.domain.PerfTemplate;
import com.ruoyi.perf.service.IPerfTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 绩效模板Service业务层处理
 * 
 * @author liudahua
 * @date 2025-11-25
 */
@Service
public class PerfTemplateServiceImpl extends ServiceImpl<PerfTemplateMapper,PerfTemplate> implements IPerfTemplateService
{
    @Autowired
    private PerfTemplateMapper perfTemplateMapper;

    /**
     * 查询绩效模板
     * 
     * @param templateId 绩效模板主键
     * @return 绩效模板
     */
    @Override
    public PerfTemplate selectPerfTemplateByTemplateId(Long templateId)
    {
        return perfTemplateMapper.selectPerfTemplateByTemplateId(templateId);
    }

    /**
     * 查询绩效模板列表
     * 
     * @param perfTemplate 绩效模板
     * @return 绩效模板
     */
    @Override
    public List<PerfTemplate> selectPerfTemplateList(PerfTemplate perfTemplate)
    {
        return perfTemplateMapper.selectPerfTemplateList(perfTemplate);
    }

    /**
     * 新增绩效模板
     * 
     * @param perfTemplate 绩效模板
     * @return 结果
     */
    @Override
    public int insertPerfTemplate(PerfTemplate perfTemplate)
    {
        perfTemplate.setCreateTime(DateUtils.getNowDate());
        return perfTemplateMapper.insertPerfTemplate(perfTemplate);
    }

    /**
     * 修改绩效模板
     * 
     * @param perfTemplate 绩效模板
     * @return 结果
     */
    @Override
    public int updatePerfTemplate(PerfTemplate perfTemplate)
    {
        perfTemplate.setUpdateTime(DateUtils.getNowDate());
        return perfTemplateMapper.updatePerfTemplate(perfTemplate);
    }

    /**
     * 批量删除绩效模板
     * 
     * @param templateIds 需要删除的绩效模板主键
     * @return 结果
     */
    @Override
    public int deletePerfTemplateByTemplateIds(Long[] templateIds)
    {
        return perfTemplateMapper.deletePerfTemplateByTemplateIds(templateIds);
    }

    /**
     * 删除绩效模板信息
     * 
     * @param templateId 绩效模板主键
     * @return 结果
     */
    @Override
    public int deletePerfTemplateByTemplateId(Long templateId)
    {
        return perfTemplateMapper.deletePerfTemplateByTemplateId(templateId);
    }
}

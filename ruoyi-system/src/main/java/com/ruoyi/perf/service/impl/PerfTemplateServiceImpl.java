package com.ruoyi.perf.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.perf.dto.PerfTemplateSaveDTO;
import com.ruoyi.perf.vo.PerfTemplateVO;
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
    public PerfTemplateVO selectPerfTemplateByTemplateId(Long templateId)
    {
        PerfTemplate perfTemplate = this.getById(templateId);
        if(perfTemplate == null){
            return null;
        }
        PerfTemplateVO perfTemplateVO = new PerfTemplateVO();
        BeanUtils.copyProperties(perfTemplate,perfTemplateVO);

        if (StringUtils.isNotEmpty(perfTemplate.getPostIds())) {
            List<Integer> postIds = Arrays.stream(perfTemplate.getPostIds().split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
            perfTemplateVO.setPostIds(postIds);
        } else {
            perfTemplateVO.setPostIds(new ArrayList<>());
        }
        return perfTemplateVO;
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
    public int insertPerfTemplate(PerfTemplateSaveDTO perfTemplate)
    {
        if(StringUtils.isEmpty(perfTemplate.getTemplateName())){
            throw new RuntimeException("模板名称不能为空");
        }
        if(StringUtils.isEmpty(perfTemplate.getTemplateType())){
            throw new RuntimeException("模板类型不能为空");
        }
        if(perfTemplate.getDeptId() == null){
            throw new RuntimeException("适用部门不能为空");
        }
        if(perfTemplate.getPostIds().isEmpty()){
            throw new RuntimeException("适用岗位不能为空");
        }
        PerfTemplate template = new PerfTemplate();
        BeanUtils.copyProperties(perfTemplate,template);
        template.setCreateTime(DateUtils.getNowDate());
        template.setPostIds(StringUtils.join(perfTemplate.getPostIds(), ","));
        template.setCreateBy(SecurityUtils.getUserId().toString());
        return this.save(template) ? 1 : 0;
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

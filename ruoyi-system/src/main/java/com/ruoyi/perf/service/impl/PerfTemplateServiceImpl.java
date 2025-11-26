package com.ruoyi.perf.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.perf.domain.PerfTemplate;
import com.ruoyi.perf.domain.dto.PerfTemplateSaveDTO;
import com.ruoyi.perf.mapper.PerfTemplateMapper;
import com.ruoyi.perf.service.IPerfTemplateService;
import com.ruoyi.perf.domain.vo.CommonChooseVO;
import com.ruoyi.perf.domain.vo.PerfTemplateVO;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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

    @Resource
    private ISysDeptService sysDeptService;

    @Resource
    private ISysPostService sysPostService;

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

        if(perfTemplate.getDeptId() == null){
            SysDept sysDept = sysDeptService.selectDeptById(perfTemplate.getDeptId());
            perfTemplateVO.setDeptName(sysDept.getDeptName());
        }

        if (StringUtils.isNotEmpty(perfTemplate.getPostIds())) {
            List<Long> postIds = Arrays.stream(perfTemplate.getPostIds().split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
            perfTemplateVO.setPostIds(postIds);
            List<String> strings = sysPostService.selectPostNamByIds(postIds);
            perfTemplateVO.setPostNames(strings.isEmpty()? new ArrayList<>():strings);
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
    public List<PerfTemplateVO> selectPerfTemplateList(PerfTemplate perfTemplate)
    {
        List<PerfTemplate> list = perfTemplateMapper.selectPerfTemplateList(perfTemplate);
        List<Long> deptIds = list.stream().map(PerfTemplate::getDeptId).collect(Collectors.toList());
        List<SysDept> deptList = sysDeptService.selectDeptListByIds(deptIds);

        // 收集所有岗位ID并一次性查询
        Set<Long> allPostIds = list.stream()
                .filter(t -> StringUtils.isNotEmpty(t.getPostIds()))
                .flatMap(t -> Arrays.stream(t.getPostIds().split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(Long::valueOf))
                .collect(Collectors.toSet());

        // 获取所有相关岗位信息
        List<SysPost> allPosts = sysPostService.selectPostListByIds(new ArrayList<>(allPostIds));
        Map<Long, String> postMap = allPosts.stream()
                .collect(Collectors.toMap(SysPost::getPostId, SysPost::getPostName));

        List<PerfTemplateVO> perfTemplateVOS = new ArrayList<>();

        for (PerfTemplate template : list){
            PerfTemplateVO perfTemplateVO = new PerfTemplateVO();
            BeanUtils.copyProperties(template,perfTemplateVO);
            if(template.getDeptId() != null) {
                SysDept sysDept = deptList.stream().filter(dept -> dept.getDeptId().equals(template.getDeptId())).findFirst().orElse(null);
                perfTemplateVO.setDeptName(sysDept.getDeptName());
            }

            // 设置岗位名称
            if (StringUtils.isNotEmpty(template.getPostIds())) {
                List<Long> postIds = Arrays.stream(template.getPostIds().split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(Long::valueOf)
                        .collect(Collectors.toList());

                // 从已查询的映射中获取岗位名称
                List<String> postNames = postIds.stream()
                        .map(postMap::get)
                        .filter(java.util.Objects::nonNull)
                        .collect(Collectors.toList());
                perfTemplateVO.setPostNames(postNames);
            }

            perfTemplateVOS.add(perfTemplateVO);
        }


        return perfTemplateVOS;
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
        verificationPerfTemplate(perfTemplate);
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
    public int updatePerfTemplate(PerfTemplateSaveDTO perfTemplate)
    {
        verificationPerfTemplate(perfTemplate);
        if(perfTemplate.getId() == null){
            throw new RuntimeException("模板ID不能为空");
        }
        PerfTemplate template = this.getById(perfTemplate.getId());
        template.setTemplateName(perfTemplate.getTemplateName());
        template.setTemplateType(perfTemplate.getTemplateType());
        template.setDeptId(perfTemplate.getDeptId());
        template.setPostIds(StringUtils.join(perfTemplate.getPostIds(), ","));
        template.setStatus(perfTemplate.getStatus());
        template.setUpdateTime(DateUtils.getNowDate());
        template.setUpdateBy(SecurityUtils.getUserId().toString());
        return this.updateById(template)? 1 : 0;
    }

    public void verificationPerfTemplate(PerfTemplateSaveDTO perfTemplate){
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
        return perfTemplateMapper.deleteBatchIds(Arrays.asList(templateIds));
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

    @Override
    public List<CommonChooseVO> getCommonChooseList() {
        List<PerfTemplate> list = this.list(Wrappers.lambdaQuery(PerfTemplate.class).eq(PerfTemplate::getStatus, 0));
        if(!list.isEmpty()){
            return list.stream().map(item -> {
                CommonChooseVO vo = new CommonChooseVO();
                vo.setLabel(item.getTemplateName());
                vo.setValue(item.getId());
                return vo;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}

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
import org.springframework.util.CollectionUtils;

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

        // 获取部门名称
        if(perfTemplate.getDeptId() == null){
            SysDept sysDept = sysDeptService.selectDeptById(perfTemplate.getDeptId());
            perfTemplateVO.setDeptName(sysDept.getDeptName());
        }

        // 岗位名称
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
        return convertToVO(list);
    }

    @Override
    public Long selectPerfTemplateCount(PerfTemplate perfTemplate) {
        return perfTemplateMapper.selectPerfTemplateCount(perfTemplate);
    }

    /**
     * 通用的VO转换方法
     */
    private List<PerfTemplateVO> convertToVO(List<PerfTemplate> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }

        // 批量查询部门信息
        List<Long> deptIds = list.stream()
                .map(PerfTemplate::getDeptId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        List<SysDept> deptList = CollectionUtils.isEmpty(deptIds) ?
                new ArrayList<>() :
                sysDeptService.selectDeptListByIds(deptIds);

        Map<Long, String> deptMap = deptList.stream()
                .collect(Collectors.toMap(SysDept::getDeptId, SysDept::getDeptName));

        // 批量查询岗位信息
        Set<Long> allPostIds = list.stream()
                .filter(t -> StringUtils.isNotEmpty(t.getPostIds()))
                .flatMap(t -> Arrays.stream(t.getPostIds().split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(Long::valueOf))
                .collect(Collectors.toSet());

        List<SysPost> allPosts = CollectionUtils.isEmpty(allPostIds) ?
                new ArrayList<>() :
                sysPostService.selectPostListByIds(new ArrayList<>(allPostIds));

        Map<Long, String> postMap = allPosts.stream()
                .collect(Collectors.toMap(SysPost::getPostId, SysPost::getPostName));

        // 转换为VO
        return list.stream().map(template -> {
            PerfTemplateVO vo = new PerfTemplateVO();
            BeanUtils.copyProperties(template, vo);

            // 设置部门名称
            if (template.getDeptId() != null) {
                vo.setDeptName(deptMap.get(template.getDeptId()));
            }

            // 设置岗位名称
            if (StringUtils.isNotEmpty(template.getPostIds())) {
                List<String> postNames = Arrays.stream(template.getPostIds().split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(Long::valueOf)
                        .map(postMap::get)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                vo.setPostNames(postNames);
            }

            return vo;
        }).collect(Collectors.toList());
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
        template.setDefaultScoreId(perfTemplate.getDefaultScoreId());
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
        if(perfTemplate.getDeptId() == null){
            throw new RuntimeException("适用部门不能为空");
        }
        if(perfTemplate.getPostIds().isEmpty()){
            throw new RuntimeException("适用岗位不能为空");
        }
        if(perfTemplate.getDefaultScoreId() == null){
            throw new RuntimeException("默认评分人不能为空");
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

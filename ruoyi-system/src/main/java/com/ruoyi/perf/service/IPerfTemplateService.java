package com.ruoyi.perf.service;

import java.util.List;
import com.ruoyi.perf.domain.PerfTemplate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.perf.domain.dto.PerfTemplateSaveDTO;
import com.ruoyi.perf.domain.vo.CommonChooseVO;
import com.ruoyi.perf.domain.vo.PerfTemplateVO;

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
    public PerfTemplateVO selectPerfTemplateByTemplateId(Long templateId);

    /**
     * 查询绩效模板列表
     * 
     * @param perfTemplate 绩效模板
     * @return 绩效模板集合
     */
    public List<PerfTemplateVO> selectPerfTemplateList(PerfTemplate perfTemplate);

    /**
     * 查询绩效模板总数
     *
     * @param perfTemplate 绩效模板
     * @return 绩效模板集合
     */
    public Long selectPerfTemplateCount(PerfTemplate perfTemplate);

    /**
     * 新增绩效模板
     * 
     * @param perfTemplate 绩效模板
     * @return 结果
     */
    public int insertPerfTemplate(PerfTemplateSaveDTO perfTemplate);

    /**
     * 修改绩效模板
     * 
     * @param perfTemplate 绩效模板
     * @return 结果
     */
    public int updatePerfTemplate(PerfTemplateSaveDTO perfTemplate);

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

    /**
     * 选择列表
     * @return
     */
    public List<CommonChooseVO> getCommonChooseList();
}

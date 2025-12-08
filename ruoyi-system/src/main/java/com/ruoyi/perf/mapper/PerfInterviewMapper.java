package com.ruoyi.perf.mapper;

import java.util.List;
import com.ruoyi.perf.domain.PerfInterview;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.perf.domain.vo.PerfInterviewVO;

/**
 * 绩效面谈Mapper接口
 * 
 * @author liudahua
 * @date 2025-11-26
 */
public interface PerfInterviewMapper extends BaseMapper<PerfInterview>
{
    /**
     * 查询绩效面谈
     * 
     * @param id 绩效面谈主键
     * @return 绩效面谈
     */
    public PerfInterview selectPerfInterviewById(Long id);

    /**
     * 查询绩效面谈列表
     * 
     * @param perfInterview 绩效面谈
     * @return 绩效面谈集合
     */
    public List<PerfInterview> selectPerfInterviewList(PerfInterview perfInterview);

    /**
     * 关联查询绩效面谈列表
     *
     * @param perfInterview 绩效面谈
     * @return 绩效面谈集合
     */
    public List<PerfInterviewVO> selectPerfInterviewListVO(PerfInterview perfInterview);

    /**
     * 关联查询绩效面谈详情
     *
     * @param id 绩效面谈主键
     * @return 绩效面谈
     */
    public PerfInterviewVO selectPerfInterviewVOById(Long id);

    /**
     * 新增绩效面谈
     * 
     * @param perfInterview 绩效面谈
     * @return 结果
     */
    public int insertPerfInterview(PerfInterview perfInterview);

    /**
     * 修改绩效面谈
     * 
     * @param perfInterview 绩效面谈
     * @return 结果
     */
    public int updatePerfInterview(PerfInterview perfInterview);

    /**
     * 删除绩效面谈
     * 
     * @param id 绩效面谈主键
     * @return 结果
     */
    public int deletePerfInterviewById(Long id);

    /**
     * 批量删除绩效面谈
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePerfInterviewByIds(Long[] ids);
}

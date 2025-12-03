package com.ruoyi.perf.mapper;

import java.util.List;
import com.ruoyi.perf.domain.PerfPerformanceContentScore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.perf.domain.dto.PerfScoreDTO;
import com.ruoyi.perf.domain.vo.PerfScoreItemVO;
import org.apache.ibatis.annotations.Param;

/**
 * 绩效指标评分Mapper接口
 * 
 * @author liudahua
 * @date 2025-12-02
 */
public interface PerfPerformanceContentScoreMapper extends BaseMapper<PerfPerformanceContentScore>
{
    /**
     * 查询绩效指标评分
     * 
     * @param id 绩效指标评分主键
     * @return 绩效指标评分
     */
    public PerfPerformanceContentScore selectPerfPerformanceContentScoreById(Long id);

    /**
     * 查询绩效指标评分列表
     * 
     * @param perfPerformanceContentScore 绩效指标评分
     * @return 绩效指标评分集合
     */
    public List<PerfPerformanceContentScore> selectPerfPerformanceContentScoreList(PerfPerformanceContentScore perfPerformanceContentScore);

    /**
     * 新增绩效指标评分
     * 
     * @param perfPerformanceContentScore 绩效指标评分
     * @return 结果
     */
    public int insertPerfPerformanceContentScore(PerfPerformanceContentScore perfPerformanceContentScore);

    /**
     * 修改绩效指标评分
     * 
     * @param perfPerformanceContentScore 绩效指标评分
     * @return 结果
     */
    public int updatePerfPerformanceContentScore(PerfPerformanceContentScore perfPerformanceContentScore);

    /**
     * 删除绩效指标评分
     * 
     * @param id 绩效指标评分主键
     * @return 结果
     */
    public int deletePerfPerformanceContentScoreById(Long id);

    /**
     * 批量删除绩效指标评分
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePerfPerformanceContentScoreByIds(Long[] ids);

    /**
     * 获取待评分信息
     * @param userId
     * @return
     */
    public List<PerfPerformanceContentScore> getWaitScoreUserList(@Param("userId") Long userId,
                                                                  @Param("pageDTO") PerfScoreDTO pageDTO);

    /**
     * 获取待评分列表
     * @param userId
     * @return
     */
    public List<PerfScoreItemVO> getPerformanceScoreDetailByPerformanceId(@Param("performanceId") Long performanceId, @Param("userId") Long userId);
}

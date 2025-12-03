import request from '@/utils/request'

// 查询绩效指标评分列表
export function listScore(query) {
  return request({
    url: '/perf/score/list',
    method: 'get',
    params: query
  })
}

// 查询绩效指标评分详细
export function getScore(performanceId) {
  return request({
    url: '/perf/score/' + performanceId,
    method: 'get'
  })
}

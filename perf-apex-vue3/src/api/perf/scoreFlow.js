import request from '@/utils/request'

// 查询评分流程列表
export function listScoreFlow(query) {
  return request({
    url: '/perf/scoreFlow/list',
    method: 'get',
    params: query
  })
}

// 查询评分流程详细
export function getScoreFlow(id) {
  return request({
    url: '/perf/scoreFlow/' + id,
    method: 'get'
  })
}

// 新增评分流程
export function addScoreFlow(data) {
  return request({
    url: '/perf/scoreFlow',
    method: 'post',
    data: data
  })
}

// 修改评分流程
export function updateScoreFlow(data) {
  return request({
    url: '/perf/scoreFlow',
    method: 'put',
    data: data
  })
}

// 删除评分流程
export function delScoreFlow(id) {
  return request({
    url: '/perf/scoreFlow/' + id,
    method: 'delete'
  })
}

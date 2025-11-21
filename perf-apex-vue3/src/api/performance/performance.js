import request from '@/utils/request'

// 查询绩效实例列表
export function listPerformance(query) {
  return request({
    url: '/system/performance/list',
    method: 'get',
    params: query
  })
}

// 查询绩效实例详细
export function getPerformance(performanceId) {
  return request({
    url: '/system/performance/' + performanceId,
    method: 'get'
  })
}

// 新增绩效实例
export function addPerformance(data) {
  return request({
    url: '/system/performance',
    method: 'post',
    data: data
  })
}

// 修改绩效实例
export function updatePerformance(data) {
  return request({
    url: '/system/performance',
    method: 'put',
    data: data
  })
}

// 删除绩效实例
export function delPerformance(performanceId) {
  return request({
    url: '/system/performance/' + performanceId,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询绩效实例列表
export function listPerformance(query) {
  return request({
    url: '/perf/performance/list',
    method: 'get',
    params: query
  })
}


// 查询绩效实例详细
export function getPerformance(id) {
  return request({
    url: '/perf/performance/' + id,
    method: 'get'
  })
}

// 新增绩效实例
export function addPerformance(data) {
  return request({
    url: '/perf/performance',
    method: 'post',
    data: data
  })
}

// 修改绩效实例
export function updatePerformance(data) {
  return request({
    url: '/perf/performance',
    method: 'put',
    data: data
  })
}

// 删除绩效实例
export function delPerformance(id) {
  return request({
    url: '/perf/performance/' + id,
    method: 'delete'
  })
}

// =======================我的绩效=======================

// 查询当前用户绩效列表
export function listMyPerformance(query) {
  return request({
    url: '/perf/performance/myList',
    method: 'get',
    params: query
  })
}

// 新增绩效实例
export function addMyPerformance(data) {
  return request({
    url: '/perf/performance/addMyPerformance',
    method: 'post',
    data: data
  })
}

// 修改绩效实例
export function editMyPerformance(data) {
  return request({
    url: '/perf/performance/editMyPerformance',
    method: 'put',
    data: data
  })
}

// =======================绩效审核=======================

// 绩效审核
export function auditPerformance(data) {
  return request({
    url: '/perf/performance/auditPerformance',
    method: 'put',
    data: data
  })
}

// =======================根据用户ID获取绩效列表=======================

// 根据用户ID获取绩效列表
export function listPerformanceByUserId(userId, query) {
  return request({
    url: '/perf/performance/listByUserId/' + userId,
    method: 'get',
    params: query
  })
}
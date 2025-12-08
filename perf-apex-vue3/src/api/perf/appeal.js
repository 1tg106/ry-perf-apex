import request from '@/utils/request'

// 查询绩效申诉列表
export function listAppeal(query) {
  return request({
    url: '/perf/appeal/list',
    method: 'get',
    params: query
  })
}

// 关联查询绩效申诉列表
export function selectRelevancePerfAppealList(query) {
  return request({
    url: '/perf/appeal/selectRelevancePerfAppealList',
    method: 'get',
    params: query
  })
}

// 查询绩效申诉详细
export function getAppeal(id) {
  return request({
    url: '/perf/appeal/' + id,
    method: 'get'
  })
}

// 新增绩效申诉
export function addAppeal(data) {
  return request({
    url: '/perf/appeal',
    method: 'post',
    data: data
  })
}

// 修改绩效申诉
export function updateAppeal(data) {
  return request({
    url: '/perf/appeal',
    method: 'put',
    data: data
  })
}

// 绩效申诉处理
export function handlePerfAppeal(data) {
  return request({
    url: '/perf/appeal/handle',
    method: 'put',
    data: data
  })
}

// 删除绩效申诉
export function delAppeal(id) {
  return request({
    url: '/perf/appeal/' + id,
    method: 'delete'
  })
}
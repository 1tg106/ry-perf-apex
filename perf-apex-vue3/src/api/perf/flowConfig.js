import request from '@/utils/request'

// 查询绩效流程配置列表
export function listFlowConfig(query) {
  return request({
    url: '/perf/flowConfig/list',
    method: 'get',
    params: query
  })
}

// 查询绩效流程配置详细
export function getFlowConfig(id) {
  return request({
    url: '/perf/flowConfig/' + id,
    method: 'get'
  })
}

// 新增绩效流程配置
export function addFlowConfig(data) {
  return request({
    url: '/perf/flowConfig',
    method: 'post',
    data: data
  })
}

// 修改绩效流程配置
export function updateFlowConfig(data) {
  return request({
    url: '/perf/flowConfig',
    method: 'put',
    data: data
  })
}

// 删除绩效流程配置
export function delFlowConfig(id) {
  return request({
    url: '/perf/flowConfig/' + id,
    method: 'delete'
  })
}

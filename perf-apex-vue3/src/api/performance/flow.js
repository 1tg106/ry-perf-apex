import request from '@/utils/request'

// 查询评分流程列表
export function listFlow(query) {
  return request({
    url: '/system/flow/list',
    method: 'get',
    params: query
  })
}

// 查询评分流程详细
export function getFlow(flowId) {
  return request({
    url: '/system/flow/' + flowId,
    method: 'get'
  })
}

// 新增评分流程
export function addFlow(data) {
  return request({
    url: '/system/flow',
    method: 'post',
    data: data
  })
}

// 修改评分流程
export function updateFlow(data) {
  return request({
    url: '/system/flow',
    method: 'put',
    data: data
  })
}

// 删除评分流程
export function delFlow(flowId) {
  return request({
    url: '/system/flow/' + flowId,
    method: 'delete'
  })
}

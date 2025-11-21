import request from '@/utils/request'

// 查询绩效申诉列表
export function listAppeal(query) {
  return request({
    url: '/system/appeal/list',
    method: 'get',
    params: query
  })
}

// 查询绩效申诉详细
export function getAppeal(appealId) {
  return request({
    url: '/system/appeal/' + appealId,
    method: 'get'
  })
}

// 新增绩效申诉
export function addAppeal(data) {
  return request({
    url: '/system/appeal',
    method: 'post',
    data: data
  })
}

// 修改绩效申诉
export function updateAppeal(data) {
  return request({
    url: '/system/appeal',
    method: 'put',
    data: data
  })
}

// 删除绩效申诉
export function delAppeal(appealId) {
  return request({
    url: '/system/appeal/' + appealId,
    method: 'delete'
  })
}

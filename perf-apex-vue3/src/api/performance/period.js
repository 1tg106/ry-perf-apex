import request from '@/utils/request'

// 查询绩效周期列表
export function listPeriod(query) {
  return request({
    url: '/system/period/list',
    method: 'get',
    params: query
  })
}

// 查询绩效周期详细
export function getPeriod(periodId) {
  return request({
    url: '/system/period/' + periodId,
    method: 'get'
  })
}

// 新增绩效周期
export function addPeriod(data) {
  return request({
    url: '/system/period',
    method: 'post',
    data: data
  })
}

// 修改绩效周期
export function updatePeriod(data) {
  return request({
    url: '/system/period',
    method: 'put',
    data: data
  })
}

// 删除绩效周期
export function delPeriod(periodId) {
  return request({
    url: '/system/period/' + periodId,
    method: 'delete'
  })
}

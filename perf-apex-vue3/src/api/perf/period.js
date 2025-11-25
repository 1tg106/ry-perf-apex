import request from '@/utils/request'

// 查询绩效周期列表
export function listPeriod(query) {
  return request({
    url: '/perf/period/list',
    method: 'get',
    params: query
  })
}

// 查询绩效周期详细
export function getPeriod(periodId) {
  return request({
    url: '/perf/period/' + periodId,
    method: 'get'
  })
}

// 新增绩效周期
export function addPeriod(data) {
  return request({
    url: '/perf/period',
    method: 'post',
    data: data
  })
}

// 修改绩效周期
export function updatePeriod(data) {
  return request({
    url: '/perf/period',
    method: 'put',
    data: data
  })
}

// 删除绩效周期
export function delPeriod(periodId) {
  return request({
    url: '/perf/period/' + periodId,
    method: 'delete'
  })
}

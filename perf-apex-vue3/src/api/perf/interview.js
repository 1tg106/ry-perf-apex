import request from '@/utils/request'

// 查询绩效面谈列表
export function listInterview(query) {
  return request({
    url: '/perf/interview/list',
    method: 'get',
    params: query
  })
}

// 查询绩效面谈详细
export function getInterview(id) {
  return request({
    url: '/perf/interview/' + id,
    method: 'get'
  })
}

// 新增绩效面谈
export function addInterview(data) {
  return request({
    url: '/perf/interview',
    method: 'post',
    data: data
  })
}

// 修改绩效面谈
export function updateInterview(data) {
  return request({
    url: '/perf/interview',
    method: 'put',
    data: data
  })
}

// 删除绩效面谈
export function delInterview(id) {
  return request({
    url: '/perf/interview/' + id,
    method: 'delete'
  })
}

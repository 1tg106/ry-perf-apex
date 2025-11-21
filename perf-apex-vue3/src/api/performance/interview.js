import request from '@/utils/request'

// 查询绩效面谈列表
export function listInterview(query) {
  return request({
    url: '/system/interview/list',
    method: 'get',
    params: query
  })
}

// 查询绩效面谈详细
export function getInterview(interviewId) {
  return request({
    url: '/system/interview/' + interviewId,
    method: 'get'
  })
}

// 新增绩效面谈
export function addInterview(data) {
  return request({
    url: '/system/interview',
    method: 'post',
    data: data
  })
}

// 修改绩效面谈
export function updateInterview(data) {
  return request({
    url: '/system/interview',
    method: 'put',
    data: data
  })
}

// 删除绩效面谈
export function delInterview(interviewId) {
  return request({
    url: '/system/interview/' + interviewId,
    method: 'delete'
  })
}

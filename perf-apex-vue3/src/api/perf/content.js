import request from '@/utils/request'

// 查询绩效内容列表
export function listContent(query) {
  return request({
    url: '/perf/content/list',
    method: 'get',
    params: query
  })
}

// 查询绩效内容详细
export function getContent(id) {
  return request({
    url: '/perf/content/' + id,
    method: 'get'
  })
}

// 新增绩效内容
export function addContent(data) {
  return request({
    url: '/perf/content',
    method: 'post',
    data: data
  })
}

// 修改绩效内容
export function updateContent(data) {
  return request({
    url: '/perf/content',
    method: 'put',
    data: data
  })
}

// 删除绩效内容
export function delContent(id) {
  return request({
    url: '/perf/content/' + id,
    method: 'delete'
  })
}

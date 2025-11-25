import request from '@/utils/request'

// 查询模板指标列表
export function listTemplateItem(query) {
  return request({
    url: '/perf/templateItem/list',
    method: 'get',
    params: query
  })
}

// 查询模板指标详细
export function getTemplateItem(itemId) {
  return request({
    url: '/perf/templateItem/' + itemId,
    method: 'get'
  })
}

// 新增模板指标
export function addTemplateItem(data) {
  return request({
    url: '/perf/templateItem',
    method: 'post',
    data: data
  })
}

// 修改模板指标
export function updateTemplateItem(data) {
  return request({
    url: '/perf/templateItem',
    method: 'put',
    data: data
  })
}

// 删除模板指标
export function delTemplateItem(itemId) {
  return request({
    url: '/perf/templateItem/' + itemId,
    method: 'delete'
  })
}

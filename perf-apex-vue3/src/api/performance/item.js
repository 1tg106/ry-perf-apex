import request from '@/utils/request'

// 查询模板指标列表
export function listItem(query) {
  return request({
    url: '/system/item/list',
    method: 'get',
    params: query
  })
}

// 查询模板指标详细
export function getItem(itemId) {
  return request({
    url: '/system/item/' + itemId,
    method: 'get'
  })
}

// 新增模板指标
export function addItem(data) {
  return request({
    url: '/system/item',
    method: 'post',
    data: data
  })
}

// 修改模板指标
export function updateItem(data) {
  return request({
    url: '/system/item',
    method: 'put',
    data: data
  })
}

// 删除模板指标
export function delItem(itemId) {
  return request({
    url: '/system/item/' + itemId,
    method: 'delete'
  })
}

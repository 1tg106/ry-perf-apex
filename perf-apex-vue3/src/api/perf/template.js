import request from '@/utils/request'

// 查询绩效模板列表
export function listTemplate(query) {
  return request({
    url: '/perf/template/list',
    method: 'get',
    params: query
  })
}

// 查询绩效模板详细
export function getTemplate(templateId) {
  return request({
    url: '/perf/template/' + templateId,
    method: 'get'
  })
}

// 新增绩效模板
export function addTemplate(data) {
  return request({
    url: '/perf/template',
    method: 'post',
    data: data
  })
}

// 修改绩效模板
export function updateTemplate(data) {
  return request({
    url: '/perf/template',
    method: 'put',
    data: data
  })
}

// 删除绩效模板
export function delTemplate(templateId) {
  return request({
    url: '/perf/template/' + templateId,
    method: 'delete'
  })
}

// 获取模板选择列表
export function getTemplateChooseList() {
  return request({
    url: '/perf/template/getTemplateChooseList',
    method: 'get'
  })
}
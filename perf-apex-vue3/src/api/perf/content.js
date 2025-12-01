import request from '@/utils/request'

// 查询绩效内容列表
export function listContent(query) {
  return request({
    url: '/perf/content/list',
    method: 'get',
    params: query
  })
}

// 查询绩效内容列表（根据绩效ID）
export function getContentListByPerformanceId(performanceId) {
  return request({
    url: '/perf/content/getContentListByPerformanceId',
    method: 'get',
    params: { performanceId }
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

// 批量更新绩效内容
export function updateContentBatch(data) {
  return request({
    url: '/perf/content/batch',
    method: 'put',
    data: data
  })
}

// 保存草稿
export function saveDraft(performanceId, contentList) {
  const data = {
    performanceId: performanceId,
    contentList: contentList,
    performanceStatus: 'DRAFT'
  }
  return request({
    url: '/perf/content/batch',
    method: 'put',
    data: data
  })
}

// 提交绩效
export function submitPerformance(performanceId, contentList, performanceStatus, performanceStep) {
  const data = {
    performanceId: performanceId,
    contentList: contentList,
    performanceStatus: performanceStatus,
    performanceStep: performanceStep
  }
  return request({
    url: '/perf/content/batch',
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

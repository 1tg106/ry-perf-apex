import request from '@/utils/request'

// 获取统计卡片数据
export function getStatCards() {
  return request({
    url: '/perf/stat/statCards',
    method: 'get'
  })
}

// 获取绩效评分分布
export function getPerformanceScoreDistributeList() {
  return request({
    url: '/perf/stat/getPerformanceScoreDistributeList',
    method: 'get'
  })
}

// 查询根据用户id获取待评分列表
export function waitMyScorePerformanceList(query) {
  return request({
    url: '/perf/stat/waitMyScorePerformanceList',
    method: 'get',
    params: query
  })
}

// 查询绩效周期概览
export function getPerfPeriodList(query) {
  return request({
    url: '/perf/stat/getPerfPeriodList',
    method: 'get',
    params: query
  })
}

// 查询绩效申诉列表
export function getPerfAppealList(query) {
  return request({
    url: '/perf/stat/getPerfAppealList',
    method: 'get',
    params: query
  })
}

// 查询绩效面谈列表
export function getPerfInterviewList(query) {
  return request({
    url: '/perf/stat/getPerfInterviewList',
    method: 'get',
    params: query
  })
}
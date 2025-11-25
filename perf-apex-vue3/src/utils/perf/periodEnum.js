export const PERF_PERIOD_TYPE = {
  MONTH: 'MONTH', // 月度
  QUARTER: 'QUARTER', // 季度
  HALF_YEAR: 'HALF_YEAR', // 半年度
  YEAR: 'YEAR', // 年度
}

export const PERF_PERIOD_STATUS = {
  PREPARE: '0', // 准备中
  UNDERWAY: '1', // 进行中
  FINISHED: '2', // 已结束
}

/**
 * 列表
 */
export const PERF_PERIOD_TYPE_LIST = [
    {
        label: '月度',
        value: 'MONTH'
    },
    {
        label: '季度',
        value: 'QUARTER'
    },
    {
        label: '半年度',
        value: 'HALF_YEAR'
    },
    {
        label: '年度',
        value: 'YEAR'
    }
]

/**
 * 列表
 */
export const PERF_PERIOD_STATUS_LIST = [
    {
        label: '准备中',
        value: '0'
    },
    {
        label: '进行中',
        value: '1'
    },
    {
        label: '已结束',
        value: '2'
    }
]
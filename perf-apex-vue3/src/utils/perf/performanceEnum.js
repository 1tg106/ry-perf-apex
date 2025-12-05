/**
 * 绩效状态类型
 * @enum {string}
 */
export const PERFORMANCE_STATUS = {
  DRAFT: 'DRAFT', // 草稿
  PENDING_SUBMISSION: 'PENDING_SUBMISSION', // 待提交
  PENDING_SCORE: 'PENDING_SCORE', // 待评分
  PENDING_AUDIT: 'PENDING_AUDIT', // 待审核人确认
  CONFIRMED: 'CONFIRMED', // 已确认
  REJECTED: 'REJECTED', // 已驳回
  CANCELLATION: 'CANCELLATION', // 已作废
  APPEAL: 'APPEAL', // 申诉中
}

// 绩效状态类型列表
export const PERFORMANCE_STATUS_LIST = [
    {
        label: '草稿',
        value: 'DRAFT'
    },
    {
        label: '待提交',
        value: 'PENDING_SUBMISSION'
    },
    {
        label: '待评分',
        value: 'PENDING_SCORE'
    },
    {
        label: '待审核人确认',
        value: 'PENDING_AUDIT'
    },
    {
        label: '已确认',
        value: 'CONFIRMED'
    },
    {
        label: '已驳回',
        value: 'REJECTED'
    },
    {
        label: '已作废',
        value: 'CANCELLATION'
    },
    {
        label: '申诉中',
        value: 'APPEAL'
    }
]

/**
 * 绩效步骤状态
 * @enum {string}
 */
export const PERFORMANCE_STEP_STATUS = {
  DRAFT: 0, // 草稿状态 / 员工自评中
  PENDING_SUPERVISOR_SCORE: 1, // 待直属上级评分
  PENDING_DEPT_HEAD_SCORE: 2, // 待部门负责人评分
  PENDING_AUDIT_CONFIRMATION: 3, // 待审核人确认
  COMPLETED: 4 // 已完成所有步骤
}

export const PERFORMANCE_STEP_STATUS_LIST = [
  { label: "自评中", value: 0 },
  { label: "待评分", value: 1 },
  { label: "待审核人确认", value: 2 },
  { label: "申述中", value: 3 },
  { label: "申述完成", value: 4 },
  { label: "已驳回", value: 5 },
  { label: "申述完成", value: 4 },
  { label: "已完成", value: 6 },
  { label: "已作废", value: 7 }
]
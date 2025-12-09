<template>
  <div class="dashboard-container">
    <el-container>
      <el-main>
        <!-- 统计卡片 -->
        <el-row :gutter="20" class="stats-row">
          <el-col :xs="24" :sm="12" :md="6" :lg="6" :xl="6">
            <el-card class="stat-card" shadow="hover">
              <div class="stat-content">
                <div class="stat-icon stat-1">
                  <el-icon><Calendar /></el-icon>
                </div>
                <div class="stat-info">
                  <h3>进行中周期</h3>
                  <div class="stat-value">3</div>
                  <p>2个季度，1个年度</p>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :xs="24" :sm="12" :md="6" :lg="6" :xl="6">
            <el-card class="stat-card" shadow="hover">
              <div class="stat-content">
                <div class="stat-icon stat-2">
                  <el-icon><Check /></el-icon>
                </div>
                <div class="stat-info">
                  <h3>已完成绩效</h3>
                  <div class="stat-value">1,014</div>
                  <p>占总绩效的85%</p>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :xs="24" :sm="12" :md="6" :lg="6" :xl="6">
            <el-card class="stat-card" shadow="hover">
              <div class="stat-content">
                <div class="stat-icon stat-3">
                  <el-icon><Warning /></el-icon>
                </div>
                <div class="stat-info">
                  <h3>待处理申诉</h3>
                  <div class="stat-value">7</div>
                  <p>平均处理时间2.3天</p>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :xs="24" :sm="12" :md="6" :lg="6" :xl="6">
            <el-card class="stat-card" shadow="hover">
              <div class="stat-content">
                <div class="stat-icon stat-4">
                  <el-icon><ChatDotSquare /></el-icon>
                </div>
                <div class="stat-info">
                  <h3>待安排面谈</h3>
                  <div class="stat-value">12</div>
                  <p>其中3个已逾期</p>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>

        <!-- 主内容区 -->
        <el-row :gutter="20" class="main-content-row">
          <!-- 左侧列 -->
          <el-col :xs="24" :lg="12" class="left-column">
            <!-- 绩效周期概览 -->
            <el-card class="panel-card" shadow="never">
              <template #header>
                <div class="panel-header">
                  <div class="panel-title">
                    <el-icon><Calendar /></el-icon>
                    <span>绩效周期概览</span>
                  </div>
                  <el-button link @click="viewAllPeriods">查看全部</el-button>
                </div>
              </template>
              <div class="period-list">
                <div
                  v-for="period in periods"
                  :key="period.id"
                  class="period-item"
                >
                  <div class="period-info">
                    <h4>{{ period.name }}</h4>
                    <div class="period-dates">{{ period.dates }}</div>
                  </div>
                  <el-tag
                    :type="period.statusType"
                    effect="light"
                    class="period-status"
                    >{{ period.statusText }}</el-tag
                  >
                </div>
              </div>
            </el-card>

            <!-- 申诉处理 -->
            <el-card class="panel-card" shadow="never">
              <template #header>
                <div class="panel-header">
                  <div class="panel-title">
                    <el-icon><ScaleToOriginal /></el-icon>
                    <span>申诉处理</span>
                  </div>
                  <el-button link @click="viewAllAppeals">查看全部</el-button>
                </div>
              </template>
              <el-table
                :data="appeals"
                style="width: 100%"
                :show-header="false"
              >
                <el-table-column>
                  <template #default="{ row }">
                    <div class="appeal-row">
                      <div class="appeal-info">
                        <div class="appeal-title">{{ row.title }}</div>
                        <div class="appeal-meta">{{ row.meta }}</div>
                      </div>
                      <el-button
                        type="primary"
                        size="small"
                        @click="handleAppeal(row)"
                        >处理</el-button
                      >
                    </div>
                  </template>
                </el-table-column>
              </el-table>
            </el-card>

            <!-- 面谈安排 -->
            <el-card class="panel-card" shadow="never">
              <template #header>
                <div class="panel-header">
                  <div class="panel-title">
                    <el-icon><ChatLineRound /></el-icon>
                    <span>面谈安排</span>
                  </div>
                  <el-button link @click="viewAllInterviews"
                    >查看全部</el-button
                  >
                </div>
              </template>
              <div class="interview-list">
                <div
                  v-for="interview in interviews"
                  :key="interview.id"
                  class="interview-item"
                >
                  <div class="interview-header">
                    <div class="interview-person">
                      {{ interview.person }}
                    </div>
                    <el-tag
                      v-if="interview.status"
                      :type="interview.statusType"
                      size="small"
                      >{{ interview.status }}</el-tag
                    >
                    <div v-else class="interview-time">
                      {{ interview.time }}
                    </div>
                  </div>
                  <div class="interview-points">{{ interview.points }}</div>
                </div>
              </div>
            </el-card>
          </el-col>

          <!-- 右侧列 -->
          <el-col :xs="24" :lg="12" class="right-column">
            <!-- 绩效评分统计 -->
            <el-card class="panel-card" shadow="never">
              <template #header>
                <div class="panel-header">
                  <div class="panel-title">
                    <el-icon><Histogram /></el-icon>
                    <span>绩效评分分布</span>
                  </div>
                  <el-button link @click="viewScoreReport">详细报告</el-button>
                </div>
              </template>
              <div class="score-distribution">
                <div
                  v-for="score in scoreDistribution"
                  :key="score.label"
                  class="score-bar"
                >
                  <div class="score-label">{{ score.label }}</div>
                  <el-progress
                    :percentage="score.percentage"
                    :show-text="false"
                    :stroke-width="10"
                    :color="score.color"
                    class="score-progress"
                  />
                  <div class="score-count">{{ score.count }}</div>
                </div>
              </div>
            </el-card>

            <!-- 我的待办 -->
            <el-card class="panel-card" shadow="never">
              <template #header>
                <div class="panel-header">
                  <div class="panel-title">
                    <el-icon><List /></el-icon>
                    <span>我的待办</span>
                  </div>
                  <el-button link @click="viewAllTodos">查看全部</el-button>
                </div>
              </template>
              <div class="todo-list">
                <div
                  v-for="todo in todos"
                  :key="todo.id"
                  class="todo-item"
                  @click="toggleTodo(todo)"
                >
                  <div
                    class="todo-checkbox"
                    :class="{ checked: todo.completed }"
                  >
                    <el-icon v-if="todo.completed"><Check /></el-icon>
                  </div>
                  <div class="todo-content">
                    <h4>{{ todo.title }}</h4>
                    <div class="todo-meta">{{ todo.meta }}</div>
                  </div>
                </div>
              </div>
            </el-card>

          </el-col>
        </el-row>
      </el-main>

      <!-- 页脚 -->
    </el-container>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import {
  Search,
  Calendar,
  Check,
  Warning,
  ChatDotSquare,
  ScaleToOriginal,
  ChatLineRound,
  Histogram,
  List,
  Lightning,
  Plus,
  DocumentCopy
} from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

// 搜索查询
const searchQuery = ref('')

// 绩效周期数据
const periods = ref([
  {
    id: 1,
    name: '2024年第三季度绩效',
    dates: '2024-07-01 至 2024-09-30',
    statusType: 'success',
    statusText: '进行中'
  },
  {
    id: 2,
    name: '2024年第二季度绩效',
    dates: '2024-04-01 至 2024-06-30',
    statusType: 'info',
    statusText: '已结束'
  },
  {
    id: 3,
    name: '2024年年度绩效',
    dates: '2024-01-01 至 2024-12-31',
    statusType: 'success',
    statusText: '进行中'
  }
])

// 申诉数据
const appeals = ref([
  {
    id: 1,
    title: '绩效编号: PF202407023',
    meta: '张三 · 技术部 · 2024-08-12'
  },
  {
    id: 2,
    title: '绩效编号: PF202407045',
    meta: '李四 · 市场部 · 2024-08-10'
  },
  {
    id: 3,
    title: '绩效编号: PF202407018',
    meta: '王五 · 产品部 · 2024-08-08'
  }
])

// 面谈数据
const interviews = ref([
  {
    id: 1,
    person: '与 李明的面谈',
    time: '',
    points: '需讨论季度目标达成情况及下季度计划...',
    status: '待安排',
    statusType: 'warning'
  },
  {
    id: 2,
    person: '与 王芳的面谈',
    time: '2024-08-15 14:30',
    points: '讨论项目延期原因及改进措施...',
    status: '',
    statusType: ''
  },
  {
    id: 3,
    person: '与 赵雷的面谈',
    time: '2024-08-18 10:00',
    points: '绩效申诉处理结果反馈...',
    status: '',
    statusType: ''
  }
])

// 评分分布数据
const scoreDistribution = ref([
  { label: '优秀(90+)', percentage: 25, count: 254, color: '#34c759' },
  { label: '良好(80-89)', percentage: 45, count: 456, color: '#4a7dff' },
  { label: '合格(70-79)', percentage: 20, count: 203, color: '#ff9500' },
  { label: '需改进(<70)', percentage: 10, count: 101, color: '#ff3b30' }
])

// 待办事项数据
const todos = ref([
  {
    id: 1,
    title: '审批张三的绩效评分',
    meta: '技术部 · 2024-08-12 前完成',
    completed: true
  },
  {
    id: 2,
    title: '处理李四的绩效申诉',
    meta: '市场部 · 逾期2天',
    completed: false
  },
  {
    id: 3,
    title: '与王五进行绩效面谈',
    meta: '产品部 · 2024-08-15 14:30',
    completed: false
  },
  {
    id: 4,
    title: '确认2024Q3绩效模板',
    meta: 'HR部门 · 2024-08-20 前完成',
    completed: false
  }
])

// 快速操作数据
const quickActions = ref([
  { id: 1, icon: 'Plus', label: '新建绩效', command: 'newPerformance' },
  { id: 2, icon: 'UserChecked', label: '批量评分', command: 'batchScore' },
  { id: 3, icon: 'CalendarPlus', label: '创建周期', command: 'createPeriod' },
  { id: 4, icon: 'DocumentCopy', label: '导出数据', command: 'exportData' }
])

// 搜索处理
const handleSearch = () => {
  if (searchQuery.value.trim()) {
    ElMessage.info(`搜索: ${searchQuery.value}`)
  }
}

// 用户菜单处理
const handleUserCommand = (command) => {
  switch (command) {
    case 'profile':
      ElMessage.info('打开个人中心')
      break
    case 'settings':
      ElMessage.info('打开系统设置')
      break
    case 'logout':
      ElMessageBox.confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        ElMessage.success('已退出登录')
      })
      break
  }
}

// 查看所有周期
const viewAllPeriods = () => {
  ElMessage.info('查看所有绩效周期')
}

// 处理申诉
const handleAppeal = (row) => {
  ElMessageBox.confirm(`确定要处理申诉 ${row.title} 吗？`, '处理申诉', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'primary'
  }).then(() => {
    ElMessage.success(`开始处理: ${row.title}`)
  })
}

// 查看所有申诉
const viewAllAppeals = () => {
  ElMessage.info('查看所有申诉')
}

// 查看所有面谈
const viewAllInterviews = () => {
  ElMessage.info('查看所有面谈')
}

// 查看评分报告
const viewScoreReport = () => {
  ElMessage.info('查看详细评分报告')
}

// 切换待办状态
const toggleTodo = (todo) => {
  todo.completed = !todo.completed
  const action = todo.completed ? '完成' : '取消完成'
  ElMessage.success(`${action}: ${todo.title}`)
}

// 查看所有待办
const viewAllTodos = () => {
  ElMessage.info('查看所有待办事项')
}

// 快速操作处理
const handleQuickAction = (command) => {
  const actionMap = {
    newPerformance: '新建绩效',
    batchScore: '批量评分',
    createPeriod: '创建周期',
    exportData: '导出数据'
  }
  ElMessage.info(`执行操作: ${actionMap[command]}`)
}
</script>

<style scoped>
.dashboard-container {
  min-height: 100vh;
  background-color: #f5f7fa;
}

/* 头部样式 */
.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
}

.header-left h1 {
  color: #2c3e50;
  font-size: 24px;
  margin-bottom: 5px;
  display: flex;
  align-items: center;
  gap: 10px;
}

.header-left p {
  color: #666;
  font-size: 14px;
  margin: 0;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.search-input {
  width: 250px;
}

.user-profile {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 8px 12px;
  border-radius: 6px;
  background: white;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
  cursor: pointer;
  transition: background-color 0.3s;
}

.user-profile:hover {
  background-color: #f5f7fa;
}

.user-info {
  line-height: 1.4;
}

.user-name {
  font-weight: 600;
  color: #2c3e50;
  font-size: 14px;
}

.user-role {
  color: #666;
  font-size: 12px;
}

/* 统计卡片样式 */
.stats-row {
  margin-bottom: 20px;
}

.stat-card {
  border-radius: 10px;
  border: none;
  height: 100%;
}

.stat-card .el-card__body {
  padding: 20px;
}

.stat-content {
  display: flex;
  align-items: center;
  gap: 15px;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
}

.stat-1 {
  background-color: rgba(74, 125, 255, 0.1);
  color: #4a7dff;
}
.stat-2 {
  background-color: rgba(52, 199, 89, 0.1);
  color: #34c759;
}
.stat-3 {
  background-color: rgba(255, 149, 0, 0.1);
  color: #ff9500;
}
.stat-4 {
  background-color: rgba(255, 69, 58, 0.1);
  color: #ff453a;
}

.stat-info h3 {
  font-size: 12px;
  color: #666;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  margin-bottom: 5px;
  font-weight: normal;
}

.stat-value {
  font-size: 28px;
  font-weight: 700;
  color: #2c3e50;
  line-height: 1;
  margin-bottom: 5px;
}

.stat-info p {
  font-size: 12px;
  color: #888;
  margin: 0;
}

/* 主内容区样式 */
.main-content-row {
  margin-top: 20px;
}

.panel-card {
  margin-bottom: 20px;
  border-radius: 10px;
  border: 1px solid #e0e6ed;
}

.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0;
}

.panel-title {
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 600;
  color: #2c3e50;
}

/* 周期列表样式 */
.period-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.period-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  border-radius: 8px;
  background-color: #f9fafc;
  border-left: 4px solid #4a7dff;
  transition: background-color 0.2s;
}

.period-item:hover {
  background-color: #f0f4ff;
}

.period-info h4 {
  font-size: 15px;
  color: #2c3e50;
  margin-bottom: 5px;
  font-weight: 600;
}

.period-dates {
  font-size: 13px;
  color: #666;
}

.period-status {
  min-width: 60px;
  text-align: center;
}

/* 申诉表格样式 */
.appeal-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  padding: 8px 0;
}

.appeal-info {
  flex: 1;
}

.appeal-title {
  font-weight: 600;
  color: #2c3e50;
  font-size: 14px;
  margin-bottom: 3px;
}

.appeal-meta {
  font-size: 12px;
  color: #666;
}

/* 面谈列表样式 */
.interview-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.interview-item {
  padding: 15px;
  border-radius: 8px;
  background-color: #f9fafc;
  border-left: 4px solid #ff9500;
  transition: background-color 0.2s;
}

.interview-item:hover {
  background-color: #fff8f0;
}

.interview-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.interview-person {
  font-weight: 600;
  color: #2c3e50;
  font-size: 14px;
}

.interview-time {
  font-size: 13px;
  color: #666;
}

.interview-points {
  font-size: 13px;
  color: #666;
  line-height: 1.5;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

/* 评分分布样式 */
.score-distribution {
  margin-top: 10px;
}

.score-bar {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.score-label {
  width: 80px;
  font-size: 14px;
  color: #666;
}

.score-progress {
  flex: 1;
  margin: 0 15px;
}

.score-count {
  width: 50px;
  font-size: 16px;
  font-weight: 600;
  text-align: right;
  color: #2c3e50;
}

/* 待办事项样式 */
.todo-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.todo-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 15px;
  border-radius: 8px;
  background-color: #f9fafc;
  border-left: 4px solid #4a7dff;
  cursor: pointer;
  transition: background-color 0.2s;
}

.todo-item:hover {
  background-color: #f0f4ff;
}

.todo-checkbox {
  width: 20px;
  height: 20px;
  border-radius: 4px;
  border: 2px solid #ddd;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  transition: all 0.2s;
}

.todo-checkbox.checked {
  background-color: #4a7dff;
  border-color: #4a7dff;
}

.todo-content {
  flex: 1;
}

.todo-content h4 {
  font-size: 14px;
  color: #2c3e50;
  margin-bottom: 3px;
  font-weight: 600;
}

.todo-meta {
  font-size: 12px;
  color: #888;
}

/* 快速操作样式 */
.quick-actions {
  margin-top: 10px;
}

.action-btn {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 10px;
  padding: 20px 10px;
  background-color: #f9fafc;
  border-radius: 8px;
  border: 1px solid #e0e6ed;
  cursor: pointer;
  transition: all 0.2s;
  text-align: center;
  height: 100px;
}

.action-btn:hover {
  background-color: #4a7dff;
  color: white;
  border-color: #4a7dff;
  transform: translateY(-2px);
}

.action-btn:hover .action-icon {
  color: white;
}

.action-icon {
  font-size: 24px;
  color: #4a7dff;
  transition: color 0.2s;
}

.action-btn span {
  font-size: 14px;
  font-weight: 500;
}

/* 页脚样式 */
.el-footer {
  padding: 20px 0;
  text-align: center;
  color: #888;
  font-size: 13px;
  border-top: 1px solid #e0e6ed;
  margin-top: 20px;
}

.footer-content p {
  margin: 5px 0;
}

.footer-subtitle {
  font-size: 12px;
  color: #aaa;
}

/* 响应式调整 */
@media (max-width: 768px) {
  .header-content {
    flex-direction: column;
    align-items: flex-start;
    gap: 15px;
  }

  .header-right {
    width: 100%;
    justify-content: space-between;
  }

  .search-input {
    flex: 1;
  }

  .quick-actions .el-col {
    margin-bottom: 10px;
  }
}

@media (max-width: 576px) {
  .score-bar {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }

  .score-progress {
    width: 100%;
    margin: 0;
  }

  .score-count {
    text-align: left;
  }
}
</style>
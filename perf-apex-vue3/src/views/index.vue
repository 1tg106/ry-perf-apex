<template>
  <div class="dashboard-container">
    <el-container>
      <el-main>
        <!-- 统计卡片 -->
        <StatCards />
        
        <!-- 主内容区 -->
        <el-row :gutter="20" class="main-content-row">
          <!-- 左侧列 -->
          <el-col :xs="24" :lg="12" class="left-column">
            <!-- 绩效周期概览 -->
            <PeriodOverview />
            
            <!-- 申诉处理 -->
            <AppealHandling />
            
            <!-- 面谈安排 -->
            <InterviewSchedule />
          </el-col>
          
          <!-- 右侧列 -->
          <el-col :xs="24" :lg="12" class="right-column">
            <!-- 绩效评分统计 -->
            <ScoreDistribution />
            
            <!-- 我的待办 -->
            <TodoList />
          </el-col>
        </el-row>
      </el-main>
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

// 引入组件
import StatCards from '@/components/dashboard/StatCards.vue'
import PeriodOverview from '@/components/dashboard/PeriodOverview.vue'
import AppealHandling from '@/components/dashboard/AppealHandling.vue'
import InterviewSchedule from '@/components/dashboard/InterviewSchedule.vue'
import ScoreDistribution from '@/components/dashboard/ScoreDistribution.vue'
import TodoList from '@/components/dashboard/TodoList.vue'

// 搜索查询
const searchQuery = ref('')

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

/* 主内容区样式 */
.main-content-row {
  margin-top: 20px;
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
</style>
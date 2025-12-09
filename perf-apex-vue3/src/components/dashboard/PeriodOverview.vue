<template>
  <el-card class="panel-card" shadow="never">
    <template #header>
      <div class="panel-header">
        <div class="panel-title">
          <el-icon><Calendar /></el-icon>
          <span>绩效周期概览</span>
        </div>
        <!-- <el-button link @click="viewAllPeriods">查看全部</el-button> -->
      </div>
    </template>
    <div class="period-list">
      <div
        v-for="period in periods"
        :key="period.periodId"
        class="period-item"
      >
        <div class="period-info">
          <h4>{{ period.periodName }}</h4>
          <div class="period-dates">{{ period.submitDeadline }} 至 {{ period.scoreDeadline }}</div>
        </div>
        <el-tag
          :type="getStatusType(period.status)"
          effect="light"
          class="period-status"
          >{{ getStatusText(period.status) }}</el-tag
        >
      </div>
    </div>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { Calendar } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { getPerfPeriodList } from '@/api/perf/stat'

// 绩效周期数据
const periods = ref([])

// 获取绩效周期数据
const loadPeriodData = async () => {
  try {
    const response = await getPerfPeriodList({ pageNum: 1, pageSize: 5 })
    if (response.code === 200) {
      periods.value = response.rows || []
    }
  } catch (err) {
    console.error('获取绩效周期数据失败:', err)
    ElMessage.error('获取绩效周期数据失败')
  }
}

// 获取状态类型
const getStatusType = (status) => {
  switch (status) {
    case '1': return 'success' // 进行中
    case '2': return 'info'    // 已结束
    default: return 'info'
  }
}

// 获取状态文本
const getStatusText = (status) => {
  switch (status) {
    case '1': return '进行中'
    case '2': return '已结束'
    default: return '未知'
  }
}

// 查看所有周期
const viewAllPeriods = () => {
  ElMessage.info('查看所有绩效周期')
}

// 页面加载时获取数据
onMounted(() => {
  loadPeriodData()
})
</script>

<style scoped>
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
</style>
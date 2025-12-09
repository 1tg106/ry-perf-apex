<template>
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
</template>

<script setup>
import { ref } from 'vue'
import { Calendar } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

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

// 查看所有周期
const viewAllPeriods = () => {
  ElMessage.info('查看所有绩效周期')
}
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
<template>
  <el-card class="panel-card" shadow="never">
    <template #header>
      <div class="panel-header">
        <div class="panel-title">
          <el-icon><ChatLineRound /></el-icon>
          <span>面谈安排</span>
        </div>
        <el-button link @click="viewAllInterviews">查看全部</el-button>
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
</template>

<script setup>
import { ref } from 'vue'
import { ChatLineRound } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

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

// 查看所有面谈
const viewAllInterviews = () => {
  ElMessage.info('查看所有面谈')
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
</style>
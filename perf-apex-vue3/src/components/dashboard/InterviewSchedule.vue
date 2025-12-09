<template>
  <el-card class="panel-card" shadow="never">
    <template #header>
      <div class="panel-header">
        <div class="panel-title">
          <el-icon><ChatLineRound /></el-icon>
          <span>面谈安排</span>
        </div>
        <!-- <el-button link @click="viewAllInterviews">查看全部</el-button> -->
      </div>
    </template>
    <div class="interview-list">
      <div
        v-for="interview in interviews"
        :key="interview.interviewId"
        class="interview-item"
      >
        <div class="interview-header">
          <div class="interview-person">
            与 {{ interview.intervieweeName }} 的面谈
          </div>
          <el-tag
            v-if="interview.ifInterview === 0"
            type="warning"
            size="small"
            >待面谈</el-tag
          >
          <div v-else class="interview-time">
            {{ interview.interviewTime }}
          </div>
        </div>
        <div class="interview-points">{{ interview.keyPoints }}</div>
      </div>
    </div>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ChatLineRound } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { getPerfInterviewList } from '@/api/perf/stat'

// 面谈数据
const interviews = ref([])

// 获取面谈数据
const loadInterviewData = async () => {
  try {
    const response = await getPerfInterviewList({ pageNum: 1, pageSize: 5, ifInterview: 0 })
    if (response.code === 200) {
      interviews.value = response.rows || []
    }
  } catch (err) {
    console.error('获取面谈数据失败:', err)
    ElMessage.error('获取面谈数据失败')
  }
}

// 查看所有面谈
const viewAllInterviews = () => {
  ElMessage.info('查看所有面谈')
}

// 页面加载时获取数据
onMounted(() => {
  loadInterviewData()
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
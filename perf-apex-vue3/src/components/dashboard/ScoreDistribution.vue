<template>
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
</template>

<script setup>
import { ref } from 'vue'
import { Histogram } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

// 评分分布数据
const scoreDistribution = ref([
  { label: '优秀(90+)', percentage: 25, count: 254, color: '#34c759' },
  { label: '良好(80-89)', percentage: 45, count: 456, color: '#4a7dff' },
  { label: '合格(70-79)', percentage: 20, count: 203, color: '#ff9500' },
  { label: '需改进(<70)', percentage: 10, count: 101, color: '#ff3b30' }
])

// 查看评分报告
const viewScoreReport = () => {
  ElMessage.info('查看详细评分报告')
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

/* 响应式调整 */
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
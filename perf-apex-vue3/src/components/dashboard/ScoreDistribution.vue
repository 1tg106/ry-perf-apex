<template>
  <el-card class="panel-card" shadow="never">
    <template #header>
      <div class="panel-header">
        <div class="panel-title">
          <el-icon><Histogram /></el-icon>
          <span>绩效评分分布</span>
        </div>
        <!-- <el-button link @click="viewScoreReport">详细报告</el-button> -->
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
import { ref, onMounted } from 'vue'
import { Histogram } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { getPerformanceScoreDistributeList } from '@/api/perf/stat'

// 评分分布数据
const scoreDistribution = ref([
  { label: '优秀(90+)', percentage: 0, count: 0, color: '#34c759' },
  { label: '良好(80-89)', percentage: 0, count: 0, color: '#4a7dff' },
  { label: '合格(70-79)', percentage: 0, count: 0, color: '#ff9500' },
  { label: '需改进(<70)', percentage: 0, count: 0, color: '#ff3b30' }
])

// 获取评分分布数据
const loadScoreDistributionData = async () => {
  try {
    const response = await getPerformanceScoreDistributeList()
    if (response.code === 200) {
      const data = response.data || []
      // 更新评分分布数据
      data.forEach(item => {
        switch (item.scoreLevel) {
          case 1: // 优秀(90+)
            scoreDistribution.value[0].count = item.count
            scoreDistribution.value[0].percentage = item.percentage
            break
          case 2: // 良好(80-89)
            scoreDistribution.value[1].count = item.count
            scoreDistribution.value[1].percentage = item.percentage
            break
          case 3: // 合格(70-79)
            scoreDistribution.value[2].count = item.count
            scoreDistribution.value[2].percentage = item.percentage
            break
          case 4: // 需改进(<70)
            scoreDistribution.value[3].count = item.count
            scoreDistribution.value[3].percentage = item.percentage
            break
        }
      })
    }
  } catch (err) {
    console.error('获取评分分布数据失败:', err)
    ElMessage.error('获取评分分布数据失败')
  }
}

// 查看评分报告
const viewScoreReport = () => {
  ElMessage.info('查看详细评分报告')
}

// 页面加载时获取数据
onMounted(() => {
  loadScoreDistributionData()
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
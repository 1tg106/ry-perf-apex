<template>
  <el-row :gutter="20" class="stats-row">
    <el-col :xs="24" :sm="12" :md="6" :lg="6" :xl="6">
      <el-card class="stat-card" shadow="hover">
        <div class="stat-content">
          <div class="stat-icon stat-1">
            <el-icon><Calendar /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">
              <el-statistic title="进行中周期" :value="periodValue" />
            </div>
            <!-- <p>2个季度，1个年度</p> -->
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
            <div class="stat-value">
              <el-statistic title="已完成绩效" :value="finishPerformanceValue" />
            </div>
            <!-- <p>占总绩效的85%</p> -->
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
            <div class="stat-value">
              <el-statistic title="待处理申诉" :value="appealValue" />
            </div>
            <!-- <p>平均处理时间2.3天</p> -->
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
            <div class="stat-value">
              <el-statistic title="待安排面谈" :value="interviewValue" />
            </div>
            <!-- <p>其中3个已逾期</p> -->
          </div>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup>
import { Calendar, Check, Warning, ChatDotSquare } from '@element-plus/icons-vue'
import { useTransition } from '@vueuse/core'
import { ref } from 'vue'

// 使用
const { stat: periodStat, value: periodValue } = useAnimatedCounter(0) // 进行中周期数
const { stat: finishPerformanceStat, value: finishPerformanceValue } = useAnimatedCounter(0) // 已完成绩效数
const { stat: appealStat, value: appealValue } = useAnimatedCounter(0) // 待处理申诉数
const { stat: interviewStat, value: interviewValue } = useAnimatedCounter(0) // 待安排面谈数

setTimeout(()=>{
  periodStat.value = 200
  finishPerformanceStat.value = 1200
  appealStat.value = 85
  interviewStat.value = 12
})

// 创建一个可复用的 hook
function useAnimatedCounter(initialValue = 0, duration = 500) {
  const stat = ref(initialValue)
  const value = useTransition(stat, { duration })
  return { stat, value }
}

</script>

<style scoped>
::v-deep .el-statistic__content{
  font-size: 28px;
  font-weight: 700;
}
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
</style>
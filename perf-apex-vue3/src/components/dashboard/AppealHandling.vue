<template>
  <el-card class="panel-card" shadow="never">
    <template #header>
      <div class="panel-header">
        <div class="panel-title">
          <el-icon><ScaleToOriginal /></el-icon>
          <span>申诉处理</span>
        </div>
        <!-- <el-button link @click="viewAllAppeals">查看全部</el-button> -->
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
              <div class="appeal-title">绩效编号: {{ row.performanceNo }}</div>
              <div class="appeal-meta">{{ row.nickName }} · {{ row.deptName }} · {{ row.createTime }}</div>
            </div>
            <!-- <el-button
              type="primary"
              size="small"
              @click="handleAppeal(row)"
              >处理</el-button
            > -->
          </div>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ScaleToOriginal } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getPerfAppealList } from '@/api/perf/stat'

// 申诉数据
const appeals = ref([])

// 获取申诉数据
const loadAppealData = async () => {
  try {
    const response = await getPerfAppealList({ pageNum: 1, pageSize: 5 })
    if (response.code === 200) {
      appeals.value = response.rows || []
    }
  } catch (err) {
    console.error('获取申诉数据失败:', err)
    ElMessage.error('获取申诉数据失败')
  }
}

// 处理申诉
// const handleAppeal = (row) => {
//   ElMessageBox.confirm(`确定要处理申诉 ${row.performanceNo} 吗？`, '处理申诉', {
//     confirmButtonText: '确定',
//     cancelButtonText: '取消',
//     type: 'primary'
//   }).then(() => {
//     ElMessage.success(`开始处理: ${row.performanceNo}`)
//   })
// }

// 查看所有申诉
const viewAllAppeals = () => {
  ElMessage.info('查看所有申诉')
}

// 页面加载时获取数据
onMounted(() => {
  loadAppealData()
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
</style>
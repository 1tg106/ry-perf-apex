<template>
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
        :key="todo.performanceId"
        class="todo-item"
        @click="toggleTodo(todo)"
      >
        <div class="todo-content">
          <h4>{{ todo.periodName }}</h4>
          <div class="todo-meta">{{ todo.nickName }} · {{ todo.deptName }} · {{ formatDate(todo.createTime) }}</div>
        </div>
      </div>
    </div>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { List, Check } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { waitMyScorePerformanceList } from '@/api/perf/stat'

const router = useRouter()

// 待办事项数据
const todos = ref([])

// 获取待评分列表数据
const loadTodoData = async () => {
  try {
    const response = await waitMyScorePerformanceList({ pageNum: 1, pageSize: 10, ifScore: 0 })
    if (response.code === 200) {
      todos.value = response.rows || []
      // 初始化completed状态
      todos.value.forEach(todo => {
        todo.completed = false
      })
    }
  } catch (err) {
    console.error('获取待办事项数据失败:', err)
    ElMessage.error('获取待办事项数据失败')
  }
}

// 格式化日期
const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`
}

// 切换待办状态
const toggleTodo = (todo) => {
  todo.completed = !todo.completed
  const action = todo.completed ? '完成' : '取消完成'
  ElMessage.success(`${action}: ${todo.periodName}`)
}

// 查看所有待办
const viewAllTodos = () => {
  router.push('/myPerf/backlog')
}

// 页面加载时获取数据
onMounted(() => {
  loadTodoData()
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
</style>
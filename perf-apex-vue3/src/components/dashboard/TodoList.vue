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
        :key="todo.id"
        class="todo-item"
        @click="toggleTodo(todo)"
      >
        <div
          class="todo-checkbox"
          :class="{ checked: todo.completed }"
        >
          <el-icon v-if="todo.completed"><Check /></el-icon>
        </div>
        <div class="todo-content">
          <h4>{{ todo.title }}</h4>
          <div class="todo-meta">{{ todo.meta }}</div>
        </div>
      </div>
    </div>
  </el-card>
</template>

<script setup>
import { ref } from 'vue'
import { List, Check } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

// 待办事项数据
const todos = ref([
  {
    id: 1,
    title: '审批张三的绩效评分',
    meta: '技术部 · 2024-08-12 前完成',
    completed: true
  },
  {
    id: 2,
    title: '处理李四的绩效申诉',
    meta: '市场部 · 逾期2天',
    completed: false
  },
  {
    id: 3,
    title: '与王五进行绩效面谈',
    meta: '产品部 · 2024-08-15 14:30',
    completed: false
  },
  {
    id: 4,
    title: '确认2024Q3绩效模板',
    meta: 'HR部门 · 2024-08-20 前完成',
    completed: false
  }
])

// 切换待办状态
const toggleTodo = (todo) => {
  todo.completed = !todo.completed
  const action = todo.completed ? '完成' : '取消完成'
  ElMessage.success(`${action}: ${todo.title}`)
}

// 查看所有待办
const viewAllTodos = () => {
  ElMessage.info('查看所有待办事项')
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
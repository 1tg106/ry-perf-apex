<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="是否评分" prop="templateId">
        <el-select v-model="queryParams.templateId" placeholder="请选择" clearable style="width: 220px" @keyup.enter="handleQuery">
          <el-option label="未评分" :value="0" />
          <el-option label="已评分" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="performanceList" @selection-change="handleSelectionChange">
      <el-table-column label="绩效编号" align="center" prop="performanceNo" width="220" />
      <el-table-column label="绩效周期" align="center" prop="periodName" />
      <el-table-column label="模板" align="center" prop="templateName" />
      <el-table-column label="人员" align="center" prop="nickName" />
      <el-table-column label="部门" align="center" prop="deptName" />
      <el-table-column label="状态" align="center" prop="status">
        <template #default="{row}">
          <el-tag type="success" v-if="row.ifScore">已评分</el-tag>
          <el-tag type="danger" v-if="!row.ifScore">待评分</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="自评总分" align="center" prop="selfScore" />
      <el-table-column label="提交时间" align="center" prop="submitTime">
        <template #default="scope">
          <span>{{ scope.row.submitTime}}</span>
        </template>
      </el-table-column>
      <el-table-column label="评分时间" align="center" prop="confirmTime">
        <template #default="scope">
          <span>{{ scope.row.scoringTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right" width="180">
        <template #default="scope">
          <el-button link type="success" icon="Star" v-if="!scope.row.ifScore" @click="openScoreDialog(scope.row)">评分</el-button>
          <el-button link type="success" icon="View" v-else @click="openScoreDialog(scope.row)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加评分弹窗组件 -->
    <PerformanceScoreDialog ref="scoreDialog" @submit="getList" />

  </div>
</template>

<script setup name="Performance">
import { PERFORMANCE_STATUS, PERFORMANCE_STATUS_LIST, PERFORMANCE_STEP_STATUS, PERFORMANCE_STEP_STATUS_LIST } from '@/utils/perf/performanceEnum'
// 引入评分弹窗组件
import PerformanceScoreDialog from '@/components/perf/PerformanceScoreDialog/PerformanceScoreDialog.vue'
import { listScore, getScore } from '@/api/perf/score'

const { proxy } = getCurrentInstance()

const performanceList = ref([])
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    ifScore: null
  }
})

const { queryParams } = toRefs(data)
const performanceDialog = ref()
const scoreDialog = ref() // 评分弹窗引用

const openPerformanceDialog = (row) => {
  performanceDialog.value.openDialog(row.id)
}

// 打开评分弹窗
const openScoreDialog = (row) => {
  scoreDialog.value.openDialog(row.id)
}

/** 查询绩效实例列表 */
function getList() {
  loading.value = true
  listScore(queryParams.value).then(response => {
    performanceList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

getList()
</script>
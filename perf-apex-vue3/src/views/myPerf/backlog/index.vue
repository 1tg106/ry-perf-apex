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
      <el-table-column label="绩效编号" align="center" prop="performanceNo" width="180" />
      <el-table-column label="绩效周期" align="center" prop="periodName" width="100" />
      <el-table-column label="模板" align="center" prop="templateName" width="100" />
      <el-table-column label="人员" align="center" prop="nickName" width="100" />
      <el-table-column label="部门" align="center" prop="deptName" width="100" />
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template #default="{row}">
          <el-tag type="primary" v-if="row.status === PERFORMANCE_STATUS.DRAFT">{{ PERFORMANCE_STATUS_LIST[0].label }}</el-tag>
          <el-tag type="primary" v-if="row.status === PERFORMANCE_STATUS.PENDING_SUBMISSION">{{ PERFORMANCE_STATUS_LIST[1].label }}</el-tag>
          <el-tag type="primary" v-if="row.status === PERFORMANCE_STATUS.PENDING_SCORE">{{ PERFORMANCE_STATUS_LIST[2].label }}</el-tag>
          <el-tag type="warning" v-if="row.status === PERFORMANCE_STATUS.PENDING_HR">{{ PERFORMANCE_STATUS_LIST[3].label }}</el-tag>
          <el-tag type="success" v-if="row.status === PERFORMANCE_STATUS.CONFIRMED">{{ PERFORMANCE_STATUS_LIST[4].label }}</el-tag>
          <el-tag type="danger" v-if="row.status === PERFORMANCE_STATUS.REJECTED">{{ PERFORMANCE_STATUS_LIST[5].label }}</el-tag>
          <el-tag type="danger" v-if="row.status === PERFORMANCE_STATUS.APPEAL">{{ PERFORMANCE_STATUS_LIST[6].label }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="当前步骤" align="center" prop="currentStep" width="130">
        <template #default="{row}">
          <el-tag type="primary" v-if="row.currentStep === PERFORMANCE_STEP_STATUS.DRAFT">{{ PERFORMANCE_STEP_STATUS_LIST[0].label }}</el-tag>
          <el-tag type="primary" v-if="row.currentStep === PERFORMANCE_STEP_STATUS.PENDING_SUPERVISOR_SCORE">{{ PERFORMANCE_STEP_STATUS_LIST[1].label }}</el-tag>
          <el-tag type="warning" v-if="row.currentStep === PERFORMANCE_STEP_STATUS.PENDING_DEPT_HEAD_SCORE">{{ PERFORMANCE_STEP_STATUS_LIST[2].label }}</el-tag>
          <el-tag type="warning" v-if="row.currentStep === PERFORMANCE_STEP_STATUS.PENDING_HR_CONFIRMATION">{{ PERFORMANCE_STEP_STATUS_LIST[3].label }}</el-tag>
          <el-tag type="success" v-if="row.currentStep === PERFORMANCE_STEP_STATUS.COMPLETED">{{ PERFORMANCE_STEP_STATUS_LIST[4].label }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="自评总分" align="center" prop="selfScore" width="100" />
      <el-table-column label="最终得分" align="center" prop="finalScore" width="100" />
      <el-table-column label="驳回原因" align="center" prop="rejectReason" width="100" />
      <el-table-column label="提交时间" align="center" prop="submitTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.submitTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="确认时间" align="center" prop="confirmTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.confirmTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right" width="180">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="openPerformanceDialog(scope.row)">填写</el-button>
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

    <PerformanceDialog ref="performanceDialog" @submit="getList" />

  </div>
</template>

<script setup name="Performance">
import { PERFORMANCE_STATUS, PERFORMANCE_STATUS_LIST, PERFORMANCE_STEP_STATUS, PERFORMANCE_STEP_STATUS_LIST } from '@/utils/perf/performanceEnum'
import PerformanceDialog from '@/components/perf/PerformanceWriteDialog/PerformanceWriteDialog.vue'
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

const openPerformanceDialog = (row) => {
  performanceDialog.value.openDialog(row.id)
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
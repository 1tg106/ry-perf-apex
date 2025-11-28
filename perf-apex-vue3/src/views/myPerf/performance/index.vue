<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="绩效周期" prop="periodId">
        <el-select v-model="queryParams.periodId" placeholder="请选择绩效周期" clearable filterable style="width: 220px" @keyup.enter="handleQuery">
          <el-option
            v-for="(item, index) in periodOptions"
            :key="index"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="模板" prop="templateId">
        <el-select v-model="queryParams.templateId" placeholder="请选择模板" clearable filterable style="width: 220px" @keyup.enter="handleQuery">
          <el-option
            v-for="(item,index) in templateOptions"
            :key="index"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="提交时间" prop="submitTime">
        <el-date-picker clearable
          v-model="queryParams.submitTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择提交时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="确认时间" prop="confirmTime">
        <el-date-picker clearable
          v-model="queryParams.confirmTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择确认时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['perf:performance:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['perf:performance:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['perf:performance:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['perf:performance:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="performanceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
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
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['perf:performance:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['perf:performance:remove']">删除</el-button>
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

    <!-- 添加或修改绩效实例对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="performanceRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="绩效周期" prop="periodId">
          <el-select v-model="form.periodId" placeholder="请选择绩效周期" clearable style="width: 220px">
            <el-option v-for="(item,index) in periodOptions" :key="index" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="模板" prop="templateId">
          <el-select v-model="form.templateId" placeholder="请选择模板" clearable style="width: 220px">
            <el-option v-for="(item,index) in templateOptions" :key="index" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <!-- 部门 & 员工选择已移除（由上层业务自动处理或不允许在此处编辑） -->
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Performance">
import { listMyPerformance, getPerformance, delPerformance, addPerformance, updatePerformance, addMyPerformance, editMyPerformance } from "@/api/perf/performance"
import { getPerfChooseList } from '@/api/perf/period'
import { getTemplateChooseList } from '@/api/perf/template'
import { PERFORMANCE_STATUS, PERFORMANCE_STATUS_LIST, PERFORMANCE_STEP_STATUS, PERFORMANCE_STEP_STATUS_LIST } from '@/utils/perf/performanceEnum'

const { proxy } = getCurrentInstance()

const performanceList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const data = reactive({
  form: {},
  periodOptions: [],
  templateOptions: [],
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    performanceNo: null,
    periodId: null,
    templateId: null,
    selfScore: null,
    finalScore: null,
    currentStep: null,
    status: null,
    rejectReason: null,
    submitTime: null,
    confirmTime: null,
  },
  rules: {
    periodId: [
      { required: true, message: "绩效周期ID不能为空", trigger: "blur" }
    ],
    templateId: [
      { required: true, message: "模板ID不能为空", trigger: "blur" }
    ],
    status: [
      { required: true, message: "状态(DRAFT:草稿, PENDING_SCORE:待评分, PENDING_HR:待HR确认, CONFIRMED:已确认, REJECTED:已驳回, APPEAL:申诉中)不能为空", trigger: "change" }
    ],
  }
})

const { queryParams, form, rules, periodOptions, templateOptions } = toRefs(data)

/** 查询绩效实例列表 */
function getList() {
  loading.value = true
  listMyPerformance(queryParams.value).then(response => {
    performanceList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

// 获取下拉选项：绩效周期
function getPeriodOptions() {
  getPerfChooseList().then(response => {
    data.periodOptions = response.data || []
  })
}

// 获取模板下拉选项
function getTemplateOptions() {
  getTemplateChooseList().then(response => {
    data.templateOptions = response.data || []
  })
}

// 取消按钮
function cancel() {
  open.value = false
  reset()
}

// 表单重置
function reset() {
  form.value = {
  id: null,
  performanceNo: null,
  periodId: null,
  templateId: null,
    selfScore: null,
    finalScore: null,
    currentStep: null,
    status: null,
    rejectReason: null,
    submitTime: null,
    confirmTime: null
  }
  proxy.resetForm("performanceRef")
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

/** 新增按钮操作 */
function handleAdd() {
  reset()
  getPeriodOptions()
  getTemplateOptions()
  open.value = true
  title.value = "添加绩效实例"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  getPeriodOptions()
  getTemplateOptions()
  const _id = row.id || ids.value
  getPerformance(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改绩效实例"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["performanceRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        editMyPerformance(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addMyPerformance(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value
  proxy.$modal.confirm('是否确认删除绩效实例编号为"' + _ids + '"的数据项？').then(function() {
    return delPerformance(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('perf/performance/export', {
    ...queryParams.value
  }, `performance_${new Date().getTime()}.xlsx`)
}

getList()
getPeriodOptions()
getTemplateOptions()
</script>
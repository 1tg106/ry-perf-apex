<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="绩效ID" prop="performanceId">
        <el-input
          v-model="queryParams.performanceId"
          placeholder="请输入绩效ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="步骤序号" prop="step">
        <el-input
          v-model="queryParams.step"
          placeholder="请输入步骤序号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="步骤名称" prop="stepName">
        <el-input
          v-model="queryParams.stepName"
          placeholder="请输入步骤名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评分人ID" prop="scorerId">
        <el-input
          v-model="queryParams.scorerId"
          placeholder="请输入评分人ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评分" prop="score">
        <el-input
          v-model="queryParams.score"
          placeholder="请输入评分"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="提交时间" prop="submitTime">
        <el-date-picker clearable
          v-model="queryParams.submitTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择提交时间">
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
          v-hasPermi="['system:flow:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:flow:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:flow:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:flow:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="flowList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="流程ID" align="center" prop="flowId" />
      <el-table-column label="绩效ID" align="center" prop="performanceId" />
      <el-table-column label="步骤序号" align="center" prop="step" />
      <el-table-column label="步骤名称" align="center" prop="stepName" />
      <el-table-column label="评分人ID" align="center" prop="scorerId" />
      <el-table-column label="评分人类型(DIRECT_LEADER:直属上级, DEPT_HEAD:部门负责人, HR:HR)" align="center" prop="scorerType" />
      <el-table-column label="评分" align="center" prop="score" />
      <el-table-column label="评语" align="center" prop="comment" />
      <el-table-column label="状态(PENDING:待处理, SCORED:已评分, SKIPPED:已跳过)" align="center" prop="status" />
      <el-table-column label="提交时间" align="center" prop="submitTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.submitTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:flow:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:flow:remove']">删除</el-button>
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

    <!-- 添加或修改评分流程对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="flowRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="绩效ID" prop="performanceId">
          <el-input v-model="form.performanceId" placeholder="请输入绩效ID" />
        </el-form-item>
        <el-form-item label="步骤序号" prop="step">
          <el-input v-model="form.step" placeholder="请输入步骤序号" />
        </el-form-item>
        <el-form-item label="步骤名称" prop="stepName">
          <el-input v-model="form.stepName" placeholder="请输入步骤名称" />
        </el-form-item>
        <el-form-item label="评分人ID" prop="scorerId">
          <el-input v-model="form.scorerId" placeholder="请输入评分人ID" />
        </el-form-item>
        <el-form-item label="评分" prop="score">
          <el-input v-model="form.score" placeholder="请输入评分" />
        </el-form-item>
        <el-form-item label="评语" prop="comment">
          <el-input v-model="form.comment" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="提交时间" prop="submitTime">
          <el-date-picker clearable
            v-model="form.submitTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择提交时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="删除标志" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标志" />
        </el-form-item>
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

<script setup name="Flow">
import { listFlow, getFlow, delFlow, addFlow, updateFlow } from "@/api/performance/flow"

const { proxy } = getCurrentInstance()

const flowList = ref([])
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
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    performanceId: null,
    step: null,
    stepName: null,
    scorerId: null,
    scorerType: null,
    score: null,
    comment: null,
    status: null,
    submitTime: null,
  },
  rules: {
    performanceId: [
      { required: true, message: "绩效ID不能为空", trigger: "blur" }
    ],
    step: [
      { required: true, message: "步骤序号不能为空", trigger: "blur" }
    ],
    stepName: [
      { required: true, message: "步骤名称不能为空", trigger: "blur" }
    ],
    scorerId: [
      { required: true, message: "评分人ID不能为空", trigger: "blur" }
    ],
    scorerType: [
      { required: true, message: "评分人类型(DIRECT_LEADER:直属上级, DEPT_HEAD:部门负责人, HR:HR)不能为空", trigger: "change" }
    ],
    status: [
      { required: true, message: "状态(PENDING:待处理, SCORED:已评分, SKIPPED:已跳过)不能为空", trigger: "change" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询评分流程列表 */
function getList() {
  loading.value = true
  listFlow(queryParams.value).then(response => {
    flowList.value = response.rows
    total.value = response.total
    loading.value = false
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
    flowId: null,
    performanceId: null,
    step: null,
    stepName: null,
    scorerId: null,
    scorerType: null,
    score: null,
    comment: null,
    status: null,
    submitTime: null,
    delFlag: null,
    createTime: null,
    updateTime: null
  }
  proxy.resetForm("flowRef")
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
  ids.value = selection.map(item => item.flowId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加评分流程"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _flowId = row.flowId || ids.value
  getFlow(_flowId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改评分流程"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["flowRef"].validate(valid => {
    if (valid) {
      if (form.value.flowId != null) {
        updateFlow(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addFlow(form.value).then(response => {
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
  const _flowIds = row.flowId || ids.value
  proxy.$modal.confirm('是否确认删除评分流程编号为"' + _flowIds + '"的数据项？').then(function() {
    return delFlow(_flowIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/flow/export', {
    ...queryParams.value
  }, `flow_${new Date().getTime()}.xlsx`)
}

getList()
</script>

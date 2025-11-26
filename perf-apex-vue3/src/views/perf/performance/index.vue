<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="绩效编号" prop="performanceNo">
        <el-input
          v-model="queryParams.performanceNo"
          placeholder="请输入绩效编号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="绩效周期ID" prop="periodId">
        <el-input
          v-model="queryParams.periodId"
          placeholder="请输入绩效周期ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="模板ID" prop="templateId">
        <el-input
          v-model="queryParams.templateId"
          placeholder="请输入模板ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="员工ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入员工ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部门ID" prop="deptId">
        <el-input
          v-model="queryParams.deptId"
          placeholder="请输入部门ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="自评总分" prop="selfScore">
        <el-input
          v-model="queryParams.selfScore"
          placeholder="请输入自评总分"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="最终得分" prop="finalScore">
        <el-input
          v-model="queryParams.finalScore"
          placeholder="请输入最终得分"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="当前步骤" prop="currentStep">
        <el-input
          v-model="queryParams.currentStep"
          placeholder="请输入当前步骤"
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
      <el-table-column label="绩效ID" align="center" prop="id" />
      <el-table-column label="绩效编号" align="center" prop="performanceNo" />
      <el-table-column label="绩效周期ID" align="center" prop="periodId" />
      <el-table-column label="模板ID" align="center" prop="templateId" />
      <el-table-column label="员工ID" align="center" prop="userId" />
      <el-table-column label="部门ID" align="center" prop="deptId" />
      <el-table-column label="自评总分" align="center" prop="selfScore" />
      <el-table-column label="最终得分" align="center" prop="finalScore" />
      <el-table-column label="当前步骤" align="center" prop="currentStep" />
      <el-table-column label="状态(DRAFT:草稿, PENDING_SCORE:待评分, PENDING_HR:待HR确认, CONFIRMED:已确认, REJECTED:已驳回, APPEAL:申诉中)" align="center" prop="status" />
      <el-table-column label="驳回原因" align="center" prop="rejectReason" />
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
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
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
        <el-form-item label="绩效编号" prop="performanceNo">
          <el-input v-model="form.performanceNo" placeholder="请输入绩效编号" />
        </el-form-item>
        <el-form-item label="绩效周期ID" prop="periodId">
          <el-input v-model="form.periodId" placeholder="请输入绩效周期ID" />
        </el-form-item>
        <el-form-item label="模板ID" prop="templateId">
          <el-input v-model="form.templateId" placeholder="请输入模板ID" />
        </el-form-item>
        <el-form-item label="员工ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入员工ID" />
        </el-form-item>
        <el-form-item label="部门ID" prop="deptId">
          <el-input v-model="form.deptId" placeholder="请输入部门ID" />
        </el-form-item>
        <el-form-item label="自评总分" prop="selfScore">
          <el-input v-model="form.selfScore" placeholder="请输入自评总分" />
        </el-form-item>
        <el-form-item label="最终得分" prop="finalScore">
          <el-input v-model="form.finalScore" placeholder="请输入最终得分" />
        </el-form-item>
        <el-form-item label="当前步骤" prop="currentStep">
          <el-input v-model="form.currentStep" placeholder="请输入当前步骤" />
        </el-form-item>
        <el-form-item label="驳回原因" prop="rejectReason">
          <el-input v-model="form.rejectReason" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="提交时间" prop="submitTime">
          <el-date-picker clearable
            v-model="form.submitTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择提交时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="确认时间" prop="confirmTime">
          <el-date-picker clearable
            v-model="form.confirmTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择确认时间">
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

<script setup name="Performance">
import { listPerformance, getPerformance, delPerformance, addPerformance, updatePerformance } from "@/api/perf/performance"

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
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    performanceNo: null,
    periodId: null,
    templateId: null,
    userId: null,
    deptId: null,
    selfScore: null,
    finalScore: null,
    currentStep: null,
    status: null,
    rejectReason: null,
    submitTime: null,
    confirmTime: null,
  },
  rules: {
    performanceNo: [
      { required: true, message: "绩效编号不能为空", trigger: "blur" }
    ],
    periodId: [
      { required: true, message: "绩效周期ID不能为空", trigger: "blur" }
    ],
    templateId: [
      { required: true, message: "模板ID不能为空", trigger: "blur" }
    ],
    userId: [
      { required: true, message: "员工ID不能为空", trigger: "blur" }
    ],
    deptId: [
      { required: true, message: "部门ID不能为空", trigger: "blur" }
    ],
    status: [
      { required: true, message: "状态(DRAFT:草稿, PENDING_SCORE:待评分, PENDING_HR:待HR确认, CONFIRMED:已确认, REJECTED:已驳回, APPEAL:申诉中)不能为空", trigger: "change" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询绩效实例列表 */
function getList() {
  loading.value = true
  listPerformance(queryParams.value).then(response => {
    performanceList.value = response.rows
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
    id: null,
    performanceNo: null,
    periodId: null,
    templateId: null,
    userId: null,
    deptId: null,
    selfScore: null,
    finalScore: null,
    currentStep: null,
    status: null,
    rejectReason: null,
    submitTime: null,
    confirmTime: null,
    delFlag: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null
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
  open.value = true
  title.value = "添加绩效实例"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
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
        updatePerformance(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addPerformance(form.value).then(response => {
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
</script>

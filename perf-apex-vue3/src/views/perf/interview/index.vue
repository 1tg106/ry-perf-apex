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
      <el-form-item label="面谈人ID" prop="interviewerId">
        <el-input
          v-model="queryParams.interviewerId"
          placeholder="请输入面谈人ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="被面谈人ID" prop="intervieweeId">
        <el-input
          v-model="queryParams.intervieweeId"
          placeholder="请输入被面谈人ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="面谈时间" prop="interviewTime">
        <el-date-picker clearable
          v-model="queryParams.interviewTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择面谈时间">
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
          v-hasPermi="['perf:interview:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['perf:interview:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['perf:interview:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['perf:interview:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="interviewList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="面谈ID" align="center" prop="id" />
      <el-table-column label="绩效ID" align="center" prop="performanceId" />
      <el-table-column label="面谈人ID" align="center" prop="interviewerId" />
      <el-table-column label="被面谈人ID" align="center" prop="intervieweeId" />
      <el-table-column label="面谈时间" align="center" prop="interviewTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.interviewTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="面谈要点" align="center" prop="keyPoints" />
      <el-table-column label="优点与成绩" align="center" prop="strengths" />
      <el-table-column label="待改进点" align="center" prop="improvements" />
      <el-table-column label="行动计划" align="center" prop="actionPlan" />
      <el-table-column label="员工反馈" align="center" prop="feedback" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['perf:interview:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['perf:interview:remove']">删除</el-button>
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

    <!-- 添加或修改绩效面谈对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="interviewRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="绩效ID" prop="performanceId">
          <el-input v-model="form.performanceId" placeholder="请输入绩效ID" />
        </el-form-item>
        <el-form-item label="面谈人ID" prop="interviewerId">
          <el-input v-model="form.interviewerId" placeholder="请输入面谈人ID" />
        </el-form-item>
        <el-form-item label="被面谈人ID" prop="intervieweeId">
          <el-input v-model="form.intervieweeId" placeholder="请输入被面谈人ID" />
        </el-form-item>
        <el-form-item label="面谈时间" prop="interviewTime">
          <el-date-picker clearable
            v-model="form.interviewTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择面谈时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="面谈要点" prop="keyPoints">
          <el-input v-model="form.keyPoints" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="优点与成绩" prop="strengths">
          <el-input v-model="form.strengths" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="待改进点" prop="improvements">
          <el-input v-model="form.improvements" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="行动计划" prop="actionPlan">
          <el-input v-model="form.actionPlan" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="员工反馈" prop="feedback">
          <el-input v-model="form.feedback" type="textarea" placeholder="请输入内容" />
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

<script setup name="Interview">
import { listInterview, getInterview, delInterview, addInterview, updateInterview } from "@/api/perf/interview"

const { proxy } = getCurrentInstance()

const interviewList = ref([])
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
    interviewerId: null,
    intervieweeId: null,
    interviewTime: null,
    keyPoints: null,
    strengths: null,
    improvements: null,
    actionPlan: null,
    feedback: null,
  },
  rules: {
    performanceId: [
      { required: true, message: "绩效ID不能为空", trigger: "blur" }
    ],
    interviewerId: [
      { required: true, message: "面谈人ID不能为空", trigger: "blur" }
    ],
    intervieweeId: [
      { required: true, message: "被面谈人ID不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询绩效面谈列表 */
function getList() {
  loading.value = true
  listInterview(queryParams.value).then(response => {
    interviewList.value = response.rows
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
    performanceId: null,
    interviewerId: null,
    intervieweeId: null,
    interviewTime: null,
    keyPoints: null,
    strengths: null,
    improvements: null,
    actionPlan: null,
    feedback: null,
    delFlag: null,
    createTime: null,
    updateTime: null
  }
  proxy.resetForm("interviewRef")
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
  title.value = "添加绩效面谈"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getInterview(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改绩效面谈"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["interviewRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateInterview(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addInterview(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除绩效面谈编号为"' + _ids + '"的数据项？').then(function() {
    return delInterview(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('perf/interview/export', {
    ...queryParams.value
  }, `interview_${new Date().getTime()}.xlsx`)
}

getList()
</script>

<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="周期名称" prop="periodName">
        <el-input
          v-model="queryParams.periodName"
          placeholder="请输入周期名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="提交时间" prop="submitDeadline">
        <el-date-picker clearable
          v-model="queryParams.submitDeadline"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择提交截止时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="评分时间" prop="scoreDeadline">
        <el-date-picker clearable
          v-model="queryParams.scoreDeadline"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择评分截止时间">
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
          v-hasPermi="['system:period:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:period:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:period:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:period:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="periodList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="周期名称" align="center" prop="periodName" />
      <el-table-column label="周期类型" align="center" prop="periodType">
        <template #default="scope">
          <el-tag type="primary" v-if="scope.row.periodType === PERF_PERIOD_TYPE.MONTH">{{ PERF_PERIOD_TYPE_LIST[0].label }}</el-tag>
          <el-tag type="success" v-if="scope.row.periodType === PERF_PERIOD_TYPE.QUARTER">{{ PERF_PERIOD_TYPE_LIST[1].label }}</el-tag>
          <el-tag type="warning" v-if="scope.row.periodType === PERF_PERIOD_TYPE.HALF_YEAR">{{ PERF_PERIOD_TYPE_LIST[2].label }}</el-tag>
          <el-tag type="danger" v-if="scope.row.periodType === PERF_PERIOD_TYPE.YEAR">{{ PERF_PERIOD_TYPE_LIST[3].label }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="提交截止时间" align="center" prop="submitDeadline" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.submitDeadline, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="评分截止时间" align="center" prop="scoreDeadline" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.scoreDeadline, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="periodType">
        <template #default="scope">
          <el-tag type="primary" v-if="scope.row.status === PERF_PERIOD_STATUS.PREPARE">{{ PERF_PERIOD_STATUS_LIST[0].label }}</el-tag>
          <el-tag type="success" v-if="scope.row.status === PERF_PERIOD_STATUS.UNDERWAY">{{ PERF_PERIOD_STATUS_LIST[1].label }}</el-tag>
          <el-tag type="info" v-if="scope.row.status === PERF_PERIOD_STATUS.FINISHED">{{ PERF_PERIOD_STATUS_LIST[2].label }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right" width="180">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:period:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:period:remove']">删除</el-button>
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

    <!-- 添加或修改绩效周期对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="periodRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="周期名称" prop="periodName">
          <el-input v-model="form.periodName" placeholder="请输入周期名称(如：2024年Q1)" />
        </el-form-item>
        <el-form-item label="周期类型" prop="periodType">
          <el-select v-model="form.periodType" placeholder="请选择类型" style="width: 220px">
            <el-option
              v-for="(item,index) in PERF_PERIOD_TYPE_LIST"
              :key="index"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="提交时间" prop="submitDeadline">
          <el-date-picker clearable
            v-model="form.submitDeadline"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择提交截止时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="评分时间" prop="scoreDeadline">
          <el-date-picker clearable
            v-model="form.scoreDeadline"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择评分截止时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="状态" prop="periodType">
          <el-select v-model="form.status" placeholder="请选择类型" style="width: 220px">
            <el-option
              v-for="(item,index) in PERF_PERIOD_STATUS_LIST"
              :key="index"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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

<script setup name="Period">
import { listPeriod, getPeriod, delPeriod, addPeriod, updatePeriod } from "@/api/perf/period"
import { PERF_PERIOD_TYPE, PERF_PERIOD_STATUS, PERF_PERIOD_STATUS_LIST, PERF_PERIOD_TYPE_LIST } from "@/utils/perf/periodEnum"

const { proxy } = getCurrentInstance()

const periodList = ref([])
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
    periodName: null,
    periodType: null,
    startDate: null,
    endDate: null,
    submitDeadline: null,
    scoreDeadline: null,
    status: null,
  },
  rules: {
    periodName: [
      { required: true, message: "周期名称(如：2024年Q1)不能为空", trigger: "blur" }
    ],
    periodType: [
      { required: true, message: "周期类型(QUARTER:季度, HALF_YEAR:半年度, YEAR:年度)不能为空", trigger: "change" }
    ],
    submitDeadline: [
      { required: true, message: "提交截止时间不能为空", trigger: "blur" }
    ],
    scoreDeadline: [
      { required: true, message: "评分截止时间不能为空", trigger: "blur" }
    ],
    status: [
      { required: true, message: "状态(0:准备中 1:进行中 2:已结束)不能为空", trigger: "change" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询绩效周期列表 */
function getList() {
  loading.value = true
  listPeriod(queryParams.value).then(response => {
    periodList.value = response.rows
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
    periodId: null,
    periodName: null,
    periodType: null,
    startDate: null,
    endDate: null,
    submitDeadline: null,
    scoreDeadline: null,
    status: null,
    remark: null
  }
  proxy.resetForm("periodRef")
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
  ids.value = selection.map(item => item.periodId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加绩效周期"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _periodId = row.periodId || ids.value
  getPeriod(_periodId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改绩效周期"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["periodRef"].validate(valid => {
    if (valid) {
      if (form.value.periodId != null) {
        updatePeriod(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addPeriod(form.value).then(response => {
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
  const _periodIds = row.periodId || ids.value
  proxy.$modal.confirm('是否确认删除该数据项？').then(function() {
    return delPeriod(_periodIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/period/export', {
    ...queryParams.value
  }, `period_${new Date().getTime()}.xlsx`)
}

getList()
</script>

<template>
  <div class="app-container">
    <el-row :gutter="10">
      <el-col :span="1.5">
        <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="处理状态" prop="appealStatus">
            <el-select 
              v-model="queryParams.appealStatus" 
              placeholder="请选择处理状态" 
              clearable 
              style="width: 180px"
            >
              <el-option
                v-for="item in APPEAL_STATUS_LIST"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
            <el-button icon="Refresh" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="appealList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="考核周期" align="center" prop="periodName" width="100" />
      <el-table-column label="绩效编号" align="center" prop="performanceNo" width="200" />
      <el-table-column label="申请人" align="center" prop="nickName" />
      <el-table-column label="申诉理由" align="center" prop="appealReason" width="200" />
      <el-table-column label="处理状态" align="center" prop="appealStatus">
        <template #default="scope">
          <dict-tag :options="APPEAL_STATUS_LIST" :value="scope.row.appealStatus" />
        </template>
      </el-table-column>
      <el-table-column label="处理人" align="center" prop="handleNickName" />
      <el-table-column label="申诉时间" align="center" prop="appealTime" width="180" />
      <el-table-column label="处理时间" align="center" prop="processTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right" width="160">
        <template #default="scope">
          <el-button 
            link 
            type="primary" 
            icon="Edit" 
            @click="handleProcess(scope.row)" 
            v-if="scope.row.appealStatus === 'PENDING'"
            v-hasPermi="['perf:appeal:edit']"
          >处理</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['perf:appeal:remove']">删除</el-button>
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

    <!-- 添加或修改绩效申诉对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="appealRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="绩效ID" prop="performanceId">
          <el-input v-model="form.performanceId" placeholder="请输入绩效ID" />
        </el-form-item>
        <el-form-item label="申诉理由" prop="appealReason">
          <el-input v-model="form.appealReason" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="处理状态" prop="appealStatus">
          <el-select v-model="form.appealStatus" placeholder="请选择处理状态">
            <el-option
              v-for="item in APPEAL_STATUS_LIST"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="处理人ID" prop="processorId">
          <el-input v-model="form.processorId" placeholder="请输入处理人ID" />
        </el-form-item>
        <el-form-item label="处理意见" prop="processComment">
          <el-input v-model="form.processComment" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="处理结果" prop="processResult">
          <el-input v-model="form.processResult" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="调整分数" prop="adjustScore">
          <el-input v-model="form.adjustScore" placeholder="请输入调整分数" />
        </el-form-item>
        <el-form-item label="申诉时间" prop="appealTime">
          <el-date-picker clearable
            v-model="form.appealTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择申诉时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="处理时间" prop="processTime">
          <el-date-picker clearable
            v-model="form.processTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择处理时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
    
    <!-- 处理绩效申诉对话框 -->
    <PerfAppealHandleDialog
      v-model="processOpen"
      :row-data="currentRow"
      @success="handleProcessSuccess"
    />
  </div>
</template>

<script setup name="Appeal">
import { selectRelevancePerfAppealList, getAppeal, delAppeal, addAppeal, updateAppeal } from "@/api/perf/appeal"
import { APPEAL_STATUS_LIST } from "@/utils/perf/appeal"
import PerfAppealHandleDialog from "@/components/perf/PerfAppealHandleDialog/PerfAppealHandleDialog.vue"

const { proxy } = getCurrentInstance()

const appealList = ref([])
const open = ref(false)
const processOpen = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const currentRow = ref({})

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    appealStatus: null,
  },
  rules: {
    performanceId: [
      { required: true, message: "绩效ID不能为空", trigger: "blur" }
    ],
    appealReason: [
      { required: true, message: "申诉理由不能为空", trigger: "blur" }
    ],
    appealStatus: [
      { required: true, message: "处理状态不能为空", trigger: "change" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询绩效申诉列表 */
function getList() {
  loading.value = true
  selectRelevancePerfAppealList(queryParams.value).then(response => {
    appealList.value = response.rows
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
    appealReason: null,
    appealStatus: null,
    processorId: null,
    processComment: null,
    processResult: null,
    adjustScore: null,
    appealTime: null,
    processTime: null,
    createTime: null,
    updateTime: null
  }
  proxy.resetForm("appealRef")
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
  title.value = "添加绩效申诉"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getAppeal(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改绩效申诉"
  })
}

/** 处理按钮操作 */
function handleProcess(row) {
  currentRow.value = row
  processOpen.value = true
}

/** 处理成功回调 */
function handleProcessSuccess() {
  getList()
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["appealRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateAppeal(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addAppeal(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除绩效申诉编号为"' + _ids + '"的数据项？').then(function() {
    return delAppeal(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('perf/appeal/export', {
    ...queryParams.value
  }, `appeal_${new Date().getTime()}.xlsx`)
}

getList()
</script>
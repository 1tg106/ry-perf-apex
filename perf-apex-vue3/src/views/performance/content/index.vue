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
      <el-form-item label="指标项ID" prop="itemId">
        <el-input
          v-model="queryParams.itemId"
          placeholder="请输入指标项ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="自评分数" prop="selfScore">
        <el-input
          v-model="queryParams.selfScore"
          placeholder="请输入自评分数"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="最终评分" prop="finalScore">
        <el-input
          v-model="queryParams.finalScore"
          placeholder="请输入最终评分"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="排序" prop="sortOrder">
        <el-input
          v-model="queryParams.sortOrder"
          placeholder="请输入排序"
          clearable
          @keyup.enter="handleQuery"
        />
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
          v-hasPermi="['system:content:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:content:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:content:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:content:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="contentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="内容ID" align="center" prop="contentId" />
      <el-table-column label="绩效ID" align="center" prop="performanceId" />
      <el-table-column label="指标项ID" align="center" prop="itemId" />
      <el-table-column label="自评目标" align="center" prop="selfTarget" />
      <el-table-column label="自评成果" align="center" prop="selfResult" />
      <el-table-column label="自评分数" align="center" prop="selfScore" />
      <el-table-column label="自评评语" align="center" prop="selfComment" />
      <el-table-column label="最终评分" align="center" prop="finalScore" />
      <el-table-column label="最终评语" align="center" prop="finalComment" />
      <el-table-column label="排序" align="center" prop="sortOrder" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:content:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:content:remove']">删除</el-button>
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

    <!-- 添加或修改绩效内容对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="contentRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="绩效ID" prop="performanceId">
          <el-input v-model="form.performanceId" placeholder="请输入绩效ID" />
        </el-form-item>
        <el-form-item label="指标项ID" prop="itemId">
          <el-input v-model="form.itemId" placeholder="请输入指标项ID" />
        </el-form-item>
        <el-form-item label="自评目标" prop="selfTarget">
          <el-input v-model="form.selfTarget" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="自评成果" prop="selfResult">
          <el-input v-model="form.selfResult" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="自评分数" prop="selfScore">
          <el-input v-model="form.selfScore" placeholder="请输入自评分数" />
        </el-form-item>
        <el-form-item label="自评评语" prop="selfComment">
          <el-input v-model="form.selfComment" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="最终评分" prop="finalScore">
          <el-input v-model="form.finalScore" placeholder="请输入最终评分" />
        </el-form-item>
        <el-form-item label="最终评语" prop="finalComment">
          <el-input v-model="form.finalComment" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="排序" prop="sortOrder">
          <el-input v-model="form.sortOrder" placeholder="请输入排序" />
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

<script setup name="Content">
import { listContent, getContent, delContent, addContent, updateContent } from "@/api/performance/content"

const { proxy } = getCurrentInstance()

const contentList = ref([])
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
    itemId: null,
    selfTarget: null,
    selfResult: null,
    selfScore: null,
    selfComment: null,
    finalScore: null,
    finalComment: null,
    sortOrder: null,
  },
  rules: {
    performanceId: [
      { required: true, message: "绩效ID不能为空", trigger: "blur" }
    ],
    itemId: [
      { required: true, message: "指标项ID不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询绩效内容列表 */
function getList() {
  loading.value = true
  listContent(queryParams.value).then(response => {
    contentList.value = response.rows
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
    contentId: null,
    performanceId: null,
    itemId: null,
    selfTarget: null,
    selfResult: null,
    selfScore: null,
    selfComment: null,
    finalScore: null,
    finalComment: null,
    sortOrder: null,
    delFlag: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null
  }
  proxy.resetForm("contentRef")
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
  ids.value = selection.map(item => item.contentId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加绩效内容"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _contentId = row.contentId || ids.value
  getContent(_contentId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改绩效内容"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["contentRef"].validate(valid => {
    if (valid) {
      if (form.value.contentId != null) {
        updateContent(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addContent(form.value).then(response => {
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
  const _contentIds = row.contentId || ids.value
  proxy.$modal.confirm('是否确认删除绩效内容编号为"' + _contentIds + '"的数据项？').then(function() {
    return delContent(_contentIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/content/export', {
    ...queryParams.value
  }, `content_${new Date().getTime()}.xlsx`)
}

getList()
</script>

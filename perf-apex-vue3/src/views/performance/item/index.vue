<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="模板ID" prop="templateId">
        <el-input
          v-model="queryParams.templateId"
          placeholder="请输入模板ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="父指标ID" prop="parentId">
        <el-input
          v-model="queryParams.parentId"
          placeholder="请输入父指标ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="指标名称" prop="itemName">
        <el-input
          v-model="queryParams.itemName"
          placeholder="请输入指标名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="权重(0-100)" prop="weight">
        <el-input
          v-model="queryParams.weight"
          placeholder="请输入权重(0-100)"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="最低分" prop="minScore">
        <el-input
          v-model="queryParams.minScore"
          placeholder="请输入最低分"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="最高分" prop="maxScore">
        <el-input
          v-model="queryParams.maxScore"
          placeholder="请输入最高分"
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
          v-hasPermi="['system:item:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:item:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:item:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:item:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="itemList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="指标项ID" align="center" prop="itemId" />
      <el-table-column label="模板ID" align="center" prop="templateId" />
      <el-table-column label="父指标ID" align="center" prop="parentId" />
      <el-table-column label="指标名称" align="center" prop="itemName" />
      <el-table-column label="指标类型(OBJECTIVE:目标, KEY_RESULT:关键成果, COMPETENCY:能力项)" align="center" prop="itemType" />
      <el-table-column label="权重(0-100)" align="center" prop="weight" />
      <el-table-column label="评分标准描述" align="center" prop="scoreStandard" />
      <el-table-column label="最低分" align="center" prop="minScore" />
      <el-table-column label="最高分" align="center" prop="maxScore" />
      <el-table-column label="排序" align="center" prop="sortOrder" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:item:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:item:remove']">删除</el-button>
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

    <!-- 添加或修改模板指标对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="itemRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="模板ID" prop="templateId">
          <el-input v-model="form.templateId" placeholder="请输入模板ID" />
        </el-form-item>
        <el-form-item label="父指标ID" prop="parentId">
          <el-input v-model="form.parentId" placeholder="请输入父指标ID" />
        </el-form-item>
        <el-form-item label="指标名称" prop="itemName">
          <el-input v-model="form.itemName" placeholder="请输入指标名称" />
        </el-form-item>
        <el-form-item label="权重(0-100)" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入权重(0-100)" />
        </el-form-item>
        <el-form-item label="评分标准描述" prop="scoreStandard">
          <el-input v-model="form.scoreStandard" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="最低分" prop="minScore">
          <el-input v-model="form.minScore" placeholder="请输入最低分" />
        </el-form-item>
        <el-form-item label="最高分" prop="maxScore">
          <el-input v-model="form.maxScore" placeholder="请输入最高分" />
        </el-form-item>
        <el-form-item label="排序" prop="sortOrder">
          <el-input v-model="form.sortOrder" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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

<script setup name="Item">
import { listItem, getItem, delItem, addItem, updateItem } from "@/api/performance/item"

const { proxy } = getCurrentInstance()

const itemList = ref([])
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
    templateId: null,
    parentId: null,
    itemName: null,
    itemType: null,
    weight: null,
    scoreStandard: null,
    minScore: null,
    maxScore: null,
    sortOrder: null,
  },
  rules: {
    templateId: [
      { required: true, message: "模板ID不能为空", trigger: "blur" }
    ],
    itemName: [
      { required: true, message: "指标名称不能为空", trigger: "blur" }
    ],
    itemType: [
      { required: true, message: "指标类型(OBJECTIVE:目标, KEY_RESULT:关键成果, COMPETENCY:能力项)不能为空", trigger: "change" }
    ],
    weight: [
      { required: true, message: "权重(0-100)不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询模板指标列表 */
function getList() {
  loading.value = true
  listItem(queryParams.value).then(response => {
    itemList.value = response.rows
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
    itemId: null,
    templateId: null,
    parentId: null,
    itemName: null,
    itemType: null,
    weight: null,
    scoreStandard: null,
    minScore: null,
    maxScore: null,
    sortOrder: null,
    remark: null,
    delFlag: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null
  }
  proxy.resetForm("itemRef")
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
  ids.value = selection.map(item => item.itemId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加模板指标"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _itemId = row.itemId || ids.value
  getItem(_itemId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改模板指标"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["itemRef"].validate(valid => {
    if (valid) {
      if (form.value.itemId != null) {
        updateItem(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addItem(form.value).then(response => {
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
  const _itemIds = row.itemId || ids.value
  proxy.$modal.confirm('是否确认删除模板指标编号为"' + _itemIds + '"的数据项？').then(function() {
    return delItem(_itemIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/item/export', {
    ...queryParams.value
  }, `item_${new Date().getTime()}.xlsx`)
}

getList()
</script>

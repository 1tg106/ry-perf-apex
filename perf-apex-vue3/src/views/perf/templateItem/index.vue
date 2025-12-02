<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="指标名称" prop="itemName">
        <el-input
          v-model="queryParams.itemName"
          placeholder="请输入指标名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="权重" prop="weight">
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
          v-hasPermi="['perf:templateItem:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['perf:templateItem:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['perf:templateItem:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['perf:templateItem:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="templateItemList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="指标名称" align="center" prop="itemName" />
      <el-table-column label="模版名称" align="center" prop="templateName" />
      <el-table-column label="指标类型" align="center" prop="itemType">
        <template #default="scope">
          <el-tag type="primary" v-if="scope.row.itemType == ITEM_TYPE.OBJECTIVE">{{ ITEM_TYPE_LIST[0].label }}</el-tag>
          <el-tag type="success" v-else-if="scope.row.itemType == ITEM_TYPE.KEY_RESULT">{{ ITEM_TYPE_LIST[1].label }}</el-tag>
          <el-tag type="warning" v-else-if="scope.row.itemType == ITEM_TYPE.COMPETENCY">{{ ITEM_TYPE_LIST[2].label }}</el-tag>
          <el-tag type="info" v-else>{{ "指标大类" }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="权重(0-100)" align="center" prop="weight" />
      <el-table-column label="最低分" align="center" prop="minScore" />
      <el-table-column label="最高分" align="center" prop="maxScore" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right" width="220">
        <template #default="scope">
          <el-button link type="primary" icon="CopyDocument" @click="handleCopy(scope.row)" v-hasPermi="['perf:templateItem:edit']">复制</el-button>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['perf:templateItem:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['perf:templateItem:remove']">删除</el-button>
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
      <el-form ref="templateItemRef" :model="form" :rules="getDynamicRules()" label-width="80px">
        <el-form-item label="指标名称" prop="itemName">
          <el-input v-model="form.itemName" placeholder="请输入指标名称" />
        </el-form-item>
        <el-form-item label="模板" prop="templateId">
          <el-select v-model="form.templateId" placeholder="请选择模板" style="width: 220px">
            <el-option
              v-for="(item,index) in templateOptions"
              :key="index"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="父指标" prop="parentId">
          <el-select v-model="form.parentId" placeholder="请选择父指标" clearable style="width: 220px" @change="handleParentIdChange">
            <el-option
              v-for="(item,index) in parentItemOptions"
              :key="index"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item v-show="form.parentId" label="指标类型" prop="itemType">
          <el-select v-model="form.itemType" clearable placeholder="请选择类型" style="width: 220px">
            <el-option
              v-for="(item,index) in ITEM_TYPE_LIST"
              :key="index"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="权重" prop="weight">
          <el-input-number v-model="form.weight" :min="1" :max="100" />
        </el-form-item>
        <el-form-item v-show="form.parentId" label="最低分" prop="minScore">
          <el-input-number v-model="form.minScore" :min="1" :max="10" />
        </el-form-item>
        <el-form-item v-show="form.parentId" label="最高分" prop="maxScore">
          <el-input-number v-model="form.maxScore" :min="1" :max="10" />
        </el-form-item>
        <el-form-item v-show="form.parentId" label="标准描述" prop="scoreStandard">
          <el-input v-model="form.scoreStandard" type="textarea" placeholder="请输入评分标准描述" />
        </el-form-item>
        <el-form-item v-show="form.parentId" label="备注" prop="remark">
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

<script setup name="TemplateItem">
import { listTemplateItem, getTemplateItem, delTemplateItem, addTemplateItem, updateTemplateItem, getItemChooseList, copyTemplateItem } from "@/api/perf/templateItem"
import { ITEM_TYPE, ITEM_TYPE_LIST } from "@/utils/perf/templateItemEnum"
import { getTemplateChooseList } from "@/api/perf/template"
import { nextTick } from 'vue'

const { proxy } = getCurrentInstance()

const templateItemList = ref([])
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
  // Add template and parent item options
  templateOptions: [],
  parentItemOptions: [],
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    templateId: null,
    parentId: 0,
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
      { required: true, message: "模板不能为空", trigger: "change" }
    ],
    itemName: [
      { required: true, message: "指标名称不能为空", trigger: "blur" }
    ],
    itemType: [
      { required: true, message: "指标类型不能为空", trigger: "change" }
    ],
    weight: [
      { required: true, message: "权重不能为空", trigger: "blur" }
    ],
    minScore: [
      { required: true, message: "最低分不能为空", trigger: "blur" }
    ],
    maxScore: [
      { required: true, message: "最高分不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules, templateOptions, parentItemOptions } = toRefs(data)

/** 查询模板指标列表 */
function getList() {
  loading.value = true
  listTemplateItem(queryParams.value).then(response => {
    templateItemList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

// 获取模板选项
function getTemplateOptions() {
  getTemplateChooseList().then(response => {
    templateOptions.value = response.data.map(item => ({
      value: item.value,
      label: item.label
    }))
  })
}

// 获取父级指标选项
function getParentItemOptions() {
  // 获取所有指标作为父级选项
  getItemChooseList().then(response => {
    parentItemOptions.value = response.data.map(item => ({
      value: item.value,
      label: item.label
    }))
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
    templateId: null,
    parentId: null,
    itemName: null,
    itemType: ITEM_TYPE_LIST[0].value,
    weight: 0,
    scoreStandard: null,
    minScore: 0,
    maxScore: 0,
    sortOrder: null,
    remark: null,
  }
  proxy.resetForm("templateItemRef")
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
  // 加载下拉选项
  getTemplateOptions()
  getParentItemOptions()
  open.value = true
  title.value = "添加模板指标"
}

function handleCopy(row) {
  const _id = row.id;
  proxy.$modal.confirm('是否确认复制该数据项？').then(function() {
    return copyTemplateItem(_id)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("复制成功")
  }).catch(() => {})
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  // 加载下拉选项
  getTemplateOptions()
  getParentItemOptions()
  const _id = row.id || ids.value
  getTemplateItem(_id).then(response => {
    form.value = response.data
    if(form.value.parentId === 0){
      form.value.parentId = null
    }
    open.value = true
    title.value = "修改模板指标"
    
    // 确保表单验证规则正确更新
    nextTick(() => {
      if (proxy.$refs["templateItemRef"]) {
        proxy.$refs["templateItemRef"].clearValidate()
      }
    })
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["templateItemRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateTemplateItem(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addTemplateItem(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除该数据项？').then(function() {
    return delTemplateItem(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('perf/templateItem/export', {
    ...queryParams.value
  }, `templateItem_${new Date().getTime()}.xlsx`)
}

/** 父指标改变时触发 */
function handleParentIdChange(value) {
  // 当取消父指标选择时，清空指标相关字段
  if (!value) {
    form.value.itemType = ITEM_TYPE_LIST[0].value
    form.value.minScore = 0
    form.value.maxScore = 0
    form.value.scoreStandard = ''
    form.value.remark = ''
  }
  
  // 强制重新验证表单
  nextTick(() => {
    if (proxy.$refs["templateItemRef"]) {
      proxy.$refs["templateItemRef"].clearValidate()
    }
  })
}

/** 动态获取表单验证规则 */
function getDynamicRules() {
  const dynamicRules = { ...rules.value }
  
  // 如果选择了父指标，则这些字段需要验证；否则不需要验证
  if (form.value.parentId) {
    dynamicRules.itemType[0].required = true
    dynamicRules.minScore[0].required = true
    dynamicRules.maxScore[0].required = true
  } else {
    dynamicRules.itemType[0].required = false
    dynamicRules.minScore[0].required = false
    dynamicRules.maxScore[0].required = false
  }
  
  return dynamicRules
}

getList()
getTemplateOptions()
getParentItemOptions()
</script>

<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="模板名称" prop="templateName">
        <el-input
          v-model="queryParams.templateName"
          placeholder="请输入模板名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="模板类型" prop="templateType">
        <el-select v-model="queryParams.templateType" placeholder="请选择模板类型" clearable style="width: 200px">
          <el-option label="关键指标" value="KPI" />
          <el-option label="目标与成果" value="OKR" />
          <el-option label="能力素质" value="COMPETENCY" />
        </el-select>
      </el-form-item>
      <el-form-item label="适用部门" prop="deptId">
        <el-tree-select
          v-model="queryParams.deptId"
          :data="deptOptions"
          :props="{ value: 'id', label: 'label', children: 'children' }"
          value-key="id"
          placeholder="请选择适用部门"
          clearable
          check-strictly
          style="width: 200px"
        />
      </el-form-item>
      <el-form-item label="适用岗位" prop="postIds">
        <el-select v-model="queryParams.postIds" multiple placeholder="请选择适用岗位" clearable style="width: 200px">
          <el-option
            v-for="item in postOptions"
            :key="item.postId"
            :label="item.postName"
            :value="item.postId"
          />
        </el-select>
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
          v-hasPermi="['perf:template:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['perf:template:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['perf:template:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['perf:template:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="templateList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="模板名称" align="center" prop="templateName" />
      <el-table-column label="模板类型" align="center" prop="templateType">
        <template #default="scope">
          <el-tag type="primary" v-if="scope.row.templateType == TEMPLATE_TYPE.OKR">{{ TEMPLATE_TYPE_LIST[0].label }}</el-tag>
          <el-tag type="success" v-if="scope.row.templateType == TEMPLATE_TYPE.KPI">{{ TEMPLATE_TYPE_LIST[1].label }}</el-tag>
          <el-tag type="warning" v-if="scope.row.templateType == TEMPLATE_TYPE.COMPETENCY">{{ TEMPLATE_TYPE_LIST[2].label }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="适用部门" align="center" prop="deptName" />
      <el-table-column label="适用岗位" align="center" prop="postNames" />
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <el-tag type="primary" v-if="scope.row.status === COMMON_STATUS.NORMAL">{{ COMMON_STATUS_LIST[0].label }}</el-tag>
          <el-tag type="info" v-if="scope.row.status === COMMON_STATUS.DISABLE">{{ COMMON_STATUS_LIST[1].label }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right" width="180">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['perf:template:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['perf:template:remove']">删除</el-button>
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

    <!-- 添加或修改绩效模板对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="templateRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="模板名称" prop="templateName">
          <el-input v-model="form.templateName" placeholder="请输入模板名称" />
        </el-form-item>
        <el-form-item label="模板类型" prop="templateType">
          <el-select v-model="form.templateType" placeholder="请选择模板类型">
            <el-option label="关键指标" value="KPI" />
            <el-option label="目标与成果" value="OKR" />
            <el-option label="能力素质" value="COMPETENCY" />
          </el-select>
        </el-form-item>
        <el-form-item label="适用部门" prop="deptId">
          <el-tree-select
            v-model="form.deptId"
            :data="enabledDeptOptions"
            :props="{ value: 'id', label: 'label', children: 'children' }"
            value-key="id"
            placeholder="请选择适用部门"
            clearable
            check-strictly
          />
        </el-form-item>
        <el-form-item label="适用岗位" prop="postIds">
          <el-select v-model="form.postIds" multiple placeholder="请选择适用岗位">
            <el-option
              v-for="item in postOptions"
              :key="item.postId"
              :label="item.postName"
              :value="item.postId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="periodType">
          <el-select v-model="form.status" placeholder="请选择类型" style="width: 220px">
            <el-option
              v-for="(item,index) in COMMON_STATUS_LIST"
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

<script setup name="Template">
import { onMounted, ref, reactive, toRefs, getCurrentInstance } from "vue"
import { listTemplate, getTemplate, delTemplate, addTemplate, updateTemplate } from "@/api/perf/template"
import { deptTreeSelect } from "@/api/system/user"
import { listPost } from "@/api/system/post"
import { COMMON_STATUS_LIST, COMMON_STATUS } from "@/utils/perf/commonStatus"
import { TEMPLATE_TYPE, TEMPLATE_TYPE_LIST } from "@/utils/perf/templateIEnum"

const { proxy } = getCurrentInstance()

const templateList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const deptOptions = ref(undefined)
const enabledDeptOptions = ref(undefined)
const postOptions = ref([])

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    templateName: null,
    templateType: null,
    deptId: null,
    postIds: [],
    status: null,
  },
  rules: {
    templateName: [
      { required: true, message: "模板名称不能为空", trigger: "blur" }
    ],
    templateType: [
      { required: true, message: "模板类型不能为空", trigger: "change" }
    ],
    deptId: [
      { required: true, message: "适用部门不能为空", trigger: "change" }
    ],
    postIds: [
      { required: true, message: "适用岗位不能为空", trigger: "change" }
    ],
    status: [
      { required: true, message: "状态(0:正常 1:停用)不能为空", trigger: "change" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询绩效模板列表 */
function getList() {
  loading.value = true
  listTemplate(queryParams.value).then(response => {
    templateList.value = response.rows
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
    templateName: null,
    templateType: null,
    deptId: null,
    postIds: [],
    status: null,
    remark: null,
    delFlag: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null
  }
  proxy.resetForm("templateRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  queryParams.value.deptId = undefined
  queryParams.value.postIds = []
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
  getDeptTree()
  getPostList()
  open.value = true
  title.value = "添加绩效模板"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  getDeptTree()
  getPostList()
  const _id = row.id || ids.value
  getTemplate(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改绩效模板"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["templateRef"].validate(valid => {
    if (valid) {
      console.log(form.value);
      
      if (form.value.id != null) {
        updateTemplate(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addTemplate(form.value).then(response => {
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
    return delTemplate(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 查询部门下拉树结构 */
function getDeptTree() {
  deptTreeSelect().then(response => {
    deptOptions.value = response.data
    enabledDeptOptions.value = filterDisabledDept(JSON.parse(JSON.stringify(response.data)))
  })
}

/** 过滤禁用的部门 */
function filterDisabledDept(deptList) {
  return deptList.filter(dept => {
    if (dept.disabled) {
      return false
    }
    if (dept.children && dept.children.length) {
      dept.children = filterDisabledDept(dept.children)
    }
    return true
  })
}

/** 查询岗位列表 */
function getPostList() {
  listPost().then(response => {
    postOptions.value = response.rows
  })
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('perf/template/export', {
    ...queryParams.value
  }, `template_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getDeptTree()
  getPostList()
  getList()
})
</script>

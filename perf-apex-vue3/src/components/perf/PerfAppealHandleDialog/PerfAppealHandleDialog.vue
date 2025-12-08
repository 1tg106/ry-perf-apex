<template>
  <el-dialog
    :title="title"
    v-model="visible"
    width="600px"
    append-to-body
    @close="handleClose"
  >
    <el-form
      ref="handleFormRef"
      :model="formData"
      :rules="rules"
      label-width="100px"
    >
      <el-row>
        <el-col :span="12">
          <el-form-item label="考核周期" prop="periodName">
            <el-input v-model="formData.periodName" disabled />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="申请人" prop="nickName">
            <el-input v-model="formData.nickName" disabled />
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-form-item label="绩效编号" prop="performanceId">
        <el-input v-model="formData.performanceNo" disabled />
      </el-form-item>
      
      <el-form-item label="申诉理由" prop="appealReason">
        <el-input
          v-model="formData.appealReason"
          type="textarea"
          :rows="3"
          disabled
        />
      </el-form-item>
      
      <el-form-item label="处理意见" prop="processComment">
        <el-input
          v-model="formData.processComment"
          type="textarea"
          :rows="3"
          placeholder="请输入处理意见"
        />
      </el-form-item>
      
      <el-form-item label="是否调整分数" prop="ifAdjustScore">
        <el-radio-group v-model="formData.ifAdjustScore">
          <el-radio :label="0">否</el-radio>
          <el-radio :label="1">是</el-radio>
        </el-radio-group>
      </el-form-item>
      
      <el-form-item 
        v-if="formData.ifAdjustScore === 1" 
        label="调整分数" 
        prop="adjustScore"
      >
        <el-input-number 
          v-model="formData.adjustScore" 
          :min="0" 
          :max="100" 
          controls-position="right" 
          style="width: 100%"
        />
      </el-form-item>
    </el-form>
    
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="handleClose">取 消</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, computed, watch, defineExpose, getCurrentInstance } from 'vue'
import { handlePerfAppeal } from '@/api/perf/appeal'

const { proxy } = getCurrentInstance()

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false
  },
  rowData: {
    type: Object,
    default: () => ({})
  }
})

const emit = defineEmits(['update:modelValue', 'success'])

const visible = computed({
  get: () => props.modelValue,
  set: (val) => {
    emit('update:modelValue', val)
  }
})

const title = ref('处理绩效申诉')
const handleFormRef = ref()

const formData = reactive({
  id: null,
  periodName: '',
  nickName: '',
  performanceId: null,
  performanceNo: null,
  appealReason: '',
  processComment: '',
  ifAdjustScore: 0,
  adjustScore: null
})

const rules = reactive({
  processComment: [
    { required: true, message: '处理意见不能为空', trigger: 'blur' }
  ],
  adjustScore: [
    { required: true, message: '调整分数不能为空', trigger: 'blur' }
  ]
})

watch(
  () => props.rowData,
  (newVal) => {
    if (newVal) {
      formData.id = newVal.id
      formData.periodName = newVal.periodName
      formData.nickName = newVal.nickName
      formData.performanceId = newVal.performanceId
      formData.performanceNo = newVal.performanceNo
      formData.appealReason = newVal.appealReason
      formData.processComment = ''
      formData.ifAdjustScore = 0
      formData.adjustScore = null
    }
  },
  { immediate: true }
)

function submitForm() {
  handleFormRef.value.validate((valid) => {
    if (valid) {
      const submitData = {
        id: formData.id,
        processComment: formData.processComment,
        ifAdjustScore: formData.ifAdjustScore
      }
      
      if (formData.ifAdjustScore === 1) {
        submitData.adjustScore = formData.adjustScore
      }
      
      handlePerfAppeal(submitData).then((response) => {
        if (response.code === 200) {
          proxy.$modal.msgSuccess('处理成功')
          handleClose()
          emit('success')
        } else {
          proxy.$modal.msgError(response.msg || '处理失败')
        }
      })
    }
  })
}

function handleClose() {
  handleFormRef.value.resetFields()
  visible.value = false
}

defineExpose({
  handleClose
})
</script>
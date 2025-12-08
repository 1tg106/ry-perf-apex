<template>
  <el-dialog
    :title="title"
    v-model="visible"
    width="800px"
    append-to-body
    @close="handleClose"
  >
    <el-descriptions :column="2" border>
      <el-descriptions-item label="面谈ID">{{ detailData.id }}</el-descriptions-item>
      <el-descriptions-item label="绩效ID">{{ detailData.performanceId }}</el-descriptions-item>
      <el-descriptions-item label="考核周期">{{ detailData.periodName }}</el-descriptions-item>
      <el-descriptions-item label="面谈人">{{ detailData.interviewerName }}</el-descriptions-item>
      <el-descriptions-item label="被面谈人">{{ detailData.intervieweeName }}</el-descriptions-item>
      <el-descriptions-item label="面谈时间">{{ parseTime(detailData.interviewTime, '{y}-{m}-{d}') }}</el-descriptions-item>
      <el-descriptions-item label="面谈要点" :span="2">{{ detailData.keyPoints }}</el-descriptions-item>
      <el-descriptions-item label="优点与成绩" :span="2">{{ detailData.strengths }}</el-descriptions-item>
      <el-descriptions-item label="待改进点" :span="2">{{ detailData.improvements }}</el-descriptions-item>
      <el-descriptions-item label="行动计划" :span="2">{{ detailData.actionPlan }}</el-descriptions-item>
      <el-descriptions-item label="员工反馈" :span="2">{{ detailData.feedback }}</el-descriptions-item>
      <el-descriptions-item label="是否已面谈">{{ detailData.ifInterview === 1 ? '是' : '否' }}</el-descriptions-item>
      <el-descriptions-item label="创建时间">{{ parseTime(detailData.createTime) }}</el-descriptions-item>
    </el-descriptions>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="handleClose">关 闭</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { parseTime } from '@/utils/ruoyi'

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false
  },
  detailData: {
    type: Object,
    default: () => ({})
  }
})

const emit = defineEmits(['update:modelValue'])

const visible = computed({
  get() {
    return props.modelValue
  },
  set(value) {
    emit('update:modelValue', value)
  }
})

const title = computed(() => {
  return '绩效面谈详情'
})

function handleClose() {
  visible.value = false
}
</script>
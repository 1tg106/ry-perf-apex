<template>
  <el-dialog
    :title="title"
    v-model="visible"
    width="600px"
    append-to-body
    @close="handleClose"
  >
    <el-descriptions :column="1" border>
      <el-descriptions-item label="考核周期">{{ detailData.periodName }}</el-descriptions-item>
      <el-descriptions-item label="绩效编号">{{ detailData.performanceNo }}</el-descriptions-item>
      <el-descriptions-item label="申请人">{{ detailData.nickName }}</el-descriptions-item>
      <el-descriptions-item label="申诉理由">{{ detailData.appealReason }}</el-descriptions-item>
      <el-descriptions-item label="处理状态">
        <dict-tag :options="APPEAL_STATUS_LIST" :value="detailData.appealStatus" />
      </el-descriptions-item>
      <el-descriptions-item label="处理人">{{ detailData.handleNickName }}</el-descriptions-item>
      <el-descriptions-item label="处理意见">{{ detailData.processComment }}</el-descriptions-item>
      <el-descriptions-item label="处理结果">{{ detailData.processResult }}</el-descriptions-item>
      <el-descriptions-item label="是否调整分数">{{ detailData.ifAdjustScore === 1 ? '是' : '否' }}</el-descriptions-item>
      <el-descriptions-item label="调整分数" v-if="detailData.ifAdjustScore === 1">{{ detailData.adjustScore }}</el-descriptions-item>
      <el-descriptions-item label="申诉时间">{{ parseTime(detailData.appealTime) }}</el-descriptions-item>
      <el-descriptions-item label="处理时间">{{ parseTime(detailData.processTime) }}</el-descriptions-item>
    </el-descriptions>
    
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="handleClose">关 闭</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, computed, watch, defineExpose } from 'vue'
import { getAppeal } from '@/api/perf/appeal'
import { APPEAL_STATUS_LIST } from "@/utils/perf/appeal"
import { parseTime } from '@/utils/ruoyi'

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

const emit = defineEmits(['update:modelValue'])

const visible = computed({
  get: () => props.modelValue,
  set: (val) => {
    emit('update:modelValue', val)
  }
})

const title = ref('绩效申诉详情')
const detailData = reactive({
  id: null,
  periodName: '',
  performanceNo: '',
  nickName: '',
  appealReason: '',
  appealStatus: '',
  handleNickName: '',
  processComment: '',
  processResult: '',
  ifAdjustScore: 0,
  adjustScore: null,
  appealTime: null,
  processTime: null
})

watch(
  () => props.rowData,
  (newVal) => {
    if (newVal && newVal.id) {
      // 获取申诉详情
      getAppeal(newVal.id).then(response => {
        if (response.code === 200) {
          Object.assign(detailData, response.data)
        }
      })
    }
  },
  { immediate: true }
)

function handleClose() {
  visible.value = false
}

defineExpose({
  handleClose
})
</script>
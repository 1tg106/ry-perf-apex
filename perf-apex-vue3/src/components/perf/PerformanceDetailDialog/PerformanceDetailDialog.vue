<template>
  <div>
    <!-- 绩效详情弹窗 -->
    <el-dialog
      v-model="dialogVisible"
      title="绩效详情"
      width="90%"
      top="5vh"
      class="performance-detail-dialog"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <div class="dialog-content">
        <el-tabs v-model="activeTab" class="detail-tabs">
          <!-- 基本信息 Tab -->
          <el-tab-pane label="基本信息" name="basic">
            <el-descriptions :column="2" border>
              <el-descriptions-item label="绩效编号">{{ performanceData.performanceNo }}</el-descriptions-item>
              <el-descriptions-item label="绩效周期">{{ performanceData.periodName }}</el-descriptions-item>
              <el-descriptions-item label="模板">{{ performanceData.templateName }}</el-descriptions-item>
              <el-descriptions-item label="人员">{{ performanceData.nickName }}</el-descriptions-item>
              <el-descriptions-item label="部门">{{ performanceData.deptName }}</el-descriptions-item>
              <el-descriptions-item label="状态">
                <el-tag v-if="performanceData.status === PERFORMANCE_STATUS.DRAFT">{{ PERFORMANCE_STATUS_LIST[0].label }}</el-tag>
                <el-tag v-if="performanceData.status === PERFORMANCE_STATUS.PENDING_SUBMISSION">{{ PERFORMANCE_STATUS_LIST[1].label }}</el-tag>
                <el-tag v-if="performanceData.status === PERFORMANCE_STATUS.PENDING_SCORE">{{ PERFORMANCE_STATUS_LIST[2].label }}</el-tag>
                <el-tag type="warning" v-if="performanceData.status === PERFORMANCE_STATUS.PENDING_AUDIT">{{ PERFORMANCE_STATUS_LIST[3].label }}</el-tag>
                <el-tag type="success" v-if="performanceData.status === PERFORMANCE_STATUS.CONFIRMED">{{ PERFORMANCE_STATUS_LIST[4].label }}</el-tag>
                <el-tag type="danger" v-if="performanceData.status === PERFORMANCE_STATUS.REJECTED">{{ PERFORMANCE_STATUS_LIST[5].label }}</el-tag>
                <el-tag type="danger" v-if="performanceData.status === PERFORMANCE_STATUS.APPEAL">{{ PERFORMANCE_STATUS_LIST[6].label }}</el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="自评总分">{{ performanceData.selfScore }}</el-descriptions-item>
              <el-descriptions-item label="最终得分">{{ performanceData.finalScore }}</el-descriptions-item>
              <el-descriptions-item label="提交时间">{{ parseTime(performanceData.submitTime) }}</el-descriptions-item>
              <el-descriptions-item label="评分时间">{{ parseTime(performanceData.scoringTime) }}</el-descriptions-item>
              <el-descriptions-item label="确认时间">{{ parseTime(performanceData.confirmTime) }}</el-descriptions-item>
              <el-descriptions-item label="创建时间">{{ parseTime(performanceData.createTime) }}</el-descriptions-item>
              <el-descriptions-item label="备注">{{ performanceData.remark || '-' }}</el-descriptions-item>
              <el-descriptions-item label="驳回原因" :span="2" v-show="performanceData.rejectReason">{{ performanceData.rejectReason || '-' }}</el-descriptions-item>
            </el-descriptions>
          </el-tab-pane>

          <!-- 指标明细 Tab -->
          <el-tab-pane label="指标明细" name="indicators"> 
            <div class="main-content">
              <!-- 指标树 -->
              <div class="indicator-tree">
                <h3>绩效指标</h3>
                <el-input
                  v-model="filterText"
                  placeholder="搜索指标"
                  :prefix-icon="Search"
                  clearable
                  style="margin-bottom: 15px;"
                />
                <el-tree
                  ref="treeRef"
                  :data="indicators"
                  :props="treeProps"
                  :filter-node-method="filterNode"
                  node-key="itemId"
                  highlight-current
                  :expand-on-click-node="false"
                  @node-click="handleNodeClick"
                >
                  <template #default="{ node, data }">
                    <div class="custom-tree-node">
                      <span class="tree-node-title">{{ node.label }}</span>
                      <span>
                        <el-tag 
                          :type="getItemTypeTagType(data.itemType)"
                          size="small"
                          class="item-type-tag"
                        >
                          {{ getItemTypeText(data.itemType) }}
                        </el-tag>
                      </span>
                    </div>
                  </template>
                </el-tree>
              </div>

              <!-- 指标详情 -->
              <div class="indicator-detail">
                <div v-if="currentItem" class="detail-content">
                  <el-card shadow="never">
                    <template #header>
                      <div class="card-header">
                        <span>{{ currentItem.itemName }}</span>
                        <el-tag :type="getItemTypeTagType(currentItem.itemType)" size="small">
                          {{ getItemTypeText(currentItem.itemType) }}
                        </el-tag>
                      </div>
                    </template>
                    
                    <el-form label-width="100px" label-position="left">
                      <el-form-item label="权重" class="mb-2">
                        <div>{{ currentItem.weight }}%</div>
                      </el-form-item>
                      
                      <el-form-item label="目标" class="mb-2">
                        <div>{{ currentItem.selfTarget || '-' }}</div>
                      </el-form-item>
                      
                      <el-form-item label="成果" class="mb-2">
                        <div>{{ currentItem.selfResult || '-' }}</div>
                      </el-form-item>
                      
                      <el-form-item label="自评分数" class="mb-2">
                        <div style="color: #67c23a;font-weight: bold;">{{ currentItem.selfScore }}</div>
                        <span style="margin-left: 10px; color: #909399;">
                          评分范围: {{ currentItem.minScore }} - {{ currentItem.maxScore }}
                        </span>
                      </el-form-item>
                      
                      <el-form-item label="自评评语" class="mb-2">
                        <div>{{ currentItem.selfComment || '-' }}</div>
                      </el-form-item>

                      <el-form-item label="最终得分" class="mb-2">
                        <div v-if="currentItem.finalScore" style="color: #67c23a;font-weight: bold;">{{ currentItem.finalScore }}</div>
                        <div v-else>评分完成后计算</div>
                      </el-form-item>

                      <el-form-item v-if="currentItem.scoreStandard" label="评分标准">
                        <div style="padding: 10px; background-color: #f5f7fa; border-radius: 4px;">
                          {{ currentItem.scoreStandard }}
                        </div>
                      </el-form-item>
                      
                      <el-divider />
                      
                      <!-- 评分部分 -->
                      <div v-for="(scoreItem, index) in currentItem.perfContentScoreVOList" :key="index">
                        <el-form-item label="评分人" class="mb-1">
                          <div>{{ scoreItem.scoreUserName }}</div>
                          <el-tag 
                            style="margin-left: 20px;"
                            :type="scoreItem.ifScore?'success':'pending'"
                            size="small"
                          >
                            {{ scoreItem.ifScore?'已评分':'未评分' }}
                          </el-tag>
                        </el-form-item>

                        <el-form-item label="评分" class="mb-1" v-if="scoreItem.ifScore">
                          <div style="color: #67c23a;font-weight: bold;">{{ scoreItem.score || '-' }}</div>
                        </el-form-item>
                        
                        <el-form-item label="评语" class="mb-1" v-if="scoreItem.ifScore">
                          <div>{{ scoreItem.remark || '-' }}</div>
                        </el-form-item>
                        
                        
                        
                        <el-form-item label="评分时间" class="mb-1" v-if="scoreItem.ifScore">
                          <div>{{ scoreItem.updateTime }}</div>
                        </el-form-item>
                        
                        <el-divider v-if="index < currentItem.perfContentScoreVOList.length - 1" />
                      </div>
                      
                      
                    </el-form>
                  </el-card>
                </div>
                
                <div v-else class="empty-state">
                  <el-icon size="48"><Document /></el-icon>
                  <p>请从左侧选择一项绩效指标查看详情</p>
                </div>
              </div>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="closeDialog">关闭</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, watch, nextTick } from 'vue'
import { ElMessage } from 'element-plus'
import { Document, Search } from '@element-plus/icons-vue'
import { getPerformance } from '@/api/perf/performance'
import { PERFORMANCE_STATUS, PERFORMANCE_STATUS_LIST, PERFORMANCE_STEP_STATUS, PERFORMANCE_STEP_STATUS_LIST } from '@/utils/perf/performanceEnum'
import { parseTime } from '@/utils/ruoyi'

export default {
  name: 'PerformanceDetailDialog',
  setup() {
    const dialogVisible = ref(false)
    const activeTab = ref('basic')
    const filterText = ref('')
    const treeRef = ref()
    const currentItem = ref(null)
    
    // 绩效数据
    const performanceData = ref({})
    
    // 树配置
    const treeProps = {
      children: 'children',
      label: 'itemName'
    }
    
    // 指标数据
    const indicators = ref([])
    
    // 获取指标类型标签样式
    const getItemTypeTagType = (type) => {
      const typeMap = {
        'OBJECTIVE': '',
        'KEY_RESULT': 'success',
        'COMPETENCY': 'warning'
      }
      return typeMap[type] || 'info'
    }
    
    // 获取指标类型文本
    const getItemTypeText = (type) => {
      const typeMap = {
        'OBJECTIVE': '目标',
        'KEY_RESULT': '关键成果',
        'COMPETENCY': '能力项'
      }
      return typeMap[type] || type
    }
    
    // 获取步骤名称
    const getStepName = (step) => {
      const stepMap = {
        1: '直属上级',
        2: '部门负责人',
        3: 'HR确认'
      }
      return stepMap[step] || `步骤${step}`
    }
    
    // 树节点过滤方法
    const filterNode = (value, data) => {
      if (!value) return true
      return data.itemName.includes(value)
    }
    
    // 处理树节点点击
    const handleNodeClick = (data) => {
      // 只允许选择叶子节点（没有子节点的节点）
      if (!data.children || data.children.length === 0) {
        currentItem.value = data
      }
    }
    
    // 打开弹窗
    const openDialog = async (performanceId) => {
      dialogVisible.value = true
      
      // 如果传入了绩效ID，则从API获取数据
      if (performanceId) {
        try {
          const response = await getPerformance(performanceId)
          performanceData.value = response.data || {}
          indicators.value = response.data.perfContentVOList || []
          
          // 默认选中第一个叶子节点
          nextTick(() => {
            // 查找第一个叶子节点
            const findFirstLeaf = (nodes) => {
              for (const node of nodes) {
                if (!node.children || node.children.length === 0) {
                  return node
                }
                const leaf = findFirstLeaf(node.children)
                if (leaf) return leaf
              }
              return null
            }
            
            const firstLeaf = findFirstLeaf(indicators.value)
            if (firstLeaf) {
              currentItem.value = firstLeaf
              // 设置树节点选中状态
              treeRef.value.setCurrentKey(firstLeaf.itemId)
            }
          })
        } catch (error) {
          ElMessage.error('获取绩效详情失败: ' + error.message)
          indicators.value = []
        }
      }
    }
    
    // 关闭弹窗
    const closeDialog = () => {
      dialogVisible.value = false
      // 重置数据
      performanceData.value = {}
      indicators.value = []
      currentItem.value = null
      activeTab.value = 'basic'
    }
    
    // 监听搜索文本变化
    watch(filterText, (val) => {
      treeRef.value.filter(val)
    })
    
    return {
      dialogVisible,
      activeTab,
      filterText,
      treeRef,
      currentItem,
      performanceData,
      PERFORMANCE_STATUS,
      PERFORMANCE_STATUS_LIST,
      PERFORMANCE_STEP_STATUS,
      PERFORMANCE_STEP_STATUS_LIST,
      indicators,
      treeProps,
      Document,
      Search,
      getItemTypeTagType,
      getItemTypeText,
      getStepName,
      filterNode,
      handleNodeClick,
      openDialog,
      closeDialog,
      parseTime
    }
  }
}
</script>

<style scoped>
.performance-detail-dialog {
  max-height: 80vh;
  display: flex;
  flex-direction: column;
}

.dialog-content {
  flex: 1;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.detail-tabs {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.detail-tabs :deep(.el-tabs__content) {
  flex: 1;
  overflow: auto;
}

.main-content {
  display: flex;
  height: 100%;
  min-height: 400px;
}

.indicator-tree {
  width: 300px;
  border-right: 1px solid #ebeef5;
  padding-right: 15px;
  overflow-y: auto;
}

.indicator-tree h3 {
  margin-top: 0;
}

.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}

.tree-node-title {
  flex: 1;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.item-type-tag {
  margin-left: 8px;
}

.indicator-detail {
  flex: 1;
  padding-left: 15px;
  overflow-y: auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.detail-content {
  height: 100%;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  color: #909399;
}

.empty-state p {
  margin-top: 10px;
}
</style>
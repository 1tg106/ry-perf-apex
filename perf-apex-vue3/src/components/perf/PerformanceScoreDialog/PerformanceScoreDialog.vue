<template>
  <div>
    <!-- 绩效评分弹窗 -->
    <el-dialog
      v-model="dialogVisible"
      title="绩效评分"
      width="90%"
      top="5vh"
      class="performance-score-dialog"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <div class="dialog-content">
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
                    <span class="tree-node-weight">{{ data.weight }}%</span>
                  </span>
                </div>
              </template>
            </el-tree>
          </div>
          
          <!-- 表单区域 -->
          <div class="form-section">
            <div v-if="currentItem">
              <div class="score-info">
                <div class="score-item">
                  <div class="score-value">{{ currentItem.selfScore || 0 }}</div>
                  <div class="score-label">自评分数</div>
                </div>
                <div class="score-item">
                  <div class="score-value">{{ currentItem.finalScore || 0 }}</div>
                  <div class="score-label">最终评分</div>
                </div>
                <div class="score-item">
                  <div class="score-value">{{ currentItem.weight }}%</div>
                  <div class="score-label">权重</div>
                </div>
                <div class="score-item">
                  <div class="score-value">{{ currentItem.minScore }} - {{ currentItem.maxScore }}</div>
                  <div class="score-label">评分范围</div>
                </div>
              </div>
              
              <el-card class="form-card">
                <template #header>
                  <div style="display: flex; justify-content: space-between; align-items: center;">
                    <span>{{ currentItem.itemName }}</span>
                    <el-tag :type="getItemTypeTagType(currentItem.itemType)">
                      {{ getItemTypeText(currentItem.itemType) }}
                    </el-tag>
                  </div>
                </template>
                
                <el-form :model="currentItem" label-width="100px">
                  <el-form-item label="自评目标">
                    <div>{{ currentItem.selfTarget }}</div>
                  </el-form-item>
                  
                  <el-form-item label="自评成果">
                    <div>{{ currentItem.selfResult }}</div>
                  </el-form-item>
                  
                  <el-form-item label="自评分数">
                    <div>{{ currentItem.selfScore }}</div>
                    <span style="margin-left: 10px; color: #909399;">
                      评分范围: {{ currentItem.minScore }} - {{ currentItem.maxScore }}
                    </span>
                  </el-form-item>
                  
                  <el-form-item label="自评评语">
                    <div>{{ currentItem.selfComment }}</div>
                  </el-form-item>
                  
                  <el-divider />
                  
                  <!-- 评分部分 -->
                  <el-form-item label="评分" required>
                    <el-input-number
                      v-if="performanceData.status == PERFORMANCE_STATUS.PENDING_SCORE"
                      v-model="currentItem.score"
                      :min="currentItem.minScore"
                      :max="currentItem.maxScore"
                      :precision="2"
                      :step="0.5"
                      controls-position="right"
                      style="width: 150px;"
                    />
                    <div v-else>{{ currentItem.score }}</div>
                    <span style="margin-left: 10px; color: #909399;">
                      评分范围: {{ currentItem.minScore }} - {{ currentItem.maxScore }}
                    </span>
                  </el-form-item>
                  
                  <el-form-item label="评语">
                    <el-input
                      v-if="performanceData.status == PERFORMANCE_STATUS.PENDING_SCORE"
                      v-model="currentItem.remark"
                      type="textarea"
                      :rows="2"
                      placeholder="请输入评分评语"
                      maxlength="50"
                      show-word-limit
                    />
                    <div v-else>{{ currentItem.remark?currentItem.remark:'-' }}</div>
                  </el-form-item>
                  
                  <el-form-item v-if="currentItem.scoreStandard" label="评分标准">
                    <div style="padding: 10px; background-color: #f5f7fa; border-radius: 4px;">
                      {{ currentItem.scoreStandard }}
                    </div>
                  </el-form-item>
                </el-form>
              </el-card>
            </div>
            
            <div v-else class="empty-state">
              <el-icon size="48"><Document /></el-icon>
              <p>请从左侧选择一项绩效指标进行评分</p>
            </div>
          </div>
        </div>
      </div>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="closeDialog">{{performanceData.status == PERFORMANCE_STATUS.PENDING_SCORE?'取消':'关闭'}}</el-button>
          <el-button type="primary" v-if="performanceData.status == PERFORMANCE_STATUS.PENDING_SCORE" @click="submitScore">提交评分</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, watch, nextTick } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Document, Search } from '@element-plus/icons-vue'
import { getScore, submitScoreBatch } from '@/api/perf/score'
import { PERFORMANCE_STATUS } from '@/utils/perf/performanceEnum'


export default {
  name: 'PerformanceScoreDialog',
  emits: ['submit'],
  setup(props, { emit }) {
    const dialogVisible = ref(false)
    const filterText = ref('')
    const treeRef = ref()
    const currentItem = ref(null)
    const performanceData = ref({
      status: null
    })
    
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
          const response = await getScore(performanceId)
          performanceData.value = response.data || {}
          indicators.value = response.data.perfScoreItemVOList || []
          console.log(performanceData.value);
          
        } catch (error) {
          ElMessage.error('获取绩效评分详情失败: ' + error.message)
          indicators.value = []
        }
      }
      
      // 延迟设置默认选中项
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
    }
    
    // 关闭弹窗
    const closeDialog = () => {
      dialogVisible.value = false
    }
    
    // 提交评分
    const submitScoreFunc = async () => {
      try {
        // 检查是否已评分
        if (currentItem.value.score === null || currentItem.value.score === undefined) {
          ElMessage.warning('请先填写评分')
          return
        }
        
        await ElMessageBox.confirm(
          '确定提交评分吗？提交后将无法修改。',
          '提交确认',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
        )
        
        // 调用提交评分的API
        await submitScoreBatch(indicators.value)
        
        ElMessage.success('评分提交成功')
        // 关闭弹窗并通知父组件
        closeDialog()
        // 触发提交事件，将数据传递给父组件
        emit('submit', performanceData.value)
      } catch (error) {
        if (error !== 'cancel') {
          console.error(error)
          // ElMessage.error('评分提交失败: ' + error.message)
        }
      }
    }
    
    // 监听搜索文本变化
    watch(filterText, (val) => {
      treeRef.value.filter(val)
    })
    
    return {
      dialogVisible,
      filterText,
      treeRef,
      currentItem,
      performanceData,
      PERFORMANCE_STATUS,
      indicators,
      treeProps,
      Document,
      Search,
      getItemTypeTagType,
      getItemTypeText,
      filterNode,
      handleNodeClick,
      openDialog,
      closeDialog,
      submitScore: submitScoreFunc
    }
  }
}
</script>

<style scoped>
.performance-score-dialog {
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

.main-content {
  flex: 1;
  display: flex;
  gap: 20px;
  overflow: hidden;
}

.indicator-tree {
  flex: 0 0 300px;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  padding: 15px;
  background-color: #fafafa;
  overflow-y: auto;
}

.form-section {
  flex: 1;
  overflow-y: auto;
}

.score-info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
  padding: 15px;
  background-color: #f0f9ff;
  border-radius: 4px;
}

.score-item {
  text-align: center;
}

.score-value {
  font-size: 24px;
  font-weight: bold;
  color: #409eff;
}

.score-label {
  font-size: 14px;
  color: #909399;
}

.form-card {
  margin-bottom: 20px;
}

.empty-state {
  text-align: center;
  padding: 40px 0;
  color: #909399;
}

.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  padding-right: 8px;
  min-width: 0;
}

.tree-node-title {
  font-weight: 500;
  flex: 1;
  min-width: 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.tree-node-weight {
  color: #67c23a;
  font-size: 12px;
}

.item-type-tag {
  margin-left: 8px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>
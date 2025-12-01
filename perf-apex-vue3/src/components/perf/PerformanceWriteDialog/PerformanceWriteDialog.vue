<template>
  <div>
    <!-- 触发弹窗的按钮 -->
    <el-button type="primary" @click="openDialog">填写绩效</el-button>

    <!-- 绩效填写弹窗 -->
    <el-dialog
      v-model="dialogVisible"
      title="绩效填写"
      width="90%"
      top="5vh"
      class="performance-dialog"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
    >
      <div class="dialog-content">
        <div class="header">
          <h2>绩效自评填写</h2>
          <div class="header-actions">
            <el-button @click="saveDraft" :icon="Document">保存草稿</el-button>
          </div>
        </div>
        
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
              node-key="id"
              highlight-current
              :expand-on-click-node="false"
              @node-click="handleNodeClick"
            >
              <template #default="{ node, data }">
                <div class="custom-tree-node">
                  <span class="tree-node-title">{{ node.label }}</span>
                  <span>
                    <el-tag 
                      v-if="data.itemType !== 'OBJECTIVE'" 
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
                    <el-tag :type="getItemTypeTagType(currentItem.itemType)" effect="dark">
                      {{ getItemTypeText(currentItem.itemType) }}
                    </el-tag>
                  </div>
                </template>
                
                <el-form :model="currentItem" label-width="100px">
                  <el-form-item label="自评目标">
                    <el-input
                      v-model="currentItem.selfTarget"
                      type="textarea"
                      :rows="3"
                      placeholder="请填写自评目标"
                      maxlength="500"
                      show-word-limit
                    />
                  </el-form-item>
                  
                  <el-form-item label="自评成果">
                    <el-input
                      v-model="currentItem.selfResult"
                      type="textarea"
                      :rows="3"
                      placeholder="请填写自评成果"
                      maxlength="500"
                      show-word-limit
                    />
                  </el-form-item>
                  
                  <el-form-item label="自评分数">
                    <el-input-number
                      v-model="currentItem.selfScore"
                      :min="currentItem.minScore"
                      :max="currentItem.maxScore"
                      :precision="2"
                      :step="0.5"
                      controls-position="right"
                      style="width: 150px;"
                    />
                    <span style="margin-left: 10px; color: #909399;">
                      评分范围: {{ currentItem.minScore }} - {{ currentItem.maxScore }}
                    </span>
                  </el-form-item>
                  
                  <el-form-item label="自评评语">
                    <el-input
                      v-model="currentItem.selfComment"
                      type="textarea"
                      :rows="3"
                      placeholder="请填写自评评语"
                      maxlength="500"
                      show-word-limit
                    />
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
              <p>请从左侧选择一项绩效指标进行填写</p>
            </div>
          </div>
        </div>
      </div>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="closeDialog">取消</el-button>
          <el-button type="primary" @click="saveDraft">保存草稿</el-button>
          <el-button type="success" @click="submitPerformance">提交绩效</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, watch, nextTick } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Document, Search, DocumentAdd } from '@element-plus/icons-vue'

export default {
  name: 'PerformanceDialog',
  props: {
    // 可以接收外部传入的绩效数据
    performanceData: {
      type: Array,
      default: () => []
    }
  },
  setup(props) {
    const dialogVisible = ref(false)
    const filterText = ref('')
    const treeRef = ref()
    const currentItem = ref(null)
    
    // 树配置
    const treeProps = {
      children: 'children',
      label: 'itemName'
    }
    
    // 模拟指标数据
    const indicators = ref([
      {
        id: 1,
        itemName: '工作业绩',
        itemType: 'OBJECTIVE',
        parentId:0,
        weight: 60,
        minScore: 0,
        maxScore: 100,
        scoreStandard: '根据工作完成情况、质量、效率等综合评定',
        children: [
          {
            id: 2,
            itemName: '任务完成率',
            itemType: 'KEY_RESULT',
            parentId:1,
            weight: 30,
            minScore: 0,
            maxScore: 100,
            scoreStandard: '完成率100%得满分，每降低5%扣10分',
            selfTarget: '',
            selfResult: '',
            selfScore: 0,
            selfComment: '',
            finalScore: 0,
            finalComment: ''
          },
          {
            id: 3,
            itemName: '工作质量',
            itemType: 'KEY_RESULT',
            parentId:1,
            weight: 20,
            minScore: 0,
            maxScore: 100,
            scoreStandard: '根据工作质量、错误率等评定',
            selfTarget: '',
            selfResult: '',
            selfScore: 0,
            selfComment: '',
            finalScore: 0,
            finalComment: ''
          },
          {
            id: 4,
            itemName: '工作效率',
            itemType: 'KEY_RESULT',
            parentId:1,
            weight: 10,
            minScore: 0,
            maxScore: 100,
            scoreStandard: '根据任务完成时效评定',
            selfTarget: '',
            selfResult: '',
            selfScore: 0,
            selfComment: '',
            finalScore: 0,
            finalComment: ''
          }
        ]
      },
      {
        id: 5,
        itemName: '能力素质',
        itemType: 'OBJECTIVE',
        parentId:0,
        weight: 40,
        minScore: 0,
        maxScore: 100,
        scoreStandard: '根据专业能力、团队协作、学习能力等评定',
        children: [
          {
            id: 6,
            itemName: '专业技能',
            itemType: 'COMPETENCY',
            parentId:5,
            weight: 15,
            minScore: 0,
            maxScore: 100,
            scoreStandard: '根据专业知识的掌握和应用能力评定',
            selfTarget: '',
            selfResult: '',
            selfScore: 0,
            selfComment: '',
            finalScore: 0,
            finalComment: ''
          },
          {
            id: 7,
            itemName: '团队协作',
            itemType: 'COMPETENCY',
            parentId:5,
            weight: 15,
            minScore: 0,
            maxScore: 100,
            scoreStandard: '根据团队合作、沟通能力等评定',
            selfTarget: '',
            selfResult: '',
            selfScore: 0,
            selfComment: '',
            finalScore: 0,
            finalComment: ''
          },
          {
            id: 8,
            itemName: '学习成长',
            itemType: 'COMPETENCY',
            parentId:5,
            weight: 10,
            minScore: 0,
            maxScore: 100,
            scoreStandard: '根据学习能力、知识更新等评定',
            selfTarget: '',
            selfResult: '',
            selfScore: 0,
            selfComment: '',
            finalScore: 0,
            finalComment: ''
          }
        ]
      }
    ])
    
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
    const openDialog = () => {
      dialogVisible.value = true
      // 如果有外部传入的数据，使用外部数据
      if (props.performanceData && props.performanceData.length > 0) {
        indicators.value = props.performanceData
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
          treeRef.value.setCurrentKey(firstLeaf.id)
        }
      })
    }
    
    // 关闭弹窗
    const closeDialog = () => {
      dialogVisible.value = false
      // 可以添加关闭前的确认逻辑
    }
    
    // 保存草稿
    const saveDraft = () => {
      ElMessage.success('绩效已保存为草稿')
      // 这里可以触发保存事件，将数据传递给父组件
      // emit('save', indicators.value)
    }
    
    // 提交绩效
    const submitPerformance = async () => {
      try {
        await ElMessageBox.confirm(
          '提交后绩效将无法修改，确定提交吗？',
          '提交确认',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
        )
        ElMessage.success('绩效提交成功')
        // 这里可以触发表单提交事件
        // emit('submit', indicators.value)
        closeDialog()
      } catch (error) {
        // 用户取消提交
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
      indicators,
      treeProps,
      Document,
      Search,
      DocumentAdd,
      getItemTypeTagType,
      getItemTypeText,
      filterNode,
      handleNodeClick,
      openDialog,
      closeDialog,
      saveDraft,
      submitPerformance
    }
  }
}
</script>

<style scoped>
.performance-dialog {
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

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid #ebeef5;
}

.header h2 {
  margin: 0;
  color: #303133;
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
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}

.tree-node-title {
  font-weight: 500;
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
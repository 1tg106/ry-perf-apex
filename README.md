# PerfApex

PerfApex：结合Performance（绩效）和Apex（顶点），寓意助力员工与团队攀登绩效顶峰。

- 基于 RuoYi 3.9（Spring Boot + Spring Security + MyBatis-plus + Redis）与 Vue3 + Element Plus + Vite 的前后端分离绩效管理模块。
- 核心业务：绩效周期、模板与指标、绩效实例（自评/评分/审核）、绩效面谈、绩效申诉、统计看板与待办。
- 适用场景：企业 OKR/KPI/胜任力综合考核，支持多岗位、分权评分与申诉闭环。
- 非常的简单易用

## 功能预览

### 首页
![](./docs/image/2_home.png)

### 绩效指标
![](./docs/image/5_target.png)
 
### 绩效填写
![](./docs/image/7_performance_fill.png)

### 绩效评分
![](./docs/image/9_performance_score.png)

### 处理申述

![](./docs/image/11_appeal.png)


### 绩效面谈
![](./docs/image/10_perf_interview.png)



## 技术与运行环境
- 后端：JDK 8+、Maven、MySQL、Redis。
- 前端：Vue3 + Element Plus + Vite，代码在 `perf-apex-vue3/`。
- 快速启动
  - 初始化库表：导入 `sql/ry-perf-apex1.0.sql`（包含系统与绩效表）。
  - 配置数据库与 Redis：`ruoyi-admin/src/main/resources/application-druid.yml`、`application.yml`（按需修改）。
  - 后端启动：`mvn clean package -DskipTests` 后运行 `ruoyi-admin`（可用 `ry.bat`/`ry.sh` 或 `java -jar ruoyi-admin/target/*.jar`）。
  - 前端启动：进入 `perf-apex-vue3`，执行 `yarn install --registry=https://registry.npmmirror.com`后，再执行`npm run dev`或`yarn dev`。

## 目录速览
- 后端
  - 控制层：`ruoyi-admin/src/main/java/com/ruoyi/web/controller/perf/`
  - 领域与服务：`ruoyi-system/src/main/java/com/ruoyi/perf/`（domain/mapper/service）
  - Mapper：`ruoyi-system/src/main/resources/mapper/perf/`
- 前端
  - API：`perf-apex-vue3/src/api/perf/`
  - 视图：`perf-apex-vue3/src/views/perf/`、`src/views/myPerf/`
  - 绩效组件：`perf-apex-vue3/src/components/perf/`
  - 绩效工具/枚举：`perf-apex-vue3/src/utils/perf/`

## 前后端主要功能
- 周期管理：创建周期、设定提交/评分截止与状态流转，提供选择列表 `GET /perf/period/getPerfChooseList`。
- 模板与指标：支持模板分页、导出、增删改查，指标树配置权重与评分人，用于生成绩效内容。
- 绩效实例
  - 管理端：增删改查、导出、按部门/周期/模板筛选。
  - 员工端：`/perf/performance/myList` 查询本人绩效；自评提交、修改草稿；状态驱动评分与审核。
  - 审核与确认：`PUT /perf/performance/auditPerformance` 审核，`handleConfirm/handleReject`（前端）触发确认/驳回。
- 评分流程：每个指标可配置评分人，评分数据存于内容评分表；状态枚举见前端 `performanceEnum.js`（草稿、待提交、待评分、待审核、已确认、驳回、作废、申诉中）。
```5:48:perf-apex-vue3/src/utils/perf/performanceEnum.js
export const PERFORMANCE_STATUS = {
  DRAFT: 'DRAFT',
  PENDING_SUBMISSION: 'PENDING_SUBMISSION',
  PENDING_SCORE: 'PENDING_SCORE',
  PENDING_AUDIT: 'PENDING_AUDIT',
  CONFIRMED: 'CONFIRMED',
  REJECTED: 'REJECTED',
  CANCELLATION: 'CANCELLATION',
  APPEAL: 'APPEAL',
}
```
- 绩效面谈：`/perf/interview` 增删改查；`/relevanceList` 关联绩效/人员信息；支持详情查询。
- 绩效申诉：员工提交申诉 `/perf/appeal`，处理接口 `/perf/appeal/handle` 支持意见、结果与分数调整。
- 统计与待办：`/perf/stat/statCards` 获取周期/完成/申诉/面谈数量；`/getPerformanceScoreDistributeList` 得分分布；`/waitMyScorePerformanceList` 查询待评分列表；`/getPerfPeriodList` 获取进行中的周期概览。

## 典型业务流程
1) 周期管理员创建绩效周期（提交/评分截止、状态=准备中→进行中）。
2) HR 创建绩效模板与指标树，配置默认评分人、权重与评分标准。
3) 管理员为员工生成绩效实例（绑定周期+模板+部门+员工）。
4) 员工自评：填写各指标目标与成果，提交后进入待评分。
5) 评分人按指标打分并提交，系统计算自评/最终分。
6) 审核人确认或驳回；驳回可退回修改，确认后锁定成绩。
7) 面谈记录：按绩效发起面谈，记录反馈与行动计划。
8) 申诉处理：员工在确认或评分后提交申诉，处理人给出结果并可调整得分，状态同步至绩效实例。
9) 统计看板展示周期进度、得分分布、申诉与面谈情况，待办列表提醒评分与处理。
10) 业务流转图

![](./docs/image/1_flow_chart.png)



## 前端页面入口（默认通过菜单/权限下发）
- 绩效周期：`views/perf/period`
- 绩效模板：`views/perf/template`，指标维护：`views/perf/templateItem`
- 绩效实例：`views/perf/performance`；员工侧入口 `views/myPerf`
- 评分/填写：`components/perf/PerformanceWriteDialog`、`PerformanceScoreDialog`
- 申诉管理：`views/perf/appeal`
- 面谈管理：`views/perf/interview`
- 统计看板：`views/perf/stat`

## 权限与接口约定
- 统一前缀 `/perf/**`，遵循 RuoYi 权限注解 `@PreAuthorize("@ss.hasPermi('perf:xxx:op')")`，需在菜单中为角色分配按钮权限。
- 分页查询采用后台 `startPage()` + `TableDataInfo` 响应，前端表格统一使用分页组件。

## 核心数据模型
- 绩效周期 `PerfPeriod`：定义周期名称、类型（月/季/半年/年）、提交与评分截止、状态（准备中/进行中/已结束）。
```22:45:ruoyi-system/src/main/java/com/ruoyi/perf/domain/PerfPeriod.java
    /** 周期名称(如：2024年Q1) */
    private String periodName;
    /** 周期类型(MONTH:月度, QUARTER:季度, HALF_YEAR:半年度, YEAR:年度) */
    private String periodType;
    /** 提交截止时间 */
    private Date submitDeadline;
    /** 状态(0:准备中 1:进行中 2:已结束) */
    private String status;
```
- 绩效模板与指标 `PerfTemplate` / `PerfTemplateItem`：模板类型（KPI/OKR/COMPETENCY）、适用部门岗位、默认评分人，指标树含权重、评分标准、评分人。
```22:47:ruoyi-system/src/main/java/com/ruoyi/perf/domain/PerfTemplate.java
    private Long id;
    private String templateName;
    private String templateType;
    private Long defaultScoreId;
    private Long deptId;
```
- 绩效实例 `PerfPerformance`：绑定周期/模板/员工/部门，记录自评分、最终分、当前步骤、状态（草稿/待提交/待评分/待审核/已确认/驳回/作废/申诉中），含提交/确认/评分/审核时间。
```24:84:ruoyi-system/src/main/java/com/ruoyi/perf/domain/PerfPerformance.java
    private String performanceNo;
    private Long periodId;
    private Long templateId;
    private Long userId;
    private BigDecimal selfScore;
    private BigDecimal finalScore;
    private String status;
```
- 绩效内容与评分：`PerfPerformanceContent`（自评目标/成果/分数、最终分与评语，按模板指标展开）；评分人列表与得分在 `perf_performance_content_score`（mapper/service 同目录）。
- 面谈 `PerfInterview`：面谈人/被访人、时间、要点、改进与行动计划、反馈，支持标记已面谈。
- 申诉 `PerfAppeal`：申诉理由、处理意见与结果，可调整分数，状态（待处理/已处理/已驳回）。

## 二次开发指引
- 新增评分角色或步骤：扩展绩效状态枚举、内容评分表与状态机（service 层）；同步更新前端 `performanceEnum.js`。
- 自定义计算公式：在 `PerfPerformanceServiceImpl` 中调整评分汇总逻辑，并扩展 mapper 统计字段。
- 单点扩展：遵循 RuoYi 代码生成规范，Controller->Service->Mapper->XML，全链路已有样例可参考现有绩效文件。
- 前端保持与后端字段对齐，公共请求封装在 `src/api/perf/*`，复用通用表格/弹窗组件。

## 版权与开源协议
- 项目遵循 MIT License（见根目录 `LICENSE`）。使用与二次分发请保留原作者版权声明。



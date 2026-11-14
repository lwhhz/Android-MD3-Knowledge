# 术语表

> 核验日期：2026-11-14

## 设计系统

| 术语 | 中文说明 |
|---|---|
| Material 3 / M3 | Google 第三代 Material Design 设计系统 |
| Material You | Android 12 起的个性化方向，动态取色是其主要载体 |
| Material 3 Expressive | M3 的表现力扩展：更强动效、更大形状变化、扩展字阶与新组件 |
| Design token | 可复用设计令牌，如颜色角色、字体样式、形状 |
| Color role | 语义化颜色角色，如 `primary`、`surface`、`onSurface` |
| Tonal palette | 由种子色生成的 0-100 明度色阶，角色从中取色 |
| Surface container | 五级表面色阶，用颜色而非阴影表达层级 |
| Dynamic color | 从系统壁纸或用户选择生成配色的能力，Android 12+ |
| Scrim | 模态浮层背后的遮罩层 |
| Shape morph | 状态切换时组件形状的连续变化（Expressive 特性） |

## Android 平台

| 术语 | 中文说明 |
|---|---|
| Jetpack Compose | Android 声明式 UI 工具包 |
| Compose BOM | 统一管理 Compose 各 artifact 版本的物料清单 |
| Adaptive layout | 根据可用窗口空间与设备形态调整布局 |
| Window size class | 按窗口宽高划分的尺寸等级（compact / medium / expanded） |
| Window insets | 系统栏、键盘、刘海等系统 UI 占用区域信息 |
| edge-to-edge | 内容绘制到系统栏之下，由应用自行处理 inset |
| Predictive back | 返回手势的预览式反馈，Android 14+ 正式能力 |
| Navigation 3 | 开发者持有返回栈的新一代导航库，支持类型安全深链 |

## Compose 编码

| 术语 | 中文说明 |
|---|---|
| State hoisting | 把状态提升到调用方管理，组件只接收值与回调 |
| UDF / 单向数据流 | 状态向下传递、事件向上回调 |
| Slot API | 通过 composable 参数留出内容插槽的组件设计方式 |
| Recomposition | 状态变化时重新执行 composable 的过程 |
| Stable / Immutable | 供编译器判断能否跳过重组的稳定性标注 |
| Strong skipping | 编译器对不稳定参数也做跳过的优化模式 |
| `@OptIn` | 显式声明使用实验性 API 的注解 |
| Experimental | 标记为 `@Experimental*Api`，可能在版本间变更 |

## 状态与生命周期

| 术语 | 中文说明 |
|---|---|
| `remember` | 在 composition 内部缓存值，重组间保留 |
| `rememberSaveable` | 在上述基础上支持配置变更与进程恢复 |
| `derivedStateOf` | 由其他状态派生的状态，避免重复计算 |
| UiState | 屏幕级不可变状态模型，通常为 data class 或 sealed interface |
| Process death | 进程被系统回收，需靠保存状态恢复 |

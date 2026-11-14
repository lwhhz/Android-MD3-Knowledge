# Progress Indicator / 进度指示

- 官方指南：https://m3.material.io/components/progress-indicators ｜ 规格页：https://m3.material.io/components/progress-indicators/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-15 ｜ 核验方式：Chrome CDP 读官方配置表
- 状态：**配置项与固定数值已核对**；变体尺寸主要为图片

## 先选行为，再选形状

三个维度要分开想：

| 维度 | 取值 |
|---|---|
| **行为** | Determinate（可确定，有百分比）/ Indeterminate（不确定） |
| **形状** | Linear（线性）/ Circular（环形） |
| **厚度** | Fixed 4dp（默认）/ **Variable（Expressive）** |
| **样式** | Flat（默认）/ **Wavy（Expressive）** |

**最容易犯的错**：明明能算出进度却用 indeterminate。用户盯着无限转圈 30 秒，比看到"卡在 80%"更焦虑。

判断：**能拿到总量就用 determinate**；确实不知道要多久（等一个外部回调）才用 indeterminate。

## 配置的代际差异（官方原文）

| 类别 | 配置 | M3 | M3 Expressive |
|---|---|---|---|
| Behavior | Determinate（默认） | ✅ | ✅ |
| Behavior | Indeterminate | ✅ | ✅ |
| Track thickness | Fixed（4dp） | ✅ | ✅ |
| Track thickness | **Configurable** | ❌ | ✅ |
| Shape | Flat（默认） | ✅ | ✅ |
| Shape | **Wavy** | ❌ | ✅ |

所以：**可调厚度和波浪形状只有 Expressive 有**。

## 数值

| 属性 | 值 |
|---|---|
| 默认轨道厚度 | **4dp** |
| 距屏幕边缘内缩 | **4dp** |

第二条容易被忽略：线性进度条**不是贴边**的，左右各内缩 4dp。

> 官网另有一条重要说明：**「The circular and linear progress indicator had separate token sets. These are no longer recommended.」** —— 环形和线性原本各有一套 token，现在已统一，不要再按旧文档分别配置。

## 什么时候用哪个

| 场景 | 建议 |
|---|---|
| 页面首次加载、内容区为空 | Circular indeterminate（居中） |
| 下拉刷新 | Linear indeterminate（顶部） |
| 文件上传 / 下载有百分比 | Linear determinate |
| 表单提交按钮内 | 按钮内的小 Circular（16dp 左右） |
| 长时间任务有阶段 | Determinate + 阶段文案，不要只给环形 |

**进度条不能替代加载文案。** 用户需要知道在等什么，尤其是超过 5 秒的操作。

## Compose 实现

```kotlin
// 确定进度
LinearProgressIndicator(progress = { downloaded / total })

// 不确定进度
CircularProgressIndicator()

// 按钮内的小号
CircularProgressIndicator(
    modifier = Modifier.size(16.dp),
    strokeWidth = 2.dp,
    color = LocalContentColor.current
)
```

注意 `progress` 参数在新版是 **lambda**（`progress = { value }`），旧版是直接传 Float，迁移时容易踩。

Expressive 的波浪进度条是 `WavyProgressIndicator`，**使用前确认当前 material3 版本是否已转正**（见 `02-Components/expressive-components.md`）。

## 无障碍

- determinate 会自动朗读百分比；indeterminate 要说明"正在加载"。
- 给进度条一个可理解的上下文：`Modifier.semantics { contentDescription = "正在上传头像" }`。
- 不要用颜色单独表达状态（成功/失败）——加图标或文案。
- 长时间任务的进度更新要能被读屏感知，但不要每秒播报一次。

## 常见错误

1. 能算进度却用 indeterminate。
2. 只有进度条没有说明文字，用户不知道在等什么。
3. 线性进度条贴边（应内缩 4dp）。
4. 按钮内的 loading 用默认尺寸的 Circular，撑爆按钮。
5. 进度完成后不消失，或直接跳走没有完成反馈。
6. 多个进度条同时出现在一屏（用户不知道在等哪个）。
7. 用颜色表示成功/失败，色盲用户无法分辨。
8. 仍按"环形/线性各一套 token"的旧文档配置（已统一）。

## 来源

- https://m3.material.io/components/progress-indicators/specs
- `02-Components/buttons.md`（按钮内加载态写法）
- `02-Components/expressive-components.md`（WavyProgressIndicator 状态）

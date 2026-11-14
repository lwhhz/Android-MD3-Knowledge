# Loading Indicator / 加载指示（Expressive）

- 官方指南：https://m3.material.io/components/loading-indicator ｜ 规格页：https://m3.material.io/components/loading-indicator/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-15 ｜ 核验方式：Chrome CDP 读官方 Specs 页
- 状态：**M3 无此组件，Expressive 新增**；尺寸为官方原值

## 代际归属

| 变体 | M3 | M3 Expressive |
|---|---|---|
| Loading indicator | ❌ | ✅ |

注意它和 **Progress Indicator 不是同一个东西**：

| | Progress indicator | Loading indicator |
|---|---|---|
| 表达 | 有多少进度（可确定 / 不可确定） | **只是"在处理"，没有进度概念** |
| 形态 | 线性 / 环形 + 轨道 | 一组形状持续 morph |
| 何时用 | 有进度或至少能显示"在转" | 短暂等待、局部处理中 |

所以：**能量化用 progress，纯粹"稍等"用 loading。**

## 配置

官方两种：

| 配置 | 说明 |
|---|---|
| **Default** | 独立成形 |
| **Contained** | 装在一个容器里（如按钮内、卡片内） |

官方还注明：**loading indicator 只有一套 token set**（不像当年 progress indicator 分环形/线性两套）。

## 尺寸（官方原文）

> To ensure sufficient margins, the size is **48dp** while the shape container is **38dp**.

也就是：

| 层级 | 尺寸 |
|---|---|
| 形状容器（可见部分） | **38dp** |
| 占位尺寸（含必要留白） | **48dp** |

这两者的差值是刻意留的**边距**。如果你把布局高度写成 38dp，形状在动画中放大时会溢出或挤压相邻元素——写 48dp 才是安全值。

## 颜色

单套 token，按上下文取色：

- 独立使用时跟随 `primary`
- 在按钮内（Contained）时跟随 `LocalContentColor`，与按钮内容色一致

**不要**自己指定颜色——它通常出现在别的组件内部，硬编码会与宿主冲突。

## Compose 实现

```kotlin
// Contained：放在按钮里
Button(onClick = save, enabled = !loading) {
    if (loading) {
        LoadingIndicator(
            modifier = Modifier.size(48.dp)    // 官方占位尺寸
        )
        Spacer(Modifier.width(8.dp))
        Text("保存中")
    } else {
        Text("保存")
    }
}
```

**给不出完整代码的原因**：该组件的类名与参数在 1.5.0 线仍在演进（`LoadingIndicator` / `ContainedLoadingIndicator` 之类），且是否已从 experimental 转正需按版本核对。用前先查当前版本的 API reference。

如果当前版本还没有它，退路是 `CircularProgressIndicator`（不确定进度）——功能等价，只是视觉朴素。

## 什么时候用哪个

| 场景 | 选择 |
|---|---|
| 文件上传有百分比 | Progress（determinate） |
| 页面首次加载、时长未知 | Progress（indeterminate）或 Loading indicator |
| 按钮内、几百毫秒的处理 | Loading indicator（Contained） |
| 列表项内的局部刷新 | Loading indicator（小尺寸） |

**不要在同一屏同时出现两种加载指示**——用户会以为出了两个问题。

## 无障碍

- 必须有可理解的上下文：`contentDescription = "正在保存"`，不能只转圈。
- 超过 5 秒的等待要给文字说明，不能只靠动画。
- 不要用颜色表达成功/失败（加载失败要切到明确的错误态）。
- 动画本身对读屏无意义，状态要通过语义传递。

## 常见错误

1. 把 loading indicator 当 progress 用（它没有进度概念）。
2. 布局写 38dp 高，形状放大时被裁。
3. 硬编码颜色，放进按钮后与内容色不一致。
4. 一屏多个加载指示。
5. 只有动画没有文案，超过几秒用户开始怀疑卡死。
6. 在 M3（非 Expressive）项目里找它。
7. 加载完成后不消失，或直接跳走没有完成反馈。

## 待核验

- [ ] 当前 material3 版本的确切类名与参数
- [ ] `@ExperimentalMaterial3ExpressiveApi` 是否已移除
- [ ] 形状动画的周期与各阶段时长

## 来源

- https://m3.material.io/components/loading-indicator/specs
- `02-Components/progress-indicator.md`（有进度时用哪个）
- `02-Components/expressive-components.md`（Expressive 组件总览）

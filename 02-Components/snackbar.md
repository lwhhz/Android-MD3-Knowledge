# Snackbar / 轻提示

- 官方指南：https://m3.material.io/components/snackbar ｜ 规格页：https://m3.material.io/components/snackbar/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-14 ｜ 核验方式：Chrome CDP 读官方 Specs 页
- 状态：结构与颜色角色已核对；自动消失时长等行为参数为通行值，见文末

## 用途

Snackbar 在屏幕底部显示**关于应用进程的简短更新**，不打断用户，可以带一个动作。

它和对话框的分工：

| | Snackbar | Dialog |
|---|---|---|
| 打断程度 | 不打断 | 强制打断 |
| 用户必须处理吗 | 不必 | 必须 |
| 典型场景 | 已删除·撤销、已发送、离线 | 不可逆操作确认 |

**判断标准**：如果这个提示用户不看也不会出错，用 Snackbar；必须看才能继续，才用 Dialog。

## 结构（官网 Anatomy）

```
┌──────────────────────────────────────┐
│  文案                [动作]     [✕]  │
└──────────────────────────────────────┘
   ①Supporting text   ②Action   ③Icon
   Container 承载全部
```

| 元素 | 必需 | 说明 |
|---|---|---|
| Container | ✅ | 承载背景 |
| Supporting text | ✅ | 一行或两行 |
| Action | 可选 | 最多**一个**文字动作 |
| Icon | 可选 | 关闭按钮（close affordance） |

**动作只能有一个。** 需要两个动作说明这不是轻提示，该用对话框或页面。

## 颜色角色（官网原文）

| 部位 | 角色 |
|---|---|
| 容器 | `inverseSurface` |
| 文案 | `inverseOnSurface` |
| 图标 | `inverseOnSurface` |
| 动作按钮 | `inversePrimary` |

这就是为什么 Snackbar 在深色主题下看起来是"浅色浮层"——它用的是**反色**角色，不是普通 surface。

**注意**：Snackbar 不出现在官方的 elevation 分级表里。它不是靠 elevation 抬起来的，是靠反色对比。

## 配置形态（官网列出五种）

单行 · 单行带动作 · 双行 · 双行带动作 · 双行带较长动作

规则：
- 文案**最多两行**，超过两行要改写或改跳转页面。
- 有动作时文案要相应变短，给按钮留出空间。
- 动作文案用动词：「撤销」「查看」「重试」，不要「确定」。

## Compose 实现

```kotlin
val snackbarHostState = remember { SnackbarHostState() }

Scaffold(
    snackbarHost = {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.navigationBarsPadding()
        )
    }
) { padding ->
    // content
}
```

显示一条可撤销的提示：

```kotlin
scope.launch {
    val result = snackbarHostState.showSnackbar(
        message = "已删除 1 条笔记",
        actionLabel = "撤销",
        withDismissAction = true,
        duration = SnackbarDuration.Short
    )
    if (result == SnackbarResult.ActionPerformed) {
        restoreNote()
    }
}
```

- `showSnackbar()` 是**挂起函数**，`ActionPerformed` / `Dismissed` 告诉你用户怎么处理的。
- `withDismissAction = true` 会自动加关闭图标（对应 Anatomy 里的 Icon）。
- 需要底部导航时不加 `navigationBarsPadding()`，由 `Scaffold` 统一处理；两者叠加会出现双倍间距。

## 无障碍

- Snackbar 出现时**读屏会自动播报**，不要把同样内容再写进 `contentDescription`。
- 动作按钮的文案要能独立听懂：「撤销」优于「确定」。
- 自动消失前必须给足阅读时间；有动作的提示应停留更久。
- 关闭图标要有 `contentDescription = "关闭"`。
- 不要在 Snackbar 里放必需信息——它可能被用户错过。

## 常见错误

1. 队列堆叠：连续触发导致多条排队，用户要等好几秒。**同一时刻只应有一条**，新提示应替换旧的。
2. 用 Snackbar 报错并要求用户处理（应该用内联错误或对话框）。
3. 两个动作按钮（撤销 + 取消），语义冗余且挤。
4. 文案超过两行被截断。
5. 被底部导航栏或 FAB 盖住——注意叠放顺序：Snackbar 应在 FAB 之上。
6. 平板 / 大屏上 Snackbar 拉满整屏宽（通行做法是限制最大宽度，官方未在当前 Specs 页给出数值）。
7. 用 Snackbar 做「操作成功」并配一个「好的」按钮——多此一举，用户看得到就够了。

## 待核验

- [ ] 自动消失时长（通行说法：短 4s / 长 10s）与最长停留规则
- [ ] 大屏最大宽度
- [ ] 与 FAB 的精确避让间距（官方 Measurements 为图片）

## 来源

- https://m3.material.io/components/snackbar/specs
- `01-Foundations/color-system.md`（inverse 角色）
- `02-Components/dialogs.md`（什么时候该用对话框）

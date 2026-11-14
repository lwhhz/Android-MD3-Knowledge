# Tooltip / 提示气泡

- 官方指南：https://m3.material.io/components/tooltips ｜ 规格页：https://m3.material.io/components/tooltips/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-15 ｜ 核验方式：Chrome CDP 读官方 Specs 页（text + raw 两种模式）
- 状态：**尺寸与颜色角色为官方原值**

## 两种 Tooltip，用途完全不同

| 类型 | 内容 | 触发 | 用在哪 |
|---|---|---|---|
| **Plain** | 一行短文字 | 长按（触摸）/ 悬停（指针） | 给图标补一句说明 |
| **Rich** | 标题 + 正文 + **最多两个按钮** | 点击图标（通常是信息图标） | 需要解释 + 提供动作 |

**关键区别**：Plain 是「这是什么」，Rich 是「为什么 + 你能做什么」。

## 尺寸（官方 Measurements 原值）

### Plain tooltip

| 属性 | 值 |
|---|---|
| 容器高度 | **24dp** |
| 内边距 | **8dp** |

### Rich tooltip

| 属性 | 值 |
|---|---|
| 上内边距 | **12dp** |
| 下内边距 | **8dp** |
| 左右内边距 | **16dp** |
| 按钮上限 | **2 个** |

Rich tooltip 可以包含：副标题（subhead）、正文、文字按钮、超链接。

## 颜色角色（官方原文）

| 部位 | 角色 |
|---|---|
| 容器 | `inverseSurface` |
| 文字 | `inverseOnSurface` |

和 Snackbar 一样用**反色**角色——所以它在深色主题下是浅色气泡。

## Compose 实现

Plain：

```kotlin
TooltipBox(
    positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
    tooltip = { PlainTooltip { Text("添加到收藏") } },
    state = rememberTooltipState()
) {
    IconButton(onClick = onFav) {
        Icon(Icons.Outlined.BookmarkBorder, contentDescription = "收藏")
    }
}
```

Rich：

```kotlin
TooltipBox(
    positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
    tooltip = {
        RichTooltip(
            title = { Text("同步设置") },
            action = {
                TextButton(onClick = onOpen) { Text("查看详情") }
            }
        ) {
            Text("关闭后仅在 Wi-Fi 下同步数据。")
        }
    },
    state = rememberTooltipState()
) {
    IconButton(onClick = {}) {
        Icon(Icons.Outlined.Info, contentDescription = "同步说明")
    }
}
```

Tooltip 相关 API 在部分 material3 版本里是 `@ExperimentalMaterial3Api`，用前确认注解状态。

## 无障碍（这一节最重要）

**Tooltip 里的信息不能是必需的。**

原因是：触摸设备上 tooltip 靠长按触发，读屏用户拿到的是 `contentDescription` 而不是气泡内容；键盘用户需要额外操作才能唤出。如果某个信息只在 tooltip 里出现，那三类用户都拿不到。

规则：
- 图标按钮的 `contentDescription` 必须**自包含**，不能写「见提示」。
- Rich tooltip 的按钮如果提供了唯一入口，那个入口必须在页面上另有可达路径。
- 气泡出现后不要自动消失得太快（读屏需要时间）。

## 常见错误

1. 把关键信息只放在 tooltip 里（触摸端基本等于没写）。
2. Plain tooltip 写成一整段话（它是 24dp 高的单行气泡）。
3. Rich tooltip 放三个按钮（上限是两个）。
4. 用 tooltip 做错误提示（应该用字段下方的 supporting text）。
5. 长按触发但同时也绑了长按菜单，手势冲突。
6. 气泡挡住被说明的控件本身。
7. 深色主题下自定义了 containerColor，破坏反色约定导致不够醒目。

## 来源

- https://m3.material.io/components/tooltips/specs
- `01-Foundations/color-system.md`（inverse 角色）
- `01-Foundations/accessibility.md`（contentDescription 自包含）

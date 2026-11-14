# FAB / 悬浮操作按钮

- 官方指南：https://m3.material.io/components/floating-action-button ｜ 规格页：https://m3.material.io/components/floating-action-button/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-14 ｜ 核验方式：Chrome CDP 读官方 Specs 页
- 状态：**变体与代际差异已核对**；尺寸 dp 值在图片中，标为通行值

## 用途

FAB 代表屏幕的**首要动作**，一个屏幕最多一个。它解决的是「这个页面最该被点的那件事，不能被滚动带走」。

不适合 FAB 的：
- 次要或并列动作（用普通按钮或菜单）
- 破坏性操作（删除不该是最显眼的按钮）
- 需要解释的操作（FAB 只有图标或图标+短文字）

## 三个变体（含代际差异）

| 变体 | Compose | M3 | M3 Expressive |
|---|---|---|---|
| FAB（默认） | `FloatingActionButton` | ✅ | ✅ |
| Large FAB | `LargeFloatingActionButton` | ✅ | ✅ |
| **Medium FAB** | `MediumFloatingActionButton`（如已提供） | ❌ | ✅ |
| Small FAB | `SmallFloatingActionButton` | ✅ | ⚠️ **不再推荐**，建议用更大尺寸 |

要点：
- **Medium 是 Expressive 才有的**。
- **Small 官方已标注 "no longer recommended"**，新设计不要用。
- Expanded / Extended FAB（图标 + 文字）用于需要明确文案的主操作。

## 颜色（Expressive 的命名变更）

官网原文：

> In the expressive update, the primary, secondary, and tertiary **set colors** were renamed to **primary container, secondary container, and tertiary container** to match the actual color roles used. New primary, secondary, and tertiary color styles were created to match the corresponding color roles.

意思是：
- 老的「primary 色 FAB」实际上用的是 `primaryContainer` 填充 + `onPrimaryContainer` 内容，现在**名字改对了**。
- 同时新增了真正用 `primary` / `onPrimary` 的样式。
- **Surface FAB 样式仍可用，但不再推荐。**

迁移时别只看名字：确认你的 FAB 到底要「低强调的 container 填充」还是「高强调的 primary 填充」。

## 状态（官网原文）

| 状态 | 状态层 | elevation |
|---|---|---|
| Enabled | — | 3（resting） |
| Hovered | **8%** | **4** |
| Focused | **10%** | 4 |
| Pressed | 10% | — |

**hover 时 elevation 从 3 升到 4**——这正是官方「hover/focus 通常抬升一级」的实例。

还有一条容易踩的规则：

> 使用非默认颜色映射时，**状态层颜色必须与图标颜色一致**。例如 primary 色样式的状态层应使用 `md.sys.color.primary`。

也就是不要自己写半透明黑，用内容色的 alpha 叠加。

## Compose 实现

```kotlin
Scaffold(
    floatingActionButton = {
        FloatingActionButton(onClick = { compose() }) {
            Icon(Icons.Default.Edit, contentDescription = "写新笔记")
        }
    }
) { padding -> /* content */ }
```

带文字（Extended）：

```kotlin
ExtendedFloatingActionButton(
    onClick = { compose() },
    icon = { Icon(Icons.Default.Edit, contentDescription = null) },
    text = { Text("写笔记") }
)
```

自定义颜色时状态层要跟着改：

```kotlin
FloatingActionButton(
    onClick = {},
    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
) {
    Icon(Icons.Default.Add, contentDescription = "添加")
}
```

## 位置与避让

- 默认右下角（LTR），距屏幕边缘 16dp。
- **在底部导航之上**：`Scaffold` 会自动处理；手写布局时注意 FAB 不能压在导航栏上。
- 与 Snackbar 同时出现时，Snackbar 在上、FAB 在下，或 FAB 临时上移。
- 列表滚动到底部时 FAB 不应遮住最后一项的点击区域。

## 无障碍

- **必须有 `contentDescription`**，且描述动作：「写新笔记」而不是「编辑图标」。
- 触控目标默认 ≥ 48dp，不要为了"精致"缩小。
- Extended FAB 里的图标 `contentDescription = null`（文字已表达动作）——和普通按钮同理。
- FAB 是页面唯一主操作，读屏顺序上应靠后但不该被隐藏。

## 常见错误

1. 一个屏幕两个 FAB。
2. 用 FAB 做删除 / 清空这类破坏性操作。
3. 仍用 Small FAB（官方已不推荐）。
4. 在 M3（非 Expressive）里找 Medium FAB。
5. FAB 压在底部导航或 Snackbar 上。
6. 图标 `contentDescription` 写成图标名而不是动作。
7. 自定义 containerColor 却没同时改 contentColor，图标对比度不足。
8. 自定义颜色后状态层仍是默认色，按下时出现突兀的色块。

## 来源

- https://m3.material.io/components/floating-action-button/specs
- `01-Foundations/elevation.md`（FAB = Level 3，hover 升到 4）
- `02-Components/buttons.md`（颜色角色与状态层写法）

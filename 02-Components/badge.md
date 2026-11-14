# Badge / 角标

- 官方指南：https://m3.material.io/components/badges ｜ 规格页：https://m3.material.io/components/badges/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-15 ｜ 核验方式：Chrome CDP 读官方 Measurements 表
- 状态：**尺寸全部为官方原值**

## 用途

Badge 在导航项或图标上标记**通知、数量或状态**。

| 形态 | 用来表示 |
|---|---|
| **Small badge**（6dp 圆点） | 有未读 / 有更新，但不关心数量 |
| **Large badge**（带数字） | 具体数量 |

只用于**提示存在**，不承载操作。点 badge 本身不应该有独立行为——交互对象是它附着的那个图标。

## 尺寸（官方 Measurements 原值）

| 属性 | 值 |
|---|---|
| Small badge 形状 | **3dp 圆角**（实际是圆点） |
| Small badge 尺寸 | **6dp** |
| Large badge 形状 | **8dp 圆角** |
| Large badge 单位数字尺寸（H×W） | **16dp** |
| Large badge 最大字符数尺寸（H×W） | **16×34dp** |
| Small badge 偏移（从右上角图标角到左下角角标角） | **6×6dp** |
| Large badge 偏移 | **14×12dp** |
| Large badge 与文字容器之间的内边距 | **4dp** |

要点：
- **单位数字时是圆的（16×16）**，两位数以上拉成胶囊（最大 34dp 宽）。34dp 是上限——「99+」再长就该用 small badge 或改写。
- 偏移量不是随便放的：small 6×6、large 14×12，都是从图标**右上角**算起的定位值。

## 颜色角色（官方原文）

导航栏与导航栏（rail）场景下均为：

| 部位 | 角色 |
|---|---|
| 容器 | `error` |
| 内容 | `onError` |

Badge 用 `error` 是刻意的——它要抢注意力，而且 error 是**静态色**（不随品牌色变化），在各种动态取色方案下都保持醒目。

## 配置

官方列出了按导航状态组合的多套配置：inactive / active、带文字 / 不带文字、small / large、以及最大字符数状态。也就是说 badge 在**选中与未选中**的导航项上表现不同，需要分别检查。

## Compose 实现

```kotlin
BadgedBox(
    badge = {
        if (unreadCount > 0) {
            Badge {
                Text(if (unreadCount > 99) "99+" else "$unreadCount")
            }
        }
    }
) {
    Icon(Icons.Default.Notifications, contentDescription = "通知")
}
```

只要圆点：

```kotlin
BadgedBox(badge = { Badge() }) {
    Icon(Icons.Default.Email, contentDescription = "消息")
}
```

导航项上：

```kotlin
NavigationBarItem(
    selected = selected,
    onClick = onClick,
    icon = {
        BadgedBox(badge = { Badge { Text("3") } }) {
            Icon(Icons.Default.Inbox, contentDescription = null)
        }
    },
    label = { Text("收件箱") }
)
```

注意：`NavigationBarItem` 里图标通常 `contentDescription = null`，由 label 承担语义；但 badge 的数字要能被读屏读到，必要时在 item 上补 `stateDescription`。

## 无障碍

- **数字必须能被读屏读到**：「收件箱，3 条未读」，而不是只画个红点。
- 纯圆点 badge 没有文字，必须靠父元素的 `contentDescription` 传递信息（如「有新消息」）。
- 不要用 badge 表达必须立刻处理的状态——它太小、太容易被忽略。
- 超过 99 用「99+」时，读屏应读「超过 99 条」。

## 常见错误

1. 用 badge 承载点击行为（它是一个标记，不是按钮）。
2. 数字无上限，「1234」把布局撑破（应做 99+ 截断）。
3. 一个屏幕上到处是 badge，注意力被稀释。
4. 用 success / warning 色自定义 badge，破坏了"红色=需要注意"的约定。
5. badge 盖住图标本身（偏移量用错）。
6. 深色主题下 badge 与背景对比度不足。
7. 读屏只能读到「收件箱」，读不到数量。
8. 已读后 badge 不消失或延迟很久才消失，用户反复点确认。

## 来源

- https://m3.material.io/components/badges/specs
- `01-Foundations/color-system.md`（error 是静态色）
- `02-Components/navigation.md`（导航项语义）

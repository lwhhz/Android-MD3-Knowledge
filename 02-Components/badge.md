# Badge / 角标

- 官方指南：https://m3.material.io/components/badges ｜ 规格页：https://m3.material.io/components/badges/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary（`BadgedBox` / `Badge`）
- 核验日期：2026-11-16 ｜ 核验方式：官方 Specs / Guidelines / Accessibility 页全文 dump
- 状态：**尺寸与颜色全部为官方原值**

## 用途

Badge 在导航项或图标上标记**通知、数量或状态**，不承载操作——交互对象是它附着的图标/导航项。

> Badges are used to indicate a notification, item count, or other information relating to a navigation destination. They are placed on the ending edge of icons, typically within other components.

| 变体 | 官方定义 |
|---|---|
| **Small badge** | 「a simple circle, used to indicate an unread notification」——只表示"有未读"，不表示数量 |
| **Large badge** | 「contains label text communicating item count information」——用文字传达具体数量 |

最常见的宿主组件：navigation bar、navigation rail、app bar、tabs。Web 平台官方**不提供**实现。

## 规格（官方 Measurements 原值）

| 属性 | 值 |
|---|---|
| Small badge 形状 | **3dp 圆角** |
| Small badge 尺寸（H×W） | **6dp** |
| Large badge 形状 | **8dp 圆角** |
| Large badge 单位数字尺寸（H×W） | **16dp** |
| Large badge 最大字符数尺寸（H×W） | **16×34dp** |
| Small badge 偏移（图标右上角 → 角标左下角，H×W） | **6×6dp** |
| Large badge 偏移 | **14×12dp** |
| Large badge 文字与容器内边距 | **4dp** |

要点：

- **单位数字时是圆的（16×16）**；超过一位拉成胶囊，宽度上限 34dp。
- **标签最多 4 个字符（含 `+`）**：

> The maximum number of characters within large badge label text is four, including a + to indicate more.

数字增长时宽度扩展但锚点位置不变；容器锚定在图标边界框（icon bounding box）内、图标右上（trailing）角。

## 颜色角色（官方原值）

Navigation bar 与 navigation rail 场景一致：

| 部位 | 角色 |
|---|---|
| 容器 | `error` |
| 内容 | `onError` |

官方刻意用 `error` 抢注意力，且要求**保持默认配色**以避免色彩冲突。若确需自定义角色，**对比度至少 3:1**（见 Accessibility 页 Do/Don't）。

## 使用规则（Guidelines Do / Don't 翻译）

1. **位置固定，不要随意挪动，也不要盖住图标**。官方唯一的例外：RTL 语言中要镜像调整 badge 位置。
2. **用默认色**（`error`/`onError`）；自定义角色必须保证 3:1 对比度。
3. **数字超限要截断**（如「99+」），不要让 badge 被裁切或与相邻元素碰撞。
4. **空间选择**：navigation rail 等宽松处用 large badge；app bar 等紧凑处用 small badge——官方明说 small badge 不会碰到屏幕边缘。
5. **图标后跟文字/元素时，large badge 放在 trailing edge**；可能重叠 trailing 元素时改用 small badge。
6. **导航栏中选中该目的地后隐藏 badge**：

> In navigation bars, hide the badge once the destination has been selected.

## Compose 实现

```kotlin
BadgedBox(
    badge = {
        if (unreadCount > 0) {
            Badge { Text(if (unreadCount > 99) "99+" else "$unreadCount") }
        }
    }
) {
    Icon(Icons.Default.Notifications, contentDescription = "通知")
}
// 纯圆点：BadgedBox(badge = { Badge() }) { ... }
```

导航项上：`NavigationBarItem` 的 icon 槽放 `BadgedBox`；图标 `contentDescription = null`（label 承担语义），badge 数字通过 item 语义播报（见下）。

## 无障碍

- **读屏顺序**：badge 的无障碍标签在其导航目的地之后读出。

> The accessibility label for a badge item will be read after its navigation destination. Any numerical badges will have their number read, while non-counting badges will simply announce "New notification".

- 数字 badge 播报数字本身；纯圆点（非计数）badge 播报 **"New notification"**——不要只画红点不给语义。
- AT 用户应能通过选中对应导航项来处理 badge 通知。
- 不要用 badge 承载必须立刻处理的状态；badge 选中后要立即隐藏（对应未读已读）。

## 常见错误

1. 给 badge 本身挂点击行为（它是标记不是按钮）。
2. 数字无上限撑破布局（应按 4 字符上限截断为 99+）。
3. 自定义 success/warning 色，破坏「红 = 需注意」的约定，或对比度 < 3:1。
4. 读屏只读到「收件箱」读不到数量/未读状态。
5. RTL 布局下 badge 未镜像，位置错误或盖住图标。
6. 选中导航目的地后 badge 不消失。

## 来源

- https://m3.material.io/components/badges/overview
- https://m3.material.io/components/badges/specs
- https://m3.material.io/components/badges/guidelines
- https://m3.material.io/components/badges/accessibility
- 核验日期：2026-11-16

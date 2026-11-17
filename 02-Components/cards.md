# Cards / 卡片

- 官方指南：https://m3.material.io/components/cards ｜ 规格页：https://m3.material.io/components/cards/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-16
- 核验方式：官方全站文本 dump（overview / specs / guidelines / accessibility 四页）
- 状态：**尺寸为官方 measurements 明示值**；elevation 数值来自 Compose 默认值，已标注

## 用途

卡片把**同一主题的内容与操作**装进一个可识别容器：「这些内容属于同一件事，并且可以进一步查看」。

> "Use a card to display content and actions on a single topic."
> "Cards should be easy to scan for relevant and actionable information."

判断标准：

| 场景 | 用卡片 | 说明 |
|---|---|---|
| 图文混排、每项是独立主题 | ✅ | 专辑、房源、文章、商品 |
| 作为进入详情页的入口 | ✅ | 整卡可点 |
| 同质条目连续扫描（设置项、消息） | ❌ | 用 list，卡片反而降低密度 |
| 仅靠间距 / 标题 / 分隔线就能分层 | ❌ | 官方明确不要硬套卡片 |
| 只有一行文字 | ❌ | 不需要容器 |

> "Don't force content into cards when spacing, headlines, or dividers would create a simpler visual hierarchy"（Don't）

## 变体

三种变体**可读性与功能完全相同**，只按视觉强调程度选择：

| 变体 | 容器色 | 边界表达 | 强调度 | Compose |
|---|---|---|---|---|
| Elevated | `surface container low` | 投影 | 与背景分离强于 filled，弱于 outlined | `ElevatedCard` |
| Filled | `surface container highest` | 仅色差 | 最弱，最克制 | `Card` |
| Outlined | `surface` + `outline variant` 描边 | 1dp 视觉边界 | 最强 | `OutlinedCard` |

官方描述：

> "Elevated cards have a drop shadow, providing more separation from the background than filled cards, but less than outlined cards"

> "Filled cards provide subtle separation from the background. This has less emphasis than elevated or outlined cards."

> "Outlined cards have a visual boundary around the container. This can provide greater emphasis than the other variants."

Elevation 数值（**Compose 默认值，非 dump 文本**）：`Card` 0dp、`ElevatedCard` 1dp、`OutlinedCard` 0dp。官方只说 M3「elevation 更低、默认无阴影」，未在 specs 文本给出 dp 数——引用时请注明来源是 Compose API。

## 规格

cards specs 页的测量章节以图片为主，但附了一张只有四行的属性表，是本组件唯一可引用的数字：

| 属性 | 值 |
|---|---|
| 圆角 | **12dp** |
| 左右内边距 | **16dp** |
| 卡片之间的间距 | **8dp（最大）** |
| 标签文字对齐 | 起始边（start-aligned） |

测量图补充（2026-11-16 视觉读取，原始 PNG 存档见 `06-Figma-Resources/asset-index.md`）：布局图显示**水平间距 8dp、纵向间距 12dp**——与属性表「间距最大 8dp」的横向一致，纵向 12 为图示排版值（⚠️ 视觉判读，网格纵向间距以 Figma Kit 为准）。

补充说明：

- 卡片**没有固定高度**：「Card containers hold all card elements. Their size is determined by the space those elements occupy.」尺寸由内容决定。
- 卡片**没有固定宽度**，随栅格 / 断点伸缩。
- 12dp 圆角小于 carousel 的 28dp，大于按钮的内侧圆角；三者不要混用。

## 结构

容器是**唯一必需元素**，其余全部可选。

```
┌─────────────────────────┐
│  [image / thumbnail]    │  可选：图片、缩略图、视频
│  Headline               │  可选：主题
│  Subhead                │  可选：署名、地点等小字
│  Supporting text        │  可选：正文、摘要
│  ─────────────────      │  可选：分割线
│  [button]   [icon btn]  │  可选：动作区
└─────────────────────────┘
```

| 元素 | 内容 |
|---|---|
| Container | 承载全部内容；elevation 由容器表达 |
| Headline | 卡片主题（专辑名、文章标题） |
| Subhead | 更小的辅助文字（作者署名、地点标签） |
| Supporting text | 正文摘要、描述 |
| Media | 缩略图（头像 / logo）、图片、视频 |
| Actions | 按钮、图标按钮、选择控件、链接文字、溢出菜单 |

分组规则：

- 内容按 block 分组，按重要性给不同视觉强调。
- **分割线**：整宽分割线用于「可展开的区域」；内缩分割线（inset）用于分隔相关内容，不跑满整卡宽度。

## 颜色角色

| 部位 | 颜色角色 |
|---|---|
| Elevated 容器 | `surface container low` |
| Filled 容器 | `surface container highest` |
| Outlined 容器 | `surface` |
| Outlined 描边 | `outline variant` |
| 标题 / 正文 | 官方未逐项列出，按 `on surface` / `on surface variant` 层级使用（⚠️ 推断，specs 仅给出容器与描边角色） |

状态：三种变体都定义了 **Hovered / Focused / Pressed / Dragged / Disabled** 五态；**没有 selected 态**——卡片本身不表达「已选中」，多选请用 checkbox / radio 等选择控件。

## 使用规则

### 内容与层级

1. 一张卡只讲一件事；文字与图片的排布必须能看出层级。
2. 卡片可以作为进入更深层内容的入口（专辑、行程详情）。
3. ❌ 不要把互不相关的内容塞进同一张卡。
4. 卡片可放在 grid、vertical list、carousel 中；同组卡片**默认共面**（相同静止 elevation），除非被拿起或拖拽。

### 图片上的文字

- ❌ 不推荐把文字或图标压在图片上。
- 必须这样做时：确保对比度达标，并在文字/图标下加**半透明 scrim 或包围形状**。

> "It isn't recommended to place text or icons on images. If it's necessary, ensure the background image provides sufficient contrast for the text to meet accessibility standards."

### 动作：二选一，不要叠加

官方把卡片分成两类，**这是卡片最常见的实现错误**：

| 类型 | 行为 | 内部还能放按钮吗 |
|---|---|---|
| 不可操作卡片（non-actionable） | 纯容器，动作在内部按钮/链接上 | ✅ 可以，且必须有 |
| 可直接操作卡片（directly actionable） | 整卡是一个触控目标，打开详情 | ❌ 不要再放按钮/链接 |

> "A card can be a non-actionable container that holds actions like buttons and links, or it can be directly actionable without any buttons or links. This is to avoid stacking actionable elements. An action shouldn't be placed on an actionable surface."

其他动作形式：图标按钮（收藏、点赞、评分）、选择控件（chip、slider、checkbox）、正文内链接。溢出菜单通常放在卡片**右上或右下角**。

### 集合（collection）

1. 同组卡片可排成 grid、纵向列表、横向 carousel。
2. 同一集合的筛选/排序选项放在**集合之外**；筛选必须作用于集合内每张卡。
3. 排列方式要直观，影响用户对内容关系的感知。
4. 大屏用多列，不要把卡片拉成横贯全屏的一条；可把相关卡片重组为横排或 carousel。
5. compact 断点可考虑用 list 替代 card，但控件与动作不能丢。

### 行为

| 交互 | 规则 |
|---|---|
| 展开 | 用 container transform（父子过渡）揭示更多内容；仅用于 hero 时刻 |
| 导航 | 用 forward/backward 过渡进入下一层，动作比 container transform 更轻 |
| 滑动 | 一张卡**只绑定一个** swipe 动作（删除 / 归档 / 标记）；卡内不能有可滑动内容（如图片轮播、分页） |
| 拿起移动 | 拿起时提高 elevation；悬浮在所有元素之上，但**低于 app bar 与导航**；不要推挤其他元素 |
| 滚动 | 移动端卡片内部**不滚动**，超出高度就截断，靠展开卡片看全文；桌面端允许卡内滚动 |

> "Card content that's taller than the maximum card height is truncated and doesn't scroll, but can be displayed by expanding the height of a card."

## 无障碍

- 读屏用户应能：导航到卡片及其内部元素；按输入方式获得正确的反馈。
- **不要堆叠可操作区域**：整卡可点时，内部不要再放按钮（同上）。
- 拖拽 / 滑动必须提供**单指替代方案**，例如点击或长按打开菜单，在菜单里改位置或删除。
- 焦点规则：
  - 可直接操作的卡片本身是 tab stop，`Tab` 直接到下一张卡；
  - 不可操作卡片本身不是 tab stop，但**内部每个可操作元素都是** tab stop，逐个访问后才进入下一张卡。
- 键盘：`Tab` 移动焦点；`Space` / `Enter` 确认动作；菜单内用方向键移动、`Space` / `Enter` 选择、`Tab` 退出。
- 语义角色：可直接操作的卡片可用 **button 或 link** role；不可操作的卡片是纯容器，**不需要 role**。
- 卡片内容会按顺序朗读；纯装饰图片必须对读屏隐藏。
- 拖拽态下不建议把菜单盖在卡片上；必须这样时，确保交互能完成。

## Compose 对应 API

| 需求 | API |
|---|---|
| Filled 卡片 | `Card` |
| Elevated 卡片 | `ElevatedCard` |
| Outlined 卡片 | `OutlinedCard` |
| 整卡可点 | `Card(onClick = …)` 等带 `onClick` 的重载 |
| 颜色 | `CardDefaults.cardColors()` / `elevatedCardColors()` / `outlinedCardColors()` |
| Elevation | `CardDefaults.cardElevation()` / `elevatedCardElevation()` |

```kotlin
Card(
    onClick = { openDetail(id) },
    modifier = Modifier.fillMaxWidth()
) {
    Column(Modifier.padding(16.dp)) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(4.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
```

实现要点：

- 整卡可点时用 `onClick` 重载，自带涟漪与可点击语义，**不要再套一层 `clickable`**。
- 卡片内部的 `padding` 需自己加；Compose 的 `Card` 不会自动给 16dp 内容边距。
- 12dp 圆角是 `CardDefaults.shape` 的默认值（medium 档），不要手写 `RoundedCornerShape` 覆盖。
- 整卡可点时，`clickable` 会合并子节点语义（标题+副标题一次读完）；卡片内还有独立按钮时不要整卡可点。

## 常见错误

1. 整卡可点，里面又放独立按钮，点击区域互相抢夺。
2. Outlined 卡片再加 elevation，描边与阴影打架。
3. 圆角写 28dp（carousel 值）或 16dp（list 值），与卡片 12dp 不一致。
4. 卡片之间间距 16dp 以上，视觉上散成独立模块（官方上限 8dp）。
5. 列表每一项都套卡片并加阴影，本该系统列表的地方变成视觉噪音。
6. 移动端让卡片内部滚动，出现双滚动条。
7. 一张卡绑定左右两个不同滑动动作，用户无法预期。
8. 卡片里塞必须读完的长正文——卡片是「扫」不是「读」。
9. 用卡片底色表达状态（红卡 = 异常），色觉障碍用户无法分辨（⚠️ 推断，页面未明示）。

## 来源

- https://m3.material.io/components/cards/overview
- https://m3.material.io/components/cards/specs
- https://m3.material.io/components/cards/guidelines
- https://m3.material.io/components/cards/accessibility
- 核验日期：2026-11-16
- 关联：`02-Components/lists.md`（何时用列表）、`02-Components/carousel.md`（卡片横排）、`02-Components/divider.md`

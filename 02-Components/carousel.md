# Carousel / 轮播

- 官方指南：https://m3.material.io/components/carousel ｜ 规格页：https://m3.material.io/components/carousel/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-16
- 核验方式：官方全站文本 dump（overview / specs / guidelines / accessibility 四页）
- 状态：**六种布局，28dp 项圆角为官方明示值**

## 用途

Carousel 展示**一组可横向浏览的视觉条目**，让用户看到「还有更多」，并能在滑动中掠过。

> "Carousels show a collection of items that can be scrolled on and off the screen"

| 适合 | 不适合 |
|---|---|
| 图片、视频、封面等视觉内容 | 必须完整读完的正文 |
| 商品推荐、相册、活动流 | 只有一项内容 |
| 需要「露出下一项」提示更多 | 条目需要大量文字说明 |
| 沉浸式全屏内容流 | 需要精确对比多个条目 |

条目以视觉为主，文字是**可选且简短**的：

> "Carousel items emphasize visuals, but can also contain brief text that adapts to the item size."

## 六种布局

| 布局 | 可见项 | 适合 | 推荐滚动 |
|---|---|---|---|
| **Multi-browse** | ≥1 大 + 1 中 + 若干小 | 一次浏览大量视觉条目（照片、活动流） | Snap |
| **Uncontained** | 同尺寸项，滚到容器边缘外 | 高度定制、文字较多、图文堆叠 | Default 或 Snap |
| **Uncontained multi-aspect ratio** | 多种宽高比（16:9 / 9:16 / 1:1 / 3:4） | 条目本身宽度不一 | Default 或 Snap |
| **Hero** | ≥1 大 + 1 小 | 突出重点内容（电影、应用） | Snap |
| **Center-aligned hero** | 1 大居中 + 两侧各 1 小 | 居中突出大图 | Snap |
| **Full-screen** | 1 项，边到边 | 竖向视频/图片流、沉浸体验 | Snap（强制） |

> ⚠️ 旧版文档只列「contained / uncontained 两种布局」，已过期。官方现为六种，centered 是配置而非独立布局族。

布局选择要点：

1. multi-browse 不适合文字多或图像复杂的条目。
2. uncontained 条目尺寸不变，因此可在条目上下放更多文字或 UI，不会被裁切。
3. uncontained multi-aspect ratio **只在条目宽度确实不同时使用**；宽度范围从 9:16 到 16:9。
4. hero 突出「一张大图 + 下一张的预览」；大屏上可见更多大项。
5. center-aligned hero 在 leading 侧多一个预览项，使大项居中。
6. full-screen 只适合**竖向**内容；compact / medium 断点仅竖屏可用，**横屏不要用**。

## 规格

### 各布局测量（specs 明示值）

| 属性 | Multi-browse | Uncontained | Uncontained multi-aspect | Hero | Center-aligned hero | Full-screen |
|---|---|---|---|---|---|---|
| 垂直对齐 | 居中 | 居中 | 居中 | 居中 | 居中 | 居中 |
| Leading 内边距 | 16dp | 16dp | 16dp | 16dp | 16dp | 0dp |
| Trailing 内边距 | 16dp | —（出血） | —（出血） | 16dp | 16dp | 0dp |
| 上/下内边距 | 8dp | 8dp | 8dp | 8dp | 8dp | 0dp |
| 项间距 | 8dp | 8dp | 8dp | 8dp | 8dp | **16dp** |
| 大项宽度 | 动态或用户设定 | 动态 | 动态 | 动态 | 动态 | 填满窗口 |
| 中项宽度 | 动态 | — | — | — | — | — |
| 小项宽度 | 40–56dp，动态 | — | — | 40–56dp | 40–56dp | — |
| 项圆角 | **28dp** | 28dp | 28dp | 28dp | 28dp | 未标注（全屏边到边） |

关键点：

- **28dp 是全部非全屏布局的统一项圆角**，比卡片（12dp）大得多，是「独立成形」的视觉特征。
- 小项 40–56dp 是「露出相邻项边缘」的提示尺寸，不承载可读内容。
- 大项有可调的最大宽度；调窄后同屏可见更多项，但**必须仍足够大、可辨认**。
- specs 表**没有给条目高度**，高度由宽高比与容器决定。

> "Large items have a customizable maximum width that's used to optimally fit carousel items into the available space."
> "Small carousel items have a minimum width of 40dp and a maximum width of 56dp."

## 颜色角色

| 部位 | 颜色角色 |
|---|---|
| 容器 | `surface` |
| 条目内容色 | 官方未在 specs 列出；条目通常由图片或卡片自身颜色决定（⚠️ 推断，页面未明示） |

状态：**Enabled / Hovered / Focused / Pressed / Disabled**（token 数值未在 dump 文本展开）。

## 使用规则

### 布局与滚动

1. 条目必须**完整可见**（uncontained 除外）；滚动后自动改变尺寸并吸附回布局。
2. 默认滚动不吸附，可停在任意位置——**只用于 uncontained**。
3. snap 滚动在松手后对齐栅格——用于 multi-browse、hero、full-screen。
4. full-screen **必须** snap 到容器边缘，不允许自由滚动或停在半途。
5. compact 断点最多舒适展示 3 项；超过 3 项时，图片与内容必须一眼可辨。
6. 大屏随容器变大增加可见项数并放大尺寸；full-screen 始终只显示 1 项。

> "The default scrolling behavior doesn't snap items to a layout grid. They can stop anywhere in the container. Only use this for the uncontained layout."

### 文字

- 条目文字可选、简短；compact 断点**默认不超过 2 行**，除非背景是纯色等简单背景。
- 文字必须在每种尺寸下都可理解；小项可用缩写或省略标题。
- 文字需求大 → 改用 uncontained，或改用一组卡片。

### 尺寸与可读性

- ✅ 设定足够大的大项宽度，保证图片与文字可读可辨。
- ❌ 不要把条目缩到图片无法辨认。

### 无障碍兜底入口（强制）

- 在**竖向滚动页面**里，carousel 必须提供不依赖横向滑动的访问方式（full-screen 除外）。
- 推荐做法：carousel 下方加 **"Show all" 按钮**，打开一个竖向滚动的全量页面。
- 若 carousel 有标题，可在标题同行放**箭头图标按钮**替代。
- "Show all" 按钮内边距 **4dp**；箭头图标 **48dp**，标题与箭头同排、标题起始边对齐。
- ❌ 不要把按钮放进 carousel 容器内或紧贴其旁；也不要让按钮遮住 carousel。

### 动效与降级

- 条目滚动时有 **parallax**（内容与容器不同速）；条目在移动中改变尺寸。
- 开启「减少动态效果」后：**移除 parallax、条目不再放大，所有条目同尺寸**；carousel 应贴到窗口边缘避免裁切。
- hero 在减少动态效果时，小项只部分露出。

### RTL

官方未在 carousel 页面单列 RTL 规则；按镜像滚动方向与图标处理（⚠️ 推断，页面未明示）。

## 无障碍

- 读屏用户应能：导航到 carousel 容器；在条目间移动；激活条目；**跳过整个 carousel**。
- **初始焦点落在第一个 carousel item，而不是容器**；`Tab` 或方向键在条目间移动。
- 上下方向键用于**离开 carousel**，移到页面下一个元素（如 Show all 按钮）。
- `Space` / `Enter` 激活当前条目。
- 语义：容器为 **container role**，标签要合适；由于每个 carousel 条目数不同，标签需读出**当前项与总数**（如「第 2 项，共 8 项」）。
- 触控：点击条目时轻微变形 + 涟漪；hover 有状态提示；click 有涟漪。
- 减速动效是**无障碍要求**，不是可选装饰。
- 不要用「只能横向滑动」作为唯一入口——这是官方对滚动页面的硬性要求。

## Compose 对应 API

| 需求 | API | 状态 |
|---|---|---|
| 官方 carousel 布局 | `androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel` / `HorizontalUncontainedCarousel` | `@ExperimentalMaterial3Api`，类名与参数需按当前 material3 版本核验 |
| 通用分页 | `HorizontalPager`（foundation） | 稳定 |
| 减少动效 | 读取系统 animator duration scale / `LocalMotionDurationScale` | —（⚠️ 具体 API 需核验） |

```kotlin
val pagerState = rememberPagerState(pageCount = { items.size })

HorizontalPager(
    state = pagerState,
    contentPadding = PaddingValues(horizontal = 16.dp), // specs: leading/trailing 16dp
    pageSpacing = 8.dp                                  // specs: 项间距 8dp
) { page ->
    Surface(
        shape = RoundedCornerShape(28.dp),              // specs: 项圆角 28dp
        color = MaterialTheme.colorScheme.surface
    ) {
        AsyncImage(
            model = items[page].image,
            contentDescription = items[page].title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(180.dp)
        )
    }
}
```

实现要点：

- `HorizontalPager` 默认**不吸附**；需要 snap 时用 `rememberPagerState` + `snapPosition`，或直接用官方 carousel 组件。
- 官方 carousel 的布局尺寸（大/中/小项动态宽度）由组件计算，不要手写 `width` 覆盖。
- full-screen 时 `contentPadding = 0.dp`、`pageSpacing = 16.dp`，并强制 snap。
- 若使用 `HorizontalPager` 自己拼，需自行实现 parallax、动态宽度与 28dp 圆角，工作量不小。

## 常见错误

1. 只写「contained / uncontained 两种布局」——官方现为六种，选型会漏掉 hero / multi-browse / full-screen。
2. 项圆角用 12dp（卡片值），失去轮播的独立感。
3. 任意布局都用自由滚动，松手停在半张图上（应 snap）。
4. full-screen 允许自由滚动或横向使用。
5. 竖屏页面只提供横向滑动，没有 Show all / 箭头入口。
6. 把按钮塞进 carousel 容器或盖在条目上。
7. compact 断点一次塞 6 个带文字的条目，谁也读不清。
8. 忽略「减少动态效果」，parallax 与放大照常播放。
9. 只给图片不给 `contentDescription`。

## 来源

- https://m3.material.io/components/carousel/overview
- https://m3.material.io/components/carousel/specs
- https://m3.material.io/components/carousel/guidelines
- https://m3.material.io/components/carousel/accessibility
- 核验日期：2026-11-16
- 关联：`02-Components/cards.md`（卡片横排）、`01-Foundations/shape-system.md`（28dp 档位）


## 示例实现限制（android-compose 归档，2026-11-18）

- material3 1.5.0-rc01 中自定义 `Carousel` / `keylineListOf` / `KeylineListScope` 为 **internal**，应用层无法用官方 keyline 实现逐项宽高比。
- 归档实现：MULTI_BROWSE / HERO / CENTER_ALIGNED_HERO / UNCONTAINED / FULL_SCREEN 用公开官方组件；**MULTI_ASPECT 降级为 LazyRow + snap + 固定宽度**。
- 因此不能把该示例视为“六种布局全部官方实现”；动效/窄项缩放仍以官方组件为准。

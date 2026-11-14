# Carousel / 轮播

- 官方指南：https://m3.material.io/components/carousel ｜ 规格页：https://m3.material.io/components/carousel/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-15 ｜ 核验方式：Chrome CDP 读官方 Measurements 表
- 状态：**尺寸为官方原值**

## 用途

Carousel 展示**一组可横向浏览的同类项目**，让用户一次看到"还有更多"，并能快速扫过。

| 场景 | 适合 |
|---|---|
| 图片集、商品推荐、卡片流 | ✅ |
| 步骤引导、首次使用教学 | ✅（但要能跳过） |
| 关键信息 / 需要完整阅读的内容 | ❌ 用户会滑走 |
| 只有一张图 | ❌ 不该用轮播 |

**最重要的一条**：轮播里的内容用户**大概率只会看到第一屏**。所以不要把它当"信息归档"，它只能做"吸引注意 + 点击进入"。

## 两种布局

| 布局 | 行为 |
|---|---|
| **Contained** | 项目在容器内滚动，左右留出边距 |
| **Uncontained** | 项目一直滚动到容器边缘（贴边） |

Uncontained 视觉更沉浸，但会牺牲左右留白的一致性——同屏不要混用两种。

## 尺寸（官方 Measurements 原值）

| 属性 | 值 |
|---|---|
| 垂直对齐 | 居中 |
| 左右内边距 | **16dp** |
| 上下内边距 | **8dp** |
| 元素间距 | **8dp** |
| Large 项宽度 | 动态（或用户设定） |
| Medium 项宽度 | 动态 |
| **Small 项宽度** | **40–56dp**，动态 |
| **项圆角** | **28dp** |

注意圆角是 **28dp（extraLarge 档）**，比卡片（12dp）大得多——这是让轮播项看起来"独立成形"的关键视觉特征。改成 12dp 会立刻显得像列表。

Small 项 40-56dp 是"露出相邻项边缘"的尺寸，起提示作用，不是可读内容。

## 交互规则

- **横向滑动**切换，不要自动轮播（自动播会让用户读不完就被带走；确需自动播放时提供暂停）。
- 滑动要**带吸附（snap）**，停在完整项上，不要停在半张。
- 显示当前位置的指示（点或"3/8"），但**不要超过 8 个点**——再多改用文字计数。
- 键盘 / 遥控要能用方向键切换。
- 首项与末项不要循环到另一头（除非明确是无限流），用户会失去位置感。

## Compose 实现

```kotlin
val pagerState = rememberPagerState(pageCount = { items.size })

HorizontalPager(
    state = pagerState,
    contentPadding = PaddingValues(horizontal = 16.dp),
    pageSpacing = 8.dp
) { page ->
    Card(shape = RoundedCornerShape(28.dp)) {          // 28dp 是官方项圆角
        AsyncImage(
            model = items[page].image,
            contentDescription = items[page].title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(180.dp)
        )
    }
}
```

`contentPadding` + `pageSpacing` 就是官方那 16dp / 8dp 的来源。

## 无障碍

- **每一项都要有语义描述**（图片用 `contentDescription`，卡片用文字标题）。轮播图常常只给图片不给描述，读屏用户完全拿不到内容。
- 指示器要能被读出当前页（"第 2 张，共 5 张"），纯圆点对读屏无意义。
- 不要依赖自动播放传达信息。
- 滑动区域要避开内部可点击元素——否则滑一下变成点进去。
- 提供非滑动入口（"查看全部"按钮），因为部分用户无法完成滑动手势。

## 常见错误

1. 项圆角用 12dp（卡片值），失去轮播的独立感。
2. 自动轮播且不能暂停。
3. 滑动不吸附，停在半张卡片上。
4. 只给图片不给 `contentDescription`。
5. 指示器 10 个圆点，用户数不清。
6. 轮播里放必须读完的正文。
7. 第一屏放"无关紧要"的内容——那是唯一保证被看到的位置。
8. 在轮播项内部又放了横向滚动的控件，手势打架。

## 来源

- https://m3.material.io/components/carousel/specs
- `01-Foundations/shape-system.md`（28dp = extraLarge）
- `01-Foundations/accessibility.md`

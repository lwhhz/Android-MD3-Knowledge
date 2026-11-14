# Search Bar / 搜索栏

- 官方指南：https://m3.material.io/components/search ｜ 规格页：https://m3.material.io/components/search/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-15 ｜ 核验方式：Chrome CDP 读官方 Specs 页
- 状态：**尺寸测量为官方原值**；Compose 侧 API 拆分状态需按版本核验

## 先分清三个概念

很容易混：

| 概念 | 是什么 |
|---|---|
| **Search bar** | 那个输入框本身（未聚焦时也可能只是个"假输入框"） |
| **Search view** | 点开后进入的搜索态，含建议、历史、结果 |
| **Style / Layout** | 视觉风格（contained / divided）× 排布（full-screen / docked） |

## 两个 Style（官方已给结论）

| Style | M3 | M3 Expressive | 特点 |
|---|---|---|---|
| **Contained** | ✅ | ✅ | 填充容器 + 圆角 + expressive 动效，把搜索栏从列表里"分离"出来 |
| Divided | ✅ | ❌ **Not recommended. Use contained.** | 老样式，不要再用 |

官网对 Expressive 的 divided 直接写了「不推荐，用 contained」。新项目只做 contained。

## 两个 Layout

| Layout | 说明 | 适用 |
|---|---|---|
| **Full-screen** | 搜索态占满屏幕 | 手机，结果是主要内容 |
| **Docked** | 搜索栏停在顶部，结果在下方 | 平板 / 折叠屏，需要保留上下文 |

排布跟着窗口宽度走，不要跟设备型号走。

## 尺寸（官方 Measurements 原值）

| 元素 | 属性 | 值 |
|---|---|---|
| Container | 宽度 | **最小 360dp，最大 720dp** |
| Container | 高度 | **56dp** |
| Container | 文字对齐 | 起始对齐 |
| Leading padding | 未聚焦 | **24dp** |
| Leading padding | 聚焦 | **12dp** |
| Trailing padding | 未聚焦 | **24dp** |
| Trailing padding | 聚焦 | **12dp** |
| 前置图标与文字间距（从点击区算） | | **4dp** |
| 文字与尾部图标间距（从点击区算） | | **4dp** |
| Avatar 尺寸 | | **30dp** |

**最关键的一条**：Expressive 里搜索栏聚焦时会**展开**，左右内边距从 24dp 收到 12dp。这个变化是动效的一部分，不是随意设的数值。

宽度上限 720dp 也很重要——大屏上不让搜索框横贯全屏。

## Compose 实现

```kotlin
var query by rememberSaveable { mutableStateOf("") }
var active by rememberSaveable { mutableStateOf(false) }

SearchBar(
    inputField = {
        SearchBarDefaults.InputField(
            query = query,
            onQueryChange = onQueryChange,
            onSearch = { active = true },
            expanded = active,
            onExpandedChange = { active = it },
            placeholder = { Text("搜索笔记") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { query = "" }) {
                        Icon(Icons.Default.Close, contentDescription = "清空")
                    }
                }
            }
        )
    },
    expanded = active,
    onExpandedChange = { active = it }
) {
    // 建议 / 历史 / 结果
}
```

**API 状态提示**：搜索相关的 Compose API 在近几个版本有过拆分（`SearchBar` / `DockedSearchBar` / `ExpandedFullScreenSearchBar` / `ExpandedDockedSearchBar`，以及新的状态控制方式）。用之前先确认当前 material3 版本提供的是哪一套，参数差异不小。

## 行为规则

- **输入即搜要有防抖**（250-400ms），不要每个字符打一次接口。
- **聚焦但未输入时**显示历史或推荐，而不是空白。
- **有输入但无结果**要有明确的空态 + 建议（改关键词、检查筛选条件）。
- 搜索结果页要能一键清空/返回，不要困在搜索里。
- 搜索栏在页面顶部时，滚动后是否吸顶要想清楚——吸顶会永久占掉 56dp。

## 无障碍

- 输入框必须有可访问的标签（`placeholder` 不算标签，读屏聚焦后它就消失了）。
- 清空按钮要写 `contentDescription = "清空"`。
- 结果列表要有"共 N 条结果"之类的语义提示。
- 键盘 `imeAction = Search`，回车即搜。
- 聚焦展开的动效不应对读屏造成焦点跳转。

## 常见错误

1. 还在用 divided 样式（Expressive 已标不推荐）。
2. 没有防抖，输入"安卓"打了三次接口。
3. 搜索栏做装饰：点了不进入搜索态，或只是个跳转按钮却长得像输入框。
4. 大屏上搜索框拉满全屏宽（应限制 720dp）。
5. 聚焦后内边距没变，缺少展开的视觉反馈。
6. 空结果显示白屏，没有引导。
7. 清空按钮没有语义，读屏只读「按钮」。
8. 键盘弹出时结果列表被遮挡。

## 来源

- https://m3.material.io/components/search/specs
- `02-Components/text-fields.md`（输入框通用规则）
- `03-Android-Compose/adaptive-layout.md`（full-screen / docked 的选择）

# Search / 搜索

- 官方指南：https://m3.material.io/components/search ｜ [Specs](https://m3.material.io/components/search/specs) ｜ [Guidelines](https://m3.material.io/components/search/guidelines) ｜ [Accessibility](https://m3.material.io/components/search/accessibility)
- Compose API：`SearchBar`、`DockedSearchBar`；Expressive 为 `ExpandedFullScreenSearchBar`、`ExpandedDockedSearchBar`（androidx.compose.material3）
- 核验日期：2026-11-16 ｜ 依据：官网四页全文
- 状态：尺寸为 Specs 原值；颜色清单存在"仅按顺序平铺"的问题，已逐条标注

## 命名与概念

2025 年 2 月改版后，官网把 search bar 与 search view 统称为 **search**：

> "Search bar and search view are now collectively named search"

| 概念 | 是什么 |
|---|---|
| **Search bar** | 输入框本身（未聚焦时可能只是"假输入框"） |
| **Focused search** | 选中入口后进入的搜索态，含建议 / 历史 / 结果 |
| **Search app bar** | 集成在 app bar 里的强调型全局入口，属另一组件 |
| **Style** | 视觉风格：contained / divided |
| **Layout** | 建议与结果的排布：full-screen / docked |

## 两种 Style

> "Contained: Has an expressive look and feel. It uses a filled container to separate a search bar from a list of suggestions or results"
> "Divided (baseline): Doesn't have the latest visual style, motion, or flexibility"

| Style | M3 | M3 Expressive | 结论 |
|---|---|---|---|
| **Contained** | --（无此配置） | ✅ Available | **新设计唯一选择** |
| **Divided** | ✅ Available | "Not recommended. Use contained." | 仅用于维护旧设计 |

注意：contained 是 Expressive 新增的，M3 baseline **没有**它；反过来 divided 在 Expressive 里被明确标为不推荐。不要把两者都说成"两个都可用"。

## 两种 Layout

| Layout | 行为 | 默认场景 |
|---|---|---|
| **Full-screen** | 搜索态占满屏幕 | Compact（手机）默认 |
| **Docked** | 搜索栏停在上方，结果在其下方，并用遮罩盖住主内容 | Medium / Expanded（平板、桌面） |

> "Search suggestions or results should swap from full-screen in compact windows to docked in larger breakpoints."

判断依据是**窗口宽度**，不是设备型号。

## 规格（Specs 原值）

### 未聚焦的 search bar

| 元素 | 属性 | 值 |
|---|---|---|
| Container | 宽度 | **最小 360dp，最大 720dp** |
| Container | 高度 | **56dp** |
| Container | 标签对齐 | 起始 |
| Container | Leading padding | 未聚焦 **24dp**；聚焦 **12dp** |
| Container | Trailing padding | 未聚焦 **24dp**；聚焦 **12dp** |
| Container | 图标/标签到点击区、标签到尾图标的间距 | **4dp** |
| Avatar | 尺寸 | **30dp** |

> "In M3 Expressive, the search bar expands when focused. The margins change from 24dp to 12dp."

**官网表述不一致（需注意）**：Specs 表把 24dp→12dp 写成 `Leading padding` / `Trailing padding`，Guidelines 同一组数字却写成 `The container's margins should be: Unfocused: 24dp; Focused: 12dp`。同一组值不可能既是 padding 又是 margin——⚠️ 推断：24/12 指搜索栏相对窗口或容器的水平边距，聚焦搜索态的内部内边距另算 16dp，三者不要叠加。

### 聚焦后的容器

| 元素 | 属性 | 值 |
|---|---|---|
| Full-screen container | 宽度 | 全宽 |
| Full-screen container | 高度 | 全高 |
| Docked container | 宽度 | **最小 360dp，最大 720dp** |
| Docked container | 高度 | **最小 240dp，最大屏幕高度的 2/3** |
| Search bar container | 高度 | **56dp** |
| Search bar container | 标签对齐 | 起始 |
| Search bar container | Leading padding | **16dp** |
| Search bar container | Trailing padding | **16dp** |

聚焦搜索态里 search bar 容器的左右内边距是 **16dp**（Specs 表），与 24/12 的边距不是同一个量；不要把 16dp 再叠加到 24/12 上。

### 官网缺数字的部分

- divided style 的 full-screen / docked 测量页只有图，**文本没有数值**；
- 圆角半径四页均未给数值（只说 "persistent, rounded corners"）；
- 结果列表的行高、内边距不在 search 页，引用 `lists`。

## 颜色角色

Guidelines 对搜索栏容器有明确结论：

> "Search bars use the surface container high color role. This role applies when the screen background is white or a tonal surface color, ensuring the container has clear contrast."

> "Avoid using a surface container high color on a surface container background. This can cause the search bar to blend in, making it difficult for people to find."

Specs 页按布局列出 6 个角色（未逐项绑定元素）：

| 布局 | 角色清单（原文顺序） |
|---|---|
| Full-screen | Surface container low → On surface variant → On surface variant → Surface container high → On surface variant → On surface |
| Docked | Surface container high → On surface variant → On surface variant → Surface container high → On surface variant → On surface |

按 Anatomy（bar 容器、前置图标、支持文本、后置图标/Avatar、输入文字、结果容器）推断：

| 元素 | 颜色角色 |
|---|---|
| Search bar 容器 | **Surface container high**（guidelines 明确） |
| 前置图标 | On surface variant |
| Supporting text / hint | On surface variant |
| 输入文字 | **On surface** |
| 结果容器 | Surface container high（docked 与 full-screen 清单中均有） |
| Full-screen 视图底色 | **Surface container low**（⚠️ 推断：这是清单首项，且不可能是搜索栏本身） |

⚠️ 这里存在一处官方文本冲突：full-screen 清单首项是 `surface container low`，而 guidelines 说搜索栏用 `surface container high`。合理的解释是首项指"聚焦搜索视图的底层表面"，搜索栏叠在其上；但官网没有写明，落地时以 Figma Design Kit 的绑定为准。

## 使用规则

### 入口形式怎么选

> "The search entry point is dependent on a product's needs, and should be easy to find"

| 入口 | 何时用 |
|---|---|
| **Search bar** | 搜索**特定视图**里的内容，如「搜索你的消息」 |
| **Search app bar** | 搜索是产品的**主要全局功能**，需要常驻强调入口 |
| **Search icon button** | 搜索是次要动作，不是页面焦点 |

- 搜索栏通常放在屏幕顶部，靠近可搜索的内容。
- 不要用"长得像输入框的按钮"骗点击：点它就应进入聚焦搜索态。

### 容器

- Contained 风格下，搜索栏容器在**聚焦与未聚焦时形状一致**：

> "In the contained style, the search bar container remains the same shape in both the unfocused and focused states. Avoid changing the container behavior."

- Divided 风格用一条 divider 分隔搜索栏与结果。
- 容器颜色要与背景至少差**两级** surface container 角色，否则会糊在一起。

### 图标

| 位置 | 规则 |
|---|---|
| Leading | 要么是**导航型 icon button**（菜单、返回箭头），要么是**非功能性搜索图标** |
| Trailing | 一到两个 icon button，最多 **2 个** |
| Avatar | 可与**最多一个**其他尾图标组合 |

可用的尾动作：语音搜索、当前定位/个人资料、overflow 菜单、装饰性搜索图标。聚焦后可出现清除输入的 clear 图标；官方上限是 "Use a maximum of two trailing icons"。

### 文案

- Hinted text 要说明能搜什么：「搜索回复」「搜索你的消息」，不要只写「搜索」。
- 输入后 hinted text 被输入文字替换；聚焦未输入时可显示历史建议，也可以边输入边显示结果，或等执行后再显示——三种都合法，选一种并保持一致。

### 结果

- 结果默认放在 **list** 组件里，可加分类标签、前置图标、头像、filter chips、gap 分组。
- 结果在搜索栏下方滚动，**输入文字保持可见但不再聚焦**（"the input text should remain visible, but not in focus"）。
- 执行搜索：输入后按 Enter，或直接点建议/结果项；返回图标会释放焦点、清除建议并让搜索栏回到原状态。

### 滚动与自适应

- 搜索栏可以随内容滚走、向上滚时再出现，也可以固定在顶部；二选一。
- 宽度随所在 pane 缩放，内部元素左右锚定，不要横贯整个窗口；Android 的 predictive back 会让搜索面从边缘脱离并缩放。

## 无障碍

### 焦点

> "Initial focus lands on the first interactive element. This is often a leading icon button or text field."

- 有前置图标时焦点先落在图标；没有则落在输入框。

| 按键 | 行为 |
|---|---|
| Tab / Shift + Tab | 在可交互元素之间移动 |
| Space / Enter | 激活输入框开始输入 |
| 方向键 | 在搜索结果项之间移动 |

### 标签：hint 就是无障碍标签

> "The hinted search text should be used as the accessibility label describing the search bar."

这一点容易和"placeholder 不能当标签"的通用经验冲突。官方在这里明确要求**用 hinted text 作为标签**：

- hint 写具体：「搜索消息」而不是「请输入关键词」。
- 因为 hint 在输入后消失，**不要把必须长期可见的说明只放在 hint 里**；输入框角色在 Android 为 **Text field**、iOS 为 **Search field**。

### 动态结果播报

> "When search suggestions and results appear, the screen reader must announce the change. This lets people know list items are available for selection."

- 建议/结果出现时必须播报变化，而不是静默刷新列表；结果区要有明确的"正在搜索/结果"状态指示（搜索图标或 Results 标签）。
- 结果用 list 语义，读屏会自动播报为列表；条目按 list 规范标注。
- 清除按钮、前置/后置图标按钮按 icon button 规范标注。

## Compose 对应 API

### 经典两段式（M3 / 早期 Expressive）

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
                    IconButton(onClick = { query = "" }) { Icon(Icons.Default.Close, "清空") }
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

### Expressive 新 API

- `ExpandedFullScreenSearchBar` / `ExpandedDockedSearchBar` + `SearchBarState`（`rememberSearchBarState()`）是较新的一套，参数与经典 `SearchBar` 差异较大。
- `SearchBar`、`DockedSearchBar`、`SearchBarDefaults.InputField` 在多个 material3 版本中标注 `@ExperimentalMaterial3Api`；Expressive 那套更新，注解状态以当前版本为准。
- 输入即搜需要自己做防抖（250–400ms），组件不提供。

## 常见错误

1. 说"contained 和 divided 都可用"——contained 在 M3 baseline 不存在，divided 在 Expressive 被标为不推荐。
2. 大屏上搜索框拉满全屏宽（上限 720dp）。
3. 聚焦后内边距/边距不变化，缺少 Expressive 的展开反馈。
4. 把 24dp / 12dp / 16dp 三组值叠加使用，搜索栏内部被挤扁。
5. 前置换成一个可点但无功能的图标；尾部图标超过两个，或 avatar 之外还堆两个 icon。
6. 搜索栏容器用 `surfaceContainerHigh`，背景也是 `surfaceContainer`，对比不足。
7. 结果出现后输入文字被清空或失去可见性；读屏无法感知建议列表刷新。
8. 把 hit text 写成「请输入关键词」，既没说明搜索范围，也不能当有效标签。
9. 用 search bar 做装饰跳转，点击不进聚焦搜索态。

## 来源

- https://m3.material.io/components/search/overview
- https://m3.material.io/components/search/specs
- https://m3.material.io/components/search/guidelines
- https://m3.material.io/components/search/accessibility
- 以上四页均于 **2026-11-16** 核验
- 关联：`02-Components/app-bars.md`（search app bar）、`02-Components/lists.md`（结果列表语义）

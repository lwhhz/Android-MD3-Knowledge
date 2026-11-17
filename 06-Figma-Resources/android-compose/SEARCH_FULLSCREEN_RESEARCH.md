> **状态：已采纳方案 A（2026-11-18）** —— 新增 `rememberM3SearchBarState()` / 状态化 M3SearchBar + ExpandedFullScreenSearchBar；旧 query 入口保留兼容。

# Search 全屏修复 · 方案调研

> 日期：2026-11-17　范围：仅调研，不改代码　背景：旧 `SearchBar(query, onQueryChange, expanded, …)` 在 `verticalScroll` 中展开会抛约束异常（width 947 / height 557588908），当前用 56/700dp 容器绕过，但不是真正全屏。

## 一、结论先行

1. **1.5.0 已提供状态化 SearchBar**：`rememberSearchBarState()` + `TextFieldState` + `SearchBar(state, inputField)`；旧的 query/onQueryChange 重载在 1.5.0-beta02 起已标记 deprecated。
2. 全屏由 **`ExpandedFullScreenSearchBar(state = sameState, inputField = sameField)`** 负责；它和折叠态共享同一个 `SearchBarState`，展开/收起由 state 驱动。
3. 官方 sample 的用法是把 `SearchBar` 与 `ExpandedFullScreenSearchBar` **并列组合**，展开内容放在后者尾部 lambda。
4. 建议改为状态化 API；如仍遇到滚动约束问题，再退回“全屏 Dialog 包裹”方案。

## 二、调研到的 API 形态

```kotlin
val searchBarState = rememberSearchBarState()
val textFieldState = rememberTextFieldState()
val scope = rememberCoroutineScope()
val inputField = @Composable {
    SearchBarDefaults.InputField(
        textFieldState = textFieldState,
        searchBarState = searchBarState,
        onSearch = { query ->
            onSearch(query)
            scope.launch { searchBarState.animateToCollapsed() }
        },
        placeholder = { Text("Search") },
    )
}
SearchBar(state = searchBarState, inputField = inputField)
ExpandedFullScreenSearchBar(state = searchBarState, inputField = inputField) {
    // suggestions / results
}
```

来源：官方 `SearchBarSamples.kt`（Android Code Search）与 M3 Expressive 迁移文章，代码为公开示例的等价结构，**未复制到本仓库**。

### 与当前实现的差异

| 项 | 当前（旧 API） | 状态化 API |
|---|---|---|
| 输入 | `query: String` + `onQueryChange` | `TextFieldState`（需 `snapshotFlow` 同步或直接暴露 state） |
| 展开 | `expanded: Boolean` + `onExpandedChange` | `SearchBarState.currentValue/targetValue` + `animateToExpanded/Collapsed` |
| 全屏 | SearchBar 自身就地展开，受父约束影响 | `ExpandedFullScreenSearchBar` 独立处理全屏 |
| 建议 | `suggestions` lambda | 同一 lambda，通常 LazyColumn/ListItem |
| API 状态 | 旧重载 deprecated | SearchBar 稳定；ExpandedFullScreenSearchBar 可能仍需 Experimental opt-in |

## 三、接入影响

### 兼容当前公开 API

`M3SearchBar(query, onQueryChange, expanded, onExpandedChange, …)` 可以保留：
- 内部 `rememberTextFieldState(initialText = query)`，用 `snapshotFlow { textFieldState.text }` 回调 `onQueryChange`；
- 内部 `rememberSearchBarState()`，用 `snapshotFlow { state.currentValue }` 回调 `onExpandedChange`；
- 建议列表、avatar、leading/trailing 图标保持不变。

风险：双向同步可能引入循环/抖动，需要 `distinctUntilChanged` 与“外部值变化才写入”的判断。

### 更彻底的做法（推荐评估）

新增状态化入口 `M3SearchBarState` / `rememberM3SearchBarState()`，旧 `M3SearchBar` 保留为便捷封装。这样避免 String 与 TextFieldState 双源。

## 四、可选方案与影响

| 方案 | 内容 | 成本 | 风险 | 建议 |
|---|---|---|---|---|
| **A** | 改用 `rememberSearchBarState` + `ExpandedFullScreenSearchBar`，旧 API 保留封装 | 中 | 双向同步复杂度；Expanded API 实验性 | **推荐先试** |
| **B** | 保留旧 SearchBar，展开时套全屏 `Dialog`，父容器 `fillMaxSize` | 低-中 | 仍需处理 Dialog 内约束；动画不如官方 | A 失败时回退 |
| **C** | 继续 56/700dp 容器 | 低 | 不是全屏，已知约束折衷 | 仅作兜底 |

## 五、验收标准（若采用 A）

1. 折叠态在 `verticalScroll` 中展开**不再崩溃**，App 保持前台；
2. 展开态覆盖**整个窗口**（不是 700dp），搜索栏贴顶、建议列表可滚动；
3. 返回键/关闭按钮可收起；点击建议会回填输入并收起；
4. 现有 `M3SearchBar` 调用点（demo）不需要大改，或只改内部；
5. 单测/构建通过；补一张真机全屏截图；
6. 在 SEARCH_TRIAL 中删除“必须 700dp 容器”的限制，改成状态化 API 说明。

## 六、许可证与合规

- AndroidX Compose Material3 / 官方 samples：**Apache-2.0**；
- 迁移文章站点标注 MIT，但**不复制其代码**，只参考 API 用法；
- 落地代码为本仓库自写，保持 Apache-2.0 + NOTICE。

## 七、来源

- ExpandedFullScreenSearchBar API：https://developer.android.com/reference/kotlin/androidx/compose/material3/ExpandedFullScreenSearchBar.composable
- 官方 SearchBarSamples.kt：https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/material3/material3/samples/src/main/java/androidx/compose/material3/samples/SearchBarSamples.kt
- 示例镜像（含 FullScreenSearchBarScaffoldSample）：https://composables.com/jetpack-compose/androidx.compose.material3/material3/components/ExpandedFullScreenSearchBar/examples.md
- 状态化迁移说明：https://expressive.sanikant.dev/components/search/
- Material3 版本线：https://developer.android.com/jetpack/androidx/releases/compose-material3

## 八、待决策

1. 采用 **A（状态化 API）**，还是先做 **B（Dialog 包裹）**？
2. 公开 API 是否新增状态化入口，还是只改内部、保留现有 query 参数？
3. 是否接受 ExpandedFullScreenSearchBar 的 Experimental opt-in？
# Components / 组件索引

> 官方目录：https://m3.material.io/components
> 核验日期：2026-11-14

## 页面

| 页面 | 覆盖组件 | 状态 |
|---|---|---|
| `buttons.md` | `Button` / `FilledTonalButton` / `ElevatedButton` / `OutlinedButton` / `TextButton` / `IconButton` 系列 | ✅ 参考范例 |
| `cards.md` | `Card` / `ElevatedCard` / `OutlinedCard` | ✅ |
| `dialogs.md` | `AlertDialog` / `BasicAlertDialog` | ✅ |
| `text-fields.md` | `TextField` / `OutlinedTextField` | ✅ |
| `navigation.md` | `NavigationBar` / `NavigationRail` / `NavigationDrawer` / `TopAppBar` | ✅ |
| `lists.md` | `ListItem` / `LazyColumn` 列表模式 | ✅ |
| `menus.md` | `DropdownMenu` / `ExposedDropdownMenuBox` | ✅ |
| `toolbars.md` | `TopAppBar` 四变体 / `BottomAppBar` / `FloatingToolbar` | ✅ |
| `snackbar.md` | `Snackbar` + `SnackbarHost` | ✅ |
| `fab.md` | `FloatingActionButton` / `ExtendedFloatingActionButton` / Large / Medium | ✅ |
| `chips.md` | `AssistChip` / `FilterChip` / `InputChip` / `SuggestionChip` | ✅ 官方原值 |
| `tabs.md` | `PrimaryTabRow` / `SecondaryTabRow` / `Tab` | ✅ 官方原值 |
| `bottom-sheet.md` | `ModalBottomSheet` / `BottomSheetScaffold` | ✅ 官方原值 |
| `search-bar.md` | `SearchBar` / `DockedSearchBar` / 搜索态 | ✅ 官方原值 |
| `selection-controls.md` | `Switch` / `Checkbox` / `TriStateCheckbox` / `RadioButton` | ✅ 官方原值 |
| `slider.md` | `Slider` / `RangeSlider` | ✅ 官方原值 |
| `progress-indicator.md` | `LinearProgressIndicator` / `CircularProgressIndicator` | ✅ |
| `badge.md` | `Badge` / `BadgedBox` | ✅ 官方原值 |
| `expressive-components.md` | `WavyProgressIndicator` / `LoadingIndicator` / `ButtonGroup` 等 | ⚠️ 状态待逐项核验 |
| `component-template.md` | 写新组件页时的模板 | — |

## 每页的固定结构

1. 元信息（官方指南 / Compose API / 核验日期 / 状态）
2. 用途 —— 以及**什么时候不该用它**
3. 变体与层级
4. 结构 / 尺寸
5. 交互状态
6. Compose 实现（可运行）
7. 无障碍
8. 常见错误
9. 来源

`buttons.md` 是完整体例，新增页面请对齐它的结构。

## 尚未覆盖的组件

以下官方组件暂未单独建页，需要时按 `component-template.md` 补写：

`Tooltip`、`DatePicker` / `TimePicker`、`SegmentedButton`、`SwipeToDismissBox`、`ButtonGroup`（Expressive）

## 写新组件页时的要求

- 尺寸与状态数值**必须标注来源和核验日期**；查不到就写「待人工核验」，不要按截图估。
- API 名称必须能在 `androidx.compose.material3` 的 API reference 里搜到。
- 必须有「常见错误」一节——这是同类知识库里最缺、也最有用的部分。
- 如果组件有 expressive 变体，单独说明状态，不要混进稳定 API 里。

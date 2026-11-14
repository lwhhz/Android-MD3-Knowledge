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
| `tooltip.md` | `PlainTooltip` / `RichTooltip` | ✅ 官方原值 |
| `date-time-picker.md` | `DatePicker` / `DateRangePicker` / `TimePicker` / `TimeInput` | ✅ 时间选择器原值 |
| `segmented-button.md` | `SegmentedButton` 单选 / 多选 | ✅ 官方原值 |
| `button-group.md` | Standard / Connected（Expressive） | ⚠️ 尺寸待核验 |
| `swipe-to-dismiss.md` | `SwipeToDismissBox` | ⚠️ 官方无 Specs 页 |
| `carousel.md` | `HorizontalPager` 轮播模式 | ✅ 官方原值 |
| `side-sheet.md` | Side sheet（Modal / Docked） | ✅ 官方原值 |
| `split-button.md` | Split button（Expressive） | ✅ 五档原值 |
| `fab-menu.md` | FAB menu（Expressive） | ⚠️ 部分尺寸待核验 |
| `loading-indicator.md` | Loading indicator（Expressive） | ✅ 官方原值 |
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

## 尚未覆盖

官方组件目录里的主要组件已覆盖。两点说明：

- **Banner 已不在当前 M3 组件目录**：`/components/banners` 与 `/components/banner` 均返回 404，组件索引页里也没有它。历史文档里提到的 banner 不要再引用。
- Expressive 新增组件中尚未单独建页的：`WavyProgressIndicator`（在 `expressive-components.md` 有状态说明）、其余随 1.5.0 转正的组件。

## 写新组件页时的要求

- 尺寸与状态数值**必须标注来源和核验日期**；查不到就写「待人工核验」，不要按截图估。
- API 名称必须能在 `androidx.compose.material3` 的 API reference 里搜到。
- 必须有「常见错误」一节——这是同类知识库里最缺、也最有用的部分。
- 如果组件有 expressive 变体，单独说明状态，不要混进稳定 API 里。

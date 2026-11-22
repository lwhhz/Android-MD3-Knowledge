# Components / 组件索引

> 官方目录：https://m3.material.io/components（37 个组件，多数为 4 个子页；站点 sitemap 385 页，2026-11-22 复核）
> 核验日期：2026-11-22（上一轮 2026-11-16 全量核对）

## 页面

| 页面 | 覆盖组件 | 状态 |
|---|---|---|
| `app-bars.md` | `TopAppBar` 系列（search / small / medium flexible / large flexible） | ✅ 四页核对 |
| `toolbars.md` | Docked toolbar / Floating toolbar（底部） | ✅ 四页核对 |
| `buttons.md` | `Button` / `FilledTonalButton` / `ElevatedButton` / `OutlinedButton` / `TextButton` + Toggle 与五档尺寸 | ✅ 四页核对 |
| `icon-buttons.md` | `IconButton` 四风格 × 三尺寸 | ✅ 官方阶梯值 |
| `fab.md` | `FloatingActionButton` small /（Expressive）medium / large | ✅ 四页核对 |
| `extended-fab.md` | `ExtendedFloatingActionButton` 三档 | ✅ 官方原值 |
| `fab-menu.md` | FAB menu（Expressive） | ✅ 四页核对 |
| `split-button.md` | Split button（Expressive） | ✅ 五档原值 |
| `button-group.md` | Standard / Connected button group（Expressive） | ✅ 四页核对 |
| `segmented-button.md` | `SegmentedButton` 单选 / 多选 | ⚠️ 官方已标「不再推荐」，改用 connected button group |
| `cards.md` | `Card` / `ElevatedCard` / `OutlinedCard` | ✅ 四页核对 |
| `carousel.md` | `HorizontalPager` 轮播模式（六种布局） | ✅ 四页核对 |
| `lists.md` | `ListItem` / `LazyColumn` 列表模式 | ✅ 四页核对 |
| `divider.md` | `HorizontalDivider` / `VerticalDivider` | ✅ 四页核对 |
| `dialogs.md` | `AlertDialog` / `BasicAlertDialog` / full-screen dialog | ✅ 四页核对 |
| `bottom-sheet.md` | `ModalBottomSheet` / `BottomSheetScaffold` | ✅ 四页核对 |
| `side-sheet.md` | Side sheet（standard / modal） | ✅ 四页核对 |
| `menus.md` | `DropdownMenu` / `ExposedDropdownMenuBox` | ✅ 四页核对 |
| `snackbar.md` | `Snackbar` + `SnackbarHost` | ✅ 四页核对 |
| `tooltip.md` | `PlainTooltip` / `RichTooltip` | ✅ 四页核对 |
| `badge.md` | `Badge` / `BadgedBox` | ✅ 四页核对 |
| `search-bar.md` | `SearchBar` / `DockedSearchBar` / 搜索态 | ✅ 四页核对 |
| `tabs.md` | `PrimaryTabRow` / `SecondaryTabRow` / `Tab` | ✅ 四页核对 |
| `chips.md` | `AssistChip` / `FilterChip` / `InputChip` / `SuggestionChip` | ✅ 四页核对 |
| `text-fields.md` | `TextField` / `OutlinedTextField` | ✅ 四页核对 |
| `date-time-picker.md` | `DatePicker` / `DateRangePicker` / `TimePicker` / `TimeInput` | ⚠️ 日期部分测量为图 |
| `selection-controls.md` | `Switch` / `Checkbox` / `TriStateCheckbox` / `RadioButton` | ✅ 四页核对 |
| `slider.md` | `Slider` / `RangeSlider`（五档尺寸） | ✅ 四页核对 |
| `progress-indicator.md` | `LinearProgressIndicator` / `CircularProgressIndicator` + wavy | ✅ 四页核对 |
| `loading-indicator.md` | `LoadingIndicator`（Expressive） | ✅ 四页核对 |
| `scroll-fields.md` | `ScrollField`（Expressive 新增：滚动选值 / 单位，2026-11 上线） | ✅ 四页核对（2026-11-22） |
| `navigation.md` | **选型指南**：三种导航怎么挑 | ✅ |
| `navigation-bar.md` | `NavigationBar` + flexible navigation bar | ✅ 四页核对 |
| `navigation-rail.md` | `NavigationRail` collapsed / expanded | ⚠️ 测量为图 |
| `navigation-drawer.md` | `ModalNavigationDrawer` / `DismissibleNavigationDrawer` / `PermanentNavigationDrawer` | ✅ 四页核对 |
| `swipe-to-dismiss.md` | `SwipeToDismissBox` | ⚠️ 官方无 Specs 页 |
| `expressive-components.md` | Expressive 组件总览 | ✅ |
| `component-template.md` | 写新组件页时的模板 | — |

## 每页的固定结构

1. 元信息（官方指南 / Compose API / 核验日期 / 状态）
2. 用途 —— 以及**什么时候不该用它**
3. 变体与层级
4. 结构 / 尺寸（官方数值带 dp，测量为图时明确标注）
5. 交互状态与颜色角色
6. Compose 实现（可运行）
7. 无障碍
8. 常见错误
9. 来源（四条官方 URL + 核验日期）

## 覆盖情况

官方 **37 个组件**已全部建页（`app-bars` 与 `toolbars` 是两个不同组件，已分篇；2026-11-22 新增 `scroll-fields`）。索引口径：

- **测量图**：官方 Specs 页的部分 measurements 只有标注图、没有文字数值。这类页面在文件里统一写「官方未给 dp，需从 Figma Design Kit 取」，不按截图估算。
- **Segmented button**：Expressive 更新后官方标注不再推荐，新项目用 connected button group。
- **Navigation**：baseline nav bar / rail / drawer 三件套已被 flexible nav bar、collapsed/expanded rail 取代，旧文档不要引用 80dp 之类数字。
- **Banner 已不在组件目录**：`/components/banners` 与 `/components/banner` 均 404，历史文档里的 banner 不要再引用。

## 写新组件页时的要求

- 尺寸与状态数值**必须标注来源和核验日期**；查不到就写「待人工核验」，不要按截图估。
- API 名称必须能在 `androidx.compose.material3` 的 API reference 里搜到。
- 必须有「常见错误」一节——这是同类知识库里最缺、也最有用的部分。
- 如果组件有 expressive 变体，单独说明状态，不要混进稳定 API 里。
- 全站页面覆盖映射见 `00-Metadata/site-map.md`。

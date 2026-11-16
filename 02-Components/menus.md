# Menus / 菜单

- 官方指南：https://m3.material.io/components/menus ｜ [Specs](https://m3.material.io/components/menus/specs) ｜ [Guidelines](https://m3.material.io/components/menus/guidelines) ｜ [Accessibility](https://m3.material.io/components/menus/accessibility)
- Compose API：`DropdownMenu`、`DropdownMenuItem`、`ExposedDropdownMenuBox`（androidx.compose.material3）
- 核验日期：2026-11-16 ｜ 依据：官网四页全文
- 状态：只有 **baseline menu** 的 Measurements 表给了数字；**vertical menu 的测量只有图**，见"规格"一节

## 用途与边界

> "Use a menu to show a temporary set of actions. To show actions on screen at all times, use a toolbar instead."

菜单从触发点展开一组平级动作；它比一组 radio button 或 chips 占空间更少。常见打开方式：icon button、split button、text field、filter chip、选中文本、右键。主操作、需要多步输入的操作、常驻动作都不该放进菜单（分别用按钮、bottom sheet / 页面、toolbar）。

## 变体：vertical 与 baseline

| 变体 | M3 | M3 Expressive | 说明 |
|---|---|---|---|
| **Vertical menus** | -- | ✅ Available | 2025 年 11 月引入，圆角、standard/vibrant 双色系、更丰富选中态、子菜单动效 |
| **Menu (baseline)** | ✅ | ✅ | 方角、旧视觉，新设计不推荐 |

> "In M3 Expressive, baseline menu is still available to use, but doesn't have the latest shapes, color styles, selection states, and motion."
> "A baseline menu has square corners, as compared to a vertical menu's round corners and expressive styling."

Color 的 **Vibrant** 与 Layout 的 **Grouped** 只在 M3 Expressive 提供（Specs 配置表原值）。**新项目一律用 vertical menu**；只有维护老设计时才碰 baseline。

## 规格

### Baseline menu（唯一有数字的一套）

| 属性 | 值 |
|---|---|
| 容器宽度 | **最小 112dp，最大 280dp** |
| 圆角 | **4dp** |
| 纵向标签对齐 | 居中 |
| 横向标签对齐 | 起始 |
| 左右内边距 | **12dp** |
| 带图标时左右内边距 | **12dp** |
| 列表项高度 | **48dp** |
| 列表项内元素间距 | **12dp** |
| Divider 上下内边距 | **8dp** |
| Divider 高度 | **1dp** |
| 前 / 后置图标尺寸 | **24dp** |

注意左右内边距是 **12dp**（带图标也是 12dp），不是 12–16dp 区间。

### Vertical menu

> "Vertical menu padding and size measurements"

Specs 页在 vertical menu 下只放了两张测量图（`Vertical menu padding and size measurements`），**没有任何数字表格**。因此：

- 圆角具体值、项高、内边距、gap 高度，官网文本均未给出；
- 只能从图里量，或直接读 Figma Design Kit；
- 不要拿 baseline 的 48dp / 280dp / 4dp 直接套到 vertical menu 上（⚠️ 推断：两者形状与布局不同）。

Elevation 方面，菜单在官方 elevation 分级表中属 Level 2（3dp）；该数值不在 menus 页面，本仓库引自 `01-Foundations/elevation.md`。

## 颜色角色

菜单有两套颜色映射，官网明确说明视觉权重不同：

> "Standard: Surface-based, lower visual emphasis"
> "Vibrant: Tertiary-based, higher visual emphasis"
> "Vibrant menus are more prominent, and should be used sparingly."

### Vertical menu（⚠️ 元素归组为推断）

Specs 按 Anatomy 顺序平铺了 11 个角色，未逐项标注。下表左列是归组后的用途：

| 用途 | Standard | Vibrant |
|---|---|---|
| 容器 | **Surface container low** | **Tertiary container** |
| 菜单项文字 | **On surface** | **On tertiary container** |
| 前置/后置图标、支持文本、标签文本 | **On surface variant** | **On tertiary container** |
| 状态层（state layer） | **On surface**（state layer） | On tertiary container（state layer） |
| 选中项容器 | **Tertiary container** | **Tertiary** |
| 选中项文字/图标 | **On tertiary container** | **On tertiary** |

### Baseline menu

| 用途 | 颜色角色 |
|---|---|
| 容器 | **Surface container** |
| 文字 / 图标 / 支持文本 | On surface / On surface variant（⚠️ 清单 9 项未逐项绑定） |
| 状态层 | **On surface，opacity 0.08** |
| 焦点指示 | Surface container highest（⚠️ 推断） |
| Divider | **Outline variant** |

**关键差异**：baseline 容器是 `surfaceContainer`，vertical standard 容器是 `surfaceContainerLow`，vibrant 是 `tertiaryContainer`。三者不能混用。

## 使用规则

### 菜单项

- 不可用的动作**置灰而不是删除**（"it should appear disabled rather than be removed"），否则用户会以为功能消失。
- 菜单项文字就是读屏标签，不要再额外加 `contentDescription`。
- Vertical menu 的 Anatomy 含 **Supporting text（可选）** 与 **Label text（可选）**；baseline 的 Anatomy 没有 supporting text。需要两行解释时用 vertical menu 或 bottom sheet，不要硬塞进 baseline 菜单。

### 分组：gap 与 divider

| 方式 | 适用 | 限制 |
|---|---|---|
| **Gap** | 需要更强表达力的分组 | 不要改 gap 大小；一个菜单最多 1–2 个；**可滚动菜单里不能用**；Web 暂不支持 |
| **Divider** | 可滚动菜单、带下拉的 text field、Web | 更轻微的分隔 |

> "Use a gap to visually divide menu items into distinct groups. Gaps are more expressive than dividers and make the relationship between items clear."
> "Don't use gaps in scrollable menus."

### 自定义插槽（slots）

> "Targets should be 48x48dp or larger."
> "Don't add buttons, switches, or other direct actions into the menu item. Nested elements should only perform one action. Adding multiple actions can break keyboard navigation and screen reader functionality."

- 插槽适合图片、进度指示器、色块等**简单内容**。
- 保持菜单项内边距一致；不要在菜单项里再放按钮/开关。

### 定位与子菜单

> "If a menu is in a position to be cut off, it should automatically reposition to appear to the left, right, or above the element that generates it."
> "Submenus should open next to the parent menu item without overlapping it."

- 菜单相对窗口边缘定位，通常出现在触发元素下方、旁边或前方；子菜单**不覆盖父项**，从侧边展开。
- 小屏上子菜单体验差，考虑 bottom sheet。

> "Submenus are not currently available on Jetpack Compose."

### 自适应

- Compact：可把菜单改成 bottom sheet（更多空间、更长标签）。
- Medium / Expanded：菜单就地展开最有效，可用子菜单组织复杂选项。

### 行为

- 靠近屏幕顶部时向下展开，避免被裁切；enter/exit 过渡建立与触发元素的关系，展开时触发元素呈 pressed。
- 选中后触发元素外观不变，只是加 pressed 态。
- 可在菜单内放 text field 做 autocomplete；输入时选项平滑移动到新位置。
- 放不下时可滚动，并显示**常驻滚动条**。
- 单选选新项自动取消旧项；多选保持打开直到用户主动关闭。
- 桌面等密集界面可**立即打开**以减少动效；密度（density）仅 Web 支持。

## 无障碍

### 焦点

> "When a menu opens, focus should be placed on the first menu item."

> "People expect to exit a menu by: Selecting an option; Tapping Escape or outside of the menu; Using the system back button."

> "Where focus is placed after closing the menu depends on the app."

- **关闭后焦点去哪由应用决定**，官方并没有要求"必须回到触发按钮"。回到触发元素是常见且稳妥的选择，但要自己实现。
- 禁用项**可以获得焦点但不可选中**；divider 和 gap **不能获得焦点**。

### 键盘

| 按键 | 行为 |
|---|---|
| Tab | 焦点落到菜单 |
| Space / Enter | 关闭状态：打开菜单/子菜单；打开状态：选中当前项 |
| ↑ / ↓ | 关闭状态：打开菜单；打开状态：移动到下一项 |
| ← / → | 打开或关闭子菜单 |
| 字母键 | 跳到以该字母开头的下一个菜单项 |
| Escape | 关闭菜单 |

### 选中态的视觉提示

> "By default, menu items change shape and color when selected"
> "The default color contrast is 3:1 between selected and unselected menu items"
> "It's recommended to include another visual cue, like a checkmark"

选中/未选中的颜色对比至少 **3:1**；除颜色外再加一个线索（对勾、形状），不要只靠颜色。

### 读屏语义

> "The accessibility label should be the same as the menu item text."

> "For menu items with text and an icon, the icon's accessibility label should be marked as decorative to avoid redundant verbalizations."

| 元素 | A11y 标签 | Web 角色 | Android Views 角色 | Compose 角色 |
|---|---|---|---|---|
| Menu item text | 同 UI 文字 | Menu item | Generic actionable element | Generic actionable element |

- 文字 + 图标时，图标设 `contentDescription = null`；触发按钮必须有语义（「更多操作」），不能只有一个三点图标。

## Compose 对应 API

### 普通菜单

```kotlin
Box {
    IconButton(onClick = { expanded = true }) {
        Icon(Icons.Default.MoreVert, contentDescription = "更多操作")
    }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        DropdownMenuItem(text = { Text("重命名") }, onClick = { expanded = false; onRename() })
        DropdownMenuItem(text = { Text("导出") }, onClick = { expanded = false; onExport() })
        HorizontalDivider()
        DropdownMenuItem(text = { Text("删除") }, onClick = { expanded = false; onDelete() })
    }
}
```

### 选择式下拉

```kotlin
ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
    OutlinedTextField(
        value = selected,
        onValueChange = {},
        readOnly = true,
        label = { Text("排序方式") },
        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
        modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable)
    )
    ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        options.forEach { option ->
            DropdownMenuItem(text = { Text(option.label) }, onClick = { onSelect(option); expanded = false })
        }
    }
}
```

| API | 状态（以当前 material3 版本为准） |
|---|---|
| `DropdownMenu` / `DropdownMenuItem` | 稳定 |
| `ExposedDropdownMenuBox` / `ExposedDropdownMenu` | 多个版本中为 `@ExperimentalMaterial3Api`，编译前确认 |
| 子菜单 | 官方明确 Compose 暂不支持 |
| `menuAnchor` | 1.3 后需要 `MenuAnchorType` 参数，旧的 `menuAnchor()` 已废弃 |

- 多选菜单要保持打开：不要在 `onClick` 里关 `expanded`，只在用户点外部/返回时关。

## 常见错误

1. 把「设置」「关于」这类导航入口塞进菜单——那是 drawer/页面的职责。
2. 菜单项超过 10 个，用户点开才能知道有没有自己要的；展开菜单后 `onClick` 忘了 `expanded = false`（多选菜单除外）。
3. 用 gap 做滚动菜单的分组，或一个菜单里放 3 个以上 gap。
4. 在菜单项里嵌开关或按钮，键盘与读屏全部失效；把不可用项直接删掉，而不是置灰。
5. Baseline 菜单套 28dp 大圆角，或 vertical 菜单用 4dp 方角——两者不能混。
6. 用 `surface` 做 vertical menu 容器（官方是 `surfaceContainerLow`）。
8. 选中态只靠颜色（对比 3:1 是底线，还要加对勾等第二线索）。
9. 认为关闭菜单后焦点会自动回触发按钮——Compose 不保证，需要自己管理。

## 来源

- https://m3.material.io/components/menus/overview
- https://m3.material.io/components/menus/specs
- https://m3.material.io/components/menus/guidelines
- https://m3.material.io/components/menus/accessibility
- 以上四页均于 **2026-11-16** 核验
- 关联：`01-Foundations/elevation.md`（菜单 Level 2）、`02-Components/lists.md`（列表与菜单的边界）

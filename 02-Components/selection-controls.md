# Selection Controls / 选择控件（Switch · Checkbox · Radio）

- 官方指南：https://m3.material.io/components/switch ｜ https://m3.material.io/components/checkbox ｜ https://m3.material.io/components/radio-button
- Compose API：`Switch` / `Checkbox` / `TriStateCheckbox` / `RadioButton`（均稳定）
- 核验日期：2026-11-16 ｜ 核验方式：三组件各 4 个官方页面（overview/specs/guidelines/accessibility）全文 dump
- 状态：**尺寸全部为官方原值**

## 先选对控件

官方对三者的分工（三个 guidelines 页共用同一节「Alternate selection controls」）：

| 问题 | 控件 |
|---|---|
| 单个设置项**立即开/关**（如飞行模式） | **Switch** |
| **多选**相关选项、可成组、占空间更小 | **Checkbox** |
| **少量互斥**选项里单选（≤5 个，全部暴露） | **Radio** |
| 两个**对立**选项（如列表/地图视图） | 连接按钮组（Connected button group），不是 Switch |
| >5 个互斥选项 | 下拉菜单（点击与认知成本更高） |

> The effects of a switch should start immediately, without needing to save.

**Switch = 立即生效；需要点"保存"的多选用 Checkbox；单选列表用 Radio。**Switch 控制的是二元（on/off）而非对立选项，也不能替代按钮（call to action 必须是按钮）。

## Switch / 开关

### 规格（官方 Measurements 原值）

| 元素 | 属性 | 值 |
|---|---|---|
| Track | 高 × 宽 | **32 × 52dp** |
| Track | 描边宽度 | **2dp** |
| Track / Handle / 状态层 | 形状 | `md.sys.shape.corner.full`（胶囊） |
| Handle | 高（未选中 / 选中 / 带图标） | **16 / 24 / 24dp** |
| Handle | 高（**按下**） | **28dp** |
| Handle | 宽 | 与高同值（16 / 24 / 24 / 28dp） |
| 状态层 | 尺寸 | **40dp** |
| 触控目标 | 尺寸 | **48dp** |
| 图标 | 尺寸（选中/未选中） | **16dp** |

配置三档：无图标 / 仅选中态带图标 / 两态都带图标。**命名**：官方现叫 Handle（旧名 thumb）。
handle 动画是官方手感：未选中 16 → 选中 24 → 按下 28，按压增长提供交互反馈。

### 颜色角色（官方原值）

官方列出的角色：`surface container highest` · `outline` · `primary` · `on primary` · `on primary container`。
⚠️ 推断，页面未明示映射：未选中轨道 = `surfaceContainerHighest` + `outline` 描边；选中轨道 = `primary`；handle = `onPrimary`；handle 图标 = `onPrimaryContainer`。

> Use the color role on surface for adjacent text labels. This remains the same even if interacting with the label or component.

相邻标签固定 `onSurface`，辅助说明 `onSurfaceVariant`。

### 使用规则

1. 只做**二元**开关（on/off、true/false）；立即生效，无需保存。
2. handle 里的图标必须清楚表达开/关，官方举例 **X 和 checkmark**；避免模糊图标（月亮、编辑铅笔）。
3. **永远配内联标签**，标签写清「打开时做什么」，保持短而直接；**禁止把文字放进开关本体**（字号小到不可访问，用图标代替）。
4. 设置页等堆叠布局是典型场景。

### 无障碍

- AT 用户应能：导航到开关、切换状态、按输入类型获得反馈。
- **键盘**：Tab 焦点直接落 handle；**Space 或 Enter** 切换。
- **密度**：不要默认压缩密度，会把触控目标压到 48×48 CSS 像素以下；改密度须用户可选且可撤销。
- 读屏读到「相邻 UI 文字 + 角色」；**可见文字含糊时无障碍标签要更具体**（官方例：可见标签 "Photo album" → 无障碍标签 "Photo album access"）。

## Checkbox / 复选框

### 规格（官方 Measurements 原值）

| 属性 | 值 |
|---|---|
| 容器尺寸 | **18dp** |
| 容器圆角 | **2dp** |
| 图标尺寸 | **18dp**（居中对齐） |
| 状态层尺寸 | **40dp** |
| 触控目标 | **48dp** |

状态含未选 / 选中 / **indeterminate**，且 M3 新增三种状态的 **error 态**（overview 页）。Specs 页颜色区只列了 Container/State-layer/Icon 部件名**无角色文字**（仅图），勿引用具体角色。

### 使用规则

1. 用于：多选列表、含子选择的列表、桌面端单项开关、视觉分组。
2. **列表多选优先 Checkbox 而非 Switch**（暗示相关、更省空间）。
3. **父子联动**：父选中→全部子选中；父取消→全部子取消；部分子选中→父为 indeterminate；勾选 indeterminate 父项会全选子项。
4. 用于开/关某项时动作立即执行。

### 无障碍

- **文字标签和框体都能点击选中**；三态：selected / unselected / indeterminate；组内各框互不影响。
- 密度警告同 Switch（48×48 CSS 像素）。
- ⚠️ 官方 Accessibility 页的「Keyboard navigation」表格内容是 **Chip** 的键位（Tab/Arrows/Backspace 描述 chip group），疑为页面误植，**不采信**——Checkbox 键盘行为以通用 Space/Enter 激活为准，页面未给出专属键位表。
- 读屏读到「UI 文字 + 角色」；无障碍标签与相邻文字标签一致。

## Radio Button / 单选按钮

### 规格（官方 Measurements 原值）

| 属性 | 值 |
|---|---|
| 图标尺寸 | **20dp** |
| 状态层尺寸 | **40dp** |
| 触控目标 | **48dp** |

**Checkbox 18dp、Radio 20dp** 的不对称是官方设定，不要"统一"。结构：选中图标 / 未选中图标 / 相邻标签。

### 颜色角色（官方原值）

`primary` · `on surface variant`。⚠️ 推断，页面未明示映射：选中态 `primary`，未选中图标 `onSurfaceVariant`。相邻标签固定 `onSurface`。

### 使用规则

1. **单选、全部暴露选项**；同时只能选一个；**禁止嵌套** radio。
2. **≤5 个选项用 radio**；空间紧张改下拉（更多点击与认知成本）。
3. **每个选项必须有相邻标签**（每个选择各自有标签）。
4. **垂直堆叠，并始终预选一项**；避免水平排列。
5. 立即生效，除非在需要保存的对话框/页面中。
6. **选中后组内不可反选**——要允许退出选择需提供 "Not applicable"/"No" 选项或单独的「清除选择」入口。

### 无障碍

- **键盘**（官方表）：

| 键 | 行为 |
|---|---|
| Tab | 焦点进入组内：到已选项；无选中则到第一项 |
| Shift + Tab | 到已选项；无选中则到最后一项 |
| 方向键 | 移动焦点**并选中**上/下一项，首尾循环（wrap） |
| Space | 选中聚焦项；已选中则无操作 |

- **组语义**：组的无障碍标签 = 分类标题，角色 **Radio group**；单选项标签 = 相邻文字。
- 初始焦点规则同上表；密度警告同前（48×48 CSS 像素）。

## Compose 实现（三件套通用）

```kotlin
// Switch / Checkbox：整行可点，控件本身 onCheckedChange = null
Row(
    modifier = Modifier
        .fillMaxWidth()
        .toggleable(value = checked, role = Role.Switch, onValueChange = ::onChecked)
        .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    Column(Modifier.weight(1f)) { /* 标签 + onSurfaceVariant 辅助说明 */ }
    Switch(checked = checked, onCheckedChange = null)  // 由外层 toggleable 接管
}

// Radio：必须成组
Column(Modifier.selectableGroup()) {          // 读屏才知道这是单选组
    options.forEach { option ->
        Row(Modifier.selectable(
            selected = option == selected,
            role = Role.RadioButton,
            onClick = { onSelect(option) }
        )) {
            RadioButton(selected = option == selected, onClick = null)
            Text(option.label)
        }
    }
}
```

- `toggleable` / `selectable` 挂在外层让**文字也可点**且读屏只报一次；避免 Switch 自己处理 + 外层再包 toggleable 的双触发。
- 三态父项用 `TriStateCheckbox`，indeterminate 配文字说明。
- Compose `RadioButton` 单击即选中且组内互斥；「不可反选」由组语义自然成立。

## 常见错误

1. 用 Switch 做要保存才生效的设置；用 Checkbox 做立即开关。
2. Switch 表达对立选项（应按钮组）或替代按钮。
3. handle 图标用月亮/铅笔这类模糊语义。
4. Radio 未包 `selectableGroup()`，读屏读不出组关系；或未预选一项。
5. 没给 radio「取消选择」的出口（官方要求 Not applicable 选项或清除入口）。
6. 把 18/20dp 的框体本身当触控目标（应为整行 ≥48dp）。
7. 把 Checkbox 和 Radio 尺寸强行统一。
8. indeterminate 无文字说明；radio 水平排列。
9. 默认压缩密度，触控目标 < 48×48。

## 来源

- https://m3.material.io/components/switch/overview | /specs | /guidelines | /accessibility
- https://m3.material.io/components/checkbox/overview | /specs | /guidelines | /accessibility
- https://m3.material.io/components/radio-button/overview | /specs | /guidelines | /accessibility
- 核验日期：2026-11-16

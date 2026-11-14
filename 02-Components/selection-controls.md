# Selection Controls / 选择控件（Switch · Checkbox · Radio）

- 官方指南：https://m3.material.io/components/switch ｜ https://m3.material.io/components/checkbox ｜ https://m3.material.io/components/radio-button
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-15 ｜ 核验方式：Chrome CDP 读三个官方 Specs 页
- 状态：**尺寸测量全部为官方原值**

## 先选对控件

这三个控件最容易互相误用。判断顺序：

| 问题 | 控件 |
|---|---|
| 这个开关是**立刻生效**的吗？（如飞行模式） | **Switch** |
| 要**多选**，且最后统一提交？ | **Checkbox** |
| 从**少量互斥**选项里选一个？ | **Radio** |
| 从**较多**选项里选一个（>5 个）？ | 下拉菜单 / 列表页，不是 Radio |

**核心区别**：Switch 是「立即改变状态」，Checkbox 是「先勾选，之后提交」。用 Switch 做需要点"保存"才生效的设置，用户会以为已经生效了。

反过来，用 Checkbox 做立即生效的开关，用户会以为还要提交。

## 尺寸（官方 Measurements 原值）

### Switch

| 属性 | 值 |
|---|---|
| 高度 | **32dp** |
| 宽度 | **52dp** |
| 描边宽度 | **2dp** |
| 形状 | `md.sys.shape.corner.full`（胶囊） |
| Handle 高度（未选中） | **16dp** |
| Handle 高度（选中） | **24dp** |
| Handle 高度（带图标） | **24dp** |
| Handle 高度（**按下**） | **28dp** |
| Handle 宽度（未选中/选中） | 16dp / 24dp |
| Handle 宽度（**按下**） | **28dp** |
| 状态层尺寸 | **40dp** |

注意 handle 的三个尺寸：**未选中 16 → 选中 24 → 按下 28**。这就是 M3 里开关"按住会变大"的手感来源，不是随意的缩放。

**命名变更**：官方现在叫 **Handle**，以前叫 **thumb**。看到旧文档写 thumb 指的是同一个东西。

### Checkbox

| 属性 | 值 |
|---|---|
| 容器尺寸 | **18dp** |
| 容器圆角 | **2dp** |
| 图标尺寸 | **18dp** |
| 图标对齐 | 居中 |
| 触控目标 | **48dp** |
| 状态层尺寸 | **40dp** |

支持三态：未选中 / 已选中 / **indeterminate（部分选中）**。`TriStateCheckbox` 用于"全选"父项——子项有的选有的没选时显示横线。

### Radio

| 属性 | 值 |
|---|---|
| 图标尺寸 | **20dp** |
| 状态层尺寸 | **40dp** |
| 触控目标 | **48dp** |

**Checkbox 18dp、Radio 20dp**——这个不对称是官方的，不要为了"统一"把两者改成一样大。

三者触控目标都是 48dp，状态层都是 40dp。

## 颜色角色（Switch，官网原文）

`surfaceContainerHighest` · `outline` · `primary` · `onPrimary` · `onPrimaryContainer`

- 未选中轨道：`surfaceContainerHighest` 填充 + `outline` 描边
- 选中轨道：`primary`
- handle：`onPrimary`（选中态）
- handle 上的图标：`onPrimaryContainer`

**标签颜色**：相邻文字标签统一用 `onSurface`，无论是否与控件交互；辅助说明用 `onSurfaceVariant`。

## Compose 实现

### Switch（整行可点）

```kotlin
Row(
    modifier = Modifier
        .fillMaxWidth()
        .toggleable(
            value = checked,
            role = Role.Switch,
            onValueChange = onCheckedChange
        )
        .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    Column(Modifier.weight(1f)) {
        Text("自动同步", style = MaterialTheme.typography.bodyLarge)
        Text(
            "仅在 Wi-Fi 下同步",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
    Switch(checked = checked, onCheckedChange = null)  // ← null：由整行处理
}
```

关键点：`onCheckedChange = null` 让 Switch 自己不处理点击，由外层 `toggleable` 接管。这样**点文字也能切换**，且读屏只读一次。

### Checkbox（多选列表）

```kotlin
Row(
    modifier = Modifier
        .fillMaxWidth()
        .toggleable(value = checked, role = Role.Checkbox, onValueChange = onCheckedChange)
        .padding(16.dp)
) {
    Checkbox(checked = checked, onCheckedChange = null)
    Spacer(Modifier.width(8.dp))
    Text("同意服务条款")
}
```

### Radio（必须成组）

```kotlin
Column(Modifier.selectableGroup()) {   // ← 别忘了，否则读屏不知道这是一组
    options.forEach { option ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected = option == selected,
                    role = Role.RadioButton,
                    onClick = { onSelect(option) }
                )
                .padding(16.dp)
        ) {
            RadioButton(selected = option == selected, onClick = null)
            Spacer(Modifier.width(8.dp))
            Text(option.label)
        }
    }
}
```

## 无障碍

- **整行可点**，不要只让 18dp 的小方块可点。
- 用 `toggleable(role = Role.Switch/Checkbox)` 或 `selectable(role = Role.RadioButton)`，让读屏正确报出控件类型和状态。
- Radio 组必须包 `Modifier.selectableGroup()`，读屏才知道"3 个选项中的第 1 个"。
- 标签在右侧时，读屏顺序为「标签 → 状态」，符合预期。
- 状态变化不要只靠颜色：Switch 有位置变化、Checkbox 有对勾、Radio 有实心圆点。
- 三态 Checkbox 的 indeterminate 要有文字说明（如"部分已选"）。

## 常见错误

1. 用 Switch 做需要点"保存"才生效的设置（用户以为已生效）。
2. 用 Checkbox 做立即生效的开关。
3. 用 Radio 呈现 10 个选项（应该用下拉或列表页）。
4. Radio 没包 `selectableGroup()`，读屏读不出组关系。
5. 只有小方块可点，文字不可点。
6. Switch 自己处理点击，外层又包了一层 `toggleable`，点击触发两次。
7. 把 Checkbox 和 Radio 的尺寸"统一"成一样（官方是 18 / 20dp）。
8. 用 `Switch` 表达"选择"而不是"开关"——它没有"未选择"这个中间态。
9. indeterminate 状态没有说明文字，用户不知道横线是什么意思。

## 来源

- https://m3.material.io/components/switch/specs
- https://m3.material.io/components/checkbox/specs
- https://m3.material.io/components/radio-button/specs
- `01-Foundations/accessibility.md`（触控目标与语义）

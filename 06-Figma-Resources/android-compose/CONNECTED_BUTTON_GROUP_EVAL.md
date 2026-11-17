> **状态：已实施方案 A（2026-11-18）** —— 新增 `M3ConnectedButtonGroup`，`M3SegmentedButton` 保留；实现记录见 CONNECTED_GROUP_TRIAL.md。

# Connected Button Group 替代评估

> 日期：2026-11-18　范围：是否用 connected button group 替代/补充当前 Segmented button　状态：**仅评估，不改代码**

## 一、结论先行

1. **不建议直接替换 `M3SegmentedButton` 的现有 API**：两者的“尺寸/密度”语义不一致（Segmented 是 40dp 高度、每档 -4dp；Button group 是 XS–XL 的五档按钮尺寸），替换会破坏已验收的 Density 参数。
2. **建议新增 `M3ConnectedButtonGroup`**，与 segmented 并存：
   - segmented 保留为“存量 UI / 旧设计”路径；
   - connected group 作为 Expressive 推荐路径，覆盖 round/square、XS–XL、单选/多选/必选。
3. 若只想维护一套组件，可做**兼容包装层**把 `selectedIndex`/`density` 映射到 ButtonGroup，但必须接受密度不再逐档 -4dp；不建议本阶段做。

## 二、Material3 1.5.0-rc01 API 事实（javap 核对）

### ButtonGroup

```kotlin
ButtonGroup(
    overflowIndicator = { state -> ButtonGroupDefaults.OverflowIndicator(state) },
    modifier = Modifier,
    spaceBetween = 2.dp,
    content = {
        toggleableItem(
            checked = selected,
            label = "Day",
            onCheckedChange = { ... },
            shapes = ButtonGroupDefaults.connectedLeadingButtonShapes(),
        ) { Text("Day") }
    },
)
```

- 组容器：`ButtonGroupScope`，提供 `clickableItem` / `toggleableItem` / `customItem`；
- 形状：`ButtonGroupDefaults.connectedLeading/Middle/TrailingButtonShapes()`，另有 checked/pressed shape；
- 组内间距：`ButtonGroupDefaults.connectedSpaceBetween`（官方 2dp）；
- 溢出：`ButtonGroupDefaults.OverflowIndicator(menuState)` 已提供；
- `ButtonGroup` 类在 1.5.0-rc01 中**未见 Experimental 注解**；`ToggleButton`/Overflow 的注解状态仍需编译验证。

### 当前 Segmented Button

- `SingleChoiceSegmentedButtonRow` / `MultiChoiceSegmentedButtonRow` + `SegmentedButton`；
- 已实现 2–5 段、单选/多选、Density 4 档（40/36/32/28dp）、选中对勾；
- 官方已声明 **Expressive 后不再推荐**，替代品就是 connected button group。

## 三、规格差异（决定能否替换）

| 维度 | Segmented button（当前） | Connected button group |
|---|---|---|
| 官方定位 | 存量，不推荐 | Expressive 推荐，明确取代 segmented |
| 高度 | 40dp；Density 每档 -4dp（36/32/28） | 跟随内部按钮尺寸 XS–XL（32/40/56/96/136） |
| 段间距 | 拼接（0） | 2dp |
| 圆角 | `itemShape` 首尾/中间 | 4/8/8/16/20dp（按尺寸档），round/square |
| 最小宽 | 48dp 触控 | 48dp（XS/S 靠间距保证） |
| 宽度 | 内容自适应 | 撑满容器（可设最大宽度） |
| 选择模式 | 单选/多选 | 单选/多选/selection-required |
| 溢出 | 无（2–5 段约束） | 官方 overflow indicator + menu state |
| 键盘/无障碍 | 方向键、双通道对勾 | 官方组语义、逐按钮导航、48dp |

**关键冲突：Density。** Segmented 的 40→28dp 是“密度”，ButtonGroup 没有密度步进，只有按钮尺寸 XS–XL；两者不能一一映射。

## 四、可选方案

| 方案 | 内容 | 成本 | 风险 | 建议 |
|---|---|---|---|---|
| **A** | 新增 `M3ConnectedButtonGroup`，保留 segmented | 中（约 150–250 行 + demo + 单测） | 两套并存；API 学习成本 | **推荐** |
| B | 用 ButtonGroup 重写 `M3SegmentedButton` 内部，保留签名 | 中 | Density 无法保持；视觉/选中态可能变化；已有 21 项单测需改 | 不推荐 |
| C | 直接移除 segmented，只留 connected | 低（删代码） | 破坏存量调用方；丢失 2/3/4 段与 Density 语义 | 不推荐 |
| D | 不做 connected，只保留 segmented | 0 | 与 Expressive 推荐方向相反；缺口表继续缺 5 个参数 | 不推荐 |

## 五、若选 A 的实施边界

1. 新组件 `M3ConnectedButtonGroup`：
   - 参数：`segments`、`selectedIndices`、`selectionMode`（single / multi / required）、`size`（XS–XL）、`shape`（round / square）、`overflow`；
   - 用 `ButtonGroup` + `toggleableItem` + `connected*ButtonShapes`；
   - 组宽撑满，最大宽度按需。
2. `M3SegmentedButton` 保留现状并标 deprecated（文档层），不删 API。
3. 验收：真机截图（round/square、XS/M、单选/多选、overflow）、单测（2dp 间距、圆角档、48dp 目标）、参数表更新。
4. 预计新增 1 个测试类、1 个 demo 区、1 份 TRIAL；总测试从 21 → 22+。

## 六、许可证与依赖

- `ButtonGroup` / `ToggleButton` 属 AndroidX Compose Material3，**Apache-2.0**；
- 不引入新依赖（已在 material3 1.5.0-rc01）；
- 无第三方代码复制。

## 七、待用户决策

1. 选 **A（新增 connected，保留 segmented）**、B（重写 segmented）还是 D（不做）？
2. 若选 A：`selectionMode` 是否需要 `selection-required`（必须保持一项选中）？
3. 若选 A：Size 五档是否全做，还是先做 M/L 两档？

## 八、来源

- KB `button-group.md`（官方四页 dump，2026-11-16）
- KB `segmented-button.md`（不推荐声明）
- 本地 material3-android-1.5.0-rc01 `classes.jar` javap：`ButtonGroupKt`、`ButtonGroupDefaults`、`ButtonGroupScope`、`ToggleButtonKt`
- 官方 API 参考：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
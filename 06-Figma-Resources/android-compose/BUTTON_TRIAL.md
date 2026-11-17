# Button 家族试点记录（阶段 1）

> 日期：2026-11-17　范围：`M3Button` + Text / Elevated / Outline / Tonal 四个命名变体　结论：**5 种样式全部实现，7/7 × 5 规格参数落地**；编译、单测、真机截图均通过。

## 一、Button 家族映射

Figma 的 Buttons 页把样式拆成 5 个组件，实现上收敛为 1 个 `M3Button(style=…)` + 4 个命名包装：

| Figma 组件 | 实现入口 | 样式 |
|---|---|---|
| Button | `M3Button` | `M3ButtonStyle.FILLED` |
| Tonal button | `M3ButtonTonal` | `TONAL` |
| Elevated button | `M3ButtonElevated` | `ELEVATED` |
| Outline button | `M3ButtonOutline` | `OUTLINED` |
| Text button | `M3ButtonText` | `TEXT` |

## 二、规格 → 实现（每个组件 7/7）

| Figma 参数 | 类型 | 实现 |
|---|---|---|
| `Label text` | 文本 | `label: String` → `labelLarge`，单行不换行 |
| `Show icon` | 布尔 | `showIcon: Boolean` |
| `Icon` | 实例替换 | `icon: Painter?`，20–40dp 按档位、`ColorFilter.tint(content)` |
| `Show focus indicator` | 布尔 | Focused 时画 3dp 环 |
| `Type` | Round / Square | `shape: M3ButtonShape` |
| `Size` | XS–XL | `size: M3ButtonSize` |
| `State` | 5 态（Enabled/Hovered/Focused/Pressed/Disabled） | 运行时由 interaction source 推导；预览可 `M3ButtonVisual(state=…)` 强制 |

## 三、官方数值（D2）

| 项 | 值 |
|---|---|
| 五档高度 / 内边距 | 32/40/56/96/136 dp；12/16/24/48/64 dp |
| 图标尺寸 / 间距 | 20/20/24/32/40 dp；4/8/8/12/16 dp |
| 圆角（方角）/ 按下 | 12/12/16/28/28 dp；8/8/12/16/16 dp（Round 与 Square 一致） |
| 状态层 | Hover 8% / Focus 10% / Press 10% / Drag 16% / Disabled 38% |
| 状态层尺寸 | 视觉 40dp、触控热区 48dp |
| 五种样式的颜色角色 | filled: primary/onPrimary；tonal: secondaryContainer/onSecondaryContainer；elevated: surfaceContainerLow/primary + elevation 1；outlined: 透明 + outlineVariant 描边 + onSurfaceVariant；text: 透明 + primary |
| 禁用 | 容器 onSurface 12%、内容 onSurface 38%；outlined 描边同步降为 12% |

图标依赖按 KB 规则 V3 显式声明：`androidx.compose.material:material-icons-core:1.7.8`。

## 四、行为与可访问性

- 状态优先级：Disabled > Pressed > Focused > Hovered > Enabled。
- 触控目标：外层 `sizeIn(minWidth = 48.dp, minHeight = 48.dp)`；XS 的 32dp 视觉高度不再直接当热区。
- 形状 morph：按下时圆角动画收缩到 `pressedCornerRadius`。
- `clickable(indication = null)`，状态层自绘，避免与 ripple 叠加；`role = Role.Button`。
- 内容不换行、不截断；焦点环 3dp。
- 演示页关闭动态取色（`dynamicColor = false`），让截图对应 baseline 角色，便于与设计稿对照。

## 五、运行时验证（真机）

设备：`e91fb0f`（1080×2400，Android）。`adb install` 成功，启动 `MainActivity` 截图：

![五种样式与五种状态](<screenshots/button-styles-states.png>)

![五档尺寸、Square / Disabled / 带图标](<screenshots/button-sizes-icon.png>)

人工核对结果：

| 项 | 结果 |
|---|---|
| 五种样式颜色角色 | ✅ 与上文角色一致（深色主题 baseline） |
| Focused 3dp 环 | ✅ 可见 |
| Pressed / Dragged 形状收缩 | ✅ 圆角明显变小 |
| Hover 8% / Focus 10% / Press 10% / Drag 16% | ✅ 逐级变亮，肉眼可辨 |
| Disabled 38% 内容 + 12% 容器 | ✅ |
| XS–XL 尺寸阶梯 | ✅ 32→136dp 递增 |
| 图标槽 | ✅ Material `Icons.Filled.Add`，20dp |
| 边到边（Android 15+） | ✅ 已加 `windowInsetsPadding(safeDrawing)`，滚动内容不再钻到状态栏下 |

## 六、验证命令与结果

| 项 | 结果 |
|---|---|
| `:app:compileDebugKotlin` | 0 error |
| `:app:testDebugUnitTest` | **2 tests / 0 failures**（状态层 + 五档数值回归） |
| `:app:assembleDebug` | 通过，APK 13.3 MB |
| `adb install -r` | Success；启动截图见上 |

## 七、已知限制

1. **焦点环画在内侧**（3dp 边框 + 2dp padding），官方为外扩 2dp；当前视觉可用，但尺寸差 2dp。
2. **关闭 ripple**：按下反馈仅状态层 + 形状 morph；可访问性不受影响，但与系统默认触感不同。
3. **Elevated 在深色主题下对比很弱**（surfaceContainerLow 与背景接近），这是角色选择的结果，符合官方值。
4. Text 按钮沿用 size 的内边距（官方 text 按钮更紧凑，S 档 12dp vs 我们的 16dp）；数值差异待后续按 specs 细化。
5. 状态层/颜色只做了肉眼与截图核对，尚无 pixel diff（可接 figmingo 的 compare）。
6. Round 档静止圆角用 `height / 2` 近似 `full`。

## 八、验收对照

| 验收项 | 状态 |
|---|---|
| 规格参数 0 条未落地 | ✅ 5 × 7/7 |
| 每个 State 有 Preview / 可强制 | ✅ `M3ButtonStatesPreview` |
| 官方尺寸/圆角/颜色一致 | ✅ 单测锁数值 + 真机截图 |
| 无“布局骨架”注释与硬编码 `tLabel` | ✅ |

## 九、给后续组件的复用建议

- 交互状态推导 + `*Visual(state)` + 样式枚举这套结构可直接复用到 IconButton / FAB / ToggleButton / Chip。
- 颜色角色表（5 样式）建议抽到 `M3ButtonTokens` 或 `ui/M3Colors`，避免每个组件重写。
- 下一步可把状态层/焦点环抽成通用 `Modifier.m3StateLayer(state, contentColor)`，供 Checkbox / Switch / ListItem 复用。

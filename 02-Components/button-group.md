# Button Group / 按钮组（Expressive）

- 官方指南：https://m3.material.io/components/button-groups ｜ 规格页：https://m3.material.io/components/button-groups/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-15 ｜ 核验方式：Chrome CDP 读官方 Specs 页
- 状态：变体与配置已核对；**尺寸为图片，未取到文字值**；API 稳定性需按版本核验

## 两个变体，来路不同

| 变体 | M3 | M3 Expressive | 说明 |
|---|---|---|---|
| **Standard button group** | ❌ | ✅ | 全新组件，多个按钮成组，**用 shape morph 表达交互** |
| **Connected button group** | ✅（以 segmented button 形式） | ✅ | 就是分段按钮的进化版 |

最需要注意的一句官方说明在变体表里：**Connected button group 在 M3 里叫 segmented button**。所以：

- 做 2-5 个互斥选项 → 用 `02-Components/segmented-button.md` 那套；
- 想要更大幅度的形状动画与多尺寸档 → 用 Expressive 的 connected button group；
- **Standard button group 是纯新增**，M3 里没有对应物，能不能用取决于你的 material3 版本。

## 配置

两个变体都支持：

| 维度 | 取值 |
|---|---|
| 尺寸 | **XS / S / M / L / XL** |
| 选择模式 | **single-select / multi-select** |
| 形状 | **round / square** |

这和普通按钮的尺寸档一致（见 `02-Components/buttons.md`），所以同一屏里按钮和按钮组可以对齐到同一档。

## Standard vs Connected 怎么选

| | Standard button group | Connected button group |
|---|---|---|
| 视觉 | 按钮之间有间距，**各自独立成形** | 分段**连成一条**，外侧圆角、内侧直角 |
| 交互表达 | 按下时形状 morph、间距收拢 | 选中段填充 + 对勾 |
| 适合 | 一组**相关但独立**的动作（如"复制/移动/删除"） | 一组**互斥选项**（如"日/周/月"） |

一句话：**Connected 是"选项"，Standard 是"动作"。** 把动作做成连体条，用户会以为只能选一个。

## 形状 morph 的地位

官方在 Shape 章节明确写过：**Standard button group 用 shape morph 来表达交互**（另一个用到 morph 的是 loading indicator）。

这意味着：
- 按下时按钮会改变形状/位置，这是**状态反馈**，不是装饰。
- 关闭动效后，静态形状必须仍然合理（不能塌成奇怪的比例）。
- 依赖 expressive API，`@ExperimentalMaterial3ExpressiveApi` 状态需按版本核验。

## 密度

官方给按钮组提供了 Density 配置（与 segmented button 一致：**每档减 4dp 高度**）。密集界面里可以压缩，但触控目标不能低于 48dp。

## Compose 实现

```kotlin
// 形态示意：具体 API 名称与签名需按当前版本核对
HorizontalFloatingToolbar { /* 不是按钮组，仅示意位置 */ }
```

**这里刻意不写具体代码。** 原因：按钮组的 Compose API 在 1.5.0 线仍在演进（`ButtonGroup` / `ToggleButton` / `ConnectedButtonGroup` 之类的命名与参数在不同版本有差异），照抄一段可能编译不过。

正确做法是先查当前版本的 API reference，再写。核对清单：

1. 组件类名是否存在；
2. 是否需要 `@OptIn(ExperimentalMaterial3ExpressiveApi::class)`；
3. 选择模式是参数控制还是靠不同的 Row 容器；
4. 形状档是否通过 `shape` 参数而不是主题。

## 无障碍

- **一组按钮要有组语义**（`selectableGroup()` 或等价方式），读屏才能报「3 个中的第 1 个」。
- single-select 组的语义等价于 Radio，multi-select 等价于 Checkbox——不要用 `Button` 的点击语义糊过去。
- 触控目标 ≥ 48dp，密度压缩后要复测。
- 形状 morph 不应对读屏造成焦点跳动。
- 只有图标的按钮必须有 `contentDescription`，但按钮组优先用文字。

## 常见错误

1. 把一组**动作**做成 connected 样式，用户以为只能点一个。
2. 在 M3（非 Expressive）里找 standard button group。
3. 按钮组里混入不同尺寸档，视觉上参差。
4. 单选组没给组语义，读屏读成三个独立按钮。
5. 关了动画后形状塌陷（morph 的静态兜底没设）。
6. 5 个以上按钮塞进一组，标签被压扁。
7. 用按钮组替代底部导航做页面切换。

## 待核验

- [ ] 五档尺寸的具体高度与内边距（官方以图片呈现）
- [ ] 当前 material3 版本中按钮组 API 的确切类名与签名
- [ ] `@ExperimentalMaterial3ExpressiveApi` 是否已移除

## 来源

- https://m3.material.io/components/button-groups/specs
- `02-Components/segmented-button.md`（connected 的前身）
- `02-Components/buttons.md`（尺寸档与形状）
- `01-Foundations/shape-system.md`（shape morph）

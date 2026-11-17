# P2 试点记录：Plain Tooltip / Generic Avatar / Shape Set

> 日期：2026-11-18　范围：P2 首批三个组件　结论：**Avatar 2/2、Shape 1/1、Tooltip 2/2；长按弹出已在真机确认（按住时显示，松手消失）**。

## 一、实现

| 组件 | 实现 |
|---|---|
| Plain Tooltip | `TooltipBox` + `PlainTooltip`；单行/两行；容器 min 24dp、内边距 8dp；inverseSurface 由官方默认 |
| Generic Avatar | 40dp 圆形；AVATAR（图片/字母回退）、MONOGRAM、PORTRAIT（人像图标） |
| Shape Set | 10 个形状：circle/square/pill/diamond/hexagon/pentagon/triangle/arch/gem/slanted |

Shape 的 diamond/hexagon/pentagon/triangle/arch/gem/slanted 用 `GenericShape` 路径；circle/square/pill 用内置 shape。

## 二、真机验证

![P2 首批：Tooltip 锚点 / 3 种 Avatar / 横向 Shape 列表](<screenshots/p2-primitives.png>)

![Plain Tooltip：长按 Add 时显示「Add item」](<screenshots/p2-tooltip.png>)

| 项 | 结果 |
|---|---|
| Avatar A / B / 人像三态 | ✅ |
| Shape 10 个、横向滚动 | ✅ |
| Tooltip 锚点渲染 | ✅ |
| Tooltip 长按弹出 | ✅ 按住时显示「Add item」，松手消失 |

## 三、验证命令

| 项 | 结果 |
|---|---|
| :app:compileDebugKotlin | 0 error |
| :app:testDebugUnitTest | **27 tests / 0 failures** |
| :app:assembleDebug | 通过 |
| 真机 adb install | Success |

## 四、参数落地

| 组件 | 参数 | 落地 | 说明 |
|---|---|---|---|
| Plain Tooltip | 2 | **2/2** | Type 单/双行 + 文案 |
| Generic Avatar | 2 | **2/2** | Style 三态 + 字母/图片 |
| Shape Set | 1 | **1/1** | 10 形状 |

## 五、已知限制

1. Tooltip 长按已验证，但**松手即消失**；未验证官方 1.5 秒延迟消失与多 tooltip 互斥。
2. Tooltip 未做 hover/focus；1.5 秒延迟消失与多 tooltip 互斥未验证。
3. Avatar 无图片裁剪之外的形状变体；未做在线图片加载。
4. Shape 只做 10 个；未做其余 25 个 shape token，也未做形状动画。
5. 未做 RTL、pixel diff、无障碍读屏走查。

## 六、下一步

P2 剩余：XR 家族（先评估依赖）、Keyboard、Layout grid、Extended FAB 旧行核对。
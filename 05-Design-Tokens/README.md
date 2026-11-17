# Design Tokens

> 核验日期：2026-11-14 ｜ 2026-11-16 校对：与 `00-Metadata/version-tracking.md` 的勾选状态同步（动效数值已取、形状十档已确认）

## 基本原则

**颜色不存值，结构存值。**

M3 的颜色是语义角色，Dynamic color 开启后同一角色在不同设备上就是不同颜色。在这个库里固化十六进制值，只会在换主题、换设备、开启动态取色时全部错位。

| 令牌类 | 是否存值 | 原因 |
|---|---|---|
| 颜色 | ❌ 只存角色名 | 受 dynamic color 影响，值不稳定 |
| 字阶 | ⚠️ 结构已存、数值待补 | 30 档 = 15 baseline + 15 emphasized 的结构已核；**逐档字号/行高数值仍未取到**（官网以图片/令牌模块呈现） |
| 形状 | ✅ 存值 | **十档圆角**（0/4/8/12/16/20/28/32/48/full），2026-11-16 已确认；Compose 原生 `Shapes` 仍只有五槽 |
| 动效 | ✅ 存族 + 值，标注代际 | **spring 数值已获取**（12 组 damping/stiffness，见 `motion.json`，`verified: true`）；easing + duration 标为 legacy，仍用于平台转场 |

## 使用流程

1. **颜色** → 用 [Material Theme Builder](https://m3.material.io/theme-builder) 生成 `ColorScheme`，粘贴到项目 `ui/theme/Color.kt`，在 `colors.json` 只登记「用哪个角色做什么」。
2. **字阶** → 从 `typography.json` 取值构造 `Typography`，品牌字体替换时 fontFamily / lineHeight / letterSpacing 三者同改。
3. **形状** → 从 `shapes.json` 取值构造 `Shapes`，注意只有五档。
4. **动效** → 先读 `01-Foundations/motion.md` 的判断规则，新代码优先用 `motion.json` 的 spring 数值；easing/duration 仅用于平台转场（legacy，官方标 no longer maintained）。

## 禁止

- 从设计稿截图吸色，然后写成「官方色值」。
- 把第三方 Figma 复刻 Kit 的令牌标为官方。
- 只存一组值就宣称「完整 MD3 色彩系统」——缺深色主题和 dynamic color 的都不算。
- 把品牌自定义令牌和官方令牌混在同一个文件里。自定义令牌请单独放 `brand.json`（本库未提供，按需新建），并注明「本项目品牌，非 MD3 官方」。

## 校验

改完令牌后，用 `07-Development-Rules/ui-review-checklist.md` 的「主题」一节过一遍，重点看深色主题和 dynamic color 关闭后的 fallback。

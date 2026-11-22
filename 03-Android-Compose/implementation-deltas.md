# Design vs Compose 实现差异清单

- 核验日期：2026-11-22
- 对照基准：设计值来自 m3.material.io 官方页面；实现值来自 `androidx.compose.material3:material3:1.5.0` sources jar（Compose 1.13.0 时代）
- 关联：逐项核对明细见 [`05-Design-Tokens/crosscheck-2026-11-22.md`](../05-Design-Tokens/crosscheck-2026-11-22.md)
- 用途：**设计值与实现值分层记录，不互相覆盖**。写规格、做评审用设计值；截图回归、像素对照用 Compose 实现值。

## 一、确有差异（4 条）

| 组件 | 设计值（m3） | Compose 1.5.0 实现 | 差异 | 落地建议 |
|---|---|---|---|---|
| **Tabs** | 图标 + 文字容器高 **64dp** | `Tab.kt` 的 `LargeTabHeight = **72dp**`（另有令牌 `IconAndLabelTextContainerHeight = 64dp` 未被该路径使用） | **+8dp** | 截图像素对照按 72dp 预期；设计稿仍标 64dp。见 [`tabs.md`](../02-Components/tabs.md) |
| **Snackbar（双行）** | 48 → **64dp** | `SnackbarTokens.TwoLinesContainerHeight = **68dp**`（单行 48dp 一致） | **+4dp** | 同上。见 [`snackbar.md`](../02-Components/snackbar.md) |
| **Tooltip（plain）** | 内边距 **8dp** | `PlainTooltip` 内边距 **垂直 4 / 水平 8dp**（`TooltipMinHeight = 24dp` 一致） | **垂直 -4dp** | 单行气泡高度一致，视觉更紧；见 [`tooltip.md`](../02-Components/tooltip.md) |
| **Date picker（modal）** | **360 × 524dp** | `DatePickerModalTokens.ContainerWidth/Height = 360 × **568dp**` | **高度 +44dp** | 弹窗比设计稿高；见 [`date-time-picker.md`](../02-Components/date-time-picker.md) |

> 这 4 条都已在对应组件页写明「设计值 / Compose 值」，本页只做索引，不要据此修改组件页的设计值。

## 二、不是「错」，而是变体/默认值不同

| 项 | 说明 |
|---|---|
| Navigation bar 高度 | baseline 80dp（`TallContainerHeight`）与 **short 变体 64dp**（`ContainerHeight`）并存；Compose 默认走 80dp |
| Large app bar | 设计侧 flexible large 为 **120dp**；Compose `AppBarLargeTokens.ContainerHeight = **152dp**` 是 baseline large（旧 152 值的来源） |
| Progress circular | 设计页只给范围 **24–240dp**；Compose 默认 `Size = 40dp`、`WaveSize = 48dp` |
| Slider | Compose 令牌只覆盖 XS（轨道 16 / handle 44×4）；S–XL 三档目前只有设计测量图值 |
| FAB（Expressive） | 设计页只给示意，Compose 提供 small 40 / medium 80 / large 96 的实现尺寸 |
| Bottom app bar | Compose 令牌 `ContainerHeight = 80dp`；设计页未在已抓文本中单列数值 |
| Tooltip（rich） | 设计页未给圆角/宽度；Compose 有实现默认值（水平 16dp、按钮最小高 36dp），不能当官方规范值引用 |

## 三、已确认一致（34 组抽样）

按钮、对话框、导航栏/栏、FAB、Extended FAB、Chips、Menus、Badge、Search bar、Divider、Icon button、Loading、Progress（轨道）、Switch/Checkbox/Radio、Bottom sheet、Toolbar、Cards、Text fields、Slider（XS）、Navigation drawer、Lists、Split button、Segmented、FAB menu、Time picker 等，逐项结果见 crosscheck 报告第四节。

## 四、升级时怎么重查

```bash
# 1) 下载对应版本 sources jar
curl -sS -o /tmp/material3.jar \
  https://dl.google.com/dl/android/maven2/androidx/compose/material3/material3/<version>/material3-<version>-sources.jar
# 2) 解压并看关键文件
tar -xf /tmp/material3.jar -C /tmp/m3src \
  commonMain/androidx/compose/material3/Tab.kt \
  commonMain/androidx/compose/material3/Snackbar.kt \
  commonMain/androidx/compose/material3/AlertDialog.kt \
  commonMain/androidx/compose/material3/Tooltip.kt \
  commonMain/androidx/compose/material3/tokens/
# 3) 对照本页四条差异 + crosscheck 的令牌数值
```

每次 BOM/artifact 升级按规则 V4 重跑；差异消失或新增时更新本页与 CHANGELOG，并同步对应组件页。

## 五、来源

- [Crosscheck 2026-11-22](../05-Design-Tokens/crosscheck-2026-11-22.md)（34 组逐项结果）
- [Compose Material3 1.5.0 release notes](https://developer.android.com/jetpack/androidx/releases/compose-material3)
- 各组件页顶部的官方 m3 链接（设计值出处）
- Google Maven：`material3/1.5.0/material3-1.5.0-sources.jar`（实现值出处）

# Extended FAB / 扩展悬浮操作按钮

- 官方指南：https://m3.material.io/components/extended-fab ｜ 规格页：https://m3.material.io/components/extended-fab/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-16
- 核验方式：官方全站文本 dump（overview / specs / guidelines / accessibility 四页）
- 状态：**三档尺寸 dp 值、baseline 测量表、颜色映射、Do / Don't 已核对**；三档的内边距 / 圆角在图片中

> 官方将 Extended FAB 作为独立组件维护（独立的 overview/specs/guidelines/accessibility 四页），本文档与之对应，不并入 fab.md。

## 用途

> Use instead of FAB when label text is needed to understand action.

- 长滚动视图需要**始终可见**的主动作（如结账页）。
- 图标单独表意太模糊、需要文字补充时。
- 想给页面主动作更多强调时。

与 FAB 一样：**一屏最多一个**，多个会破坏视觉层级。

## 变体与代际

| 变体 | 高度 | M3 | M3 Expressive |
|---|---|---|---|
| **Small extended FAB** | **56dp** | ❌ | ✅（取代 baseline） |
| **Medium extended FAB** | **80dp** | ❌ | ✅ |
| **Large extended FAB** | **96dp** | ❌ | ✅ |
| Extended FAB（baseline，56dp） | 56dp | ✅ | ❌ **不再推荐**，用 small 替代 |
| Surface 色 extended FAB | — | ✅ | ❌ 不再推荐 |

Expressive 更新要点（官方原文）：三档新尺寸**与 FAB 尺寸对齐**（方便 FAB ↔ extended FAB 互转）；字号加大（baseline 的 type style 从 label large 升为 **title medium**）；内边距减小。

### Baseline 测量（官方文字表）

| 属性 | 值 |
|---|---|
| 容器高度 | **56dp** |
| 容器宽度 | 动态，**最小 80dp** |
| 容器形状 | **16dp 圆角** |
| 图标尺寸 | **24dp** |
| 内边距 | **16dp** |
| 屏幕边距（margins） | **16dp** |

Small/Medium/Large 三档的内边距与圆角在 specs 页图片中，未给文字值。三档「与 FAB 尺寸对齐」指高度对齐 FAB 的 56/80/96dp 阶梯。⚠️ M3 baseline 与 FAB 同高（"Extended FAB is the same height as the FAB"，M2→M3 差异原文）。

## 结构与规则

解剖：Container + Label text + Icon（可选）。

- **图标可选**；但**不能只有图标没有文字**（官方 Don't："An extended FAB can't have an icon without a text label"）。
- 标签清晰描述动作，**最多 1-2 个词**；注意本地化会加宽容器；**不换行、不截断**。
- 容器是圆角矩形，**宽度随内容伸缩**（hugs its contents）。
- RTL 语言整体镜像：图标在 LTR 放标签左侧、RTL 放右侧。

## 颜色角色

与 FAB 相同的六套等价映射（官方：对比度与功能一致，凭风格选择）：

| 容器色 | 内容色 |
|---|---|
| `primaryContainer`（默认） | `onPrimaryContainer` |
| `secondaryContainer` | `onSecondaryContainer` |
| `tertiaryContainer` | `onTertiaryContainer` |
| `primary` | `onPrimary` |
| `secondary` | `onSecondary` |
| `tertiary` | `onTertiary` |

- **Surface 色样式不再推荐**（仍可用）。
- baseline 默认为 primary container + 阴影。
- 状态：Hovered **elevation 4**；非默认映射时**状态层颜色 = 图标颜色**（如 primary 映射用 `md.sys.color.primary`）。

## 使用规则（Guidelines 页 Do / Don't）

**位置**：

- ✅ 放在其余 UI **之上**（脱离 app bar 等元素）。
- ❌ 不要压在 toolbar 上（破坏 elevation 与表面层一致性）。
- ❌ 不要放手机屏**上半部**（干扰阅读顺序）。
- ❌ 不要放在卡片或其它容器里。
- ❌ 不要与 floating toolbar 同屏搭配。

**断点**：

- compact/medium：屏幕底部，居中或 trailing 对齐。
- expanded 及以上：右下角（**LTR 和 RTL 都右对齐**），或放进展开的 navigation rail 顶部。
- 空间受限时可收缩为 FAB（折叠 nav rail 用 FAB，展开后变 extended FAB）。

**选型**：

- ❌ **不要把 extended FAB 当作一组动作中的选项**——同层级选项用 filled 按钮。
- compact 窗口 + 单一主动作 → 可用 large；较大断点用 medium 或 large。

**行为**：

- 出现时用 enter/exit 转场展开表面。
- 可用 container transform 变形为任意形状（含全屏表面）。
- **滚动**：下滚收起为 FAB、上滚展开；展开时 FAB 形状变化、图标移到左侧、文字淡入。

## 无障碍（Accessibility 页）

- 读屏用户应能：**导航到并激活** extended FAB。
- **初始焦点**：整体焦点顺序中优先安排（移动端顺序可为 app bar → 导航栏 → 跳到 extended FAB）。
- **标签 + 图标是一个可聚焦元素**（不要拆开）；**无需 tooltip**——已有可见标签（官方 Don't 明确列出）。
- 大屏可放左上区域（如展开的 nav rail）方便读屏触达；小窗放右下角；**不要遮挡其它可交互元素**。
- **键盘**：Tab 聚焦；Space 或 Enter 激活。
- **读屏标签**：与可见标签**首词一致**——可见标签为 "Create"，无障碍标签可为 "Create a new invite"；图标与文字组合表达同一目的。

## Compose 实现

稳定 API：`ExtendedFloatingActionButton`（`icon` / `text` 两个 slot）：

```kotlin
ExtendedFloatingActionButton(
    onClick = { compose() },
    icon = { Icon(Icons.Default.Edit, contentDescription = null) }, // 文字已表达动作
    text = { Text("写笔记") },
    // expanded = … 可配合滚动收起/展开（版本相关参数，核对签名）
)
```

- 图标 `contentDescription = null`（标签已表达动作，且标签+图标本就是单一可聚焦元素）。
- 三档新尺寸（56/80/96dp）与对应 type style 属 Expressive 能力，`MediumExtendedFloatingActionButton` 等命名**需 `@OptIn(ExperimentalMaterial3ExpressiveApi::class)`，随版本变动，接入前核对当前版本文档**。

## 常见错误

1. 一屏多个 extended FAB，或 extended FAB 与 FAB 并存。
2. 只有图标没有文字（官方明确禁止）。
3. 放在 toolbar / 卡片 / 手机屏上半部。
4. 当作一组动作中的选项（同组请用 filled 按钮）。
5. 又加了 tooltip（有可见标签，不需要）。
6. 无障碍标签与可见标签首词不一致。
7. 在 M3（非 Expressive）里找 small/medium/large 三档 extended FAB。

## 来源

- https://m3.material.io/components/extended-fab/overview（三档尺寸 56/80/96dp、baseline 替代关系、M2 差异）
- https://m3.material.io/components/extended-fab/specs（颜色映射、状态、baseline 测量表、16dp 边距）
- https://m3.material.io/components/extended-fab/guidelines（Do / Don't、断点、行为）
- https://m3.material.io/components/extended-fab/accessibility（焦点、标签、键盘）
- 核验日期：2026-11-16
- 关联：`02-Components/fab.md`（FAB 基础规则与颜色映射）

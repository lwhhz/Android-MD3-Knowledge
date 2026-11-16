# Buttons / 按钮

- 官方指南：https://m3.material.io/components/buttons ｜ 规格页：https://m3.material.io/components/buttons/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-16
- 核验方式：官方全站文本 dump（overview / specs / guidelines / accessibility 四页）
- 状态：**变体、配置、颜色角色、圆角、状态层、无障碍要求均已按官方文本核对**

## 用途

> Buttons prompt most actions in a UI.

按钮触发**一个**明确动作。官方列出的典型位置：Dialogs、Modal windows、Forms、Cards、Toolbars，也可以放进 button group。

## 两代能力要分清

Specs 页变体/配置表逐行标注「M3」与「M3 Expressive」两列：

| 能力 | M3 | M3 Expressive |
|---|---|---|
| Default（默认按钮） | ✅ | ✅ |
| **Toggle（选择态按钮）** | ❌ | ✅ |
| 尺寸：Small（默认档） | ✅ | ✅ |
| 尺寸：**XS / M / L / XL** | ❌ | ✅ |
| 形状：Round（默认） | ✅ | ✅ |
| 形状：**Square（方角）** | ❌ | ✅ |
| 小尺寸水平内边距 24dp | ✅ | ⚠️ 官方标注 "Not recommended. Use 16dp" |
| 小尺寸水平内边距 16dp | ❌ | ✅ |

方角按钮、新尺寸档、Toggle 按钮都只在 Expressive 下存在。写代码前先确认 material3 版本是否提供对应 API。

## 颜色角色（五种颜色样式）

官方五种内置颜色样式（按强调递减）：elevated、filled、tonal、outlined、text。

### Default 按钮

| 样式 | 容器色 | 内容色（图标+文字） |
|---|---|---|
| Elevated | `surfaceContainerLow` | `primary` |
| Filled | `primary` | `onPrimary` |
| Tonal | `secondaryContainer` | `onSecondaryContainer` |
| Outlined | 透明 + `outlineVariant` 描边 | `onSurfaceVariant` |
| Text | 透明（交互时才显示容器） | `primary` |

M3 起图标与文字**同色**（M2→M3 差异官方原文："Icons and labels now share the same color"）；中性色 text 按钮不再推荐。

### Toggle 按钮（仅 Expressive）

Toggle **不提供 text 样式**（官方原文："Toggle buttons don't use the text style"）：

| 样式 | 未选中（容器/内容） | 选中（容器/内容） |
|---|---|---|
| Elevated | `surfaceContainerLow` / `primary` | `primary` / `onPrimary` |
| Filled | `surfaceContainer` / `onSurfaceVariant` | `primary` / `onPrimary` |
| Tonal | `secondaryContainer` / `onSecondaryContainer` | `secondary` / `onSecondary` |
| Outlined | `outlineVariant` 描边 / `onSurfaceVariant` | `inverseSurface` / `inverseOnSurface` |

> These color roles were chosen to create design coherence and familiarity. Other color roles can be used as long as the container and text have a 3:1 contrast ratio.

## 尺寸与形状

### 尺寸档与高度

- 尺寸档：XS / Small（M3 唯一档、默认）/ M / L / XL，后四档仅 Expressive。
- **Small 高度 40dp 为官方文字值**（Overview 页 M2/M3 对比：M2 36dp → M3 40dp）。
- XS/M/L/XL 的具体高度：buttons specs 的 Measurements 区**只有图片，未给文字值**。同族 icon-buttons 官方给的高度阶梯为 32/40/56/96/136dp，按钮族各档高度大概率一致，但**⚠️ 推断，buttons 页面未明示**。
- 图标：前后图标标准尺寸 **20dp**（M2→M3 官方原文："Standard size for leading and trailing icons is now 20dp"）。
- 小尺寸水平内边距：M3 为 24dp；Expressive 推荐 16dp（与新尺寸档统一）。

### 圆角（官方 Measurements 表原文）

| | XS | S | M | L | XL |
|---|---|---|---|---|---|
| A. 圆形按钮 | Full | Full | Full | Full | Full |
| B. 方角按钮 | 12dp | 12dp | 16dp | 28dp | 28dp |
| C. 按下态 | 8dp | 8dp | 12dp | 16dp | 16dp |

### 形状 morph（Expressive 核心交互）

- **按下时按钮变方**，round 与 square 按钮按下形状**必须一致**（都取 C 行值）。
- Toggle 按钮静止态也会变形：默认未选中圆 → 选中方；若静止未选中是方角，选中应变圆。
- 形状变化是**状态反馈**，不是装饰。关闭动效后静态形状仍需合理。

## 状态

- Elevated 按钮：默认 elevation **1**，禁用时 **0**（官方原文）。
- Outlined / Text 容器静止时不可见，但禁用/悬停/聚焦/按下的**不透明度与状态层行为与其它样式一致**。
- 状态层百分比数值未在 buttons specs 文字中出现（icon-buttons specs 给出：hover 8%，focused/pressed/disabled 10%），**⚠️ 推断按钮同值，页面未明示**。

## 使用规则（Guidelines 页 Do / Don't）

1. **按钮别滥用**：太多按钮破坏视觉层级。低优先级动作放 overflow 菜单或改用图标按钮（Don't：用按钮堆满 UI）。
2. **容器宽度随标签动态**：可响应式拉伸，但**不能窄于标签文字**；不要设小于标签的固定宽度。
3. **强调梯度**：filled 用于最重要的动作（Save、Join now、Confirm 类终结动作），**一页最好只有一个**；「重要性仅次于 FAB」。
4. **Elevated = tonal + 阴影**：仅在需要与强背景分离时用；更高 elevation 意味着更强强调，要克制，高强调首选 filled。
5. **Tonal**：比 outlined 略强的次优先动作（如 onboarding 的 "Next"），用 secondary 映射。
6. **Outlined**：中等强调，适合与 filled 搭配做次动作；放在简单背景上，图片/视频上慎用（可加对比容器填充或改 filled）。与 chips 视觉相似，**易混时改 filled/tonal**。
7. **Text**：最低优先级；不要加下划线（链接用正文超链接）；对话框中 text 按钮靠 trailing 边对齐（LTR 右、RTL 左）。
8. **标签文案**：1-3 个词，sentence case；**不截断、不换行**，单行完整显示。
9. **图标**：放 leading 侧（LTR 左、RTL 右）；一个按钮最多一个图标；不要垂直堆叠图标和文字。
10. **Toggle**：用于二元选择（Save、Favorite）；未选中用 outlined 图标，选中用 filled 版本；两态标签长度应接近，不要剧变。
11. **自适应**：大屏时按钮可加宽/换位，但图标和文字保持成组居中；不要拉成长条空心按钮；大小屏之间**元素顺序保持不变**（读屏与键盘导航一致性）。

## 无障碍（Accessibility 页）

- **对比度**：启用态按钮与背景 **≥ 3:1**——elevated/filled/tonal 按容器算，outlined/text 按标签文字算。
- **200% 字号**：Android 上按钮标签在 200% 字号下应不超过两行；若被截断，须提供单击可达的完整内容替代。
- **快速连点**（Web）：预期连续点击处用修正的运动曲线，避免叠加动画共振。
- **键盘**：Tab 聚焦按钮；Space 或 Enter 激活。
- **读屏标签**：无障碍标签与可见文案一致（Done、Send 等），必要时可附加上下文。
- **触控目标**：**XS 和 S 的图标按钮触控目标必须 ≥ 48×48dp**。

## Compose 实现

稳定 API（material3）：

| 官方样式 | Compose |
|---|---|
| Filled | `Button` |
| Tonal | `FilledTonalButton` |
| Elevated | `ElevatedButton` |
| Outlined | `OutlinedButton` |
| Text | `TextButton` |

```kotlin
Button(onClick = { save() }) {
    Icon(Icons.Default.Check, contentDescription = null,
        modifier = Modifier.size(ButtonDefaults.IconSize)) // 18dp，官方视觉标准 20dp
    Spacer(Modifier.width(ButtonDefaults.IconSpacing))
    Text("保存")
}
```

- 按钮内图标 `contentDescription = null`（标签已表达动作，避免读两遍）。
- 自定义颜色：`ButtonDefaults.buttonColors(containerColor = …, contentColor = …)`，改容器必须同时保证内容 3:1 对比度。
- Toggle / Square / XS-M-L-XL 属 Expressive 能力，对应 `ToggleButton`、`Button(shapes = ButtonShapes(…))` 一类 API，**需 `@OptIn(ExperimentalMaterial3ExpressiveApi::class)`，名称与签名随版本变动，接入前核对当前版本文档**。

## 常见错误

1. 在 M3（非 Expressive）里找 Square / Toggle / XS 尺寸档。
2. 两个 filled 按钮并列当主操作（官方：一页一个）。
3. 按钮内图标写了 `contentDescription`，读屏读两遍。
4. 固定 `height(40.dp)`，200% 字号下文字被裁。
5. Outlined 按钮放在花哨背景上，与 chips 混淆。
6. 只改容器色不管 3:1 对比度（官方允许换角色，前提是对比达标）。
7. 标签换行/截断（官方：单行完整显示）。

## 来源

- https://m3.material.io/components/buttons/overview（M2→M3 差异、40dp 高度、20dp 图标）
- https://m3.material.io/components/buttons/specs（配置表、颜色角色、圆角、24/16dp 内边距）
- https://m3.material.io/components/buttons/guidelines（Do / Don't）
- https://m3.material.io/components/buttons/accessibility（3:1、200%、键盘、标签）
- 核验日期：2026-11-16
- 关联：`02-Components/icon-buttons.md`（尺寸阶梯与状态层数值）、`02-Components/button-group.md`

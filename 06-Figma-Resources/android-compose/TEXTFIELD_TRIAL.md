# Text field 试点记录（阶段 1 · 第四批）

> 日期：2026-11-17　范围：`M3TextField`（Filled / Outlined）　结论：**10/10 规格参数落地**；编译、单测、真机截图（含聚焦态）通过。

## 一、规格 → 实现

| Figma 参数 | 类型 | 实现 |
|---|---|---|
| `Style` | 变体 Filled / Outlined | `style: M3TextFieldStyle`，分别用 `TextField` / `OutlinedTextField` |
| `State` | Enabled / Hovered / Focused / Disabled / Error | 交互与 `enabled` / `isError` 交给 Material 3 默认状态层 |
| `Text configurations` | Input / Label / Placeholder | `value` + `label` + `placeholder` |
| `Leading icon` | 实例替换 / 布尔 | `leadingIcon: ImageVector?`（24dp） |
| `Trailing icon` | 实例替换 / 布尔 | `trailingIcon` + 可选 `onTrailingIconClick`（自动包 `IconButton`） |
| `Show supporting text` | 布尔 | `showSupportingText` |
| `Label text` | 文本 | `label` |
| `Input text` | 文本 | `value` / `onValueChange` |
| `Placeholder text` | 文本 | `placeholder` |
| `Supporting text` | 文本 | `supportingText`（错误时由 `errorText` 替换，不追加） |

## 二、官方测量与颜色

| 项 | 值 | 来源 |
|---|---|---|
| 容器高度 | 56dp | KB text-fields.md（官方原值） |
| 上下 / 左右内边距 | 8dp / 16dp（无图标）、12dp（有图标） | 同上 |
| 图标与文字间距 | 16dp | 同上 |
| 辅助文本上间距 | 4dp | 同上 |
| 图标 | 24dp | 同上 |
| 圆角 | 官方未给 dp，沿用 Compose 默认 | KB 标注 |
| 颜色 | 走 Material 3 `TextField`/`OutlinedTextField` 默认 token（surfaceContainerHighest、primary 指示线、onSurfaceVariant 辅助文本等） | KB 颜色表 |

数值常量放在 [M3TextFieldTokens.kt](<app/src/main/java/com/example/m3appbar/ui/M3TextFieldTokens.kt>)，并加单测锁定。

## 三、行为细节

- **错误文本替换辅助文本**：`isError && errorText != null` 时只显示错误文本，避免布局跳动（官方硬要求）。
- **错误图标**：错误且未提供 trailing 图标时自动补 `Icons.Filled.Warning`（官方强烈建议）。
- **标签始终可见**：`label` 槽位，不允许用 placeholder 代替。
- **不支持自行写死高度**：KB 指出硬写 `height(56.dp)` 会与 8dp 内边距冲突，沿用默认。
- 支持 `singleLine` / `readOnly` / `enabled`。

## 四、真机验证

![四种状态：Filled / Outlined 已填 / Error / Disabled](<screenshots/textfields-states.png>)

![聚焦态：label 上浮、指示线转 primary、占位符显示](<screenshots/textfields-focused.png>)

核对结果：

| 项 | 结果 |
|---|---|
| Filled 容器 / Outlined 描边与浮动 label | ✅ |
| 聚焦：label 上浮变 primary、2dp 指示线、caret | ✅（`dumpsys input_method` 确认 mInputShown=true） |
| Error：label/指示线/辅助文本转 error 色 + 警告图标 | ✅ |
| Error 文本替换辅助文本 | ✅ |
| Disabled 置灰、不可聚焦 | ✅（UI 树 `enabled=false`） |
| 前后图标 24dp、`contentDescription` | ✅ |

## 五、验证命令

| 项 | 结果 |
|---|---|
| `:app:compileDebugKotlin` | 0 error |
| `:app:testDebugUnitTest` | **5 tests / 0 failures**（新增 TextField 尺寸回归） |
| `:app:assembleDebug` | 通过 |
| 真机 `adb install` | Success |

## 六、已知限制

1. **颜色走 Compose 默认 token**，未按 Figma 节点逐项覆写；KB 颜色表本身标注「元素↔角色绑定为推断」，需在 Figma 设计工具里二次确认。
2. **圆角未自定义**：官方未给 dp，沿用 Compose 默认 4dp。
3. 未实现 Prefix / Suffix / 字数统计 / 多行 text area 的专用参数（当前可用 `TextField` 默认能力，但未暴露 API）。
4. 错误图标文案写死 "Error"（英文），未做本地化。
5. 未做 RTL、密度模式、大屏宽度约束的截图验证。
6. 尚无 pixel diff。

## 七、下一步

按计划是 **Checkbox / Radio / Switch**（选择控件三件套），它们可以复用 `m3StateLayer` 与颜色表；之后是 Slider 和 Chip。

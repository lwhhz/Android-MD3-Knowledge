# Text Fields / 文本输入框

- 官方指南：https://m3.material.io/components/text-fields
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-14 ｜ 状态：`TextField` / `OutlinedTextField` 为当前 API；新版 `BasicTextField` + `TextFieldState` 在 1.13 线，见文末

## 用途

收集用户输入。它是最容易做错、也最影响可用性的组件——因为错误都发生在「用户已经输完了才发现不对」的时刻。

## 两种容器

| Compose | 外观 | 用在哪 |
|---|---|---|
| `TextField` | 填充式，底部有指示线 | 表单主体、需要强调输入区域时 |
| `OutlinedTextField` | 描边式，四角 4dp | 与卡片、对话框搭配；密集表单 |

**同一屏不要混用两种**，除非有明确的层级区分理由。

## 结构

```
标签（浮起后变小，仍在容器内）      ← 必需
┌────────────────────────────┐
│ [前置图标] 输入内容  [尾部图标] │
└────────────────────────────┘
辅助文本 / 错误信息                ← 强烈建议
```

| 元素 | 规则 |
|---|---|
| 标签 | **必需**。不能用 placeholder 代替标签——用户输入后标签消失，就不知道这格是什么了 |
| Placeholder | 可选。写格式示例（「如 13800138000」），不要重复标签 |
| 辅助文本 | 写约束条件（「最多 50 字」）或错误原因 |
| 前置图标 | 表示输入类型或开启选择器（如日期） |
| 尾部图标 | 清空、密码可见性切换、单位 |

最小高度 56dp。`OutlinedTextField` 与外层间距建议 8dp 以上。

## 状态

| 状态 | 表现 |
|---|---|
| 静止 | 标签居中/靠上，无描边强调 |
| 聚焦 | 描边或指示线变为 `primary`，标签上浮缩小 |
| 已输入 | 标签保持上浮，显示内容 |
| 错误 | 描边/指示线变 `error`，**并显示错误文本** |
| 禁用 | 容器与文字降低不透明度 |
| 只读 | 可聚焦可复制，不可编辑 |

**错误态必须同时有描边变化和文字说明。** 只变红等于只靠颜色传递信息。

## Compose 实现

标准表单项：

```kotlin
OutlinedTextField(
    value = email,
    onValueChange = onEmailChange,
    label = { Text("邮箱") },
    placeholder = { Text("name@example.com") },
    isError = emailError != null,
    supportingText = {
        Text(emailError ?: "用于登录和找回密码")
    },
    keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Email,
        imeAction = ImeAction.Next
    ),
    singleLine = true,
    modifier = Modifier.fillMaxWidth()
)
```

多行 + 字数限制：

```kotlin
OutlinedTextField(
    value = note,
    onValueChange = { if (it.length <= 200) onNoteChange(it) },
    label = { Text("备注") },
    supportingText = { Text("${note.length}/200") },
    minLines = 3,
    maxLines = 5,
    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
)
```

密码框的可见性切换：尾部 `IconButton` + `visualTransformation`，`contentDescription` 写「显示密码 / 隐藏密码」。

## 键盘与 IME

- `imeAction` 要匹配位置：中间字段用 `Next`，最后一个用 `Done`，搜索场景用 `Search`。
- `KeyboardActions` 里处理 `Next` 时手动 `focusManager.moveFocus(FocusDirection.Down)`。
- **不要把提交按钮固定在键盘正上方之外的地方**，用户输完找不到下一步。
- 表单要能滚动，并处理 IME inset，否则键盘会盖住下方字段。

## 无障碍

- 标签是必读信息。用 `label` 槽位，不要手写 `Text` 放在框外（读屏关联不上）。
- 错误信息用 `supportingText` 输出，读屏会随字段朗读。
- 尾部图标按钮必须写 `contentDescription`（「清空」「显示密码」）。
- 校验时机：**失焦时校验**，不要每输入一个字符就报错（用户还没输完就红一片）。
- 触控目标 ≥ 48dp；尾部图标按钮不要小于 48dp。

## 常见错误

1. 只用 placeholder 不用 label。
2. 错误只把框变红，没有文字原因。
3. 输入类型没设，邮箱弹出普通键盘、手机号可以输字母。
4. 每次 `onValueChange` 都跑一遍正则并显示错误，用户边输边被骂。
5. `singleLine = true` 但正文可能很长，内容被截断且无法查看。
6. 键盘盖住输入框，没有滚动或 IME padding。
7. 密码框没有可见性切换。
8. 在 `onValueChange` 里直接改外部状态却不做防抖，导致每个字符触发一次网络请求。

## API 状态提示

Compose 1.13 线引入了重写的 `BasicTextField`（`TextFieldState` 驱动、支持富文本格式化），并在较新的 material3 中提供 `SecureTextField` / `OutlinedSecureTextField`。这两条都在演进中：

- 用之前先确认当前依赖版本是否已包含，且是否仍为 experimental。
- 迁移前评估：`TextFieldState` 与现有 `value/onValueChange` 模型的差异不小，不是替换类名能解决的。

详见 `00-Metadata/version-tracking.md` 待核验清单。

## 来源

- https://m3.material.io/components/text-fields
- `01-Foundations/accessibility.md`（标签、错误、缩放）
- `03-Android-Compose/window-insets.md`（IME 处理）

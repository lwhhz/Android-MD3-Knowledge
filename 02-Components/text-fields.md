# Text Fields / 文本输入框

- 官方指南：https://m3.material.io/components/text-fields ｜ 规格页：https://m3.material.io/components/text-fields/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-16
- 核验方式：官方全站文本 dump（overview / specs / guidelines / accessibility 四页）
- 状态：**56dp 容器高度、8/12/16dp 内边距、4dp 辅助文本间距为官方明示值**；圆角半径官方未给 dp

## 用途

收集用户输入。两种容器，功能完全相同：

> "Both variants of text field provide the same functionality. The variant of text field used can depend on style alone."

| Compose | 外观 | 官方定位 |
|---|---|---|
| `TextField` | 填充式，底部有指示线，**上方圆角、下方直角** | 表单主体 |
| `OutlinedTextField` | 描边式，四角圆角 | 大量字段并排的表单、登录页——弱化视觉重量 |

> "Outlined text fields have less visual emphasis than filled text fields. When they appear in places like forms (where many text fields are placed together), their reduced emphasis helps simplify the layout."

选型三问（官方原文）：与 App 视觉风格最协调、最符合 UI 目标、**与按钮等周边组件区分度最高**。

**混用规则**（旧文写「同一屏不要混用」，官方口径更精确）：

> "If both variants of text field are used in a UI, they should be used consistently within different sections, and not intermixed within the same region."

即：可以一个区域全填充、另一个区域全描边；不能在同一表单里相邻混排（官方 Don't 原文：don't use both next to each other or within the same form）。

## 尺寸（官方 Measurements 原值）

| 属性 | Filled | Outlined |
|---|---|---|
| 默认容器高度 | **56dp** | **56dp**（Container height） |
| 上下内边距 | **8dp** | —（未单列） |
| 左右内边距（无图标） | **16dp** | **16dp** |
| 左右内边距（有图标） | **12dp** | **12dp** |
| 图标与文字间距 | **16dp** | **16dp** |
| 图标对齐 | 垂直居中 | 垂直居中 |
| 标签对齐（未输入时） | 垂直居中 | 垂直居中 |
| 已输入标签左右内缩 | — | **4dp**（populated label） |
| 辅助文本/字数统计上间距 | **4dp** | **4dp** |
| 辅助文本与字数统计间距 | **16dp** | **16dp** |
| 触控目标（Target size） | **56dp** | **56dp** |

补充：

- **图标尺寸**：specs 页未给图标 dp；guidelines 只规定**图片高度 24dp**——"Images that are 24dp in height can be placed inside of text fields. This image height allows for optimal top and bottom padding within the field and is consistent with icon size recommendations." ⚠️ 推断，页面未明示：图标同为 24dp 是由这句话反推的。
- **圆角半径**：官方只写 "The container of an outlined text field has rounded corners, while the container of a filled text field has rounded top corners and square bottom corners." **没有给 dp 数值**。Compose 默认 4dp，属于实现值而非本页规格。
- 触控目标 56dp 是**组件本身**；密度模式下另见下文（不得低于 48×48）。

## 颜色角色

官方 token 面板按元素顺序列出颜色值，filled 共 10 项、outlined 共 9 项。下表按 anatomy 元素顺序对应（⚠️ 推断，页面未明示元素与值的绑定关系，落地前在设计工具里二次确认）：

### Filled

| 元素 | 颜色角色 |
|---|---|
| Container | `surfaceContainerHighest` |
| Leading icon | `onSurfaceVariant` |
| Label（空态） | `onSurfaceVariant` |
| Label（已输入） | `primary` |
| Trailing icon | `onSurfaceVariant` |
| Focused active indicator | `primary` |
| Caret | `primary` |
| Input text | `onSurface` |
| Supporting text | `onSurfaceVariant` |
| Enabled active indicator | `onSurface` |

### Outlined

| 元素 | 颜色角色 |
|---|---|
| Enabled container outline | `outline` |
| Leading icon | `onSurfaceVariant` |
| Label（空态） | `onSurfaceVariant` |
| Label（已输入） | `primary` |
| Trailing icon | `onSurfaceVariant` |
| Focused container outline | `primary` |
| Caret | `primary` |
| Input text | `onSurface` |
| Supporting text | `onSurfaceVariant` |

状态集合（官方 states 面板）：Enabled / Focused / Hovered / Disabled × (empty / populated)；错误态另有 Enabled / Focused / Hovered × (empty / populated)。

## 结构与文案

| 元素 | 规则 |
|---|---|
| Label | "Every text field should have a label." 必须与输入文字左对齐、**始终可见**；不可截断、不可换行 |
| 相邻标签 | "A text field doesn't require a label if the field's purpose is indicated by a separate, adjacent label."（相邻标签要与容器左缘对齐） |
| 必填标记 | 标签后加 `*`，并在**辅助文本**或**表单开头的统一说明**里解释星号含义；所有必填项都要标 |
| Prefix | 币种符号等，如 `¥` |
| Suffix | 计量单位、邮箱域名等 |
| 辅助文本 | 说明用途/约束；理想一行，可换行；可常显或仅聚焦时显示 |
| 字数统计 | 有字数限制时必须给，显示「已用/上限」比例 |
| 错误文本 | **替换**辅助文本，而不是追加 |
| 只读 | 样式与普通文本框一致，但必须明确标注为只读 |

错误态强制要求：

> "For text fields that validate their content such as passwords, replace supporting text with error text. Swapping supporting text with error text prevents new lines of text from bumping content and changing the layout."

> "It's strongly recommended to show an error icon when the text field is in the error state. This highlights the error for people with visual impairments, and provides an additional sensory indicator."

错误文案写法：只可能有一种错误时写**怎么避免**；可能有多重错误时写**最可能发生的那种**怎么避免。

## 输入形态

| 形态 | 行为 | 适用 |
|---|---|---|
| 单行 | 光标到右缘时内容自动左滚 | 短输入。**不适合长文本** |
| 多行 | 溢出时容器增高、换行、下方元素下移 | 初始一行、可容纳大量文字 |
| Text area | **固定高度**，纵向滚动 | Web 优先；移动端要保证高度能放进屏幕 |

## 使用规则（Do / Don't）

- ✅ 用文本框收集联系信息、支付信息等文本输入
- ✅ 让状态（空、已填、错误）一眼可见
- ❌ 用 placeholder 替代 label：输入后标签消失，字段含义随之丢失
- ❌ 截断标签、让标签折成多行
- ❌ 在辅助文本之外**再加**错误文本（会挤动布局）
- ❌ 自适应布局里给文本框固定边距、让它占满大屏整宽
- ❌ 默认开启密度模式

> "Don't apply density to text fields by default. This lowers their targets below the recommended 48x48 CSS pixels."

响应式：紧凑断点可占满宽度；中等/展开断点必须被弹性边距或其它容器约束。错误文案较长会换行，此时要保证文本框之间的间距足够，避免多行错误挤动布局。

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
        // 错误时替换辅助文本，而不是追加
        Text(emailError ?: "用于登录和找回密码")
    },
    trailingIcon = if (emailError != null) {
        { Icon(Icons.Default.Error, contentDescription = "错误") }
    } else null,
    keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Email,
        imeAction = ImeAction.Next
    ),
    singleLine = true,
    modifier = Modifier.fillMaxWidth()
)
```

多行 + 字数统计：

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

尺寸对齐 56dp 时不要自己写死 `height(56.dp)`——内边距由 `TextFieldDefaults.contentPadding(...)` / `OutlinedTextFieldDefaults.contentPadding(...)` 控制，硬写高度会与 8dp 上下内边距冲突。

密码可见性切换：尾部 `IconButton` + `PasswordVisualTransformation` 切换，`contentDescription` 随状态在「显示密码 / 隐藏密码」之间切换（这是官方无障碍原文要求）。

## 无障碍

| 要求 | 官方原文要点 |
|---|---|
| 标签即无障碍名称 | "The accessibility label for a text field is the same as the text field label." |
| 错误播报 | 错误态给 `alert` 角色；同时存在辅助文本与错误文本时，**先读辅助文本、再读错误文本** |
| 错误图标 | 无交互角色时标签写 "Error" |
| 尾部图标 | 必须说明功能（"Show password" / "Hide password"），角色说明它是按钮 |
| 字数统计 | 标签里要说明可输入字符数，计数项命名为 "character count" |
| 辅助文本 | 有独立无障碍标签，内容即显示文字 |
| 必填 | 标签末尾的 `*` 必须包含在无障碍标签里 |
| 描边对比度 | 容器描边与背景 ≥ **3:1** |
| 键盘 | `Tab` 落到未禁用的文本框 |

其余工程约束：

- 错误信息走 `supportingText`，读屏才会与字段关联；手写 `Text` 放在框外无法关联。
- ⚠️ 推断，页面未明示：**失焦时校验**优于逐字符校验——官方只要求「错误时可给出修复说明」，没有规定校验时机。
- 触控目标按 56dp 设计；密度模式下限 48×48。

## 常见错误

1. 只用 placeholder 不用 label。
2. 错误只把框变红，既没有错误文本也没有错误图标。
3. 错误文本追加在辅助文本下方，导致布局跳动（应替换）。
4. 必填项漏标 `*`，或标了星号却没解释含义。
5. 输入类型没设，邮箱弹普通键盘、手机号能输字母。
6. 单行字段承载长文本，内容被滚动隐藏且无法回看。
7. 密码框没有可见性切换。
8. 大屏上文本框拉满整宽，行长超出可读范围。
9. 默认开密度模式，触控目标掉到 48dp 以下。

## 来源

- https://m3.material.io/components/text-fields/specs
- https://m3.material.io/components/text-fields/guidelines
- https://m3.material.io/components/text-fields/accessibility
- 关联：`01-Foundations/accessibility.md`（标签、对比度）、`03-Android-Compose/window-insets.md`（IME）

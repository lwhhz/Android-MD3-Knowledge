# Buttons / 按钮

- 官方指南：https://m3.material.io/components/buttons ｜ 规格页：https://m3.material.io/components/buttons/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-14
- 核验方式：Chrome CDP 渲染官方 Specs 页提取（含尺寸档位、形状、颜色角色表）
- 状态：**变体、尺寸档、形状、颜色角色已核对**；dp 高度值仍在图片中，标为通行值

## 用途

按钮触发**一个**明确动作。判断标准：点下去之后，用户是否知道「刚刚发生了什么」。

## 两代能力要分清

官网 Specs 页的变体/配置表里，每一行都标注了「M3」与「M3 Expressive」两列。差别很大：

| 能力 | M3 | M3 Expressive |
|---|---|---|
| Default（默认按钮） | ✅ | ✅ |
| **Toggle（选择态按钮）** | ❌ | ✅ |
| 尺寸：Small（默认） | ✅ | ✅ |
| 尺寸：**XS / M / L / XL** | ❌ | ✅ |
| 形状：Round（默认） | ✅ | ✅ |
| 形状：**Square（方角）** | ❌ | ✅ |

也就是说：**方角按钮、尺寸档、Toggle 按钮都只在 Expressive 下存在**。写代码前先确认你的 material3 版本是否已把对应 API 转正。

## 颜色样式（五种）

| Compose | 强调 | 容器色 | 内容色 |
|---|---|---|---|
| `Button`（filled） | 最高 | `primary` | `onPrimary` |
| `FilledTonalButton`（tonal） | 中 | `secondaryContainer` | `onSecondaryContainer` |
| `ElevatedButton` | 中 | `surfaceContainerLow` | `primary` |
| `OutlinedButton` | 中低 | 透明 + `outlineVariant` 描边 | `onSurfaceVariant` |
| `TextButton` | 最低 | 透明 | `primary` |

补充（官网原文）：
- Elevated 按钮默认 elevation **1**，禁用时为 **0**。
- Outlined 与 Text 的容器在静止态**不可见**，但禁用/悬停/聚焦/按下时的不透明度与状态层行为与其它样式一致。
- 官方说明：这套颜色角色是为了视觉一致性选的，**只要容器与文字达到 3:1 对比度，可以用别的角色**（例如 tertiary / onTertiary）。

## Toggle 按钮的颜色（Expressive）

Toggle 有「未选中 / 选中」两套颜色，且**都不使用 text 样式**（没有 toggle text button）：

| 样式 | 未选中 | 选中 |
|---|---|---|
| Elevated | 容器 `surfaceContainerLow` / 内容 `primary` | `primary` / `onPrimary` |
| Filled | 容器 `surfaceContainer` / 内容 `onSurfaceVariant` | `primary` / `onPrimary` |
| Tonal | 容器 `secondaryContainer` / 内容 `onSecondaryContainer` | `secondary` / `onSecondary` |
| Outlined | 描边 `outlineVariant` / 内容 `onSurfaceVariant` | `inverseSurface` / `inverseOnSurface` |

## 尺寸与形状

### 尺寸档（Expressive）

XS / Small（M3 唯一档）/ M / L / XL。**小尺寸的水平内边距**：M3 为 24dp；Expressive 推荐 16dp（官网对 small 用 24dp 标注为 "Not recommended. Use 16dp"），XS/M/L/XL 均为 16dp。

容器高度：Small 为 40dp（通行值，官方图片中未取到文字值），其余档位按比例递增，需查 Specs 图。

### 圆角

| | XS | S | M | L | XL |
|---|---|---|---|---|---|
| A. 圆形按钮 | Full | Full | Full | Full | Full |
| B. 方角按钮 | 12dp | 12dp | 16dp | 28dp | 28dp |
| C. **按下态** | 8dp | 8dp | 12dp | 16dp | 16dp |

### 形状 morph（Expressive 的核心交互）

- **按下时按钮会变得更方**，圆形与方角按钮的按下形状**必须一致**（都取 C 行的值）。
- Toggle 按钮除了按下变形，**静止态也会变**：未选中是圆形，选中变方角。
- 反过来也成立：如果静止未选中是方角，选中应变圆形。

这不是装饰，是状态反馈。但要跑在 expressive API 上，且关掉动效后形状仍需是合理静态值。

## 触控目标

官网原文：**Extra small 和 small 尺寸的图标按钮，触控目标必须 ≥ 48×48dp 才算可访问。**

普通按钮由组件补齐，但自定义尺寸时要自己守这条。

## 文案规则

- 用动词短语：「保存修改」「删除这条笔记」，不用「确定」「是」。
- 破坏性操作用 `error` 色系，并配二次确认或撤销。
- 一屏只允许一个最高强调（filled）按钮。

## Compose 实现

```kotlin
Button(onClick = { save() }) {
    Text("保存")
}
```

带图标 + 加载态（图标描述置空，避免重复朗读）：

```kotlin
Button(onClick = { save() }, enabled = !isSaving) {
    if (isSaving) {
        CircularProgressIndicator(
            modifier = Modifier.size(16.dp),
            strokeWidth = 2.dp,
            color = LocalContentColor.current
        )
        Spacer(Modifier.width(8.dp))
        Text("保存中")
    } else {
        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(ButtonDefaults.IconSize))
        Spacer(Modifier.width(ButtonDefaults.IconSpacing))
        Text("保存")
    }
}
```

层级搭配（一个主 + 一个备选）：

```kotlin
Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
    OutlinedButton(onClick = onCancel) { Text("取消") }
    Button(onClick = onConfirm) { Text("提交申请") }
}
```

自定义颜色（破坏性操作）：

```kotlin
Button(
    onClick = onDelete,
    colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.error,
        contentColor = MaterialTheme.colorScheme.onError
    )
) { Text("删除账号") }
```

## 无障碍

- **按钮内图标不写 `contentDescription`（设 `null`）**：文案已表达动作，否则读成「删除 删除这条笔记」。
- 纯图标按钮（`IconButton`）**必须**写 `contentDescription`，且描述结果。
- 按钮内图标按钮的触控目标 ≥ 48dp（XS/S 图标按钮尤其容易违反）。
- 加载中状态用 `stateDescription` 或更新文案，不要只是转圈。
- 焦点顺序跟随代码顺序；「取消」放左侧时，代码也应在左。

## 常见错误

1. 在 M3（非 Expressive）里找 Square / Toggle / XS 尺寸 —— 不存在。
2. 用 `Modifier.background` 给按钮加底色，按下时状态层被盖住、没有反馈。
3. 两个 `Button` 并列，用户不知道哪个是「继续」。
4. 用 `TextButton` 做主操作，再用 `Button` 做「取消」，层级倒了。
5. 按钮内图标写了 `contentDescription`，读屏读两遍。
6. 用固定 `height(40.dp)`，200% 字体下文字被裁。
7. 深色主题下用 `ElevatedButton` 当主按钮，阴影几乎不可见。
8. 只改按钮颜色却不管对比度（官方要求容器与文字 ≥ 3:1）。

## 来源

- https://m3.material.io/components/buttons/specs
- https://m3.material.io/components/buttons
- `01-Foundations/color-system.md`（角色配对）
- `01-Foundations/shape-system.md`（形状档位）

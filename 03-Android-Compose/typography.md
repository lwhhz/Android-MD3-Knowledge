# Compose Typography 实现

- 官方指南：https://developer.android.com/develop/ui/compose/designsystems/material3
- 核验日期：2026-11-14 ｜ 版本相关：`defaultFontFamily` 参数随 material3 1.5.0 线提供

## 构造

```kotlin
// ui/theme/Type.kt
val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    // ... 其余 14 档
)
```

`Typography` 的 15 个槽位与 `01-Foundations/typography.md` 的字阶一一对应。**只覆盖需要改的档位**，其余用默认值——逐个覆盖全部 15 档是维护负担，而且容易和官方值漂移。

## 换品牌字体

三种方式，按侵入性从低到高：

| 方式 | 做法 | 适用 |
|---|---|---|
| 全局默认字体 | `Typography(defaultFontFamily = AppFontFamily)` | 1.5.0 线起可用；换整套字族最简单 |
| 逐档覆盖 | 每个 `TextStyle` 都指定 `fontFamily` | 需要按档位用不同字体（如标题用衬线） |
| 单个 Text | `style = MaterialTheme.typography.bodyLarge.copy(fontFamily = ...)` | 极个别场景 |

**只换 family 不够。** 不同字体的字面高度、字重分布不同，必须同时调整 `lineHeight` 和（必要时）`letterSpacing`，否则会出现文字垂直偏移、多行过挤。

中文尤其要注意：很多中文黑体只有 400 / 700 两档，指定 500 会 fallback 或合成加粗，导致标题粗细在中英混排时不一致。

## 字体回退

- Android 会按 `FontFamily` 内的 fallback 列表找字形，找不到时用系统默认字体。
- 自定义字体只包含拉丁字符时，中文会自动回退到系统字体——**字重和字面可能不一致**，需实机确认。
- 使用 `FontFamily(Font(R.font.xxx))` 时，建议同时提供不同字重文件，否则合成加粗会发虚。

## 文本缩放

Compose 的 `sp` 受系统字体缩放影响。规则：

1. **不要禁用系统字体缩放。** 用 `LocalDensity` 手动改 `fontScale` 会让所有无障碍设置失效。
2. **不要用 `dp` 写字号。** 那等于关掉了缩放。
3. **容器高度要自适应。** 用 `heightIn(min = ...)` 而不是 `height(...)`。
4. **测试 200%。** 在系统设置里把字体调到最大，逐屏看。

```kotlin
Text(
    text = title,
    style = MaterialTheme.typography.titleLarge,
    maxLines = 2,
    overflow = TextOverflow.Ellipsis
)
```

注意 `maxLines` + `Ellipsis` 在大字号下会丢信息。如果这段文字是关键信息，不要限制行数，让容器长高。

## 局部调整的边界

允许：`style = MaterialTheme.typography.bodyMedium.copy(color = ...)`（改颜色）
允许：`textAlign`、`maxLines`、`overflow`、`fontWeight` 的轻微调整
**不允许**：直接写 `fontSize = 17.sp`（脱离字阶），或 `.copy(fontSize = ...)` 微调字号（会让字阶系统失效）

需要「大一点点」时，正确的做法是换到上一档，而不是把 16 改成 17。

## 常见错误

1. 15 档全部手写覆盖，官方值更新后不同步。
2. 换字体只改 family，行高沿用旧值，文字垂直居中偏移。
3. 用 `dp` 写字号。
4. 为了「保持设计稿一致」关闭系统字体缩放。
5. `maxLines = 1` 写死，200% 字号下标题被截成「订单详…」。
6. 中文标题用 700 字重做强调，笔画糊在一起。

## 检查项

- [ ] 字号全部来自字阶，无裸 `fontSize`
- [ ] 自定义字体同时调过 `lineHeight`
- [ ] 200% 字号下无截断
- [ ] 深浅色主题下文字对比度达标
- [ ] 未禁用系统字体缩放

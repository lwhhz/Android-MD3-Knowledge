# 字体系统 / Typography

> 官方指南：https://m3.material.io/styles/typography/type-scale-tokens
> Compose：https://developer.android.com/develop/ui/compose/designsystems/material3
> 核验日期：2026-11-14
> 核验方式：headless 浏览器渲染官网正文后提取文字（WebFetch 只能拿到标题）
> 状态：**结构已确认（30 档 = 15 baseline + 15 emphasized，行高按语言类别伸缩）；具体数值仍待确认**

## 先记住结论

M3 现在的字阶不是 15 档，是 **30 档**：

| 组 | 档数 | 用途 |
|---|---|---|
| Baseline | 15 | 默认排版 |
| Emphasized | 15 | 已经在用字重（medium / bold）的文字上，进一步表达层级与状态 |

再加一条容易忽略的维度：**行高按语言自动伸缩**（中文属于需要加高 ~7% 的类别），所以「一套行高走天下」在中英混排里必然出问题。

## 30 档结构

```
Display   Large / Medium / Small     × {Baseline, Emphasized}
Headline  Large / Medium / Small     × {Baseline, Emphasized}
Title     Large / Medium / Small     × {Baseline, Emphasized}
Body      Large / Medium / Small     × {Baseline, Emphasized}
Label     Large / Medium / Small     × {Baseline, Emphasized}
```

## Baseline 15 档的通行值

> ⚠️ 下表数值为 M3 长期通行值（多为 2021 年起的规格），**本次通过浏览器渲染仍未在官网 DOM 中取到数值表**——官网把令牌放在图片和折叠的 token 模块里。用于生产前请对照官网 token 模块或 Material Theme Builder 导出值确认。

| 样式 | 字号 | 行高 | 字重 | 字距 |
|---|---|---|---|---|
| Display Large | 57sp | 64sp | 400 | -0.25sp |
| Display Medium | 45sp | 52sp | 400 | 0sp |
| Display Small | 36sp | 44sp | 400 | 0sp |
| Headline Large | 32sp | 40sp | 400 | 0sp |
| Headline Medium | 28sp | 36sp | 400 | 0sp |
| Headline Small | 24sp | 32sp | 400 | 0sp |
| Title Large | 22sp | 28sp | 400 | 0sp |
| Title Medium | 16sp | 24sp | 500 | 0.15sp |
| Title Small | 14sp | 20sp | 500 | 0.1sp |
| Body Large | 16sp | 24sp | 400 | 0.5sp |
| Body Medium | 14sp | 20sp | 400 | 0.25sp |
| Body Small | 12sp | 16sp | 400 | 0.4sp |
| Label Large | 14sp | 20sp | 500 | 0.1sp |
| Label Medium | 12sp | 16sp | 500 | 0.5sp |
| Label Small | 11sp | 16sp | 500 | 0.5sp |

规律：Display / Headline / Title Large / Body 用 400；Title Medium、Title Small 与全部 Label 用 500。字号越小，正字距越大。

## Emphasized 字阶（官网已确认存在，数值待取）

官网对它的说明，直接决定了怎么用：

- **用在已经有字重的文字上**（medium、bold），用来表达层级或状态。
- 「Emphasized context」和「weight」**可以同时使用**。
- Baseline 与 emphasized **可以用不同字体**。
- 自定字阶或单个样式，**可能导致无法接收官方的 typography token 更新**。

判断规则：

| 情况 | 用什么 |
|---|---|
| 普通正文、普通标题 | Baseline |
| 需要在一堆 medium 文字里再突出一个 | Emphasized 同档 |
| 需要强调段落内某几个词 | 同档 Emphasized，或换字体宽度轴，不要直接改字号 |

**不要**为了强调去写 `fontSize = 18.sp`。官网明确写了：改字号会影响组件的渲染与重排。

## 语言高度（Language height）——中文必看

官方把语言按行高需求分四类：

| 类别 | 行高相对基准 | 语言 |
|---|---|---|
| Small（基准） | ×1.00 | 西里尔、希腊、希伯来、拉丁系（越南语除外） |
| **Medium** | **×1.07（约 +7%）** | **中文**、日文、韩文、阿拉伯、印地、泰、孟加拉、泰米尔、越南语等大多数语言 |
| Large | ×1.30 | 缅甸语、泰卢固语 |
| Extra large | ×2.00 | Nastaliq（波斯/乌尔都书法体） |

含义：
- Display 这类大字号样式，行高会**按当前语言类别自动适配**。
- 中文界面若沿用 Latin 的行高，多行段落会明显拥挤；反之在英文界面用中文行高会显得松散。
- **固定高度的组件（如按钮、列表项）不会自动适配**，语言高度变大时会出现文字重叠——这正是官网点名的问题。
- 需要在 token 模块里切换「language height context」查看不同语言下的行高值。

Compose 侧的具体 API（是否有 `LocalLanguageHeight` 之类）**尚未确认**，见待核验清单。

## 按用途选样式，不按字号选

| 场景 | 样式 |
|---|---|
| 首屏大数字、品牌口号 | Display Small / Medium |
| 页面主标题 | Headline Small / Medium |
| 分区标题 | Title Large |
| 列表项标题 | Title Medium |
| 正文段落 | Body Large（长文）/ Body Medium（默认） |
| 辅助说明、时间戳 | Body Small |
| 按钮文字、Chip、Tab | Label Large |
| 输入框浮动标签 | Body Small（浮起态）/ Body Large（静止态） |
| 角标、极小标注 | Label Small |

**常见误用**：拿字阶当「字号表」，在 Body Large 的位置放 16sp 的 Title。决定用哪档的是**语义**，不是像素接近。

## 字重

常规层级只用 400 和 500。需要更强强调时，**优先用 Emphasized 同档，其次升级字阶，最后才考虑加字重**——700 在中文里容易糊成一团。

## 字体族与回退

- 默认 Roboto；M3 使用 Roboto Flex 的可变轴（weight、width）做定制。
- **品牌字体（brand）与正文/强调样式可以分开设置**，官方支持 baseline 与 emphasized 使用不同字体。
- 中文由系统回退到设备中文字体，各厂商不同，需实机确认。
- 换字体时 `FontFamily`、`lineHeight`、`letterSpacing` 必须一起改；只换 family 会导致垂直居中偏移、多行过挤。

## Compose 实现

```kotlin
val AppTypography = Typography(
    headlineSmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    )
)
```

- material3 1.5.0 线的 `Typography` 新增 `defaultFontFamily` 参数，可一次性替换全套默认字体。
- 覆盖个别档位即可，**不要 15/30 档全部手写**——官方更新 token 时你不会同步。

## 文本缩放

- 全部使用 `sp`，**禁止用 `dp` 写字号**。
- 必须在系统字体缩放 200% 下验证。
- 固定高度容器是字体缩放与语言高度的共同杀手，改用 `heightIn(min = ...)`。

## 常见错误

- 以为字阶只有 15 档，忽略 emphasized。
- 为强调改字号（官网明确警告会影响组件渲染与重排）。
- 中文界面沿用 Latin 行高，多行段落拥挤。
- 固定高度容器装中文 Display 样式，文字被裁。
- 自定义字体只改 family 不改行高与字距。
- 用 ALL CAPS 做强调（中文无此概念，且破坏本地化）。
- 一屏用超过 4 档字阶，层级反而模糊。

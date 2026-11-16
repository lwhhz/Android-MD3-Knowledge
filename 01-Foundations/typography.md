# 字体系统 / Typography

> 官方指南：https://m3.material.io/styles/typography/type-scale-tokens
> Compose：https://developer.android.com/develop/ui/compose/designsystems/material3
> 核验日期：2026-11-16
> 核验方式：headless 浏览器渲染官网正文后提取文字（WebFetch 只能拿到标题）
> 状态：**结构、规则、字体家族、editorial treatments 均已按官网原文核对；30 档的逐档数值官网正文仍不输出（只在折叠的 token 模块里），见下文标注**

## 先记住结论

M3 现在的字阶不是 15 档，是 **30 档**：

| 组 | 档数 | 用途 |
|---|---|---|
| Baseline | 15 | 默认排版 |
| Emphasized | 15 | 已经在用字重（medium / bold）的文字上，进一步表达层级与状态 |

再加一条容易忽略的维度：**行高按语言自动伸缩**（中文属于需要加高 ~7% 的类别），所以「一套行高走天下」在中英混排里必然出问题。

## 30 档结构

官网原文：**「Material 3 has one type scale containing two sets of type styles: 15 baseline and 15 emphasized. Both of these style sets follow the same scale from Display Large to Label Small.」**

```
Display   Large / Medium / Small     × {Baseline, Emphasized}
Headline  Large / Medium / Small     × {Baseline, Emphasized}
Title     Large / Medium / Small     × {Baseline, Emphasized}
Body      Large / Medium / Small     × {Baseline, Emphasized}
Label     Large / Medium / Small     × {Baseline, Emphasized}
```

一些容易忽略的前提：

- 它是**一套**字阶（one type scale），不是两套。baseline 与 emphasized 是同一套字阶的两种风格，设计意图是**一起用**。
- 「No single product will use all the styles.」——15 档是选择池，不是清单。
- 30 档每档都有一个**复合令牌**（一个令牌包含全部默认属性）；font、line height、size、tracking、weight 每个轴另有**单属性令牌**，供细调。

## Baseline 15 档的通行值

> ⚠️ 下表数值为 M3 长期通行值（多为 2021 年起的规格）。**2026-11-16 本次核验仍然取不到数值表**：官网 `type-scale-tokens` 页在「Baseline type style tokens」「Emphasized type style tokens」两处只留下折叠容器的入口，正文里没有任何数字。用于生产前请对照官网 token 模块或 Material Theme Builder 导出值确认。

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

## Emphasized 字阶（规则已确认，数值待取）

官网对它的定义：**「They have a higher weight and other minor adjustments compared to the baseline styles, and are best applied to bold, selection, and other areas of emphasis.」**

也就是说 emphasized 不是「更大的字」，而是**同档、更重、带微调**的一套平行样式。

官网给出的适用位置：

> Material recommends using emphasized styles for selection, actions, headlines, and other editorial treatments.

### 哪些组件该用（官方点名）

| 组件 | 用法 |
|---|---|
| Badges | 未读/计数需要突出 |
| Buttons（仅 primary actions） | 主操作 |
| Extended FAB | 主操作 |
| Selected list items | 选中态 |
| Selected menu items | 选中态 |

**关键前提**：官网原文 **「Material components don't use emphasized type styles by default.」**——组件默认不吃 emphasized，必须手动换令牌：

```
Baseline:    md.sys.typescale.display-large
Emphasized:  md.sys.typescale.emphasized.display-large
```

即「把同档的 baseline 令牌换成 emphasized 令牌」，不是改字号、不是加粗。

### 两条使用维度

- **Weight**：「Use the emphasized styles on text that already uses weight (such as medium, bold) to communicate hierarchy.」
- **Context**：用于选中态、未读消息、关键交互等局部位置。
- 两者**可以同时使用**（「Emphasized context and weight can be used at the same time.」）。

判断规则：

| 情况 | 用什么 |
|---|---|
| 普通正文、普通标题 | Baseline |
| 需要在一堆 medium 文字里再突出一个 | Emphasized 同档 |
| 需要强调段落内某几个词 | 同档 Emphasized，或换字体宽度轴，不要直接改字号 |
| 选中态 / 未读 / 主操作 | Emphasized 同档（官方点名的五类组件） |

**不要**为了强调去写 `fontSize = 18.sp`。官网明确写了：改字号会影响组件的渲染与重排。

### 自定时的一致性要求

- Baseline 与 emphasized **可以用不同字体**（「Different typefaces can be used for baseline and emphasized type styles.」）。
- 但同时自定时要「Try to keep emphasized styles visually consistent, like all wider than baseline.」——例如统一让 emphasized 比 baseline 宽，而不是每档各调各的。
- 自定字阶或单个样式，**可能导致无法接收官方的 typography token 更新**。

## 语言高度（Language height）——中文必看

官方把语言按行高需求分四类：

| 类别 | 行高相对基准 | 语言 |
|---|---|---|
| Small（base） | ×1.00 | 西里尔、希腊、希伯来、拉丁系（越南语除外） |
| **Medium**（约 +7%） | **×1.07** | **中文**、日文、韩文、阿拉伯、印地、泰、孟加拉、泰米尔、越南语、阿姆哈拉语、亚美尼亚语、古吉拉特语、卡纳达语、高棉语、马拉雅拉姆语、奥迪亚语、旁遮普语、僧伽罗语，以及大多数其他语言 |
| Large（约 +30%） | ×1.30 | 缅甸语、泰卢固语 |
| Extra large（约 +100%） | ×2.00 | Nastaliq（波斯/乌尔都书法体） |

官网的落地建议（Development considerations）：

- **默认按 medium 处理**，再按检测到的语言切换——「It's recommended to default to medium language height, as most languages fall in that category」。
- 带**垂直内边距**的组件会自动适配语言高度。
- **固定高度**的组件是按 small 值构建的，「may not adapt by default」。后果官方写得很重：「Ignoring language height can lead to overlapping text and broken UI elements, leading to major internationalization issues and failing readability standards.」

含义：
- Display 这类大字号样式，行高会**按当前语言类别自动适配**。
- 中文界面若沿用 Latin 的行高，多行段落会明显拥挤；反之在英文界面用中文行高会显得松散。
- **固定高度的组件（如按钮、列表项）不会自动适配**，语言高度变大时会出现文字重叠——这正是官网点名的问题。
- 需要在 token 模块里切换「language height context」查看不同语言下的行高值。

Compose 侧的具体 API（是否有 `LocalLanguageHeight` 之类）**尚未确认**，见待核验清单。

## 五个 role 的官方定义

字阶用五个 role 命名，每个 role 三个 size。官网对每个 role 的定位是有明确边界的：

| Role | 官方定位 | 能不能用表达型字体 |
|---|---|---|
| **Display** | 屏幕上最大的文字，留给**短的、重要的文本或数字**；在大屏上效果最好 | **推荐**换手写体/脚本体这类表达型字体，并按需设 optical size |
| **Headline** | 短、高强调，**更适合小屏**；用来标记主要内容段落或重要区域 | 可以，「provided that appropriate line height and letter spacing is also integrated」 |
| **Title** | 比 headline 小，中等强调，篇幅仍然较短；用于划分次级段落/次级区域 | **谨慎**，官方点名要小心 display、手写、脚本体 |
| **Body** | 较长段落 | **不要**用表达型/装饰字体——小字号下更难读 |
| **Label** | 更小的功能性样式：组件内文字、正文里极小的标注（如 caption） | 不适用（button 用的是 label large） |

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

## 排版落地（Applying type）

### 两套垂直排版方法，先按平台选

官网给了两套互斥的方法，混用会导致设计稿与实现差几个 dp：

| 方法 | 适用平台 | 行高怎么量 | 居中怎么定 |
|---|---|---|---|
| **padding + bounding box** | Web、iOS（部分设计工具也用 bounding box，但方法各异，需与工程实现对齐） | 行高 **就是** bounding box 的高度 | 文字在框内垂直居中，遵循 CSS 的 half-leading |
| **baseline（基线）** | Android、平台无关规格 | 上一行 baseline 到下一行 baseline 的距离 | 指定 center alignment 作参照，**不要**去量到 baseline 的距离 |

要点：

- Web 上「文字垂直位置不是直接控制的，而是 bounding box 与字体度量共同决定」——所以光改 `line-height` 会连带改变文字在框内的位置。
- Android 上「specifying distances relative to baseline enables accurate implementation」——间距一律写成「参考点到 baseline」。
- 间距（spacing）也用同一套参照系推导，不要一半用 padding 一半用 baseline。

### 行高比例（只在自定尺寸时用）

> For larger type legibility using styles like title, headline, and display, we recommend a line height ratio of **1.2 times** the type size.

> For smaller body copy using styles like body and label, we recommend a line height ratio around **1.5 times** the type size.

「If your line height is too tight, you'll undermine the flow of the text. Too loose, and the lines won't feel cohesive.」

> ⚠️ 推断，页面未明示：1.2 / 1.5 是「改了字号之后」的建议比例，不是现有令牌的实际比值——通行值里 Title Large 是 22→28（≈1.27）、Label Large 是 14→20（≈1.43）。没改字号就照抄令牌行高，不要用比例倒推。

### 数字：表格和时钟用等宽数字

官网：表格、时钟这类「值会频繁变化」的地方用 **tabular figures（等宽数字）**，不要用 proportional digits。等宽数字让数值光学对齐，且变化时不引起布局跳动。

### 颜色与对比度

- 正文默认色是 **on-surface**；**on-surface-variant** 是强有力的替代。
- 链接：surface 上用 **primary**；想让链接不那么抢眼可以用 **tertiary**。
- **链接必须加下划线**（「Hyperlinked text must also be underlined.」）——不能只靠颜色区分。
- 对比度门槛：**大字号 3:1，小字号 4.5:1**。

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

## 来源

| 页面 | URL |
|---|---|
| Typography overview | https://m3.material.io/styles/typography/overview |
| Type scale tokens | https://m3.material.io/styles/typography/type-scale-tokens |
| Fonts | https://m3.material.io/styles/typography/fonts |
| Applying type | https://m3.material.io/styles/typography/applying-type |
| Editorial treatments | https://m3.material.io/styles/typography/editorial-treatments |

核验日期：2026-11-16（浏览器渲染取正文，覆盖全站 375 页抓取批次）。

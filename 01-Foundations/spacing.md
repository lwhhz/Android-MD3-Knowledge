# 间距系统 / Spacing

> 官方指南：https://m3.material.io/styles/spacing/overview
> 官方 Tokens：https://m3.material.io/styles/spacing/tokens
> 核验日期：2026-11-16
> 核验方式：浏览器渲染官网正文后提取（m3.material.io 全站 dump）
> 状态：**8dp 基线、18 个 system token、命名规则均为官方原文**；「场景→具体数值」对照表官网未提供，本文不编

## 一句话结论

官网原文：**「The spacing system is measured on an 8dp scale, where space100 = 8dp」**

基线单位是 **8dp**，不是 4dp。4dp 在这一套里是 `space50`，属于「嵌套单位」，不是基线。

## 先纠正一个通行说法：不是「4dp/8dp 双基线」

| 说法 | 官网实际 |
|---|---|
| 「Material 用 4dp 网格，8dp 是常用值」 | 官网只写 8dp 尺度，`space100 = 8dp` |
| 4dp 是基线 | 4dp = `space50`，是 8dp 的 0.5 倍，属 **nested unit** |
| 所有值必须是 8 的倍数 | 2dp / 4dp / 6dp / 10dp 等非 8 倍数在布局与组件里真实存在 |

官网对非 8 倍数的原话：「**Values other than multiples of 8 are also used in layouts and Material components, like 2dp, 4dp, 6dp, and 10dp. Material only defines the most recommended nested units.**」

也就是说：**8dp 是乘数基线，4dp 是可用的半档，不是两套并行的栅格。**

⚠️ 推断，页面未明示：官方只给了 token 表与「nested units 用在布局与组件里」这一句，没有说哪些档位用于主结构、哪些用于微调。按 token 密度归纳，16 / 24 / 32 / 48 这类 8 的倍数更适合定主结构，2 / 4 / 6 / 10 更适合紧凑组件的微调。

## 18 个 system token（官方全表）

| Token | 值 | 相对 8dp |
|---|---|---|
| `space0` | 0dp | 0 |
| `space25` | 2dp | 0.25× |
| `space50` | 4dp | 0.5× |
| `space75` | 6dp | 0.75× |
| `space100` | **8dp** | 1×（基线） |
| `space125` | 10dp | 1.25× |
| `space150` | 12dp | 1.5× |
| `space175` | 14dp | 1.75× |
| `space200` | 16dp | 2× |
| `space250` | 20dp | 2.5× |
| `space300` | 24dp | 3× |
| `space400` | 32dp | 4× |
| `space450` | 36dp | 4.5× |
| `space500` | 40dp | 5× |
| `space600` | 48dp | 6× |
| `space700` | 56dp | 7× |
| `space800` | 64dp | 8× |
| `space900` | 72dp | 9× |

官网对这套表的定位：「**System spacing tokens are a linear range of spacing values recommended by Material. They're intended to cover the majority of spacing needs within the design system.**」

注意 token 值里的 25 / 50 / 75 不是 dp，是 **8dp 的百分比**：`space125` = 8 × 1.25 = 10dp。命名公式：

```
token 名 = space + (dp ÷ 8 × 100)
反推：dp = N / 100 × 8
```

官网给的扩展示例：「**space225 = 18dp (8dp x 2.25)**」——这条公式是官方认可的加 token 方式。

## 三种间距：padding / gap / margin

| 类型 | 定义（原文） | 用在哪 |
|---|---|---|
| Padding | **Space inside an element** | 容器边缘到内容（文字、图标）之间的缓冲 |
| Gap | **Space between elements in a grid or container** | 同级元素之间；水平 gap 用于并排，垂直 gap 用于堆叠 |
| Margin | **Space outside an element** | 元素与父容器或屏幕边缘之间 |

三个位置维度：**horizontal / vertical**，以及单边 **leading / trailing / top / bottom**。

**RTL 会换边**：leading 与 trailing 在从右到左的语言里互换（官网原文：「**Leading and trailing edges swap sides in right-to-left (RTL) languages.**」）。所以不要写 `paddingLeft` / `paddingRight`。

## 什么时候用哪种

官网的优先级非常明确：**先 padding 与 gap，最后才 margin**。

> 「**Material rarely uses margins in components; padding and gaps tend to apply spacing in a more uniform way.**」
> 「**Only use margins to apply further spacing beyond the parent container's padding, or in layouts.**」

落到判断上：

| 场景 | 用什么 | 依据 |
|---|---|---|
| 组件内文字/图标到边界 | padding | 官网：padding 是容器边缘到内容的缓冲 |
| 组件内同级元素之间 | gap | 官网：gap 是 grid/container 内元素之间 |
| 组件内元素间距各处不同 | 按两侧元素命名 gap（如 icon-label gap） | 「**Complex components with many different gaps should define them by the elements on each side**」 |
| 超出父容器 padding 的额外留白 | margin | 官网唯一允许 margin 的组件场景 |
| 页面级留白（面板外、贴屏幕边） | margin | 「**or in layouts**」 |
| 子元素自己加外边距 | **不要** | 官网 Don't：子元素 margin 通常不均匀且要多出 token |

Do / Don't 的原文：

> Do：「**Define padding and gaps on the parent container to organize all elements inside**」
> Don't：「**Avoid defining margins on child elements as they usually aren't uniform, and require more tokens**」

## 边缘与元素间距：官方唯一给数值的例子

Search 容器（官网范例，三处数值都写明）：

| 部位 | 值 |
|---|---|
| 垂直 padding | **8dp**（`space100`） |
| 水平 gap | **8dp**（`space100`） |
| 水平 margin（默认） | **24dp**（`space300`） |
| 水平 margin（获焦时） | **12dp**（`space150`） |

> 「**Search horizontal margins are 24dp by default to ensure accurate placement from the screen edge**」
> 「**Search margins change to 12dp when focused, while the container padding and horizontal gaps remain the same**」

这个例子值得记的不是四个数字，而是三条结构信息：

1. **贴屏幕边的那层留白用 margin，且是 24dp**；
2. **获焦时只动 margin，不动 padding 与 gap**——动一处，视觉重心才会稳；
3. 组件内部保持 8dp 的整数倍，24→12 也是 token 之间的跳转，不出现 14dp、18dp 这类临时值。

⚠️ 推断，页面未明示：24dp 是 Material 常见的页面侧边距，但 spacing 三页只给了 search 这一个例子，没有给「所有页面边距 = 24dp」的普适规则。

## 组件布局 vs 页面布局

官网把间距分成两个作用层级：

**组件布局（Component layouts）**——定义组件内部 text / icon / control 之间的 padding 与 gap：

- 垂直 padding（top & bottom）
- 垂直 gap
- 水平 padding（leading & trailing）
- 水平 gap

**页面布局（Page layouts）**——标准化整页构图：

- 用 **panes、spacers、margins** 搭结构
- 用 **padding 与 gap** 组织 pane 内部内容
- 概念包括：margin、top padding、horizontal padding（leading & trailing）、spacer（gap）、vertical gap

## 与布局网格的关系

官网在 overview 里只给了一句外链式表述：间距「**More details on grid & spacing design principles**」，把网格细节交给 layout 指南。

可以确认的边界：

- spacing 三页**没有**给列数、gutter、断点值，也没有「12 列栅格」之类内容；
- spacing 与网格的分工是：**网格决定页面结构与分栏（panes/spacers/margins 层面），spacing token 决定结构内部的 padding 与 gap**；
- 因此本库不把「12 列 / 24dp gutter」写进间距系统。

⚠️ 推断，页面未明示：栅格数值需查 layout 相关页面，本页 dump 未覆盖，不做断言。

## Token 在代码里的位置

**只有 Jetpack Compose 落地了这套 token。**

> 官方 Note：「**The spacing system tokens are only used on Jetpack Compose.**」

资源可用性（官网表格原文）：

| 平台 | 状态 |
|---|---|
| Jetpack Compose | Available |
| Android Views (MDC-Android) | **Unavailable** |
| Web | **Unavailable** |

所以 Android Views 项目里不存在「官方 spacing token」，只能按 8dp 表手写或自建 attrs。这不等于数值失效，只是没有 token 层。

## 组件属性命名已换规则

官网原文：「**Going forward, all component spacing attributes will use padding, margin, and gap, and positional language: horizontal, vertical, leading, trailing, top, and bottom**」

| 旧 | 新 |
|---|---|
| `leading-space` | `leading padding` |
| `trailing-space` | `trailing padding` |
| `top-space` | `top padding` |
| `bottom-space` | `bottom padding` |
| `between-space` | `gap`（或按元素命名，如 icon-label gap） |

示例对比：**「Medium button: leading space」→「Medium button: leading padding」**。旧 token 用 `space` 一词统称 padding/gap/margin，新规则要求说清是哪一种。

## 三档定制手段

官网把定制分成三条路，选哪条取决于改动范围：

| 手段 | 什么时候用 | 官方示例 |
|---|---|---|
| 改 Material 组件的既有映射 | 想让整个产品里的该组件都变 | 把 button 的 top padding 从 `space125` 映射到 `space200`，得到更高的默认按钮 |
| 新增 system spacing 与 pattern | 现有值不够用，或有反复出现的自适应模式 | 按 `space225 = 18dp` 的倍数规则加值；把「卡片与 sheet 同步变化的水平内边距」抽成 surface content horizontal padding |
| 加自适应布局与密度 | 同一组件要按设备类型 / 密度呈现不同 | 按设备类型映射不同 token；density **只调垂直 padding** |

注意最后一条：官网对 density 的描述是「**Density: Adapt vertical padding to different spacing values for each setting**」——密度改的是**垂直** padding，不是等比缩放全部间距。

## 文本缩放时不要缩间距

> 「**When text is scaled up to 200%, the same spacing should be preserved by default.**」

即：字号放大 200%，padding / gap 保持原值，不跟着乘 2。间距是结构，不是文字的一部分。

⚠️ 常见误读：这条说的是「不要压缩间距」，不是「间距要跟着字号变大」。

## 为什么这套系统比色彩简单

官网自己做了对比：「**Material's spacing system is intentionally designed to be a simple linear scale. Unlike the color system, which adjusts light and dark theme logic across all components at once, tailored spacing logic is built within each component.**」

含义：色彩是全局算法（一个源色改全部组件），间距是**逐组件**逻辑（每个组件自带映射）。所以：
- 不要指望改一个 token 就统一全站间距；
- 组件间不一致时，先查该组件的映射，而不是全局换值。

组件到 token 的链路（原文）：「**Components have padding tokens that map to system tokens, which resolve to final values**」。

现状提示：「**Work is ongoing to hook up all Material components to spacing tokens.**」——不是所有组件都已接上，遇到没接上的组件属预期。

## 常见错误

- 把 4dp 当基线，写出 4 / 12 / 20 / 28 的数值序列——那不是官方尺度。
- 用 14dp、18dp、22dp 这类非 token 值凑视觉。
- 在子元素上写 margin，父容器 padding 形同虚设。
- 用 margin 做组件内部间距。
- 用 `paddingLeft` / `paddingRight`，RTL 下不翻转。
- 字号放大时同步放大间距，200% 字号下布局被撑爆。
- 以为改一个 spacing token 能统一全产品——间距是逐组件映射。
- 在 Android Views 里找官方 spacing token——该平台标为 Unavailable。

## 检查项

- [ ] 所有间距值来自 18 个 system token，或按 `spaceN = N/100 × 8dp` 扩展
- [ ] 没有非 token 的临时值（14 / 18 / 22 等）
- [ ] 组件内优先 padding + gap，margin 只用于超出父 padding 或页面布局
- [ ] 子元素没有自定义 margin
- [ ] 用 leading / trailing，不用 left / right
- [ ] 200% 字号下间距保持不变
- [ ] density 适配只改垂直 padding
- [ ] 组件属性命名为 padding / margin / gap + 方位，不再用 `-space`

## 来源

- 间距总览（8dp 尺度、padding/gap/margin、search 示例）：https://m3.material.io/styles/spacing/overview
- 间距应用（定制三档、文本缩放）：https://m3.material.io/styles/spacing/applying-spacing
- 间距 token（18 个值、命名规则、组件命名迁移）：https://m3.material.io/styles/spacing/tokens

核验日期：2026-11-16

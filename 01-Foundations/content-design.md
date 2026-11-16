# 内容设计 / Content Design（UX 写作）

> 官方指南（总览）：https://m3.material.io/foundations/content-design/overview
> 官方指南（风格指南）：.../content-design/style-guide/ux-writing-best-practices ｜ /word-choice ｜ /grammar-and-punctuation
> 官方指南（全球化写作）：.../content-design/global-writing/overview ｜ /word-choice
> 官方指南（Alt text）：.../content-design/alt-text
> 官方指南（通知）：.../content-design/notifications
> 官方指南（写作与文本）：https://m3.material.io/foundations/writing/best-practices ｜ /text-truncation ｜ /text-resizing
> 核验日期：2026-11-16
> 核验方式：m3.material.io 全站 dump 逐页核对
> 状态：**字符上限、200% 缩放、1.5 倍译文膨胀均为官网原文数值**；alt text 存在 140 / 125 两处不一致，已并列

## 一句话结论

官网原文：**「UI text should be clear to anyone」**，基准是 **「Follow Associated Press (AP) Style unless noted otherwise.」**

本页所有标点、大小写、代词规则都是**美式英语 + AP 风格**，中文界面借的是「原则」，不是「结论」。

## 一、五条写作硬规则

| # | 规则 | 官网原话 |
|---|---|---|
| 1 | 用第二人称 | 「Use the second-person pronouns "you" and "your" to help the user to feel like the UI is talking to them」 |
| 2 | 不混用第一、第二人称 | 「Avoid mixing "me" or "my" with "you" or "your."」 |
| 3 | 慎用 I / we | 「Avoid using first person pronouns. Write from the user's point of view by using second person pronouns or removing pronouns altogether.」 |
| 4 | 说明后果 | 「Emphasize the results of the user's potential action in neutral, direct language.」 |
| 5 | 用 sentence case | 「All text, including titles, headings, labels, menu items, navigation components, app bars, and buttons should use sentence-style capitalization.」 |

第 3 条例外：**法律文本**。「"I" or "my" (the first person) emphasizes ownership in agreements or acknowledgments.」例如「I agree to the terms of service.」

第 4 条要求同时告诉用户**怎么撤销**：「Tell users what will happen if they take an action and how they can undo it」，且**不要试图左右用户决策**。

### 可扫读（scannable）

> People scan UI text in search of the most meaningful content to them.

手段：具体的标题与副标题、拆段、用到位的 heading 分组。原文特别指出用户在赶时间时，结构清晰能减少误操作。

### 缩写

「Spell out words whenever possible.」**避免拉丁缩写**（e.g.、etc.），改写成「for example」「and more」——理由很实在：**缩写对人和屏幕阅读器都不友好**。

## 二、强调手段：字重、斜体、大小写

| 手段 | 官方结论 | 原话 |
|---|---|---|
| 粗体（bold weight） | **首选**强调方式 | 「When emphasizing text, use bold weight instead.」 |
| 斜体 | 只用于单词或短语（名称、标题） | 「Italics typically aren't easy to read.」「Don't italicize a sentence」 |
| 全大写块（caps block） | **禁用** | 「Avoid using caps blocks altogether; they're not accessible.」 |
| 引号 / 颜色 | 未在本轮 dump 中给出规则 | — |

⚠️ 推断，页面未明示：中文没有大小写，因此「禁止 caps block」在中文语境等价于**不要用全角大写英文、不要用字距拉伸的全大写标签**；中文强调优先用字重，而非加颜色。

## 三、标点与语法

### 句号

| 加句号 | 不加句号 |
|---|---|
| 多句文本 | 标签（labels） |
| 长句或复杂句（视语境） | Tooltip 文本 |
| — | 项目符号列表项 |
| — | 对话框正文（单句时） |
| — | 超链接文本 |

> Avoid using periods to end single sentences.

### 其他标点规则表

| 项 | 规则 | 例外 / 注意 |
|---|---|---|
| 缩约（contractions） | 能用缩写就用（don't、can't），更易扫读 | 需要警示时「do not」比「don't」更有力 |
| 序列逗号（Oxford comma） | 三项及以上列表要加；逗号放在引号内 | 在 `&` 之前不加 |
| 数字逗号 | 1,000–1 百万之间用逗号 | 门牌号、无线电频率、年份不加；超过 100 万可用「M」并取整 |
| 冒号 | 正文内的列表用冒号 | **列表的标题不加冒号** |
| 感叹号 | 极少用；留给问候、祝贺 | 空态与常规任务不要用，「Save it for bigger accomplishments」 |
| 省略号 | 表示进行中或未完成 | 前面不加空格；**按钮与菜单项不要用省略号** |
| 括号 | 只用于定义缩写/术语或标注来源 | 不要用它塞补充说明，直接写进句子 |
| & 符号 | 可用于标题、列头、表头、导航标签、按钮 | 句子与段落中拼写为 and；**邮件主题行不要用 &** |
| Em dash（—） | **UX 写作中最好避免** | 用逗号、句号或断句替代 |
| En dash（–） | 表示范围，前后不加空格 | 例：`9 AM–Noon` |
| Hyphen（-） | 连接关系紧密的词、表示负数 | 前后**永不加空格**；动词短语不加连字符（check in ≠ check-in） |

## 四、长度配额（按场景）

| 场景 | 上限 | 出处 |
|---|---|---|
| Alt text | **140 字符** | content-design/alt-text：「The recommended length for alt text is 140 characters.」 |
| Alt text | **125 字符** | writing/best-practices：「a short label (up to 125 characters)」 |
| 通知标题 | **< 29 字符** | notifications |
| 通知折叠正文 | **< 40 字符** | notifications |
| 通知展开正文 | **< 80 字符**，从折叠正文扩写而来 | notifications |
| 通知按钮 | 1–2 个，每个 **1–2 词** | notifications |
| SMS（拉丁语系） | **< 160 字符** | notifications |
| SMS（非拉丁语系） | **< 134 字符** | notifications |

两处 alt text 上限**官方自相矛盾**，落地取更严的 125 字符即可覆盖两者。

通知页补充说明：新版本 Android 空间更大，**但这些限制仍然推荐**，以防小屏截断。

SMS 超限会被拆成多条并**增加费用**，所以上限是成本约束而非排版约束；需要翻译时，**在 message description 里把字符限制告知译者**。

## 五、通知文案

### 四条前提

| # | 要求 | 原话 |
|---|---|---|
| 1 | 关于用户，不是关于产品 | 「Be about the user, not the product」 |
| 2 | 精确、及时、可操作、有上下文、相关 | 「Be precise, timely, actionable, contextual, and relevant」 |
| 3 | 给用户容易的退出控制 | 「Give users easy controls to opt out」 |
| 4 | 不发未经许可的广告 | 「Not be used to send unsolicited ads」 |

### 具体规则

| 规则 | 做法 | 原因 |
|---|---|---|
| 重要信息放最前 | 把最关键信息提到句首 | 人按 **F 形**扫读，句尾容易被忽略或截断 |
| 告诉用户能做什么 | CTA 简洁、具体、可执行 | 不要把下一步埋在文案里 |
| 相关且个性化 | 用分群、应用内行为、个性化信息 | 越泛越容易被判定为无关 |
| 避免动态文本 | 动态文本放正文；放标题则**最多再加一个词**，并准备一条符合字数的备用通知 | 动态文本常超字数，**翻译后尤甚**；标题被截断后即使展开也不恢复 |
| 别浪费字符 | 不写应用名、客套话 | 应用名/图标已由系统呈现 |
| 不要过度卖萌 | 优先直白有用 | 「Jokes aren't appropriate for notifications」 |
| 写具体星期 | 用「周一」而不是「今天/明天」 | **约 20% 用户当天不会看到通知**，第二天读到「明天」就错了；唯一例外是到点自动消失的通知 |
| 别打断流程 | 引导流程期间不要推送相关通知/邮件 | — |
| 上下文内可退订 | 就地提供开关，并说明用户得到或失去什么 | 否则用户要钻进设置里找，容易受挫 |

### Emoji

- 少用。很多 emoji 无法翻译、跨文化理解不一致。
- 表情与手势类表现优于通用符号，因为「tell a better story」。
- **不要用 emoji 强调坏消息**：实验中皱眉、痛苦、疲惫脸引发强烈负面反应。
- 不要用 emoji 替代词。

## 六、Alt text

官网定义：**「Alternative text (alt text) refers to off-screen text that is used by screen reader accessibility software.」** 图片加载失败时也会显示。

### 判定流程

1. **删掉这张图，信息有损失吗？** 没有 → 装饰性 → `alt=""`（HTML），屏幕阅读器直接跳过。
2. 有信息 → 写 alt text，**描述含义与上下文，不描述像素细节**。
3. 图里有嵌入文字且信息必要 → 必须写进 alt text，**屏幕阅读器读不到图里的字**。
4. 图本身就是内容主体（图表、信息图、地图、截图、头像、示意图）→ 可以点名类型，但要谨慎。

### 禁止项

| 禁止 | 原因 |
|---|---|
| 以「image of」「picture of」开头 | 屏幕阅读器会念成「image, image of」 |
| 用文件名/自动编号当 alt text（如 `jpg - 0223939-330`） | 零信息 |
| 把图注（caption）原样复制成 alt text | 用户会听两遍 |
| 罗列全部细节 | 「Avoid detailed descriptions that don't contribute to the image's meaning.」 |
| 超过 140 字符 | 部分屏幕阅读器会截断 |

### 与上下文一致

- 用邻近文本（正文、图注、标题）确定用词；不要一处写「antique」另一处写「vintage」。
- **同一张图在不同页面应有不同 alt text。**
- 好的图注服务于所有用户，也让 alt text 更好写；图注不是 alt text 的复读机。

### 图表公式

> A general formula for chart alt text would be: "Summary of [data type] + [reason for showing the chart]."

| 图表类型 | 写法 |
|---|---|
| 编辑型（支撑一个结论） | 点出主结论 + 关键数据点，素材可取自正文、标签、图内文字 |
| 分析型（供整体研判） | 说明整体结构 + 指出可深入探索的入口（如数据源链接）；**不要强行总结成一句话结论**，避免引入偏差 |
| 复杂可视化 | 优先用**可交互图表**代替静态图，数据点细节放 tooltip |

### 视频与动效

- 动效信息若未在别处出现 → 需要 alt text；长视频用**视频描述（video description）**，不需要 alt text。
- GIF / 动效的 alt text 写「你会给它起的标题」，不要逐步描述每个动作。

## 七、本地化与全球化写作

官网定义：**「Global writing is the practice of writing inclusively for many cultures and optimizing for localization.」**

### 长度：译文平均膨胀 1.5 倍

> Other languages average at 1.5 times longer than English, so text that's short may be long when translated.

因此：句子拆短、多用项目符号与分节标题。⚠️ 德语在官网举例中被点名「比英语长」，但**未给具体倍数**。

### 十条规则

| # | 规则 | 要点 |
|---|---|---|
| 1 | 用全球通用例子 | 「Most countries and cultures have holidays」，不要只举某一国的节日 |
| 2 | 局部引用要解释 | 地点、人名/昵称、货币、温度、日期格式、网络/电视服务商都要在 message description 里说明，便于译者替换 |
| 3 | 短句 | 用 bullet、heading 拆内容 |
| 4 | 避免缩写 | 缩写译不好也脱离语境；**时间单位的常见缩写可以** |
| 5 | 明确代词 | 碎片化字符串 + 名词有性别时，代词会翻错；重复名词或在描述里说明 |
| 6 | 不用「this」「that」开头 | 除非紧跟名词 |
| 7 | 避免习语、口语、客套 | 「Please」「Sorry」「Thank you」尤其不要用在错误提示里；只有**请用户做麻烦事**时才用 please |
| 8 | 减少技术行话 | 描述简单、必要时字面化 |
| 9 | 消除歧义 | traffic / filter / change 既是名词又是动词；同一段里不要用两种词性；home 可能是首页也可能是家 |
| 10 | 提供 message description | 上下文、字数限制、歧义说明都写给译者 |

### RTL（从右到左）

本轮 dump 中**唯一**与 RTL 直接相关的原文，是 design tokens 对「context」的举例：

> Examples of different contexts include: device form factors, dark theme, dense layouts, and right-to-left writing systems.

⚠️ 推断，页面未明示：镜像规则（图标镜像、进度方向、内边距翻转、数字与拉丁字符保持 LTR 等）不在本轮 dump 内，本库不虚构条目；需要在实现时按 Android `LayoutDirection` 与组件文档处理，并补做一轮官网核验。

## 八、文本放大 200% 与截断

### 200% 是下限

> UIs should support a minimum text increase of 200%.

放大时**只有文字与行高按倍数缩放**：

| 属性 | 放大后的行为 |
|---|---|
| 文字大小 | 按缩放值成比例放大 |
| 行高 | 按缩放值成比例放大 |
| 内边距（padding） | **保持 1×**（例：按钮上下 8dp、左右 24dp 不变） |
| 元素间距 | **保持 1×** |
| 不含文字的组件（进度条、checkbox、radio） | **不缩放** |

计算公式（官网给的法子）：`默认字号（density = 0）× 缩放值`。例：14pt 在 2× 下是 28pt。

设备不接管缩放时，**在应用内提供 1.5× / 2× 倍率**；倍率会产生小数，但实现上更可行。

### 四类放大事故与四种解法

| 事故 | 解法 |
|---|---|
| 容器不响应，文字被裁 | 增大容器尺寸 |
| 文字不响应 | 重排布局：并排内容在空间不足时**改为堆叠** |
| 元素重叠 | 同上，并检查 padding 是否被硬编码 |
| 不想要的截断 | 允许换行、扩大容器、必要时提供滚动或 tooltip |

补充规则：

- 滚动**优先垂直**，不要让用户同时上下左右滚（「Users should only be asked to scroll in one direction」）。
- 空间受限的组件（top app bar、navigation bar、navigation rail、固定在顶部的 tabs）可用**长按 tooltip 显示放大后的文字**，组件内文字仍按 1× 显示。

### 截断的可访问性底线

> Information should always be available to readers, even if text is truncated or wrapped.

| 做法 | 规则 |
|---|---|
| 换行（wrapping） | 信息关键或组件有余量时必须换行；仍放不下则必须提供查看途径 |
| 扩大组件高/宽 | 用能随内容伸缩的容器，不要设放不下内容的字号上限 |
| 省略号 + tooltip / 链接 | **仅当被截断内容可通过 tooltip 或链接看到时才允许加省略号** |
| 只有省略号 | 「If there's an ellipsis, but no way to show the truncated text, it is not accessible.」 |

硬性要求：**用户修改字号后，内容、可理解性、功能都不能丢失**；非拉丁字母语言可能有例外，但需显式论证。

## 检查项

- [ ] 通篇用「你」，没有混用「我 / 你」
- [ ] 所有标题、按钮、标签都是 sentence case，没有全大写块
- [ ] 强调用字重，不用整句斜体/彩色文字
- [ ] 单句标签、tooltip、列表项末尾没有多余句号
- [ ] alt text ≤ 125 字符，不以「图片」开头，装饰图设空
- [ ] 通知标题 < 29 字符，正文 < 40 / 80 字符，写「周三」不写「明天」
- [ ] 所有字符串预留 1.5 倍译文膨胀空间
- [ ] 200% 字号下无裁切、无重叠，滚动只有一个方向
- [ ] 带省略号的地方都能点开或 hover 看到全文

## 来源

- https://m3.material.io/foundations/content-design/overview （核验 2026-11-16）
- https://m3.material.io/foundations/content-design/style-guide/ux-writing-best-practices （核验 2026-11-16）
- https://m3.material.io/foundations/content-design/style-guide/word-choice （核验 2026-11-16）
- https://m3.material.io/foundations/content-design/style-guide/grammar-and-punctuation （核验 2026-11-16）
- https://m3.material.io/foundations/content-design/global-writing/overview （核验 2026-11-16）
- https://m3.material.io/foundations/content-design/global-writing/word-choice （核验 2026-11-16）
- https://m3.material.io/foundations/content-design/alt-text （核验 2026-11-16）
- https://m3.material.io/foundations/content-design/notifications （核验 2026-11-16）
- https://m3.material.io/foundations/writing/best-practices （核验 2026-11-16）
- https://m3.material.io/foundations/writing/text-truncation （核验 2026-11-16）
- https://m3.material.io/foundations/writing/text-resizing （核验 2026-11-16）

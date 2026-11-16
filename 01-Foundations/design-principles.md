# 设计原则 / Design Principles

> 官方指南（可用性）：https://m3.material.io/foundations/usability/overview
> 官方指南（应用 Expressive）：https://m3.material.io/foundations/usability/applying-m3-expressive
> 官方指南（无障碍原则）：https://m3.material.io/foundations/overview/principles
> 官方指南（定制化）：https://m3.material.io/foundations/customization
> 官方术语：https://m3.material.io/foundations/glossary
> 核验日期：2026-11-16
> 核验方式：m3.material.io 全站 dump 逐页核对
> 状态：**可用性五维度、四条 key takeaways、六种设计战术、定制化边界均为官网原文**；「三原则」旧表述已按 dump 修正

## 一句话结论

官网对 Material 的自我定义：

> Material is an adaptable system of guidelines, components, and tools that support the best practices of user interface design.

关键词是 **adaptable（可适配）**：不是一套固定视觉，而是一套**随窗口、随用户、随品牌变化**的系统。判断任何设计决策，最终都回到一句官网原话：

> Usability focuses on making products intuitive and easy to understand for everyone.

## 一、先纠正一处旧表述

本库此前把 M3 归纳为「Personal / Adaptive / Expressive 三原则，优先级 Adaptive > Personal > Expressive」。⚠️ 说明：这三条**在本轮 dump 里找不到对应的官方原文**，属于二手归纳。

dump 里能追到的是两个更窄的说法：

| 说法 | 出处 | 原文 |
|---|---|---|
| adaptable system | glossary | 「Material is an adaptable system of guidelines, components, and tools…」 |
| Material You | glossary | 「The new Material visual style and set of features embracing the personal and expressive needs of individual users, part of Material Design 3」 |
| Adaptive design | glossary | 「A design approach in which the interface changes based on known user, device, or environmental conditions.」 |

所以本页改用**可追溯的主张**组织：可用性五维度 + 六种战术 + 定制化边界。

## 二、可用性：五个维度

官网引用 Nielsen Norman Group 的定义，用五个方面衡量可用性：

| 维度 | 官方定义 |
|---|---|
| Efficiency / 效率 | 「Users can efficiently complete tasks and goals」 |
| Errors / 错误 | 「Proper design reduces the likelihood of mistakes, and users can easily correct any errors that do occur」 |
| Learnability / 易学 | 「New users learn to use the product and complete tasks easily, even if it's the first time they're using it」 |
| Memorability / 易记 | 「When users come back to a product, they remember how to use it」 |
| Satisfaction / 满意度 | 「Users are satisfied with the designed experience」 |

### 可用性 ≠ 无障碍

| | 关注点 | 官方表述 |
|---|---|---|
| Accessibility | 残障用户能否使用 | 「Accessible experiences are perceivable, operable, understandable and robust, and support people who use assistive technology.」 |
| Usability | 所有人是否觉得直观易懂 | 「Usability focuses on making products intuitive and easy to understand for everyone.」 |

两者是**并列指标**，不是包含关系。一个对比度达标但主任务找不到的界面，无障碍合格、可用性不合格。详见 [accessibility.md](accessibility.md)。

## 三、四条 key takeaways

官网把整章压缩成四句，可直接当评审清单：

> - Emphasize key actions to create effective visual hierarchy
> - Leverage expressive design tactics to improve usability
> - Don't overwhelm the user with too much visual information
> - Test and iterate to validate designs

注意第二条的措辞：**expressive 是为 usability 服务的工具**，不是并列目标。

## 四、六种设计战术

| 战术 | 官方规则 | 边界 |
|---|---|---|
| Color & color contrast | 用醒目的主/次色建立层级、强调关键动作；**用对比色（如紫与绿）而不是相近色** | 「Colors should always follow basic accessibility guidelines.」 |
| Containment & grouping | 用轻量容器把相关元素归组，用 containment + spacing + heading 切分内容 | 容器要「subtle」，不要层层套卡片 |
| Motion | 动效强调关键瞬间 | 「use it sparingly since motion can be distracting」 |
| Shape & shape morph | 形状库共 **35 个形状**；任意形状可 morph 成另一形状 | morph 用于传达状态（selected、tap、swipe、scroll、release、long press）与进行中动作 |
| Size | **最重要的动作 / 主 CTA 应该是屏幕上最大的元素** | 大尺寸会「dramatically increases usability」，但不要所有元素都放大 |
| Typography | 用字阶区分信息层级：最大最易读的文本可指向主行动，小字指向次级 | 同类内容用同一字体样式 |

形状的五个作用（原文）：增加强调与愉悦感、引导焦点、区分容器/按钮/动画、传达交互、设定情绪基调。

尺寸的官方收益写得很具体：**用户更满意、出错更少、觉得更好学**。

## 五、基于主目标设计

> Primary goals are the main tasks to use a product successfully… so they need the strongest emphasis.

| 层级 | 定义 | 例子 |
|---|---|---|
| Primary | 用这个产品要成功完成的主任务 | 启动流程、做决策 |
| Secondary / Tertiary | 增强体验但非必需 | 查看状态、进入设置 |

五条落地做法（官网列举）：

1. 用尺寸、颜色等建立强视觉层级；
2. **每页只留一个主任务**，用留白聚焦注意力；
3. 核心动作要易识别、易触达（高频动作用大按钮）；
4. 整体和谐、好看且易懂；
5. **不要同时使用太多 expressive 战术**。

## 六、Expressive 的主张与边界

官网对 M3 Expressive 的定位只有一句，但信息量很大：

> By leveraging M3 Expressive design tactics like containment, size, shape, color, and typography, designers can guide users through experiences and emphasize key actions to create intuitive, usable products.

拆开看是三层主张：

| 主张 | 含义 |
|---|---|
| Expressive 是**引导手段** | 用来带用户走完流程、突出关键动作，而不是装饰 |
| Expressive 是**组合战术** | containment / size / shape / color / typography 一起用才成立 |
| Expressive 有**用量上限** | 「Not using too many expressive tactics at the same time as they can be distracting」 |

### 什么时候别用

官网给出的反面约束：

- 「Don't overwhelm the user with too much visual information.」
- 动效「use it sparingly」。
- 同时堆多个 expressive 战术会分散注意力。

⚠️ 推断，页面未明示：官网没有给出「Expressive 使用决策树」。以下三问是本库为 Android 落地补的工程判据，不是官方规则：

1. 当前 `material3` 版本是否已把该 API 从实验状态转正？（版本事实需查 release notes）
2. 这个动效/形状变化是否帮助用户理解「状态从哪来、到哪去」？
3. 关掉动效后，信息与操作是否仍然完整？

任一为「否」，就不要上 expressive 处理。**任务清晰度优先于表现力。**

### 验证方式

> Test, iterate, and gather feedback early and often from a range of users and contexts to validate design choices and minimize errors.

Expressive 不是设计稿上判断的，是**测出来的**。

## 七、无障碍也是设计原则

官网把无障碍写成三条**设计原则**，而不是检查项（详见 [accessibility.md](accessibility.md)）：

| 原则 | 一句话 |
|---|---|
| Honor individuals | 通用默认体验满足不了所有人，默认里要留可定制空间 |
| Learn before, not after | 方案之前先研究多样能力用户的需求 |
| Requirements as a starting point | WCAG 是起点；深色模式、TTS、STT 都诞生于具体访问需求 |

> Anticipating a wide range of human experiences and disabilities prevents costly redesigns, reduces tech and design debt, and conserves resources upfront.

## 八、定制化的边界

官网定义：**「Customization creates unique branded products with familiar patterns and accessible interactions.」**

术语表定义：定制是「a modification made to a UI reflecting an app, OEM, or individual user's visual preferences and brand」，可以**只改单个元素**，也可以**全局成主题**。

### 可以定制

| 项 | 说明 |
|---|---|
| 品牌色 / 自定义配色方案 | 输入 1 个或多个源色，系统生成 tonal palette 与全部角色 |
| 字体、形状、高度等系统性参数 | 「M3 supports systematic applications of custom parameters」 |
| 是否启用动态取色 | **「Dynamic color is both a user setting and a developer choice.」** 可选择性启用，例如只在个人资料页跟随用户配色 |
| 单组件样式 | 改某个组件的颜色、增加可选动作 |

### 不可越过

| 约束 | 原文 |
|---|---|
| 必须有自己的主题，动态色才有落点 | 「you'll implement a custom theme that user-generated color schemes can map to」 |
| 必须准备回退方案 | 「using a custom theme ensures that your app has a fallback color scheme available for users who don't enable dynamic color」 |
| 对比度、可读性、状态、组件结构由系统负责 | 「The color system automatically handles critical adjustments that provide accessible color contrast, legibility, interaction states, and component structure.」 |

### 五组必备颜色角色

定制配色时角色仍按五组映射：**Primary、Secondary、Tertiary、Neutral、Neutral Variant**。一个输入色会生成 tonal palette，再填充 primary / on-primary / primary container 等角色。

⚠️ 注意口径差异：本库 [color-system.md](color-system.md) 记录的是**成品界面的 26 个角色 / 六组**（primary、secondary、tertiary、error、surface、outline）；customization 页讲的是**生成配色时的五组源色**。两者不冲突，但不要混用。

### 定制与令牌

定制不是「改十六进制值」，而是**改令牌指向**。参考层存全部可选值，系统层决定「这个角色在此主题下指向谁」，组件层再引用系统层。详见 [design-tokens.md](design-tokens.md)。

## 九、从任务到组件的推导顺序

⚠️ 本库实践约定，非官网原文。固定按这个顺序走，不要从「我想用个 FAB」开始：

1. 用户此刻要完成的一件事（primary action）
2. 这件事在界面上的层级（主 / 次 / 三）
3. 承载它的容器与导航结构
4. 用哪个官方组件
5. 颜色 / 字体 / 形状 / 动效的语义角色
6. 窗口变化时如何重排
7. 无障碍与文本缩放下是否仍成立

跳过 1–3 直接选组件，是绝大多数「做得挺好看但用起来别扭」的界面成因。

## 一致性边界

- **必须一致**：语义色角色、字阶、形状层级、间距节奏、导航模式、返回行为、状态表达方式。
- **可以变化**：品牌色相、具体 radius 数值、字体族、动效风格。
- **禁止**：同一含义在不同屏幕用不同角色。例如「危险」在一个页面用 `error`，在另一个页面用红字配 `surface`。

## 反模式

- 把「好看」当需求，先定视觉再补功能。
- 同屏堆三个以上 expressive 战术，主任务反而不突出。
- 用单张设计稿交付，不写交互状态、空态、错误态。
- 用自定义组件替代官方组件，却说不出官方组件哪里不适用。
- 用设备型号（手机 / 平板）判断布局，而不是窗口宽度。
- 只在浅色主题下验证。
- 把动态取色当成「开了就完事」，不留回退配色。

## 本章检查项

- [ ] 能一句话说清每个屏幕的主任务，且它是屏幕上最大的元素
- [ ] 主任务在视觉层级上确实最突出，每页只有一个主任务
- [ ] 语义色、字阶、形状、间距四套系统没有互相打架
- [ ] 同屏 expressive 战术不超过两种，且每个都能说出「帮用户理解了什么」
- [ ] 动效关掉后信息与操作仍完整
- [ ] 定制化只改令牌指向，保留回退配色与对比度
- [ ] 浅色 / 深色 / 200% 字体缩放三种状态都看过
- [ ] 用真实用户测过一轮，而不是只做设计评审

## 来源

- https://m3.material.io/foundations/usability/overview （核验 2026-11-16）
- https://m3.material.io/foundations/usability/applying-m3-expressive （核验 2026-11-16）
- https://m3.material.io/foundations/overview/principles （核验 2026-11-16）
- https://m3.material.io/foundations/customization （核验 2026-11-16）
- https://m3.material.io/foundations/glossary （核验 2026-11-16）
- https://m3.material.io/foundations/design-tokens/overview （核验 2026-11-16）

# 设计令牌 / Design Tokens

> 官方指南（总览）：https://m3.material.io/foundations/design-tokens/overview
> 官方指南（使用）：https://m3.material.io/foundations/design-tokens/how-to-use-tokens
> 官方术语：https://m3.material.io/foundations/glossary
> 核验日期：2026-11-16
> 核验方式：m3.material.io 全站 dump 逐页核对
> 状态：**三层分类、命名结构、上下文机制、官方示例令牌均为原文**；Compose 落法为本库约定，已标注

## 一句话结论

官网原文：**「Design tokens are the building blocks of all UI elements. The same tokens are used in designs, tools, and code.」**

以及最实用的一条：

> Use design tokens instead of hardcoded values.

令牌不是「变量改名」，而是**把设计决策变成可追溯、可替换的一层引用**：值可以变，名字和用途不变。

## 一、令牌是什么

一个令牌由**两部分**组成：

| 组成 | 示例 |
|---|---|
| 代码式名称 | `md.ref.palette.secondary90` |
| 关联值 | `#E8DEF8` |

> The token's value can be one of several things: A color, typeface, measurement, or even another token.

关键性质：**「Even if a token's end value is changed, its name and use remain the same.」**

### 为什么重要

| 收益 | 官方表述 |
|---|---|
| 单一事实来源 | 「a single source of truth – a repository where style choices are recorded and changes can be tracked」 |
| 全局可推 | 「style updates propagate consistently through an entire product or suite of products」 |
| 设计-工程同语言 | 「They also help designers and engineers "speak the same language"」 |
| 支持主题与情境切换 | 高对比配色、TV 字阶、深色主题都能系统性替换 |

### 什么时候值得上

| 适合 | 不适合 |
|---|---|
| 要改版或从零建产品 | 已有硬编码、且一两年内不会大改的应用 |
| 设计系统跨多个产品/平台 | 产品本身没有设计系统 |
| 希望以后容易维护样式 | — |
| 想用上动态取色等特性 | — |

## 二、三层令牌

> There are three classes of tokens in Material: Reference tokens / System tokens / Component tokens

| 层 | 英文 | 前缀 | 定义（原文） | 存什么 |
|---|---|---|---|---|
| 参考层 | Reference | `md.ref.*` | 「All available tokens with associated values.」 | 全部可选值：调色板色阶、字号、字体 |
| 系统层 | System | `md.sys.*` | 「Decisions and roles that give the design system its character, from color and typography, to elevation and shape.」 | 语义角色：primary、on-surface、body-large… |
| 组件层 | Component | `md.comp.*` | 「The design properties assigned to elements in a component, such as the color of a button icon.」 | 某个组件某元素的属性：FAB 容器色、按钮标签色 |

一句总结分工：**参考层是「有哪些值」，系统层是「这个值在这里扮演什么角色」，组件层是「这个组件的这个部位用哪个角色」。**

### 参考层（ref）

- 通常指向静态值（hex、字号），**也可以指向另一个参考令牌**。
- **「Reference tokens don't change based on context.」**——这是它最重要的性质。
- 作用：给团队一份「已批准的选项清单」。

### 系统层（sys）

- **主题化发生在这里**：「This is where theming occurs.」
- 同一个系统令牌在不同上下文（浅色/深色）可以指向不同参考令牌。
- 硬规则：**「Whenever possible, system tokens should point to reference tokens rather than static values.」**

### 组件层（comp）

- 描述构成组件所需的元素：容器、标签文字、图标、状态，以及它们的尺寸、形状、颜色、高度。
- 硬规则：**「Whenever possible, component tokens should point to a system or reference token, and not contain hardcoded values such as hex codes.」**
- 覆盖边界：**「Not every stylistic choice of a component will be able to be expressed as a token, but whenever a design choice applies to multiple components of similar intent, a token should be used.」**

## 三、命名规则

> The parts of a token name are separated by periods and proceed from the most general information ("md") to the most specific ("on-secondary").

```
md . sys . color . on-secondary
│    │     │       └── 用途 / 角色
│    │     └────────── 类别（color / typescale / shape / elevation / state …）
│    └──────────────── 令牌层（ref / sys / comp）
└───────────────────── 设计系统名（md = Material Design）
```

| 段 | 含义 | 取值 |
|---|---|---|
| 第 1 段 | 设计系统标识 | `md`（Material Design） |
| 第 2 段 | 令牌层 | `ref` / `sys` / `comp` |
| 后续段 | 由粗到细的描述 | 类别 → 角色 → 元素 → 属性 |

### 官方示例对照

| 层 | 示例 | 指向 |
|---|---|---|
| ref | `md.ref.palette.secondary90` | `#E8DEF8` |
| ref | `md.ref.palette.secondary200` | 一个色阶值 |
| sys | `md.sys.color.secondary-container` | 某个 ref 调色板色阶 |
| comp | `md.comp.fab.primary.container.color` | 「sets the container color for a FAB」 |

组件层命名会把**变体**写进去（如 `fab.primary`），因此同一个组件不同变体是不同令牌，不是同一令牌换值。

## 四、ref 与 sys 的使用边界

| 场景 | 该用哪层 | 理由 |
|---|---|---|
| 给组件定颜色、字体、形状 | **sys** | 角色即语义，换主题自动跟随 |
| 品牌调色板、字号表等原始选项 | **ref** | 只在这里出现具体值 |
| 组件内部元素的属性 | **comp** | 组件 spec 的表述方式 |
| 业务代码里直接写 `#6750A4` | **都不要** | 硬编码，主题切换即失效 |
| 业务代码里直接引用 `md.ref.palette.*` | **不要**（⚠️ 推断，页面未明示禁止，但与「ref 不随上下文变化」冲突） | 绕过系统层 = 放弃深色主题与动态取色 |
| 想「这个屏幕特殊一点」 | **sys 的另一个角色**，不要新造 ref | 「同一含义在不同屏幕用不同角色」是本库明令禁止项 |

判断口诀：**ref 是备选库，sys 是决策，comp 是装配。** 出现「这个颜色只在深色下不一样」的需求，说明它必须是 sys，不能是 ref。

## 五、上下文（contexts）

> Tokens can point to different values depending on a set of conditions. These conditions are called contexts and their resulting values are called contextual values.

官方列出的上下文类型：

- 设备形态（device form factors）
- 深色主题（dark theme）
- 紧凑布局（dense layouts）
- **从右到左书写系统（right-to-left writing systems）**

机制比喻：**context 是一个标签**。某个值被打上 `dark theme` 标签，就在深色上下文里覆盖默认值。

术语表补充：context 是「The set of conditions in which tokens can point to non-default values (for example, dark theme, dense layout).」

⚠️ 推断，页面未明示：RTL 被列为 context，说明布局方向在 M3 里是**令牌级**问题而非散落的 `if` 判断；但本轮 dump 没有给出 RTL 具体令牌名，实现时需查组件 specs。

## 六、怎么用

### 1. 下载基线令牌

Material 基线主题包含令牌与默认值，以 **Design System Package（DSP）** 形式分发，可导入设计工具与产品代码。

### 2. 在 Figma 里生成 / 更新

| 步骤 | 操作 |
|---|---|
| 安装 | 从社区页安装 Material Theme Builder 插件 |
| 生成 | Plugins > Material Theme Builder > Open Plugin > Get started；画板会生成深浅两套 tonal palette 与默认字阶 |
| 更新 | 在插件里改颜色（仅颜色），Figma 样式随之更新 |
| 微调 | 右键样式 → Go to style definition → Edit style，改名称/描述/属性 |
| 套用到设计稿 | 用 Figma 样式而不是手动填色 |
| 套用到 Design Kit | 选中组件 → swap，把基线令牌样式换成自己的 |
| 导出 | Export 标签页，选 Android / Jetpack Compose，导出 zip |

### 3. 读组件页的令牌模块

组件文章里的 specs 标签页这样组织：

| 组织方式 | 说明 |
|---|---|
| 先按状态分组 | enabled、disabled、hover… |
| 再按元素分组 | container、label text… |
| 列 | Name（属性，如 color / font）、Token ID、Description、Context/value |

用法定式：**先找组件 → 找变体 → 找状态 → 找元素 → 拿 Token ID。**

## 七、落到 Compose

⚠️ 以下为本库工程约定，dump 中**没有** Compose API 说明：

| 层 | Compose 对应 |
|---|---|
| sys（颜色） | `MaterialTheme.colorScheme.primary` 等 26 个角色 |
| sys（字阶） | `MaterialTheme.typography.bodyLarge` 等 |
| sys（形状） | `MaterialTheme.shapes.medium` 等 |
| comp / ref | 一般不直接用；需要时经 Theme 映射回来 |

原则：**应用代码只碰 `MaterialTheme.*`，不碰 `md.ref.*`，不碰十六进制值。** 换主题、开动态取色时，只换 `colorScheme` 的构造，不动任何组件代码。

## 八、检查项

- [ ] 代码里搜不到裸十六进制颜色（除 Theme 定义处）
- [ ] 没有跨层引用：业务代码不直接引用 `md.ref.*`
- [ ] 深色主题下没有「手工判断 if dark」的颜色分支
- [ ] 同一语义在不同屏幕用的是同一个 sys 角色
- [ ] 新增的可复用视觉决策，已抽成令牌而不是复制粘贴
- [ ] 组件改动优先改 comp → sys 的指向，而不是覆盖值
- [ ] 设计稿用 Figma 样式，没有手动填色

## 来源

- https://m3.material.io/foundations/design-tokens/overview （核验 2026-11-16）
- https://m3.material.io/foundations/design-tokens/how-to-use-tokens （核验 2026-11-16）
- https://m3.material.io/foundations/glossary （核验 2026-11-16）
- https://m3.material.io/foundations/customization （核验 2026-11-16，主题与回退配色）

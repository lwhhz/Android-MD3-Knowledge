# 图标系统 / Icons

> 官方指南：https://m3.material.io/styles/icons/overview
> 设计规范：https://m3.material.io/styles/icons/designing-icons
> 应用规则：https://m3.material.io/styles/icons/applying-icons
> 图标库：https://fonts.google.com/icons
> 核验日期：2026-11-16
> 核验方式：浏览器渲染官网正文后提取（m3.material.io 全站 dump）
> 状态：**四种轴、四个尺寸、24dp 网格的 keyline 与度量均为官方原文**

## 一句话结论

官网原文：**「Material Symbols have four adjustable stylistic variable font attributes called axes.」** 四轴是 **weight / fill / grade / optical size**。

Material Symbols 是默认图标集，三种风格：**outlined、rounded、sharp**。旧 Material Icons 仍可用，但没有可变字体能力。

## 三种风格：先选风格，再调轴

| 风格 | 形态 | 官方适用描述 |
|---|---|---|
| **Outlined** | 描边 + 填充并用，轻、干净 | 适合**密集 UI**；描边粗细可调，用来呼应或对比字体粗细 |
| **Rounded** | 圆角 | 配**较重字体**、曲线 logo、圆形元素的品牌 |
| **Sharp** | 直角、方角 | 清晰、小尺寸仍可读；配矩形感强、圆角无法表达的品牌 |

官网对 sharp 的定位值得注意：「**for a crisp style that remains legible even at smaller scales**」——小尺寸优先考虑 sharp，不是 outlined。

**有些符号必须保持填充**：「**For optimal legibility and recognition, some symbols should remain filled, such as full body human icons or proprietary icons**」。人体剪影类、专有图标不要用描边版。

## 四个轴

| 轴 | 范围 | 作用 | 关键规则 |
|---|---|---|---|
| **Weight** | 100（thin）– 700（bold） | 笔画粗细，也会影响符号整体尺寸 | 24dp 图标**最小 200**，不要用最细；不要混用不同 weight |
| **Fill** | 0 – 1 | 从描边过渡到填充，1 = 完全填充 | 用来表达**状态过渡**（未选中 → 选中） |
| **Grade** | 负值 / 0 / 正值 | 更细粒度地改厚薄，对尺寸影响小于 weight | 浅底深图标默认 **0**；深底浅图标默认 **-25**；正值用于激活态强调 |
| **Optical size** | 20dp – 48dp | 随尺寸自动调整笔画粗细 | 不要直接缩放 24dp 矢量源 |

### Weight

- 推荐笔画是 **2dp**，对应 **regular 400**。
- 「**Don't use the lightest weight for standard-size (24dp) icons. The minimum weight for this size should be 200.**」
- 24dp 上用特别大的 weight 也要谨慎（官网标为 Caution）。
- 同一套界面里 weight 必须一致。

### Fill

「**A fill attribute can be used to convey a state of transition, such as unfilled and filled states. Values range from 0 to 1, with 1 being completely filled.**」

官网示例是底部导航：选中与未选中用填充/未填充区分。**具体哪个状态用 fill=1 属于组件规范**（页面只给了底部导航这一例），不要当成全域规则。

### Grade

Grade 和 weight 都改厚薄，区别在粒度与副作用：

> 「**Adjustments to grade are more granular than adjustments to weight and have a smaller impact on the size of the symbol.**」

两个实用点：

1. **和字体对齐**：文字字体有 -25 grade 时，图标也给 -25，视觉厚度才一致。
2. **补偿视觉溢出（visual bleed）**：同一图标在浅底上显得大、深底上显得小。官方默认值就是为此设的——浅底深图标 **grade 0**，深底浅图标 **grade -25**。

正值 grade 用于让笔画更重、更强调，官网举例是**激活态图标**。

### Optical size

「**Optical sizes range from 20dp to 48dp.**」实际四档：**20 / 24 / 40 / 48dp**。

解决的问题：把小尺寸矢量直接放大，笔画会显得过粗。optical size 轴会随尺寸重新配笔画。

> 「**Traditionally, icons are resized from a 24dp source vector, resulting in a large scaled icon that's too heavy compared to the original. With the optical size axis, you can maintain the stroke weight (thickness) as the icon size grows.**」

用法：

- **20dp**：桌面端密集布局、小尺度视觉。
- **24dp**：标准档。
- **40 / 48dp**：display / headline 级文字旁，或大屏；需要突出主操作时用。

## 尺寸档

| 尺寸 | 定位（原文） |
|---|---|
| **24dp** | Standard（Baseline），默认 |
| 20dp | 「primarily for desktop, dense layouts, and small scale visuals」 |
| 40dp | 「optimized for display or headline type, plus larger screen sizes」 |
| 48dp | 同上 |

官网没有给「12dp / 16dp 图标」这类更小的官方档。比 20dp 更小的用法不在支持范围内。

## 24dp 网格与安全区

一个 24dp 图标内部是三层：

| 区域 | 尺寸 | 规则 |
|---|---|---|
| **Live area**（活区） | **20dp × 20dp** | 图标内容应留在这里 |
| **Padding** | 四周各 **2dp** | 需要额外视觉重量时可侵入 |
| **Trim area**（裁切区） | 24dp × 24dp | **任何部分都不得超出** |

原文：

> 「**Icon content should remain inside of the live area**」
> 「**If additional visual weight is needed, content may extend into the padding between the live area and the trim area (the complete size of a graphic). No parts of the icon should extend outside of the trim area.**」

所以 2dp padding 是「可借用的余量」，不是硬边界；24dp 外框才是硬边界。

## Keyline 形状（自绘图标用）

24dp 网格上的四个基准形，用来保持不同形状图标的视觉体量一致：

| Keyline | 尺寸 |
|---|---|
| 正方形 | **18dp × 18dp** |
| 圆形 | 直径 **20dp** |
| 竖矩形 | 高 **20dp** × 宽 **16dp** |
| 横矩形 | 高 **16dp** × 宽 **20dp** |

官方定义：「**Keyline shapes are the foundation of the grid. By using these core shapes as guidelines, you can maintain consistent visual proportions across system icons.**」

两条落地要求：

- **落在像素上**：「**Position icons "on pixel" within the icon grid**」，不要放在半像素坐标。
- 自绘模板：官方提供 **24dp keyline 模板（ZIP，Apache 2.0）**，Illustrator 用。

## 度量：角与笔画

### 圆角

| 风格 | 外角 | 内角 |
|---|---|---|
| 默认 | **2dp** | 内角为**方角**，不圆 |
| Outlined | 2dp | 方角 |
| Rounded | 圆 | 圆 |
| Sharp | **0dp** | **0dp**（从 2dp 减到 0） |

- 宽度 ≤ 2dp 的笔画，端点转角**不要**做圆。
- 过度圆角会降低辨识度（官方 Caution）。
- 同一图标内不要混用不同半径。

### 笔画

- 推荐 **2dp / weight 400**，涵盖曲线、斜角、内外描边。
- 笔画端点用**方头**（squared stroke terminals），不要圆头。
- 同一图标内笔画粗细要一致。

### 光学修正（optical corrections）

复杂图形允许微调，但必须仍用同一套几何形：

- 回形针：在 24dp 内塞多条曲线，用 **1.5dp**（而非 2dp）笔画。
- 拉面碗：同一图标内 **1.5dp 与 2dp 混用**。

原则：「**Any optical correction should use the geometric forms on which all other icons are based, without skewing or distorting those shapes.**」——可以改粗细，不能拉歪形体。

## 设计原则

官网定调：「**They're designed to be simple, modern, friendly, and sometimes quirky.**」但小尺寸下必须严格守规范。

| Do | Don't |
|---|---|
| 简化，提升清晰度与可读性 | 过于字面、复杂 |
| 图形化、粗壮 | 纤细、松散的有机形 |
| 一套图标内保持统一风格 | 混风格 |
| 图标正面朝前 | 倾斜、旋转、做出立体感 |

「正面朝前」原文：「**Make icons face forward**」/「**Don't tilt, rotate, or make icons appear dimensional**」。

## 和文字一起用

| 规则 | 说明 |
|---|---|
| 同尺寸 | 图标与文字用**相同尺寸** |
| 同光学重量 | 图标与文字的视觉厚度要匹配 |
| 基线错位 | 图标基线**下移约文字大小的 11.5%** |

第三条原文：「**Shift down the baseline of symbols to approximately 11.5% of the text size**」。用同一基线会让图标看起来偏高——这是排版级细节，不是可选项。

## 应用：outlined 还是 filled

官网没有给「所有场景 → 风格」的对照表，能确认的是这几条：

1. **图标集默认用 Material Symbols**（官网称其为 new default；旧 Material Icons 仍可用，但没有可变字体）。三种风格里 outlined 最适合密集 UI；「哪种风格是默认」页面未明示。
2. **描边粗细跟着字体走**：字体细就调细，字体重就调重，目的是「complement or contrast the weight of your typography」。
3. **该填充的别描边**：人体剪影、专有图标保持 filled。
4. **用 fill 表达状态**：未选中/选中这类状态切换，用 fill 0→1，而不是换一个图标。
5. **风格跟品牌形态一致**：圆角品牌配 rounded，矩形品牌配 sharp。

⚠️ 推断，页面未明示：「导航选中项一律用 filled」是组件层惯例（底部导航示例支持，但页面未写成全局规则）。

## 无障碍

### 标签

> 「**Label text provides short, meaningful descriptions when symbols are more abstract.**」

- 图标含义不唯一时必须配文字标签。
- 没有标签的图标要极其谨慎：「**Icon meaning should always be unambiguous and accessible for all users.**」
- **导航项必须有标签**（本地化章节再次强调）。

### 小图标

- 简单符号（如评分星星）可单独使用，任意尺寸，只要仍可识别。
- **小于 20dp 时**，以下两类必须配文字标签：
  - 复杂图标（细节多、由多个部分组成）；
  - 承担关键动作的图标（产品核心操作）。

### 触控目标

| 图标尺寸 | 默认目标尺寸 |
|---|---|
| 24dp | **48dp** |
| 20dp（鼠标 + 键盘为主的密集布局） | **40dp** |

原文：「**Symbols of 24dp should have a target size of 48dp by default.**」「**A 20dp size symbol can use a target size of 40dp.**」

后一条有前提：**mouse and keyboard are the primary input methods**。触屏场景不要用 40dp 目标。

## 本地化

- 抽象图标加标签；导航项必须带标签。
- 考虑技术素养差异：重度上网用户与轻度用户对同一图标的理解不同。
- **翻译图标本身**：结算入口在不同地区可能是 cart / bag / basket。
- **颜色有文化含义**：白色在西方常表纯洁，在部分东方文化表哀悼。
- **符号有文化差异**：猫头鹰在西方表智慧，在部分东方文化是负面征兆。
- 警告色也不通用：有些地区用红，有些用绿。

## 常见错误

- 24dp 图标用 weight 100，笔画糊成一片。
- 同一屏混用 300 与 500 两种 weight。
- 直接放大 24dp 矢量当 48dp 用，笔画过重。
- 深色主题下不调 grade，图标显得偏细/偏小。
- 用 fill 表达「重要」，而不是表达「状态」。
- 人体剪影类图标用 outlined，辨识度下降。
- 圆角品牌配 sharp 图标，风格打架。
- 图标与文字不同尺寸、不同视觉重量、共用同一基线。
- 24dp 图标触控区只做 24dp。
- 抽象图标裸用无标签；导航项无标签。
- 在 20dp 以下用复杂图标且不加文字。

## 检查项

- [ ] 使用 Material Symbols（而非旧 Material Icons），并统一一种风格
- [ ] weight 在 24dp 上 ≥ 200，全屏一致
- [ ] 深色主题下 grade 为 -25（浅底深图标为 0）
- [ ] 图标尺寸取自 20 / 24 / 40 / 48，且设了对应 optical size
- [ ] 自绘图标内容在 20dp 活区内，未越出 24dp 裁切区
- [ ] 圆角 2dp、内角方角（outlined）；笔画 2dp、方头端点
- [ ] 图标与文字同尺寸、同光学重量，基线约下移 11.5%
- [ ] 24dp 图标触控目标 48dp；桌面 20dp 图标 40dp
- [ ] 抽象图标与导航项均有文字标签
- [ ] 小尺寸（<20dp）的复杂图标与关键动作图标都配了标签

## 来源

- 图标总览（三风格、四轴、资源）：https://m3.material.io/styles/icons/overview
- 设计图标（尺寸、活区、keyline、度量）：https://m3.material.io/styles/icons/designing-icons
- 应用图标（轴用法、排版配合、无障碍、本地化）：https://m3.material.io/styles/icons/applying-icons
- Material Symbols 图标库：https://fonts.google.com/icons

核验日期：2026-11-16

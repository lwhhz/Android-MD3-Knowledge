# 色彩系统 / Color System

> 官方指南（角色）：https://m3.material.io/styles/color/roles
> 官方指南（机制）：https://m3.material.io/styles/color/system/how-the-system-works
> 官方指南（方案）：https://m3.material.io/styles/color/choosing-a-scheme
> Compose：https://developer.android.com/develop/ui/compose/designsystems/material3
> 核验日期：2026-11-16
> 核验方式：浏览器渲染官网正文后提取（m3.material.io 全站 dump）
> 状态：**26 个标准角色与六组分组已按原文核对**；机制、方案、advanced、对比度为本轮新增；具体色值不在本库固化

## 一句话结论

官网原文：**「There are 26 standard color roles organized into six groups: primary, secondary, tertiary, error, surface, and outline.」**

26 个标准角色 + 一组**可选的 add-on 角色**。add-on 不是「高级用法」，是「大多数产品不需要」——官网明确说：不确定要不要用，就是不需要。

官网有「26」与「26+」两种写法：**26 是标准角色数**，「26+」出现在 overview 与 advanced 页，指加上 add-on / 自定义角色之后的规模。

## 先分清四个词

| 词 | 含义 | 常见误用 |
|---|---|---|
| Surface | 背景、大面积低强调区域 | 当成卡片专用色 |
| Container | **前景元素的填充色**（按钮底、Chip 底） | **拿它当文字色**——官方明确禁止 |
| On | 画在配对颜色之上的文字 / 图标色 | 自己拿白或黑硬顶 |
| Variant | 同名角色的低强调版本 | 用 `outlineVariant` 定义可点击边界 |

## 26 个标准角色

### 强调三组（各 4 个，共 12）

| 角色 | 用途（官网表述） |
|---|---|
| `primary` | 最重要的组件：FAB、高强调按钮、激活态 |
| `onPrimary` | primary 上的文字与图标 |
| `primaryContainer` | 相对 surface 的醒目填充，用于关键组件如 FAB |
| `onPrimaryContainer` | primaryContainer 上的文字与图标 |
| `secondary` | 不那么突出的组件，如 Filter Chip、导航图标选中态、次要按钮 |
| `onSecondary` | secondary 上的文字与图标 |
| `secondaryContainer` | 相对 surface 的低强调填充，用于 Tonal 按钮这类退后的组件 |
| `onSecondaryContainer` | secondaryContainer 上的文字与图标 |
| `tertiary` | 与 primary/secondary 形成对比的强调色，用于 badge、通知等小元素 |
| `onTertiary` | tertiary 上的文字与图标 |
| `tertiaryContainer` | 相对 surface 的互补容器色，用于**输入框**这类组件 |
| `onTertiaryContainer` | tertiaryContainer 上的文字与图标 |

**本轮修正**：原文档把「输入框」记在 `tertiary` 上。官网分层是——tertiary 用于「needs special emphasis but don't require immediate attention」的小元素（badge、通知），**输入框用的是 `tertiaryContainer`**。

强调层级（原文）：primary 是最重要的操作与元素；secondary 不需要立即注意、也不需要强调；tertiary 需要特别强调但不需要立即注意，且作用在较小元素上。

### Error（4 个）

`error` / `onError` / `errorContainer` / `onErrorContainer`

**关键特性**：error 是**静态色**——在任意 dynamic color scheme 中都默认保持不变（但仍随浅色/深色主题适配）。所以「错误必须显眼」不依赖品牌色。

> 「**Error is an example of a static color (it doesn't change even in dynamic color schemes). Error color roles are made static by default with any dynamic color scheme. They still adapt to light and dark theme.**」

### Surface（3 + 5 个容器 = 8）

| 角色 | 用途 |
|---|---|
| `surface` | 背景默认色 |
| `onSurface` | 任意 surface / surfaceContainer 上的文字与图标 |
| `onSurfaceVariant` | 同上，低强调 |
| `surfaceContainerLowest` | 最低强调容器 |
| `surfaceContainerLow` | 低强调容器 |
| `surfaceContainer` | **默认**容器色 |
| `surfaceContainerHigh` | 高强调容器 |
| `surfaceContainerHighest` | 最高强调容器 |

官网的计数是「**three surface roles**」+「**five surface container roles**」，合起来 8 个才凑成 26。

五个容器色用于**在展开尺寸的屏幕上建立层级与嵌套容器**。

**跨断点映射不能变**：正文区用 `surface`、导航区用 `surfaceContainer`，手机与平板一致。窗口变大可以增加容器色层次，但这两个映射要守住。

### Outline（2 个）

`outline` / `outlineVariant`

## Outline 的四条禁令（官网 Don't 原文）

1. **不要用 `outline` 做分割线** → 用 `outlineVariant`（两者对比度要求不同）。
2. **不要用 `outline` 做卡片这类包含多个元素的组件的边框** → 用 `outlineVariant`。
3. **不要用 `outlineVariant` 建立视觉层级或定义可点击目标的边界** → 用 `outline` 或其它与 surface 达 3:1 的颜色。
4. **例外（Caution，不是 Do）**：Chip、Button 的边框可用 `outlineVariant`，前提是内部图标与文字本身已达 4.5:1。

## Add-on 角色（默认不用）

### Fixed 强调色（12 个）

`primaryFixed` / `primaryFixedDim` / `onPrimaryFixed` / `onPrimaryFixedVariant`，secondary、tertiary 同构。Fixed 在浅色与深色主题下**保持同一色调**（container 会翻转）；FixedDim 提供更强、更强调的色调；OnFixed / OnFixedVariant 是画在其上的文字图标，后者低强调。

**风险**：Fixed 不随主题变，**容易造成对比度问题**。需要对比度时仍用 primary/secondary/tertiary。

### 表面 add-on（2 个）

`surfaceDim` / `surfaceBright`——与默认 surface 的反转方式不同：**保持相对明度**。默认 surface 在浅色主题最亮、深色主题最暗；surfaceBright 在两种主题下都最亮。典型用法：导航栏用 surfaceDim 作背景，聊天窗口用 surfaceBright 作背景。

### Inverse（3 个）

`inverseSurface` / `inverseOnSurface` / `inversePrimary`

**本轮修正**：官网把 Inverse 放在 **Surface 章节**里讲，不属于「Add-on color roles」小节，也不计入 26。

用于需要与周围界面**反色对比**的组件。典型：Snackbar 的背景、文字、文字按钮分别用这三个。

## 两个功能色（不属于角色分组）

`scrim`（模态遮罩）、`shadow`（阴影色）——Compose 中有，但不在 26 个角色与 add-on 清单里。

## 色彩系统怎么运作

官网把整个过程比喻成「数字油画」：每个 UI 元素分到一个「编号」（角色），每个编号分到一个颜色。静态方案是手挑，动态方案由**单一源色**自动生成整套可访问配色。

| 步骤 | 做了什么 |
|---|---|
| 1 | 取一个**源色**（source color） |
| 2 | 源色进入算法（Material Color Utilities，MCU） |
| 3 | 生成 **5 个 key colors**：primary / secondary / tertiary / neutral / neutral variant |
| 4 | 每个 key color 展开成一条 **tonal palette** |
| 5 | 从各 palette 取特定 tone 填进 26 个角色（浅色 + 深色各一套） |
| 6 | 角色已绑在组件上，UI 换色 |

源色有三种来源：壁纸、应用内内容、**设计师手挑**。官网特别提示：「**Did you know? The baseline static color scheme uses a hand-picked source color.**」——baseline 也走同一条链路，只是源色固定。

五色由算法操纵源色的 hue 与 chroma 得到：「**Material's color algorithms manipulate the source color's hue and chroma to generate five complimentary key colors.**」（原文拼写 complimentary，即 complementary）

**tonal palette 的刻度**：「**Colors in these palettes are given a number from 0 to 100 in increments of 10, as well as 95, 98, and 99. Some palettes include more values.**」tone 越小越暗：0 = 纯黑，100 = 纯白。同一 hue 下 tone 变化会带来 chroma 变化，所以调亮/调暗不是等比缩放。

**角色怎么取 tone**：「**the algorithm assigns the color tone primary40 to the primary role and the tone primary100 to the on primary role.**」规律：primary 一族取自 primary palette，surface 一族取自 neutral palette；深色主题的 tone 也由算法给出——**一套角色名同时产出浅色与深色两套值**。

### HCT：色彩空间的三个维度

「**The system uses a color space called HCT, which defines all colors using three dimensions: hue, chroma, and tone.**」

| 维度 | 定义 | 取值范围 |
|---|---|---|
| Hue（色相） | 红/橙/黄/绿/蓝/紫的知觉 | 0–360，环状（0 与 360 同色） |
| Chroma（彩度） | 有多「彩」或多「灰」 | 0 到无穷，**HCT 里实际上限约 120** |
| Tone（色调） | 明暗，等价于 luminance | 0（纯黑）–100（纯白） |

两条硬性认知：

1. **tone 决定对比度**，可访问性配对本质上是「tone 间距」。官方例子：按钮容器 tone 50 + 文字 tone 98 → 3:1；tone 30 + tone 98 → 7:1。用户可调对比度就是让算法把 tone 间距拉大。
2. **HCT 不能和 HSL/RGB 互换**。「**Don't conflate color spaces when inspecting or adjusting colors.**」同样的感知亮度在 HCT 里 tone 相同，在 HSL 里 lightness 可能不同。用拾色器读 HSL 判断对比度是错的。

## 三档对比度

官网（2025 年 5 月新增）：「**In addition to light and dark theme, color roles also support three levels of contrast.**」

| 档位 | 含义（原文要点） |
|---|---|
| Standard（默认） | 用高/低对比元素混搭建立层级，降低认知负担 |
| Medium | **最低 3:1**；给需要更多对比、但高对比会不适（如 halation 光晕）的人 |
| High | **7:1**，减少视觉干扰；只作用于要害内容——「high contrast is applied to the content in a card but not the card container」 |

- 档位**同时作用于浅色与深色主题**，且已 token 化。
- 自定义组件要支持三档，办法是用标准角色对（如 `primaryContainer` + `onPrimaryContainer`），让角色换档时自动取新值。
- 档位是**用户设置**，不是产品自选；产品能做的是不要破坏它。

## 配色方案怎么选

官网：一个 color scheme 描述「**all of a product's colors, color roles, and color relationships across light and dark themes**」。只有两大类：静态、动态。

| | 静态（Static） | 动态（Dynamic） |
|---|---|---|
| 随用户/内容变色 | ✗ | ✓ |
| 个性化 | ✗ | ✓ |
| 用户可控对比度 | ✗ | ✓ |
| 各设备颜色一致 | ✓ | ✗（唯一的「得不到」） |
| 成本 | 低（有现成 baseline） | 系统生成便宜，设计与测试贵 |

**选静态（baseline）**：还没准备好上动态色（以后切换很容易）；从 M2 迁移、要 M3 特性又不想弄坏现有外观；面向企业用户，个性化与对比度设置没有收益；产品要上 iOS。

**选动态**：想展示个性化；想随壁纸 / 内容变色；想提供用户可控对比度；还不确定要不要混用动静——可以先上动态，后续再加静态色。

**关键做法**：即使最终要上动态，也**先用 baseline 设计**，确保角色映射正确，再用 Material Theme Builder 在多组源色下验一遍。「**While the actual colors may change, the color role mappings remain the same across dynamic color schemes.**」

## 动态取色

| | User-generated | Content-based |
|---|---|---|
| 源 | 用户壁纸 | 应用内内容（专辑封面、logo、视频预览） |
| 提取 | 量化（quantization）取一个源色 | 同样量化取一个源色 |
| tone 处理 | 按系统设定 + 用户偏好映射明暗 | **调整 tone 贴合源图观感**，同时保住对比度 |
| 适用 | 要「个性化」、要最新 Material 能力 | 内容为核心、团队能做高级定制、媒体播放等 |

原文对 content-based 的边界写得很具体：「**Content-based color is best used for contained screen elements adjacent to the source image, though the source image is not always visible.**」

**多个源**：产品同时满足多类条件、且不介意做高级定制时可混用。官网建议「**Get started with user-generated color before customizing**」——先跑通壁纸方案，再叠加内容色。

### 三条落地约束（比选型更重要）

1. **一屏最多两种来源类型**：「**Limit a screen to two color schemes from different source types.**」
2. **内容色跟着源内容出现**：「**Pair content-based color with its source content**」「**Avoid applying content-based color in spaces where the content itself isn't visible.**」
3. **不要替换语义色**：红色错误、绿色成功这类约定不能换成动态内容色，「**it may interfere with someone's understanding**」。

### 混用两种方案

以 baseline 或壁纸方案打底铺全局，再在**被内容界定的局部容器**（媒体控制区、某张卡片、整屏沉浸页）套内容色。官方示例是智能家居控制页：媒体控制区用专辑封面的 teal 方案，其余 UI 用壁纸的 red 方案。

## 静态基线色与自定义品牌色

### Baseline

「**Baseline is the default static color scheme. It uses accessible color pairings and includes colors for both light and dark themes.**」用它就是拿一套出厂配色，不为品牌改色；适合先跑通结构，也适合 M2 迁移。

一个容易忽略的历史修正（2023-02）：浅色主题 surface 的默认 tone 从 **99 改成 98**，neutral palette 的 chroma 从 **4 提到 6**，深色主题的 surface 角色略微压暗——目的是和 Android 系统 UI 对齐。网上抄来的旧 baseline 十六进制值会和现在的对不上。

### Custom brand

「**In a brand-based static scheme, the colors are hand-picked by your team to align with your product's brand color. Brand-based schemes are entirely created and maintained by your team, so this approach requires a larger investment of time and effort.**」

流程：新建 theme 并命名（成为 Figma 角色前缀）→ Custom → Primary → 输入品牌色 Hex → Apply → 需要时对 Secondary / Tertiary / Error / Neutral / Neutral Variant **分别**设源 → 导出（Compose、Android Views、Flutter、Web、JSON）。

### 自定义 baseline 的配色惯例

官网原文：「**Conventionally, primary and tertiary colors are the most visually prominent in the scheme, with tertiary appearing complementary to primary by changing its hue. Secondary, neutral variant, and neutral colors match primary in hue but are progressively less chromatic in that order.**」

可执行版：primary 与 tertiary 负责「跳」，tertiary 靠**换 hue** 形成互补；secondary → neutralVariant → neutral 保持**与 primary 同 hue**，彩度依次递减。颜色输进对应类别，才会得到预期关系；对不上时用 **color fidelity** 调。

## Advanced：应用 / 定义 / 调整

官网把高级定制归成三个动作：「**applying, defining, or adjusting colors**」。

| 动作 | 能做什么 | 手段 |
|---|---|---|
| Apply | 用在默认没提供的位置/方式 | 组合多套 scheme；改组件默认映射；给自定义组件映射角色 |
| Define | 在现有角色之外加颜色 | 定义 static colors（原 custom colors）；定义 custom color roles |
| Adjust | 改算法输出，不改角色 | 自定义 baseline / dynamic scheme；color fidelity；harmonize |

**定义静态色**：输入一个参考色，Material 返回**四个派生角色**（main / on-main / container / on-container），遵循 accent 组的同一套关系。适合品牌表达与语义色，且仍吃动态色与对比度能力。**不要再自建红色错误色**：「**Material provides the red Error color out of the box as an example of a static color**」。

**定义自定义角色**：只在 26+ 角色与静态色都满足不了时做。要按官方方式声明三件事——palette + reference tone（从六条 palette 里取，浅/深色各给 tone，如 `primary70`）；color pairing（成对的前景/背景、需保留的 tone 差）；contrast（每对达到最低对比度）。然后用 MCU 按主题与对比度档位动态生成。

**Color fidelity（保真）**：「**Color fidelity is a feature that adjusts colors' tones to match that of your input color.**」Material 会把颜色压到特定 tone 以满足对比度，有时这会让颜色不够艳；fidelity 在不破坏对比度的前提下调 tone。Theme Builder 里是输入色的 **match color** 开关；自定义 baseline / 定义静态色时**默认开启**；代码里是给角色加布尔标记。开了它更要守住「on-* 配父色」，因为它改的就是 tone。

**Harmonize（协调）**：「**In dynamic schemes, you can automatically adjust the hue of static colors so they look better alongside the scheme's primary color.**」原理是色相越近越协调；有保护——**限制 hue 偏移量**，红可以更暖/更冷，但不会变成紫或橙，语义不丢。实现用 MCU 的 `Blend`。**品牌色不要 harmonize**：「**Don't harmonize colors whose appearance should stay absolutely consistent, such as brand colors.**」

## 落地到 Compose

```kotlin
val colorScheme = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (dynamicColor) {
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        } else {
            if (darkTheme) DarkColors else LightColors
        }
    }
    else -> if (darkTheme) DarkColors else LightColors
}
```

- 动态取色依赖平台能力（API 31 / Android 12 起；**色彩页未写明**，属平台实现事实）。
- **必须有 fallback**：关掉主题色、旧设备上界面要完整。
- 开启动态取色后**用户可调对比度档位**，必须在该状态下重新验证可读性。
- 品牌色只应落在 primary / tertiary 一族，不要污染 surface 系列。
- **只绑角色，不绑色调值**：「**Always apply color roles rather than static values or tonal palette values, as these colors will break with light and dark themes, contrast control, and other features.**」

## 对比度

| 内容 | 最低 | 依据 |
|---|---|---|
| 成组（clustered）元素容器对背景 | 3:1 | designing 页 |
| 小号文字 | 4.5:1 | W3C / designing 页 |
| 大号文字与图形 | 3:1 | W3C / designing 页 |
| 独立组件（如 FAB）容器对背景 | 不要求 | designing 页 |
| Disabled 态 | 不要求 | designing 页 |

「大号文字」的官方定义：「**Large text (at 14 pt bold/18 pt regular and up)**」——14pt 粗体或 18pt 常规及以上。

### 必须修正的一处既有结论

原文档写「系统对配对本身保证至少 3:1」，并把 4.5:1 只当文字要求。官网 advanced/apply-colors 页的口径更严格：

> 「**Any color roles starting with "on-" are guaranteed to have sufficient contrast with the corresponding color role. Other color role pairs may not meet the 4.5:1 (small text) and 3:1 (large text) Material contrast requirements.**」

准确说法：**3:1 是角色配对的设计下限；只有 `on*` 与父色是「保证足够」的配对**。自己发明的配对（如 `primaryContainer` 上放 `onSurface`）必须实测。

**独立组件为什么豁免**：「**Standalone components, such as FABs, don't need to meet Material's minimum contrast of 3:1 between the container and background colors because of their prominence.**」——脱离群体的元素靠体积与位置就能被识别；成组元素才需要 3:1 把自己从背景里分出来。

## 四条硬规则

1. **Container 不能当文字色**。官网明确：container 是前景元素的填充色，不应用于文字或图标。
2. **只在官方给定的配对与层级里用色**。乱配会在用户调整对比度档位时直接不可读；只有 `on*` 配对是保证可读的。
3. **颜色不能是唯一信息载体**。错误态必须同时有图标或文案。
4. **不要用 tone 值或十六进制值代替角色**。tonal palette 值不参与主题翻转与对比度切换。

## 常见错误

- 直接写 `Color(0xFF6750A4)` 当主色，绕过角色系统。
- 用 tonal palette 的 tone 值（如「primary40」）当组件色——切浅色深色就崩。
- 用 `primaryContainer` 当文字色（官方禁止：container 是填充色）。
- 用 `outline` 画分割线或卡片边框（应用 `outlineVariant`）。
- 深色主题只是把浅色主题反相。
- 用 `background` / `onBackground`——新版 material3 已 deprecated，改用 `surface` 系列。
- 用 primary 铺大面积背景；一屏出现两个以上 primary 填充按钮。
- 用 Fixed 色做需要对比度的地方，深色主题下不可读。
- 把 scrim / shadow 当成普通角色参与配对。
- 一屏堆三套以上不同来源的 scheme；内容色离开源图单独使用。
- 用内容色替换错误红 / 成功绿。
- 给品牌色开 harmonize，品牌色被系统悄悄改色相。
- 用拾色器的 HSL lightness 判断对比度（HCT 与 HSL 不可互换）。

## 检查项

- [ ] 组件里没有裸 `Color(0x...)`，也没有直接引 tone 值
- [ ] 每个填充色都配了对应的 `on*` 内容色（而不是自选前景色）
- [ ] 没有用 container 当文字色
- [ ] 分割线用 `outlineVariant`，可点击边界用 `outline`
- [ ] Dynamic color 有 fallback，三档对比度都验过；浅色 / 深色两套都验过
- [ ] 跨断点 surface 映射一致（正文 surface / 导航 surfaceContainer）
- [ ] 一屏 scheme 来源类型 ≤ 2，且内容色旁能看到源图
- [ ] 语义色（error / 成功）未被动态色或 harmonize 改动
- [ ] 小号文字 4.5:1、大号文字与图形 3:1、成组容器对背景 3:1 均已实测

## 来源

- 色彩角色（26 角色、outline 禁令、add-on）：https://m3.material.io/styles/color/roles
- 系统机制（HCT、tonal palette、三档对比度）：https://m3.material.io/styles/color/system/how-the-system-works
- 系统总览与更新记录：https://m3.material.io/styles/color/system/overview
- 方案选择：https://m3.material.io/styles/color/choosing-a-scheme
- 动态源选择：https://m3.material.io/styles/color/dynamic/choosing-a-source
- 用户壁纸源：https://m3.material.io/styles/color/dynamic/user-generated-source
- 内容源：https://m3.material.io/styles/color/dynamic/content-based-source
- 静态基线：https://m3.material.io/styles/color/static/baseline
- 自定义品牌：https://m3.material.io/styles/color/static/custom-brand
- 高级定制总览：https://m3.material.io/styles/color/advanced/overview
- 应用颜色：https://m3.material.io/styles/color/advanced/apply-colors
- 定义新颜色：https://m3.material.io/styles/color/advanced/define-new-colors
- 调整既有颜色（fidelity / harmonize）：https://m3.material.io/styles/color/advanced/adjust-existing-colors
- 工具与教程：https://m3.material.io/styles/color/resources
- 对比度要求：https://m3.material.io/foundations/designing/color-contrast

核验日期：2026-11-16

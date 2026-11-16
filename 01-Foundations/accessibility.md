# 无障碍 / Accessibility

> 官方指南（原则）：https://m3.material.io/foundations/overview/principles
> 官方指南（辅助技术）：https://m3.material.io/foundations/overview/assistive-technology
> 官方指南（为所有人设计）：https://m3.material.io/foundations/building-for-all/user-needs ｜ /co-design
> 官方指南（文本与写作）：https://m3.material.io/foundations/writing/best-practices ｜ /text-resizing ｜ /text-truncation
> Android：https://developer.android.com/guide/topics/ui/accessibility
> Compose：https://developer.android.com/develop/ui/compose/accessibility
> 核验日期：2026-11-16
> 核验方式：m3.material.io 全站 dump 逐页核对；Android/Compose 数值沿用本库既有条目
> 状态：**「无障碍默认开启」、三类辅助技术、200% 缩放、对比度与色调差换算均按官网原文核对**

## 一句话结论

官网原文：**「Accessibility by default is a core design value for Material.」**

无障碍不是交付前的检查环节，而是**组件默认自带**的属性：

> Accessibility standards are built into Material components, providing a foundation for inclusive product design.

## 四条硬指标

| 项 | 要求 | 依据 |
|---|---|---|
| 触控目标 | 最小 48×48dp，相邻目标至少 8dp 间隔 | Android / Material 交互规范 |
| 文字对比度 | 4.5:1（正文）/ 3:1（≥18pt 或 ≥14pt bold） | Material 写作页：「3:1 minimum color contrast for large text and 4.5:1 for small text」 |
| 图形对比度 | 3:1（图标、边框、图形元素） | WCAG AA |
| 文本缩放 | 系统字体 200% 下不截断、不重叠、可滚动 | 「UIs should support a minimum text increase of 200%.」 |

禁用态是**唯一豁免**：

> Disabled states don't need to meet Material's contrast requirements.

## 辅助技术类型

官网定义：**「Assistive technology helps increase, maintain, or improve the functional capabilities of individuals with disabilities.」** 途径包括键盘、屏幕阅读器、盲文显示器、追踪输入、放大镜、语音输入。

| 类型 | 工作方式 | 对设计/实现的要求 |
|---|---|---|
| 键盘（含 D-pad、轨迹球） | 硬件或软件方向控制器，**线性**逐项跳转 | 焦点顺序必须可预测；焦点态必须可见 |
| 屏幕阅读器（TalkBack / VoiceOver / JAWS） | 用盲文显示器或朗读方式输出；会读可见文本，也会读隐藏的 alt text、heading | 语义标签完整；装饰元素要能被跳过 |
| 开关输入（Switch Access） | 逐个**扫描**屏幕上的项目并高亮，直到用户确认 | 焦点必须线性可达；不能只靠手势完成关键操作 |

⚠️ 推断，页面未明示：官网只列了这三类，但定义段落还提到放大镜（magnifier）、语音输入、追踪输入。放大镜意味着**布局不能依赖像素级精确对齐**；语音输入意味着**控件的可访问名称要能被口述**（名称过长或含符号会难以命中）。

### 屏幕阅读器的两条推论

1. **图里的文字读不到**：「Screen readers are unable to read text that is embedded in imagery.」关键信息必须在 alt text 里复述。
2. **caption 与 alt text 是两套信息**：caption 说明图片与内容的关联，alt text 描述图片本身，两者都不该复读对方。

## 包容性设计方法

### 三条官方原则

| 原则 | 原文要点 | 落地含义 |
|---|---|---|
| Honor individuals / 尊重个体 | 「Universal default experiences rarely meet everyone's needs.」 | 默认体验里要留出可定制空间，允许个人适配 |
| Learn before, not after / 先学后做 | 「Before defining solutions, invest time in understanding the needs of users with a wide range of abilities」 | 需求研究放在方案之前，而不是上线后补 |
| Requirements as a starting point / 把要求当起点 | 「The minimum requirements established by WCAG support specific human needs.」 | WCAG 是下限，不是上限；深色模式、语音转文字都源自具体访问需求 |

官网对第三条给了一个很有用的历史论证：**深色模式、text-to-speech、speech-to-text 都是「在约束里做创造性解题」的产物**，最终惠及所有人。

### 经验的维度（研究清单）

官网要求在研究、设计、测试、市场各环节持续考虑这份**会演进的清单**：

年龄、文化、残障、教育与识字水平、族裔、性别、地理与全球相关性、身体特征、种族、宗教、性取向、社会经济地位、技术熟练度。

### 共同设计（co-design）

> Co-design supports innovation by involving the people who use a product or service in the creation process and goes beyond traditional user research.

官网给的提问清单（节选，可直接拿去评审）：

- 还有谁能从这个产品受益？他们的需求是什么？
- 如何确保被忽视的群体**持续**参与测试？
- 社区成员与专家是否**早期且频繁**介入？
- 他们的反馈如何影响功能优先级、功能范围与文案？
- 如果把这些群体排除在外，风险与潜在伤害是什么？
- 若使用 ML / AI，如何降低偏见风险？

## 语义

屏幕阅读器读到的内容与视觉呈现是两套信息。常见缺口：只有图标没有描述、装饰性图片读出无意义文本、自定义绘制内容完全没有语义。

```kotlin
IconButton(onClick = onDelete) {
    Icon(Icons.Default.Delete, contentDescription = "删除这条笔记")
}

Image(painter = painter, contentDescription = null)

Row(modifier = Modifier.semantics(mergeDescendants = true) { }) { /* 标题 + 副标题 */ }

Switch(
    checked = checked,
    onCheckedChange = onToggle,
    modifier = Modifier.semantics {
        stateDescription = if (checked) "已开启" else "已关闭"
    }
)
```

规则：

- `contentDescription` 描述**结果**（「删除这条笔记」），不是控件名（「垃圾桶图标」）。
- 纯装饰元素设 `null`，不要用空字符串凑数。官网对应写法是 HTML 的 `alt=""`：**「which will hide the content from screen reader users」**。
- 同一条信息被拆成多个 `Text` 时用 `mergeDescendants` 合并，否则 TalkBack 断成碎片。
- 自定义 `Canvas` 绘制的内容必须补 `semantics`，否则完全不可访问。

## Alt text

| 规则 | 数值 / 做法 |
|---|---|
| 长度 | **140 字符**（content-design/alt-text）；**125 字符**（writing/best-practices）——官方两页不一致，取 125 更安全 |
| 开头 | 不要写「image of」「picture of」，屏幕阅读器已会播报图片 |
| 装饰图 | 直接标记装饰（`alt=""` / `contentDescription = null`） |
| 图注 | 不要把 caption 复制成 alt text，用户会听两遍 |
| 图表 | 公式：`Summary of [data type] + [reason for showing the chart]`，讲结论而非罗列数据点 |
| 动效 / GIF | 写「你会给它起的标题」，不逐步描述动作；长视频用 video description |

判定起点只有一句：**「If you remove the image from the page and no information is lost, then the image is decorative.」**

## 焦点顺序

- 默认按组合顺序朗读。视觉顺序与代码顺序不一致时，**优先改代码顺序**，`traversalIndex` 只作兜底。
- Tab 顺序必须符合逻辑：官网给的默认是**从左到右、从上到下**。
- **焦点态要有环状指示器**：「the focus state includes a ring-like keyboard focus indicator」，它让键盘用户知道自己在页面何处。
- Escape 键要能：关闭 modal（菜单、对话框、bottom sheet）、移除可见焦点指示并把焦点顺序置 0、结束文本输入但不删已输入内容。
- 弹层（Dialog / BottomSheet）出现时焦点必须进入弹层，关闭后回到触发元素。
- 不要用 `focusable` 把不可交互的容器塞进焦点链。
- 一屏内**只能有一个** focused 状态。

## 触控目标

| 项 | 数值 | 说明 |
|---|---|---|
| 最小触控目标 | **48×48dp** | 交互热区 |
| state layer 视觉尺寸 | **40dp** | 「The size of state layers is 40dp while the interactive target size is 48dp.」 |
| 相邻目标间隔 | ≥ 8dp | 避免误触 |

关键区分：**视觉 40dp，热区 48dp**。图标本身可能只有 24dp，热区必须外扩到 48dp——官网在辅助技术页用图标举例说明「the expanded icon perimeter makes interacting with an action more accessible」。

## 对比度

### 数值门槛

| 文本类型 | 最低对比度 |
|---|---|
| 小字（正文） | **4.5:1** |
| 大字（large text） | **3:1** |
| 非文本图形（图标、边框） | **3:1** |
| 禁用态 | **豁免** |

### 色调差换算（比记比值更好用）

> A difference of 40 in tone guarantees a WCAG contrast ratio ≥ 3.0; a difference of 50 in tone guarantees a contrast ratio ≥ 4.5.

即：在 HCT 色调轴上，**差 40 个 tone 保证 ≥ 3.0，差 50 个 tone 保证 ≥ 4.5**。这是 M3 角色配色能「自动达标」的数学基础。

### 必要与非必要元素

| 类别 | 是否要达标 |
|---|---|
| 必要信息（文字、说明性图形） | 要 |
| 非必要装饰（营造氛围的背景元素） | 不要：**「They do not relay information and do not have to meet Material's contrast requirements.」** |

## 颜色不是唯一载体

官网在状态页的第一条总则：**「States have two visual indicators to ensure accessibility.」**

| 状态 | 除颜色外必须还有 |
|---|---|
| 错误 | 图标 + 文案 |
| 必填 | 文字标注（不要只加星号） |
| 选中 | 勾选图标 / checkbox / active indicator / 形状变化 |
| 悬停、聚焦、按下 | state layer + 形状、描边、高度或环状焦点指示 |
| 图表数据系列 | 图案或直接标注 |

色盲用户看不到「红变绿」，但看得到图标和文字。

## 字体缩放

| 规则 | 数值 / 做法 |
|---|---|
| 支持下限 | **200%** |
| 文字与行高 | 按缩放值成比例放大 |
| 内边距 | **保持 1×**（例：按钮上下 8dp、左右 24dp 不变） |
| 元素间距 | **保持 1×** |
| 无文字组件 | **不缩放**（进度条、checkbox、radio） |
| 计算方式 | 默认字号（density = 0）× 缩放值；14pt @ 2× = 28pt |
| 设备不接管时 | 应用内提供 1.5× / 2× 倍率 |

工程侧配套：

- 全部文字用 `sp`。
- 固定高度容器（`height(48.dp)`）是大字号截断的主因，改用 `heightIn(min = 48.dp)`。
- 按钮文字在 200% 时要允许换行或改用扩展宽度，不要用 `maxLines = 1` 硬截。
- 空间不足时**重排**：并排改堆叠，而不是缩小字号。
- 滚动**只给一个方向**，优先垂直。
- 单个 `Icon` 不要跟着字号无限放大，图标尺寸应单独约束。
- 受限区域（top app bar、navigation bar、navigation rail、固定 tabs）可用**长按 tooltip** 展示放大后的文字。

底线：**用户改字号后，内容、可理解性、功能都不能丢失**。带省略号却没有查看全文途径的截断，**不可访问**。

## 键盘与遥控

- 平板 / 折叠屏 / TV / 桌面场景下，所有可交互元素要能被 Tab / D-pad 到达。
- 焦点态必须有可见视觉指示，不能只依赖系统默认。
- 鼠标 / 触控板 / 手写笔操作时提供上下文菜单（次键点击），触摸时提供触摸选择控件——两套控件不要同时出现。
- 自定义 `clickable` 会丢失按钮语义，优先用 `Button` / `IconButton` 等语义完整的组件。

## 检查项

- [ ] 所有图标按钮有 `contentDescription`
- [ ] 装饰性图片 `contentDescription = null` / `alt=""`
- [ ] alt text ≤ 125 字符，不以「图片」开头，不复制图注
- [ ] 触控目标 ≥ 48dp，视觉 state layer 40dp，相邻目标留 8dp
- [ ] 浅色 / 深色下对比度均达标（小字 4.5:1、大字 3:1）
- [ ] 系统字体 200% 下无截断、无重叠，滚动只有一个方向
- [ ] 截断处都能通过 tooltip / 链接看到全文
- [ ] 弹层焦点能进能出，Escape 能关闭并清焦点
- [ ] 状态不用颜色单独表达
- [ ] 开关输入 / 键盘可以线性走完所有关键操作
- [ ] 自定义绘制内容有语义描述

## 来源

- https://m3.material.io/foundations/overview/principles （核验 2026-11-16）
- https://m3.material.io/foundations/overview/assistive-technology （核验 2026-11-16）
- https://m3.material.io/foundations/building-for-all/user-needs （核验 2026-11-16）
- https://m3.material.io/foundations/building-for-all/co-design （核验 2026-11-16）
- https://m3.material.io/foundations/writing/best-practices （核验 2026-11-16）
- https://m3.material.io/foundations/writing/text-resizing （核验 2026-11-16）
- https://m3.material.io/foundations/writing/text-truncation （核验 2026-11-16）
- https://m3.material.io/foundations/content-design/alt-text （核验 2026-11-16）
- https://m3.material.io/foundations/interaction/states/state-layers（40dp / 48dp，核验 2026-11-16）
- https://m3.material.io/foundations/glossary（tone 差 40 / 50，核验 2026-11-16）
- https://developer.android.com/guide/topics/ui/accessibility （核验 2026-11-16）
- https://developer.android.com/develop/ui/compose/accessibility （核验 2026-11-16）

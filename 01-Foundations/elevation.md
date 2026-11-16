# 层级 / Elevation

> 官方指南：https://m3.material.io/styles/elevation ｜ 令牌：https://m3.material.io/styles/elevation/tokens ｜ 落地：https://m3.material.io/styles/elevation/applying-elevation
> 核验日期：2026-11-14（Applying elevation 增补于 2026-11-16）
> 核验方式：Chrome CDP 渲染官方 Tokens 页提取（含组件分级表）
> 状态：**六级 dp 值与组件分级已按官方原文核对**

## 先纠正两个容易搞错的前提

**1. surfaceTint 已废弃。**
官网原文：**「Surface tint color is deprecated. Use elevation level tokens (0–5) instead.」**
所以不要再靠 tint 给表面染色来「抬高层级」。

**2. elevation 本身没有阴影、也没有自己的值。**
官网原文：**「Elevation has no shadow or value of its own by default.」**

elevation 只回答一个问题：**这个组件在 z 轴上叠在谁上面**。它决定的是堆叠顺序、以及 hover / focus 时是否「抬高一层」，不负责视觉上的轻重。

> 换句话说：**视觉层级靠 surface container 色阶（色彩系统的事），堆叠关系靠 elevation。** 两套系统各管一段，不要拿 elevation 去表达「重要」。

## 六个等级

| Level | dp | 说明 |
|---|---|---|
| 0 | 0dp | 默认贴地 |
| 1 | 1dp | 轻微抬升 |
| 2 | 3dp | |
| 3 | 6dp | |
| 4 | 8dp | **官方未分配给任何组件的静止态** |
| 5 | 12dp | **官方未分配给任何组件的静止态** |

注意 4 和 5：官方明确标注「not assigned as resting level」，只可能在交互过程中临时出现。Applying elevation 页原文：

> An element's resting state can be on levels 0 to +3, while **levels +4 and +5 are reserved for user-interacted states such as hover and dragged**.

（注意这里点名的交互态是 **hover 和 dragged（拖拽）**——比「focus」更具体。）

## 组件分级（官方原文）

### Level 0（0dp）

App bar（未滚动）· Buttons（filled / tonal / outlined）· Button groups · Cards（filled / outlined）· Carousel · Chips · Dialog（全屏）· Extended FAB（在导航栏内）· FAB（在导航栏内）· FAB menu（列表项）· Icon buttons · List · Navigation rail · Segmented button · Side sheet（docked）· Slider · Split button · Tabs

### Level 1（1dp）

Banner · Bottom sheet（模态）· Button（elevated）· Card（elevated）· Chips（elevated）· Navigation drawer（模态）· Side sheet（模态）

### Level 2（3dp）

App bar（已滚动）· Menu · Navigation bar · Rich tooltip · Toolbar

### Level 3（6dp）

Date pickers · Dialogs（模态）· Extended FAB · FAB · FAB menu（关闭按钮）· Search · Time pickers

### Level 4 / 5

不分配给静止态组件，仅在悬停/聚焦时临时抬升（通常 +1 级）。

**几个容易记错的点**：
- **模态 Dialog 是 Level 3（6dp）**，不是 5。
- **Snackbar 不在表里**——它用的是 inverse surface 角色，不是靠 elevation 抬升。
- **Navigation bar 是 Level 2**，Navigation rail 是 Level 0。
- **FAB 是 Level 3**，但 FAB 放进 navigation rail 后变成 Level 0。

## Compose 对应

```kotlin
// 卡片：用 Surface 的表面色表达层级
Card(
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
)

// 需要单独控制时
Surface(
    tonalElevation = 0.dp,
    shadowElevation = 1.dp
) { /* content */ }
```

关于两个参数：

- `tonalElevation` 在旧版 M3 里通过 surfaceTint 给表面染色。**surfaceTint 已废弃**，新代码不要依赖它做视觉层级。
- `shadowElevation` 只画阴影。
- 两者都不是「表达重要程度」的工具。

## 深色主题

- 深色下阴影几乎不可见，**不要**只靠 `shadowElevation` 表达任何东西。
- 层级差异在深色主题里只能靠 surface container 色阶体现。
- 相邻容器色的色差本身很小，需在低亮度屏 / 强光下确认可分辨。

## 落地规则（Applying elevation）

### 三种描绘手段

Applying elevation 页原文：

> Elevation can be depicted using **shadows or other visual cues, such as surface fills with a tone difference or scrims**.

| 手段 | 官方定位 |
|---|---|
| **Tonal difference（色调差）** | **默认手段**——「By default, Material 3's surfaces use tonal difference to indicate separation.」 |
| Shadows | 特定场景补充（见下） |
| Scrims | modal / 展开的导航菜单下方 |

要成功描绘层级，一个表面必须能展示三件事（官方原文）：

> Surface **edges**, contrasting the surface from its surroundings
> **Overlap** with other surfaces, either at rest or in motion
> **Distance** from other surfaces

交互组件的表面边缘要达到**可访问对比度**，才能被看成独立表面——「Ensure floating elements have sufficient contrast with surfaces beneath.」

### elevation 与 surface container 色阶的分工

Applying elevation 页原文（这是两套系统关系的最权威表述）：

> You can pick from a range of surface and surface container color roles. **These roles are not tied to elevation**, and provide flexibility for defining containment areas.
> **Any overlapping containment areas or components should have different color roles** in order to visually communicate separation.

落地成三条：

- surface / surface container 色阶**与 elevation 解耦**：不存「Level 2 必须配 surfaceContainerHigh」这类映射，选色自由。
- 但**重叠的容器必须用不同的 color role**，否则视觉上分不出两个表面。
- 分工重申：elevation 管 z 轴堆叠关系（+ 色调差默认由 color role 承担视觉呈现），color role 管容纳区域的视觉区分。

### 阴影怎么用（少即是多）

> When it comes to applying shadows, **less is more**. The fewer levels in your UI, the more power they have to direct attention and action.

阴影的尺寸与软硬表达距离：

> A surface with a shadow that's **small and sharp** indicates a surface's **close** proximity to the surface behind it. **Larger, softer** shadows express **more distance**.

**该用可见阴影的两种时机**（官方仅列两种）：

1. **Protect elements（保护元素）**：背景有图案或视觉嘈杂、发丝线不够用时，用 elevation 分离并强调 cards / chips / buttons。
2. **Encourage interaction（鼓励交互）**：元素在 focus / selection / swipe 等交互时**临时抬升**；且被抬升的元素也可以在更高层元素出现时**降低**。

### Scrim（遮罩）

> Scrims use the **scrim color role at an opacity of 32%**.

用在 modal 与展开的 navigation menu 下方，通过提升大块层叠表面的视觉对比来聚焦。

### 交互抬升的官方示例

Overview 页原文：

> Hovering a FAB temporarily increases the elevation by **1 level, from level 3 to level 4**. **All Material buttons increase elevation by 1 level when hovered.**

配套规则：

- 所有组件都有默认 resting elevation，**避免改动**（「Avoid changing the default elevation of Material components.」）。
- 组件只应因**系统事件或用户交互**改变 elevation，且同类元素必须一致。
- M3 刻意把层级限制在 6 档——「Material 3's elevation system is deliberately limited to just a handful of levels. This creative constraint means you need to make thoughtful decisions about your UI's elevation story.」

### 与 M2 的差异（官方三条）

| 维度 | M2 | M3 |
|---|---|---|
| 阴影 | 所有层级默认加阴影 | **只在需要时**用（保护元素 / 鼓励交互） |
| 颜色 | 旧映射 | 新 color 映射 + 动态色兼容 |
| 层级 | 按数值描述 | 按 **levels（0–5）**描述 |

### surfaceTint 废弃后的替代做法

综合 Tokens 页与 Applying 页的官方口径：

- Tokens 页：**「Surface tint color is deprecated. Use elevation level tokens (0–5) instead.」**——直接用 level 令牌（0/1/3/6/8/12dp）声明堆叠关系。
- 视觉呈现交给 **surface container 色阶**（tonal difference 是官方默认的分离手段，且 color role 与 elevation 解耦、按「重叠即异色」规则选）。
- 阴影降级为两种特定时机的补充手段；scrim 固定 32% 透明度。
- Compose 侧：新代码不要依赖 `tonalElevation` 的染色行为表达层级（旧版 M3 中它经 surfaceTint 给表面染色）；`shadowElevation` 只画阴影，不承载语义。

## 常见错误

- 把模态 Dialog 设为 Level 5（官方是 3）。
- 用 elevation 表达「重要」——那是颜色与字阶的职责。
- 给静止态卡片加大阴影「显得立体」，回到 M2 审美。
- 依赖 `surfaceTint` 做层级（已废弃）。
- 用 4dp / 16dp 这类档位外数值。
- 忘了 hover / focus 会抬升一级，导致交互态与设计稿不一致。
- 在深色主题里用 shadowElevation 做唯一区分手段。
- 重叠容器用同一个 color role，色阶与 elevation 双双失效。
- 乱用 scrim——官方值只有 scrim role @ 32%。
- 把 color role 和 elevation 强行建映射（官方明确「not tied to elevation」）。

## 检查项

- [ ] 组件 elevation 与官方分级表一致
- [ ] 没有用 elevation 表达重要性
- [ ] 未依赖已废弃的 surfaceTint
- [ ] 交互态（hover / dragged）抬升一级已验证
- [ ] 深色主题下层级靠 surface container 而非阴影
- [ ] 重叠容器使用不同 color role
- [ ] 可见阴影只出现在「保护元素 / 鼓励交互」两种场景
- [ ] scrim 为 32% 透明度

## 来源

- https://m3.material.io/styles/elevation/overview（hover 抬升示例、M2/M3 差异、resting elevation 规则）
- https://m3.material.io/styles/elevation/tokens（六级 dp 值、组件分级表、surfaceTint 废弃声明）
- https://m3.material.io/styles/elevation/applying-elevation（描绘手段、color role 分工、阴影/scrim 规则）
- 核验日期：2026-11-16

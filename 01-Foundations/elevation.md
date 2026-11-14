# 层级 / Elevation

> 官方指南：https://m3.material.io/styles/elevation ｜ 令牌：https://m3.material.io/styles/elevation/tokens
> 核验日期：2026-11-14
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

注意 4 和 5：官方明确标注「not assigned as resting level」，只可能在交互过程中（hover / focus 抬升）临时出现。

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

## 常见错误

- 把模态 Dialog 设为 Level 5（官方是 3）。
- 用 elevation 表达「重要」——那是颜色与字阶的职责。
- 给静止态卡片加大阴影「显得立体」，回到 M2 审美。
- 依赖 `surfaceTint` 做层级（已废弃）。
- 用 4dp / 16dp 这类档位外数值。
- 忘了 hover / focus 会抬升一级，导致交互态与设计稿不一致。
- 在深色主题里用 shadowElevation 做唯一区分手段。

## 检查项

- [ ] 组件 elevation 与官方分级表一致
- [ ] 没有用 elevation 表达重要性
- [ ] 未依赖已废弃的 surfaceTint
- [ ] 交互态（hover / focus）抬升一级已验证
- [ ] 深色主题下层级靠 surface container 而非阴影

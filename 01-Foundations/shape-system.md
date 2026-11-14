# 形状系统 / Shape System

> 官方指南：https://m3.material.io/styles/shape/corner-radius-scale
> Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/MaterialShapes
> 核验日期：2026-11-15
> 核验方式：Chrome CDP 读官方正文（用 `textContent` 才能拿到折叠面板里的档位值）
> 状态：**十档 dp 值全部为官方原文**

## 先纠正一个常见错误：不是七档，是十档

官网原文：**「The Material 3 shape system uses a size-based scale with ten styles.」**

而且它和 M2 的差异是本质性的：

| | 依据 | 档数 |
|---|---|---|
| M2 | 组件容器的**尺寸** | 3 |
| **M3** | 形状圆角的**圆润程度** | **10** |

所以 M3 里圆角不是「小/中/大容器」的附属属性，而是独立的、可跨组件复用的表达维度。

## 十档（官方原文）

| 档位 | 圆角 |
|---|---|
| None | **0dp** |
| Extra small | **4dp** |
| Small | **8dp** |
| Medium | **12dp** |
| Large | **16dp** |
| **Large increased** | **20dp** |
| Extra large | **28dp** |
| **Extra large increased** | **32dp** |
| **Extra extra large** | **48dp** |
| Full | 完全圆角（胶囊） |

我原先写的七档漏了 `Large increased`(20) / `Extra large increased`(32) / `Extra extra large`(48) 三档——这三个正是 Expressive 带来的更大圆角。

`Full` 不是固定数值，按容器短边算。

## 对称与非对称

官网：**「Both symmetric and asymmetric shapes use the same 10-step scale.」**

- **对称**：四角相同。
- **非对称**：只有部分角是圆的，用于贴边组件。官方命名的档位包括：
  `Extra small top rounding`、`Large top rounding`、`Extra large top rounding`、`Large start rounding`、`Large end rounding`
- 典型场景：Bottom sheet 顶部圆角、依附屏幕边缘的 FAB menu。

非对称**不是新档位**，只是同一套十档作用在部分角上。

## Compose 里只有五个槽位

`Shapes` 只有五个字段，缺 0 / 20 / 32 / 48 / full：

```kotlin
val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp)
)
```

超出五档的需求，只能就地写：

```kotlin
Card(shape = RoundedCornerShape(20.dp))            // large increased
FloatingActionButton(shape = RoundedCornerShape(32.dp))  // extra large increased
Box(Modifier.clip(RoundedCornerShape(48.dp)))      // extra extra large
```

另外，material3 提供了 **`MaterialShapes`**（可做 shape morph 的那套命名形状），它和 `Shapes` 不是一回事：`Shapes` 是主题里的五档圆角，`MaterialShapes` 是用于 morph 的形状库。

## 已验证的组件默认值

| 组件 | 形状 |
|---|---|
| Button（圆形样式） | Full（全档） |
| Button（方角样式，Expressive） | XS/S 12dp · M 16dp · L/XL 28dp |
| Button（**按下态**） | XS/S 8dp · M 12dp · L/XL 16dp |
| Chip | 8dp（small） |
| Card | medium（12dp，通行值） |
| Dialog | extraLarge（28dp） |
| Bottom sheet | 顶部圆角（非对称） |

注意按钮那两行：**按下时圆角变小（更方）**，这是 Expressive 的形状 morph，不是笔误。

## 同一屏的档位纪律

- 一屏内圆角档位不超过 3 种，否则视觉上会碎。
- 嵌套容器时内层圆角应小于外层（外 16 内 12），否则同心感会破。
- 圆角值只从十档里取，不要出现 10dp / 11dp / 13dp。

## 常见错误

- 以为只有七档，漏掉 20 / 32 / 48。
- 为了凑 full 把 `extraLarge` 改成一个大数值，连带改坏所有 Dialog 和 Sheet。
- 每个组件手写 `RoundedCornerShape(10.dp)`，主题 `Shapes` 形同虚设。
- 用 `clip()` 改形状却没同时改背景，露出方角。
- 大圆角配小内边距，内容贴边。
- 非对称形状用对称 API 硬拼，四角全圆了。
- 把 `MaterialShapes` 和 `Shapes` 搞混。

## 检查项

- [ ] 所有圆角来自十档之一
- [ ] 主题 `Shapes` 只覆盖五档，其余就地写并注明档位名
- [ ] 一屏圆角档位 ≤ 3
- [ ] 嵌套结构内外圆角递减
- [ ] 非对称形状只作用于需要的角

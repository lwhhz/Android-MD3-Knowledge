# Split Button / 分裂按钮（Expressive）

- 官方指南：https://m3.material.io/components/split-button ｜ 规格页：https://m3.material.io/components/split-button/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-15 ｜ 核验方式：Chrome CDP 读官方 Specs 页
- 状态：**M3 无此组件，Expressive 新增**；完整尺寸 token 表存在配对歧义，仅收录可确认项

## 代际归属

官方变体表写得很明确：

| 变体 | M3 | M3 Expressive |
|---|---|---|
| Split button | ❌ | ✅ |

所以这**不是 M3 的老组件**，是新东西。在 M3 项目里想实现类似效果，只能用"按钮 + 紧邻的图标按钮"自己拼，但那样没有连体形状。

## 它解决什么问题

普通按钮只有"执行"一种能力。需要"执行默认动作"**和**"换一个动作"并排时，以前的方案是：

- 两个挨着的按钮（视觉上像两个东西，用户会犹豫点哪个）
- 按钮 + 溢出菜单（多一次点击）

Split button 把两者合成一条：**左边执行默认动作，右边展开可选动作**。

## 结构（官方 Anatomy）

```
┌──────────────┬─────┐
│ [icon] 标签   │  ▾  │
└──────────────┴─────┘
   Leading button  Trailing button
```

官方原文的规则：

> **Leading button 可以有图标、文字标签，或两者都有；Trailing button 必须始终是菜单图标。**

也就是说右边那半永远只负责"展开菜单"，不能自定义成别的动作。这是它和"两个普通按钮拼在一起"的本质区别。

## 尺寸（官方 token 表，五档全）

单位 dp。数据取自官网 token 面板的 `token-value-swatch` 结构（值与其标签在同一节点内），**不存在错位**。

| 属性 | XS | S | M | L | XL |
|---|---|---|---|---|---|
| 容器高度 | **32** | **40** | **56** | **96** | **136** |
| 两段之间的间隙 | 2 | 2 | 2 | 2 | 2 |
| 内圆角 | 4 | 4 | 4 | 8 | 12 |
| 外圆角 | 50% | — | — | — | — |
| 前段 左内边距 | 12 | 16 | 24 | 48 | 64 |
| 前段 右内边距 | 10 | 12 | 24 | 48 | 64 |
| 尾部图标尺寸 | 22 | 22 | 26 | 38 | 50 |
| 后段 左内边距 | 13 | 13 | 15 | 29 | 43 |
| 后段 右内边距 | 13 | 13 | 15 | 29 | 43 |
| 内圆角（hover） | 8 | 12 | 12 | 20 | 20 |
| 内圆角（pressed） | 8 | 12 | 12 | 20 | 20 |
| 后段内圆角（selected） | 50% | 50% | 50% | 50% | 50% |

几点说明：

- **容器形状是形状 token，不是数值**（表里显示为空白 + 一个形状预览块），对应 `shape` 而非 `dp`。
- **只有 XS 单独给了 `外圆角 = 50%`**，其余档位走容器形状 token。
- **内圆角在 hover / pressed 时会变大**（如 XS 从 4 → 8dp），这是 Expressive 的形状 morph，不是笔误。
- **后段选中态的内圆角是 50%**（完全圆角），这是"展开后被选中"的视觉反馈。
- 高度跨度很大（32 → 136dp），XL 已经接近一个中等卡片的高度——选型时不要只看"大一点"，要按实际承载的内容量定。

## 配置

- 尺寸：XS / S / M / L / XL
- 颜色：与普通按钮同一套（filled / tonal / elevated / outlined / text 的对应关系需按版本核对）

## Compose 实现

**不给代码**。原因和二：这套 API 在 1.5.0 线仍在演进（`SplitButtonLayout` / `SplitButtonDefaults` 之类的命名与签名跨版本有差异），照抄大概率编译不过。

接入前核对：

1. 类是否存在（`SplitButtonLayout`、`SplitButtonDefaults` 一类）；
2. 是否需 `@OptIn(ExperimentalMaterial3ExpressiveApi::class)`；
3. 前段/后段的内容是 slot 还是参数；
4. 形状是否由 `itemShape` 一类的辅助函数生成（避免手写圆角）。

## 使用判断

**该用**：一个主操作 + 2-3 个变体（如"发送" + "定时发送 / 存草稿"）。

**不该用**：
- 只有主操作、没有变体 → 普通按钮。
- 变体超过 5 个 → 用普通按钮 + 完整菜单。
- 主操作是破坏性的 → 让最显眼的半边做删除，误触代价太高。

## 无障碍

- **前段和后段是两个独立控件**，各自要有语义。不要合并成一个"分裂按钮"。
- 前段的 `contentDescription` 描述动作（"发送"），后段描述"更多发送选项"。
- 触控目标各自 ≥ 48dp，XS 档尤其容易只做到 32dp 高——用 `minimumInteractiveComponentSize()` 补足。
- 读屏顺序：前段 → 后段，代码顺序要与视觉一致。
- 键盘 / 遥控要能分别聚焦两段。

## 常见错误

1. 在 M3 项目里找这个组件。
2. 把后段改成别的动作（官方规定它只能是菜单图标）。
3. XS 档 32dp 高却没有补足 48dp 触控目标。
4. 主操作是删除，误触直接生效。
5. 两段视觉上没连在一起（手写圆角导致中间出现缝）。
6. 变体太多，展开后是个 10 项长菜单。
7. 读屏把两段读成一个整体，用户不知道右边还能展开。

## 待核验

- [x] 完整尺寸表 —— **五档全部取得**（见上表）
- [ ] `container shape` 与 `外圆角` 对应的具体形状 token 名（面板只显示形状预览，不给文字值）
- [ ] 当前 material3 版本的确切 API 名称与签名
- [ ] `@ExperimentalMaterial3ExpressiveApi` 是否已移除

## 怎么自己复查这张表

1. 打开 https://m3.material.io/components/split-button/specs
2. 页面下方 **Tokens & specs** 区块，左上角有一个下拉：`Split button - Size - Xsmall`
3. 点开它，依次选 Small / Medium / Large / Xlarge
4. 每组 12-13 行，**值在左侧的色块里，属性名在右侧**

注意：**不要把整页文本复制出来读**。扁平化后"值"会跑到"属性名"前面一位，看起来像错位——这是 DOM 顺序问题，不是数据问题。要读就读单个 `token-value-swatch` 节点。

## 来源

- https://m3.material.io/components/split-button/specs
- `02-Components/buttons.md`（颜色与状态层通则）
- `02-Components/button-group.md`（另一组 Expressive 按钮能力）

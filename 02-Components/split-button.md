# Split Button / 分裂按钮（Expressive）

- 官方指南：https://m3.material.io/components/split-button ｜ 规格页：https://m3.material.io/components/split-button/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-16
- 核验方式：官方全站文本 dump（overview / specs / guidelines / accessibility 四页）+ 此前 CDP 读 token 面板
- 状态：**M3 无此组件，Expressive 新增**；文字明示值与 token 面板值已交叉核对

## 代际归属

| 变体 | M3 | M3 Expressive |
|---|---|---|
| Split button | ❌ | ✅（Jetpack Compose: Expressive 与 MDC-Android: Expressive 均已实现；Web: Expressive 不可用） |

M3 项目里只能用「按钮 + 紧邻图标按钮」自己拼，但没有连体形状与联动 morph。

## 它解决什么问题

一个主操作 + 一组相关变体（"发送" + "定时发送 / 存草稿"）。官方定位：**在主操作旁加一个动作菜单，隐藏额外选项以降低视觉复杂度**；可单独使用，也可与普通按钮、图标按钮、button group 同排。

## 结构

```
┌──────────────┬─────┐
│ [icon] 标签   │  ▾  │
└──────────────┴─────┘
 Leading button  Trailing button
```

官方规则：**Leading button 可有图标、文字标签或两者**；**Trailing button 必须始终是展开/收起菜单图标**（选中时旋转），**不要改这个图标**。leading 标签保持 1-2 个词。RTL 语言整体镜像。

## 规格

### 配置

- 尺寸：**XS / S（默认）/ M / L / XL**，与 buttons、icon buttons 完全同档。
- 颜色：**elevated / filled / tonal / outlined**——与普通按钮同方案，但**没有 text 样式**。

### 文字明示值（specs 页 Measurements）

| 属性 | XS | S | M | L | XL |
|---|---|---|---|---|---|
| 菜单图标未选中偏移 | -1dp | -1dp | -2dp | -3dp | -6dp |
| 内圆角（相邻侧） | 4dp | 4dp | 4dp | 8dp | 12dp |
| 两段之间的间隙 | 2dp | 2dp | 2dp | 2dp | 2dp |

- 间隙官方原文："The inner corner radius changes depending on button sizing. **The space should always be 2dp**."
- 文字与图标在按钮不对称时做**光学居中**，对称时正常居中。

### token 面板值（此前 CDP 直读，本次 dump 文本未见冲突）

| 属性 | XS | S | M | L | XL |
|---|---|---|---|---|---|
| 容器高度 | 32 | 40 | 56 | 96 | 136 |
| 前段 左/右内边距 | 12 / 10 | 16 / 12 | 24 / 24 | 48 / 48 | 64 / 64 |
| 尾部图标尺寸 | 22 | 22 | 26 | 38 | 50 |
| 后段 左/右内边距 | 13 / 13 | 13 / 13 | 15 / 15 | 29 / 29 | 43 / 43 |
| 内圆角（hover/pressed） | 8 | 12 | 12 | 20 | 20 |
| 后段选中态内圆角 | 50% | 50% | 50% | 50% | 50% |

（高度 32-136dp 与 icon buttons 官方阶梯一致，可互为佐证。）

## 颜色与状态

- 颜色、状态层**复用 buttons / icon buttons 的方案**（官方原文："Split buttons use the same color schemes as standard buttons"）。
- 与 toggle 按钮的关键区别：**选中不改颜色，只叠加状态层**。
- 内侧圆角在 hover / focused / pressed 时变化（形状 morph）；后段选中时图标**居中**并旋转。

## 使用规则（Guidelines 页）

1. 大断点可放大 split button；小屏也可故意用大号制造 hero 时刻。
2. 可与普通按钮、button group 同排；**允许与其它按钮不同尺寸**（split button 本身占位更大）。
3. ✅ 后段默认展开**菜单**；❌ 不要魔改菜单（"Avoid modifying the menu in unusual ways"）；也可自定义为打开卡片等其它组件。
4. **菜单对齐**：优先与 trailing button 对齐；放不下时对齐整条按钮的一侧；再不行贴按钮某条边——**始终与按钮某边缘对齐**。
5. **菜单与 split button 间距 4dp**。
6. 旋转动画：菜单按钮**向内旋转 180°**（开与关都是）；使用**标准 motion 方案**（不是 expressive motion）——官方唯一明说「用标准动效」的按钮族组件。
7. ❌ 不要用超长标签或替换尾部图标。

## 无障碍（Accessibility 页）

- 读屏应能：**分别导航并操作两段**；到达后段打开的元素；理解当前选择状态。
- **触控目标**：两段各自 ≥ 48×48dp；**XS/S 档高度不足 48dp，周围目标区必须补足到至少 48dp 高**（官方原文）。
- **初始焦点**：先 leading，后 trailing（受系统设置影响）。
- **键盘**：Tab 在两段间移动；Space / Enter 激活聚焦段。
- **标签**：leading 段与普通按钮一致；**trailing 段要额外表达「有更多选项」及菜单展开状态**——例：主按钮 "Watch later"，trailing 段标签类似 "More watch options"。
- 打开的菜单按 menu 无障碍指南标注。

## Compose 实现

Expressive 新增组件（官方确认 Compose: Expressive 可用），对应 `androidx.compose.material3` 的 `SplitButtonLayout` / `SplitButtonDefaults` 一族 API。**需 `@OptIn(ExperimentalMaterial3ExpressiveApi::class)`，名称与签名随版本变动**，接入前核对：

1. `SplitButtonLayout`（leading/trailing slot）是否存在；
2. 形状是否由 `SplitButtonDefaults.itemShape` 一类的辅助函数生成（避免手写圆角出缝）；
3. 间距 2dp / 菜单 4dp / 触控补足是否由组件默认处理。

## 常见错误

1. 在 M3 项目里找这个组件。
2. 把后段改成别的动作（官方规定只能是菜单图标）。
3. XS 档 32dp 高没补足 48dp 触控目标。
4. 菜单飘在离按钮很远的地方（官方：4dp）。
5. 选中态改了容器颜色（官方：split button 选中只加状态层）。
6. 两段视觉上没连在一起（手写圆角导致中间出缝）。
7. 读屏把两段读成一个整体，或 trailing 段没表达「更多选项」。
8. 在 split button 里用 text 颜色样式（不存在）。

## 来源

- https://m3.material.io/components/split-button/overview（代际、配置、平台可用性）
- https://m3.material.io/components/split-button/specs（文字明示值、颜色复用规则、状态）
- https://m3.material.io/components/split-button/guidelines（菜单对齐、4dp 间距、180° 旋转）
- https://m3.material.io/components/split-button/accessibility（48dp、焦点顺序、标签规范）
- 核验日期：2026-11-16
- 关联：`02-Components/buttons.md`（颜色与状态层）、`02-Components/icon-buttons.md`（尺寸阶梯）

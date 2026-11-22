# Desktop / 桌面形态

> 官方指南：[Overview](https://m3.material.io/foundations/desktop/overview) ｜ [Layout](https://m3.material.io/foundations/desktop/layout) ｜ [Components](https://m3.material.io/foundations/desktop/components) ｜ [Interaction](https://m3.material.io/foundations/desktop/interaction)
> 核验日期：2026-11-22
> 核验方式：headless Chrome 渲染官方四页取正文（**2026-11-16 全站抓取时这 4 页尚不存在**，sitemap 从 375 增至 385 页）
> 适用范围：Android 应用的桌面形态——Chromebook / Googlebook、平板接扩展屏或 folio 键盘、手机接外接显示器

## 一句话结论

桌面不是「放大版的手机」：**用扩展空间增加信息密度，而不是把元素拉满**；导航换 rail、底部面板换 side sheet / 右键菜单、所有主要操作补指针与键盘路径。

## 官方设计原则（Overview 原文）

1. **People can use any Android app in a desktop environment by opening it on a Chromebook or Googlebook, docking a tablet to a folio, or connecting a phone to an external monitor** —— 桌面形态不是新平台，是同一 app 的新窗口尺寸/输入组合。
2. **Design apps so they're easy to use on desktop, rather than feeling like a stretched mobile app.**
3. **Take advantage of the expanded space and longer viewing distances, and consider denser layouts that show more information at once.**
4. **Use adaptive components and layouts so apps scale smoothly across breakpoints**（breakpoints 即旧称 window size classes）。
5. **Support external devices for efficient navigation across every input type, including mice, trackpads, and physical keyboards.**

## 布局（Layout 页）

- 桌面 app 通常从 **medium / large** 断点打开，可扩到 **extra-large**。
- **优先多栏 / 多窗格**（medium、large、extra-large）；只有一栏时必须**限宽并居中**，否则就是「拉长的手机 UI」。
- 推荐从 Material canonical layouts 起步：**Feed**（长滚动内容）、**List-detail**（聊天/邮件）、**Supporting pane**（工具/辅助信息）。
- 组件随断点适配：medium 及以上把 **navigation bar 换成 navigation rail**；extra-large 可默认展开 rail 以提升标签可读性。

## 组件（Components 页）

| 移动端做法 | 桌面上应改成 | 原因（原文要点） |
|---|---|---|
| 通栏按钮 / 通栏文本框 | **保持默认宽高与内边距**，不要拉伸填满窗格 | 大屏上通栏控件「can feel imprecise」 |
| Navigation bar | 换 **Navigation rail**（medium+；extra-large 默认展开） | 横向空间足够，纵向更省 |
| Bottom sheet | **Context menu / Side sheet / Dialog** | 桌面鼠标移动距离长，底部面板还会挡住主内容；若必须用 bottom sheet，**必须脱离窗口底边** |
| 长按 / 溢出触发的面板 | **右键 context menu** | 桌面主要用 secondary click |
| 复杂工具 / 配置面板 | **无 scrim 的 side sheet**，与主内容并排 | 不遮挡主内容 |

## 交互（Interaction 页）

**基础要求**：Provide single-click controls · Support keyboard shortcuts · Show clear hover and focus states。

### 拖放（Drag & drop）

- 开始拖动：应用 **dragged state** + 提升 elevation，把被拖项做**半透明**，让下方投放目标可见。
- 悬停在合法目标上：用**边框或插入指示线**高亮。
- 非法目标：显示 **prohibited cursor**；放错位置要回到原位或视觉消散。
- **指针拖动必须即时**（不同于触摸的长按再拖）。
- 所有拖放动作都要有**单击替代方案**（例如 + 图标按钮）。

### 缩放与平移

- 提供多种缩放路径：键盘快捷键、滚动、双指捏合、专用 **+ / −** 按钮。
- Zoom-first 内容（地图）：滚动即缩放，缩放后可按住拖动平移。
- Pan-first 内容（画布）：按住 Ctrl/Cmd 再滚动缩放；滚动垂直平移、Shift+滚动水平平移、触控板双指滑动。
- 单一指针用户必须能用 **+ / − 按钮**完成缩放。

### 次要操作与多选

- 桌面可用**右键 context menu** 收起次要操作，减少视觉复杂度。
- 多选：桌面常用 **Shift 连选 / Ctrl（Cmd）点选**；配合批量操作条。
- 刷新：提供明确刷新入口（工具栏图标 / 快捷键），不要只依赖下拉刷新。

## 平台落地（Android 侧）

- **窗口尺寸**：用 breakpoints（App 侧仍是 `WindowSizeClass` / `calculateWindowSizeClass`），不要按设备型号写死。
- **输入**：Compose 用 `Modifier.pointerHoverIcon` 切换光标、`onKeyEvent` 处理快捷键、`focusable` + focus 状态给键盘焦点。
- **桌面窗口**：检查 `WindowInfo` / 多窗口与可调整窗口支持；具体 API 见 Android Developers「Support desktop windowing」。
- **大屏/桌面 ≠ 平板**：平板横屏通常落在 expanded（840–1199dp），桌面/laptop 落在 large（1200–1599dp），外接超宽屏是 extra-large（1600dp+）。

## 常见错误

1. 把手机布局整体等比放大／通栏拉伸——官方第一条红线。
2. 桌面仍用 bottom sheet 承载主要操作，且贴着窗口底边。
3. 只保留触摸手势，没有 hover / focus / 右键路径。
4. 拖放没有单击替代，键盘用户无法完成同一任务。
5. 单栏内容不限宽，行长超过约 70 字符后不可读。
6. 把「平板 = 桌面」混为一谈：断点相同，但输入方式与窗口行为不同。

## 来源

- [Desktop · Overview](https://m3.material.io/foundations/desktop/overview)（设计原则、可用资源）
- [Desktop · Layout](https://m3.material.io/foundations/desktop/layout)（多窗格、限宽、canonical layouts）
- [Desktop · Components](https://m3.material.io/foundations/desktop/components)（rail、side sheet、context menu、bottom sheet 替换）
- [Desktop · Interaction](https://m3.material.io/foundations/desktop/interaction)（拖放、缩放平移、次要操作、多选、刷新）
- [Breakpoints](https://m3.material.io/foundations/layout/breakpoints/overview)（五档 dp 边界，旧称 window size class）

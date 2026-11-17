# Tabs 试点记录（阶段 2 · P0 第一批）

> 日期：2026-11-17　范围：`M3Tabs`　结论：**4/4 参数落地**；Primary / Secondary × 固定 / 可滚动 × 三种布局，真机切换已验证。

## 一、实现

| 参数（Kit） | 实现 |
|---|---|
| Configuration（Fixed / Scrollable） | `M3TabConfiguration.FIXED` / `SCROLLABLE`，可滚动时前缘留 52dp |
| Style（Primary / Secondary） | `M3TabStyle`，分别映射到 `PrimaryTabRow` / `SecondaryTabRow` |
| Layout（Label only / Icon+label / Icon only） | `M3TabLayout`，图标 24dp、图标+文字高度 64dp、仅文字 48dp |
| Tab 内容 | `M3TabItem(label, icon, enabled, showBadge)` + `selectedIndex` + `onSelect` |

底层用 Material3 原生 `PrimaryTabRow` / `SecondaryTabRow` / `PrimaryScrollableTabRow` / `SecondaryScrollableTabRow`，指示器、分隔线、状态层与无障碍由官方实现。

## 二、官方尺寸（已锁进单测）

| 项 | 值 |
|---|---|
| 仅文字高度 | 48dp |
| 图标+文字高度 | 64dp |
| 图标 | 24dp |
| 分隔线 | 1dp |
| Primary 指示器 | 3dp |
| Secondary 指示器 | 2dp |
| 指示器内缩 / 最短长度 | 2dp / 24dp |
| 可滚动前缘留白 | 52dp |
| 小圆点 Badge | 6dp；文字后间距 4dp；图标右上偏移 6dp |

常量在 [M3TabTokens.kt](<app/src/main/java/com/example/m3appbar/ui/M3TabTokens.kt>)，单测 [M3TabMetricsTest.kt](<app/src/test/java/com/example/m3appbar/ui/M3TabMetricsTest.kt>)。

## 三、Badge（2026-11-17 补）

- 范围：Kit 的 Tabs 子变体只有 `Show badge` 布尔，因此只做 **6dp 小圆点**，不做数量/大 badge。
- 位置：
  - 图标 tab → 叠在图标右上角（`BadgedBox`，官方 small badge 偏移 6dp）；
  - Label only → 文字 trailing，间距 4dp。
- 颜色：`error` / `onError`（Compose `Badge` 默认）。
- 语义：小圆点带 `contentDescription = "新通知"`，uiautomator 实测 16×16px（≈6dp），共 2 个。
- 选中不隐藏（Kit 的 Show badge 与 Selected 是独立轴；导航栏的“选中后隐藏”规则不套用到 tabs）。

![补 Badge 之前](<screenshots/tabs-before-badge.png>)

![补 Badge 之后：Usage 文字后、Settings 图标右上](<screenshots/tabs.png>)

## 四、真机验证

![Tabs：Primary 固定 / Primary 图标+文字 / Secondary / 可滚动](<screenshots/tabs.png>)

核对结果：

| 项 | 结果 |
|---|---|
| Primary 三 tab，Overview 初始选中 | ✅ |
| 点击 Specs 后指示器与文字颜色切换 | ✅ |
| Primary 图标+文字（Home/Search/Settings），高度 64dp | ✅ |
| Secondary（Inbox/Archive），指示器更细 | ✅ |
| 可滚动（One…Six），首 tab 前缘留白 | ✅ |
| 选中/未选中颜色与分隔线 | ✅ |

## 五、验证命令

| 项 | 结果 |
|---|---|
| `:app:compileDebugKotlin` | 0 error |
| `:app:testDebugUnitTest` | **9 tests / 0 failures** |
| `:app:assembleDebug` | 通过 |
| 真机 `adb install` | Success |

## 六、参数落地

| 组件 | 参数 | 落地 | 说明 |
|---|---|---|---|
| Tabs | Configuration / Style / Layout | **4/4** | 三种布局、两种样式、两种配置均可组合 |

## 七、已知限制

1. **Secondary 不支持图标**（官方无图标槽位），传入 icon 会被忽略；API 不会报错。
2. Badge 只做 **6dp 小圆点**；未做数量/大 badge（Kit 未给该轴）。
3. 未实现 Tab 与内容区的 **HorizontalPager 联动**，只做 tab 本身。
4. 固定 tabs 的“最多 4 个”是使用规范，不由组件强制；调用方自行控制。
5. 未做 RTL / 密度 / pixel diff。
6. Badge 的选中后隐藏规则未做（按 Kit 的独立轴处理）。

## 八、下一步

按 P0 顺序：**Navigation**（bar / rail / drawer / adaptive 可选），之后 Menu → Dialog → Snackbar → Search。

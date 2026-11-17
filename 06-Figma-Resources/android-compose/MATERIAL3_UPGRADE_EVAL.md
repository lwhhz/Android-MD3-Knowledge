# Material3 升级评估（为公开 Carousel keyline API）

> 日期：2026-11-18　问题：是否升级 material3 以获得官方自定义 Carousel/keyline API　状态：**仅评估，未改依赖**

## 一、结论先行

**不建议为 keyline 升级，因为当前没有可升级的目标版本。**

- 项目当前：`material3 1.5.0-rc01`（compose-bom-alpha 2026.11.00）；
- 官方发布页（最后更新 2026-11-04）：**Stable 1.4.0 / RC 1.5.0-rc01 / 无 beta / 无 alpha**；
- 也就是说 1.5.0-rc01 已是可用的最新候选；不存在 1.6.0 或更高版本可拿公开 keyline；
- 1.5.0 stable 尚未发布，且即使发布，RC 中 `Carousel`/`keylineListOf`/`KeylineListScope` 仍是 internal，**没有证据表明 stable 会公开**。

## 二、已核实的 API 事实

| 项 | 结果 |
|---|---|
| `HorizontalMultiBrowseCarousel` / `HorizontalUncontainedCarousel` / `HorizontalCenteredHeroCarousel` | public，已用 |
| `Carousel(...)` 自定义容器 | Kotlin **internal**（编译报 “it is internal in file”） |
| `keylineListOf(...)` / `KeylineListScope` / `Keyline` | Kotlin **internal**，应用层不可调用 |
| 版本 | 1.5.0-rc01 为最新 RC；stable 仅 1.4.0 |

证据：本地 `material3-android-1.5.0-rc01` classes.jar + 项目编译错误；官方发布页版本表。

## 三、可选方案

| 方案 | 内容 | 成本 | 风险 | 建议 |
|---|---|---|---|---|
| **A** | 保持 1.5.0-rc01，MULTI_ASPECT 用 LazyRow + snap 降级 | 0 | 该布局无官方 parallax/mask 动效 | **推荐（短期）** |
| B | 等 1.6.0 alpha/stable 再评估 | 等待 | 无 ETA；可能仍 internal | 监控 |
| C | 升级到 stable 1.4.0 | 中 | 实为降级，失去 Expressive 组件 | 不推荐 |
| D | 复制/改造 AndroidX internal keyline 代码 | 高 | Apache-2.0 允许但维护成本高、偏离“官方组件” | 不推荐 |
| E | 反射调用 internal API | 中 | 版本脆弱、R8/Compose 编译器风险 | 禁止 |

## 四、若未来升级的评估清单

1. 目标版本是否 **public** 暴露 `Carousel`/`keylineListOf`/`KeylineListScope`（以编译验证，不只看文档）；
2. 是否仍有 `HorizontalMultiBrowseCarousel` 等签名变化；
3. 23 个单测与全部组件是否仍编译（尤其 Toolbar/ButtonGroup/Carousel）；
4. AGP/Kotlin/compileSdk 兼容（当前 AGP 9.4.1、Kotlin 2.3.21、compileSdk 37）；
5. 离线依赖缓存是否具备目标版本（当前构建 `--offline`）；
6. 许可证仍 Apache-2.0（AndroidX）。

## 五、对当前交付的影响

- Carousel MULTI_ASPECT 保持 **LazyRow + snap**，逐项不同宽度已可见；
- 其他布局仍用官方组件（snap/parallax/maskClip）；
- 不升级不会阻塞 Date/time pickers 等后续 P1 工作。

## 六、待用户决策

1. 接受方案 A（保持版本、MULTI_ASPECT 降级）？
2. 还是先暂停 Carousel，等未来 material3 公开 keyline 再补？

## 七、来源

- https://developer.android.com/jetpack/androidx/releases/compose-material3（版本表，2026-11-04）
- 本地 material3 1.5.0-rc01 `classes.jar` / Kotlin 编译验证
- 项目 [CAROUSEL_TRIAL.md](<CAROUSEL_TRIAL.md>)
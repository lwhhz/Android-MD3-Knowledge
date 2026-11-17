# Carousel 试点记录（P1 · 第六批）

> 日期：2026-11-18　范围：官方 carousel + 图片内容 + MULTI_ASPECT　结论：**图片完成；MULTI_ASPECT 因官方 keyline API 为 internal，降级为 LazyRow + snap**。

## 一、实现

| 项 | 实现 |
|---|---|
| MULTI_BROWSE / HERO | 官方 `HorizontalMultiBrowseCarousel`（280dp preferred，small 40–56dp） |
| CENTER_ALIGNED_HERO | 官方 `HorizontalCenteredHeroCarousel` |
| UNCONTAINED | 官方 `HorizontalUncontainedCarousel`（240dp） |
| FULL_SCREEN | 官方 Uncontained，itemWidth = 容器宽、间距 16dp、直角 |
| **MULTI_ASPECT** | **LazyRow + `rememberSnapFlingBehavior`**，固定宽度序列 160/120/200/140dp |
| 图片 | `M3CarouselItem.image: Painter?` + `contentDescription`；`Image` + Crop；矢量示例图 3 张 |
| 标签 | 按 `CarouselItemDrawInfo.size` 动态显示，>56dp 才显示 |

## 二、真机验证

![Carousel 图片：multi-browse / hero / full-screen](<screenshots/carousel.png>)

![MULTI_ASPECT：160 / 120 / 200dp 变宽条目](<screenshots/carousel-multi-aspect.png>)

| 项 | 结果 |
|---|---|
| 矢量图片 + 内容描述 | ✅ |
| 大项显示标签、小项隐藏 | ✅ |
| MULTI_ASPECT 逐项不同宽度 | ✅（LazyRow 实现） |
| 官方布局滑动/吸附 | ✅（MULTI_ASPECT 除外） |

## 三、验证命令

| 项 | 结果 |
|---|---|
| :app:compileDebugKotlin | 0 error |
| :app:testDebugUnitTest | **23 tests / 0 failures** |
| :app:assembleDebug | 通过 |
| 真机 adb install | Success |

## 四、参数落地

| 组件 | 参数 | 落地 |
|---|---|---|
| Carousel | Context(2) / Layout(5) | **7/9**（缺显式 Show 4th/5th 布尔） |
| Full screen | 内容/布局 | **2/2** |

图片与 contentDescription 为额外能力，未计入 Figma 参数。

## 五、关键限制（API 事实）

1. **官方自定义 `Carousel` / `keylineListOf` / `KeylineListScope` 在 material3 1.5.0-rc01 中是 internal**，应用层无法调用；因此无法用官方 keyline 做真正逐项宽高比。
2. MULTI_ASPECT 的 LazyRow 方案有 snap，但**没有官方 parallax、动态缩放、maskClip 动画**。
3. FULL_SCREEN 仍是 Uncontained 近似；HERO 复用 MultiBrowse。
4. 图片为本地矢量图，非真实照片；Show all 仍无全量页面。
5. 减少动态效果、RTL、键盘、pixel diff 未验证。

## 六、待决策

1. 接受 MULTI_ASPECT 的 LazyRow 降级，还是改回官方固定宽度？
2. 若必须官方 keyline，需要升级 material3 到 keyline API 公开的版本（需验证）。

## 七、下一步

P1 剩余：**Date / time pickers**。
# Slider 三档试点记录（阶段 1 · 第六批）

> 日期：2026-11-17　范围：`M3StandardSlider` / `M3CenteredSlider` / `M3RangeSlider`　结论：**三档全部实现并可交互**；参数 7/8、5/6、5/6（垂直方向未做）。

## 一、实现方式

自绘 Canvas + `BoxWithConstraints`（宽度确定）+ 指针手势，不依赖 Material3 Slider 的外观：

- 轨道：16/24/40/56/96dp，圆角 8/8/12/16/28dp；
- handle：高 44/44/52/68/108dp、宽 **4dp**（按下收窄到 3dp）；
- active track = `primary`，inactive = `secondaryContainer`，value indicator = `inverseSurface/onInverseSurface`；
- **value indicator**：拖动时显示 44×48dp 气泡（文字 14sp/Medium）；
- **stops**：按 `steps` 画 2dp 圆点，位置内缩 4dp；
- **centered**：active 段从中心向 handle 填充；
- **range**：双 handle，拖拽时自动排序；
- 图标：仅 M/L/XL 显示 inset icon（24/24/32dp）；
- 无障碍：单值映射 `progressBarRangeInfo`，range 用 `stateDescription`。

尺寸常量在 [M3SliderTokens.kt](<app/src/main/java/com/example/m3appbar/ui/M3SliderTokens.kt>)，并有单测 [M3SliderTokensTest.kt](<app/src/test/java/com/example/m3appbar/ui/M3SliderTokensTest.kt>)。

## 二、参数落地

| 组件 | 参数 | 落地 | 说明 |
|---|---|---|---|
| Standard（8） | Orientation / Size / State / Value / Show value indicator / Show stops / Show icon / Icon | 7/8 | **垂直方向未实现**；其余生效 |
| Centered（6） | Orientation / Size / State / Value / Show value indicator / Show stops | 5/6 | 垂直方向未实现；中心填充已实现 |
| Range（6） | Orientation / Size / State / Value / Show value indicator / Show stops | 5/6 | 垂直方向未实现 |

## 三、真机验证

![Slider 三档：standard（M，含 inset icon）/ centered / range + XS/L 尺寸对照](<screenshots/sliders.png>)

![拖动时的 value indicator](<screenshots/slider-value-indicator.png>)

核对结果：

| 项 | 结果 |
|---|---|
| 三档尺寸（XS 16dp 轨道 → L 56dp）可见 | ✅ |
| standard 50%：active 填充 + handle + 终点停靠点 + inset icon | ✅ |
| centered 值 0：从中心起无填充，handle 居中 | ✅ |
| range：两 handle、区间填充、拖动后自动排序 | ✅ |
| 点击轨道跳值（standard 在 50% 停靠点附近会吸附） | ✅ |
| 拖动时 44×48dp 数值气泡 | ✅（截图 “50”） |
| 按下 handle 宽度 4→3dp | ✅（代码；截图不明显） |
| disabled 颜色 | ✅（代码，未截图） |

## 四、验证命令

| 项 | 结果 |
|---|---|
| `:app:compileDebugKotlin` | 0 error |
| `:app:testDebugUnitTest` | **7 tests / 0 failures** |
| `:app:assembleDebug` | 通过 |
| 真机 `adb install` | Success |

## 五、已知限制

1. **垂直方向未实现**（Expressive 独有），所以三档的 Orientation 参数只支持 Horizontal。
2. **Hover / Focus 状态没有单独绘制**：目前只有 enabled / pressed / disabled；hover/focus 的 handle 状态层未做。
3. Range 的 value indicator 固定显示第一个 handle 的值（官方要求一次只显示一个）。
4. inset icon 只做了固定图标，没有“零值换图标”逻辑。
5. 手势为自绘实现，无 TalkBack 增量调节键（方向键）支持；只提供 progressBar 语义。
6. 尚无 pixel diff。

## 六、下一步

按计划是 **Chip**（5 个变体：Assist / Filter / Input / Suggestion / Chip group），之后进入 P1 组件（Carousel / Date picker / Sheets 等）。

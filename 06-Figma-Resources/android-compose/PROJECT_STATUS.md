# 项目状态收口（2026-11-18）

> 决策：**接受当前人工 Pixel 基线；XR 不再推进**。本文件作为当前交付状态的单一入口。

## 一、交付范围

| 范围 | 状态 |
|---|---|
| P0 组件 | 已实现并回归；参数 173/315 |
| P1 七个组件族 | Progress、Toolbar、Bottom sheet、Segmented、Connected、Carousel、Date/Time pickers；参数 147/180 |
| P2 非 XR 组件 | Tooltip、Avatar、Shape、Shape Set、Keyboard、Layout grid、Extended FAB、.Shape；参数 18/43 |
| XR 家族 | **明确不推进**（Out of scope） |

## 二、最终统计

| 优先级 | 参数 | 已落地 | 未落地 | 落地率 |
|---|---:|---:|---:|---:|
| P0 | 315 | 173 | 142 | 54.9% |
| P1 | 180 | 147 | 33 | 81.7% |
| P2 | 43 | 18 | 25 | 41.9% |
| **合计** | **538** | **338** | **200** | **62.8%** |

P2 未落地的 25 条全部是 XR 组件，按本轮决策不计入后续计划。

## 三、质量状态

| 项 | 当前状态 |
|---|---|
| 单测 | **30 tests / 0 failures** |
| 构建/安装 | assembleDebug 通过；真机 e91fb0f 已安装 |
| Pixel 基线 | 人工基线 6 张（P1/P2），自检 0%；**未自动化** |
| RTL | 单 demo 行 smoke，通过 |
| 密度 | 320 / 480dpi 渲染通过，恢复 420dpi |
| 采集脚本 | `pixel_capture.py` 为草稿，uiautomator 挂起，未通过端到端验证 |

## 四、明确不做 / 不再推进

1. **XR 家族**：不引入 androidx.xr，不做 App Bar/Dialog/Navigation/Toolbar。
2. **Figma 像素级还原**：当前 pixel 基线是“当前实现锚点”，不是设计稿对比。
3. **完整 RTL/多语言矩阵**：只保留一个 RTL demo 行。
4. **完整密度/字体缩放矩阵**：只测 320/480/420。
5. **CI 级 Pixel 自动化**：采集脚本未验收，不纳入 CI。

## 五、已知限制（交付说明必须保留）

1. Date picker 的 MONTH/YEAR、全屏 Range、清除按钮未做；
2. Toolbar docked+vertical 回落 horizontal，滚动隐藏未验证；
3. Bottom sheet 640dp 无平板视觉回归；
4. Carousel MULTI_ASPECT 因官方 keyline API internal 用 LazyRow 降级；
5. Connected button group 未用官方 ButtonGroup 容器，无 overflow/宽度动画；
6. Keyboard 是视觉 mock，不提供输入；
7. Tooltip 只验证按住显示，1.5 秒消失未验证；
8. Extended FAB focus 边框未做真机 Tab 截图。

## 六、相关文档

- 参数总表：[IMPLEMENTATION_GAPS.md](<IMPLEMENTATION_GAPS.md>)
- P0 回归：[P0_REGRESSION.md](<P0_REGRESSION.md>)
- P1 回归：[P1_REGRESSION.md](<P1_REGRESSION.md>)
- P2 回归：[P2_REGRESSION.md](<P2_REGRESSION.md>)
- 工程收尾：[ENGINEERING_WRAP.md](<ENGINEERING_WRAP.md>)
- Pixel 基线：[PIXEL_DIFF.md](<PIXEL_DIFF.md>)
- 组件与 KB 映射：[KB_COMPONENT_MAP.md](<KB_COMPONENT_MAP.md>)
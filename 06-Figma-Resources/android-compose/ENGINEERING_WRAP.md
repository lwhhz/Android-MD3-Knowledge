# 工程收尾：Pixel 基线 / RTL / 密度（2026-11-18）

> 范围：工程性验证，不新增组件　结论：**接受人工 Pixel 基线；RTL 与 320/480dpi 均能渲染**，但都不是完整矩阵。

## 一、构建与单测

| 项 | 结果 |
|---|---|
| :app:testDebugUnitTest | **30 tests / 0 failures** |
| :app:assembleDebug | 通过 |
| 真机 install | Success |

## 二、Pixel 基线（P1/P2）

目录：

| 目录 | 内容 |
|---|---|
| `pixel-baseline-p1p2/` | carousel、connected-button-group、p2-primitives、p2-keyboard-grid、date-picker-modal、rtl-smoke |
| `pixel-current-p1p2/` | 本轮同图拷贝，用于工具自检 |

命令：

```bash
java PixelDiff.java pixel-baseline-p1p2 pixel-current-p1p2 8 0.01 pixel-report-p1p2.txt 90 40
```

结果：

```text
PASS carousel.png diff=0/2451600 ratio=0.0000%
PASS connected-button-group.png ...
PASS date-picker-modal.png ...
PASS p2-keyboard-grid.png ...
PASS p2-primitives.png ...
PASS rtl-smoke.png ...
threshold=1.00% failed=0
```

⚠️ 这是**基线→current 自检**，不是对最新 APK 的重新截图对比；基线来自当前构建截图，属于“视觉锚点”，不是 Figma 还原度。

## 三、RTL

新增 demo 分区 `RTL smoke · layout direction`：用 `CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl)` 包住 Button / OutlinedTextField / IconButton。

![RTL smoke：按钮在右、图标在左、输入框内容右对齐](<screenshots/rtl-smoke.png>)

| 项 | 结果 |
|---|---|
| RTL 行排列镜像 | ✅ |
| 阿拉伯文输入右对齐 | ✅ |
| 仅覆盖三个控件 | 限制 |

## 四、密度

设备：1080×2400，默认 `wm density 420`；测试 320 / 480 后恢复 420。

| 密度 | 结果 | 截图 |
|---|---|---|
| 320dpi | App 正常渲染、MainActivity 前台 | `screenshots/density-320.png` |
| 480dpi | App 正常渲染（首张截图抓拍过早为空白，延长等待后正常） | `screenshots/density-480.png` |
| 420dpi（恢复） | 恢复成功 | — |

## 五、采集脚本（未验证）

已提交 [pixel_capture.py](<pixel_capture.py>)：adb + uiautomator 按标题滚动截图。

**但端到端验证未通过**：本机 uiautomator 在多次 dump 后会挂起，脚本无法稳定产出全部 6 张；当前 pixel-baseline-p1p2/ 仍以人工截图为准。脚本不建议直接用于 CI，需后续修超时/锁问题。

## 六、已知限制

1. Pixel 基线仍以人工截图为准；采集脚本未通过验证。
2. P0 六类的最新构建视觉对比仍未重新采集。
3. RTL 只验证一个 demo 行，未切系统 locale，也未覆盖 Navigation/Toolbar/Carousel 等复杂组件。
4. 密度只验证 320/480，未做 560/大屏/字体缩放组合。
5. 未做 diff 高亮图、无障碍读屏、键盘焦点矩阵。

## 七、下一步

1. XR 可行性评估；
2. 或把 Pixel/P1P2 采集脚本化，补 P0 最新截图；
3. 或做系统 locale RTL 与字体缩放矩阵。
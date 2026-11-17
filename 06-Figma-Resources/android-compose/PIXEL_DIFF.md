# Pixel Diff 基线（2026-11-18）

> 目标：为 P0 六类建立**当前构建基线**，后续构建做视觉回归；不是与 Figma 设计稿的还原度对比。

## 一、基线文件

| 基线 | 来源 |
|---|---|
| `pixel-baseline/tabs.png` | `screenshots/tabs.png` |
| `pixel-baseline/navigation.png` | `screenshots/navigation.png` |
| `pixel-baseline/menu.png` | `screenshots/menu.png` |
| `pixel-baseline/dialog.png` | `screenshots/dialog-basic.png` |
| `pixel-baseline/snackbar.png` | `screenshots/snackbar-auto-dismiss.png` |
| `pixel-baseline/search.png` | `screenshots/search-fullscreen.png`（本次状态化全屏） |

分辨率统一 1080×2400。tabs/navigation/menu/dialog/snackbar 取自各自试点构建；这些组件在本次 Search 改动中未改代码。Search 基线来自当前构建。

## 二、运行方式

1. 把新构建的六张截图按同名放进 `pixel-current/`：
   - `adb exec-out screencap -p > pixel-current/tabs.png` 等；
2. 运行：

```bash
java PixelDiff.java pixel-baseline pixel-current 8 0.01 pixel-report.txt 90 40
```

参数：

| 参数 | 含义 |
|---|---|
| `pixel-baseline` / `pixel-current` | 基线目录 / 当前截图目录 |
| `8` | 每通道容差（0–255） |
| `0.01` | 最大差异像素比例（1%） |
| `pixel-report.txt` | 报告路径 |
| `90` / `40` | 忽略顶部 90px（状态栏）与底部 40px（手势区） |

退出码：0 = 全部通过；1 = 有文件超阈值。

## 三、验证结果

```text
PASS  dialog.png  diff=0/2451600  ratio=0.0000%
PASS  menu.png  diff=0/2451600  ratio=0.0000%
PASS  navigation.png  diff=0/2451600  ratio=0.0000%
PASS  search.png  diff=0/2451600  ratio=0.0000%
PASS  snackbar.png  diff=0/2451600  ratio=0.0000%
PASS  tabs.png  diff=0/2451600  ratio=0.0000%
threshold=1.00%  tolerance=8/channel  failed=0
```

负向测试：把 `pixel-current/search.png` 换成折叠态截图，结果为 `FAIL ratio=88.7716%`，退出码 1，说明检测生效。

## 四、局限

1. **不是 Figma 还原度**：基线是当前实现，不是设计稿；
2. 动态内容（时钟、光标闪烁、输入法）可能造成差异，已裁掉状态栏/手势区，但输入框光标仍需注意；
3. 设备/密度/主题变化会使整图失配，需按同一设备重新建基线；
4. 1% 阈值是经验值，未对每个组件单独调参；
5. 尚未生成 diff 高亮图（只有比率与计数）。

## 五、P1/P2 基线（2026-11-18）

- `pixel-baseline-p1p2/`：carousel、connected-button-group、p2-primitives、p2-keyboard-grid、date-picker-modal、rtl-smoke；
- 运行：`java PixelDiff.java pixel-baseline-p1p2 pixel-current-p1p2 8 0.01 pixel-report-p1p2.txt 90 40`；
- 当前为基线→current 自检 0%，不是重新截图回归。

## 六、后续

- P1 组件开始前，可把本套脚本扩展到新组件；
- 若要对比 Figma，需要另建节点截图与对齐流程。
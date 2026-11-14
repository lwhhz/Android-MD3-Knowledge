# Bottom Sheet / 底部弹层

- 官方指南：https://m3.material.io/components/bottom-sheets ｜ 规格页：https://m3.material.io/components/bottom-sheets/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-15 ｜ 核验方式：Chrome CDP 读官方 Specs 页
- 状态：**尺寸测量全部为官方原值**

## 用途

Bottom sheet 从屏幕底部升起，承载**与当前上下文相关的补充内容或操作**，不离开当前页面。

它夹在对话框和页面之间：

| | Dialog | Bottom sheet | 新页面 |
|---|---|---|---|
| 打断程度 | 强 | 中 | 无 |
| 适合内容量 | 1-2 句 + 2 个按钮 | 一屏内的列表 / 表单 | 任意 |
| 典型场景 | 不可逆确认 | 筛选、分享、选择器、详情预览 | 完整流程 |

**判断标准**：内容超过一屏、或者用户要在里面走多步，就别用 sheet，用页面。

## 两种形态

| 形态 | 行为 | Compose |
|---|---|---|
| **Modal** | 有遮罩，必须处理或关闭才能继续，可拖拽关闭 | `ModalBottomSheet` |
| **Standard** | 与内容共存，可一直停在那儿（如地图上的列表） | `BottomSheetScaffold` |

绝大多数场景用 Modal。Standard 只在"底部内容与主内容需要同时可交互"时用。

## 尺寸（官方 Measurements 原值）

| 属性 | 值 |
|---|---|
| 宽度 | 撑满窗口，**最大 640dp** |
| 窗口宽度 > 640dp 时 | 加**上边距 56dp**、**左右边距 56dp** |
| 默认上边距 | **72dp** |
| 高度 | 可变（按内容） |
| 拖拽手柄水平对齐 | 居中 |
| 拖拽手柄上下内边距 | **22dp** |

也就是说：**手机上贴边全宽，平板/折叠屏展开后变成一块悬浮的圆角面板**（左右各留 56dp）。这是它和 Dialog 在响应式上的关键差异——写死全宽会在平板上很丑。

## 结构

```
        ▬▬▬            ← 拖拽手柄（居中，上下 22dp）
  标题
  内容（列表 / 表单 / 操作）
  ...
```

- 拖拽手柄是**可关闭的视觉暗示**，有它用户才知道能往下拖。
- 标题可选；内容复杂时建议加，读屏也会用它作为上下文。
- 底部的操作按钮要避开导航栏（`navigationBarsPadding`）。

## Compose 实现

```kotlin
val sheetState = rememberModalBottomSheetState(
    skipPartiallyExpanded = false
)

if (showSheet) {
    ModalBottomSheet(
        onDismissRequest = { showSheet = false },
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            Text("筛选", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))
            FilterContent()
        }
    }
}
```

- `skipPartiallyExpanded = false`：允许"半展开"中间态，内容较长时体验更好；内容很短时设 `true` 直接全展开。
- 关闭要走 `sheetState.hide()` 再改状态，直接用 `showSheet = false` 会跳过收起动画。
- `ModalBottomSheet` 在部分 material3 版本里仍是 `@ExperimentalMaterial3Api`，用前确认注解状态。

## 交互规则

- **返回键 = 关闭 sheet**，不是退出页面。
- 点遮罩关闭：可撤销的操作可以；有未保存输入时不要。
- **拖拽关闭是预期行为**，不要禁用（这是 sheet 的核心手势）。
- 屏幕旋转 / 配置变更后 sheet 状态不应丢失。
- sheet 里不要再弹 sheet（嵌套弹层会让用户迷失）。

## 无障碍

- sheet 打开时**焦点必须进入 sheet**，关闭后回到触发元素。
- 拖拽手柄除了视觉，还要有可访问的关闭方式（返回键、关闭按钮），读屏用户无法"拖"。
- 标题用 `titleLarge` 做视觉即可，语义上读屏会顺序读到内容。
- 底部按钮要避开手势条，否则点不到。

## 常见错误

1. 平板上 bottom sheet 拉满全宽（超过 640dp 应留 56dp 边距）。
2. 内容超过一屏还硬塞，内部滚动与外层拖拽冲突。
3. 用 sheet 装长表单（应该用页面）。
4. 没有拖拽手柄，用户不知道能关。
5. `showSheet = false` 直接切换，没有收起动画。
6. 底部按钮被导航栏遮住。
7. sheet 里的输入框弹键盘时，sheet 被顶出屏幕。
8. 旋转屏幕后 sheet 消失但状态还留着，出现"幽灵筛选条件"。

## 来源

- https://m3.material.io/components/bottom-sheets/specs
- `01-Foundations/elevation.md`（模态 bottom sheet = Level 1）
- `02-Components/dialogs.md`（哪些场景该用对话框）

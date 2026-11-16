# Bottom Sheet / 底部面板

- 官方指南：https://m3.material.io/components/bottom-sheets ｜ [Specs](https://m3.material.io/components/bottom-sheets/specs) ｜ [Guidelines](https://m3.material.io/components/bottom-sheets/guidelines) ｜ [Accessibility](https://m3.material.io/components/bottom-sheets/accessibility)
- Compose API：`ModalBottomSheet`、`BottomSheetScaffold`（androidx.compose.material3）
- 核验日期：2026-11-16 ｜ 依据：官网四页全文
- 状态：尺寸与颜色角色为 Specs 页原值；圆角来自 Overview 的 "Differences from M2"

## 用途与边界

> "Bottom sheets show secondary content anchored to the bottom of the screen."

两个变体：**standard**（与主内容共存、无遮罩）与 **modal**（有遮罩、阻断交互）。内容必须是补充性的，不能是应用主内容。

> "Use bottom sheets in compact and medium breakpoints."
> "Content should be additional or secondary (not the app's main content)."

它夹在对话框和页面之间：

| | Dialog | Bottom sheet | 新页面 |
|---|---|---|---|
| 打断程度 | 强（阻断全部功能） | 中（modal 阻断，standard 不阻断） | 无 |
| 适合内容量 | 一两句 + 两个按钮 | 一屏内的列表 / 操作 | 任意 |
| 典型场景 | 不可逆确认 | 筛选、分享、选择器、详情预览 | 完整流程 |

**判断标准**：modal bottom sheet 可替代手机上的 inline menu 或简单对话框，尤其适合**较长的动作列表**或**需要图标与较长描述**的项：

> "Use a modal bottom sheet as an alternative to inline menus or simple dialogs on mobile, especially when offering a long list of action items, or when items require longer descriptions and icons."

## 规格（Specs 原值）

> "Modal bottom sheets are above a scrim while standard bottom sheets don't have a scrim. Besides this, both variants of bottom sheets have the same specs."

| 属性 | 值 |
|---|---|
| 宽度 | 撑满窗口，**最大 640dp** |
| 窗口宽度 > 640dp | 上边距 **56dp**、左右边距 **56dp** |
| 默认上边距 | **72dp** |
| 高度 | 可变 |
| 拖拽手柄水平对齐 | 居中 |
| 拖拽手柄上下内边距 | **22dp** |
| 顶部圆角 | **28dp**（来自 Overview，Specs 表未列） |

> "Bottom sheets span the full window width up to 640dp. When the window width exceeds 640dp, bottom sheets adjust to have a top margin of 56dp and side margins of 56dp."

- 手机上贴边全宽；平板/折叠屏展开后变成一块左右各留 56dp 的悬浮面板。
- 28dp 只作用于**顶部两角**，底部贴屏幕边缘、无圆角。写死全宽是本组件最常见的响应式错误。

## 颜色角色

Specs 页列出三个角色（含一条脚注）：

| 元素 | 颜色角色 |
|---|---|
| Container | **Surface container low** |
| Drag handle | **On surface variant** |
| Scrim（仅 modal） | **Scrim** |

> "On Android platforms, the scrim color and opacity is automatically handled by the system UI."

- 容器色是 `surfaceContainerLow`，不是 `surface` 也不是 `surfaceContainerHigh`。
- 拖拽手柄用 `onSurfaceVariant`，比正文弱一档，不要用 `primary`。
- Compose 的 `ModalBottomSheetDefaults` 默认就是这套映射（container = surfaceContainerLow、scrim = scrim、drag handle = onSurfaceVariant）。

## 结构与可选内容

```
        ▬▬▬            ← 拖拽手柄（居中，上下 22dp）
  列表项 / 媒体 / 标题
  ...
```

容器是唯一必需元素：

> "A container is the only required element of a bottom sheet. Bottom sheet layouts can vary widely to support the kinds of content they contain."

可选元素与规则：

| 元素 | 规则 |
|---|---|
| List items | 可含 label、图标、文字按钮 |
| Dividers | 分隔不同类型的相关内容 |
| Media | 支持缩略图（头像/logo）、图片、视频 |
| Drag handle | 可选；有它才暗示"可拖" |
| Scrim | 仅 modal |

## 使用规则

### Standard 与 Modal 的分工

| 形态 | 关键行为 | 适用 |
|---|---|---|
| **Standard** | 与主 UI 区共存，两块区域可同时查看和交互 | 主内容频繁滚动/平移时（如地图上的列表、音乐播放器） |
| **Modal** | 出现在内容之前，禁用其他功能，直到确认、关闭或完成动作 | 手机上替代菜单、筛选、分享 |

> "Standard bottom sheets co-exist with the screen's main UI region and allow for simultaneously viewing and interacting with both regions, especially when the main UI region is frequently scrolled or panned."

> "Modal bottom sheets are used in mobile apps only."

Standard 拉满全屏高度时，app bar 里要有**收起图标**，让用户能回到初始位置：

> "At full-screen height, standard bottom sheets contain a collapse icon in an app bar to return to their initial position."

### Modal 的初始高度与关闭方式

> "To provide access to its top actions, the initial vertical position of modal bottom sheets is capped at 50% of the screen height."

- 初始高度**不得超过屏幕高度的 50%**。
- 内容超过 50% 时，可以上拉到全屏，再在内部滚动查看剩余项。

允许的关闭方式（官方四条）：

1. 点选面板内的菜单项或动作；
2. 点遮罩；
3. 向下滑动手柄/面板；
4. 使用 app bar 内的关闭控件（如果有）。

> "Display a close affordance in a full-screen modal bottom sheet."

### 高度切换与拖拽

> "Sheets should be able to cycle through preset heights and close completely without dragging. Selecting the drag handle should toggle through preset heights or close the sheet, while selecting the scrim should always close the bottom sheet."

- 拖拽手柄要能**循环切换预设高度**，并且不拖也能完全关闭。
- 点遮罩**始终**关闭（不要因为"有未保存内容"就改成不关；那种场景应改用页面或对话框）。
- 有多个预设高度但不能用手柄时，必须提供**单指针替代方案**（例如按钮）来改高度。

### 滚动

> "Bottom sheets can be horizontally scrolled, independent of the rest of the screen's content."
> "Bottom sheets should be scrollable when their content exceeds the initial viewable height."

- 面板内部滚动独立于背景内容。
- 内容超过可视高度时必须可滚；不要把超长内容硬塞进不滚动的面板。

### 响应式与替代方案

| 断点 | 做法 |
|---|---|
| Compact | 全宽、贴底、浮在主内容之上 |
| Medium / Expanded | 有默认最大宽度（640dp），可覆盖；复杂任务考虑 floating sheet 等非临时表面 |
| Desktop / Expanded | 可换成 side sheet 展示同类内容 |

> "For larger screens with medium and expanded breakpoints, bottom sheets have a default max-width to prevent undesired layouts and awkward spacing. However, this can be overridden if needed."

### Android 返回手势

> "On Android, a gesture called predictive back allows a user to swipe left or right on the bottom sheet."

- 手势过程中面板从**左右两侧**脱离屏幕边缘，提示即将关闭；随后露出上一屏预览。

## 无障碍

### 触控目标

> "The top 48dp portion of the bottom sheet is interactive when user-initiated resizing is available and the drag handle is present."

- 手柄存在时，顶部 **48dp** 区域都是可交互的缩放热区，不能只把视觉手柄本身做成 8dp 高的点击区。
- Overview 也明确写了手柄要"an accessible 48dp hit target"。

### 焦点与键盘

> "The optional drag handle can be focused in the tab order and interacted with using non-touch inputs, such as keyboard or switch controls."

| 按键 | 行为 |
|---|---|
| Tab | 焦点落在拖拽手柄 |
| Space / Enter | 在可用高度之间切换 |

- 官方无障碍页只规定了**手柄可聚焦**，没有写"模态面板打开后焦点进入面板"。后者是所有模态浮层的通用要求，属 ⚠️ 推断（页面未明示），实现时仍应做焦点圈定，并在关闭后归还触发元素。

### 读屏语义

> "Label only the drag handle. The accessibility role for the drag handle is "button.""

- 只给手柄加标签，不要给整个面板套一个可点击语义。
- 手柄的角色是 **button**，标签写「展开/收起」之类的动作，而不是「拖拽手柄」这种控件名。
- 面板内容按各自的组件规范标注，标题不要伪装成 heading 除非它确实是结构标题（⚠️ 推断，页面未明示）。

### 替代操作

> "Include a single-pointer alternative for any action that can be completed by dragging."
> "Drag handles should cycle the bottom sheet through available heights when selected. If a drag handle can't be used, add a button to do this action."

读屏用户、开关控制用户无法"拖"，必须能通过点击/按键完成同样的高度切换与关闭。

## Compose 对应 API

```kotlin
val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

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

- `skipPartiallyExpanded = false` 对应官方"初始高度不超过 50%"的中间态；内容很短时设 `true` 直接全展开。
- `BottomSheetDefaults.SheetMaxWidth` 为 **640dp**，与官网一致；不要自己再设更大的 `sheetMaxWidth`。
- `BottomSheetDefaults.DragHandle()` 已按官方 22dp 上下内边距与居中布局实现（以当前 material3 版本源码为准）。
- `ModalBottomSheet`、`rememberModalBottomSheetState`、`BottomSheetScaffold` 在多个 material3 版本中仍标注 `@ExperimentalMaterial3Api`，升级后先确认注解状态。
- Standard 形态用 `BottomSheetScaffold` + `rememberBottomSheetScaffoldState`，同样属于实验性 API。
- 关闭要走 `sheetState.hide()` 再改状态，否则跳过收起动画。

## 常见错误

1. 平板上 bottom sheet 拉满全宽（超过 640dp 应留 56dp 边距）。
2. 用 `surface` 或 `surfaceContainerHigh` 做容器色（官方是 `surfaceContainerLow`）。
3. Modal 初始高度超过屏幕 50%，顶部动作被顶出屏幕。
4. 内容超过一屏还硬塞，内部滚动与外层拖拽冲突。
5. 用 sheet 装长表单（应该用页面或 full-screen dialog）。
6. 没有拖拽手柄，也没有任何单指针替代方案，读屏用户无法改高度。
7. 手柄只做了 8dp 高的点击区，没有 48dp 触控目标。
8. 点遮罩不关闭（官方要求始终关闭），或反过来在有未保存输入时点遮罩就关。
9. 用 `showSheet = false` 直接切换，没有收起动画。
10. 底部按钮被导航栏/手势条遮住。
11. 旋转屏幕后 sheet 消失但筛选状态还留着，出现"幽灵条件"。
12. 大屏上仍用 modal bottom sheet，而官网建议换成 side sheet 或 floating sheet。

## 来源

- https://m3.material.io/components/bottom-sheets/overview
- https://m3.material.io/components/bottom-sheets/specs
- https://m3.material.io/components/bottom-sheets/guidelines
- https://m3.material.io/components/bottom-sheets/accessibility
- 以上四页均于 **2026-11-16** 核验
- 关联：`02-Components/side-sheet.md`（大屏同类需求）、`01-Foundations/elevation.md`（模态 bottom sheet 为 Level 1）

# Side Sheet / 侧边面板

- 官方指南：https://m3.material.io/components/side-sheets ｜ [Specs](https://m3.material.io/components/side-sheets/specs) ｜ [Guidelines](https://m3.material.io/components/side-sheets/guidelines) ｜ [Accessibility](https://m3.material.io/components/side-sheets/accessibility)
- Compose API：**官方标注 Unavailable**（无 material3 实现）；Android Views (MDC-Android) 为 Available
- 核验日期：2026-11-16 ｜ 依据：官网四页全文
- 状态：尺寸为 Specs 原值；圆角来自 Overview 的 "Differences from M2"

## 用途与边界

> "Use side sheets to provide optional content and actions without interrupting the main content."

两个变体：**standard**（常驻，与主内容并排）与 **modal**（有遮罩，必须关闭才能操作底层内容）。官方不用 "docked" 这个词来描述 side sheet——那是旧文档/内部叫法，规范名为 standard。

| 组件 | 位置 | 打断程度 | 典型场景 |
|---|---|---|---|
| **Standard side sheet** | 侧边常驻 | 无 | 筛选、详情、辅助信息（平板/桌面） |
| **Modal side sheet** | 侧边浮层 | 强（有遮罩） | 手机上承载同类内容 |
| Navigation drawer | 侧边 | 中 | 应用级导航（目的地切换） |
| Bottom sheet | 底部 | 中 | 手机上的同类需求 |
| Dialog | 居中 | 强 | 必须确认 |

**判断标准**：内容与主界面需要**并排参考**（列表点开看详情、筛选影响主内容）用 side sheet；要切换目的地用 navigation drawer。

## 规格（Specs 原值）

### 两种形态共用

| 属性 | 值 |
|---|---|
| 最大宽度 | **400dp** |
| 左右内边距 | **24dp** |
| 顶部元素之间的间距 | **12dp** |
| 底部操作区高度 | **72dp** |
| 底部操作区上内边距 | **16dp** |
| 底部操作区下内边距 | **24dp** |
| 底部操作区水平对齐 | 左对齐（Left） |
| 脱离屏幕边缘时的边距 | **16dp** |

### Modal 专属

| 属性 | 值 |
|---|---|
| 带图标时的起始内边距 | **16dp** |
| 圆角 | **16dp**（来自 Overview） |

Specs 表**没有**列圆角；16dp 出自 Overview 的 "Shape: Modal side sheets have a 16dp corner radius"，且只针对 modal。Standard 形态的圆角官网未给数值，不要照抄（⚠️ 推断：取决于是否 detached）。

> "Side sheets have a fixed width and typically span the height of the screen."

两条最容易被忽略的：最大宽度 **400dp**（宽屏拉宽会让面板变成"半个页面"）；底部操作区 **72dp 高 + 24dp 下内边距**是官方固定组合，别改成 16dp。

## 颜色角色

### Standard side sheet

Specs 的角色清单与 Anatomy 顺序一一对应（Divider → Headline → Container → Close icon），可直接采用：

| 元素 | 颜色角色 |
|---|---|
| Divider（可选） | **Outline variant** |
| Headline | **On surface variant** |
| Container | **Surface** |
| Close icon button | **On surface variant** |

注意标准型的容器是 `surface`，与 modal 的 `surfaceContainerLow` 不同——它要和主内容并排，因此不能浮起来。

### Modal side sheet

Specs 只列 4 个角色：`On surface variant`、`On surface variant`、`Surface container low`、`On surface variant`，而 Anatomy 有 7 类元素（Back icon、Headline、Container、Close icon、Divider、Action buttons、Scrim）。

| 元素 | 颜色角色 |
|---|---|
| Container | **Surface container low**（可确定） |
| Back icon / Headline / Close icon | **On surface variant**（⚠️ 推断：三者同角色，顺序无法唯一对应） |
| Divider | Specs 未给（⚠️ 推断可能是 `outlineVariant`） |
| Action buttons | Specs 未给（按钮按自身规范，通常 primary） |
| Scrim | Specs 未给（与 bottom sheet 同为 scrim，⚠️ 推断） |

## 结构与可选内容

```
┌────────────────────────┐
│ [返回] 标题        [✕]  │  ← 关闭按钮必须有，返回可选
│ ────────────────────── │  ← divider 可选
│ 内容……                  │
│                        │
├────────────────────────┤  底部操作区 72dp
│ [取消]  [保存]          │  左对齐，上 16 / 下 24dp
└────────────────────────┘
```

| 元素 | 必需 | 规则 |
|---|---|---|
| Container | ✅ | 唯一必需元素，尺寸由内容决定 |
| Close icon button | ✅（无障碍要求） | 始终提供，便于关闭聚焦中的面板 |
| Back icon button | 可选 | 用于在面板内跳到另一个区域/体验 |
| Headline | 可选 | 面板的标题 |
| Divider | 可选 | 分隔操作区与内容，或用户内容与系统内容 |
| Action buttons | 可选 | Save / Edit / Download 等，用 elevation、填充、色调区分主次 |

> "Material requires that a close affordance, such as a close icon button, is always present within a side sheet." 关闭按钮既提高可访问性，也让"聚焦中的面板"更容易关闭。

- Standard 的正文区在面板打开时会**收缩**，并在尾部保留边距（"the body area shrinks … while maintaining a margin on the body's trailing edge"）。
- 大屏上 modal 可以随断点变成 standard（"can transition to standard side sheets at larger screen sizes"）。

## 使用规则

### 位置与边距

> "Place side sheets along the edge of the screen, usually on the right side to avoid interference with any navigational components on the left edge. They can be slightly inset by 16dp."

- 默认贴右边缘；可内缩 16dp，但不要缩进远超 16dp：

> "Don't inset a side sheet from the screen edges far beyond the recommended margin. This makes the sheet's position and scroll behavior unclear, while obscuring primary content."

- RTL 语言下出现在**左边缘**，所有元素镜像（"side sheets should appear on the left edge of the window with all elements reversed"）。

### Standard vs Modal 选择

| 断点 | 推荐 |
|---|---|
| Compact（手机） | Modal（屏幕小，standard 会把内容挤没） |
| Medium / Expanded（平板、桌面） | Standard（提供稳定、可预期的上下文面板） |

> "Standard side sheets are supplementary surfaces used mostly in medium to expanded breakpoints, like tablet and desktop."
> "Modal side sheets are preferred in compact breakpoints, like mobile, due to limited screen size."

### 滚动

> "Side sheets can vertically scroll independent of the rest of the UI."
> "Side sheets cannot scroll horizontally."

- 只能纵向滚动，滚动位置在页面滚动时保持。
- 不要用任何暗示横向滚动的布局（400dp 宽度装不下横向内容）："Don't allow horizontal scrolling or lay out the side sheet in a way that suggests horizontal scrolling."

### 分隔线用在哪

官方给了两个明确场景：操作按钮与内容之间；用户生成内容与系统生成内容之间。

### Android 返回手势

> "On Android, a gesture called predictive back allows a person to swipe left or right on the side sheet."

- 手势过程中面板从**上下**边缘脱离（注意与 bottom sheet 的左右脱离相反），露出上一屏预览；
- 面板与内容始终朝手势方向缩放。

## 无障碍

### 关闭入口是硬要求

> "Material requires that a close affordance, such as a close icon button, is always present within a side sheet."

> "Without a close icon button, people can't predict the opening and closing flow of side sheets, or know if the sheet is transient or permanent."

- 不要用"点遮罩关闭"替代关闭按钮：遮罩点击不是可聚焦的控件，读屏用户拿不到。
- 返回图标是可选的导航入口，不能代替关闭。

### 焦点与键盘

> "Actions within a side sheet can be focused by tab order using a keyboard or switch control."

| 按键 | 行为 |
|---|---|
| Tab | 焦点落在（未禁用的）图标按钮上 |
| Space / Enter | 激活该图标按钮 |

- 官方只保证"内部动作可 Tab 到达"；**打开时焦点进入面板、关闭后回到触发元素**属于模态浮层通用要求，⚠️ 推断（Side sheets 无障碍页未写初始焦点）。

### 读屏语义

> "The accessibility role for a side sheet is Dialog."

- 语义角色是 **Dialog**，不是 navigation drawer、不是 region。
- 因此不要用 `ModalNavigationDrawer` 直接装表单：它会给读屏"目的地列表/导航"的语义，与 Dialog 冲突。

### 尺寸与触控

- 底部 24dp 下内边距在真机上正好避开手势条，⚠️ 推断（官网未说明这 24dp 的用途，只给了数值）。不要删。
- 关闭图标按钮的触控目标不小于 48×48dp（按 icon button 规范）。

## Compose 对应 API

官网在 side sheets 的 Overview 中把 **Jetpack Compose 标为 Unavailable**，material3 目前没有 `SideSheet` / `ModalSideSheet` 组件。可选路径：

| 方案 | 评价 |
|---|---|
| `Dialog` + `usePlatformDefaultWidth = false` + `Surface` 自建 | 语义、焦点、遮罩都正确，推荐 |
| `ModalNavigationDrawer` 改造 | 语义是导航，装表单/筛选会误导读屏，不推荐 |

自建 modal side sheet 的最小骨架：

```kotlin
Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
        Surface(
            modifier = Modifier
                .widthIn(max = 400.dp)        // 官方 max-width
                .fillMaxHeight(),
            shape = RoundedCornerShape(16.dp), // modal 16dp（官网未细分圆角位置）
            color = MaterialTheme.colorScheme.surfaceContainerLow
        ) {
            Column(Modifier.padding(horizontal = 24.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("筛选", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "关闭")
                    }
                }
                HorizontalDivider()              // outlineVariant
                Box(Modifier.weight(1f)) { Content() }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)               // 底部操作区
                        .padding(top = 16.dp, bottom = 24.dp),
                    horizontalArrangement = Arrangement.Start
                ) { Actions() }
            }
        }
    }
}
```

- `Dialog` 自带遮罩与焦点圈定，语义按 Dialog 上报；`usePlatformDefaultWidth = false` 才能让面板贴边并占满高度。
- 需要 RTL 镜像时用 `LocalLayoutDirection` 判断，把面板放到 `CenterStart`。
- 面板内部只能纵向滚动；不要嵌套横向 `LazyRow` 作为主内容容器。

## 常见错误

1. 宽屏上 side sheet 拉到 700dp 宽，主次颠倒（上限 400dp）。
2. 去掉底部 24dp 下内边距，按钮被手势条压住。
3. 小屏上做常驻标准侧栏，内容区只剩一半。
4. 没有关闭图标按钮，只靠点遮罩或返回键关闭。
5. 用 `ModalNavigationDrawer` 装表单，读屏语义变成导航。
6. 打开后焦点没进面板，读屏还在读背后的页面；或遮罩点击是否关闭没想清楚（有未保存内容时不该用 modal side sheet）。
7. 面板内横向滚动，400dp 宽度下每项都看不全。
8. 把圆角 16dp 套到 standard 形态上（官网只对 modal 给了 16dp）；内部再嵌一个 bottom sheet，两层弹层叠加。

## 来源

- https://m3.material.io/components/side-sheets/overview
- https://m3.material.io/components/side-sheets/specs
- https://m3.material.io/components/side-sheets/guidelines
- https://m3.material.io/components/side-sheets/accessibility
- 以上四页均于 **2026-11-16** 核验
- 关联：`02-Components/bottom-sheet.md`（手机端的同类需求）、`02-Components/navigation.md`（drawer 的职责）

# Side Sheet / 侧边面板

- 官方指南：https://m3.material.io/components/side-sheets ｜ 规格页：https://m3.material.io/components/side-sheets/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-15 ｜ 核验方式：Chrome CDP 读官方 Measurements 表
- 状态：**尺寸为官方原值**

## 用途

Side sheet 从屏幕**侧边滑出**，承载与当前内容相关的补充信息或操作。

它和几个近亲的区别：

| 组件 | 位置 | 打断程度 | 典型场景 |
|---|---|---|---|
| **Side sheet** | 侧边 | 中（模态时强） | 详情预览、筛选、辅助编辑 |
| Navigation drawer | 侧边 | 中 | 应用级导航 |
| Bottom sheet | 底部 | 中 | 手机上的同类需求 |
| Dialog | 居中 | 强 | 必须确认 |

**判断标准**：内容与主界面相关、需要并排参考（如"列表点开看详情"），用 side sheet；如果是要切换目的地，用 drawer。

## 两种形态

| 形态 | 行为 | 适用 |
|---|---|---|
| **Modal** | 有遮罩，必须关闭才能继续 | 紧凑/中等窗口 |
| **Docked** | 与内容并排，常驻可见 | 展开窗口（平板、桌面） |

大屏用 docked，小屏用 modal——这和大屏的一贯原则一致，不要在小屏上做常驻侧栏。

## 尺寸（官方 Measurements 原值）

| 属性 | 值 |
|---|---|
| 左右内边距 | **24dp** |
| 顶部元素之间的间距 | **12dp** |
| 底部操作区高度 | **72dp** |
| 底部操作区上内边距 | **16dp** |
| 底部操作区下内边距 | **24dp** |
| 底部操作区水平对齐 | 左对齐 |
| **最大宽度** | **400dp** |

两条最容易被忽略：

1. **最大宽度 400dp**。在宽屏上不能让 side sheet 无限拉宽，否则变成"半个页面"，用户会迷失主次。
2. **底部操作区 72dp 高、下内边距 24dp**。这个下内边距是给手势条留的位置，不是美学选择——去掉后按钮会被系统手势区吃掉。

## 结构

```
┌────────────────────────┐
│ 标题                    │
│ （顶部元素间 12dp）      │
│ 内容……                  │
│                        │
├────────────────────────┤  底部操作区 72dp
│ [取消]  [确认]          │  左对齐，上 16 / 下 24dp
└────────────────────────┘
```

## Compose 实现

Modal 形态通常用 `ModalBottomSheet` 的横版思路，或 `Dialog` + 自定义布局；material3 目前**没有与 side sheet 一一对应的稳定组件**，常见做法是用 `ModalNavigationDrawer` 的 sheet 内容区改造，或直接用 `Surface` + `AnimatedVisibility` 自己做。

用 `ModalNavigationDrawer` 复用滑动行为时要注意：它是为导航设计的，语义是"目的地列表"，拿来装表单会让读屏报错语义。

因此更稳的做法是显式实现：

```kotlin
AnimatedVisibility(
    visible = open,
    enter = slideInHorizontally(initialOffsetX = { it }),
    exit = slideOutHorizontally(targetOffsetX = { it })
) {
    Surface(
        modifier = Modifier
            .widthIn(max = 400.dp)       // 官方上限
            .fillMaxHeight(),
        tonalElevation = 1.dp
    ) {
        Column(Modifier.padding(horizontal = 24.dp)) {
            // 内容
            Spacer(Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)         // 官方底部操作区高度
                    .padding(top = 16.dp, bottom = 24.dp),
                horizontalArrangement = Arrangement.Start
            ) { /* 按钮 */ }
        }
    }
}
```

## 无障碍

- 打开时**焦点进入 sheet**，关闭后回到触发元素。
- 模态形态下，遮罩后的内容要能从读屏焦点链里排除（`Modifier.semantics { invisibleToUser() }` 或弹层自带的语义隔离）。
- 返回键 = 关闭 sheet，不是退出页面。
- 底部按钮要避开手势条（那 24dp 下内边距就是干这个的）。
- 标题要能被读成标题（`heading()` 语义），否则读屏用户不知道面板在讲什么。

## 常见错误

1. 宽屏上 side sheet 拉到 700dp 宽，主次颠倒。
2. 去掉底部 24dp 下内边距，按钮被手势条压住。
3. 小屏上做常驻 docked 侧栏，内容区只剩一半。
4. 用 `ModalNavigationDrawer` 装表单，语义变成"导航"。
5. 打开后焦点没进入，读屏还在读背后的页面。
6. 遮罩点击是否关闭没想清楚（有未保存内容时不该关）。
7. 内部再嵌一个 bottom sheet，两层弹层叠加。

## 来源

- https://m3.material.io/components/side-sheets/specs
- `02-Components/bottom-sheet.md`（手机端的同类需求）
- `02-Components/navigation.md`（drawer 的职责）

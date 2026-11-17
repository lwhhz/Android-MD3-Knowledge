# Toolbar 试点记录（P1 · 第二批）

> 日期：2026-11-18　范围：docked / floating，horizontal / vertical，standard / vibrant　结论：**4/5 参数落地**；真机截图通过。

## 一、实现

| 变体 | 实现 |
|---|---|
| Docked | 1.5.0-rc01 无 `DockedToolbar` composable，按规格自绘 Surface + Row：高 64dp、直角、铺满宽度 |
| Floating horizontal | 官方 `HorizontalFloatingToolbar`，expanded=true |
| Floating vertical | 官方 `VerticalFloatingToolbar`，expanded=true |
| 颜色 | Standard：surfaceContainer；Vibrant：primaryContainer；floating 用 `FloatingToolbarDefaults` 对应 colors |

内容通过 `content: @Composable () -> Unit` 传入，内部加 16dp 外边距 + 8dp 间距。

### 新增能力（2026-11-18）

- **FAB**：`floatingActionButton` 槽，交给官方 `Horizontal/VerticalFloatingToolbar` 布局；demo 中 horizontal floating 已显示独立 FAB。
- **溢出**：`overflowItems: List<M3ToolbarOverflowItem>` + MoreVert，DropdownMenu 显式列出动作；docked / floating 都可用。
- **滚动**：`scrollBehavior: FloatingToolbarScrollBehavior?` 透传给官方 FloatingToolbar；demo 额外加了 LazyColumn + `nestedScroll(exitAlwaysScrollBehavior)` 覆盖示例。
  ⚠️ 真机测试中该嵌套列表未接管滑动手势，**“滚出/收起”未验证**；当前仅完成参数透传与示例接入。

## 二、官方尺寸（已锁进单测）

| 项 | 值 |
|---|---|
| 高度 | 64dp |
| 外侧最小内边距 | 16dp |
| 项目间距 | 8dp（specs；guidelines 另说 32dp 为默认） |
| Floating 水平距边 | 16dp |
| Floating 垂直距边 | 24dp |
| 触控目标 | 48dp |

常量在 [M3ToolbarTokens.kt](<app/src/main/java/com/example/m3appbar/ui/M3ToolbarTokens.kt>)，单测 [M3ToolbarMetricsTest.kt](<app/src/test/java/com/example/m3appbar/ui/M3ToolbarMetricsTest.kt>)。

## 三、真机验证

![Toolbar：docked（含 MoreVert）/ floating + FAB / vibrant / vertical](<screenshots/toolbar-fab.png>)

![Docked overflow 菜单：Delete / Archive](<screenshots/toolbar-overflow.png>)

![滚动示例：LazyColumn + Floating Toolbar（未验证隐藏）](<screenshots/toolbar-scroll-demo.png>)

| 项 | 结果 |
|---|---|
| Docked 64dp 通栏 + 三个图标 + MoreVert | ✅ |
| Floating horizontal + 独立 FAB | ✅ |
| Floating horizontal vibrant（primaryContainer） | ✅ |
| Floating vertical vibrant，居中 | ✅ |
| Overflow 菜单 Delete / Archive | ✅ |
| 图标 48dp 触控目标（IconButton 默认） | ✅ |

## 四、验证命令

| 项 | 结果 |
|---|---|
| :app:compileDebugKotlin | 0 error |
| :app:testDebugUnitTest | **18 tests / 0 failures** |
| :app:assembleDebug | 通过 |
| 真机 adb install | Success |

## 五、参数落地

| 参数 | 落地 | 说明 |
|---|---|---|
| Configuration（Docked / Floating） | ✅ | docked 自绘，floating 官方 API |
| Color（Standard / Vibrant） | ✅ | 容器色对应 |
| Orientation（Horizontal / Vertical） | ⚠️ | **Vertical 仅 floating 生效；docked + vertical 会回落为 horizontal** |
| Content | ✅ | 任意 composable 槽 |

## 六、已知限制

1. **Docked + Vertical 不支持**：官方 vertical 仅用于 floating；组件内回落到 horizontal，未报错。
2. 1.5.0-rc01 **没有 DockedToolbar API**，docked 为自绘；滚动/折叠/圆角变体未做。
3. **滚动行为只做参数透传**：demo 有 LazyColumn + nestedScroll，但真机滑动手势未触发列表滚动，滚出/收起未验证。
4. FAB 只在 floating toolbar 中验证；docked FAB 未做。
5. 未做 XR Toolbar（P2）。
6. 未做 RTL、密度、pixel diff。

## 七、下一步

P1 下一批建议：**Bottom sheet** → Segmented button → Carousel → Date pickers。
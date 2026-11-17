# 时序 · MD3Clock（知识库示例应用）

基于本仓库知识库实现的 Material 3 **Expressive** 时间应用：闹钟、世界时钟、秒表、倒计时、日历五个页面。它存在的意义是「把文档里的结论跑成可运行的代码」——每个 Expressive 能力都能在下面第二节追到对应的知识库条目。

- 包名：`com.example.md3clock`｜应用名：时序｜版本：1.0.0
- 技术栈：Kotlin + Jetpack Compose + `androidx.compose.material3` 1.5.0-rc01（Compose BOM alpha 2026.11.00）
- minSdk 26（直接使用 `java.time`，不引入 desugaring）｜compileSdk 37.2｜AGP 9.4.1｜Kotlin 2.3.21
- 主题：`MaterialExpressiveTheme` + `MotionScheme.expressive()`

## 〇、下载与构建

- **安装包**：见 Release [`sample-md3clock-v1.0.0`](https://github.com/lwhhz/Android-MD3-Knowledge/releases/tag/sample-md3clock-v1.0.0) 的 `MD3Clock-1.0.0-debug.apk`（debug 签名，仅供试装，不可上架）；同页附 `MD3Clock-1.0.0-source.zip`。
- **构建**：

```bash
cd 09-Sample-Apps/MD3Clock
./gradlew assembleDebug        # 产物 app/build/outputs/apk/debug/app-debug.apk
./gradlew testDebugUnitTest    # 时间逻辑单元测试（11 个）
```

需要 JDK 17+、Android SDK **37.2** 平台。首次构建会从 Google Maven 下载 Compose alpha 线依赖。

> 记号约定：下文 `01/` `02/` `03/` 开头的路径均指**本仓库**对应目录（如 `02-Components/progress-indicator.md`）。

## 一、五个页面

| # | 页面 | 主要能力 |
|---|---|---|
| 1 | 闹钟（首页） | 当前时间 + 表盘、下一个闹钟倒计时、新建/快捷提醒、启用开关、编辑（时间/标签/重复）、左滑删除 + 撤销、到点弹窗与贪睡 |
| 2 | 世界时钟 | 本地时间与一天进度、城市列表（表盘 + 时差 + 日期差 + 昼夜）、搜索添加城市、按时差排序、移除 + 撤销、校准（加载指示） |
| 3 | 计时器（秒表） | 百分秒计时、环形秒进度、开始/暂停/继续、计次、分段最快/最慢标记、复位 |
| 4 | 倒计时 | 1/5/10/25 分钟预设（connected button group）、1–60 分钟滑杆、环形剩余进度、结束时刻、到时对话框（完成 / 再来一次） |
| 5 | 日历 | 月网格（今天描边、选中填充、日程圆点）、本月进度、按日查看日程、添加日程（时间 + 标题）、删除 + 撤销、Date picker 跳转 |

## 二、Expressive 落点（不是「换皮」，逐项有依据）

| 能力 | 用在哪 | 依据与注解状态 |
|---|---|---|
| `MaterialExpressiveTheme` | 全局主题 | `02-Components/expressive-components.md`：已转正，无实验注解；换主题会改变所有组件默认外观，本项目全页面按 expressive 回归 |
| `MotionScheme.expressive()` | 全局动效 | `01-Foundations/motion.md`：spatial damping 0.8 / stiffness 380，effects damping 1.0 不过冲；组件内动效自动走弹簧 |
| Emphasized 字阶（15 档） | 所有时间读数、选中态 | `01-Foundations/typography.md`：emphasized 与 baseline 同字号，只加字重；禁止用放大字号做强调 |
| Tabular figures（`tnum`） | 时钟、秒表、倒计时、日程时间 | `01-Foundations/typography.md`「数字」：值频繁变化处必须等宽数字，避免跳字与重排 |
| `LinearWavyProgressIndicator` | 本地一天进度、本月进度 | `02-Components/progress-indicator.md`：已转正；能拿到总量就用 determinate，两处都是真实比例 |
| `CircularWavyProgressIndicator` | 秒表秒针环、倒计时剩余环 | 同上；圆环尺寸 248/280dp，在官方 24–240dp 建议附近按大屏增大（280 略超建议上限，见「已知取舍」） |
| `SplitButtonLayout` / `SplitButtonDefaults` | 闹钟「新建闹钟 ｜ ▾」 | `02-Components/split-button.md`：后段只能是菜单图标、两段间隙 2dp、S 档 40dp 需补足 48dp 触控（组件默认交互区已覆盖） |
| `FloatingActionButtonMenu` + `ToggleFloatingActionButton` | 世界时钟右下角 | `02-Components/fab-menu.md`：收起是 FAB、展开是动作菜单；需 `@OptIn` |
| `ButtonGroupDefaults.connected*ButtonShapes` + `ToggleButton` | 倒计时预设 | `02-Components/button-group.md`：connected 表达互斥「选项」，2dp 组内间距，取代 segmented button |
| `ContainedLoadingIndicator` | 世界时钟校准 | `02-Components/loading-indicator.md`：只用于 200ms–5s 的未知进度等待，1.2s 模拟校准，禁止从 loading 切 determinate |
| 更圆的形状阶梯 | 全局 | `01-Foundations/shape-system.md`：8/12/16/20/32dp 全部来自官方十档，未发明数值 |
| 语义色 + 深浅两套 scheme | 全局 | `01-Foundations/color-system.md`：组件里没有裸色值；深色是独立配色不是反相 |

## 三、关键设计决策

1. **动态取色默认关闭**：时钟的「已过/剩余」「今天/选中」依赖稳定的冷暖对比，壁纸取色会让语义漂移。代码保留 `dynamicColor` 开关（API 31+），这是主动取舍而非能力缺失。
2. **时间数字不缩小字号**：强调用 emphasized 同档；等宽数字用 `FontFeatureSettings = "tnum"`，避免百分秒每 10ms 抖动一次布局。
3. **秒表/倒计时用 `SystemClock.elapsedRealtime()`**：挂钟时间会被校时/改时区改动，用 `currentTimeMillis()` 做计时基准会出现「秒表倒退」。
4. **闹钟存规则不存时间戳**：`hour/minute + 星期集合`，换时区或夏令时不会错一小时（`02-Components/date-time-picker.md` 常见错误 8 的同类问题）。
5. **Expressive 只用在解释状态的地方**：进度条对应真实比例、按钮形状变化对应选中、FAB 展开对应动作集合；没有纯装饰的弹跳或形状 morph（`expressive-components.md` 三条判断）。

## 四、自适应与布局

- 断点：`widthDp < 600` 为 Compact，否则 Medium 及以上。
  - Compact：`ShortNavigationBar`（5 个顶层目的地，正好是 nav bar 3–5 的上限）。
  - Medium+：`WideNavigationRail`，可展开/收起，内容区自适应剩余宽度。
- 日历在非 Compact 下变两栏：左侧月历 + 月进度，右侧选中日期的日程（list-detail 的单页变体）。
- 顶层目的地之间用 `fadeIn/fadeOut`：`01-Foundations/motion.md` 的 top-level 转场模式，刻意不用 lateral 滑动。
- 所有页面根容器可滚动（`verticalScroll`）；这是 MD3Showcase 真机暴露过的问题，本项目从第一版就按可滚动实现。

## 五、无障碍

- 全部可点击图标按钮都有 `contentDescription`；纯装饰表盘用一次语义播报「表盘，X 点 Y 分」，不让读屏逐个读刻度。
- 日历格子语义包含「M月d日 星期X，今天/有日程/已选中」，角色为 Button；日期选择器另有 `Date picker` 作为非手势入口。
- Switch 的语义带闹钟时间与启停状态，不只靠颜色。
- 颜色不是唯一状态载体：日/夜、最快/最慢、今天/选中都有文字或描边。
- 时间全部用 `sp`；页面不使用固定高度容器装多行文本。

## 六、能力边界（明确不做什么）

- **闹钟是应用前台提醒**，不注册系统 `AlarmManager`、不申请精确闹钟权限、不做开机恢复；应用被杀后不会响。这样避免「看起来像系统闹钟但实际不响」的误导。
- 世界时钟的「校准」是 1.2s 的交互演示，没有连接网络时间源（Snackbar 文案已注明）。
- 数据持久化只覆盖闹钟/城市/日程（SharedPreferences + JSON），不存「当前时间」。

## 七、目录

```
09-Sample-Apps/MD3Clock/
├── README.md                     # 本文件
├── settings.gradle.kts / build.gradle.kts / gradle.properties
├── gradle/libs.versions.toml     # 版本目录（每条版本都注了官方来源与日期）
└── app/src/main/java/com/example/md3clock/
    ├── MainActivity.kt
    ├── ClockViewModel.kt          # ticker、秒表/倒计时状态机、闹钟触发
    ├── data/
    │   ├── Models.kt              # Alarm / CalendarEvent / WorldCity + 时间格式化
    │   └── ClockRepository.kt     # SharedPreferences 持久化 + 默认数据
    └── ui/
        ├── theme/                 # Color / Type / Shape / Spacing / Theme
        ├── components/            # ClockDigits、PageHeader、AnalogDial、Snackbar 工具、响铃弹窗
        ├── navigation/ClockApp.kt # 5 目的地 + 自适应导航 + 顶层淡入淡出
        └── screens/{alarm,world,stopwatch,countdown,calendar}/
```

## 八、验证状态

| 项 | 结果 |
|---|---|
| `assembleDebug` | ✅ 通过（APK 14.8 MB，含 Release 附件） |
| 单元测试 `TimeLogicTest` | ✅ 11/11 通过：下次触发、星期匹配、一次性闹钟日期、停用、秒表/倒计时格式化边界、半小时时区偏移 |
| 真机/模拟器 | ⏳ 构建会话内无设备连接（`adb devices` 为空），未做真机截图与滚动验证 |
| 200% 字号 | ⏳ 未在设备上验证；已知风险：时间拨盘选择器本身是固定尺寸组件 |

## 九、已知取舍

1. 秒表/倒计时的环形进度用 280dp（大屏），略高于 `progress-indicator.md` 记录的 Circular 240dp 建议上限；如果严格按上限，改为 240dp 即可。
2. 倒计时预设是 connected group（互斥选项），滑杆切到自定义值时组内可能没有选中项——这是「预设」与「自定义」两种输入方式共存的结果，不是组件的 selection-required 用法。
3. 日历格子视觉 36dp、命中区约 42×42dp，低于官方 48dp 目标；受 360dp 屏 7 列布局限制，官方 Date picker 同样采用 40dp 格子 + 48dp 目标的重叠方案，本项目未做重叠命中区。
4. 示例代码按本仓库定位提供参考，未附带开源许可证；引用规范数值时请回到对应知识库条目核对来源。

# Badges 试点记录（阶段 2 · P2 补做）

> 日期：2026-11-17　范围：独立 `M3Badges`（Small / Large，count / label）　结论：**2/2 参数落地**，真机与语义已验证。

## 一、实现

```kotlin
M3Badges(size = M3BadgeSize.SMALL)                 // 6dp 圆点
M3Badges(size = M3BadgeSize.LARGE, count = 3)      // 16dp 圆形
M3Badges(size = M3BadgeSize.LARGE, count = 120)    // 99+（maxCount 默认 99）
M3Badges(size = M3BadgeSize.LARGE, label = "NEW")  // 任意 ≤4 字符
```

- Small：6dp、圆角 3dp，语义“新通知”；
- Large：高 16dp、圆角 8dp，文字 4 字符上限；count 超过 `maxCount` 自动拼 `+`；
- 颜色：容器 `error`、内容 `onError`；
- 可与 `BadgedBox` 组合挂到图标上（demo 有两个宿主示例）。

## 二、官方尺寸（已锁进单测）

| 项 | 值 |
|---|---|
| Small 尺寸 / 圆角 | 6dp / 3dp |
| Large 高 / 最大宽 / 圆角 | 16dp / 34dp / 8dp |
| 文字内边距 | 4dp |
| 最大字符数 | 4（含 +） |
| Small 偏移 | 6×6dp |
| Large 偏移 | 14×12dp |

常量在 [M3BadgeTokens.kt](<app/src/main/java/com/example/m3appbar/ui/M3BadgeTokens.kt>)，单测 [M3BadgeMetricsTest.kt](<app/src/test/java/com/example/m3appbar/ui/M3BadgeMetricsTest.kt>)。

## 三、真机验证

![Badges：small / 3 / 99+ / NEW / 宿主图标](<screenshots/badges.png>)

| 项 | 结果 |
|---|---|
| Small 6dp 圆点 | ✅ |
| Large 单位数字 16dp 圆形 | ✅ |
| count=120 → “99+” 胶囊 | ✅ |
| label=“NEW” 完整显示 | ✅（见下方限制） |
| 挂到 Settings / Home 图标 | ✅ |

## 四、验证命令

| 项 | 结果 |
|---|---|
| `:app:compileDebugKotlin` | 0 error |
| `:app:testDebugUnitTest` | **10 tests / 0 failures** |
| `:app:assembleDebug` | 通过 |
| 真机 `adb install` | Success |

## 五、参数落地

| 组件 | 参数 | 落地 | 说明 |
|---|---|---|---|
| Badges | Size（Small / Large） | **2/2** | Large 同时支持 count 与 label |

## 六、已知限制

1. **Large 的 34dp 上限只对 count 路径强制**；任意 `label` 字母较宽时允许略超 34dp，否则 `W` 会被裁切（已实测）。
2. 未做 badge 出现/消失动画。
3. 未提供“图标 + badge”一体化组件；demo 用 `BadgedBox` 组合，偏移由 Compose 默认值决定，未按官方 14×12 / 6×6 精调。
4. 未做数字滚动/更新动画、RTL、pixel diff。
5. `textPadding` 常量已定义，但实际内边距由 Compose `Badge` 内部 4dp 实现，未再叠加。

## 七、下一步

回到 P0 顺序：**Navigation**（bar / rail / drawer；方案 A，不引入 adaptive 依赖）。

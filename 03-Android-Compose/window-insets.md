# Window Insets 与 Edge-to-edge

- 官方指南：https://developer.android.com/develop/ui/compose/system/insets
- 核验日期：2026-11-14

## 现代写法只有一条路

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent { AppTheme { AppNavHost() } }
    }
}
```

`enableEdgeToEdge()` 来自 `androidx.activity`（activity-compose 1.8+），负责把系统栏设为透明并调整图标明暗。

**不要再用**这些旧做法：

- `window.statusBarColor = ...`
- `window.navigationBarColor = ...`
- `WindowCompat.setDecorFitsSystemWindows(window, false)` 手写替代（`enableEdgeToEdge` 已封装）
- 主题里的 `android:windowTranslucentStatus`
- `ViewCompat.setOnApplyWindowInsetsListener` 手动算 padding（Compose 里用 Modifier）

## Scaffold 已经帮你处理了大部分

```kotlin
Scaffold(
    topBar = { TopAppBar(title = { Text("首页") }) },
    bottomBar = { NavigationBar { /* ... */ } }
) { paddingValues ->
    LazyColumn(
        contentPadding = paddingValues,
        modifier = Modifier.fillMaxSize()
    ) { /* ... */ }
}
```

关键点：**必须消费 `paddingValues`**。用 `contentPadding` 而不是 `Modifier.padding`——列表能滚到系统栏下方，但内容不会被遮挡。

不消费的典型症状：列表顶部被状态栏压住，或者最后一项被导航栏盖住。

## 常用 inset

| API | 包含什么 |
|---|---|
| `WindowInsets.safeDrawing` | 状态栏 + 导航栏 + 刘海 + IME（推荐用于内容区） |
| `WindowInsets.systemBars` | 状态栏 + 导航栏 |
| `WindowInsets.statusBars` | 仅状态栏 |
| `WindowInsets.navigationBars` | 仅导航栏（含手势条） |
| `WindowInsets.ime` | 键盘 |
| `WindowInsets.safeContent` | 安全区但不含 IME |
| `WindowInsets.displayCutout` | 刘海 / 挖孔 |

Modifier 形式：

```kotlin
Box(modifier = Modifier.navigationBarsPadding())
Box(modifier = Modifier.statusBarPadding())
Box(modifier = Modifier.imePadding())
Box(modifier = Modifier.safeDrawingPadding())
```

## IME（键盘）

表单页最常见的坑：输入框在屏幕下半部，键盘弹出后直接盖住。

```kotlin
Column(
    modifier = Modifier
        .fillMaxSize()
        .imePadding()          // 键盘高度变成底部内边距
        .verticalScroll(rememberScrollState())
) { /* 表单项 */ }
```

配合 `android:windowSoftInputMode="adjustResize"`（现代 Compose 应用应保持默认的 adjustResize 行为）。

注意事项：

- `imePadding()` 要和滚动容器一起用，否则键盘弹起时内容只是被压缩。
- 不要同时用 `imePadding()` 和 `navigationBarsPadding()` 造成双倍间距——用 `WindowInsets.safeDrawing` 统一处理，或 `union` 合并。
- 键盘弹起时底部按钮会被顶上来，注意别让固定底栏盖住输入框。

## 消费（consume）inset

父容器已经处理过某个 inset 后，子元素不应重复处理：

```kotlin
Scaffold { padding ->
    Box(Modifier.padding(padding).consumeWindowInsets(padding)) {
        // 内部的 LazyColumn 不会再加一遍系统栏高度
    }
}
```

常见症状：嵌套两层后底部出现双倍空白。

## 沉浸式内容

需要图片延伸到状态栏下方时：**内容延伸到栏下，但可读内容仍要在安全区内**。

```kotlin
Box {
    AsyncImage(modifier = Modifier.fillMaxWidth().height(240.dp))  // 延伸到状态栏下
    TopAppBar(
        title = { Text("详情") },
        modifier = Modifier.statusBarsPadding()                     // 顶栏内容避让
    )
}
```

## 逐版本注意事项

- `enableEdgeToEdge()` 的行为随 Target SDK 版本收紧：较新的 targetSdk 下系统会强制 edge-to-edge，应用无法退出该模式。**升级 targetSdk 时必须重新测一遍所有屏幕的 inset 表现。**
- 三键导航与手势导航的导航栏高度不同，两种都要看。
- 折叠屏在展开状态下 inset 会变化，需监听窗口变化而不是只读一次。

## 检查项

- [ ] 使用 `enableEdgeToEdge()`，无旧式系统栏代码
- [ ] `Scaffold` 的 `paddingValues` 已消费
- [ ] 键盘弹起时输入框可见
- [ ] 列表最后一项不被导航栏遮挡
- [ ] 手势导航与三键导航都验过
- [ ] 嵌套容器无双倍 inset
- [ ] 横屏 / 折叠屏展开后仍正确

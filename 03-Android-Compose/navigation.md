# Compose Navigation 实现

- 官方指南：https://developer.android.com/develop/ui/compose/navigation
- 核验日期：2026-11-14 ｜ 版本相关：类型安全路由需 navigation-compose 2.8+；Navigation 3 为独立新库，见文末

## 类型安全路由

用 Kotlin Serialization 定义目的地，不用字符串拼路由：

```kotlin
@Serializable object Home
@Serializable data class NoteDetail(val noteId: String)
@Serializable data class Search(val query: String? = null)
```

```kotlin
val navController = rememberNavController()

NavHost(navController = navController, startDestination = Home) {
    composable<Home> {
        HomeScreen(onNoteClick = { id -> navController.navigate(NoteDetail(id)) })
    }
    composable<NoteDetail> { backStackEntry ->
        val route: NoteDetail = backStackEntry.toRoute()
        NoteDetailScreen(noteId = route.noteId)
    }
}
```

好处：参数类型在编译期检查，重构时不会漏改字符串。

## 返回栈控制

```kotlin
navController.navigate(NoteDetail(id)) {
    launchSingleTop = true          // 重复导航不叠栈
    popUpTo<Home> { inclusive = false }  // 清到某个目的地
}
```

常见需求与写法：

| 需求 | 写法 |
|---|---|
| 防重复点击叠栈 | `launchSingleTop = true` |
| 提交成功后返回并清空表单页 | `popUpTo<Form> { inclusive = true }` |
| 登录后清空整个栈 | `popUpTo(navController.graph.id) { inclusive = true }` |
| Tab 切换保留各自状态 | `popUpTo(startDestination) { saveState = true }` + `restoreState = true` |

## 底部导航与返回栈

这是最容易出错的地方。切 Tab 时如果不处理，会重建页面、丢滚动位置和输入内容：

```kotlin
fun navigateToTab(route: Any) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
```

三行缺一不可：`saveState` 保存离开时的状态，`restoreState` 回来时恢复，`launchSingleTop` 防叠栈。

## ViewModel 作用域

```kotlin
composable<NoteDetail> {
    val vm: NoteDetailViewModel = viewModel()   // 默认作用域 = 该 NavBackStackEntry
}
```

- 默认作用域是当前 `NavBackStackEntry`：离开目的地、从返回栈移除后销毁。
- 需要跨目的地共享时，把 `viewModelStoreOwner` 指定为父 graph 的 entry，**不要**用 Activity 作用域偷懒——会导致状态永不清除。

## 嵌套图

按功能模块拆：

```kotlin
navigation<SettingsGraph>(startDestination = SettingsHome) {
    composable<SettingsHome> { }
    composable<SettingsAccount> { }
}
```

嵌套图的价值：共享 ViewModel 作用域、整体清理返回栈、按模块拆分代码。

## 深链

```xml
<nav-graph android:value="@navigation/nav_graph" />
```

配合 `deepLinks = listOf(navDeepLink<NoteDetail>(basePath = "https://example.com/note"))`。
深链要测：应用未启动、应用在后台、已登录、未登录四种状态。

## 常见错误

1. 用字符串路由 `"detail/$id"`，参数类型全靠运行时解析，拼错不报错。
2. 切 Tab 不保存/恢复状态。
3. 用 Activity 作用域的 ViewModel，退出登录后数据还在。
4. `popUpTo` 写错目的地，返回键直接退出应用。
5. 在 `composable` 外面创建 ViewModel，作用域错误导致内存泄漏。
6. 深链没有兜底：目标内容不存在时白屏。

## Navigation 3（评估中）

Navigation 3 改用**开发者持有返回栈**的模型，`SnapshotStateList` 就是返回栈，天然类型安全，并支持类型安全深链（1.2+）。Material Adaptive 库已与其集成处理双栏场景。

与 Navigation 2 的心智模型差异较大，**新项目评估前先确认当前稳定版本、迁移成本，以及是否已满足你的深链需求**。本库暂不提供 Nav3 代码范式，等其稳定后再补。

## 检查项

- [ ] 路由用类型安全方式定义
- [ ] Tab 切换保留状态
- [ ] 返回键行为逐页验证过
- [ ] ViewModel 作用域正确
- [ ] 深链四种状态都测过
- [ ] 目标内容缺失时有兜底

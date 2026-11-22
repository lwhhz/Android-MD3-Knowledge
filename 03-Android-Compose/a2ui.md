# A2UI / Agent-to-UI 渲染

- 官方文档：[A2UI renderer for Jetpack Compose](https://developer.android.com/develop/ui/compose/agentic) ｜ [Render A2UI surfaces](https://developer.android.com/develop/ui/compose/agentic/render-surfaces) ｜ [Create and customize component catalogs](https://developer.android.com/develop/ui/compose/agentic/manage-catalogs)
- API：`androidx.compose.material3.a2ui.A2uiSurface`、`A2uiSurfaceModel`、`A2uiSurfaceDefaults`；运行时在 `androidx.a2ui.compose`
- 核验日期：2026-11-22
- 核验方式：官方文档页全文（developer.android.com），版本来自 Google Maven 与 Compose Material3 release notes
- 状态：**Alpha**。`material3-a2ui` 最新 `1.0.0-alpha02`（Maven）；release notes 版本表列「Material3 A2UI 1.0 / 1.0.0-alpha01」。API 仍在演进，**不用于生产**

## 一句话结论

A2UI 是「**AI 下发的 JSON UI 协议 + Compose 原生渲染器**」：agent 不生成代码，只发结构化的 UI 消息；app 用**组件目录（catalog）**决定哪些组件可用，渲染成全原生 Compose，并保持 Snapshot 级细粒度更新。

## 它解决什么问题

传统「AI 生成界面」要么让模型吐代码（安全/可控性差），要么让模型自由发明组件（渲染不了）。A2UI 的做法：

1. agent 按协议发消息：界面结构、组件属性、数据更新；
2. app 侧 **parser + processor** 解析、校验（schema validation），拦截畸形 payload；
3. 用注册过的 catalog 把 JSON 映射到原生 Compose 组件；
4. 双向：用户操作可以把数据回传给 agent。

官方强调：`without executing arbitrary code`，且能处理「AI 幻觉」——schema 校验 + 组件 error state + 把错误回传给 agent 让它自纠。

## 库拆解（按职责）

| 工件 | 职责 |
|---|---|
| `androidx.a2ui.compose:compose-runtime` | 运行时；基于 Compose Snapshot 的细粒度更新——流式 token 只重组受影响的组件 |
| `androidx.a2ui.compose:compose-ui` | catalog 抽象：JSON schema → 原生 Compose 组件；支持渐进渲染、子组件 loading、动画转场、双向绑定 |
| `androidx.a2ui:a2ui-model` / `a2ui-engine` | 协议模型与引擎：schema 校验、错误处理、向 agent 回传错误 |
| `androidx.compose.material3:material3-a2ui` | 官方 **Basic Catalog**：用 M3 组件实现的一套即用 catalog，带 Material 主题 |

核心是**design-system agnostic**：runtime / UI 层不绑定 M3，可以接自己的设计系统。

## 高层结构

- **Surface**：一块可独立渲染的 A2UI 界面，对应 `A2uiSurfaceModel`。
- **Catalog**：组件契约——声明可用组件、属性 schema、本地回调。agent 只能用 catalog 里有的组件。
- **Message**：create/update/delete 之类增量消息，由 processor 合并进 surface 状态。

## 接入骨架（示意，以官方文档为准）

```kotlin
// 1) ViewModel：解析 + 处理
class AgenticUiViewModel : ViewModel() {
    private val parser = A2uiMessageParser()
    private val processor = A2uiMessageProcessor(
        catalogs = listOf(BasicCatalog), // 或用 material3-a2ui 的 M3 catalog
        // actionInterceptor = ...
    )
    val surfaces: StateFlow<List<A2uiSurfaceModel>> = processor.activeSurfaces

    init {
        viewModelScope.launch(Dispatchers.Default) { processor.collectMessages() }
    }
}

// 2) UI：渲染 surface
@Composable
fun AgentSurface(model: A2uiSurfaceModel) {
    A2uiSurface(
        surfaceModel = model,
        modifier = Modifier.fillMaxWidth(),
        loadingContent = { CircularProgressIndicator() },
        errorContent = { e -> Text("渲染失败：" + (e.message ?: "")) },
    )
}
```

- `A2uiSurface` 的签名（API reference）：`A2uiSurface(surfaceModel, modifier, loadingContent, errorContent, transitionSpec)`。
- 建议用 `loadingContent` 做流式占位、用 `transitionSpec` 做出现/更新动画，避免界面跳动。

## 与 M3 / Compose 的关系

- 走 **Compose Material3 1.5.0**（BOM 2026.11.01）即可拿到 `material3-a2ui` 的 Basic Catalog；
- 但 A2UI 不是 M3 规范的一部分：它是**协议 + 渲染器**，M3 只是官方提供的一套 catalog 实现。
- 主题跟 app 的 `MaterialTheme` 一致；自定义设计系统需要自己写 catalog。

## 风险与边界

1. **Alpha**：API 可能破坏性变更，release notes 明确标 alpha。
2. **安全**：虽然不执行任意代码，catalog 暴露的属性/回调仍需按「不可信输入」处理（尤其 text、链接、动作参数）。
3. **幻觉**：必须依赖 schema 校验 + errorContent 兜底，不能假设 agent 总发合法消息。
4. **可访问性**：catalog 组件的语义需自己保证，协议层不会自动补 TalkBack 语义。
5. **性能**：流式更新下要验证重组范围；官方说基于 Snapshot 只重组受影响部分，但自定义 catalog 写不好仍会整树重组。

## 来源

- [Agent-to-UI renderer for Jetpack Compose (A2UI)](https://developer.android.com/develop/ui/compose/agentic)
- [Render A2UI surfaces in your app](https://developer.android.com/develop/ui/compose/agentic/render-surfaces)
- [Create and customize component catalogs](https://developer.android.com/develop/ui/compose/agentic/manage-catalogs)
- [androidx.compose.material3.a2ui API reference](https://developer.android.com/reference/kotlin/androidx/compose/material3/a2ui/package-summary)
- Google Maven：`material3-a2ui/maven-metadata.xml`（1.0.0-alpha02，lastUpdated 20261021）

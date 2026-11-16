# Official Samples / 官方示例

> 核验日期：2026-11-15
> 上游：https://github.com/android/compose-samples

## 为什么这里不镜像源码

官方 `android/compose-samples` 当前快照是 **1017 个文件、约 100MB**（大部分是图片资源：Jetsnack 32MB、Jetchat 23MB、Reply 22MB）。

把它整体复制进知识库会带来三个问题：

1. **仓库膨胀**：一个 Markdown 知识库变成上百 MB，克隆和上传都变慢。
2. **AI 读取成本高**：上传给 ChatGPT / Claude 时，这些代码会占用大量上下文，而 99% 与当前问题无关。
3. **必然过期**：镜像的那一份永远是旧的，而 AI 引用它时无法判断新鲜度。

所以这里**只记录索引**：pinned commit、结构、关键文件路径。需要具体代码时按路径去上游取，取到的永远是最新的。

## 当前快照

<!-- BEGIN GENERATED -->
| 项 | 值 |
|---|---|
| 仓库 | `android/compose-samples` |
| Commit | `25631e8f544a9a1e3f01e296eaf1b4c28428196c` |
| 提交日期 | 2026-10-23 |
| 提交说明 | Hide markdown markers in the Jetchat message input (#1736) |
| 文件总数 | 1017（Kotlin 文件 364） |
| 体积 | 约 106MB（以图片资源为主） |

机器可读版本见 `compose-samples.manifest.json`。

## 示例应用索引

| 示例 | 文件数 | Kotlin | 体积 | 主题 / 配色入口 |
|---|---|---|---|---|
| **Jetcaster** | 341 | 155 | 7.7MB | `Jetcaster/core/designsystem/src/main/java/com/example/jetcaster/designsystem/theme/` |
| **Jetchat** | 166 | 43 | 23.2MB | `Jetchat/app/src/main/java/com/example/compose/jetchat/theme/` |
| **Jetsnack** | 149 | 52 | 31.9MB | `Jetsnack/app/src/main/java/com/example/jetsnack/ui/theme/` |
| **JetNews** | 133 | 60 | 9.2MB | `JetNews/app/src/main/java/com/example/jetnews/ui/theme/` |
| **Reply** | 106 | 27 | 21.5MB | `Reply/app/src/main/java/com/example/reply/ui/theme/` |
| **JetLagged** | 74 | 27 | 0.9MB | `JetLagged/app/src/main/java/com/example/jetlagged/ui/theme/` |
<!-- END GENERATED -->

> 上面两张表由 workflow 自动生成，不要手工编辑——改了会在下次同步时被覆盖。聚焦说明（下面的「各示例侧重不同」）手工维护。

各示例侧重不同：

- **Jetsnack**：视觉与动效完整度最高，适合看品牌化主题与转场。
- **Jetchat**：聊天界面 + 列表滚动 + 输入框，适合看交互细节。
- **Reply**：自适应布局的典型（列表-详情），适合看响应式。
- **Jetcaster**：多模块架构（`core/designsystem` 独立成模块），适合看工程结构。
- **JetNews**：含 Glance 小组件，适合看非 Compose UI 与 Compose 的协作。
- **JetLagged**：图表与数据可视化，体积最小、结构最轻。

## 怎么取某个文件

拼上游路径即可（把 `<path>` 换成上表的目录或具体文件）：

```
https://github.com/android/compose-samples/blob/25631e8f544a9a1e3f01e296eaf1b4c28428196c/<path>
```

本机 `raw.githubusercontent.com` 不通时，用 API 取原始内容：

```bash
curl -H "Accept: application/vnd.github.raw" \
  "https://api.github.com/repos/android/compose-samples/contents/<path>?ref=25631e8f544a9a1e3f01e296eaf1b4c28428196c"
```

## 使用纪律

- **示例代码 ≠ 当前规范**。示例可能停留在旧版本 API 上，判断依据始终是 `00-Metadata/version-tracking.md` 的版本快照。
- 引用示例时**带上 commit SHA**，否则读者无法判断看到的是哪个版本。
- 不要因为示例用了某个写法就认为那是推荐做法——示例的首要目标是演示特性，不是示范工程最佳实践。

## 同步

`.github/workflows/sync-samples.yml` 每周一自动更新上面的 commit 与索引，**只更新元数据、不拉源码**，改动会体现为一次 `chore: sync compose-samples index` 提交。

另外两个官方参考仓库（不自动同步，按需手查）：

- **Now in Android**：https://github.com/android/nowinandroid （完整架构实践）
- **AndroidX Material3 源码**：https://github.com/androidx/androidx/tree/androidx-main/compose/material3 （查某个默认值到底是什么，这里最准）

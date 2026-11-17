# 示例应用

把本知识库的结论落成可运行代码的最小验证场。每个示例都标注了「用了哪条规范 / 哪个组件 / 依据在哪一篇」，方便反过来核对你自己的实现。

| 示例 | 主题 | 覆盖重点 | 状态 |
|---|---|---|---|
| [`MD3Clock/`](MD3Clock/README.md) | 「时序」时间应用：闹钟 / 世界时钟 / 秒表 / 倒计时 / 日历 | Expressive 主题与弹簧动效、emphasized 字阶 + tabular figures、wavy progress、SplitButton、FAB menu、connected ButtonGroup、ContainedLoadingIndicator、Compact/Expanded 自适应导航 | ✅ 可编译、单测 11/11；未真机验证 |

## 约定

- 示例目录自包含（含 Gradle wrapper 与版本目录），可单独复制出去构建。
- 不提交构建产物：`build/`、`.gradle/`、`local.properties`、`*.apk` 均由本地生成或走 Release 附件。
- 示例里出现的官方数值，必须在 `01-Foundations/` 或 `02-Components/` 有对应来源；推不出来的写 `⚠️ 推断`，不硬凑。
- 示例不替代文档：文档负责「为什么」，示例负责「跑起来是什么样」。

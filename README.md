# Android MD3 Knowledge Base

面向 Android 原生应用开发的 Material Design 3 知识库，重点为 Google 官方设计规范与 Jetpack Compose 实现。本仓库是个人整理，不是 Google 官方发布的资料包。

## 这个库解决什么问题

让 AI（或人）在写 Android UI 时**不靠记忆编造**规范：尺寸从哪来、颜色用哪个角色、API 是不是还实验性、版本号是多少——每条都要能追到官方来源。

## 快速开始

1. 阅读 `AI-INSTRUCTIONS.md`，将其设为云端 AI 项目指令。
2. 从 `00-Metadata/sources.yaml` 进入官方资料。
3. 按下表查对应层，不要跳层。
4. 通过 `接入教程.md` 上传 GitHub 并接入 ChatGPT / Claude / Gemini。
5. 更新时记录检查日期、官方来源、版本状态和变化。

## 内容状态

| 目录 | 状态 | 说明 |
|---|---|---|
| `00-Metadata/` | ✅ 可用 | 来源索引、版本快照、术语表 |
| `01-Foundations/` | ✅ 可用 | 7 篇基础系统，含规则与检查项 |
| `02-Components/` | ✅ 可用 | 29 篇；覆盖 M3 主要组件 + Expressive 新增（Split Button / FAB Menu / Loading Indicator） |
| `03-Android-Compose/` | ✅ 可用 | 8 篇实现指南，代码为主 |
| `04-Official-Samples/` | ✅ 索引 | 官方示例的 pinned commit + 结构索引（不镜像源码，上游约 106MB） |
| `05-Design-Tokens/` | ✅ 可用 | 字阶、形状有值；颜色只存角色 |
| `06-Figma-Resources/` | 🚧 骨架 | 入口索引，不含资源文件（需人工登记） |
| `07-Development-Rules/` | ✅ 可用 | 决策规则、编码规则、评审清单 |
| `08-Original-Sources/` | ✅ 可用 | 官方链接汇总 |

## 阅读路径

| 你要做的事 | 先看 |
|---|---|
| 做一个新界面 | `07/android-ui-rules.md` → `01/design-principles.md` |
| 写 Compose 代码 | `07/compose-coding-rules.md` → `03/components-api.md` |
| 评审一个界面 | `07/ui-review-checklist.md` |
| 定主题配色 | `01/color-system.md` → `03/color-scheme.md` |
| 处理键盘 / 系统栏 | `03/window-insets.md` |
| 适配平板 / 折叠屏 | `03/adaptive-layout.md` |
| 查版本号 / API 状态 | `00/version-tracking.md` → `00/sources.yaml` |

## 资料原则

- 设计规范以 Material Design 官方网站为准。
- Android API、依赖版本和弃用状态以 Android Developers / AndroidX Release Notes 为准。
- 官方示例项目是实现参考，不自动等同于最新规范。
- 次级来源（周报、博客转述）只用于定位变更，不作为结论依据。
- 本仓库不镜像 Google 官网全部内容；网页规范需定期复核。

## 重要前提

Material Design 官网正文为动态渲染，自动化抓取通常只能拿到标题。本库中所有数值型结论都标注了核验日期与核验方式；**未能在官方页面目视确认的，都写明了「待人工确认」**——请保持这个习惯，不要把次级来源的转述改写成「Material 规定」。

尚未逐项核实的部分集中在 `00-Metadata/version-tracking.md` 的「待核验清单」，优先处理项：material3 1.5.0 是否 GA、Expressive 各组件的实验性注解是否移除。

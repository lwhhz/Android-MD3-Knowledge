# m3.material.io 站点变更（2026-11-22 复核）

> 对比基线：2026-11-16 全站抓取（375 页，<TEMP>/m3dump/）。
> 本次抓取：2026-11-22，385 页（<TEMP>/m3dump3/）。
> 方法：headless Chrome（CDP）渲染，窗口 1440×900，取 `document.body.innerText`；每页等待正文长度稳定。对比时先做「跨页高频增删行」统计以剔除全站导航/页脚漂移，再按页做行级差异；对差异页做第二轮「激活标签 + 滚动到底」复核。
> 结果：**356 页无实质变化，19 页有差异，10 页新增，0 页消失**（剩 8 页为 404/空壳，见末节）。

## 一、新增页面（10）

| 页面 | 字符 | 落点 |
|---|---:|---|
| `components/scroll-fields/overview` | 1,834 | `02-Components/scroll-fields.md` |
| `components/scroll-fields/specs` | 2,633 | `02-Components/scroll-fields.md` |
| `components/scroll-fields/guidelines` | 5,610 | `02-Components/scroll-fields.md` |
| `components/scroll-fields/accessibility` | 2,145 | `02-Components/scroll-fields.md` |
| `foundations/desktop/overview` | 3,887 | `01-Foundations/desktop.md` |
| `foundations/desktop/layout` | 2,666 | `01-Foundations/desktop.md` |
| `foundations/desktop/components` | 4,036 | `01-Foundations/desktop.md` |
| `foundations/desktop/interaction` | 5,237 | `01-Foundations/desktop.md` |
| `compose-material-3-is-stable` | 800 ⚠️ | sitemap 少写 `/blog` 前缀，实体页在 `/blog/compose-material-3-is-stable` |
| `enhance-your-component-designs-with-parametric-prototyping` | 800 ⚠️ | 同上，实体页在 `/blog/...` |

## 二、有实质差异的页面（高置信）

| 页面 | 字数变化 | 差异摘要 |
|---|---:|---|
| `components/time-pickers/guidelines` | 7,063 → 8,736 | 新增 **Scroll time picker** 与选型口径（"Use the time picker that best complements the available space…"） |
| `components/time-pickers/specs` | 5,459 → 9,930 | 新增 **Expressive variants / Baseline variants**，"Scroll time picker was added in the expressive update" |
| `components/time-pickers/accessibility` | 3,528 → 5,019 | 新增滚动选择器的键盘/读屏要求，12/24 小时制说明 |
| `components/time-pickers/overview` | 1,849 → 2,996 | 新增 M3 Expressive update 段：可内联在页面/列表/sheet，模态用 dial，均可切 input |
| `blog`（索引页） | 16,984 → 17,461 | 置顶新增 2026-11-18「Compose Material 3 1.5.0 is stable」 |
| `develop` | 2,276 → 2,359 | 新增同一篇博客条目；Compose-first 表述更新 |
| `develop/android/jetpack-compose` | 3,295 → 3,341 | 同上（新增 Nov 18 条目） |

**结论**：m3 在 11-17/18 上线 Scroll fields，并把它接进 Time pickers；`02-Components/date-time-picker.md` 已补该变体。

## 三、已复核：「文本变短」是折叠渲染，不是官方删除

第一轮 innerText 抓取时，12 个含 token / measurements 表的页面文本变短 120–600 字符（如 `0dp | Space 0`、`Fully rounded`、`State opacities`）。这类表格在 m3 默认折叠，**不能据此断言官方删除了条目**。

复核方式（2026-11-22）：对每页点击全部 `expand_all`、滚动到底（4 轮），再分别取 `innerText` 与 `textContent`，统计「11-16 有、本次基线无」的行有多少在展开后重新出现。

| 页面 | 11-16 | 基线（折叠） | 展开后 | 缺失行 | 恢复 |
|---|---:|---:|---:|---:|---:|
| `styles/shape/corner-radius-scale` | 6,018 | 5,412 | 6,018 | 35 | **35** |
| `styles/spacing/tokens` | 2,753 | 2,452 | 2,753 | 38 | **38** |
| `styles/motion/overview/specs` | 3,541 | 3,055 | 4,366 | 29 | 28 |
| `styles/motion/easing-and-duration/tokens-specs` | 5,401 | 4,915 | 6,226 | 28 | 27 |
| `styles/color/static/baseline` | 4,458 | 4,081 | 5,964 | 27 | 25 |
| `foundations/interaction/states/state-layers` | 3,135 | 2,926 | 3,135 | 13 | **13** |
| `components/search/specs` | 6,557 | 6,343 | 8,325 | 13 | 11 |
| `components/tabs/specs` | 3,855 | 3,628 | 7,440 | 17 | 15 |
| `components/dialogs/specs` | 3,649 | 3,433 | 5,983 | 17 | 15 |
| `components/snackbar/specs` | 2,204 | 1,993 | 4,439 | 17 | 15 |
| `components/floating-action-button/specs` | 4,699 | 4,476 | 6,705 | 16 | 14 |
| `components/navigation-drawer/overview` | 2,794 | 2,580 | 2,794 | 12 | **12** |

未恢复的行经逐条查看，全部是**控件 chrome**，不是内容：

- `expand_all`（展开图标字形）
- `Default, Light` / `Default, Android, Light`（token 选择器的当前状态标签）

**结论**：这 12 页**未发现官方删除条目**；此前的文本变短是折叠渲染 + 选择器标签未渲染造成。

**本次复核的边界**：只证明「行仍存在」，**没有逐格比对 token 数值是否变化**（数值随 Light/Dark、状态标签切换，本次未逐个切换取样）。要核对具体数值，仍按 `05-Design-Tokens/` 的既有基线逐项对照。

## 四、404 / 空壳页（8）

sitemap 仍收录、但实际返回 404 或只渲染出 800 字符模板：

- `blog/get-started`
- `blog/search.html`
- `building-with-m3-expressive`
- `m3-expressive-motion-theming`
- `material-is-compose-first`
- `whats-new-at-io26`
- `compose-material-3-is-stable`（实体页在 `/blog/` 下）
- `enhance-your-component-designs-with-parametric-prototyping`（同上）

## 五、方法限制

1. 首轮用 `--dump-dom` 抽取 textContent，与旧 innerText 基线不可比（会多算折叠面板的隐藏文本），已废弃；最终以 CDP innerText 为准。
2. innerText 依赖视口宽度：800×600 会少约 10% 内容，必须 1440×900 才能对齐 11-16 基线。
3. 后台标签会被节流，需 `--disable-background-*throttling`；对差异页再做「激活 + 滚动」复核可显著降低假阳性（79 → 49 → 19）。
4. 字符数只用于定位候选，不单独作为「内容变化」证据。

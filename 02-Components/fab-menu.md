# FAB Menu / 悬浮菜单（Expressive）

- 官方指南：https://m3.material.io/components/fab-menu ｜ 规格页：https://m3.material.io/components/fab-menu/specs
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-15 ｜ 核验方式：Chrome CDP 读官方 Specs 页
- 状态：**M3 无此组件，Expressive 新增**；尺寸为官方原值

## 代际归属

| 变体 | M3 | M3 Expressive |
|---|---|---|
| FAB menu | ❌ | ✅ |

官方原文：**「There's one variant of FAB menu」**——它只有一个变体，不分大小。

## 它解决什么问题

FAB 只能承载**一个**动作。当这个入口下有 2-4 个并列动作时（"新建笔记 / 新建清单 / 扫描"），以前的做法是：

- FAB + speed dial（自己拼一圈小 FAB）——不是官方模式
- FAB 打开 bottom sheet——多一层
- 底栏放多个按钮——失去 FAB 的强调

FAB menu 就是把这个缺口补上：**FAB 作为触发器，展开一组相关动作**。

## 尺寸（官方原文）

| 属性 | 值 |
|---|---|
| 关闭按钮尺寸 | **始终 56dp** |

官方特意强调了「should **always** be 56dp」——也就是说关闭态那个 FAB 和你平时用的 FAB 尺寸一致，不会因为变成菜单触发器而缩小。

其余测量（菜单项间距、展开后的排布）在官方 Measurements 图里，未取到文字值。

## 颜色

官方提供三套色：

| 色组 | 说明 |
|---|---|
| Primary | 对应 `primaryContainer` 一族 |
| Secondary | 对应 `secondaryContainer` 一族 |
| Tertiary | 对应 `tertiaryContainer` 一族 |

**颜色组只决定视觉强调，不决定语义。** 不要因为"删除"就用 tertiary 或"重要"就用 primary——动作的层级由排序和文案表达。

## 交互规则

- 点击 FAB 展开，再点一次（或点遮罩）收起。
- **关闭按钮固定在原位 56dp**，展开的菜单项围绕它排布——位置跳变会让用户点错。
- 展开的项要有文字标签（纯图标列表在 3 项以上就分不清）。
- 滚动时收起，避免菜单悬在内容上方挡住操作。
- 展开状态不跨页面保留：离开页面就收起。

## Compose 实现

**不给代码。** 这是 Expressive 新增组件，API 名称与签名在不同 material3 版本有差异（可能是 `FloatingActionButtonMenu` 一类），照抄可能编译不过。

接入前核对：

1. 组件类是否存在；
2. 是否需 `@OptIn(ExperimentalMaterial3ExpressiveApi::class)`；
3. 菜单项是独立 composable 还是普通 `FloatingActionButton` 的变体；
4. 三套颜色组的参数入口（`*Defaults` 对象）。

## 无障碍

- FAB 的 `contentDescription` 在展开/收起时应变化（"打开新建菜单" / "关闭菜单"），让读屏用户知道当前状态。
- 展开后焦点应进入第一个菜单项，收起后回到 FAB。
- 菜单项各自有完整语义（图标 + 文字，图标 `contentDescription = null`）。
- 遮罩后的内容要从焦点链排除。
- 不要只靠位置变化表达"已展开"——读屏拿不到。

## 常见错误

1. 在 M3 项目里找这个组件。
2. 关闭态 FAB 缩小成 40dp（官方是始终 56dp）。
3. 展开项只有图标没有文字。
4. 展开时 FAB 位置跳动，用户点空。
5. 菜单项超过 4 个，绕成一圈看不清。
6. 页面滚动后菜单仍然悬着。
7. 用颜色组表达语义（"红色那组是删除"）。

## 待核验

- [ ] 菜单项尺寸、间距与展开布局参数（官方为图片）
- [ ] 当前 material3 版本的确切 API 名称与签名

## 来源

- https://m3.material.io/components/fab-menu/specs
- `02-Components/fab.md`（FAB 的基础规则）
- `02-Components/menus.md`（普通菜单的适用场景）

# Material 3 Design Kit — 完整组件参数规格（componentPropertyDefinitions）

> **数据来源**：figmingo-mcp 缓存的完整文件文档（`/v1/files/{key}`，87.9MB），含 Figma 官方 `componentPropertyDefinitions`。
> 文件 Key：`42Mc2SlwS6zU3VXsHUSaYl`；共 28 个组件页、**171 个组件集、885 个参数**。
> 参数类型：VARIANT 374 · BOOLEAN 236 · TEXT 126 · INSTANCE_SWAP 117 · SLOT 32。
> 相比上一版（从变体名解析），本版含真实的默认值、全部选项、参数描述与文档链接。提取时间：2026-11-17。

---

## 页面总览

| 页面 | 组件集数 | 参数数 | 代码文件 |
|---|---:|---:|---|
| App bars | 9 | 33 | `components/M3Appbars.kt` |
| Avatars | 1 | 2 | `components/M3Avatars.kt` |
| Badges | 1 | 2 | `components/M3Badges.kt` |
| Buttons | 38 | 242 | `components/M3Buttons.kt` |
| Cards | 5 | 22 | `components/M3Cards.kt` |
| Carousel | 2 | 11 | `components/M3Carousel.kt` |
| Checkboxes | 1 | 3 | `components/M3Checkboxes.kt` |
| Chips | 5 | 34 | `components/M3Chips.kt` |
| Date & time pickers | 14 | 50 | `components/M3Datetimepickers.kt` |
| Dialogs | 4 | 21 | `components/M3Dialogs.kt` |
| Examples | 1 | 2 | `components/M3Examples.kt` |
| Lists | 12 | 92 | `components/M3Lists.kt` |
| Loading & progress | 7 | 21 | `components/M3Loadingprogress.kt` |
| Menu | 13 | 74 | `components/M3Menu.kt` |
| Navigation | 13 | 73 | `components/M3Navigation.kt` |
| Radio button | 1 | 3 | `components/M3Radiobutton.kt` |
| Search | 5 | 24 | `components/M3Search.kt` |
| Shape | 1 | 1 | `components/M3Shape.kt` |
| Sheets | 4 | 12 | `components/M3Sheets.kt` |
| Sliders | 3 | 20 | `components/M3Sliders.kt` |
| Snackbar | 3 | 9 | `components/M3Snackbar.kt` |
| Styles | 1 | 1 | —（仅内部构建块） |
| Switch | 1 | 4 | `components/M3Switch.kt` |
| Tabs | 6 | 31 | `components/M3Tabs.kt` |
| Text fields | 1 | 10 | `components/M3Textfields.kt` |
| Toolbars | 17 | 84 | `components/M3Toolbars.kt` |
| Tooltips | 1 | 2 | `components/M3Tooltips.kt` |
| Utilities | 1 | 2 | `components/M3Utilities.kt` |

---

## 各组件参数明细

## App bars

### App bar
- 组件 Key：`722fe239697fed85fc9a79a407fff6495ea93820`　节点：`58114:20565`　变体数：12

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Image#24890:11` | 实例替换 | `58114:20558` |  |
| `Show 1st trailing action#70463:0` | 布尔 | `True` |  |
| `Show 2nd trailing action#70463:9` | 布尔 | `False` |  |
| `Show 3rd trailing action#70463:18` | 布尔 | `False` |  |
| `Configuration` | 变体 | `Small-centered` | Small-centered、Small-image、Search、Small、Medium、Large |
| `Elevation` | 变体 | `Flat` | Flat、On-scroll |

### .Building Blocks/Flat/Search bar - Modified *(内部构建块)*
- 组件 Key：`8a98541d789af5ce5ff239d4ce92c62bc7b9215a`　节点：`58114:20523`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Alignment` | 变体 | `Left aligned` | Centered、Left aligned |

### .Building Blocks/On-scroll/Search bar - Modified *(内部构建块)*
- 组件 Key：`424e71fe18188a1829ed13535df12575f7b6f22b`　节点：`58114:20530`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Alignment` | 变体 | `Left aligned` | Centered、Left aligned |

### .Building Blocks/App bar/Content/Text Small *(内部构建块)*
- 组件 Key：`1616ba2372d0a13a20d040f57f02586e5adcecd8`　节点：`58114:20537`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Headline#264:22` | 文本 | `Label` |  |
| `Show-supporting-text#264:132` | 布尔 | `True` |  |
| `Supporting Text#264:137` | 文本 | `Supporting text` |  |
| `Size` | 变体 | `Small` | Small |
| `Alignment` | 变体 | `Centered` | Centered、Left |

### .Building Blocks/App bar/Content/Text Medium *(内部构建块)*
- 组件 Key：`17df4f4bc4dde035915dda8fbaa36d7322a13cd9`　节点：`58114:20544`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Headline#264:22` | 文本 | `Label` |  |
| `Show-supporting-text#264:132` | 布尔 | `True` |  |
| `Supporting Text#264:137` | 文本 | `Supporting text` |  |
| `Size` | 变体 | `Medium` | Medium |
| `Alignment` | 变体 | `Centered` | Centered、Left |

### .Building Blocks/App bar/Content/Text Large *(内部构建块)*
- 组件 Key：`99b8fbff853590e124f00d988dd52c7f606be5fc`　节点：`58114:20551`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Headline#264:22` | 文本 | `Label` |  |
| `Show-supporting-text#264:132` | 布尔 | `True` |  |
| `Supporting Text#264:137` | 文本 | `Supporting text` |  |
| `Size` | 变体 | `Large` | Large |
| `Alignment` | 变体 | `Centered` | Centered、Left |

### .Building Blocks/App bar/Content/Avatar *(内部构建块)*
- 组件 Key：`cdb4bba6b655f7286f8219bf23e59d720a77b4cf`　节点：`58114:20560`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `size` | 变体 | `Image` | Image、Monogram |

### XR/XR App Bar
- 组件 Key：`1a042467d46532a0891224b53caaf65c0afab2c3`　节点：`58108:88092`　变体数：6

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Headline#8424:0` | 文本 | `Label` |  |
| `Show 1st trailing icon#8424:7` | 布尔 | `True` |  |
| `Show 2nd trailing icon#8424:14` | 布尔 | `True` |  |
| `Show 3rd trailing icon#8424:21` | 布尔 | `True` |  |
| `Show leading icon#8424:28` | 布尔 | `True` |  |
| `Elevation` | 变体 | `Surface container` | Surface container、Surface container high、Surface container highest |
| `Configuration` | 变体 | `Center-aligned` | Center-aligned、Small |

### Bottom app bar
- 组件 Key：`0c59024002ce52a6c764dfd56ff8a9a0763be99b`　节点：`51159:5105`　变体数：4

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show FAB#52994:0` | 布尔 | `True` |  |
| `Icons` | 变体 | `1` | 1、2、3、4 |

---

## Avatars

### Generic avatar
- 组件 Key：`da5bd0c208050c8e8d4fbb429cb0496a4a0d0e7b`　节点：`50731:13725`　变体数：3

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Letter#55581:28` | 文本 | `A` |  |
| `Style` | 变体 | `Avatar` | Check、Monogram、Avatar |

---

## Badges

### Badges
- 组件 Key：`7badabe235df27b44f08286799e71f5dcc94775d`　节点：`51592:4768`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Badge label#52867:8` | 文本 | `3` |  |
| `Size` | 变体 | `Large` | Large、Small |

---

## Buttons

### Segmented button
- 组件 Key：`f1a1d8c398c2e1e1867e55ad93b4e741f5c1322b`　节点：`53923:36615`　变体数：16

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Segments` | 变体 | `2` | 2、3、4、5 |
| `Density` | 变体 | `0` | 0、-1、-2、-3 |

### Building Blocks/Segmented button/Button segment (start) *(内部构建块)*
- 组件 Key：`b844710a18e9e605cba25576ce26c17c56dd40db`　节点：`53923:36817`　变体数：27

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#52999:1116` | 实例替换 | `54616:25411` |  |
| `Label text#53923:664` | 文本 | `Label` |  |
| `Configuration` | 变体 | `Label only` | Label only、Label & icon、Icon only |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |
| `Selected` | 变体 | `True` | True、False |

### Building Blocks/Segmented button/Button segment (middle) *(内部构建块)*
- 组件 Key：`ebc962c614be76e38f9b9cdb284f3a76b6764285`　节点：`53923:36691`　变体数：27

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#52999:1088` | 实例替换 | `54616:25411` |  |
| `Label text#53923:636` | 文本 | `Label` |  |
| `Configuration` | 变体 | `Label & icon` | Label only、Label & icon、Icon only |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |
| `Selected` | 变体 | `True` | True、False |

### Building Blocks/Segmented button/Button segment (end) *(内部构建块)*
- 组件 Key：`510b1bd5fea52b1d4fd378030d4881f49d28b6ee`　节点：`53923:36943`　变体数：27

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#52999:1144` | 实例替换 | `54616:25411` |  |
| `Label text#53923:692` | 文本 | `Label` |  |
| `Configuration` | 变体 | `Label only` | Label only、Label & icon、Icon only |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |
| `Selected` | 变体 | `True` | True、False |

### Toggle button
- 组件 Key：`9d6f2a3135cdf9e17c605a538747aff109cbedaf`　节点：`57994:2328`　变体数：100

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Label text#58653:1365` | 文本 | `Label` |  |
| `Show icon#58653:1466` | 布尔 | `True` |  |
| `Icon#58653:1567` | 实例替换 | `54616:25411` |  |
| `Show focus indicator#58653:1668` | 布尔 | `False` |  |
| `Icon (selected)#58680:2408` | 实例替换 | `54616:25409` |  |
| `Type` | 变体 | `Round` | Round、Square |
| `Size` | 变体 | `Small` | XSmall、Small、Medium、Large、XLarge |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |
| `Selected` | 变体 | `False` | False、True |

### Button
- 组件 Key：`8464a868245be874c9b5d9fba279cffc18be2e83`　节点：`57994:2227`　变体数：50

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Label text#58653:0` | 文本 | `Label` |  |
| `Show icon#58653:51` | 布尔 | `True` |  |
| `Icon#58653:102` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58654:2072` | 布尔 | `False` |  |
| `Type` | 变体 | `Round` | Round、Square |
| `Size` | 变体 | `Small` | XSmall、Small、Medium、Large、XLarge |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Button - text
- 组件 Key：`a35094a02f3262a5f9b4f7e6941d1680c132d7fa`　节点：`58650:8094`　变体数：50

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Label text#58650:0` | 文本 | `Label` |  |
| `Icon#58650:51` | 实例替换 | `54616:25409` |  |
| `Show icon#58650:102` | 布尔 | `True` |  |
| `Show focus indicator#58654:2276` | 布尔 | `False` |  |
| `Type` | 变体 | `Round` | Round、Square |
| `Size` | 变体 | `Small` | XSmall、Small、Medium、Large、XLarge |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Button - elevated
- 组件 Key：`ce7327e2b021a867bce05811c0c518b8d2aa3034`　节点：`58650:9294`　变体数：50

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Label text#58650:153` | 文本 | `Label` |  |
| `Show icon#58650:204` | 布尔 | `True` |  |
| `Icon#58650:255` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58654:2225` | 布尔 | `False` |  |
| `Type` | 变体 | `Round` | Round、Square |
| `Size` | 变体 | `Small` | XSmall、Small、Medium、Large、XLarge |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Button - outline
- 组件 Key：`0db10443509071aca67112d187e904bf242b6946`　节点：`58650:10213`　变体数：50

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Label text#58650:306` | 文本 | `Label` |  |
| `Show icon#58650:357` | 布尔 | `True` |  |
| `Icon#58650:408` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58654:2174` | 布尔 | `False` |  |
| `Type` | 变体 | `Round` | Round、Square |
| `Size` | 变体 | `Small` | XSmall、Small、Medium、Large、XLarge |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Presssed、Disabled |

### Button - tonal
- 组件 Key：`164b0e224caf7b135d4c508296f907769bdba627`　节点：`58651:11237`　变体数：50

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Label text#58651:459` | 文本 | `Label` |  |
| `Show icon#58651:510` | 布尔 | `True` |  |
| `Icon#58651:561` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58654:2123` | 布尔 | `False` |  |
| `Type` | 变体 | `Round` | Round、Square |
| `Size` | 变体 | `Small` | Large、Medium、Small、XLarge、XSmall |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Toggle button - elevated
- 组件 Key：`8e72bf96de6c87bbae013a685248956eecd66d88`　节点：`58653:13968`　变体数：100

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Label text#58653:153` | 文本 | `Label` |  |
| `Show icon#58653:254` | 布尔 | `True` |  |
| `Icon#58653:355` | 实例替换 | `54616:25411` |  |
| `Show focus indicator#58653:1769` | 布尔 | `False` |  |
| `Icon (selected)#58680:2711` | 实例替换 | `54616:25409` |  |
| `Type` | 变体 | `Round` | Round、Square |
| `Size` | 变体 | `Small` | XSmall、Small、Medium、Large、XLarge |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |
| `Selected` | 变体 | `False` | False、True |

### Toggle button - outline
- 组件 Key：`a622670ba6486552d475f68c7ccc81c0823c361a`　节点：`58653:16237`　变体数：100

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Label text#58653:456` | 文本 | `Label` |  |
| `Show icon#58653:557` | 布尔 | `True` |  |
| `Icon#58653:658` | 实例替换 | `54616:25411` |  |
| `Show focus indicator#58653:1870` | 布尔 | `False` |  |
| `Icon (selected)#58680:2610` | 实例替换 | `54616:25409` |  |
| `Type` | 变体 | `Round` | Round、Square |
| `Size` | 变体 | `Small` | XSmall、Small、Medium、Large、XLarge |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |
| `Selected` | 变体 | `False` | False、True |

### Toggle button - tonal
- 组件 Key：`cd26245b0cdf933ed441be6a34913f105d83ead7`　节点：`58653:17539`　变体数：100

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Label text#58653:1062` | 文本 | `Label` |  |
| `Show icon#58653:1163` | 布尔 | `True` |  |
| `Icon#58653:1264` | 实例替换 | `54616:25411` |  |
| `Show focus indicator#58653:1971` | 布尔 | `False` |  |
| `Icon (selected)#58680:2509` | 实例替换 | `54616:25409` |  |
| `Type` | 变体 | `Round` | Round、Square |
| `Size` | 变体 | `Small` | XSmall、Small、Medium、Large、XLarge |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |
| `Selected` | 变体 | `False` | False、True |

### Icon button
- 组件 Key：`e28ec2fd654fc65428b18e8267eb16aa7290c9cf`　节点：`57994:10081`　变体数：150

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#58665:2780` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58735:726` | 布尔 | `False` |  |
| `Type` | 变体 | `Round` | Round、Square |
| `Size` | 变体 | `Small` | XSmall、Small、Medium、Large、XLarge |
| `Width` | 变体 | `Default` | Narrow、Default、Wide |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Icon button - standard
- 组件 Key：`6185eb50d4a1137cefe5e288b4f839c51a74f872`　节点：`58663:30358`　变体数：150

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#58665:2327` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58735:1179` | 布尔 | `False` |  |
| `Type` | 变体 | `Round` | Round、Square |
| `Size` | 变体 | `Small` | Large、Medium、Small、XLarge、XSmall |
| `Width` | 变体 | `Default` | Narrow、Default、Wide |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Icon button - outline
- 组件 Key：`ea1e94592886dedb7ba40aeacac3ead2d9d21fba`　节点：`58663:30359`　变体数：150

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#58665:2478` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58735:1028` | 布尔 | `False` |  |
| `Type` | 变体 | `Round` | Round、Square |
| `Size` | 变体 | `Small` | Large、Medium、Small、XLarge、XSmall |
| `Width` | 变体 | `Default` | Narrow、Default、Wide |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Icon button - tonal
- 组件 Key：`8ed818caa1e938ae001370fdbde11e49b80b7551`　节点：`58665:33976`　变体数：150

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#58665:2629` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58735:877` | 布尔 | `False` |  |
| `Type` | 变体 | `Round` | Round、Square |
| `Size` | 变体 | `Small` | Large、Medium、Small、XLarge、XSmall |
| `Width` | 变体 | `Default` | Narrow、Default、Wide |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Icon button togglable
- 组件 Key：`0604e7a8cfff80937690a1ffdb19f5f0cb887d6b`　节点：`57994:10368`　变体数：300

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#58675:0` | 实例替换 | `54616:25411` |  |
| `Icon (selected)#58675:301` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58735:2534` | 布尔 | `False` |  |
| `Type` | 变体 | `Round` | Round、Square |
| `Size` | 变体 | `Small` | XSmall、Small、Medium、Large、XLarge |
| `Width` | 变体 | `Default` | Narrow、Default、Wide |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Icon button togglable - tonal
- 组件 Key：`920089e551cc3e387f67ce942a64f8af809e8fc9`　节点：`58668:48104`　变体数：300

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#58675:602` | 实例替换 | `54616:25411` |  |
| `Icon (selected)#58675:903` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58735:2233` | 布尔 | `False` |  |
| `Type` | 变体 | `Round` | Round、Square |
| `Size` | 变体 | `Small` | XSmall、Small、Medium、Large、XLarge |
| `Width` | 变体 | `Default` | Narrow、Default、Wide |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Icon button togglable - outline
- 组件 Key：`21aa3bbcc8d99c2ae38c43e3b0f143c7cbbbe44d`　节点：`58668:45603`　变体数：300

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#58675:1204` | 实例替换 | `54616:25411` |  |
| `Icon (selected)#58675:1505` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58735:1932` | 布尔 | `False` |  |
| `Type` | 变体 | `Round` | Round、Square |
| `Size` | 变体 | `Small` | XSmall、Small、Medium、Large、XLarge |
| `Width` | 变体 | `Default` | Narrow、Default、Wide |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Icon button togglable - standard
- 组件 Key：`e6ec37ccdb644a1c2fd001edfe83cdcc8813b5f5`　节点：`58665:42416`　变体数：300

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#58675:1806` | 实例替换 | `54616:25411` |  |
| `Icon (selected)#58675:2107` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58735:1631` | 布尔 | `False` |  |
| `Type` | 变体 | `Round` | Round、Square |
| `Size` | 变体 | `Small` | XSmall、Small、Medium、Large、XLarge |
| `Width` | 变体 | `Default` | Narrow、Default、Wide |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Split button
- 组件 Key：`820882ce4704c77751ab3ee4ac54197657ea759e`　节点：`57994:15751`　变体数：180

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Leading icon#27846:0` | 实例替换 | `54616:25409` |  |
| `Show leading icon#27846:41` | 布尔 | `True` |  |
| `Leading label text#28041:110` | 文本 | `Label` |  |
| `Show focus indicator#298033:0` | 布尔 | `False` |  |
| `Size` | 变体 | `Small` | XSmall、Small、Medium、Large、XLarge |
| `Color` | 变体 | `Filled` | Filled、Tonal、Elevated、Outlined |
| `Leading state` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |
| `Trailing state` | 变体 | `Enabled` | Enabled、Focused、Hovered、Pressed、Disabled、Selected |

### .Building Blocks/FAB Menu/Tertiary/Segment *(内部构建块)*
- 组件 Key：`2d217b7afb79dc244ca5a183338de31c2bb9cffb`　节点：`57998:42889`　变体数：4

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#1394:5` | 实例替换 | `54616:25409` |  |
| `Show icon#1394:6` | 布尔 | `True` |  |
| `Label text#1394:7` | 文本 | `Label` |  |
| `Show focus indicator#58700:3308` | 布尔 | `False` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed |

### .Building Blocks/FAB Menu/Tertiary/FAB *(内部构建块)*
- 组件 Key：`1a08bf204e6d53248c3b2c1596a6f45785c3d0d3`　节点：`57998:42907`　变体数：4

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show focus indicator#58700:3303` | 布尔 | `False` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed |

### .Building Blocks/FAB Menu/Secondary/Segment *(内部构建块)*
- 组件 Key：`106f883d89cb496afb0310391d2ff3f39e0bafcd`　节点：`57998:42921`　变体数：4

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show icon#1394:1` | 布尔 | `True` |  |
| `Icon#1394:2` | 实例替换 | `54616:25409` |  |
| `Label text#1394:3` | 文本 | `Label` |  |
| `Show focus indicator#58700:3313` | 布尔 | `False` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed |

### .Building Blocks/FAB Menu/Secondary/FAB *(内部构建块)*
- 组件 Key：`082ec316ac5f755a7716d9570e562b555328d1ed`　节点：`57998:42939`　变体数：4

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show focus indicator#58700:3298` | 布尔 | `False` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed |

### .Building Blocks/FAB Menu/Primary/Segment *(内部构建块)*
- 组件 Key：`1faed4b449aaacec6baff0e6e9a4f679b4d11e7f`　节点：`57998:42953`　变体数：4

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#1394:9` | 实例替换 | `54616:25409` |  |
| `Show icon#1394:10` | 布尔 | `True` |  |
| `Label text#1394:11` | 文本 | `Label` |  |
| `Show focus indicator#58700:3318` | 布尔 | `False` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed |

### .Building Blocks/FAB Menu/Primary/FAB *(内部构建块)*
- 组件 Key：`5c0ae6659cb2613de395f9ef3ad817a647931adf`　节点：`57998:42971`　变体数：4

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show focus indicator#58700:3293` | 布尔 | `False` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed |

### FAB menu
- 组件 Key：`4e19a4220eab3c866090097b0c261c380272feba`　节点：`57998:42986`　变体数：3

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show 4th segment#58627:0` | 布尔 | `False` |  |
| `Show 5th segment#58627:16` | 布尔 | `False` |  |
| `Show 6th segment#58627:32` | 布尔 | `False` |  |
| `Show 3rd segment#58627:48` | 布尔 | `True` |  |
| `Color` | 变体 | `Primary container` | Primary container、Secondary container、Tertiary container |
| `Segments` | 变体 | `3` | 3 |

### Extended FAB
- 组件 Key：`e34aa566674de6403c190c12221d6f85dacf0f2a`　节点：`57998:43095`　变体数：72

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Label text#1187:0` | 文本 | `Label` |  |
| `Icon#1187:2` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58687:2885` | 布尔 | `False` |  |
| `Size` | 变体 | `Small` | Small、Medium、Large |
| `Color` | 变体 | `Primary container` | Primary container、Secondary container、Tertiary container、Primary、Secondary、Tertiary |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed |

### FAB
- 组件 Key：`65671796e8f16a55f9a683206e4fca79c93ca3d3`　节点：`57998:43426`　变体数：72

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show focus indicator#58687:2812` | 布尔 | `False` |  |
| `Icon#69457:42` | 实例替换 | `54616:25409` |  |
| `Size` | 变体 | `Default` | Default、Medium、Large |
| `Color` | 变体 | `Primary container` | Primary container、Secondary container、Tertiary container、Primary、Secondary、Tertiary |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed |

### Connected button group
- 组件 Key：`a597c3b26c5ed42f89108d0089cbba0aa9fdd11b`　节点：`57998:47111`　变体数：10

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show 4th segment#58630:64` | 布尔 | `False` |  |
| `Show 5th segment#58631:105` | 布尔 | `False` |  |
| `Show 3rd segment#58631:146` | 布尔 | `True` |  |
| `Type` | 变体 | `Round` | Round、Square |
| `Size` | 变体 | `XSmall` | XSmall、Small、Medium、Large、XLarge |

### Building Blocks/Button group/Connected segments/XSmall *(内部构建块)*
- 组件 Key：`7c65f687b7c3b30d319234dc4895d5f79bd8088c`　节点：`57998:46886`　变体数：10

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#16110:0` | 实例替换 | `54616:25411` |  |
| `Label text#19551:0` | 文本 | `Label` |  |
| `Show icon#27991:0` | 布尔 | `True` |  |
| `Show label text#27991:11` | 布尔 | `True` |  |
| `Show focus indicator#58000:0` | 布尔 | `False` |  |
| `Icon (selected)#58000:55` | 实例替换 | `54616:25409` |  |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Building Blocks/Button group/Connected segments/Small *(内部构建块)*
- 组件 Key：`1043933f5ba35d185c2b5d44c075a4e9fc0ce925`　节点：`57998:46931`　变体数：10

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#16110:0` | 实例替换 | `54616:25411` |  |
| `Label text#19551:0` | 文本 | `Label` |  |
| `Show label text#27991:22` | 布尔 | `True` |  |
| `Show icon#27991:33` | 布尔 | `True` |  |
| `Show focus indicator#58000:11` | 布尔 | `False` |  |
| `Icon (selected)#58000:66` | 实例替换 | `54616:25409` |  |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Building Blocks/Button group/Connected segments/Medium *(内部构建块)*
- 组件 Key：`a497df85fc3c9e8d78d5a2545fa854564ede5c4d`　节点：`57998:46976`　变体数：10

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#16110:0` | 实例替换 | `54616:25411` |  |
| `Label text#19551:0` | 文本 | `Label` |  |
| `Show icon#27991:44` | 布尔 | `True` |  |
| `Show label text#27991:55` | 布尔 | `True` |  |
| `Show focus indicator#58000:22` | 布尔 | `False` |  |
| `Icon (selected)#58000:77` | 实例替换 | `54616:25409` |  |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Building Blocks/Button group/Connected segments/Large *(内部构建块)*
- 组件 Key：`2fa13671ce6b5b64f7f80158db185ea098737495`　节点：`57998:47021`　变体数：10

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#16110:0` | 实例替换 | `54616:25411` |  |
| `Label text#19551:0` | 文本 | `Label` |  |
| `Show label text#27991:66` | 布尔 | `True` |  |
| `Show icon#27991:77` | 布尔 | `True` |  |
| `Show focus indicator#58000:33` | 布尔 | `False` |  |
| `Icon (selected)#58000:88` | 实例替换 | `54616:25409` |  |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Building Blocks/Button group/Connected segments/Xlarge *(内部构建块)*
- 组件 Key：`25c8b114e9e27bfb07a86c25a58c8699351fb913`　节点：`57998:47066`　变体数：10

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#16110:0` | 实例替换 | `54616:25411` |  |
| `Label text#19551:0` | 文本 | `Label` |  |
| `Show label text#27991:88` | 布尔 | `True` |  |
| `Show icon#27991:99` | 布尔 | `True` |  |
| `Show focus indicator#58000:44` | 布尔 | `False` |  |
| `Icon (selected)#58000:99` | 实例替换 | `54616:25409` |  |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Standard button group
- 组件 Key：`90fc1a4fbd10ddfbe8e58cba8aad070903a0e08f`　节点：`58424:8117`　变体数：60

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show 5th button#29378:61` | 布尔 | `False` |  |
| `Show 6th button#29378:122` | 布尔 | `False` |  |
| `Show 7th button#29498:183` | 布尔 | `False` |  |
| `Type` | 变体 | `Round` | Round、Square |
| `Size` | 变体 | `XSmall` | XSmall、Small、Medium、Large、XLarge |
| `Color` | 变体 | `Filled` | Filled、Tonal、Outline |
| `Button type` | 变体 | `Icon` | Icon、Label |

---

## Cards

### Stacked card
- 组件 Key：`3de8c7f5929e3e969e39f735deecb0433a0430c3`　节点：`52346:27573`　变体数：6

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Slot swap#58710:0` | 实例替换 | `57314:35492` |  |
| `Show secondary action#58710:14` | 布尔 | `True` |  |
| `Header text#58710:21` | 文本 | `Header` |  |
| `Subhead text#58710:28` | 文本 | `Subhead` |  |
| `Title text#58710:35` | 文本 | `Title` |  |
| `Subtitle text#58710:42` | 文本 | `Subtitle` |  |
| `Supporting text#58710:49` | 文本 | `Lorem ipsum dolor sit amet, consectetur ` |  |
| `Content#59251:8` | 插槽 | `—` |  |
| `Style` | 变体 | `Outlined` | Outlined、Elevated、Filled |
| `Layout` | 变体 | `Media & text` | Slot、Media & text |

### Horizontal card
- 组件 Key：`7d257cb08942019e4346551b34144f7dddf07578`　节点：`52350:27876`　变体数：6

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Slot swap#58710:7` | 实例替换 | `57314:35492` |  |
| `Header text#58710:56` | 文本 | `Header` |  |
| `Subhead text#58710:63` | 文本 | `Subhead` |  |
| `Content#59251:15` | 插槽 | `—` |  |
| `Style` | 变体 | `Outlined` | Outlined、Elevated、Filled |
| `Layout` | 变体 | `Media & text` | Slot、Media & text |

### .Building Blocks/Card states/Outlined *(内部构建块)*
- 组件 Key：`d25106a297dd2fe921ec59a2f7435d65080277fc`　节点：`52347:27855`　变体数：5

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show focus indicator#58718:70` | 布尔 | `False` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Dragged |

### .Building Blocks/Card states/Elevated *(内部构建块)*
- 组件 Key：`ecc457d6762d178342217c4d91325f24fce7b43e`　节点：`52350:27635`　变体数：5

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show focus indicator#58718:76` | 布尔 | `False` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Dragged |

### .Building Blocks/Card states/Filled *(内部构建块)*
- 组件 Key：`6e9869014f336ef8562f3a7240889236b5b86f53`　节点：`52350:27728`　变体数：5

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show focus indicator#58718:82` | 布尔 | `False` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Dragged |

---

## Carousel

### Carousel - Full screen
- 组件 Key：`e1e3eaa87e5351281d1e444518c32b6daf709987`　节点：`54577:26060`　变体数：1

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Context` | 变体 | `Mobile` | Mobile |
| `Layout` | 变体 | `Full screen` | Full screen |

### Carousel
- 组件 Key：`86b546843be243ffea2ac3a0b756fe724e545d5b`　节点：`53912:27480`　变体数：10

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Item 1#58973:0` | 实例替换 | `58973:3163` |  |
| `Item 2#58973:1` | 实例替换 | `58973:3168` |  |
| `Item 3#58973:2` | 实例替换 | `58973:3173` |  |
| `Item 4#58973:3` | 实例替换 | `58973:3183` |  |
| `Item 5#58973:4` | 实例替换 | `58973:3178` |  |
| `Show 4th item#58973:5` | 布尔 | `True` |  |
| `Show 5th item#58973:6` | 布尔 | `True` |  |
| `Context` | 变体 | `Mobile` | Mobile、Tablet |
| `Layout` | 变体 | `Hero` | Hero、Center-aligned hero、Multi-browse、Uncontained、Multi-aspect ratio |

---

## Checkboxes

### Checkboxes
- 组件 Key：`87cceb9efb2c4c930ce858e6bc06415a889e7465`　节点：`51859:5628`　变体数：30

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show focus indicator#58718:88` | 布尔 | `False` |  |
| `Type` | 变体 | `Selected` | Selected、Unselected、Indeterminate、Error unselected、Error indeterminate、Error selected |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

---

## Chips

### Suggestion chip
- 组件 Key：`e4c91bc69e1066fc3de4124b1f5b65d5522dc8b9`　节点：`53923:28679`　变体数：48

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Leading icon#52999:796` | 实例替换 | `54616:25441` |  |
| `Label text#53923:297` | 文本 | `Label` |  |
| `Show focus indicator#58718:314` | 布尔 | `False` |  |
| `Style` | 变体 | `Outlined` | Outlined、Elevated |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Dragged、Disabled |
| `Selected` | 变体 | `False` | False、True |
| `Show icon` | 变体 | `False` | False、True |

### Filter chip
- 组件 Key：`7e3b53cb9d28794d27996116fd4fe5184176fc06`　节点：`53923:28270`　变体数：96

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Trailing icon#52999:602` | 实例替换 | `54616:25429` |  |
| `Leading icon#52999:699` | 实例替换 | `54616:25441` |  |
| `Label text#53923:200` | 文本 | `Label` |  |
| `Show focus indicator#58718:217` | 布尔 | `False` |  |
| `Style` | 变体 | `Outlined` | Outlined、Elevated |
| `Configuration` | 变体 | `Label only` | Label only、Label & leading icon |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Dragged、Disabled |
| `Selected` | 变体 | `False` | False、True |
| `Show trailing icon` | 变体 | `False` | False、True |

### Assistive chip
- 组件 Key：`3366d96d8ca5dcc233bce842b5e0d6e06f86ec67`　节点：`53923:28089`　变体数：48

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Leading icon#52999:308` | 实例替换 | `54616:25441` |  |
| `Branded icon#52999:357` | 实例替换 | `50994:15236` |  |
| `Favicon#52999:406` | 实例替换 | `52999:31408` |  |
| `Label text#53923:151` | 文本 | `Label` |  |
| `Show focus indicator#58718:168` | 布尔 | `False` |  |
| `Style` | 变体 | `Outlined` | Outlined、Elevated |
| `Configuration` | 变体 | `Label only` | Label only、Label & icon、Label & favicon、Label & brand icon |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Dragged、Disabled |

### Input chip
- 组件 Key：`9340da01632fc24b62d964378b93c89438e882b7`　节点：`53923:27888`　变体数：48

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Leading icon#52999:63` | 实例替换 | `54616:25441` |  |
| `Avatar#52999:112` | 实例替换 | `50731:13713` |  |
| `Label text#53923:102` | 文本 | `Label` |  |
| `Show focus indicator#58718:119` | 布尔 | `False` |  |
| `Configuration` | 变体 | `Label only` | Label only、Label & leading icon、Label & avatar |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Dragged |
| `Selected` | 变体 | `False` | False、True |
| `Show closing icon` | 变体 | `false` | false、true |

### Chip groups
- 组件 Key：`678b2955f11bc28f23d6621cbdda2f6994659271`　节点：`57376:5501`　变体数：8

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Type` | 变体 | `Input chips` | Input chips、Assistive chips、Filter chips、Suggestion chips |
| `Layout` | 变体 | `Single row - scrollable` | Single row - scrollable、Multiple rows - overflow |

---

## Date & time pickers

### .Building Blocks/Local M3 calendar cell *(内部构建块)*
- 组件 Key：`d650925bf01ce59413558005fe5a4607ea713b9c`　节点：`51954:18817`　变体数：22

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Extra right#3:0` | 布尔 | `True` |  |
| `Extra left#3:10` | 布尔 | `True` |  |
| `Start range#5:0` | 布尔 | `False` |  |
| `End range#5:13` | 布尔 | `False` |  |
| `Date#2473:0` | 文本 | `00` |  |
| `Show focus indicator#58718:363` | 布尔 | `False` |  |
| `Type` | 变体 | `Default` | Default、Today、Selected、Selected (Middle)、Null、Prev/Next |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled、Hovere |

### .Building Blocks/Year *(内部构建块)*
- 组件 Key：`ff891ad725a7cab27d6a321ecdd2daf2a7897a05`　节点：`51954:18918`　变体数：9

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Year#2473:21` | 文本 | `2025` |  |
| `Show focus indicator#58718:392` | 布尔 | `False` |  |
| `Selected` | 变体 | `False` | True、False |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### .Building Blocks/Menu button *(内部构建块)*
- 组件 Key：`cd5df39929421f919397e51ada1c5ca328f27d56`　节点：`51954:18957`　变体数：5

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Label text#53939:22` | 文本 | `Label` |  |
| `Show focus indicator#58718:386` | 布尔 | `False` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Docked input date picker [desktop]
- 组件 Key：`e9929eed76e3f37a14ef36616ac18fde35b9c538`　节点：`51954:18567`　变体数：3

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show Clear button#54584:7` | 布尔 | `False` |  |
| `Type` | 变体 | `Day` | Day、Month、Year |

### Input date picker
- 组件 Key：`56d4dd82d2e824a0894f92bf74fcf134b1e5c486`　节点：`51954:18540`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Supporting text#53001:16` | 文本 | `Select date` |  |
| `Headline#53001:19` | 文本 | `Enter date` |  |
| `Headline (plural)#53001:22` | 文本 | `Enter dates` |  |
| `Show clear button#54584:4` | 布尔 | `False` |  |
| `Type` | 变体 | `Single input` | Single input、Range |

### Modal date picker
- 组件 Key：`ada45662ef4094daeadd85446170a1ed749bc701`　节点：`51954:18136`　变体数：3

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Headline#53001:0` | 文本 | `Mon, Aug 17` |  |
| `Supporting text#53001:4` | 文本 | `Select date` |  |
| `Supporting text (range)#53001:8` | 文本 | `Depart - Return dates` |  |
| `Headline (range)#53001:12` | 文本 | `Aug 17 – Aug 23` |  |
| `Show clear button#54584:0` | 布尔 | `True` |  |
| `Type` | 变体 | `Day` | Day、Year、Full-screen (range) |

### .Building Blocks/Hour *(内部构建块)*
- 组件 Key：`ac4fa0aecd721693c62f749f2f6e300f1f1facc5`　节点：`52949:28086`　变体数：6

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Hour Line#2017:0` | 实例替换 | `52949:28219` |  |
| `Hour text#54015:0` | 文本 | `00` |  |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused |

### .Building Blocks/Input *(内部构建块)*
- 组件 Key：`0702f1704b33ebe140f39616282593ae8028ded2`　节点：`52949:28114`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Time selector label text#52955:0` | 文本 | `00` |  |
| `Show focus indicator#58718:402` | 布尔 | `False` |  |
| `Selected` | 变体 | `False` | False、True |

### .Building Blocks/Direct Input (keyboard) input *(内部构建块)*
- 组件 Key：`0363a3bbb2fe85f39f7d24ac1a085dda5a896621`　节点：`52949:28121`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Time selector label text#52955:3` | 文本 | `00` |  |
| `Show focus indicator#58719:405` | 布尔 | `False` |  |
| `Selected` | 变体 | `False` | True、False |

### .Building Blocks/Period Selector *(内部构建块)*
- 组件 Key：`339bc812e7209848ce0f154fd37a5890d13bda47`　节点：`52949:28132`　变体数：6

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show focus indicator#58719:408` | 布尔 | `False` |  |
| `AM/PM` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused |

### .Building Blocks/Period Selector - Horizontal *(内部构建块)*
- 组件 Key：`e55a5490b9adf89f92caec6d6bfc7093181f7519`　节点：`52949:28175`　变体数：6

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show focus indicator#58719:415` | 布尔 | `False` |  |
| `AM/PM` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused |

### .Building Blocks/hour-line *(内部构建块)*
- 组件 Key：`0f6b83962c72d7e906fd4f3fdb654c1dc04a9009`　节点：`52949:28218`　变体数：24

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Line-angle` | 变体 | `1` | 1、10、11、12、13、14、15、16、17、18… |

### Dial picker
- 组件 Key：`151a7dab83d7e790d4a8a11ef047cdd4d77cfd8f`　节点：`52949:27916`　变体数：4

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Headline#52957:6` | 文本 | `Select time` |  |
| `Format` | 变体 | `12 hour` | 12 hour、24 hour |
| `Orientation` | 变体 | `Vertical` | Vertical、Horizontal |

### Keyboard picker
- 组件 Key：`7e2ae992a75df14e2977f601c056d68674ba295c`　节点：`52949:28053`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Headline#52957:11` | 文本 | `Enter time` |  |
| `Format` | 变体 | `12 hour` | 12 hour、24 hour |

---

## Dialogs

### Basic dialog
- 组件 Key：`3746b8c1bceabb04305826dc80376db5d368b28c`　节点：`50723:10929`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show divider#52161:0` | 布尔 | `False` |  |
| `Title#56092:0` | 文本 | `Basic dialog title` |  |
| `Supporting text#56092:9` | 文本 | `A dialog is a type of modal window that ` |  |
| `Content#59267:0` | 插槽 | `—` |  |
| `Icon` | 变体 | `False` | True、False |

### List dialog
- 组件 Key：`918cadcc1436c26ef0fcb368b6246f7dbb032d46`　节点：`52112:28937`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Title#56092:3` | 文本 | `Dialog title` |  |
| `Supporting text#56092:12` | 文本 | `A dialog is a type of modal window that ` |  |
| `Content#59267:11` | 插槽 | `—` |  |
| `Icon` | 变体 | `False` | False、True |

### Scrollable list dialog
- 组件 Key：`f3917391280ddb36dde49758b0a26630735a29c5`　节点：`52112:29186`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Title#56092:6` | 文本 | `Dialog title` |  |
| `Supporting text#56092:15` | 文本 | `A dialog is a type of modal window that ` |  |
| `Content#59267:8` | 插槽 | `—` |  |
| `Icon` | 变体 | `False` | False、True |

### XR/XR Dialog
- 组件 Key：`185b5159acfbe4be23a8b4780f2358f8d5d240a7`　节点：`58108:87558`　变体数：4

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Headline text#6842:0` | 文本 | `Basic dialog title` |  |
| `Supporting text#6842:5` | 文本 | `A dialog is a type of modal window that ` |  |
| `Icon#6842:10` | 实例替换 | `58634:72783` |  |
| `Show secondary action#6842:15` | 布尔 | `True` |  |
| `Show divider#6842:45` | 布尔 | `False` |  |
| `Content#59267:3` | 插槽 | `—` |  |
| `Elevation` | 变体 | `Surface container high` | Surface container high、Surface container highest |
| `Show icon` | 变体 | `True` | True、False |

---

## Examples

### Examples/Layout grid
- 组件 Key：`1598f83c7d5014d865077ef27f72721c7f013090`　节点：`56384:120`　变体数：11

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Window size class` | 变体 | `1. Compact (0-599dp)` | 1. Compact (0-599dp)、2. Medium (600-839dp)、3. Expanded (840-1199p)、4. Large (1200-1599dp)、5. Extra-large (1600+dp) |
| `Layout regions` | 变体 | `None` | None、Navigation region、Navigation expanded |

---

## Lists

### List item/List Item: -4 Density (baseline)
- 组件 Key：`5b25e711e4e34b7d1fa2d71937cf3d27a4880f4c`　节点：`51964:68562`　变体数：197

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `State-layer#67:286` | 实例替换 | `51964:70957` |  |
| `Show trailing supporting text#70:0` | 布尔 | `False` |  |
| `Show divider#70:113` | 布尔 | `False` |  |
| `Show leading avatar#2891:0` | 布尔 | `True` |  |
| `Show leading icon#2891:233` | 布尔 | `True` |  |
| `Show image#2892:0` | 布尔 | `True` |  |
| `Show video thumbnail#2892:233` | 布尔 | `True` |  |
| `Show checkbox#2892:466` | 布尔 | `True` |  |
| `Show radio button#2892:699` | 布尔 | `True` |  |
| `Show switch#2892:932` | 布尔 | `True` |  |
| `Swap divider type#52066:457` | 实例替换 | `51816:5860` |  |
| `Overline#53046:2742` | 文本 | `Overline` |  |
| `Headline#53046:2940` | 文本 | `List item` |  |
| `Trailing supporting text#53046:3138` | 文本 | `100+` |  |
| `Supporting text#54121:756` | 文本 | `Supporting line text lorem ipsum dolor s` |  |
| `Condition` | 变体 | `3 line+` | 1 line、2 line、3 line+ |
| `Leading` | 变体 | `None` | None、Monogram、Icon、Image、Video、Check Box、Radio Button、Switch |
| `Trailing` | 变体 | `None` | None、Check Box、Icon、Radio Button、Switch |
| `Show overline` | 变体 | `False` | False、True |
| `Show supporting text` | 变体 | `True` | False、True |

### List item/List Item: -2 Density (baseline)
- 组件 Key：`05676916f17c746a091195a58a8da76191388cea`　节点：`51964:65924`　变体数：217

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `State-layer#67:286` | 实例替换 | `51964:70957` |  |
| `Show trailing supporting text#70:0` | 布尔 | `False` |  |
| `Show divider#70:113` | 布尔 | `False` |  |
| `Show leading avatar#2891:0` | 布尔 | `True` |  |
| `Show leading icon#2891:233` | 布尔 | `True` |  |
| `Show image#2892:0` | 布尔 | `True` |  |
| `Show video thumbnail#2892:233` | 布尔 | `True` |  |
| `Show checkbox#2892:466` | 布尔 | `True` |  |
| `Show radio button#2892:699` | 布尔 | `True` |  |
| `Show switch#2892:932` | 布尔 | `True` |  |
| `Swap divider type#52066:239` | 实例替换 | `51816:5860` |  |
| `Headline#53046:1434` | 文本 | `List item` |  |
| `Overline#53046:1652` | 文本 | `Overline` |  |
| `Trailing supporting text#53046:1870` | 文本 | `100+` |  |
| `Supporting text#54121:538` | 文本 | `Supporting line text lorem ipsum dolor s` |  |
| `Condition` | 变体 | `3 line+` | 1 line、2 line、3 line+ |
| `Leading` | 变体 | `None` | None、Monogram、Icon、Image、Video、Check Box、Radio Button、Switch |
| `Trailing` | 变体 | `None` | None、Check Box、Icon、Radio Button、Switch |
| `Show overline` | 变体 | `False` | False、True |
| `Show supporting text` | 变体 | `True` | False、True |

### List item/List Item: 0 Density (baseline)
- 组件 Key：`297e67ec926e7d03ad6f38a895a7b4984dc4e7af`　节点：`51964:63037`　变体数：238

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `State-layer#67:286` | 实例替换 | `51964:70957` |  |
| `Show trailing supporting text#70:0` | 布尔 | `False` |  |
| `Show divider#70:113` | 布尔 | `False` |  |
| `Show leading avatar#2891:0` | 布尔 | `True` |  |
| `Show leading icon#2891:233` | 布尔 | `True` |  |
| `Show image#2892:0` | 布尔 | `True` |  |
| `Show video thumbnail#2892:233` | 布尔 | `True` |  |
| `Show checkbox#2892:466` | 布尔 | `True` |  |
| `Show radio button#2892:699` | 布尔 | `True` |  |
| `Show switch#2892:932` | 布尔 | `True` |  |
| `Swap divider type#52066:0` | 实例替换 | `51816:5860` |  |
| `Headline#53045:239` | 文本 | `List item` |  |
| `Overline#53045:478` | 文本 | `Overline` |  |
| `Trailing supporting text#53045:717` | 文本 | `100+` |  |
| `Supporting text#54121:60` | 文本 | `Supporting line text lorem ipsum dolor s` |  |
| `Condition` | 变体 | `3 line+` | 1 line、2 line、3 line+ |
| `Leading` | 变体 | `None` | None、Monogram、Icon、Image、Video、Check Box、Radio Button、Switch |
| `Trailing` | 变体 | `None` | None、Check Box、Icon、Radio Button、Switch |
| `Show overline` | 变体 | `False` | False、True |
| `Show supporting text` | 变体 | `True` | False、True |

### List
- 组件 Key：`c1d303d373acf2ad1d8736f620262dce33138d69`　节点：`59106:13028`　变体数：12

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show divider#21291:0` | 布尔 | `False` |  |
| `Type` | 变体 | `Standard` | Standard、Segmented (filled)、Expandable、Draggable、Swipable - standard、Swipable - segmented |
| `Multi-line` | 变体 | `False` | False、True |

### List item
- 组件 Key：`bafe7cad143fa0ea34fc01300540f7e10cd2867b`　节点：`59106:13183`　变体数：24

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show trailing element#4234:0` | 布尔 | `True` |  |
| `Show leading element#4234:25` | 布尔 | `True` |  |
| `Show focus indicator#36458:0` | 布尔 | `False` |  |
| `Content#59340:24` | 插槽 | `—` |  |
| `Alignment` | 变体 | `Middle-aligned` | Middle-aligned、Top-aligned |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Dragged、Disabled |
| `Selected` | 变体 | `False` | False、True |

### List item - Accordion 
- 组件 Key：`124b49f793d8eef4300cd9a5ff9c9dad8922f301`　节点：`59106:13316`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Accordion` | 变体 | `Collapsed` | Collapsed、Expanded |

### List Item - Swipe
- 组件 Key：`3e3719d3f70f26b0e39f5e8d7903a37c19fc4a9d`　节点：`59106:13321`　变体数：4

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Swipe state` | 变体 | `Default` | Default、Initiate reveal、Swipe Action、Revealed |

### Building blocks/Leading element
- 组件 Key：`ea50d7940ce1218177847a4d0b161bebe0f47404`　节点：`59106:13333`　变体数：10

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#4243:0` | 实例替换 | `54616:25411` |  |
| `Selected#4247:50` | 布尔 | `True` |  |
| `Content#59329:24` | 插槽 | `—` |  |
| `Type` | 变体 | `Icon` | Icon、Indent、Image、Avatar、Video、Icon button、Checkbox、Radio、Switch、Slot |

### Building blocks/Trailing element
- 组件 Key：`e38b604996f963b6a0486858358292daa78470d7`　节点：`59106:13356`　变体数：8

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show trailing text#1859:6` | 布尔 | `True` |  |
| `Trailing text#4220:5` | 文本 | `⌘C` |  |
| `Icon#4243:11` | 实例替换 | `54616:25423` |  |
| `Content#59329:35` | 插槽 | `—` |  |
| `Type` | 变体 | `Icon` | Accordion button、Checkbox、Icon、Icon button、Radio、Switch、Slot、Trailing text only |

### Building blocks/Content
- 组件 Key：`1978ef121d53fd7682facc4e36494ca5c8a03e4b`　节点：`59106:13378`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show overline text#1859:0` | 布尔 | `True` |  |
| `Show supporting text#1859:3` | 布尔 | `True` |  |
| `Overline text#4212:73` | 文本 | `Overline` |  |
| `Label text#4212:76` | 文本 | `Label text` |  |
| `Supporting text#4212:79` | 文本 | `Supporting line text, lorem ipsum dolor` |  |
| `Content#59329:44` | 插槽 | `—` |  |
| `Type` | 变体 | `Text` | Text、Slot |

### Building blocks/Reveal element
- 组件 Key：`544775135100a76c2a2dbd437f6ebaaa18a2947a`　节点：`59106:13386`　变体数：9

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Actions` | 变体 | `1` | 1、2、3 |
| `Type` | 变体 | `Initiate reveal` | Initiate reveal、Revealed、Swipe action |

### Building blocks/List item/Accordion buttton
- 组件 Key：`3ec42cfef8c44f89dba970c5d871f23494021801`　节点：`59106:13414`　变体数：8

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Type` | 变体 | `Expand` | Expand、Collapse |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed |

---

## Loading & progress

### .Building Blocks/Progress indicator/Width 4/Segment - wave *(内部构建块)*
- 组件 Key：`e70c02d3c96c63e832c7f27442b2d84ab11a98bc`　节点：`58005:7970`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Wave height` | 变体 | `12` | 12 |
| `Thickness` | 变体 | `4` | 4 |
| `Wavelength` | 变体 | `40` | 40、20 |

### .Building Blocks/Progress indicator/Width 8/Segment - wave *(内部构建块)*
- 组件 Key：`028a368f7b09ff92ead9530c450ce94f8320d3e7`　节点：`58005:7976`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Wave height` | 变体 | `12` | 12 |
| `Thickness` | 变体 | `8` | 8 |
| `Wavelength` | 变体 | `40` | 40、20 |

### Linear-determinate progress indicator
- 组件 Key：`c2c9d9a1cc7c2d5f7af5f9e1ebdf014eeca2d7e9`　节点：`58005:7997`　变体数：24

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Type` | 变体 | `Flat` | Flat、Wave |
| `Thickness` | 变体 | `4 dp` | 4 dp、8 dp |
| `Progress` | 变体 | `0` | 0、10、20、50、80、100 |

### Linear-indeterminate progress indicator
- 组件 Key：`7506b07a34073db01254d272cb80396f9dec54bd`　节点：`58005:8284`　变体数：12

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Type` | 变体 | `Flat` | Flat、Wave |
| `Thickness` | 变体 | `4 dp` | 4 dp、8 dp |
| `Step` | 变体 | `0` | 0、1、2 |

### Circular-determinate progress indicator
- 组件 Key：`9705da8133cb0c0e45c9ad7c769389b36aae70f0`　节点：`58005:8459`　变体数：24

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Type` | 变体 | `Flat` | Flat、Wave |
| `Thickness` | 变体 | `4 dp` | 4 dp、8 dp |
| `Progress` | 变体 | `0` | 0、10、30、50、80、100 |

### Circular-indeterminate progress indicator
- 组件 Key：`44a4c20d38ae30d0c7042ca6bce1f90313f2ae51`　节点：`58005:8525`　变体数：8

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show track#7311:0` | 布尔 | `True` |  |
| `Type` | 变体 | `Flat` | Flat、Wave |
| `Thickness` | 变体 | `4 dp` | 4 dp、8 dp |
| `Step` | 变体 | `1` | 1、2 |

### Loading indicator
- 组件 Key：`40f79b835aeee250f7ad2977191ddc1c4a585e50`　节点：`58005:8555`　变体数：14

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Steps` | 变体 | `1` | 1、2、3、4、5、6、7 |
| `Show container` | 变体 | `False` | False、True |

---

## Menu

### Building Blocks/Menu list item *(内部构建块)*
- 组件 Key：`348162881315dd76d8cf5cb452d590369ebb49d7`　节点：`54061:37026`　变体数：5

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Label text#358:0` | 文本 | `Menu item` |  |
| `Show divider#358:5` | 布尔 | `True` |  |
| `Show leading element#364:16` | 布尔 | `True` |  |
| `Show trailing element#364:22` | 布尔 | `True` |  |
| `Supporting text#54116:0` | 文本 | `Supporting text` |  |
| `Show supporting text#54116:5` | 布尔 | `False` |  |
| `Show focus indicator#58729:423` | 布尔 | `False` |  |
| `Content#59298:133` | 插槽 | `—` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Selected、Disabled |

### Building Blocks/Menu list item: -2 density *(内部构建块)*
- 组件 Key：`6a3b9d7db1ca7e78a36a1e799e3066283f88723a`　节点：`54061:37051`　变体数：5

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Label text#358:0` | 文本 | `Menu item` |  |
| `Show divider#358:5` | 布尔 | `True` |  |
| `Show leading element#364:16` | 布尔 | `True` |  |
| `Show trailing element#364:22` | 布尔 | `True` |  |
| `Show supporting text#54116:20` | 布尔 | `False` |  |
| `Supporting text#54116:25` | 文本 | `Supporting text` |  |
| `Show focus indicator#58729:429` | 布尔 | `False` |  |
| `Content#59299:145` | 插槽 | `—` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Selected、Disabled |

### Building Blocks/Menu list item: -4 density *(内部构建块)*
- 组件 Key：`092063835454cfb53b57199d834ec0c3480e480c`　节点：`54061:37076`　变体数：5

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Label text#358:0` | 文本 | `Menu item` |  |
| `Show divider#358:5` | 布尔 | `True` |  |
| `Show leading element#364:16` | 布尔 | `True` |  |
| `Show trailing element#364:22` | 布尔 | `True` |  |
| `Supporting text#54116:10` | 文本 | `Supporting text` |  |
| `Show supporting text#54116:15` | 布尔 | `False` |  |
| `Show focus indicator#58729:435` | 布尔 | `False` |  |
| `Content#59299:175` | 插槽 | `—` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Selected、Disabled |

### Building Blocks/Leading element *(内部构建块)*
- 组件 Key：`9cb7c62b2f2237915c426d759e4bfdfc9ba0d279`　节点：`54061:37101`　变体数：3

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Leading icon#364:19` | 实例替换 | `54616:25533` |  |
| `Content#59325:0` | 插槽 | `—` |  |
| `Type` | 变体 | `Icon` | Icon、Indent、Slot |

### Building Blocks/Trailing element *(内部构建块)*
- 组件 Key：`86958bdefbf1fb943a9691c1905b6cc019775505`　节点：`54061:37105`　变体数：3

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Trailing icon#364:10` | 实例替换 | `54616:25423` |  |
| `Trailing text#364:13` | 文本 | `⌘C` |  |
| `Content#59325:4` | 插槽 | `—` |  |
| `Type` | 变体 | `Icon` | Icon、Text、Slot |

### Building Blocks/Trailing element-selected *(内部构建块)*
- 组件 Key：`4243b1b1aa9ce555a9d798f763762f647117cbc5`　节点：`55286:1288`　变体数：3

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Trailing icon#364:10` | 实例替换 | `54616:25423` |  |
| `Trailing text#364:13` | 文本 | `⌘C` |  |
| `Content#59325:8` | 插槽 | `—` |  |
| `Type` | 变体 | `Icon` | Icon、Text、Slot |

### Menu (baseline)
- 组件 Key：`1fafabccda7ad164defcd46e4fe006a3f696c9af`　节点：`54061:36963`　变体数：3

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show scrollbar#105927:0` | 布尔 | `False` |  |
| `Density` | 变体 | `0` | 0、-2、-4 |

### Menu
- 组件 Key：`a0b03ba07e6aae17ef8c98d8cf5859681bb03153`　节点：`58966:3975`　变体数：6

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show scrollbar#1421:0` | 布尔 | `False` |  |
| `Show section label#3445:14` | 布尔 | `False` |  |
| `List 1 content#59292:0` | 插槽 | `—` |  |
| `List 2 content#59292:7` | 插槽 | `—` |  |
| `List 3 content#59292:14` | 插槽 | `—` |  |
| `Theme` | 变体 | `Standard` | Standard、Vibrant |
| `Groups` | 变体 | `1` | 1、2、3 |

### Menu item/Standard
- 组件 Key：`9741e555b65ab14075e61dd5a73054264729834b`　节点：`58966:4100`　变体数：11

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show leading element#987:8` | 布尔 | `True` |  |
| `Show trailing element#987:12` | 布尔 | `True` |  |
| `Show divider#37688:0` | 布尔 | `False` |  |
| `Show focus indicator#39627:24` | 布尔 | `False` |  |
| `Content#59340:0` | 插槽 | `—` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled、Active |
| `Selected` | 变体 | `False` | False、True |

### Menu item/Vibrant
- 组件 Key：`ccf93d2559e2ff5147451d3e43b86f46ad2dbb69`　节点：`58966:4171`　变体数：11

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show leading element#987:8` | 布尔 | `True` |  |
| `Show trailing element#987:12` | 布尔 | `True` |  |
| `Show divider#37688:14` | 布尔 | `False` |  |
| `Show focus indicator#39627:12` | 布尔 | `False` |  |
| `Content#59340:12` | 插槽 | `—` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled、Active |
| `Selected` | 变体 | `False` | False、True |

### .Building Blocks/Leading element *(内部构建块)*
- 组件 Key：`317dc6316a9f2c41406043a66a19492a33a2c34b`　节点：`58966:4243`　变体数：3

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#1833:27` | 实例替换 | `54616:25411` |  |
| `Content#59325:12` | 插槽 | `—` |  |
| `Type` | 变体 | `Indent` | Icon、Indent、Slot |

### .Building Blocks/Content *(内部构建块)*
- 组件 Key：`7e45b992c0ef541e963421f76ebb5d1de32212eb`　节点：`58966:4249`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show supporting text#3319:1` | 布尔 | `False` |  |
| `Supporting text#3446:0` | 文本 | `Supporting text` |  |
| `Label text#29070:0` | 文本 | `Label` |  |
| `Content#59325:16` | 插槽 | `—` |  |
| `Type` | 变体 | `Text` | Text、Slot |

### .Building Blocks/Trailing element *(内部构建块)*
- 组件 Key：`ac6414bc41e52854b155b9ba8825b1a6bb285e4e`　节点：`58966:4255`　变体数：4

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#1833:31` | 实例替换 | `54616:25423` |  |
| `Show trailing text#1855:0` | 布尔 | `True` |  |
| `Trailing text#39626:0` | 文本 | `⌘C` |  |
| `Content#59325:19` | 插槽 | `—` |  |
| `Type` | 变体 | `Text` | Text、Icon、Slot、Badge |

---

## Navigation

### Building Blocks / Nav item *(内部构建块)*
- 组件 Key：`25a109acac2e2cefd6eedd0a1738a35054399fae`　节点：`51593:5254`　变体数：16

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show badge label#52034:0` | 布尔 | `True` |  |
| `Label text#53033:0` | 文本 | `Label` |  |
| `Badge label text#53033:17` | 文本 | `100+` |  |
| `Icon#53974:54` | 实例替换 | `54616:25411` |  |
| `Selected` | 变体 | `False` | True、False |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed |
| `Show icon` | 变体 | `True` | False、True |

### XR/XR Navigation Rail
- 组件 Key：`b5ba1d4c0b9d45565a603e843a5814f1117d280e`　节点：`57547:2577`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show menu#8172:0` | 布尔 | `True` |  |
| `Elevation` | 变体 | `Surface container` | Surface container |
| `Configuration` | 变体 | `Embedded FAB` | Embedded FAB、No FAB |

### Building Blocks/XR/Navigation rail/Nav item *(内部构建块)*
- 组件 Key：`e2eadc1ee3e7198aa2602cbd9c6c083f760b8e6a`　节点：`57547:1794`　变体数：48

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show focus indicator#58732:677` | 布尔 | `False` |  |
| `Label text#70506:240` | 文本 | `Label` |  |
| `Icon#70506:305` | 实例替换 | `54616:25411` |  |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed |
| `Badge` | 变体 | `None` | None、Small、Large |
| `Elevation` | 变体 | `Default` | Default |
| `Show label text` | 变体 | `False` | False、True |

### Building Blocks/XR/Navigation bar/Nav item *(内部构建块)*
- 组件 Key：`f4085a4c524f79b648f57d3d208d31581ee6b1dd`　节点：`57547:4010`　变体数：24

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#8050:0` | 实例替换 | `54616:25411` |  |
| `Label text#8050:39` | 文本 | `Label` |  |
| `Show focus indicator#58732:652` | 布尔 | `False` |  |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed |
| `Badge` | 变体 | `None` | None、Small、Large |
| `Show label text` | 变体 | `True` | True |

### XR/XR Navigation bar
- 组件 Key：`369270031a2fcf027ea7a79f60b894993379f6f1`　节点：`57547:4795`　变体数：3

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Elevation` | 变体 | `Surface container` | Surface container |
| `Configuration` | 变体 | `Icon & label` | Icon & label |
| `Segments` | 变体 | `3` | 3、4、5 |

### Building Blocks/Navigation rail/Vertical nav item *(内部构建块)*
- 组件 Key：`015b77e8314a1ed3c448663a1ebd4184419de199`　节点：`58016:36306`　变体数：48

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon (selected)#58027:75` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58732:578` | 布尔 | `False` |  |
| `Label text#70754:66` | 文本 | `Label` |  |
| `Icon#70754:196` | 实例替换 | `54616:25411` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed |
| `Badge` | 变体 | `None` | None、Small、Large |
| `Selected` | 变体 | `False` | False、True |
| `Show label` | 变体 | `True` | True、False |

### Building Blocks/Navigation rail/Horizontal nav item *(内部构建块)*
- 组件 Key：`f7ff3f365861752d4a7f92ef5b1767ecda1fcf5d`　节点：`58016:36557`　变体数：24

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show icon#296:0` | 布尔 | `True` |  |
| `Icon (selected)#58027:50` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58732:627` | 布尔 | `False` |  |
| `Label text#70877:51` | 文本 | `Label` |  |
| `Icon#70877:85` | 实例替换 | `54616:25411` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed |
| `Badge` | 变体 | `None` | None、Small、Large |
| `Selected` | 变体 | `False` | True、False |

### Navigation Rail: Expanded
- 组件 Key：`95eb3a050fc57f5f92243279361185d34edf093a`　节点：`58016:36670`　变体数：16

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show Menu#421:0` | 布尔 | `True` |  |
| `Show FAB#10565:34` | 布尔 | `True` |  |
| `Segments#59255:9` | 插槽 | `—` |  |
| `Type` | 变体 | `Docked` | Docked、Floating |
| `Alignment` | 变体 | `Top` | Top、Middle |
| `Nav items` | 变体 | `3` | 3、4、5、6 |

### Navigation Rail
- 组件 Key：`fffc51b26a70c7c42434dc9ef4444088e59a92ff`　节点：`58016:36879`　变体数：8

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show FAB#10565:17` | 布尔 | `True` |  |
| `Show Menu#57547:0` | 布尔 | `True` |  |
| `Segments#59255:0` | 插槽 | `—` |  |
| `Alignment` | 变体 | `Top` | Top、Middle |
| `Nav items` | 变体 | `3` | 3、4、5、6 |

### Building Blocks/Navigation bars/Vertical nav item *(内部构建块)*
- 组件 Key：`0dccb6b236dde0f44dd5133bc4bbb8504dd6495f`　节点：`58016:36961`　变体数：24

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show Label Text#264:41` | 布尔 | `True` |  |
| `Icon (selected)#58027:0` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58730:528` | 布尔 | `False` |  |
| `Label text#70754:66` | 文本 | `Label` |  |
| `Icon#70754:196` | 实例替换 | `54616:25411` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed |
| `Badge` | 变体 | `None` | None、Small、Large |
| `Selected` | 变体 | `False` | False、True |

### Building Blocks/Navigation bars/Horizontal nav item *(内部构建块)*
- 组件 Key：`f7918324a1bf3ceceed688c6f44a2e746d3195b4`　节点：`58016:37099`　变体数：24

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#489:0` | 实例替换 | `54616:25411` |  |
| `Label text#489:1` | 文本 | `Label` |  |
| `Show Label Text#489:2` | 布尔 | `True` |  |
| `Icon (selected)#58027:25` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58730:553` | 布尔 | `False` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed |
| `Badge` | 变体 | `None` | None、Small、Large |
| `Selected` | 变体 | `False` | False、True |

### Navigation Bar: Horizontal items
- 组件 Key：`f5e6025f64716900c76e3876ffc8a8bc30eff940`　节点：`58016:37236`　变体数：4

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Nav items` | 变体 | `3` | 3、4、5、6 |

### Navigation Bar: Vertical items
- 组件 Key：`dcfab7f753d0913cf952ee1320077db8509c160c`　节点：`58016:37259`　变体数：3

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Nav items` | 变体 | `3` | 3、4、5 |

---

## Radio button

### Radio buttons
- 组件 Key：`54d4a084a0ab7709a586672747294f0352855c49`　节点：`51739:4608`　变体数：10

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show focus indicator#58730:441` | 布尔 | `False` |  |
| `Selected` | 变体 | `True` | True、False |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

---

## Search

### Search bar
- 组件 Key：`86ba81afaca4101daf479b7ec93174747507127c`　节点：`52977:33813`　变体数：6

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show 2nd trailing icon#3294:13` | 布尔 | `False` |  |
| `Show 1st trailing icon#3294:26` | 布尔 | `True` |  |
| `Placeholder text#52999:1337` | 文本 | `Hinted search text` |  |
| `Show leading icon#58115:0` | 布尔 | `True` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Pressed |
| `Show avatar` | 变体 | `False` | False、True |

### Search full-screen layout (baseline)
- 组件 Key：`40a307a4fc9d2a5856a8cda78c141b04febf7975`　节点：`52977:34015`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Placeholder text#52999:1355` | 文本 | `Hinted search text` |  |
| `Input text#52999:1365` | 文本 | `Input text` |  |
| `Show list items#59179:5` | 布尔 | `True` |  |
| `Configuration` | 变体 | `Input text` | Input text、Supporting text |

### Search docked layout (baseline)
- 组件 Key：`3e9aca8951b2fff9f342f9a8b18d491069604026`　节点：`52977:33958`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Placeholder text#52999:1350` | 文本 | `Hinted search text` |  |
| `Input text#52999:1360` | 文本 | `Input text` |  |
| `Show list items#59179:0` | 布尔 | `True` |  |
| `Configuration` | 变体 | `Input text` | Supporting text、Input text |

### Search full-screen layout
- 组件 Key：`282e239758d8f4b52436f065e3b68787e8534360`　节点：`59178:4963`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show trailing Icon#7148:5` | 布尔 | `True` |  |
| `Show list items#7178:5` | 布尔 | `True` |  |
| `Input text#70722:46` | 文本 | `Input text` |  |
| `Placeholder text#70722:56` | 文本 | `Hinted search text` |  |
| `Configuration` | 变体 | `Input text` | Input text、Supporting text |

### Search docked layout
- 组件 Key：`66530738e082ca82b03a24f87814cbbd75985a31`　节点：`59178:4992`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show trailing Icon#7148:0` | 布尔 | `True` |  |
| `Show list items#7178:0` | 布尔 | `True` |  |
| `Input text#70722:26` | 文本 | `Input text` |  |
| `Placeholder text#70722:36` | 文本 | `Hinted search text` |  |
| `Configuration` | 变体 | `Input text` | Input text、Supporting text |

---

## Shape

### Shape Set
- 组件 Key：`965f0e77f76fdc5fde81b584fdc86fbbaf2dffe1`　节点：`58548:7248`　变体数：35

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Shape` | 变体 | `Circle` | 4-leaf clover、4-sided cookie、6-sided cookie、7-sided cookie、8-leaf clover、9-sided cookie、12-sided cookie、Arch、Arrow、Boom… |

---

## Sheets

### Side Sheet
- 组件 Key：`cab727fd0f371c6a9640050ff4f13d5704bde34a`　节点：`53198:27851`　变体数：4

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show actions#3022:0` | 布尔 | `True` |  |
| `Headline text#3022:9` | 文本 | `Title` |  |
| `Content#59251:3` | 插槽 | `—` |  |
| `Type` | 变体 | `Standard` | Standard、Modal |
| `Show back` | 变体 | `False` | False、True |

### Building Blocks/Side sheets/Content *(内部构建块)*
- 组件 Key：`76dca186020ba5c9092b72d4a52c4b3e2ce9fac1`　节点：`57314:35886`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Slot swap#46:3` | 实例替换 | `57314:35492` |  |
| `Layout` | 变体 | `Empty` | Empty、Slot |

### Bottom sheet
- 组件 Key：`5191812806ce622d313fd4b79aea08cfc510b729`　节点：`51827:5859`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show drag handle#52079:0` | 布尔 | `True` |  |
| `Content#59251:0` | 插槽 | `—` |  |
| `Modal` | 变体 | `False` | False、True |

### Building Blocks/Bottom sheets/Content *(内部构建块)*
- 组件 Key：`1249d06e4a3fcd54c14e182d602faeccf528c938`　节点：`57314:35893`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Slot swap#46:3` | 实例替换 | `57314:35492` |  |
| `Layout` | 变体 | `Empty` | Empty、Slot |

---

## Sliders

### Standard slider
- 组件 Key：`7c97cfd8c96f051493b03191a387fe87b822ecac`　节点：`58008:10356`　变体数：120

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show value indicator#4815:0` | 布尔 | `True` |  |
| `Show stops#7528:242` | 布尔 | `False` |  |
| `Icon#7583:121` | 实例替换 | `58015:34236` |  |
| `Show icon#7583:242` | 布尔 | `False` |  |
| `Orientation` | 变体 | `Horizontal` | Horizontal、Vertical |
| `Size` | 变体 | `XSmall` | Large、XLarge、Medium、Small、XSmall |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Pressed、Disabled |
| `Value` | 变体 | `50` | 0、50、100 |

### Centered slider
- 组件 Key：`d5702440d73ec315023786a530fd60374cff669c`　节点：`58008:10979`　变体数：120

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show value indicator#4815:13` | 布尔 | `True` |  |
| `Show stops#7559:0` | 布尔 | `False` |  |
| `Orientation` | 变体 | `Horizontal` | Horizontal、Vertical |
| `Size` | 变体 | `XSmall` | Large、XLarge、Medium、Small、XSmall |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Pressed、Disabled |
| `Value` | 变体 | `0` | -50、0、+50 |

### Range slider
- 组件 Key：`39d48338367be76eb0416cfd438e07ad86bcb8eb`　节点：`58008:11810`　变体数：60

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show value indicator#4815:39` | 布尔 | `True` |  |
| `Show stops#7571:0` | 布尔 | `False` |  |
| `Orientation` | 变体 | `Horizontal` | Horizontal |
| `Size` | 变体 | `XSmall` | Large、XLarge、Medium、Small、XSmall |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Pressed、Disabled |
| `Value` | 变体 | `0` | -50、0、+50 |

---

## Snackbar

### .Building Blocks/Snackbar-action *(内部构建块)*
- 组件 Key：`830ece6495a1dee32ba29266ff34d4eba0683a1c`　节点：`53977:33615`　变体数：4

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Label text#53881:0` | 文本 | `Label` |  |
| `Show focus indicator#58730:457` | 布尔 | `False` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed |

### .Building Blocks/Snackbar-close-affordance *(内部构建块)*
- 组件 Key：`85e92827b0a911155932c117812c89605cd4697c`　节点：`53977:33628`　变体数：4

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show focus indicator#58730:452` | 布尔 | `False` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed |

### Snackbar
- 组件 Key：`e4827520cc4fff3fec49b4dcc4765af6b79740b6`　节点：`53977:33575`　变体数：10

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Supporting text#53937:0` | 文本 | `Snackbar supporting text` |  |
| `Configuration` | 变体 | `Text only` | Text only、Text & action、Text & longer action |
| `# of lines` | 变体 | `One line` | One line、Two lines |
| `Show close affordance` | 变体 | `False` | False、True |

---

## Styles

### .Shape *(内部构建块)*
- 组件 Key：`11a508c9e911f5512cb60c62692a37493c27e222`　节点：`55343:12390`　变体数：9

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Shape` | 变体 | `1` | 1、2、3、4、5、6、7、8、9 |

---

## Switch

### Switch
- 组件 Key：`411029936e3d344fedc4808c5c38469b1b735b91`　节点：`54446:25289`　变体数：20

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show focus indicator#58730:462` | 布尔 | `False` |  |
| `Selected` | 变体 | `True` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |
| `Icon` | 变体 | `False` | False、True |

---

## Tabs

### Tabs
- 组件 Key：`ba1e39164d3f5001fba6365b3a424cd49515c084`　节点：`54563:40023`　变体数：10

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Tab group#59296:44` | 插槽 | `—` |  |
| `Configuration` | 变体 | `Fixed` | Fixed、Scrollable |
| `Style` | 变体 | `Primary` | Primary、Secondary |
| `Layout` | 变体 | `Label only` | Icon only、Label & icon、Label only |

### Primary tabs/Icon and label
- 组件 Key：`579c14bf6c7dc737a768b88b61c7cd6474117886`　节点：`54563:40142`　变体数：8

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#53409:0` | 实例替换 | `54616:25411` |  |
| `Label text#53409:9` | 文本 | `Tab` |  |
| `Show focus indicator#58730:519` | 布尔 | `False` |  |
| `Show badge#155697:72` | 布尔 | `False` |  |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed |

### Primary tabs/Icon only
- 组件 Key：`48fe86faa6db82aaf9f822ebb99dd803cf19af27`　节点：`54563:40209`　变体数：8

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#53409:18` | 实例替换 | `54616:25411` |  |
| `Show focus indicator#58730:510` | 布尔 | `False` |  |
| `Show badge#155081:54` | 布尔 | `False` |  |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed |

### Primary tabs/Label only
- 组件 Key：`8e5d77551b4f3cbd03ec8168f5735004a9165f55`　节点：`54563:40268`　变体数：8

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Label text#53409:27` | 文本 | `Tab` |  |
| `Show focus indicator#58730:501` | 布尔 | `False` |  |
| `Show badge#155033:27` | 布尔 | `False` |  |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed |

### Secondary tabs/Label only
- 组件 Key：`4d6b54a11e32098d28d1ffb9a09adce43bc060ad`　节点：`54563:40319`　变体数：8

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Label text#53409:36` | 文本 | `Tab` |  |
| `Show focus indicator#58730:492` | 布尔 | `False` |  |
| `Show badge#155033:36` | 布尔 | `False` |  |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed |

### Secondary tabs/Icon and label
- 组件 Key：`bff5c94f770605646c78a833b7b34222179c765f`　节点：`54563:40366`　变体数：8

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#53409:45` | 实例替换 | `54616:25411` |  |
| `Label text#53409:54` | 文本 | `Tab` |  |
| `Show focus indicator#58730:483` | 布尔 | `False` |  |
| `Show badge#155033:45` | 布尔 | `False` |  |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed |

---

## Text fields

### Text field
- 组件 Key：`f51eda358c4822368e6efb93292e5bc6f7d16dd8`　节点：`52798:24373`　变体数：120

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show supporting text#52051:0` | 布尔 | `True` |  |
| `Label text#52808:484` | 文本 | `Label` |  |
| `Placeholder text#52808:605` | 文本 | `Placeholder` |  |
| `Input text#52808:726` | 文本 | `Input` |  |
| `Supporting text#52808:847` | 文本 | `Supporting text` |  |
| `Style` | 变体 | `Filled` | Filled、Outlined |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Error、Disabled |
| `Text configurations` | 变体 | `Input text` | Input text、Label text、Placeholder text |
| `Leading icon` | 变体 | `False` | False、True |
| `Trailing icon` | 变体 | `True` | True、False |

---

## Toolbars

### Building Blocks/Standard/Icon button *(内部构建块)*
- 组件 Key：`60dc943962ea916638589567012f81ba75f9adcc`　节点：`58694:34208`　变体数：5

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#58694:3035` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58699:3237` | 布尔 | `False` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Building Blocks/Standard/Icon button toggleable *(内部构建块)*
- 组件 Key：`45206724e7969b9a193fcfcc9a9dfe702fd8f02d`　节点：`58027:76098`　变体数：10

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#58694:3013` | 实例替换 | `54616:25411` |  |
| `Icon (selected)#58694:3024` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58699:3243` | 布尔 | `False` |  |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Building Blocks/Standard/Button toggleable *(内部构建块)*
- 组件 Key：`c6c38031fe0b3a6543816f11edeec971050c11ac`　节点：`58027:76103`　变体数：10

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show icon#58694:2958` | 布尔 | `True` |  |
| `Icon (selected)#58694:2969` | 实例替换 | `54616:25409` |  |
| `Icon#58694:2980` | 实例替换 | `54616:25411` |  |
| `Label text#58694:2991` | 文本 | `Label` |  |
| `Show focus indicator#58699:3271` | 布尔 | `False` |  |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Building Blocks/Vibrant/Icon button *(内部构建块)*
- 组件 Key：`29a57a5acbea692217496b4cb99b9fa3af9c720d`　节点：`58694:35488`　变体数：5

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#58694:3107` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58699:3265` | 布尔 | `False` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Building Blocks/Vibrant/Icon button toggleable *(内部构建块)*
- 组件 Key：`d949d9769db4d49030e688d8d4e5e43d2a8fa6f1`　节点：`58027:76201`　变体数：10

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#58694:3085` | 实例替换 | `54616:25411` |  |
| `Icon (selected)#58694:3096` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58699:3254` | 布尔 | `False` |  |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Building Blocks/Vibrant/Button toggleable *(内部构建块)*
- 组件 Key：`04201ed3597036d83d60524d48ab416771221e80`　节点：`58027:76206`　变体数：10

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Label text#58694:3041` | 文本 | `Label` |  |
| `Show icon#58694:3052` | 布尔 | `True` |  |
| `Icon#58694:3063` | 实例替换 | `54616:25411` |  |
| `Icon (selected)#58694:3074` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58699:3282` | 布尔 | `False` |  |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### Toolbar
- 组件 Key：`c31d57330cd38a77138582b545f1a440e8697421`　节点：`58467:8206`　变体数：6

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Content (standard)#59293:21` | 插槽 | `—` |  |
| `Content (vibrant)#59293:28` | 插槽 | `—` |  |
| `Configuration` | 变体 | `Floating` | Floating、Docked |
| `Orientation` | 变体 | `Horizontal` | Horizontal、Vertical |
| `Color` | 变体 | `Standard` | Vibrant、Standard |

### XR/XR Toolbar
- 组件 Key：`3f087ae14648b67f210e1bb4ed0dca5dc0373ec2`　节点：`58823:1688`　变体数：8

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show spatial FAB#7057:0` | 布尔 | `True` |  |
| `Content#59296:35` | 插槽 | `—` |  |
| `Elevation` | 变体 | `Surface container` | Surface container、Surface container high、Surface container highest、Tertiary container |
| `Orientation` | 变体 | `Horizontal` | Horizontal、Vertical |

### XR/Building Blocks/Surface high/Icon button *(内部构建块)*
- 组件 Key：`32d1ba606e915b89408009b55e1c1d07b827012d`　节点：`58823:1763`　变体数：5

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#58694:3035` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58699:3237` | 布尔 | `False` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### XR/Building Blocks/Surface high/Icon button toggleable *(内部构建块)*
- 组件 Key：`fa168a58f1de9b1dd91d5951f86cd591d45cd80b`　节点：`58823:1786`　变体数：10

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#58694:3013` | 实例替换 | `54616:25411` |  |
| `Show focus indicator#58699:3243` | 布尔 | `False` |  |
| `Icon (selected)#58825:44` | 实例替换 | `54616:25409` |  |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### XR/Building Blocks/Surface high/Button toggleable *(内部构建块)*
- 组件 Key：`1c41ec8ede257318593f2ddbfd3908d18a534555`　节点：`58823:1831`　变体数：10

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show icon#58694:2958` | 布尔 | `True` |  |
| `Icon#58694:2980` | 实例替换 | `54616:25411` |  |
| `Label text#58694:2991` | 文本 | `Label` |  |
| `Show focus indicator#58699:3271` | 布尔 | `False` |  |
| `Icon (selected)#58825:11` | 实例替换 | `54616:25409` |  |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### XR/Building Blocks/Tertiary container/Icon button *(内部构建块)*
- 组件 Key：`069778842f474a9e6346c596e6957707cd827cfd`　节点：`58823:1887`　变体数：5

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#58694:3035` | 实例替换 | `54616:25409` |  |
| `Show focus indicator#58699:3237` | 布尔 | `False` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### XR/Building Blocks/Tertiary container/Icon button toggleable *(内部构建块)*
- 组件 Key：`d7d0cb126922361b1a9c75d2060241c8a8edb198`　节点：`58823:1910`　变体数：10

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#58694:3013` | 实例替换 | `54616:25411` |  |
| `Show focus indicator#58699:3243` | 布尔 | `False` |  |
| `Icon (selected)#58825:55` | 实例替换 | `54616:25409` |  |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### XR/Building Blocks/Tertiary container/Button toggleable *(内部构建块)*
- 组件 Key：`846d65fb0bb9c714795c44df9cad362be613e29e`　节点：`58823:1955`　变体数：10

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show icon#58694:2958` | 布尔 | `True` |  |
| `Icon#58694:2980` | 实例替换 | `54616:25411` |  |
| `Label text#58694:2991` | 文本 | `Label` |  |
| `Show focus indicator#58699:3271` | 布尔 | `False` |  |
| `Icon (selected)#58824:0` | 实例替换 | `54616:25409` |  |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### XR/Building Blocks/Surface/Icon button *(内部构建块)*
- 组件 Key：`b5403c15ea3a8446b5d2deff6543622d7ecf805b`　节点：`58823:2013`　变体数：5

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show Focus indicator#7061:0` | 布尔 | `False` |  |
| `Icon#58694:3035` | 实例替换 | `54616:25409` |  |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### XR/Building Blocks/Surface/Icon button toggleable *(内部构建块)*
- 组件 Key：`4345fcdbbaf04e96c8058422617bd87a157c85d8`　节点：`58823:2036`　变体数：10

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Icon#58694:3013` | 实例替换 | `54616:25411` |  |
| `Show focus indicator#58699:3243` | 布尔 | `False` |  |
| `Icon (selected)#58825:33` | 实例替换 | `54616:25409` |  |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

### XR/Building Blocks/Surface/Button toggleable *(内部构建块)*
- 组件 Key：`2d734fb391a5a303918409b9f077075f4fd9f089`　节点：`58823:2081`　变体数：10

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Show icon#58694:2958` | 布尔 | `True` |  |
| `Icon#58694:2980` | 实例替换 | `54616:25411` |  |
| `Label text#58694:2991` | 文本 | `Label` |  |
| `Show focus indicator#58699:3271` | 布尔 | `False` |  |
| `Icon (selected)#58825:22` | 实例替换 | `54616:25409` |  |
| `Selected` | 变体 | `False` | False、True |
| `State` | 变体 | `Enabled` | Enabled、Hovered、Focused、Pressed、Disabled |

---

## Tooltips

### Plain Tooltip
- 组件 Key：`01949b2e115426efc6e8933d74771e1a0c736962`　节点：`54061:33881`　变体数：2

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Supporting text#2609:2` | 文本 | `Supporting text` |  |
| `Type` | 变体 | `Single line` | Single line、Multi line |

---

## Utilities

### Keyboard
- 组件 Key：`f88e069a4b29934db22114dd58a7f1d7cb65a995`　节点：`52515:32926`　变体数：12

| 参数名 | 类型 | 默认值 | 可选值 / 说明 |
|---|---|---|---|
| `Configuration` | 变体 | `Base keyboard` | Base keyboard、Alphanumeric、Keypad、Numeric only |
| `Style` | 变体 | `Portrait` | Portrait、Landscape、Floating |

---

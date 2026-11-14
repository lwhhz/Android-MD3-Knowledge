# Cards / 卡片

- 官方指南：https://m3.material.io/components/cards
- Compose API：https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary
- 核验日期：2026-11-14 ｜ 状态：类名与默认色角色已对照 API；尺寸为 M3 通行值，精确值请复核官网

## 用途

卡片把**同一主题的一组信息**装进一个可识别的容器。它回答的是「这些内容属于同一件事」，不是「我想给这块加个圆角边框」。

判断标准：拿掉卡片边框，内容是否仍能读成一组？不能，才需要卡片。

## 变体

| Compose | 容器色 | 层级 | 用在哪 |
|---|---|---|---|
| `Card`（filled） | `surfaceContainerHighest` | 0dp | 默认，页面主要内容 |
| `ElevatedCard` | `surfaceContainerLow` | 1dp | 需要从背景中「浮起」时 |
| `OutlinedCard` | `surface` + `outlineVariant` 描边 | 0dp | 背景本身就花，或需要明确边界时 |

三者形状均为 `medium`（12dp）圆角。

## 结构

```
┌─────────────────────────┐
│  [media / 图片]          │  可选
│  Title                  │  Title Medium
│  Subtitle               │  Body Medium, onSurfaceVariant
│  Supporting text        │  Body Small
│  ─────────────────      │
│  [action]      [action] │  可选操作区
└─────────────────────────┘
```

内边距 16dp。内容顺序固定：媒体 → 标题 → 副标题 → 正文 → 操作。

## 交互

- **不可点击的卡片**用 `Card { }`，不要加 `clickable`。
- **可点击的卡片**用 `Card(onClick = { })` 重载，它会自动带涟漪与语义。
- 卡片内**不要再放独立按钮抢点击**。一整张卡可点，里面又有小按钮，是命中冲突的高发区——如果必须这样，把按钮放在明确分隔的操作区，并确保它有自己的触控目标。
- 卡片不表达选中态。多选场景用 `Checkbox` / 单选列表，不要「卡片高亮 = 已选」。

## Compose 实现

```kotlin
Card(
    onClick = { openDetail(id) },
    modifier = Modifier.fillMaxWidth()
) {
    Column(Modifier.padding(16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
```

带操作区：

```kotlin
ElevatedCard(modifier = Modifier.fillMaxWidth()) {
    Column(Modifier.padding(16.dp)) {
        Text(item.title, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onDismiss) { Text("忽略") }
            Spacer(Modifier.width(8.dp))
            Button(onClick = onApply) { Text("查看") }
        }
    }
}
```

## 无障碍

- 整卡可点时，卡片内的标题与副标题应由 TalkBack **一次读完**——`Card(onClick=)` 已自动合并语义，不要手动再拆。
- 卡片内的图片若有信息价值，写 `contentDescription`；纯装饰设 `null`。
- 卡片内按钮各有独立语义，读屏时会单独停顿，符合预期。
- 整卡可点时，触控目标已覆盖全卡，不要再套一层 `clickable`。

## 常见错误

1. 列表里每一项都套卡片，还加了阴影——视觉噪音，改用 `ListItem` + 分隔线。
2. 用 `OutlinedCard` 又加 elevation，边界和阴影打架。
3. 卡片里塞满 6 行文字，失去「一瞥可读」的意义。超过 3 行正文请改从列表进入详情。
4. 手写 `Surface` + `border` + `clickable` 复刻卡片，丢掉官方的水波纹与语义。
5. 用卡片颜色区分状态（红卡 = 异常），色盲用户无法分辨。

## 来源

- https://m3.material.io/components/cards
- `01-Foundations/elevation.md`（表面色阶与层级）
- `02-Components/lists.md`（什么情况该用列表而不用卡片）

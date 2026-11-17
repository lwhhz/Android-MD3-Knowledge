package com.example.md3clock.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * 形状策略（01-Foundations/shape-system.md）：
 * - 官方形状阶梯为 0/4/8/12/16/20/28/32/48/full，本主题只取其中五档填入 Compose 槽位；
 * - 比起 M3 基线，Expressive 的默认外观更圆：这里整体上移一档
 *   （卡片 16dp、对话框 32dp），仍在官方阶梯内，不发明数值；
 * - 一屏内圆角档位 ≤ 3 种，嵌套容器内层圆角小于外层。
 */
val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(32.dp),
)

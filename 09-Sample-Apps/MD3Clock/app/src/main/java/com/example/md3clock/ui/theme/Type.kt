package com.example.md3clock.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle

/**
 * 字阶策略（01-Foundations/typography.md）：
 * - 只覆盖需要偏离默认值的档位；Typography() 默认就是官方 30 档
 *   （15 baseline + 15 emphasized，emphasized 只改字重、不改字号）；
 * - 时钟/秒表/倒计时属于「值会频繁变化」的数字，官方网站明确要求用
 *   tabular figures（等宽数字）避免跳字，这里通过 [tabular] 统一追加 tnum；
 * - 时间显示使用 emphasized 同档，而不是放大字号：改字号会破坏组件重排。
 */
val AppTypography = Typography()

/** 等宽数字：表格、时钟、计时器专用（typography.md「数字」一节）。 */
fun TextStyle.tabular(): TextStyle = copy(fontFeatureSettings = "tnum")

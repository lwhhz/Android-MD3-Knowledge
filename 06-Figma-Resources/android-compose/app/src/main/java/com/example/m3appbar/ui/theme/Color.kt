package com.example.m3appbar.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

/**
 * 颜色只保存「角色 → 值」的最小占位。
 *
 * 依据 KB `05-Design-Tokens/README.md`：官方色值是语义角色，不应固化 hex。
 * 当前使用 Material 3 默认 baseline（lightColorScheme / darkColorScheme）。
 * 品牌种子色待用 Material Theme Builder 导出后替换（见 KB_COMPONENT_MAP.md 0.3）。
 */
internal val LightColors = lightColorScheme()
internal val DarkColors = darkColorScheme()

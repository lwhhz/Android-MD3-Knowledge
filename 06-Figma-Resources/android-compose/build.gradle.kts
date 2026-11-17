// 根构建脚本：只声明插件版本，不在这里 apply。
// 版本口径见 KB_COMPONENT_MAP.md（D1：material3 1.5.0-rc01 预发布线）。
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

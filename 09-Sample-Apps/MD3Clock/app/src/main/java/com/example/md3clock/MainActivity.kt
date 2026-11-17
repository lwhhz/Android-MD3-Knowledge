package com.example.md3clock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.md3clock.ui.navigation.ClockApp
import com.example.md3clock.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    private val vm: ClockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            // 动态取色默认关闭：品牌语义色要稳定（理由见 ui/theme/Color.kt）
            AppTheme(dynamicColor = false) {
                ClockApp(vm = vm)
            }
        }
    }
}

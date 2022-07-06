package com.kyhsgeekcode.loggingkeyboard

import android.content.Context
import android.widget.FrameLayout
import androidx.compose.ui.platform.ComposeView

class KeyboardView(context: Context) : FrameLayout(context) {
    init {
        val view = ComposeView(context).apply {
            setContent {
                LoggingKeyboardView()
            }
        }
        addView(view)
    }
}
package com.kyhsgeekcode.loggingkeyboard

import android.content.Context
import android.widget.FrameLayout
import androidx.compose.ui.platform.ComposeView

class KeyboardView(context: Context) : FrameLayout(context) {
    init {
        val view = ComposeView(context).apply {
            setContent {
                LoggingKeyboardView(::onKey)
            }
        }
        addView(view)
    }

    val keyListeners = mutableListOf<KeyListener>()

    fun addKeyListener(keyListener: KeyListener) {
        keyListeners.add(keyListener)
    }

    fun removeKeyListener(keyListener: KeyListener) {
        keyListeners.remove(keyListener)
    }

    private fun onKey(key: Char) {
        keyListeners.forEach {
            it.onKey(key)
        }
    }
}
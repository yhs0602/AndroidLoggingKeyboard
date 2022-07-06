package com.kyhsgeekcode.loggingkeyboard

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.text.TextUtils
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy


class LoggingInputMethodService : InputMethodService() {
    override fun onCreateInputView(): View {
        val view = ComposeView(this).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                LoggingKeyboardView()
            }
        }
        return view
    }

    fun onKey(primaryCode: Int) {
        val ic = currentInputConnection ?: return
        when (primaryCode) {
            Keyboard.KEYCODE_DELETE -> {
                val selectedText = ic.getSelectedText(0)
                if (TextUtils.isEmpty(selectedText)) {
                    // no selection, so delete previous character
                    ic.deleteSurroundingText(1, 0)
                } else {
                    // delete the selection
                    ic.commitText("", 1)
                }
            }
            else -> {
                val code = primaryCode as Char
                ic.commitText(code.toString(), 1)
            }
        }
    }
}
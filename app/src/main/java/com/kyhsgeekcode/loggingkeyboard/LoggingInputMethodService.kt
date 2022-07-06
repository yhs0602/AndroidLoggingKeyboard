package com.kyhsgeekcode.loggingkeyboard

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo


// To use compose in input method see:
// https://stackoverflow.com/a/66958772/8614565
class LoggingInputMethodService : InputMethodService() {
    private val keyboardViewLifecycleOwner = KeyboardViewLifecycleOwner()

    override fun onCreateInputView(): View {
        keyboardViewLifecycleOwner.attachToDecorView(
            window?.window?.decorView
        )
        return KeyboardView(this)
    }

    override fun onCreate() {
        super.onCreate()
        keyboardViewLifecycleOwner.onCreate()
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        keyboardViewLifecycleOwner.onResume()
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        keyboardViewLifecycleOwner.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        keyboardViewLifecycleOwner.onDestroy()
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
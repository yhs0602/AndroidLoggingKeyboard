package com.kyhsgeekcode.loggingkeyboard

import HangulMaker
import android.inputmethodservice.InputMethodService
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import org.greenrobot.eventbus.EventBus


// To use compose in input method see:
// https://stackoverflow.com/a/66958772/8614565
class LoggingInputMethodService : InputMethodService(), KeyListener {
    private val keyboardViewLifecycleOwner = KeyboardViewLifecycleOwner()
    private var hangulMaker: HangulMaker? = null

    override fun onCreateInputView(): View {
        keyboardViewLifecycleOwner.attachToDecorView(
            window?.window?.decorView
        )
        return KeyboardView(this).apply {
            addKeyListener(this@LoggingInputMethodService)
        }
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

    override fun onKey(key: Char) {
        val ic = currentInputConnection ?: return
        if (hangulMaker == null) {
            hangulMaker = HangulMaker(ic)
        }
        EventBus.getDefault().post(LoggingKeyEvent(key))
        when (key) {
            '⌫' -> {
                val selectedText = ic.getSelectedText(0)
                if (TextUtils.isEmpty(selectedText)) {
                    // no selection, so delete previous character
                    ic.deleteSurroundingText(1, 0)
                } else {
                    // delete the selection
                    ic.commitText("", 1)
                }
            }
            ' ' -> {
                hangulMaker?.commitSpace()
            }
            else -> {
                val code = key
                ic.commitText(code.toString(), 1)
            }
        }
    }
}
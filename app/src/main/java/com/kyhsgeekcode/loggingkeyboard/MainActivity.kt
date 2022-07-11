package com.kyhsgeekcode.loggingkeyboard

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.github.kimkevin.hangulparser.HangulParser
import com.kyhsgeekcode.loggingkeyboard.ui.theme.LoggingKeyboardTheme
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val hangul: String = HangulParser.assemble(listOf("ㅎ", "ㅏ", "ㄱ", "ㅗ"))
        Log.d("hangul", hangul)

        setContent {
            LoggingKeyboardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting()
                }
            }
        }
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: LoggingKeyEvent) {
        Toast.makeText(this, event.keyCode.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }
}

@Composable
fun Greeting() {
    var name by remember {
        mutableStateOf("")
    }
    TextField(value = name, onValueChange = { name = it })
}
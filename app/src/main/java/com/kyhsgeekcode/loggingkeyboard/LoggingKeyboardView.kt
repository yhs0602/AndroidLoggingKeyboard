package com.kyhsgeekcode.loggingkeyboard

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.util.*


val keysMatrixUpper = arrayOf(
    arrayOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
    arrayOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
    arrayOf("⬆", "Z", "X", "C", "V", "B", "N", "M", "⌫")
)
val keysMatrixLower = arrayOf(
    arrayOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"),
    arrayOf("a", "s", "d", "f", "g", "h", "j", "k", "l"),
    arrayOf("⇧", "z", "x", "c", "v", "b", "n", "m", "⌫")
)
val keysMatrixKorean = arrayOf(
    arrayOf("ㅂ", "ㅈ", "ㄷ", "ㄱ", "ㅅ", "ㅛ", "ㅕ", "ㅑ", "ㅐ", "ㅔ"),
    arrayOf("ㅁ", "ㄴ", "ㅇ", "ㄹ", "ㅎ", "ㅗ", "ㅓ", "ㅏ", "ㅣ"),
    arrayOf("⇧", "ㅋ", "ㅌ", "ㅊ", "ㅍ", "ㅠ", "ㅜ", "ㅡ", "⌫")
)
val keysMatrixKoreanUpper = arrayOf(
    arrayOf("ㅃ", "ㅉ", "ㄸ", "ㄲ", "ㅆ", "ㅛ", "ㅕ", "ㅑ", "ㅒ", "ㅖ"),
    arrayOf("ㅁ", "ㄴ", "ㅇ", "ㄹ", "ㅎ", "ㅗ", "ㅓ", "ㅏ", "ㅣ"),
    arrayOf("⬆", "ㅋ", "ㅌ", "ㅊ", "ㅍ", "ㅠ", "ㅜ", "ㅡ", "⌫")
)
val keysMatrixNumberSymbol = arrayOf(
    arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
    arrayOf("@", "#", "$", "_", "&", "-", "+", "(", ")", "/"),
    arrayOf("⇧", "*", "\"", "\'", ":", ";", "!", "?", "⌫")
)

val keysMatrixNumberSymbolUpper = arrayOf(
    arrayOf("~", "`", "|", "•", "√", "π", "÷", "×", "¶", "△"),
    arrayOf("£", "¢", "€", "¥", "^", "°", "=", "{", "}", "\\"),
    arrayOf("⬆", "%", "©", "®", "™", "✓", "[", "]", "⌫")
)

@Composable
fun LoggingKeyboardView(onKey: (Char) -> Unit) {
    var isShift by remember {
        mutableStateOf(false)
    }
    var isSymbol by remember {
        mutableStateOf(false)
    }
    var language by remember {
        mutableStateOf(Locale.ENGLISH)
    }

    Column(
        modifier = Modifier
            .background(Color(0xFFCCCCCC))
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        val whichKeyboard = if (isSymbol) {
            if (isShift) keysMatrixNumberSymbolUpper else keysMatrixNumberSymbol
        } else if (language == Locale.KOREAN) {
            if (isShift) keysMatrixKoreanUpper else keysMatrixKorean
        } else if (language == Locale.ENGLISH) {
            if (isShift) keysMatrixUpper else keysMatrixLower
        } else {
            keysMatrixLower
        }
        whichKeyboard.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                row.forEach { key ->
                    KeyboardKey(keyboardKey = key, modifier = Modifier
                        .weight(1f)
                        .clickable {
                            Log.d("LoggingKeyboardView", "Clicked $key")
                            when (key) {
                                "⬆" -> {
                                    isShift = false
                                }
                                "⇧" -> {
                                    isShift = true
                                }
                                else -> {
                                    onKey(key[0])
                                }
                            }
                        })
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            if (isSymbol) {
                KeyboardKey(keyboardKey = "ABC", modifier = Modifier
                    .weight(1f)
                    .clickable {
                        isSymbol = false
                    })
            } else {
                KeyboardKey(keyboardKey = "?123", modifier = Modifier
                    .weight(1f)
                    .clickable {
                        isSymbol = true
                    })
            }
            KeyboardKey(keyboardKey = ",", modifier = Modifier
                .weight(1f)
                .clickable {
                    onKey(',')
                })
            KeyboardKey(keyboardKey = "\uD83C\uDF10", modifier = Modifier
                .weight(1f)
                .clickable {
                    language = if (language == Locale.ENGLISH) {
                        Locale.KOREAN
                    } else {
                        Locale.ENGLISH
                    }
                })
            // space bar
            KeyboardKey(keyboardKey = "space", modifier = Modifier
                .weight(1f)
                .clickable {
                    onKey(' ')
                })
            KeyboardKey(keyboardKey = ".", modifier = Modifier
                .weight(1f)
                .clickable {
                    onKey('.')
                })
            KeyboardKey(keyboardKey = "enter", modifier = Modifier
                .weight(1f)
                .clickable {
                    onKey('\n')
                })
        }
    }
}

@Composable
fun FixedHeightBox(modifier: Modifier, height: Dp, content: @Composable () -> Unit) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        val h = height.roundToPx()
        layout(constraints.maxWidth, h) {
            placeables.forEach { placeable ->
                placeable.place(x = 0, y = kotlin.math.min(0, h - placeable.height))
            }
        }
    }
}


@Composable
fun KeyboardKey(
    keyboardKey: String,
    modifier: Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed = interactionSource.collectIsPressedAsState()
    Box(modifier = modifier, contentAlignment = Alignment.BottomCenter) {
        Text(
            keyboardKey, Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .border(1.dp, Color.Black)
//            .clickable(interactionSource = interactionSource, indication = null) {}
                .background(Color.White)
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 16.dp,
                    bottom = 16.dp
                )
        )
//        if (pressed.value) {
//            Text(
//                keyboardKey, Modifier
//                    .fillMaxWidth()
//                    .border(1.dp, Color.Black)
//                    .background(Color.White)
//                    .padding(
//                        start = 16.dp,
//                        end = 16.dp,
//                        top = 16.dp,
//                        bottom = 48.dp
//                    )
//            )
//        }
    }
}
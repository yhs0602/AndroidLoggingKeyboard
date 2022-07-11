package com.kyhsgeekcode.loggingkeyboard

import java.lang.Integer.parseInt

private val chosung = listOf(
    "ㄱ",
    "ㄲ",
    "ㄴ",
    "ㄷ",
    "ㄸ",
    "ㄹ",
    "ㅁ",
    "ㅂ",
    "ㅃ",
    "ㅅ",
    "ㅆ",
    "ㅇ",
    "ㅈ",
    "ㅉ",
    "ㅊ",
    "ㅋ",
    "ㅌ",
    "ㅍ",
    "ㅎ"
)
private val jungsung = listOf(
    "ㅏ",
    "ㅐ",
    "ㅑ",
    "ㅒ",
    "ㅓ",
    "ㅔ",
    "ㅕ",
    "ㅖ",
    "ㅗ",
    "ㅘ",
    "ㅙ",
    "ㅚ",
    "ㅛ",
    "ㅜ",
    "ㅝ",
    "ㅞ",
    "ㅟ",
    "ㅠ",
    "ㅡ",
    "ㅢ",
    "ㅣ"
)
private val jonsung = listOf(
    "",
    "ㄱ",
    "ㄲ",
    "ㄳ",
    "ㄴ",
    "ㄵ",
    "ㄶ",
    "ㄷ",
    "ㄹ",
    "ㄺ",
    "ㄻ",
    "ㄼ",
    "ㄽ",
    "ㄾ",
    "ㄿ",
    "ㅀ",
    "ㅁ",
    "ㅂ",
    "ㅄ",
    "ㅅ",
    "ㅆ",
    "ㅇ",
    "ㅈ",
    "ㅊ",
    "ㅋ",
    "ㅌ",
    "ㅍ",
    "ㅎ"
)

enum class HanState {
    REQUIRE_CHOSUNG,
    REQUIRE_JUNGSUNG,
}

fun tryMerge(letterList: List<Char>) {
    // 초성으로 올 수 있는 것부터만 할 수 있음
    // 왜냐면 나머지로 시작하는 것들은 애초에 앞에 그냥 붙여야 함
    val result = StringBuilder()
    var state = HanState.REQUIRE_CHOSUNG
    for (c in letterList) {
        val asString = c.toString()
        when (state) {
            HanState.REQUIRE_CHOSUNG -> {
                val chosungIndex = chosung.indexOf(asString)
                if (chosungIndex == -1) {
                    throw IllegalArgumentException("$asString is not a chosung 초성으로 시작해야 함")
                }
                state = HanState.REQUIRE_JUNGSUNG
            }
            HanState.REQUIRE_JUNGSUNG -> {

            }
        }

        if (choSungIndex != -1) {
            state = HanState.REQUIRE_JUNGSUNG
        }
        if (asString !in chosung && asString !in jungsung && asString !in jonsung) {
            result.append(c)
            continue
        }


    }
}


// https://note.heyo.me/%ed%95%9c%ea%b8%80-%ea%b0%80%ec%83%81%ed%82%a4%eb%b3%b4%eb%93%9c-%eb%a7%8c%eb%93%a4%ea%b8%b0-2/
// 초중종성 INDEX값을 문자료 변환
fun makeChar(i: Int, m: Int, t: Int): Char {
    val code = ((i * 21) + m) * 28 + t + 0xAC00
    return code.toChar()
}

fun iChrIndex(chr: Char): Int {
    val index = ((chr.code - 0xAC00) / 28) / 21
    return parseInt(index)
}

fun mChrIndex(chr: Char): Int {
    val index = ((chr.code - 0xAC00) / 28) % 21
    return parseInt(index)
}

fun tChrIndex(chr: Char): Int {
    val index = (chr.code - 0xAC00) % 28
    return parseInt(index)
}

/* var
---------------------------------------------------------- */
// 초성INDEX
var indexI = listOf(
    "ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ",
    "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ",
    "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"
)

// 중성INDEX
var indexM = listOf(
    'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ',
    'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ',
    'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ',
    'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'
)

// 종성INDEX
var indexT = listOf(
    "", "ㄱ", "ㄲ", "ㄳ", "ㄴ", "ㄵ", "ㄶ", "ㄷ", "ㄹ",
    "ㄺ", "ㄻ", "ㄼ", "ㄽ", "ㄾ", "ㄿ", "ㅀ", "ㅁ",
    "ㅂ", "ㅄ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"
)

// 조합INDEX
var indexJComb1 = listOf("ㄳ", "ㄵ", "ㄶ", "ㄺ", "ㄻ", "ㄼ", "ㄽ", "ㄾ", "ㄿ", "ㅀ", "ㅄ")
var indexJComb2 = listOf("ㄱㅅ", "ㄴㅈ", "ㄴㅎ", "ㄹㄱ", "ㄹㅁ", "ㄹㅂ", "ㄹㅅ", "ㄹㅌ", "ㄹㅍ", "ㄹㅎ", "ㅄ")
var indexMComb1 = listOf('ㅘ', 'ㅙ', 'ㅚ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅢ')
var indexMComb2 = listOf("ㅗㅏ", "ㅗㅐ", "ㅗㅣ", "ㅜㅓ", "ㅜㅔ", "ㅜㅣ", "ㅡㅣ")

// 호환용 한글 자모 (3130~318F)
var jaCode = 'ㄱ'.code
var jaCodeLast = 'ㅎ'.code
var moCode = 'ㅏ'.code
var moCodeLast = 'ㅣ'.code

fun onAddNewKey(newChar: Char, text: String): String { // char: Button string, text: Total String
    val chrCode = newChar.code
    val isJa = chrCode in jaCode..jaCodeLast
    val isMo = chrCode in moCode..moCodeLast
    if (text.isNotEmpty()) {
        val lastChr = text.last()
        val lastChrCode = lastChr.code
        if (lastChrCode in jaCode..moCodeLast) {
            // 자음, 모음
            if (lastChrCode in jaCode..jaCodeLast) {
                if (isMo) {
                    val i = indexI.indexOf(lastChr.toString())
                    val m = indexM.indexOf(newChar)
                    val t = 0
                    val c = makeChar(i, m, t)
                    return text.substring(0, text.length - 1) + c
                }
            } else if (lastChrCode in moCode..moCodeLast) {
            }
        } else if (lastChrCode >= 0xAC00 && lastChrCode <= 0xAC00 + 0x2BA4) {
            // 한글
            var i = iChrIndex(lastChr)
            var m = mChrIndex(lastChr)
            var t = tChrIndex(lastChr)
            if (t == 0) {
                // 종성이 없는 경우
                if (isJa) {
                    t = indexT.indexOf(newChar.toString())
                    if (t != -1) { // 없는 종성문자인경우 제외
                        val c = makeChar(i, m, t)
                        return text.substring(0, text.length - 1) + c
                    }
                } else if (isMo) {
                    // 모음조합문자
                    val chkChr = "${indexM[m]}$newChar"
                    val combIndex = indexMComb2.indexOf(chkChr)
                    if (combIndex != -1) {
                        val combChr = indexMComb1[combIndex]
                        m = indexM.indexOf(combChr)
                        val c = makeChar(i, m, t)
                        return text.substring(0, text.length - 1) + c
                    }
                }
            } else {
                // 종성이 있는경우
                if (isMo) {
                    var tChr = indexT[t]

                    // 조합문자일경우 다시 쪼갠다
                    val combIndex = indexJComb1.indexOf(tChr)
                    if (combIndex != -1) {
                        val partChr = indexJComb2[combIndex]
                        t = indexT.indexOf(partChr[0].toString())
                        tChr = partChr[1].toString()
                    } else {
                        t = 0
                    }

                    val c1 = makeChar(i, m, t)
                    i = indexI.indexOf(tChr)
                    if (i != -1) {
                        m = indexM.indexOf(newChar)
                        val c2 = makeChar(i, m, 0)
                        return text.substring(0, text.length - 1) + c1 + c2
                    }
                } else if (isJa) {
                    // 자음조합문자
                    val chkChr = "${indexT[t]}$newChar"
                    val combIndex = indexJComb2.indexOf(chkChr)
                    if (combIndex != -1) {
                        val combChr = indexJComb1[combIndex]
                        t = indexT.indexOf(combChr)
                        val c = makeChar(i, m, t)
                        return text.substring(0, text.length - 1) + c
                    }
                }
            }
        } else {
            // 없는 문자
        }
    }
    return text + newChar
}

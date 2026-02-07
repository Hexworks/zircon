package org.hexworks.zircon.api.util

import io.kotest.matchers.shouldBe
import kotlin.test.Test

class CharsTest {

    @Test
    fun shouldReturnTrueWhenIsControlCharacterIsCalledAndSmallerThan32() {
        3.toChar().isControlCharacter() shouldBe true
    }

    @Test
    fun shouldReturnTrueWhenIsControlCharacterIsCalledAnd127() {
        127.toChar().isControlCharacter() shouldBe true
    }

    @Test
    fun shouldReturnFalseWhenIsControlCharacterIsCalledAndNot127orSmallerThan32() {
        32.toChar().isControlCharacter() shouldBe false
    }

    @Test
    fun shouldReturnTrueWhenIsPrintableCharacterCalledAndNotControlChar() {
        'a'.isPrintableCharacter() shouldBe true
    }

    @Test
    fun shouldReturnTrueWhenIsPrintableCharacterCalledAndNewLine() {
        '\n'.isPrintableCharacter() shouldBe false
    }

    @Test
    fun shouldReturnFalseWhenIsPrintableCharacterCalledAndControlCharacter() {
        5.toChar().isPrintableCharacter() shouldBe false
    }
}

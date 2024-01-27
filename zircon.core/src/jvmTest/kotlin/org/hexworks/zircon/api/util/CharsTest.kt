package org.hexworks.zircon.api.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CharsTest {

    @Test
    fun shouldReturnTrueWhenIsControlCharacterIsCalledAndSmallerThan32() {
        assertThat(3.toChar().isControlCharacter()).isTrue()
    }

    @Test
    fun shouldReturnTrueWhenIsControlCharacterIsCalledAnd127() {
        assertThat(127.toChar().isControlCharacter()).isTrue()
    }

    @Test
    fun shouldReturnFalseWhenIsControlCharacterIsCalledAndNot127orSmallerThan32() {
        assertThat(32.toChar().isControlCharacter()).isFalse()
    }

    @Test
    fun shouldReturnTrueWhenIsPrintableCharacterCalledAndNotControlChar() {
        assertThat('a'.isPrintableCharacter()).isTrue()
    }

    @Test
    fun shouldReturnTrueWhenIsPrintableCharacterCalledAndNewLine() {
        assertThat('\n'.isPrintableCharacter()).isFalse()
    }

    @Test
    fun shouldReturnFalseWhenIsPrintableCharacterCalledAndControlCharacter() {
        assertThat(5.toChar().isPrintableCharacter()).isFalse()
    }
}

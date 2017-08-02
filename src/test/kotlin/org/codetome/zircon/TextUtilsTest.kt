package org.codetome.zircon

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.util.TextUtils
import org.junit.Test

class TextUtilsTest {

    @Test
    fun shouldReturnTrueWhenIsControlCharacterIsCalledAndSmallerThan32() {
        assertThat(TextUtils.isControlCharacter(3.toChar())).isTrue()
    }

    @Test
    fun shouldReturnTrueWhenIsControlCharacterIsCalledAnd127() {
        assertThat(TextUtils.isControlCharacter(127.toChar())).isTrue()
    }

    @Test
    fun shouldReturnFalseWhenIsControlCharacterIsCalledAndNot127orSmallerThan32() {
        assertThat(TextUtils.isControlCharacter(32.toChar())).isFalse()
    }

    @Test
    fun shouldReturnTrueWhenIsPrintableCharacterCalledAndNotControlChar() {
        assertThat(TextUtils.isPrintableCharacter('a')).isTrue()
    }

    @Test
    fun shouldReturnTrueWhenIsPrintableCharacterCalledAndNewLine() {
        assertThat(TextUtils.isPrintableCharacter('\n')).isTrue()
    }

    @Test
    fun shouldReturnFalseWhenIsPrintableCharacterCalledAndControlCharacter() {
        assertThat(TextUtils.isPrintableCharacter(5.toChar())).isFalse()
    }
}
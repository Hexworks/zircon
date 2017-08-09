package org.codetome.zircon

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.ANSITextColor.*
import org.codetome.zircon.Modifier.*
import org.codetome.zircon.builder.TextColorFactory
import org.junit.Test

@Suppress("UsePropertyAccessSyntax")
class TextCharacterTest {

    @Test
    fun defaultCharacterShouldBeEmptyStringWithBlackAndWhiteAndNoModifiers() {
        assertThat(TextCharacter.DEFAULT_CHARACTER.getCharacter()).isEqualTo(' ')
        assertThat(TextCharacter.DEFAULT_CHARACTER.getBackgroundColor()).isEqualTo(BLACK)
        assertThat(TextCharacter.DEFAULT_CHARACTER.getForegroundColor()).isEqualTo(WHITE)
        assertThat(TextCharacter.DEFAULT_CHARACTER.getModifiers()).isEmpty()
    }

    @Test
    fun boldModifierShouldBeBold() {
        assertThat(TextCharacter.builder().modifier(BOLD).build().isBold()).isTrue()
    }

    @Test
    fun underlinedModifierShouldBeUnderlined() {
        assertThat(TextCharacter.builder().modifier(UNDERLINE).build().isUnderlined()).isTrue()
    }

    @Test
    fun crossedOutModifierShouldBeCrossedOut() {
        assertThat(TextCharacter.builder().modifier(CROSSED_OUT).build().isCrossedOut()).isTrue()
    }

    @Test
    fun italicModifierShouldBeItalic() {
        assertThat(TextCharacter.builder().modifier(ITALIC).build().isItalic()).isTrue()
    }

    @Test
    fun blinkingModifierShouldBeBlinking() {
        assertThat(TextCharacter.builder().modifier(BLINK).build().isBlinking()).isTrue()
    }

    @Test
    fun shouldBeSameButWithCharChangedWhenWithCharIsCalled() {
        assertThat(TextCharacter(
                character = 'a',
                foregroundColor = EXPECTED_FG_COLOR,
                backgroundColor = EXPECTED_BG_COLOR,
                modifiersToUse = EXPECTED_MODIFIERS)
                .withCharacter(EXPECTED_CHAR))
                .isEqualTo(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldBeSameButWithFGChangedWhenWithForegroundColorIsCalled() {
        assertThat(TextCharacter(
                character = EXPECTED_CHAR,
                foregroundColor = GREEN,
                backgroundColor = EXPECTED_BG_COLOR,
                modifiersToUse = EXPECTED_MODIFIERS)
                .withForegroundColor(EXPECTED_FG_COLOR))
                .isEqualTo(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldBeSameButWithBGChangedWhenWithBackgroundColorIsCalled() {
        assertThat(TextCharacter(
                character = EXPECTED_CHAR,
                foregroundColor = EXPECTED_FG_COLOR,
                backgroundColor = RED,
                modifiersToUse = EXPECTED_MODIFIERS)
                .withBackgroundColor(EXPECTED_BG_COLOR))
                .isEqualTo(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldBeSameButWithModifiersChangedWhenWithModifiersIsCalled() {
        assertThat(TextCharacter(
                character = EXPECTED_CHAR,
                foregroundColor = EXPECTED_FG_COLOR,
                backgroundColor = EXPECTED_BG_COLOR,
                modifiersToUse = setOf(BLINK))
                .withModifiers(EXPECTED_MODIFIERS))
                .isEqualTo(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldBeSameButWithModifierAddedWhenWithModifierIsCalled() {
        assertThat(TextCharacter(
                character = EXPECTED_CHAR,
                foregroundColor = EXPECTED_FG_COLOR,
                backgroundColor = EXPECTED_BG_COLOR,
                modifiersToUse = setOf(BOLD))
                .withModifier(ITALIC))
                .isEqualTo(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldBeSameButWithModifierRemovedWhenWithoutModifierIsCalled() {
        assertThat(TextCharacter(
                character = EXPECTED_CHAR,
                foregroundColor = EXPECTED_FG_COLOR,
                backgroundColor = EXPECTED_BG_COLOR,
                modifiersToUse = setOf(BOLD, ITALIC, BLINK))
                .withoutModifier(BLINK))
                .isEqualTo(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithCharIsCalledWithSameChar() {
        assertThat(EXPECTED_TEXT_CHARACTER.withCharacter(EXPECTED_CHAR))
                .isSameAs(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithFGColorIsCalledWithSameFGColor() {
        assertThat(EXPECTED_TEXT_CHARACTER.withForegroundColor(EXPECTED_FG_COLOR))
                .isSameAs(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithBGColorIsCalledWithSameBGColor() {
        assertThat(EXPECTED_TEXT_CHARACTER.withBackgroundColor(EXPECTED_BG_COLOR))
                .isSameAs(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithModifierIsCalledWithSameModifier() {
        assertThat(EXPECTED_TEXT_CHARACTER.withModifier(BOLD))
                .isSameAs(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithModifierSIsCalledWithSameModifierS() {
        assertThat(EXPECTED_TEXT_CHARACTER.withModifiers(EXPECTED_MODIFIERS))
                .isSameAs(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithoutModifierIsCalledWithNonPresentModifier() {
        assertThat(EXPECTED_TEXT_CHARACTER.withoutModifier(CROSSED_OUT))
                .isSameAs(EXPECTED_TEXT_CHARACTER)
    }

    companion object {
        val EXPECTED_CHAR = 'x'
        val EXPECTED_FG_COLOR = TextColorFactory.fromString("#aabbcc")
        val EXPECTED_BG_COLOR = TextColorFactory.fromString("#223344")
        val EXPECTED_MODIFIERS = setOf(BOLD, ITALIC)

        val EXPECTED_TEXT_CHARACTER = TextCharacter(
                character = EXPECTED_CHAR,
                foregroundColor = EXPECTED_FG_COLOR,
                backgroundColor = EXPECTED_BG_COLOR,
                modifiersToUse = EXPECTED_MODIFIERS)
    }

}
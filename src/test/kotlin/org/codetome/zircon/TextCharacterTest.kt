package org.codetome.zircon

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.Modifier.*
import org.codetome.zircon.TextColor.ANSI.*
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
        assertThat(TextCharacter(modifiersToUse = setOf(BOLD)).isBold()).isTrue()
    }

    @Test
    fun underlinedModifierShouldBeUnderlined() {
        assertThat(TextCharacter(modifiersToUse = setOf(UNDERLINE)).isUnderlined()).isTrue()
    }

    @Test
    fun inverseModifierShouldBeInverse() {
        assertThat(TextCharacter(modifiersToUse = setOf(INVERSE)).isInverse()).isTrue()
    }

    @Test
    fun crossedOutModifierShouldBeCrossedOut() {
        assertThat(TextCharacter(modifiersToUse = setOf(CROSSED_OUT)).isCrossedOut()).isTrue()
    }

    @Test
    fun italicModifierShouldBeItalic() {
        assertThat(TextCharacter(modifiersToUse = setOf(ITALIC)).isItalic()).isTrue()
    }

    @Test
    fun blinkingModifierShouldBeBlinking() {
        assertThat(TextCharacter(modifiersToUse = setOf(BLINK)).isBlinking()).isTrue()
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

    companion object {
        val EXPECTED_CHAR = 'x'
        val EXPECTED_FG_COLOR = TextColor.fromString("#aabbcc")
        val EXPECTED_BG_COLOR = TextColor.fromString("#223344")
        val EXPECTED_MODIFIERS = setOf(BOLD, ITALIC)

        val EXPECTED_TEXT_CHARACTER = TextCharacter(
                character = EXPECTED_CHAR,
                foregroundColor = EXPECTED_FG_COLOR,
                backgroundColor = EXPECTED_BG_COLOR,
                modifiersToUse = EXPECTED_MODIFIERS)
    }

}
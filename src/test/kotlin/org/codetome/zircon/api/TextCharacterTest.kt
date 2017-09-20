package org.codetome.zircon.api

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.color.ANSITextColor.*
import org.codetome.zircon.api.factory.TextColorFactory
import org.junit.Test

@Suppress("UsePropertyAccessSyntax")
class TextCharacterTest {

    @Test
    fun defaultCharacterShouldBeEmptyStringWithBlackAndWhiteAndNoModifiers() {
        assertThat(TextCharacterBuilder.DEFAULT_CHARACTER.getCharacter()).isEqualTo(' ')
        assertThat(TextCharacterBuilder.DEFAULT_CHARACTER.getBackgroundColor()).isEqualTo(BLACK)
        assertThat(TextCharacterBuilder.DEFAULT_CHARACTER.getForegroundColor()).isEqualTo(WHITE)
        assertThat(TextCharacterBuilder.DEFAULT_CHARACTER.getModifiers()).isEmpty()
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionIfTryingToCreateTextCharacterFromUnprintableCharacter() {
        TextCharacterBuilder.newBuilder()
                .character(4.toChar())
                .build()
    }

    @Test
    fun shouldProperlyReportHavingABorderWhenThereIsBorder() {
        assertThat(TextCharacterBuilder.newBuilder()
                .modifier(Modifiers.BorderFactory.of())
                .build().hasBorder()).isTrue()
    }

    @Test
    fun shouldProperlyReportHavingABorderWhenThereIsNoBorder() {
        assertThat(TextCharacterBuilder.newBuilder()
                .build().hasBorder()).isFalse()
    }

    @Test
    fun shouldNotBeEmptyWhenNotEmpty() {
        assertThat(TextCharacterBuilder.DEFAULT_CHARACTER.isNotEmpty()).isTrue()
    }

    @Test
    fun shouldBeEmptyWhenEmpty() {
        assertThat(TextCharacterBuilder.EMPTY.isNotEmpty()).isFalse()
    }

    @Test
    fun shouldProperlyRemoveModifiersWhenWithoutModifiersIsCalled() {
        assertThat(TextCharacterBuilder.newBuilder()
                .modifier(Modifiers.Bold)
                .build()
                .withoutModifiers(setOf(Modifiers.Bold))
                .getModifiers())
                .isEmpty()
    }

    @Test
    fun shouldProperlyCreateCopyWithStyleWhenWithStyleIsCalled() {
        val style = StyleSetBuilder.newBuilder()
                .foregroundColor(ANSITextColor.BLUE)
                .backgroundColor(ANSITextColor.CYAN)
                .modifier(Modifiers.Bold)
                .build()

        val copy = TextCharacterBuilder.newBuilder()
                .build()
                .withStyle(style)

        assertThat(copy.getModifiers()).isEqualTo(style.getActiveModifiers())
        assertThat(copy.getBackgroundColor()).isEqualTo(style.getBackgroundColor())
        assertThat(copy.getForegroundColor()).isEqualTo(style.getForegroundColor())
    }

    @Test
    fun boldModifierShouldBeBold() {
        assertThat(TextCharacter.builder().modifier(Modifiers.BOLD).build().isBold()).isTrue()
    }

    @Test
    fun underlinedModifierShouldBeUnderlined() {
        assertThat(TextCharacter.builder().modifier(Modifiers.UNDERLINE).build().isUnderlined()).isTrue()
    }

    @Test
    fun crossedOutModifierShouldBeCrossedOut() {
        assertThat(TextCharacter.builder().modifier(Modifiers.CROSSED_OUT).build().isCrossedOut()).isTrue()
    }

    @Test
    fun italicModifierShouldBeItalic() {
        assertThat(TextCharacter.builder().modifier(Modifiers.ITALIC).build().isItalic()).isTrue()
    }

    @Test
    fun blinkingModifierShouldBeBlinking() {
        assertThat(TextCharacter.builder().modifier(Modifiers.BLINK).build().isBlinking()).isTrue()
    }

    @Test
    fun shouldBeSameButWithCharChangedWhenWithCharIsCalled() {
        assertThat(TextCharacter.of(
                character = 'a',
                foregroundColor = EXPECTED_FG_COLOR,
                backgroundColor = EXPECTED_BG_COLOR,
                modifiers = EXPECTED_MODIFIERS)
                .withCharacter(EXPECTED_CHAR))
                .isEqualTo(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldBeSameButWithFGChangedWhenWithForegroundColorIsCalled() {
        assertThat(TextCharacter.of(
                character = EXPECTED_CHAR,
                foregroundColor = GREEN,
                backgroundColor = EXPECTED_BG_COLOR,
                modifiers = EXPECTED_MODIFIERS)
                .withForegroundColor(EXPECTED_FG_COLOR))
                .isEqualTo(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldBeSameButWithBGChangedWhenWithBackgroundColorIsCalled() {
        assertThat(TextCharacter.of(
                character = EXPECTED_CHAR,
                foregroundColor = EXPECTED_FG_COLOR,
                backgroundColor = RED,
                modifiers = EXPECTED_MODIFIERS)
                .withBackgroundColor(EXPECTED_BG_COLOR))
                .isEqualTo(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldBeSameButWithModifiersChangedWhenWithModifiersIsCalled() {
        assertThat(TextCharacter.of(
                character = EXPECTED_CHAR,
                foregroundColor = EXPECTED_FG_COLOR,
                backgroundColor = EXPECTED_BG_COLOR,
                modifiers = setOf(Modifiers.BLINK))
                .withModifiers(EXPECTED_MODIFIERS))
                .isEqualTo(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldBeSameButWithModifierAddedWhenWithModifierIsCalled() {
        assertThat(TextCharacter.of(
                character = EXPECTED_CHAR,
                foregroundColor = EXPECTED_FG_COLOR,
                backgroundColor = EXPECTED_BG_COLOR,
                modifiers = setOf(Modifiers.BOLD))
                .withModifier(Modifiers.ITALIC))
                .isEqualTo(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldBeSameButWithModifierRemovedWhenWithoutModifierIsCalled() {
        assertThat(TextCharacter.of(
                character = EXPECTED_CHAR,
                foregroundColor = EXPECTED_FG_COLOR,
                backgroundColor = EXPECTED_BG_COLOR,
                modifiers = setOf(Modifiers.BOLD, Modifiers.ITALIC, Modifiers.BLINK))
                .withoutModifier(Modifiers.BLINK))
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
        assertThat(EXPECTED_TEXT_CHARACTER.withModifier(Modifiers.BOLD))
                .isSameAs(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithModifierSIsCalledWithSameModifierS() {
        assertThat(EXPECTED_TEXT_CHARACTER.withModifiers(EXPECTED_MODIFIERS))
                .isSameAs(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithoutModifierIsCalledWithNonPresentModifier() {
        assertThat(EXPECTED_TEXT_CHARACTER.withoutModifier(Modifiers.CROSSED_OUT))
                .isSameAs(EXPECTED_TEXT_CHARACTER)
    }

    companion object {
        val EXPECTED_CHAR = 'x'
        val EXPECTED_FG_COLOR = TextColorFactory.fromString("#aabbcc")
        val EXPECTED_BG_COLOR = TextColorFactory.fromString("#223344")
        val EXPECTED_MODIFIERS = setOf(Modifiers.BOLD, Modifiers.ITALIC)

        val EXPECTED_TEXT_CHARACTER = TextCharacter.of(
                character = EXPECTED_CHAR,
                foregroundColor = EXPECTED_FG_COLOR,
                backgroundColor = EXPECTED_BG_COLOR,
                modifiers = EXPECTED_MODIFIERS)
    }

}
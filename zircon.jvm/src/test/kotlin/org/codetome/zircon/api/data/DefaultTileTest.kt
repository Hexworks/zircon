package org.codetome.zircon.api.data

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.builder.data.TileBuilder
import org.codetome.zircon.api.builder.graphics.StyleSetBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.color.ANSITextColor.*
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.interop.Modifiers
import org.codetome.zircon.api.modifier.SimpleModifiers.*
import org.junit.Test

@Suppress("UsePropertyAccessSyntax")
class DefaultTileTest {

    @Test
    fun shouldGenerateProperCacheKey() {
        val result = TileBuilder.newBuilder()
                .character('x')
                .backgroundColor(ANSITextColor.GREEN)
                .foregroundColor(TextColor.fromString("#aabbcc"))
                .modifiers(Bold)
                .tag("foo", "bar")
                .build()
                .generateCacheKey()

        assertThat(result).isEqualTo("c:xss:fg:a:255r:170g:187b:204bg:a:255r:0g:170b:0mod:SimpleModifiers:Boldt:barfoo")
    }

    @Test
    fun defaultCharacterShouldBeEmptyStringWithBlackAndWhiteAndNoModifiers() {
        assertThat(Tile.defaultTile().getCharacter()).isEqualTo(' ')
        assertThat(Tile.defaultTile().getBackgroundColor()).isEqualTo(BLACK)
        assertThat(Tile.defaultTile().getForegroundColor()).isEqualTo(WHITE)
        assertThat(Tile.defaultTile().getModifiers()).isEmpty()
    }

    @Test
    fun shouldProperlyReportHavingABorderWhenThereIsBorder() {
        assertThat(TileBuilder.newBuilder()
                .modifiers(Modifiers.border())
                .build().hasBorder()).isTrue()
    }

    @Test
    fun shouldProperlyReportHavingABorderWhenThereIsNoBorder() {
        assertThat(TileBuilder.newBuilder()
                .build().hasBorder()).isFalse()
    }

    @Test
    fun shouldNotBeEmptyWhenNotEmpty() {
        assertThat(Tile.defaultTile().isNotEmpty()).isTrue()
    }

    @Test
    fun shouldBeEmptyWhenEmpty() {
        assertThat(Tile.empty().isNotEmpty()).isFalse()
    }

    @Test
    fun shouldProperlyRemoveModifiersWhenWithoutModifiersIsCalled() {
        assertThat(TileBuilder.newBuilder()
                .modifiers(Modifiers.bold())
                .build()
                .withoutModifiers(setOf(Modifiers.bold()))
                .getModifiers())
                .isEmpty()
    }

    @Test
    fun shouldProperlyCreateCopyWithStyleWhenWithStyleIsCalled() {
        val style = StyleSetBuilder.newBuilder()
                .foregroundColor(ANSITextColor.BLUE)
                .backgroundColor(ANSITextColor.CYAN)
                .modifiers(Modifiers.bold())
                .build()

        val copy = TileBuilder.newBuilder()
                .build()
                .withStyle(style)

        assertThat(copy.getModifiers()).isEqualTo(style.getModifiers())
        assertThat(copy.getBackgroundColor()).isEqualTo(style.getBackgroundColor())
        assertThat(copy.getForegroundColor()).isEqualTo(style.getForegroundColor())
    }

    @Test
    fun boldModifierShouldBeBold() {
        assertThat(TileBuilder.newBuilder().modifiers(Modifiers.bold()).build().isBold()).isTrue()
    }

    @Test
    fun underlinedModifierShouldBeUnderlined() {
        assertThat(TileBuilder.newBuilder().modifiers(Modifiers.underline()).build().isUnderlined()).isTrue()
    }

    @Test
    fun crossedOutModifierShouldBeCrossedOut() {
        assertThat(TileBuilder.newBuilder().modifiers(Modifiers.crossedOut()).build().isCrossedOut()).isTrue()
    }

    @Test
    fun italicModifierShouldBeItalic() {
        assertThat(TileBuilder.newBuilder().modifiers(Modifiers.italic()).build().isItalic()).isTrue()
    }

    @Test
    fun blinkingModifierShouldBeBlinking() {
        assertThat(TileBuilder.newBuilder().modifiers(Modifiers.blink()).build().isBlinking()).isTrue()
    }

    @Test
    fun shouldBeSameButWithCharChangedWhenWithCharIsCalled() {
        assertThat(Tile.create(
                character = 'a',
                styleSet = StyleSetBuilder.newBuilder()
                        .foregroundColor(EXPECTED_FG_COLOR)
                        .backgroundColor(EXPECTED_BG_COLOR)
                        .modifiers(EXPECTED_MODIFIERS)
                        .build())
                .withCharacter(EXPECTED_CHAR))
                .isEqualTo(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldBeSameButWithFGChangedWhenWithForegroundColorIsCalled() {
        assertThat(Tile.create(
                character = EXPECTED_CHAR,
                styleSet = StyleSetBuilder.newBuilder()
                        .foregroundColor(GREEN)
                        .backgroundColor(EXPECTED_BG_COLOR)
                        .modifiers(EXPECTED_MODIFIERS)
                        .build())
                .withForegroundColor(EXPECTED_FG_COLOR))
                .isEqualTo(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldBeSameButWithBGChangedWhenWithBackgroundColorIsCalled() {
        assertThat(Tile.create(
                character = EXPECTED_CHAR,
                styleSet = StyleSetBuilder.newBuilder()
                        .foregroundColor(EXPECTED_FG_COLOR)
                        .backgroundColor(RED)
                        .modifiers(EXPECTED_MODIFIERS)
                        .build())
                .withBackgroundColor(EXPECTED_BG_COLOR))
                .isEqualTo(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldBeSameButWithModifiersChangedWhenWithModifiersIsCalled() {
        assertThat(Tile.create(
                character = EXPECTED_CHAR,
                styleSet = StyleSetBuilder.newBuilder()
                        .foregroundColor(EXPECTED_FG_COLOR)
                        .backgroundColor(EXPECTED_BG_COLOR)
                        .modifiers(setOf(Blink))
                        .build())
                .withModifiers(EXPECTED_MODIFIERS)).isEqualTo(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldBeSameButWithModifierRemovedWhenWithModifierIsCalled() {
        assertThat(Tile.create(
                character = EXPECTED_CHAR,
                styleSet = StyleSetBuilder.newBuilder()
                        .foregroundColor(EXPECTED_FG_COLOR)
                        .backgroundColor(EXPECTED_BG_COLOR)
                        .modifiers(setOf(Bold))
                        .build())
                .withModifiers(Modifiers.italic())).isEqualTo(
                Tile.create(
                        character = EXPECTED_CHAR,
                        styleSet = StyleSetBuilder.newBuilder()
                                .foregroundColor(EXPECTED_FG_COLOR)
                                .backgroundColor(EXPECTED_BG_COLOR)
                                .modifiers(setOf(Italic))
                                .build()))
    }

    @Test
    fun shouldBeSameButWithModifierRemovedWhenWithoutModifierIsCalled() {
        assertThat(Tile.create(
                character = EXPECTED_CHAR,
                styleSet = StyleSetBuilder.newBuilder()
                        .foregroundColor(EXPECTED_FG_COLOR)
                        .backgroundColor(EXPECTED_BG_COLOR)
                        .modifiers(setOf(Modifiers.bold(), Modifiers.italic(), Modifiers.blink()))
                        .build())
                .withoutModifiers(Modifiers.blink()))
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
        assertThat(EXPECTED_TEXT_CHARACTER.withModifiers(Modifiers.bold(), Modifiers.italic()))
                .isSameAs(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithModifierSIsCalledWithSameModifierS() {
        assertThat(EXPECTED_TEXT_CHARACTER.withModifiers(EXPECTED_MODIFIERS))
                .isSameAs(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithoutModifierIsCalledWithNonPresentModifier() {
        assertThat(EXPECTED_TEXT_CHARACTER.withoutModifiers(Modifiers.crossedOut()))
                .isSameAs(EXPECTED_TEXT_CHARACTER)
    }

    companion object {
        val EXPECTED_CHAR = 'x'
        val EXPECTED_FG_COLOR = TextColor.fromString("#aabbcc")
        val EXPECTED_BG_COLOR = TextColor.fromString("#223344")
        val EXPECTED_MODIFIERS = setOf(Modifiers.bold(), Modifiers.italic())

        val EXPECTED_TEXT_CHARACTER = Tile.create(
                character = EXPECTED_CHAR,
                styleSet = StyleSetBuilder.newBuilder()
                        .foregroundColor(EXPECTED_FG_COLOR)
                        .backgroundColor(EXPECTED_BG_COLOR)
                        .modifiers(EXPECTED_MODIFIERS)
                        .build())
    }

}

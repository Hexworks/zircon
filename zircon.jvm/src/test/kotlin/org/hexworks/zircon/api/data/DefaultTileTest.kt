package org.hexworks.zircon.api.data

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.ANSITileColor.*
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.modifier.SimpleModifiers.*
import org.hexworks.zircon.api.Modifiers
import org.junit.Test

@Suppress("UsePropertyAccessSyntax")
class DefaultTileTest {

    @Test
    fun shouldGenerateProperCacheKey() {
        val result = TileBuilder.newBuilder()
                .character('x')
                .backgroundColor(ANSITileColor.GREEN)
                .foregroundColor(TileColor.fromString("#aabbcc"))
                .modifiers(VerticalFlip)
                .build()
                .generateCacheKey()

        assertThat(result).isEqualTo("CharacterTile(c=x,s=StyleSet(fg=TextColor(r=170,g=187,b=204,a=255),bg=TextColor(r=0,g=170,b=0,a=255),m=[Modifier.VerticalFlip]))")
    }

    @Test
    fun defaultCharacterShouldBeEmptyStringWithBlackAndWhiteAndNoModifiers() {
        assertThat(Tile.defaultTile().character).isEqualTo(' ')
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
                .modifiers(Modifiers.crossedOut())
                .build()
                .withoutModifiers(setOf(Modifiers.crossedOut()))
                .getModifiers())
                .isEmpty()
    }

    @Test
    fun shouldProperlyCreateCopyWithStyleWhenWithStyleIsCalled() {
        val style = StyleSetBuilder.newBuilder()
                .foregroundColor(ANSITileColor.BLUE)
                .backgroundColor(ANSITileColor.CYAN)
                .modifiers(Modifiers.crossedOut())
                .build()

        val copy = TileBuilder.newBuilder()
                .build()
                .withStyle(style)

        assertThat(copy.getModifiers()).isEqualTo(style.modifiers())
        assertThat(copy.getBackgroundColor()).isEqualTo(style.backgroundColor())
        assertThat(copy.getForegroundColor()).isEqualTo(style.foregroundColor())
    }

    @Test
    fun boldModifierShouldBeBold() {
        assertThat(TileBuilder.newBuilder().modifiers(Modifiers.crossedOut()).build().isCrossedOut()).isTrue()
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
        assertThat(TileBuilder.newBuilder().modifiers(Modifiers.verticalFlip()).build().isVerticalFlipped()).isTrue()
    }

    @Test
    fun blinkingModifierShouldBeBlinking() {
        assertThat(TileBuilder.newBuilder().modifiers(Modifiers.blink()).build().isBlinking()).isTrue()
    }

    @Test
    fun shouldBeSameButWithCharChangedWhenWithCharIsCalled() {
        assertThat(Tile.createCharacterTile(
                character = 'a',
                style = StyleSetBuilder.newBuilder()
                        .foregroundColor(EXPECTED_FG_COLOR)
                        .backgroundColor(EXPECTED_BG_COLOR)
                        .modifiers(EXPECTED_MODIFIERS)
                        .build())
                .withCharacter(EXPECTED_CHAR))
                .isEqualTo(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldBeSameButWithFGChangedWhenWithForegroundColorIsCalled() {
        assertThat(Tile.createCharacterTile(
                character = EXPECTED_CHAR,
                style = StyleSetBuilder.newBuilder()
                        .foregroundColor(GREEN)
                        .backgroundColor(EXPECTED_BG_COLOR)
                        .modifiers(EXPECTED_MODIFIERS)
                        .build())
                .withForegroundColor(EXPECTED_FG_COLOR))
                .isEqualTo(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldBeSameButWithBGChangedWhenWithBackgroundColorIsCalled() {
        assertThat(Tile.createCharacterTile(
                character = EXPECTED_CHAR,
                style = StyleSetBuilder.newBuilder()
                        .foregroundColor(EXPECTED_FG_COLOR)
                        .backgroundColor(RED)
                        .modifiers(EXPECTED_MODIFIERS)
                        .build())
                .withBackgroundColor(EXPECTED_BG_COLOR))
                .isEqualTo(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldBeSameButWithModifiersChangedWhenWithModifiersIsCalled() {
        assertThat(Tile.createCharacterTile(
                character = EXPECTED_CHAR,
                style = StyleSetBuilder.newBuilder()
                        .foregroundColor(EXPECTED_FG_COLOR)
                        .backgroundColor(EXPECTED_BG_COLOR)
                        .modifiers(setOf(Blink))
                        .build())
                .withModifiers(EXPECTED_MODIFIERS)).isEqualTo(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldBeSameButWithModifierRemovedWhenWithModifierIsCalled() {
        assertThat(Tile.createCharacterTile(
                character = EXPECTED_CHAR,
                style = StyleSetBuilder.newBuilder()
                        .foregroundColor(EXPECTED_FG_COLOR)
                        .backgroundColor(EXPECTED_BG_COLOR)
                        .modifiers(setOf(CrossedOut))
                        .build())
                .withModifiers(Modifiers.verticalFlip())).isEqualTo(
                Tile.createCharacterTile(
                        character = EXPECTED_CHAR,
                        style = StyleSetBuilder.newBuilder()
                                .foregroundColor(EXPECTED_FG_COLOR)
                                .backgroundColor(EXPECTED_BG_COLOR)
                                .modifiers(setOf(VerticalFlip))
                                .build()))
    }

    @Test
    fun shouldBeSameButWithModifierRemovedWhenWithoutModifierIsCalled() {
        assertThat(Tile.createCharacterTile(
                character = EXPECTED_CHAR,
                style = StyleSetBuilder.newBuilder()
                        .foregroundColor(EXPECTED_FG_COLOR)
                        .backgroundColor(EXPECTED_BG_COLOR)
                        .modifiers(setOf(Modifiers.crossedOut(), Modifiers.verticalFlip(), Modifiers.blink()))
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
        assertThat(EXPECTED_TEXT_CHARACTER.withModifiers(Modifiers.crossedOut(), Modifiers.verticalFlip()))
                .isSameAs(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithModifierSIsCalledWithSameModifierS() {
        assertThat(EXPECTED_TEXT_CHARACTER.withModifiers(EXPECTED_MODIFIERS))
                .isSameAs(EXPECTED_TEXT_CHARACTER)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithoutModifierIsCalledWithNonPresentModifier() {
        assertThat(EXPECTED_TEXT_CHARACTER.withoutModifiers(Modifiers.blink()))
                .isSameAs(EXPECTED_TEXT_CHARACTER)
    }

    companion object {
        val EXPECTED_CHAR = 'x'
        val EXPECTED_FG_COLOR = TileColor.fromString("#aabbcc")
        val EXPECTED_BG_COLOR = TileColor.fromString("#223344")
        val EXPECTED_MODIFIERS = setOf(Modifiers.crossedOut(), Modifiers.verticalFlip())

        val EXPECTED_TEXT_CHARACTER = Tile.createCharacterTile(
                character = EXPECTED_CHAR,
                style = StyleSetBuilder.newBuilder()
                        .foregroundColor(EXPECTED_FG_COLOR)
                        .backgroundColor(EXPECTED_BG_COLOR)
                        .modifiers(EXPECTED_MODIFIERS)
                        .build())
    }

}

package org.hexworks.zircon.api.data

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.Modifiers
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.ANSITileColor.*
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.modifier.SimpleModifiers.*
import org.hexworks.zircon.internal.resource.TileType
import org.junit.Test

@Suppress("UsePropertyAccessSyntax")
class DefaultTileTest {

    @Test
    fun shouldProperlyReportTileType() {
        assertThat(Tile.defaultTile().tileType).isEqualTo(TileType.CHARACTER_TILE)
    }

    @Test
    fun shouldProperlyAddNewModifiersAndKeepOldOnesWhenWithAddedModifiersIsCalled() {
        val target = Tile.createCharacterTile('x', StyleSetBuilder.newBuilder()
                .withModifiers(Underline)
                .build())
        assertThat(target.withAddedModifiers(Blink).modifiers)
                .containsExactlyInAnyOrder(Underline, Blink)
    }

    @Test
    fun shouldProperlyRemoveAllModifiersWhenWithoutModifiersIsCalled() {
        val target = Tile.createCharacterTile('x', StyleSetBuilder.newBuilder()
                .withModifiers(Blink)
                .build())
        assertThat(target.withNoModifiers().modifiers).isEmpty()
    }

    @Test
    fun shouldReturnSameTileWhenWithNoModifiersIsCalledAndItHasNoModifiers() {
        val target = Tile.createCharacterTile('x', StyleSetBuilder.newBuilder().build())

        assertThat(target.withNoModifiers()).isSameAs(target)
    }

    @Test
    fun shouldReturnSameTileWhenWithAddedModifiersIsCalledAndItAlreadyHasTheModifiers() {
        val target = Tile.createCharacterTile('x', StyleSetBuilder.newBuilder()
                .withModifiers(Blink, VerticalFlip)
                .build())

        assertThat(target.withAddedModifiers(Blink, VerticalFlip)).isSameAs(target)
    }

    @Test
    fun shouldProperlyCreateCopy() {
        val tile = Tile.createCharacterTile('x', StyleSetBuilder.newBuilder()
                .withBackgroundColor(ANSITileColor.YELLOW)
                .build())

        assertThat(tile.createCopy())
                .isEqualTo(tile)
                .isNotSameAs(tile)
    }

    @Test
    fun shouldGenerateProperCacheKey() {
        val result = TileBuilder.newBuilder()
                .withCharacter('x')
                .withBackgroundColor(ANSITileColor.GREEN)
                .withForegroundColor(TileColor.fromString("#aabbcc"))
                .withModifiers(VerticalFlip)
                .build()
                .generateCacheKey()

        assertThat(result).isEqualTo("CharacterTile(c=x,s=StyleSet(fg=TextColor(r=170,g=187,b=204,a=255),bg=TextColor(r=0,g=128,b=0,a=255),m=[Modifier.VerticalFlip]))")
    }

    @Test
    fun defaultCharacterShouldBeEmptyStringWithBlackAndWhiteAndNoModifiers() {
        assertThat(Tile.defaultTile().character).isEqualTo(' ')
        assertThat(Tile.defaultTile().backgroundColor).isEqualTo(BLACK)
        assertThat(Tile.defaultTile().foregroundColor).isEqualTo(WHITE)
        assertThat(Tile.defaultTile().modifiers).isEmpty()
    }

    @Test
    fun shouldProperlyReportHavingABorderWhenThereIsBorder() {
        assertThat(TileBuilder.newBuilder()
                .withModifiers(Modifiers.border())
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
                .withModifiers(Modifiers.crossedOut())
                .build()
                .withRemovedModifiers(setOf(Modifiers.crossedOut()))
                .modifiers)
                .isEmpty()
    }

    @Test
    fun shouldProperlyCreateCopyWithStyleWhenWithStyleIsCalled() {
        val style = StyleSetBuilder.newBuilder()
                .withForegroundColor(ANSITileColor.BLUE)
                .withBackgroundColor(ANSITileColor.CYAN)
                .withModifiers(Modifiers.crossedOut())
                .build()

        val copy = TileBuilder.newBuilder()
                .build()
                .withStyle(style)

        assertThat(copy.modifiers).isEqualTo(style.modifiers)
        assertThat(copy.backgroundColor).isEqualTo(style.backgroundColor)
        assertThat(copy.foregroundColor).isEqualTo(style.foregroundColor)
    }

    @Test
    fun boldModifierShouldBeBold() {
        assertThat(TileBuilder.newBuilder().withModifiers(Modifiers.crossedOut()).build().isCrossedOut()).isTrue()
    }

    @Test
    fun underlinedModifierShouldBeUnderlined() {
        assertThat(TileBuilder.newBuilder().withModifiers(Modifiers.underline()).build().isUnderlined()).isTrue()
    }

    @Test
    fun crossedOutModifierShouldBeCrossedOut() {
        assertThat(TileBuilder.newBuilder().withModifiers(Modifiers.crossedOut()).build().isCrossedOut()).isTrue()
    }

    @Test
    fun italicModifierShouldBeItalic() {
        assertThat(TileBuilder.newBuilder().withModifiers(Modifiers.verticalFlip()).build().isVerticalFlipped()).isTrue()
    }

    @Test
    fun blinkingModifierShouldBeBlinking() {
        assertThat(TileBuilder.newBuilder().withModifiers(Modifiers.blink()).build().isBlinking()).isTrue()
    }

    @Test
    fun shouldBeSameButWithCharChangedWhenWithCharIsCalled() {
        assertThat(Tile.createCharacterTile(
                character = 'a',
                style = StyleSetBuilder.newBuilder()
                        .withForegroundColor(EXPECTED_FG_COLOR)
                        .withBackgroundColor(EXPECTED_BG_COLOR)
                        .withModifiers(EXPECTED_MODIFIERS)
                        .build())
                .withCharacter(EXPECTED_CHAR))
                .isEqualTo(EXPECTED_CHARACTER_TILE)
    }

    @Test
    fun shouldBeSameButWithFGChangedWhenWithForegroundColorIsCalled() {
        assertThat(Tile.createCharacterTile(
                character = EXPECTED_CHAR,
                style = StyleSetBuilder.newBuilder()
                        .withForegroundColor(GREEN)
                        .withBackgroundColor(EXPECTED_BG_COLOR)
                        .withModifiers(EXPECTED_MODIFIERS)
                        .build())
                .withForegroundColor(EXPECTED_FG_COLOR))
                .isEqualTo(EXPECTED_CHARACTER_TILE)
    }

    @Test
    fun shouldBeSameButWithBGChangedWhenWithBackgroundColorIsCalled() {
        assertThat(Tile.createCharacterTile(
                character = EXPECTED_CHAR,
                style = StyleSetBuilder.newBuilder()
                        .withForegroundColor(EXPECTED_FG_COLOR)
                        .withBackgroundColor(RED)
                        .withModifiers(EXPECTED_MODIFIERS)
                        .build())
                .withBackgroundColor(EXPECTED_BG_COLOR))
                .isEqualTo(EXPECTED_CHARACTER_TILE)
    }

    @Test
    fun shouldBeSameButWithModifiersChangedWhenWithModifiersIsCalled() {
        assertThat(Tile.createCharacterTile(
                character = EXPECTED_CHAR,
                style = StyleSetBuilder.newBuilder()
                        .withForegroundColor(EXPECTED_FG_COLOR)
                        .withBackgroundColor(EXPECTED_BG_COLOR)
                        .withModifiers(setOf(Blink))
                        .build())
                .withModifiers(EXPECTED_MODIFIERS)).isEqualTo(EXPECTED_CHARACTER_TILE)
    }

    @Test
    fun shouldBeSameButWithModifierRemovedWhenWithModifierIsCalled() {
        assertThat(Tile.createCharacterTile(
                character = EXPECTED_CHAR,
                style = StyleSetBuilder.newBuilder()
                        .withForegroundColor(EXPECTED_FG_COLOR)
                        .withBackgroundColor(EXPECTED_BG_COLOR)
                        .withModifiers(setOf(CrossedOut))
                        .build())
                .withModifiers(Modifiers.verticalFlip())).isEqualTo(
                Tile.createCharacterTile(
                        character = EXPECTED_CHAR,
                        style = StyleSetBuilder.newBuilder()
                                .withForegroundColor(EXPECTED_FG_COLOR)
                                .withBackgroundColor(EXPECTED_BG_COLOR)
                                .withModifiers(setOf(VerticalFlip))
                                .build()))
    }

    @Test
    fun shouldBeSameButWithModifierRemovedWhenWithRemovedModifierIsCalled() {
        assertThat(Tile.createCharacterTile(
                character = EXPECTED_CHAR,
                style = StyleSetBuilder.newBuilder()
                        .withForegroundColor(EXPECTED_FG_COLOR)
                        .withBackgroundColor(EXPECTED_BG_COLOR)
                        .withModifiers(setOf(Modifiers.crossedOut(), Modifiers.verticalFlip(), Modifiers.blink()))
                        .build())
                .withRemovedModifiers(Modifiers.blink()))
                .isEqualTo(EXPECTED_CHARACTER_TILE)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithCharIsCalledWithSameChar() {
        assertThat(EXPECTED_CHARACTER_TILE.withCharacter(EXPECTED_CHAR))
                .isSameAs(EXPECTED_CHARACTER_TILE)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithFGColorIsCalledWithSameFGColor() {
        assertThat(EXPECTED_CHARACTER_TILE.withForegroundColor(EXPECTED_FG_COLOR))
                .isSameAs(EXPECTED_CHARACTER_TILE)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithBGColorIsCalledWithSameBGColor() {
        assertThat(EXPECTED_CHARACTER_TILE.withBackgroundColor(EXPECTED_BG_COLOR))
                .isSameAs(EXPECTED_CHARACTER_TILE)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithModifiersIsCalledWithSameModifier() {
        assertThat(EXPECTED_CHARACTER_TILE.withModifiers(Modifiers.crossedOut(), Modifiers.verticalFlip()))
                .isSameAs(EXPECTED_CHARACTER_TILE)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithModifiersSIsCalledWithSameModifierS() {
        assertThat(EXPECTED_CHARACTER_TILE.withModifiers(EXPECTED_MODIFIERS))
                .isSameAs(EXPECTED_CHARACTER_TILE)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithRemovedModifiersIsCalledWithNonPresentModifier() {
        assertThat(EXPECTED_CHARACTER_TILE.withRemovedModifiers(Modifiers.blink()))
                .isSameAs(EXPECTED_CHARACTER_TILE)
    }

    companion object {
        const val EXPECTED_CHAR = 'x'
        val EXPECTED_FG_COLOR = TileColor.fromString("#aabbcc")
        val EXPECTED_BG_COLOR = TileColor.fromString("#223344")
        val EXPECTED_MODIFIERS = setOf(Modifiers.crossedOut(), Modifiers.verticalFlip())

        val EXPECTED_CHARACTER_TILE = Tile.createCharacterTile(
                character = EXPECTED_CHAR,
                style = StyleSetBuilder.newBuilder()
                        .withForegroundColor(EXPECTED_FG_COLOR)
                        .withBackgroundColor(EXPECTED_BG_COLOR)
                        .withModifiers(EXPECTED_MODIFIERS)
                        .build())
    }

}

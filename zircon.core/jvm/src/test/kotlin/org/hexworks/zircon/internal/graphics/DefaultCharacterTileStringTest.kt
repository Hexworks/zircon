package org.hexworks.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.Modifiers
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.CharacterTileStringBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Cell
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TextWrap.NO_WRAPPING
import org.hexworks.zircon.api.graphics.TextWrap.WORD_WRAP
import org.junit.Test

class DefaultCharacterTileStringTest {

    @Test
    fun shouldBuildStringWithDefaultProperly() {
        val result = CharacterTileStringBuilder.newBuilder()
                .withText(TEXT)
                .build()

        val template = TileBuilder.newBuilder().buildCharacterTile()

        assertThat(result.textCharacters()).containsExactly(
                template.withCharacter('T'),
                template.withCharacter('E'),
                template.withCharacter('X'),
                template.withCharacter('T'))
    }

    @Test
    fun shouldBuildStringWithCustomProperly() {
        val result = CharacterTileStringBuilder.newBuilder()
                .withBackgroundColor(BACKGROUND)
                .withForegroundColor(FOREGROUND)
                .withModifiers(MODIFIER)
                .withText(TEXT)
                .withTextWrap(NO_WRAPPING)
                .build() as DefaultCharacterTileString

        val template = TileBuilder.newBuilder()
                .withForegroundColor(FOREGROUND)
                .withBackgroundColor(BACKGROUND)
                .withModifiers(MODIFIER)
                .buildCharacterTile()

        assertThat(result.textCharacters()).containsExactly(
                template.withCharacter('T'),
                template.withCharacter('E'),
                template.withCharacter('X'),
                template.withCharacter('T'))
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenOffsetColIsTooBig() {
        val surface = TileGraphicsBuilder.newBuilder()
                .withSize(Size.create(2, 2))
                .build()

        CharacterTileStringBuilder.newBuilder()
                .withText(TEXT)
                .withTextWrap(NO_WRAPPING)
                .build().drawOnto(surface, Position.create(2, 1))
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenOffsetRowIsTooBig() {
        val surface = TileGraphicsBuilder.newBuilder()
                .withSize(Size.create(2, 2))
                .build()

        CharacterTileStringBuilder.newBuilder()
                .withText(TEXT)
                .withTextWrap(NO_WRAPPING)
                .build().drawOnto(surface, Position.create(1, 2))
    }

    @Test
    fun shouldProperlyWriteNoWrapOverlappingStringToTileGraphic() {
        val surface = TileGraphicsBuilder.newBuilder()
                .withSize(Size.create(2, 2))
                .build()

        CharacterTileStringBuilder.newBuilder()
                .withText(TEXT)
                .withTextWrap(NO_WRAPPING)
                .build().drawOnto(surface)

        assertThat(surface.getTileAt(Position.create(0, 0))
                .get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo('T')
        assertThat(surface.getTileAt(Position.create(1, 0)).get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo('E')
        assertThat(surface.getTileAt(Position.create(0, 1)).get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo(' ')
        assertThat(surface.getTileAt(Position.create(1, 1)).get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo(' ')
    }

    @Test
    fun WordWrapShouldWorkCorrectlyFirstTest() {
        val surface = TileGraphicsBuilder.newBuilder()
                .withSize(Size.create(5, 1))
                .build()

        val textCharacterString = CharacterTileStringBuilder.newBuilder()
                .withText("atest")
                .withTextWrap(WORD_WRAP)
                .build()
        textCharacterString.drawOnto(surface)

        // a and space should fit on the first line
        assertThat(surface.getTileAt(Position.create(0, 0)).get()
                .asCharacterTile()
                .get()
                .character).isEqualTo('a')
        assertThat(surface.getTileAt(Position.create(1, 0)).get()
                .asCharacterTile()
                .get()
                .character).isEqualTo('t')
        assertThat(surface.getTileAt(Position.create(2, 0)).get()
                .asCharacterTile()
                .get()
                .character).isEqualTo('e')
        assertThat(surface.getTileAt(Position.create(3, 0)).get()
                .asCharacterTile()
                .get()
                .character).isEqualTo('s')
        assertThat(surface.getTileAt(Position.create(4, 0)).get()
                .asCharacterTile()
                .get()
                .character).isEqualTo('t')
    }

    @Test
    fun WordWrapShouldWorkMultipleWords() {
        val surface = TileGraphicsBuilder.newBuilder()
                .withSize(Size.create(4, 2))
                .build()

        val textCharacterString = CharacterTileStringBuilder.newBuilder()
                .withText("a test")
                .withTextWrap(WORD_WRAP)
                .build()
        textCharacterString.drawOnto(surface)

        // a and space should fit on the first line
        assertThat(surface.getTileAt(Position.create(0, 0)).get()
                .asCharacterTile()
                .get()
                .character).isEqualTo('a')
        assertThat(surface.getTileAt(Position.create(1, 0)).get()
                .asCharacterTile()
                .get()
                .character).isEqualTo(' ')
        assertThat(surface.getTileAt(Position.create(0, 1)).get()
                .asCharacterTile()
                .get()
                .character).isEqualTo('t')
        assertThat(surface.getTileAt(Position.create(1, 1)).get()
                .asCharacterTile()
                .get()
                .character).isEqualTo('e')
        assertThat(surface.getTileAt(Position.create(2, 1)).get()
                .asCharacterTile()
                .get()
                .character).isEqualTo('s')
        assertThat(surface.getTileAt(Position.create(3, 1)).get()
                .asCharacterTile()
                .get()
                .character).isEqualTo('t')
    }

    @Test
    fun wordWrapShouldWrapAsWordTooBigForSingleRow() {
        val surface = TileGraphicsBuilder.newBuilder()
                .withSize(Size.create(4, 2))
                .build()

        val textCharacterString = CharacterTileStringBuilder.newBuilder()
                .withText("atest")
                .withTextWrap(WORD_WRAP)
                .build()
        textCharacterString.drawOnto(surface)

        val expectedTiles = listOf(
                Cell.create(Position.create(0, 0), Tile.defaultTile().withCharacter('a')),
                Cell.create(Position.create(1, 0), Tile.defaultTile().withCharacter('t')),
                Cell.create(Position.create(2, 0), Tile.defaultTile().withCharacter('e')),
                Cell.create(Position.create(3, 0), Tile.defaultTile().withCharacter('s')),
                Cell.create(Position.create(0, 1), Tile.defaultTile().withCharacter('t')),
                Cell.create(Position.create(1, 1), Tile.empty()),
                Cell.create(Position.create(2, 1), Tile.empty()),
                Cell.create(Position.create(3, 1), Tile.empty()))

        assertThat(surface.fetchCells()).containsExactlyElementsOf(expectedTiles)

    }

    @Test
    fun wordWrapShouldWorkCorrectly() {
        val surface = TileGraphicsBuilder.newBuilder()
                .withSize(Size.create(5, 4))
                .build()

        val textCharacterString = CharacterTileStringBuilder.newBuilder()
                .withText("a test thghty")
                .withTextWrap(WORD_WRAP)
                .build()
        textCharacterString.drawOnto(surface)

        val expectedTiles = listOf(
                Cell.create(Position.create(0, 0), Tile.defaultTile().withCharacter('a')),
                Cell.create(Position.create(1, 0), Tile.defaultTile().withCharacter(' ')),
                Cell.create(Position.create(2, 0), Tile.empty()),
                Cell.create(Position.create(3, 0), Tile.empty()),
                Cell.create(Position.create(4, 0), Tile.empty()),

                Cell.create(Position.create(0, 1), Tile.defaultTile().withCharacter('t')),
                Cell.create(Position.create(1, 1), Tile.defaultTile().withCharacter('e')),
                Cell.create(Position.create(2, 1), Tile.defaultTile().withCharacter('s')),
                Cell.create(Position.create(3, 1), Tile.defaultTile().withCharacter('t')),
                Cell.create(Position.create(4, 1), Tile.defaultTile().withCharacter(' ')),

                Cell.create(Position.create(0, 2), Tile.defaultTile().withCharacter('t')),
                Cell.create(Position.create(1, 2), Tile.defaultTile().withCharacter('h')),
                Cell.create(Position.create(2, 2), Tile.defaultTile().withCharacter('g')),
                Cell.create(Position.create(3, 2), Tile.defaultTile().withCharacter('h')),
                Cell.create(Position.create(4, 2), Tile.defaultTile().withCharacter('t')),

                Cell.create(Position.create(0, 3), Tile.defaultTile().withCharacter('y')),
                Cell.create(Position.create(1, 3), Tile.empty()),
                Cell.create(Position.create(2, 3), Tile.empty()),
                Cell.create(Position.create(3, 3), Tile.empty()),
                Cell.create(Position.create(4, 3), Tile.empty()))

        assertThat(surface.fetchCells()).containsExactlyElementsOf(expectedTiles)
    }

    @Test
    fun shouldProperlyWriteNoWrapStringToTileGraphicWithOffset() {
        val surface = TileGraphicsBuilder.newBuilder()
                .withSize(Size.create(2, 2))
                .build()

        CharacterTileStringBuilder.newBuilder()
                .withText(TEXT)
                .withTextWrap(NO_WRAPPING)
                .build().drawOnto(surface, Position.offset1x1())

        assertThat(surface.getTileAt(Position.create(0, 0)).get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo(' ')
        assertThat(surface.getTileAt(Position.create(1, 0)).get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo(' ')
        assertThat(surface.getTileAt(Position.create(0, 1)).get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo(' ')
        assertThat(surface.getTileAt(Position.create(1, 1)).get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo('T')


    }

    @Test
    fun shouldProperlyWriteWrapStringToTileGraphicWithoutOffset() {
        val surface = TileGraphicsBuilder.newBuilder()
                .withSize(Size.create(2, 2))
                .build()

        CharacterTileStringBuilder.newBuilder()
                .withText(TEXT)
                .build().drawOnto(surface)

        assertThat(surface.getTileAt(Position.create(0, 0)).get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo('T')
        assertThat(surface.getTileAt(Position.create(1, 0)).get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo('E')
        assertThat(surface.getTileAt(Position.create(0, 1)).get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo('X')
        assertThat(surface.getTileAt(Position.create(1, 1)).get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo('T')


    }

    @Test
    fun shouldProperlyWriteWrapStringToTileGraphicWithOffset() {
        val surface = TileGraphicsBuilder.newBuilder()
                .withSize(Size.create(2, 2))
                .build()

        CharacterTileStringBuilder.newBuilder()
                .withText(TEXT)
                .build().drawOnto(surface, Position.create(1, 0))

        assertThat(surface.getTileAt(Position.create(0, 0)).get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo(' ')
        assertThat(surface.getTileAt(Position.create(1, 0)).get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo('T')
        assertThat(surface.getTileAt(Position.create(0, 1)).get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo('E')
        assertThat(surface.getTileAt(Position.create(1, 1)).get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo('X')


    }

    @Test
    fun shouldProperlyWriteStringToTileGraphicWhenLengthIs1() {
        val surface = TileGraphicsBuilder.newBuilder()
                .withSize(Size.create(2, 2))
                .build()

        CharacterTileStringBuilder.newBuilder()
                .withText("T")
                .build().drawOnto(surface, Position.create(0, 0))

        assertThat(surface.getTileAt(Position.create(0, 0)).get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo('T')
        assertThat(surface.getTileAt(Position.create(1, 0)).get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo(' ')
        assertThat(surface.getTileAt(Position.create(0, 1)).get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo(' ')
        assertThat(surface.getTileAt(Position.create(1, 1)).get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo(' ')


    }

    @Test
    fun shouldProperlyTruncateStringWhenDoesNotFitOnTileGraphic() {
        val surface = TileGraphicsBuilder.newBuilder()
                .withSize(Size.create(2, 2))
                .build()

        CharacterTileStringBuilder.newBuilder()
                .withText("TEXTTEXT")
                .build().drawOnto(surface, Position.create(0, 0))

        assertThat(surface.getTileAt(Position.create(0, 0)).get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo('T')
        assertThat(surface.getTileAt(Position.create(1, 0)).get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo('E')
        assertThat(surface.getTileAt(Position.create(0, 1)).get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo('X')
        assertThat(surface.getTileAt(Position.create(1, 1)).get()
                .asCharacterTile()
                .get()
                .character)
                .isEqualTo('T')


    }

    @Test
    fun shouldAddTwoStringsTogetherProperly() {

        val string = CharacterTileStringBuilder.newBuilder()
                .withText("TE")
                .build()

        val other = CharacterTileStringBuilder.newBuilder()
                .withText("XT")
                .build()

        val template = TileBuilder.newBuilder().buildCharacterTile()

        assertThat(string.plus(other).textCharacters()).containsExactly(
                template.withCharacter('T'),
                template.withCharacter('E'),
                template.withCharacter('X'),
                template.withCharacter('T'))
    }

    companion object {
        val FOREGROUND = ANSITileColor.RED
        val BACKGROUND = ANSITileColor.GREEN
        val MODIFIER = Modifiers.crossedOut()
        val TEXT = "TEXT"
    }
}

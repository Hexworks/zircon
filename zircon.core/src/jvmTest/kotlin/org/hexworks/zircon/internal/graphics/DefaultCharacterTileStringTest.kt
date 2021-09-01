package org.hexworks.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.Modifiers
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.CharacterTileStringBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.toCharacterTileString
import org.hexworks.zircon.api.graphics.TextWrap.NO_WRAPPING
import org.hexworks.zircon.api.graphics.TextWrap.WORD_WRAP
import org.hexworks.zircon.fetchCharacters
import org.junit.Test

class DefaultCharacterTileStringTest {

    @Test
    fun shouldBuildStringWithDefaultProperly() {
        val result = CharacterTileStringBuilder.newBuilder()
            .withText(TEXT)
            .build()

        val template = TileBuilder.newBuilder().buildCharacterTile()

        assertThat(result.characterTiles).containsExactly(
            template.withCharacter('T'),
            template.withCharacter('E'),
            template.withCharacter('X'),
            template.withCharacter('T')
        )
    }

    @Test
    fun shouldProperlyCreateFromString() {
        val result = "foobar".toCharacterTileString()

        assertThat(result.characterTiles.map { it.character })
            .containsExactly('f', 'o', 'o', 'b', 'a', 'r')
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

        assertThat(result.characterTiles).containsExactly(
            template.withCharacter('T'),
            template.withCharacter('E'),
            template.withCharacter('X'),
            template.withCharacter('T')
        )
    }

    @Test
    fun shouldTruncateWhenThereIsNoWrapAndDoesntFit() {
        val surface = TileGraphicsBuilder.newBuilder()
            .withSize(Size.create(2, 2))
            .build()

        surface.draw(
            CharacterTileStringBuilder.newBuilder()
                .withText(TEXT)
                .withTextWrap(NO_WRAPPING)
                .build(), Position.create(1, 1)
        )

        assertThat(surface.fetchCharacters()).containsExactly(
            ' ', ' ',
            ' ', 'T'
        )
    }

    @Test
    fun shouldProperlyWriteNoWrapOverlappingStringToTileGraphic() {
        val surface = TileGraphicsBuilder.newBuilder()
            .withSize(Size.create(2, 2))
            .build()

        surface.draw(
            CharacterTileStringBuilder.newBuilder()
                .withText(TEXT)
                .withTextWrap(NO_WRAPPING)
                .build()
        )

        assertThat(
            surface.getTileAtOrNull(Position.create(0, 0))
                ?.asCharacterTileOrNull()
                ?.character
        )
            .isEqualTo('T')
        assertThat(
            surface.getTileAtOrNull(Position.create(1, 0))
                ?.asCharacterTileOrNull()
                ?.character
        )
            .isEqualTo('E')
        assertThat(
            surface.getTileAtOrNull(Position.create(0, 1))
                ?.asCharacterTileOrNull()
                ?.character
        ).isNull()
        assertThat(
            surface.getTileAtOrNull(Position.create(1, 1))
                ?.asCharacterTileOrNull()
                ?.character
        ).isNull()
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
        surface.draw(textCharacterString)

        // a and space should fit on the first line
        assertThat(
            surface.getTileAtOrNull(Position.create(0, 0))
                ?.asCharacterTileOrNull()

                ?.character
        ).isEqualTo('a')
        assertThat(
            surface.getTileAtOrNull(Position.create(1, 0))
                ?.asCharacterTileOrNull()

                ?.character
        ).isEqualTo('t')
        assertThat(
            surface.getTileAtOrNull(Position.create(2, 0))
                ?.asCharacterTileOrNull()

                ?.character
        ).isEqualTo('e')
        assertThat(
            surface.getTileAtOrNull(Position.create(3, 0))
                ?.asCharacterTileOrNull()

                ?.character
        ).isEqualTo('s')
        assertThat(
            surface.getTileAtOrNull(Position.create(4, 0))
                ?.asCharacterTileOrNull()

                ?.character
        ).isEqualTo('t')
    }

    @Test
    fun shouldWorkWhenWordWrappingWithMultipleWords() {
        val surface = TileGraphicsBuilder.newBuilder()
            .withSize(Size.create(4, 2))
            .build()

        val textCharacterString = CharacterTileStringBuilder.newBuilder()
            .withText("a test")
            .withTextWrap(WORD_WRAP)
            .withSize(surface.size)
            .build()
        surface.draw(textCharacterString)

        assertThat(surface.fetchCharacters()).containsExactly(
            'a', ' ', ' ', ' ',
            't', 'e', 's', 't'
        )
    }

    @Test
    fun wordWrapShouldWrapAsWordTooBigForSingleRow() {
        val surface = TileGraphicsBuilder.newBuilder()
            .withSize(Size.create(4, 2))
            .build()

        val textCharacterString = CharacterTileStringBuilder.newBuilder()
            .withText("atest")
            .withTextWrap(WORD_WRAP)
            .withSize(surface.size)
            .build()
        surface.draw(textCharacterString)

        assertThat(surface.fetchCharacters()).containsExactly(
            'a', 't', 'e', 's',
            't', ' ', ' ', ' '
        )

    }

    @Test
    fun wordWrapShouldWorkCorrectly() {
        val surface = TileGraphicsBuilder.newBuilder()
            .withSize(5, 4)
            .build()

        val textCharacterString = CharacterTileStringBuilder.newBuilder()
            .withText("a test thghty")
            .withTextWrap(WORD_WRAP)
            .withSize(surface.size)
            .build()
        surface.draw(textCharacterString)

        assertThat(surface.fetchCharacters()).containsExactly(
            'a', ' ', ' ', ' ', ' ',
            't', 'e', 's', 't', ' ',
            't', 'h', 'g', 'h', 't',
            'y', ' ', ' ', ' ', ' '
        )
    }

    @Test
    fun shouldProperlyWriteNoWrapStringToTileGraphicWithOffset() {
        val surface = TileGraphicsBuilder.newBuilder()
            .withSize(Size.create(2, 2))
            .build()

        surface.draw(
            CharacterTileStringBuilder.newBuilder()
                .withText(TEXT)
                .withTextWrap(NO_WRAPPING)
                .withSize(surface.size)
                .build(), Position.offset1x1()
        )

        assertThat(surface.fetchCharacters()).containsExactly(
            ' ', ' ',
            ' ', 'T'
        )


    }

    @Test
    fun shouldProperlyWriteWrapStringToTileGraphicWithoutOffset() {
        val surface = TileGraphicsBuilder.newBuilder()
            .withSize(Size.create(2, 2))
            .build()

        surface.draw(
            CharacterTileStringBuilder.newBuilder()
                .withText(TEXT)
                .withSize(surface.size)
                .build()
        )

        assertThat(surface.fetchCharacters()).containsExactly(
            'T', 'E',
            'X', 'T'
        )
    }

    @Test
    fun shouldProperlyWriteWrapStringToTileGraphicWithOffset() {
        val surface = TileGraphicsBuilder.newBuilder()
            .withSize(Size.create(2, 2))
            .build()

        surface.draw(
            CharacterTileStringBuilder.newBuilder()
                .withText(TEXT)
                .withSize(surface.size)
                .build(), Position.create(1, 0)
        )

        assertThat(surface.fetchCharacters()).containsExactly(
            ' ', 'T',
            ' ', 'X'
        )
    }

    @Test
    fun shouldProperlyWriteStringToTileGraphicWhenLengthIs1() {
        val surface = TileGraphicsBuilder.newBuilder()
            .withSize(Size.create(2, 2))
            .build()

        surface.draw(
            CharacterTileStringBuilder.newBuilder()
                .withText("T")
                .withSize(surface.size)
                .build(), Position.create(0, 0)
        )

        assertThat(surface.fetchCharacters()).containsExactly(
            'T', ' ',
            ' ', ' '
        )
    }

    @Test
    fun shouldProperlyTruncateStringWhenDoesNotFitOnTileGraphic() {
        val surface = TileGraphicsBuilder.newBuilder()
            .withSize(Size.create(2, 2))
            .build()

        surface.draw(
            CharacterTileStringBuilder.newBuilder()
                .withText("TEXTTEXT")
                .withSize(surface.size)
                .build(), Position.create(0, 0)
        )

        assertThat(surface.fetchCharacters()).containsExactly(
            'T', 'E',
            'X', 'T'
        )
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

        assertThat(string.plus(other).characterTiles).containsExactly(
            template.withCharacter('T'),
            template.withCharacter('E'),
            template.withCharacter('X'),
            template.withCharacter('T')
        )
    }

    companion object {
        val FOREGROUND = ANSITileColor.RED
        val BACKGROUND = ANSITileColor.GREEN
        val MODIFIER = Modifiers.crossedOut()
        const val TEXT = "TEXT"
    }
}

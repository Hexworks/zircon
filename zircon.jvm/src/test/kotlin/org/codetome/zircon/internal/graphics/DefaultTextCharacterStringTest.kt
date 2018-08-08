package org.codetome.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.builder.data.TileBuilder
import org.codetome.zircon.api.builder.graphics.TextCharacterStringBuilder
import org.codetome.zircon.api.builder.graphics.TileImageBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.graphics.TextWrap
import org.codetome.zircon.api.interop.Modifiers
import org.junit.Test

class DefaultTextCharacterStringTest {

    @Test
    fun shouldBuildStringWithDefaultProperly() {
        val result = TextCharacterStringBuilder.newBuilder()
                .text(TEXT)
                .build()

        val template = TileBuilder.newBuilder().buildCharacterTile()

        assertThat(result.getTextCharacters()).containsExactly(
                template.withCharacter('T'),
                template.withCharacter('E'),
                template.withCharacter('X'),
                template.withCharacter('T'))
    }

    @Test
    fun shouldBuildStringWithCustomProperly() {
        val result = TextCharacterStringBuilder.newBuilder()
                .backgroundColor(BACKGROUND)
                .foregroundColor(FOREGROUND)
                .modifiers(MODIFIER)
                .text(TEXT)
                .textWrap(TextWrap.NO_WRAPPING)
                .build() as DefaultTextCharacterString

        val template = TileBuilder.newBuilder()
                .foregroundColor(FOREGROUND)
                .backgroundColor(BACKGROUND)
                .modifiers(MODIFIER)
                .buildCharacterTile()

        assertThat(result.getTextCharacters()).containsExactly(
                template.withCharacter('T'),
                template.withCharacter('E'),
                template.withCharacter('X'),
                template.withCharacter('T'))
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenOffsetColIsTooBig() {
        val surface = TileImageBuilder.newBuilder()
                .size(Size.create(2, 2))
                .build()

        TextCharacterStringBuilder.newBuilder()
                .text(TEXT)
                .textWrap(TextWrap.NO_WRAPPING)
                .build().drawOnto(surface, Position.create(2, 1))
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenOffsetRowIsTooBig() {
        val surface = TileImageBuilder.newBuilder()
                .size(Size.create(2, 2))
                .build()

        TextCharacterStringBuilder.newBuilder()
                .text(TEXT)
                .textWrap(TextWrap.NO_WRAPPING)
                .build().drawOnto(surface, Position.create(1, 2))
    }

    @Test
    fun shouldProperlyWriteNoWrapOverlappingStringToTextImage() {
        val surface = TileImageBuilder.newBuilder()
                .size(Size.create(2, 2))
                .build()

        TextCharacterStringBuilder.newBuilder()
                .text(TEXT)
                .textWrap(TextWrap.NO_WRAPPING)
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
        val surface = TileImageBuilder.newBuilder()
                .size(Size.create(5, 1))
                .build()

        val textCharacterString = TextCharacterStringBuilder.newBuilder()
                .text("atest")
                .textWrap(TextWrap.WORD_WRAP)
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
        val surface = TileImageBuilder.newBuilder()
                .size(Size.create(4, 2))
                .build()

        val textCharacterString = TextCharacterStringBuilder.newBuilder()
                .text("a test")
                .textWrap(TextWrap.WORD_WRAP)
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
    fun WordWrapShouldWrapAsWordTooBigForSingleColumn() {
        val surface = TileImageBuilder.newBuilder()
                .size(Size.create(4, 2))
                .build()

        val textCharacterString = TextCharacterStringBuilder.newBuilder()
                .text("atest")
                .textWrap(TextWrap.WORD_WRAP)
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
        assertThat(surface.getTileAt(Position.create(0, 1)).get()
                .asCharacterTile()
                .get()
                .character).isEqualTo('t')
    }

    @Test
    fun WordWrapShouldWorkCorrectly() {
        val surface = TileImageBuilder.newBuilder()
                .size(Size.create(5, 4))
                .build()

        val textCharacterString = TextCharacterStringBuilder.newBuilder()
                .text("a test thghty")
                .textWrap(TextWrap.WORD_WRAP)
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

        //words a, ` ` and test would make up 6 characters so test should wrap to the next line
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
        assertThat(surface.getTileAt(Position.create(4, 1)).get()
                .asCharacterTile()
                .get()
                .character).isEqualTo(' ')
        //thghty makes up 6 characters which is larger then the number of rows. So it should character wrap
        assertThat(surface.getTileAt(Position.create(0, 2)).get()
                .asCharacterTile()
                .get()
                .character).isEqualTo('t')
        assertThat(surface.getTileAt(Position.create(1, 2)).get()
                .asCharacterTile()
                .get()
                .character).isEqualTo('h')
        assertThat(surface.getTileAt(Position.create(2, 2)).get()
                .asCharacterTile()
                .get()
                .character).isEqualTo('g')
        assertThat(surface.getTileAt(Position.create(3, 2)).get()
                .asCharacterTile()
                .get()
                .character).isEqualTo('h')
        assertThat(surface.getTileAt(Position.create(4, 2)).get()
                .asCharacterTile()
                .get()
                .character).isEqualTo('t')
        assertThat(surface.getTileAt(Position.create(0, 3)).get()
                .asCharacterTile()
                .get()
                .character).isEqualTo('y')
    }

    @Test
    fun shouldProperlyWriteNoWrapStringToTextImageWithOffset() {
        val surface = TileImageBuilder.newBuilder()
                .size(Size.create(2, 2))
                .build()

        TextCharacterStringBuilder.newBuilder()
                .text(TEXT)
                .textWrap(TextWrap.NO_WRAPPING)
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
    fun shouldProperlyWriteWrapStringToTextImageWithoutOffset() {
        val surface = TileImageBuilder.newBuilder()
                .size(Size.create(2, 2))
                .build()

        TextCharacterStringBuilder.newBuilder()
                .text(TEXT)
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
    fun shouldProperlyWriteWrapStringToTextImageWithOffset() {
        val surface = TileImageBuilder.newBuilder()
                .size(Size.create(2, 2))
                .build()

        TextCharacterStringBuilder.newBuilder()
                .text(TEXT)
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
    fun shouldProperlyWriteStringToTextImageWhenLengthIs1() {
        val surface = TileImageBuilder.newBuilder()
                .size(Size.create(2, 2))
                .build()

        TextCharacterStringBuilder.newBuilder()
                .text("T")
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
    fun shouldProperlyTruncateStringWhenDoesNotFitOnTextImage() {
        val surface = TileImageBuilder.newBuilder()
                .size(Size.create(2, 2))
                .build()

        TextCharacterStringBuilder.newBuilder()
                .text("TEXTTEXT")
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

        val string = TextCharacterStringBuilder.newBuilder()
                .text("TE")
                .build()

        val other = TextCharacterStringBuilder.newBuilder()
                .text("XT")
                .build()

        val template = TileBuilder.newBuilder().buildCharacterTile()

        assertThat(string.plus(other).getTextCharacters()).containsExactly(
                template.withCharacter('T'),
                template.withCharacter('E'),
                template.withCharacter('X'),
                template.withCharacter('T'))
    }

    companion object {
        val FOREGROUND = ANSITextColor.RED
        val BACKGROUND = ANSITextColor.GREEN
        val MODIFIER = Modifiers.crossedOut()
        val TEXT = "TEXT"
    }
}

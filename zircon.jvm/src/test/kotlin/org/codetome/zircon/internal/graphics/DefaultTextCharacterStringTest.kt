package org.codetome.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.builder.graphics.TextCharacterStringBuilder
import org.codetome.zircon.api.builder.graphics.TextImageBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.graphics.TextWrap
import org.codetome.zircon.api.interop.Modifiers
import org.junit.Test

class DefaultTextCharacterStringTest {

    @Test
    fun shouldBuildStringWithDefaultProperly() {
        val result = TextCharacterStringBuilder.newBuilder()
                .text(TEXT)
                .build()

        val template = TextCharacterBuilder.newBuilder().build()

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

        val template = TextCharacterBuilder.newBuilder()
                .foregroundColor(FOREGROUND)
                .backgroundColor(BACKGROUND)
                .modifiers(MODIFIER)
                .build()

        assertThat(result.getTextCharacters()).containsExactly(
                template.withCharacter('T'),
                template.withCharacter('E'),
                template.withCharacter('X'),
                template.withCharacter('T'))
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenOffsetColIsTooBig() {
        val surface = TextImageBuilder.newBuilder()
                .size(Size.create(2, 2))
                .build()

        TextCharacterStringBuilder.newBuilder()
                .text(TEXT)
                .textWrap(TextWrap.NO_WRAPPING)
                .build().drawOnto(surface, Position.create(2, 1))
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenOffsetRowIsTooBig() {
        val surface = TextImageBuilder.newBuilder()
                .size(Size.create(2, 2))
                .build()

        TextCharacterStringBuilder.newBuilder()
                .text(TEXT)
                .textWrap(TextWrap.NO_WRAPPING)
                .build().drawOnto(surface, Position.create(1, 2))
    }

    @Test
    fun shouldProperlyWriteNoWrapOverlappingStringToTextImage() {
        val surface = TextImageBuilder.newBuilder()
                .size(Size.create(2, 2))
                .build()

        TextCharacterStringBuilder.newBuilder()
                .text(TEXT)
                .textWrap(TextWrap.NO_WRAPPING)
                .build().drawOnto(surface)

        assertThat(surface.getCharacterAt(Position.create(0, 0)).get().getCharacter())
                .isEqualTo('T')
        assertThat(surface.getCharacterAt(Position.create(1, 0)).get().getCharacter())
                .isEqualTo('E')
        assertThat(surface.getCharacterAt(Position.create(0, 1)).get().getCharacter())
                .isEqualTo(' ')
        assertThat(surface.getCharacterAt(Position.create(1, 1)).get().getCharacter())
                .isEqualTo(' ')
    }

    @Test
    fun WordWrapShouldWorkCorrectlyFirstTest() {
        val surface = TextImageBuilder.newBuilder()
                .size(Size.create(5, 1))
                .build()

        val textCharacterString = TextCharacterStringBuilder.newBuilder()
                .text("atest")
                .textWrap(TextWrap.WORD_WRAP)
                .build()
        textCharacterString.drawOnto(surface)

        // a and space should fit on the first line
        assertThat(surface.getCharacterAt(Position.create(0, 0)).get().getCharacter()).isEqualTo('a')
        assertThat(surface.getCharacterAt(Position.create(1, 0)).get().getCharacter()).isEqualTo('t')
        assertThat(surface.getCharacterAt(Position.create(2, 0)).get().getCharacter()).isEqualTo('e')
        assertThat(surface.getCharacterAt(Position.create(3, 0)).get().getCharacter()).isEqualTo('s')
        assertThat(surface.getCharacterAt(Position.create(4, 0)).get().getCharacter()).isEqualTo('t')
    }

    @Test
    fun WordWrapShouldWorkMultipleWords() {
        val surface = TextImageBuilder.newBuilder()
                .size(Size.create(4, 2))
                .build()

        val textCharacterString = TextCharacterStringBuilder.newBuilder()
                .text("a test")
                .textWrap(TextWrap.WORD_WRAP)
                .build()
        textCharacterString.drawOnto(surface)

        // a and space should fit on the first line
        assertThat(surface.getCharacterAt(Position.create(0, 0)).get().getCharacter()).isEqualTo('a')
        assertThat(surface.getCharacterAt(Position.create(1, 0)).get().getCharacter()).isEqualTo(' ')
        assertThat(surface.getCharacterAt(Position.create(0, 1)).get().getCharacter()).isEqualTo('t')
        assertThat(surface.getCharacterAt(Position.create(1, 1)).get().getCharacter()).isEqualTo('e')
        assertThat(surface.getCharacterAt(Position.create(2, 1)).get().getCharacter()).isEqualTo('s')
        assertThat(surface.getCharacterAt(Position.create(3, 1)).get().getCharacter()).isEqualTo('t')
    }

    @Test
    fun WordWrapShouldWrapAsWordTooBigForSingleColumn() {
        val surface = TextImageBuilder.newBuilder()
                .size(Size.create(4, 2))
                .build()

        val textCharacterString = TextCharacterStringBuilder.newBuilder()
                .text("atest")
                .textWrap(TextWrap.WORD_WRAP)
                .build()
        textCharacterString.drawOnto(surface)

        // a and space should fit on the first line
        assertThat(surface.getCharacterAt(Position.create(0, 0)).get().getCharacter()).isEqualTo('a')
        assertThat(surface.getCharacterAt(Position.create(1, 0)).get().getCharacter()).isEqualTo('t')
        assertThat(surface.getCharacterAt(Position.create(2, 0)).get().getCharacter()).isEqualTo('e')
        assertThat(surface.getCharacterAt(Position.create(3, 0)).get().getCharacter()).isEqualTo('s')
        assertThat(surface.getCharacterAt(Position.create(0, 1)).get().getCharacter()).isEqualTo('t')
    }

    @Test
    fun WordWrapShouldWorkCorrectly() {
        val surface = TextImageBuilder.newBuilder()
                .size(Size.create(5, 4))
                .build()

        val textCharacterString = TextCharacterStringBuilder.newBuilder()
                .text("a test thghty")
                .textWrap(TextWrap.WORD_WRAP)
                .build()
        textCharacterString.drawOnto(surface)

        // a and space should fit on the first line
        assertThat(surface.getCharacterAt(Position.create(0, 0)).get().getCharacter()).isEqualTo('a')
        assertThat(surface.getCharacterAt(Position.create(1, 0)).get().getCharacter()).isEqualTo(' ')

        //words a, ` ` and test would make up 6 characters so test should wrap to the next line
        assertThat(surface.getCharacterAt(Position.create(0, 1)).get().getCharacter()).isEqualTo('t')
        assertThat(surface.getCharacterAt(Position.create(1, 1)).get().getCharacter()).isEqualTo('e')
        assertThat(surface.getCharacterAt(Position.create(2, 1)).get().getCharacter()).isEqualTo('s')
        assertThat(surface.getCharacterAt(Position.create(3, 1)).get().getCharacter()).isEqualTo('t')
        assertThat(surface.getCharacterAt(Position.create(4, 1)).get().getCharacter()).isEqualTo(' ')
        //thghty makes up 6 characters which is larger then the number of rows. So it should character wrap
        assertThat(surface.getCharacterAt(Position.create(0, 2)).get().getCharacter()).isEqualTo('t')
        assertThat(surface.getCharacterAt(Position.create(1, 2)).get().getCharacter()).isEqualTo('h')
        assertThat(surface.getCharacterAt(Position.create(2, 2)).get().getCharacter()).isEqualTo('g')
        assertThat(surface.getCharacterAt(Position.create(3, 2)).get().getCharacter()).isEqualTo('h')
        assertThat(surface.getCharacterAt(Position.create(4, 2)).get().getCharacter()).isEqualTo('t')
        assertThat(surface.getCharacterAt(Position.create(0, 3)).get().getCharacter()).isEqualTo('y')
    }

    @Test
    fun shouldProperlyWriteNoWrapStringToTextImageWithOffset() {
        val surface = TextImageBuilder.newBuilder()
                .size(Size.create(2, 2))
                .build()

        TextCharacterStringBuilder.newBuilder()
                .text(TEXT)
                .textWrap(TextWrap.NO_WRAPPING)
                .build().drawOnto(surface, Position.offset1x1())

        assertThat(surface.getCharacterAt(Position.create(0, 0)).get().getCharacter())
                .isEqualTo(' ')
        assertThat(surface.getCharacterAt(Position.create(1, 0)).get().getCharacter())
                .isEqualTo(' ')
        assertThat(surface.getCharacterAt(Position.create(0, 1)).get().getCharacter())
                .isEqualTo(' ')
        assertThat(surface.getCharacterAt(Position.create(1, 1)).get().getCharacter())
                .isEqualTo('T')


    }

    @Test
    fun shouldProperlyWriteWrapStringToTextImageWithoutOffset() {
        val surface = TextImageBuilder.newBuilder()
                .size(Size.create(2, 2))
                .build()

        TextCharacterStringBuilder.newBuilder()
                .text(TEXT)
                .build().drawOnto(surface)

        assertThat(surface.getCharacterAt(Position.create(0, 0)).get().getCharacter())
                .isEqualTo('T')
        assertThat(surface.getCharacterAt(Position.create(1, 0)).get().getCharacter())
                .isEqualTo('E')
        assertThat(surface.getCharacterAt(Position.create(0, 1)).get().getCharacter())
                .isEqualTo('X')
        assertThat(surface.getCharacterAt(Position.create(1, 1)).get().getCharacter())
                .isEqualTo('T')


    }

    @Test
    fun shouldProperlyWriteWrapStringToTextImageWithOffset() {
        val surface = TextImageBuilder.newBuilder()
                .size(Size.create(2, 2))
                .build()

        TextCharacterStringBuilder.newBuilder()
                .text(TEXT)
                .build().drawOnto(surface, Position.create(1, 0))

        assertThat(surface.getCharacterAt(Position.create(0, 0)).get().getCharacter())
                .isEqualTo(' ')
        assertThat(surface.getCharacterAt(Position.create(1, 0)).get().getCharacter())
                .isEqualTo('T')
        assertThat(surface.getCharacterAt(Position.create(0, 1)).get().getCharacter())
                .isEqualTo('E')
        assertThat(surface.getCharacterAt(Position.create(1, 1)).get().getCharacter())
                .isEqualTo('X')


    }

    @Test
    fun shouldProperlyWriteStringToTextImageWhenLengthIs1() {
        val surface = TextImageBuilder.newBuilder()
                .size(Size.create(2, 2))
                .build()

        TextCharacterStringBuilder.newBuilder()
                .text("T")
                .build().drawOnto(surface, Position.create(0, 0))

        assertThat(surface.getCharacterAt(Position.create(0, 0)).get().getCharacter())
                .isEqualTo('T')
        assertThat(surface.getCharacterAt(Position.create(1, 0)).get().getCharacter())
                .isEqualTo(' ')
        assertThat(surface.getCharacterAt(Position.create(0, 1)).get().getCharacter())
                .isEqualTo(' ')
        assertThat(surface.getCharacterAt(Position.create(1, 1)).get().getCharacter())
                .isEqualTo(' ')


    }

    @Test
    fun shouldProperlyTruncateStringWhenDoesNotFitOnTextImage() {
        val surface = TextImageBuilder.newBuilder()
                .size(Size.create(2, 2))
                .build()

        TextCharacterStringBuilder.newBuilder()
                .text("TEXTTEXT")
                .build().drawOnto(surface, Position.create(0, 0))

        assertThat(surface.getCharacterAt(Position.create(0, 0)).get().getCharacter())
                .isEqualTo('T')
        assertThat(surface.getCharacterAt(Position.create(1, 0)).get().getCharacter())
                .isEqualTo('E')
        assertThat(surface.getCharacterAt(Position.create(0, 1)).get().getCharacter())
                .isEqualTo('X')
        assertThat(surface.getCharacterAt(Position.create(1, 1)).get().getCharacter())
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

        val template = TextCharacterBuilder.newBuilder().build()

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

package org.codetome.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Modifiers
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.builder.TextCharacterStringBuilder
import org.codetome.zircon.api.builder.TextImageBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.graphics.TextWrap
import org.junit.Before
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
                .size(Size.of(2, 2))
                .build()

        TextCharacterStringBuilder.newBuilder()
                .text(TEXT)
                .textWrap(TextWrap.NO_WRAPPING)
                .build().drawOnto(surface, Position.of(2, 1))
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenOffsetRowIsTooBig() {
        val surface = TextImageBuilder.newBuilder()
                .size(Size.of(2, 2))
                .build()

        TextCharacterStringBuilder.newBuilder()
                .text(TEXT)
                .textWrap(TextWrap.NO_WRAPPING)
                .build().drawOnto(surface, Position.of(1, 2))
    }

    @Test
    fun shouldProperlyWriteNoWrapStringToTextImage() {
        val surface = TextImageBuilder.newBuilder()
                .size(Size.of(2, 2))
                .build()

        TextCharacterStringBuilder.newBuilder()
                .text(TEXT)
                .textWrap(TextWrap.NO_WRAPPING)
                .build().drawOnto(surface)

        assertThat(surface.getCharacterAt(Position.of(0, 0)).get().getCharacter())
                .isEqualTo('T')
        assertThat(surface.getCharacterAt(Position.of(1, 0)).get().getCharacter())
                .isEqualTo('E')
        assertThat(surface.getCharacterAt(Position.of(0, 1)).get().getCharacter())
                .isEqualTo(' ')
        assertThat(surface.getCharacterAt(Position.of(1, 1)).get().getCharacter())
                .isEqualTo(' ')


    }

    @Test
    fun shouldProperlyWriteNoWrapStringToTextImageWithOffset() {
        val surface = TextImageBuilder.newBuilder()
                .size(Size.of(2, 2))
                .build()

        TextCharacterStringBuilder.newBuilder()
                .text(TEXT)
                .textWrap(TextWrap.NO_WRAPPING)
                .build().drawOnto(surface, Position.OFFSET_1x1)

        assertThat(surface.getCharacterAt(Position.of(0, 0)).get().getCharacter())
                .isEqualTo(' ')
        assertThat(surface.getCharacterAt(Position.of(1, 0)).get().getCharacter())
                .isEqualTo(' ')
        assertThat(surface.getCharacterAt(Position.of(0, 1)).get().getCharacter())
                .isEqualTo(' ')
        assertThat(surface.getCharacterAt(Position.of(1, 1)).get().getCharacter())
                .isEqualTo('T')


    }

    @Test
    fun shouldProperlyWriteWrapStringToTextImageWithoutOffset() {
        val surface = TextImageBuilder.newBuilder()
                .size(Size.of(2, 2))
                .build()

        TextCharacterStringBuilder.newBuilder()
                .text(TEXT)
                .build().drawOnto(surface)

        assertThat(surface.getCharacterAt(Position.of(0, 0)).get().getCharacter())
                .isEqualTo('T')
        assertThat(surface.getCharacterAt(Position.of(1, 0)).get().getCharacter())
                .isEqualTo('E')
        assertThat(surface.getCharacterAt(Position.of(0, 1)).get().getCharacter())
                .isEqualTo('X')
        assertThat(surface.getCharacterAt(Position.of(1, 1)).get().getCharacter())
                .isEqualTo('T')


    }

    @Test
    fun shouldProperlyWriteWrapStringToTextImageWithOffset() {
        val surface = TextImageBuilder.newBuilder()
                .size(Size.of(2, 2))
                .build()

        TextCharacterStringBuilder.newBuilder()
                .text(TEXT)
                .build().drawOnto(surface, Position.of(1, 0))

        assertThat(surface.getCharacterAt(Position.of(0, 0)).get().getCharacter())
                .isEqualTo(' ')
        assertThat(surface.getCharacterAt(Position.of(1, 0)).get().getCharacter())
                .isEqualTo('T')
        assertThat(surface.getCharacterAt(Position.of(0, 1)).get().getCharacter())
                .isEqualTo('E')
        assertThat(surface.getCharacterAt(Position.of(1, 1)).get().getCharacter())
                .isEqualTo('X')


    }

    companion object {
        val FOREGROUND = ANSITextColor.RED
        val BACKGROUND = ANSITextColor.GREEN
        val MODIFIER = Modifiers.CROSSED_OUT
        val TEXT = "TEXT"
    }
}
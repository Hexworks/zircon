package org.codetome.zircon.internal.font

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.junit.Test

class Java2DFontTest {

    val target = CP437TilesetResource.WANDERLUST_16X16.toFont()

    @Test
    fun shouldProperlyCacheFontWhenFetchingRegionTwice() {
        val firstResult = target.fetchRegionForChar(TextCharacterBuilder.DEFAULT_CHARACTER)
        val secondResult = target.fetchRegionForChar(TextCharacterBuilder.DEFAULT_CHARACTER)

        assertThat(firstResult).isSameAs(secondResult)
    }

    @Test
    fun shouldNotBeTheSameWhenRegionIsLoadedForDifferentTextChars() {
        val firstResult = target.fetchRegionForChar(TextCharacterBuilder.DEFAULT_CHARACTER)
        val secondResult = target.fetchRegionForChar(TextCharacterBuilder.EMPTY)

        assertThat(firstResult).isNotSameAs(secondResult)
    }

    @Test
    fun shouldBeAbleToDisplayASimpleCharacter() {
        assertThat(target.hasDataForChar('a')).isTrue()
    }

    @Test
    fun shouldNotBeAbleToDisplayControlCharacter() {
        assertThat(target.hasDataForChar(1.toChar())).isFalse()
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotBeAbleToFetchRegionWithTags() {
        target.fetchRegionForChar(TextCharacterBuilder.DEFAULT_CHARACTER.withTags("foo"))
    }

   @Test(expected = IllegalArgumentException::class)
    fun shouldNotBeAbleToFetchRegionWithChar() {
        target.fetchRegionForChar(TextCharacterBuilder.DEFAULT_CHARACTER.withCharacter(1.toChar()))
    }

    @Test
    fun shouldProperlyReportSize() {
        val expectedSize = 16
        assertThat(target.getWidth()).isEqualTo(expectedSize)
        assertThat(target.getHeight()).isEqualTo(expectedSize)
    }
}
package org.codetome.zircon.font

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.api.PhysicalFontResource
import org.codetome.zircon.api.TextCharacterBuilder
import org.junit.Test

class PhysicalFontTest {

    val target = PhysicalFontResource.ANONYMOUS_PRO.asPhysicalFont()

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

    @Test
    fun shouldNotHaveMetadataForAChar() {
        assertThat(target.fetchMetadataForChar('a')).isEmpty()
    }
}
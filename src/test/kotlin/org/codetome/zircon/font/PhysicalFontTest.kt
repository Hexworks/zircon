package org.codetome.zircon.font

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.api.PhysicalFontResource
import org.junit.Test

class PhysicalFontTest {

    val target = PhysicalFontResource.ANONYMOUS_PRO.asPhysicalFont()

    @Test
    fun shouldProperlyCacheFontWhenFetchingRegionTwice() {
        val firstResult = target.fetchRegionForChar(TextCharacter.DEFAULT_CHARACTER)
        val secondResult = target.fetchRegionForChar(TextCharacter.DEFAULT_CHARACTER)

        assertThat(firstResult).isSameAs(secondResult)
    }

    @Test
    fun shouldNotBeTheSameWhenRegionIsLoadedForDifferentTextChars() {
        val firstResult = target.fetchRegionForChar(TextCharacter.DEFAULT_CHARACTER)
        val secondResult = target.fetchRegionForChar(TextCharacter.EMPTY)

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
package org.codetome.zircon.internal.behavior.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.internal.tileset.impl.TilesetLoaderRegistry
import org.codetome.zircon.internal.tileset.impl.FontSettings
import org.codetome.zircon.internal.tileset.impl.TestTilesetLoader
import org.junit.Before
import org.junit.Test

class DefaultTilesetOverrideTest {

    lateinit var target: DefaultTilesetOverride

    @Before
    fun setUp() {
        TilesetLoaderRegistry.setFontLoader(TestTilesetLoader())
        target = DefaultTilesetOverride(INITIAL_FONT.toFont())
    }

    @Test
    fun shouldReturnInitialFontInitially() {
        val font = INITIAL_FONT.toFont()
        target = DefaultTilesetOverride(font)
        assertThat(target.getCurrentFont().getId())
                .isEqualTo(font.getId())
    }

    @Test
    fun shouldBeAbleToUseSameSizeFont() {
        val expected = CP437TilesetResource.ROGUE_YUN_16X16.toFont()

        target.useFont(expected)

        assertThat(target.getCurrentFont().getId())
                .isEqualTo(expected.getId())
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenTryingToSetNonCompatibleFont() {
        target.useFont(CP437TilesetResource.BISASAM_20X20.toFont())
    }

    @Test
    fun shouldHaveFontOverrideWhenHasOne() {
        assertThat(target.hasOverrideFont()).isTrue()
    }

    @Test
    fun shouldNotHaveFontOverrideAfterReset() {
        target.resetFont()
        assertThat(target.hasOverrideFont()).isFalse()
        assertThat(target.getCurrentFont()).isSameAs(FontSettings.NO_FONT)
    }

    companion object {
        val INITIAL_FONT = CP437TilesetResource.WANDERLUST_16X16
    }
}

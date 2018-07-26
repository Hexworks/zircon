package org.codetome.zircon.internal.font.impl

import org.codetome.zircon.api.data.Tile
import org.junit.Test

class FontSettingsTest {

    @Test(expected = NotImplementedError::class)
    fun shouldNotHaveHeightForNoFont() {
        FontSettings.NO_FONT.getHeight()
    }

    @Test(expected = NotImplementedError::class)
    fun shouldNotHaveWidthForNoFont() {
        FontSettings.NO_FONT.getWidth()
    }

    @Test(expected = NotImplementedError::class)
    fun shouldNotHaveSizeForNoFont() {
        FontSettings.NO_FONT.getSize()
    }

    @Test(expected = NotImplementedError::class)
    fun shouldNotHaveDataForCharForNoFont() {
        FontSettings.NO_FONT.hasDataForChar('a')
    }

    @Test(expected = NotImplementedError::class)
    fun shouldNotHaveNoMetadataForNoFont() {
        FontSettings.NO_FONT.fetchMetadataForChar('a')
    }

    @Test(expected = NotImplementedError::class)
    fun shouldNotHaveRegionForNoFont() {
        FontSettings.NO_FONT.fetchRegionForChar(Tile.defaultTile())
    }
}

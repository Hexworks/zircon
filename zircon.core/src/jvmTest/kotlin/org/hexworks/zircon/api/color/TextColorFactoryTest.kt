package org.hexworks.zircon.api.color

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TextColorFactoryTest {

    @Test
    fun shouldGiveBackWhiteAsDefaultForegroundColor() {
        assertThat(TileColor.defaultForegroundColor()).isEqualTo(ANSITileColor.WHITE)
        assertThat(TileColor.defaultBackgroundColor()).isEqualTo(ANSITileColor.BLACK)
    }
}

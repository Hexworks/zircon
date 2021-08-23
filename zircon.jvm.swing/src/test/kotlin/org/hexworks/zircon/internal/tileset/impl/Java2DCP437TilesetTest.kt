package org.hexworks.zircon.internal.tileset.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.SwingApplications
import org.junit.Ignore
import org.junit.Test

@Ignore
class Java2DCP437TilesetTest {

    val target = SwingApplications.defaultTilesetLoader().loadTilesetFrom(CP437TilesetResources.wanderlust16x16())

    @Test
    fun shouldProperlyReportSize() {
        val expectedSize = 16
        assertThat(target.width).isEqualTo(expectedSize)
        assertThat(target.height).isEqualTo(expectedSize)
    }
}

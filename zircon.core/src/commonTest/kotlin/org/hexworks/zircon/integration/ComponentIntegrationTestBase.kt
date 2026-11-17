package org.hexworks.zircon.integration

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.grid.tileGrid
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.screen.Screen
import kotlin.test.AfterTest
import kotlin.test.Test

@Suppress("MemberVisibilityCanBePrivate")
abstract class ComponentIntegrationTestBase(
    val size: Size = DEFAULT_SIZE_60X30,
    val tileset: TilesetResource = DEFAULT_TILESET_WANDERLUST,
    val tileGrid: TileGrid = tileGrid {
        this.tileset = tileset
        this.size = size
    },
    val screen: Screen = tileGrid.toScreen()
) {

    @AfterTest
    fun tearDown() {
        tileGrid.close()
    }

    @Test
    fun shouldProperlyWorkWithComponent() {
        screen.tileset = tileset
        buildScreenContent(screen)
        screen.display()
    }


    abstract fun buildScreenContent(screen: Screen)

    companion object {
        val DEFAULT_TILESET_WANDERLUST = CP437TilesetResources.wanderlust16x16()
        val DEFAULT_SIZE_60X30 = Size(60, 30)
    }
}

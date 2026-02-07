package org.hexworks.zircon.renderer.virtual

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.builder.application.tilesetFactory
import org.hexworks.zircon.api.data.TileType
import org.hexworks.zircon.api.resource.TilesetType
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.renderer.impl.BaseRenderer
import org.hexworks.zircon.internal.tileset.DefaultTilesetLoader
import org.hexworks.zircon.renderer.virtual.VirtualRenderer.VirtualView
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Suppress("unused")
class VirtualRenderer(
    tileGrid: InternalTileGrid
) : BaseRenderer<Char, VirtualApplication, VirtualView>(
    tileGrid = tileGrid,
    tilesetLoader = DefaultTilesetLoader(
        listOf(
            tilesetFactory {
                targetType = Char::class
                tileType = TileType.CHARACTER_TILE
                tilesetType = TilesetType.CP437Tileset
                factoryFunction {
                    VirtualTileset()
                }
            })
    )
) {

    val config = RuntimeConfig.config

    private val tileset = VirtualTileset()
    private val contents = "".toProperty()

    @OptIn(ExperimentalTime::class)
    private var lastRender: Long = Clock.System.now().toEpochMilliseconds()

    override fun create(): VirtualView {
        return VirtualView(contents)
    }

    override fun processInputEvents() {
    }

    override fun prepareRender(context: Char) {
        contents.value = tileGrid.backend.toString()
    }

    class VirtualView(
        contentsProperty: Property<String>
    ) {
        val contents: String by contentsProperty.asDelegate()
    }
}

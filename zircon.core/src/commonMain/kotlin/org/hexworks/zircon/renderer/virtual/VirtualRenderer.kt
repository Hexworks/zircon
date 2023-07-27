package org.hexworks.zircon.renderer.virtual

import korlibs.time.DateTime
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.builder.application.tilesetFactory
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.renderer.impl.BaseRenderer
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.internal.resource.TilesetType
import org.hexworks.zircon.internal.tileset.impl.DefaultTilesetLoader
import org.hexworks.zircon.renderer.virtual.VirtualRenderer.VirtualView

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
    private var lastRender: Long = DateTime.nowUnixMillisLong()

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

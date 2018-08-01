package org.codetome.zircon.api

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.codetome.zircon.api.builder.grid.VirtualTerminalBuilder
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.internal.tileset.impl.TilesetLoaderRegistry
import org.codetome.zircon.internal.tileset.impl.FontSettings.NO_FONT
import org.codetome.zircon.internal.tileset.impl.LibgdxTilesetLoader
import org.codetome.zircon.internal.grid.LibgdxAdapter

class LibgdxTerminalBuilder : VirtualTerminalBuilder() {

    init {
        TilesetLoaderRegistry.setFontLoader(LibgdxTilesetLoader())
    }

    override fun build(): LibgdxAdapter {
        if (tileset === NO_FONT) {
            tileset = CP437TilesetResource.WANDERLUST_16X16.toFont()
        }
        val adapter = LibgdxAdapter(
                initialTileset = tileset,
                initialSize = initialSize,
                deviceConfiguration = deviceConfiguration)
        val config = LwjglApplicationConfiguration()
        val (cols, rows) = initialSize
        val (width, height) = tileset.getSize()
        config.height = height * rows
        config.width = width * cols
        LwjglApplication(adapter, config)
        return adapter
    }

    companion object {

        /**
         * Creates a new [LibgdxTerminalBuilder].
         */
        @JvmStatic
        fun newBuilder() = LibgdxTerminalBuilder()
    }
}

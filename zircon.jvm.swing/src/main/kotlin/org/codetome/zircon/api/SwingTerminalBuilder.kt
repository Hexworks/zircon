package org.codetome.zircon.api

import org.codetome.zircon.api.builder.grid.VirtualTerminalBuilder
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.internal.tileset.impl.TilesetLoaderRegistry
import org.codetome.zircon.internal.tileset.impl.FontSettings.NO_FONT
import org.codetome.zircon.internal.tileset.impl.Java2DTilesetLoader
import org.codetome.zircon.internal.grid.SwingTileGridFrame
import java.awt.Toolkit

class SwingTerminalBuilder : VirtualTerminalBuilder() {

    init {
        TilesetLoaderRegistry.setFontLoader(Java2DTilesetLoader())
    }

    override fun build(): TileGrid {
        if(tileset === NO_FONT) {
            tileset = CP437TilesetResource.WANDERLUST_16X16.toFont()
        }
        checkScreenSize()
        return SwingTileGridFrame(
                title = title,
                size = initialSize,
                deviceConfiguration = deviceConfiguration,
                fullScreen = fullScreen,
                tileset = tileset).apply {
            isVisible = true
        }
    }

    private fun checkScreenSize() {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        require(screenSize.width >= tileset.getWidth() * initialSize.xLength) {
            "The requested xLength count '${initialSize.xLength}' for tileset xLength '${tileset.getWidth()}'" +
                    " won't fit on the screen (xLength: ${screenSize.width}"
        }
        require(screenSize.height >= tileset.getHeight() * initialSize.yLength) {
            "The requested yLength count '${initialSize.yLength}' for tileset yLength '${tileset.getHeight()}'" +
                    " won't fit on the screen (yLength: ${screenSize.height}"
        }
    }

    companion object {

        /**
         * Creates a new [SwingTerminalBuilder].
         */
        @JvmStatic
        fun newBuilder() = SwingTerminalBuilder()
    }
}

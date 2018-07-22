package org.codetome.zircon.api

import org.codetome.zircon.api.resource.PhysicalFontResource
import org.codetome.zircon.internal.terminal.builder.VirtualTerminalBuilder
import org.codetome.zircon.api.terminal.Terminal
import org.codetome.zircon.internal.font.impl.FontLoaderRegistry
import org.codetome.zircon.internal.font.impl.FontSettings.NO_FONT
import org.codetome.zircon.internal.font.impl.Java2DFontLoader
import org.codetome.zircon.internal.terminal.SwingTerminalFrame
import java.awt.Toolkit

class SwingTerminalBuilder : VirtualTerminalBuilder() {

    init {
        FontLoaderRegistry.setFontLoader(Java2DFontLoader())
    }

    override fun build(): Terminal {
        if(font === NO_FONT) {
            font = PhysicalFontResource.UBUNTU_MONO.toFont()
        }
        checkScreenSize()
        return SwingTerminalFrame(
                title = title,
                size = initialSize,
                deviceConfiguration = deviceConfiguration,
                fullScreen = fullScreen,
                font = font).apply {
            isVisible = true
        }
    }

    private fun checkScreenSize() {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        require(screenSize.width >= font.getWidth() * initialSize.xLength) {
            "The requested xLength count '${initialSize.xLength}' for font xLength '${font.getWidth()}'" +
                    " won't fit on the screen (xLength: ${screenSize.width}"
        }
        require(screenSize.height >= font.getHeight() * initialSize.yLength) {
            "The requested yLength count '${initialSize.yLength}' for font yLength '${font.getHeight()}'" +
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

package org.codetome.zircon.api

import org.codetome.zircon.api.builder.VirtualTerminalBuilder
import org.codetome.zircon.api.terminal.Terminal
import org.codetome.zircon.internal.font.impl.FontSettings.NO_FONT
import java.awt.Toolkit

class LibgdxTerminalBuilder : VirtualTerminalBuilder() {

    init {
//        FontLoaderRegistry.setFontLoader(Java2DFontLoader())
    }

    override fun build(): Terminal {
        if(font === NO_FONT) {
            font = DEFAULT_FONT.toFont()
        }
        checkScreenSize()
        TODO()
//        return SwingTerminalFrame(
//                title = title,
//                size = initialSize,
//                deviceConfiguration = deviceConfiguration,
//                fullScreen = fullScreen,
//                font = font).apply {
//            isVisible = true
//        }
    }

    private fun checkScreenSize() {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        require(screenSize.width >= font.getWidth() * initialSize.columns) {
            "The requested column count '${initialSize.columns}' for font width '${font.getWidth()}'" +
                    " won't fit on the screen (width: ${screenSize.width}"
        }
        require(screenSize.height >= font.getHeight() * initialSize.rows) {
            "The requested row count '${initialSize.rows}' for font height '${font.getHeight()}'" +
                    " won't fit on the screen (height: ${screenSize.height}"
        }
    }

    companion object {

        /**
         * Creates a new [LibgdxTerminalBuilder].
         */
        @JvmStatic
        fun newBuilder() = LibgdxTerminalBuilder()
    }
}

package org.codetome.zircon.api

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.codetome.zircon.api.builder.VirtualTerminalBuilder
import org.codetome.zircon.internal.font.FontLoaderRegistry
import org.codetome.zircon.internal.font.impl.FontSettings.NO_FONT
import org.codetome.zircon.internal.font.impl.LibgdxFontLoader
import org.codetome.zircon.internal.terminal.LibgdxAdapter
import org.codetome.zircon.internal.terminal.LibgdxTerminal
import java.awt.Toolkit

class LibgdxTerminalBuilder : VirtualTerminalBuilder() {

    init {
        FontLoaderRegistry.setFontLoader(LibgdxFontLoader())
    }

    override fun build(): LibgdxAdapter {
        if(font === NO_FONT) {
            font = DEFAULT_FONT.toFont()
        }
        checkScreenSize()
//        return SwingTerminalFrame(
//                title = title,
//                size = initialSize,
//                deviceConfiguration = deviceConfiguration,
//                fullScreen = fullScreen,
//                font = font).apply {
//            isVisible = true
//        }
        val adapter = LibgdxAdapter(
                initialFont = font,
                initialSize = initialSize,
                deviceConfiguration = deviceConfiguration)
        val config = LwjglApplicationConfiguration()
        LwjglApplication(adapter, config)
        return adapter
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

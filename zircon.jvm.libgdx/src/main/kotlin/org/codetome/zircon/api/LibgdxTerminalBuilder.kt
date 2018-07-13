package org.codetome.zircon.api

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.codetome.zircon.api.resource.PhysicalFontResource
import org.codetome.zircon.internal.terminal.builder.VirtualTerminalBuilder
import org.codetome.zircon.internal.font.FontLoaderRegistry
import org.codetome.zircon.internal.font.impl.FontSettings.NO_FONT
import org.codetome.zircon.internal.font.impl.LibgdxFontLoader
import org.codetome.zircon.internal.terminal.LibgdxAdapter

class LibgdxTerminalBuilder : VirtualTerminalBuilder() {

    init {
        FontLoaderRegistry.setFontLoader(LibgdxFontLoader())
    }

    override fun build(): LibgdxAdapter {
        if (font === NO_FONT) {
            font = PhysicalFontResource.UBUNTU_MONO.toFont()
        }
        val adapter = LibgdxAdapter(
                initialFont = font,
                initialSize = initialSize,
                deviceConfiguration = deviceConfiguration)
        val config = LwjglApplicationConfiguration()
        val (cols, rows) = initialSize
        val (width, height) = font.getSize()
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

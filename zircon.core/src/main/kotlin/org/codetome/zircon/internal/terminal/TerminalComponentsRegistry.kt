@file:Suppress("unused")

package org.codetome.zircon.internal.terminal

import org.codetome.zircon.api.GUIBackend
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.DeviceConfigurationBuilder
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.terminal.Terminal
import org.codetome.zircon.api.terminal.config.DeviceConfiguration
import java.awt.Toolkit

object TerminalComponentsRegistry {

    private var selectedBackend = GUIBackend.HEADLESS
    private val componentLoaders = mutableMapOf<GUIBackend, TerminalComponentsLoader>()

    init {
        TerminalComponentsLoaderInitializer.initializeAll()
    }

    fun fetchScreenSize(): ScreenSize {
        Toolkit.getDefaultToolkit().screenSize
        return componentLoaders[selectedBackend]!!.fetchScreenSize()
    }

    fun buildTerminal(title: String = "ZirconTerminal",
                      size: Size,
                      deviceConfiguration: DeviceConfiguration = DeviceConfigurationBuilder.DEFAULT,
                      font: Font,
                      fullScreen: Boolean): InternalTerminal {
        return componentLoaders[selectedBackend]!!.buildTerminal(
                title = title,
                size = size,
                deviceConfiguration = deviceConfiguration,
                font = font,
                fullScreen = fullScreen)
    }


    fun selectBackend(backend: GUIBackend) {
        require(selectedBackend == GUIBackend.HEADLESS) {
            "A GUI backend is already selected (${selectedBackend.name})! Did you include more than one GUI implementations in your project?"
        }
        selectedBackend = backend
    }

    fun registerComponentLoader(guiBackend: GUIBackend, componentsLoader: TerminalComponentsLoader) {
        componentLoaders[guiBackend] = componentsLoader
    }
}

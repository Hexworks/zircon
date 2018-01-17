package org.codetome.zircon.internal.terminal

import org.reflections.scanners.SubTypesScanner
import org.reflections.Reflections



interface TerminalComponentsLoaderInitializer {

    fun initialize()

    companion object {

        fun initializeAll() {
            val reflections = Reflections("org.codetome.zircon.internal.terminal", SubTypesScanner(false))
            val loaders = reflections.getSubTypesOf(TerminalComponentsLoaderInitializer::class.java)

            loaders.forEach {
                it.newInstance().initialize()
            }
        }
    }
}

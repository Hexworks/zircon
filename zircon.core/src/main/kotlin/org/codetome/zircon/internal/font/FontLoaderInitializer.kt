package org.codetome.zircon.internal.font

import org.reflections.scanners.SubTypesScanner
import org.reflections.Reflections



interface FontLoaderInitializer {

    fun initialize()

    companion object {

        fun initializeAll() {
            val reflections = Reflections("org.codetome.zircon.internal.font", SubTypesScanner(false))
            val loaders = reflections.getSubTypesOf(FontLoaderInitializer::class.java)

            loaders.forEach {
                it.newInstance().initialize()
            }
        }
    }
}
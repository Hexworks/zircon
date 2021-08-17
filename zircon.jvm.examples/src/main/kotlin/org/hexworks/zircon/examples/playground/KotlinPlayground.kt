@file:Suppress("DuplicatedCode")

package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.dsl.component.buildLabel
import org.hexworks.zircon.api.extensions.toScreen

object KotlinPlayground {

    @JvmStatic
    fun main(args: Array<String>) {

        val screen = SwingApplications.startTileGrid().toScreen()
        screen.display()
        screen.addComponent(buildLabel { +"foo" })

    }

}


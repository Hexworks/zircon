package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentAlignments
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.TrueTypeFontResources
import org.hexworks.zircon.api.component.ComponentAlignment.CENTER
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.buildLabel
import org.hexworks.zircon.api.dsl.component.buildVbox
import org.hexworks.zircon.api.dsl.component.header
import org.hexworks.zircon.api.dsl.component.plus
import org.hexworks.zircon.api.extensions.toScreen

class UpdateOnAttachExampleKotlin {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val screen = SwingApplications.startTileGrid().toScreen()

            val box = buildVbox {
                spacing = 1
                alignment = ComponentAlignments.alignmentWithin(screen, CENTER)
                preferredSize = Size.create(20, 10)

                header { +"Same style" }
            }

            screen.addComponent(box)

            box + buildLabel {
                +"Different style"
                colorTheme = ColorThemes.cherryBear()
                tileset = TrueTypeFontResources.ibmBios(16)
                updateOnAttach = false
            }

            screen.display()
        }
    }
}

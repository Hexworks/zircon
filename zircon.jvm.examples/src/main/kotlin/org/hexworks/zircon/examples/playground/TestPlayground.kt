@file:Suppress("UNUSED_VARIABLE")

package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.extensions.alignmentWithin

object TestPlayground {


    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        screen.addComponent(Components.hbox()
                .withDecorations(box())
                .withAlignment(alignmentWithin(tileGrid, ComponentAlignment.CENTER))
                .withSpacing(1).withSize(40, 7).build().apply {
                    addComponent(Components.label().withText("foo").withDecorations(box()))
                    addComponent(Components.label().withText("bar").withDecorations(box(), shadow()))
                })

        screen.display()
        screen.applyColorTheme(ColorThemes.adriftInDreams())
    }

}

package org.hexworks.zircon.internal.integration

import org.hexworks.zircon.api.Components

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.FadeIn
import org.hexworks.zircon.api.screen.Screen

object IssueLabelFadeInExample {

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid()

        val screen = Screen.create(tileGrid)
        screen.display()


        val styleSet = StyleSet.newBuilder()
                .withForegroundColor(ANSITileColor.YELLOW)
                .withModifiers(FadeIn(10, 1000, true))
                .build()

        val label = Components.label()
                .withText("Fade In Label")
                .withPosition(Position.create(1, 5))
                .withComponentStyleSet(ComponentStyleSetBuilder
                        .newBuilder()
                        .withDefaultStyle(styleSet)
                        .build())

        screen.addComponent(label)
    }

}

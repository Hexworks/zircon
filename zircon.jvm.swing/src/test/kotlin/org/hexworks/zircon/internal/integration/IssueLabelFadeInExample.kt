package org.hexworks.zircon.internal.integration

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.application.CursorStyle
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.modifier.FadeIn

object IssueLabelFadeInExample {

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid()

        val screen = Screens.createScreenFor(tileGrid)
        screen.display()


        val styleSet = StyleSets.newBuilder()
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

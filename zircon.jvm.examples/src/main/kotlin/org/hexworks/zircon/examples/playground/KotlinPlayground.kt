@file:Suppress("DuplicatedCode")

package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentAlignments
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.button
import org.hexworks.zircon.api.dsl.component.textArea
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.extensions.useComponentBuilder

object KotlinPlayground {

    @JvmStatic
    fun main(args: Array<String>) {
        SwingApplications.startTileGrid(
            AppConfigBuilder.newBuilder()
                .withCursorBlinking(true)
                .withSize(8, 5)
                .build()
        ).toScreen().apply {
            display()
            theme = ColorThemes.adriftInDreams()
        }.useComponentBuilder {
            textArea {
                preferredSize = Size.create(8, 5)
                decoration = box()
            }
        }
    }
}


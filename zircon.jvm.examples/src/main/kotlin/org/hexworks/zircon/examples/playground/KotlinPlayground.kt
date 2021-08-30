@file:Suppress("DuplicatedCode")

package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.builder.component.ParagraphBuilder
import org.hexworks.zircon.api.extensions.toScreen

object KotlinPlayground {

    @JvmStatic
    fun main(args: Array<String>) {

        SwingApplications.startTileGrid().toScreen().apply {
            display()
            theme = ColorThemes.arc()
            addComponent(
                ParagraphBuilder.newBuilder()
                    .withText("Some text I added")
                    .withTypingEffect(200)
            )
        }

    }

}


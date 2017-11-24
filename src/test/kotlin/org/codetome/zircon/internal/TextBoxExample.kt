package org.codetome.zircon.internal

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.BoxBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.builder.TerminalBuilder
import org.codetome.zircon.api.builder.TextImageBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.api.component.builder.TextBoxBuilder
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.screen.Screen
import org.codetome.zircon.internal.graphics.BoxType
import java.util.*


object TextBoxExample {

    @JvmStatic
    fun main(args: Array<String>) {
        val terminal = TerminalBuilder.newBuilder()
                .font(CP437TilesetResource.REX_PAINT_20X20.toFont())
                .initialTerminalSize(Size.of(10, 5))
                .build()


        val screen = TerminalBuilder.createScreenFor(terminal)

        val textBox = TextBoxBuilder.newBuilder().text("Test").build()
        screen.addComponent(textBox)

        screen.display()

        textBox.setText("update")
        screen.display()
    }

}
package org.codetome.zircon.internal

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.BoxBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.builder.TerminalBuilder
import org.codetome.zircon.api.builder.TextImageBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.internal.graphics.BoxType
import java.util.*


object Issue45 {

    @JvmStatic
    fun main(args: Array<String>) {
        val terminal = TerminalBuilder.newBuilder()
                .font(CP437TilesetResource.REX_PAINT_20X20.toFont())
                .initialTerminalSize(Size.of(10, 5))
                .build()

        val img = TextImageBuilder.newBuilder()
                .size(Size.of(10, 5))
                .build() // we create a new image to draw onto the terminal

        img.setForegroundColor(ANSITextColor.WHITE)
        img.setBackgroundColor(ANSITextColor.GREEN) // `putText` will use these

        BoxBuilder.newBuilder()
                .boxType(BoxType.DOUBLE)
                .size(Size.of(10, 5))
                .style(StyleSetBuilder.newBuilder()
                        .foregroundColor(ANSITextColor.CYAN)
                        .backgroundColor(ANSITextColor.BLUE)
                        .build())
                .build()
                .drawOnto(img, Position.DEFAULT_POSITION) // we create a box and draw it onto the image

        val logElements = ArrayList<String>()
        logElements.add("foo")
        logElements.add("bar") // our log entries

     /*   for (i in logElements.indices) {
            img.putText(logElements[i], Position.OFFSET_1x1.withRelativeRow(i)) // we have to offset because of the box
        }*/
        terminal.draw(img, Position.DEFAULT_POSITION) // you have to draw each time the image changes

        print(img.toString())
        print(img.getCharacterAt(Position.of(1,1)))

    }

}
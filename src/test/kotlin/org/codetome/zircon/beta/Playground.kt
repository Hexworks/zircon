package org.codetome.zircon.beta

import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.TerminalBuilder
import org.codetome.zircon.api.builder.TextImageBuilder
import org.codetome.zircon.api.resource.CP437TilesetResource

class Playground {

    object Playground {

        @JvmStatic
        fun main(args: Array<String>) {
            val screen = TerminalBuilder.newBuilder()
                    .font(CP437TilesetResource.REX_PAINT_18X18.toFont())
                    .initialTerminalSize(Size.of(10, 10))
                    .buildScreen()

            val myString = "foobar"
            val textImage = TextImageBuilder.newBuilder().size(Size.of(myString.length, 0)).build()
            textImage.putText(myString)
            screen.display()
        }
    }
}
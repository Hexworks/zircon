package org.hexworks.zircon.examples

import org.hexworks.zircon.api.component.ComponentRenderer
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TileGraphics

object KotlinPlayground {


    @JvmStatic
    fun main(args: Array<String>) {



    }

    class ButtonRenderer(private val text: String) : ComponentRenderer {

        override fun render(tileGraphics: TileGraphics, size: Size) {
            tileGraphics.putText(text)
        }
    }


}

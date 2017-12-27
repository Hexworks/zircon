package org.codetome.zircon.beta

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.builder.TerminalBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.component.builder.HeaderBuilder
import org.codetome.zircon.api.component.builder.PanelBuilder
import org.codetome.zircon.api.component.builder.RadioButtonGroupBuilder
import org.codetome.zircon.api.resource.CP437TilesetResource

object ComponentTest {

    @JvmStatic
    fun main(args: Array<String>) {

        val screen = TerminalBuilder.newBuilder()
                .initialTerminalSize(Size.of(40, 25))
                .font(CP437TilesetResource.REX_PAINT_16X16.toFont())
                .buildScreen()

        val panel = PanelBuilder.newBuilder()
                .wrapWithBox()
                .title("Panel")
                .size(Size.of(32, 16))
                .position(Position.OFFSET_1x1)
                .build()

        val panel2 = PanelBuilder.newBuilder()
                .wrapWithBox()
                .title("Panel2")
                .size(Size.of(16, 10))
                .position(Position.OFFSET_1x1)
                .build()
        panel2.addComponent(HeaderBuilder.newBuilder()
                .text("Header2")
                .build())

        panel.addComponent(HeaderBuilder.newBuilder()
                .text("Header")
                .build())
        panel.addComponent(panel2)

        screen.addComponent(panel)

        screen.display()
    }
}
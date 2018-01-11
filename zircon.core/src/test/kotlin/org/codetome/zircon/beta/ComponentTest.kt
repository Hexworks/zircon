package org.codetome.zircon.beta

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.TerminalBuilder
import org.codetome.zircon.api.component.builder.HeaderBuilder
import org.codetome.zircon.api.component.builder.PanelBuilder
import org.codetome.zircon.api.resource.CP437TilesetResource

object ComponentTest {

    @JvmStatic
    fun main(args: Array<String>) {

        val terminal = TerminalBuilder.newBuilder()
                .initialTerminalSize(Size.of(40, 25))
                .font(CP437TilesetResource.REX_PAINT_16X16.toFont())
                .buildTerminal(false)
        val screen = TerminalBuilder.createScreenFor(terminal)

        val panel0 = PanelBuilder.newBuilder()
                .wrapWithBox()
                .title("Panel")
                .size(Size.of(32, 16))
                .position(Position.OFFSET_1x1)
                .build()

        val panel1 = PanelBuilder.newBuilder()
                .wrapWithBox()
                .title("Panel2")
                .size(Size.of(16, 10))
                .position(Position.OFFSET_1x1)
                .build()

        val header0 = HeaderBuilder.newBuilder()
                .position(Position.of(1, 0))
                .text("Header")
                .build()

        val header1 = HeaderBuilder.newBuilder()
                .position(Position.of(1, 0))
                .text("Header2")
                .build()


        panel1.addComponent(header1)
        panel0.addComponent(panel1)
        panel0.addComponent(header0)

        screen.addComponent(panel0)

        screen.display()
    }
}
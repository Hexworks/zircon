package org.codetome.zircon.beta

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.builder.TerminalBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.component.builder.RadioButtonGroupBuilder
import org.codetome.zircon.api.component.RadioButtonGroup
import org.codetome.zircon.api.component.builder.HeaderBuilder
import org.codetome.zircon.api.component.builder.PanelBuilder
import org.codetome.zircon.api.resource.CP437TilesetResource


object RadioTest {

    @JvmStatic
    fun main(args: Array<String>) {

        val screen = TerminalBuilder.newBuilder()
                .initialTerminalSize(Size.of(40, 25))
                .font(CP437TilesetResource.REX_PAINT_16X16.toFont())
                .buildScreen()

        val panel = PanelBuilder.newBuilder()
                .wrapWithBox()
                .title("Panel")
                .wrapWithShadow()
                .size(Size.of(32, 16))
                .position(Position.OFFSET_1x1)
                .build()

        val header = HeaderBuilder.newBuilder()
                .text("Header")
                .build()

        val radios = RadioButtonGroupBuilder.newBuilder()
                .size(Size.of(20, 3))
                .position(Position.OFFSET_1x1)
                .componentStyles(ComponentStylesBuilder.newBuilder()
                        .defaultStyle(StyleSetBuilder.newBuilder()
                                .backgroundColor(ANSITextColor.CYAN)
                                .build())
                        .build())
                .build()

        radios.addOption("bar", "Bar")
        radios.addOption("baz", "Baz")
        panel.addComponent(header)
        panel.addComponent(radios)
        screen.addComponent(panel)
        screen.display()
    }
}
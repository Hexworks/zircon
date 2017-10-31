package org.codetome.zircon.beta

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.TerminalBuilder
import org.codetome.zircon.api.component.builder.ButtonBuilder
import org.codetome.zircon.api.component.builder.LabelBuilder
import org.codetome.zircon.api.component.builder.PanelBuilder
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.resource.ColorThemeResource
import org.codetome.zircon.internal.graphics.BoxType

class Playground {

    object Playground {

        @JvmStatic
        fun main(args: Array<String>) {
            val screen = TerminalBuilder.newBuilder()
                    .font(CP437TilesetResource.ROGUE_YUN_16X16.toFont())
                    .initialTerminalSize(Size.of(60, 30))
                    .buildScreen()


            val panel0 = PanelBuilder.newBuilder()
                    .size(screen.getBoundableSize())
                    .wrapWithShadow()
                    .wrapWithBox()
                    .boxType(BoxType.DOUBLE)
                    .title("Panel 0")
                    .build()
            screen.addComponent(panel0)

            val panel1 = PanelBuilder.newBuilder()
                    .size(panel0.getEffectiveSize())
                    .wrapWithShadow()
                    .wrapWithBox()
                    .boxType(BoxType.DOUBLE)
                    .title("Panel 1")
                    .build()
            panel0.addComponent(panel1)

            val panel2 = PanelBuilder.newBuilder()
                    .size(panel1.getEffectiveSize())
                    .wrapWithShadow()
                    .wrapWithBox()
                    .boxType(BoxType.DOUBLE)
                    .title("Panel 2")
                    .build()
            panel1.addComponent(panel2)

            val button = ButtonBuilder.newBuilder()
                    .position(Position.OFFSET_1x1)
                    .text("Button in Panel 2 at (1, 1)")
                    .build()
            val label = LabelBuilder.newBuilder()
                    .text("Label under Button at (2, 2)")
                    .position(Position.of(1, 0).relativeToBottomOf(button))
                    .build()

            panel2.addComponent(button)
            panel2.addComponent(label)

            screen.applyColorTheme(ColorThemeResource.AFTER_THE_HEIST.getTheme())
            panel0.applyColorTheme(ColorThemeResource.ADRIFT_IN_DREAMS.getTheme())
            panel1.applyColorTheme(ColorThemeResource.CAPTURED_BY_PIRATES.getTheme())
            panel2.applyColorTheme(ColorThemeResource.GHOST_OF_A_CHANCE.getTheme())

            screen.display()
        }
    }
}
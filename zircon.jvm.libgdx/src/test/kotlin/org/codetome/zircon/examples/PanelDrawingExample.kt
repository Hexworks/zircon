package org.codetome.zircon.examples

import org.codetome.zircon.api.LibgdxTerminalBuilder
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.BoxBuilder
import org.codetome.zircon.api.builder.ScreenBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.shape.FilledRectangleFactory
import org.codetome.zircon.internal.graphics.BoxType

object PanelDrawingExample {

    private val TERMINAL_WIDTH = 19
    private val TERMINAL_HEIGHT = 12
    private val SIZE = Size.create(TERMINAL_WIDTH, TERMINAL_HEIGHT)
    private val BACKGROUND_COLOR = TextColorFactory.fromString("#223344")
    private val PANEL_BG_COLOR = TextColorFactory.fromString("#666666")
    private val PANEL_FG_COLOR = TextColorFactory.fromString("#ffffff")

    @JvmStatic
    fun main(args: Array<String>) {
        val terminal = LibgdxTerminalBuilder.newBuilder()
                .font(CP437TilesetResource.WANDERLUST_16X16.toFont())
                .initialTerminalSize(SIZE)
                .build()
        val screen = ScreenBuilder.createScreenFor(terminal)
        screen.setCursorVisibility(false)

        FilledRectangleFactory
                .buildFilledRectangle(Position.defaultPosition(), screen.getBoundableSize())
                .toTextImage(TextCharacterBuilder.defaultCharacter()
                        .withBackgroundColor(BACKGROUND_COLOR))
                .drawOnto(screen, Position.defaultPosition())

        val box = BoxBuilder.newBuilder()
                .boxType(BoxType.DOUBLE)
                .size(Size.create(15, 8))
                .style(StyleSetBuilder.newBuilder()
                        .backgroundColor(PANEL_BG_COLOR)
                        .foregroundColor(PANEL_FG_COLOR)
                        .build())
                .build()
        box.putText("Title", Position.defaultPosition()
                .withRelativeX(5))
        box.setCharacterAt(Position.defaultPosition().withRelativeX(4),
                BoxType.TOP_BOTTOM_DOUBLE.connectorLeft)
        box.setCharacterAt(Position.defaultPosition().withRelativeX(10),
                BoxType.TOP_BOTTOM_DOUBLE.connectorRight)
        screen.draw(box, Position.create(2, 2))
        screen.display()
    }

}

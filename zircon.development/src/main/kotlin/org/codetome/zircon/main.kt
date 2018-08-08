package org.codetome.zircon

import org.codetome.zircon.api.builder.component.ButtonBuilder
import org.codetome.zircon.api.builder.component.PanelBuilder
import org.codetome.zircon.api.builder.grid.AppConfigBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.data.CharacterTile
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.graphics.BoxType
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.api.modifier.Border
import org.codetome.zircon.api.modifier.BorderPosition.*
import org.codetome.zircon.api.modifier.BorderType.SOLID
import org.codetome.zircon.api.modifier.SimpleModifiers.*
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.resource.ColorThemeResource
import org.codetome.zircon.api.util.Consumer
import org.codetome.zircon.gui.swing.internal.application.SwingApplication
import org.codetome.zircon.internal.screen.TileGridScreen

fun main(args: Array<String>) {


    val size = Size.create(80, 40)
    val tileset = CP437TilesetResource.WANDERLUST_16X16

    val app = SwingApplication.create(
            AppConfigBuilder.newBuilder()
                    .defaultSize(size)
                    .defaultTileset(tileset)
                    .debugMode(true)
                    .build())

    app.start()

    val screen = TileGridScreen(app.tileGrid)
    screen.display()

    val tile = CharacterTile(
            character = '2',
            style = StyleSet.create(
                    foregroundColor = ANSITextColor.RED,
                    backgroundColor = ANSITextColor.BLUE))

    screen.setTileAt(Position.create(1, 2), tile)
    screen.setTileAt(Position.create(3, 2), tile
            .withModifiers(CrossedOut).withBackgroundColor(ANSITextColor.GREEN))
    screen.setTileAt(Position.create(5, 2), tile
            .withModifiers(Glow).withForegroundColor(ANSITextColor.CYAN))
    screen.setTileAt(Position.create(7, 2), tile
            .withModifiers(Hidden).withBackgroundColor(ANSITextColor.MAGENTA))
    screen.setTileAt(Position.create(9, 2), tile
            .withModifiers(HorizontalFlip).withForegroundColor(ANSITextColor.BLACK))
    screen.setTileAt(Position.create(11, 2), tile
            .withModifiers(VerticalFlip).withBackgroundColor(ANSITextColor.WHITE))
    screen.setTileAt(Position.create(13, 2), tile
            .withModifiers(Underline).withForegroundColor(ANSITextColor.YELLOW))
    screen.setTileAt(Position.create(15, 2), tile
            .withModifiers(Border(SOLID, setOf(TOP, RIGHT, BOTTOM, LEFT)))
            .withBackgroundColor(ANSITextColor.DEFAULT))


    val panel = PanelBuilder.newBuilder()
            .boxType(BoxType.LEFT_RIGHT_DOUBLE)
            .position(Position.create(5, 5))
            .size(Size.create(20, 15))
            .wrapWithShadow()
            .wrapWithBox()
            .title("Title")
            .build()

    val btn = ButtonBuilder.newBuilder()
            .text("Fux")
            .build()

    btn.onMouseReleased(object: Consumer<MouseAction>{
        override fun accept(t: MouseAction) {
            println("klakk")
        }
    })

    panel.addComponent(btn)

    screen.addComponent(panel)
    screen.applyColorTheme(ColorThemeResource.ADRIFT_IN_DREAMS.getTheme())

    screen.display()

}


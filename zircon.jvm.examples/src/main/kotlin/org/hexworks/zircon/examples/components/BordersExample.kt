package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.Components.panel
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor.Companion.create
import org.hexworks.zircon.api.color.TileColor.Companion.fromString
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.data.Position.Companion.create
import org.hexworks.zircon.api.data.Tile.Companion.defaultTile
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.modifier.Border.Companion.newBuilder
import org.hexworks.zircon.api.modifier.BorderPosition
import org.hexworks.zircon.api.modifier.BorderType
import org.hexworks.zircon.examples.base.OneColumnComponentExample
import java.util.*

object BordersExample : OneColumnComponentExample() {

    @JvmStatic
    fun main(args: Array<String>) {
        BordersExample.show("Borders Example")
    }

    override fun build(box: VBox) {
        val tiles = Arrays.asList(
            defaultTile()
                .withBackgroundColor(ANSITileColor.GREEN)
                .withCharacter('a')
                .withModifiers(
                    newBuilder()
                        .withBorderPositions(BorderPosition.TOP)
                        .withBorderType(BorderType.DOTTED)
                        .withBorderColor(fromString("#ffaadd"))
                        .withBorderWidth(5)
                        .build()
                ),
            defaultTile()
                .withBackgroundColor(ANSITileColor.BLUE)
                .withCharacter('b')
                .withModifiers(
                    newBuilder()
                        .withBorderPositions(BorderPosition.RIGHT)
                        .withBorderType(BorderType.SOLID)
                        .withBorderColor(fromString("#caacaa"))
                        .withBorderWidth(10)
                        .build()
                ),
            defaultTile()
                .withBackgroundColor(ANSITileColor.GRAY)
                .withCharacter('c')
                .withModifiers(
                    newBuilder()
                        .withBorderPositions(BorderPosition.BOTTOM)
                        .withBorderType(BorderType.DASHED)
                        .withBorderColor(fromString("#caacaa"))
                        .withBorderWidth(15)
                        .build()
                ),
            defaultTile()
                .withBackgroundColor(ANSITileColor.RED)
                .withCharacter('d')
                .withModifiers(
                    newBuilder()
                        .withBorderPositions(BorderPosition.LEFT)
                        .withBorderType(BorderType.SOLID)
                        .withBorderColor(create(80, 80, 80, 80))
                        .withBorderWidth(20)
                        .build()
                ),
            defaultTile()
                .withBackgroundColor(ANSITileColor.RED)
                .withCharacter('e')
                .withModifiers(
                    newBuilder()
                        .withBorderPositions(BorderPosition.TOP)
                        .withBorderColor(create(0, 0, 0, 20))
                        .withBorderWidth(30)
                        .build(),
                    newBuilder()
                        .withBorderPositions(BorderPosition.TOP)
                        .withBorderColor(create(0, 0, 0, 20))
                        .withBorderWidth(20)
                        .build(),
                    newBuilder()
                        .withBorderPositions(BorderPosition.TOP)
                        .withBorderColor(create(0, 0, 0, 20))
                        .withBorderWidth(10)
                        .build()
                )
        )
        val panel = panel()
            .withPreferredSize(tiles.size, 1)
            .withComponentRenderer { graphics: TileGraphics, context: ComponentRenderContext<Panel> ->
                for (i in tiles.indices) {
                    graphics.draw(tiles[i], create(i, 0))
                }
            }
            .build()
        box.addComponent(panel)
    }
}
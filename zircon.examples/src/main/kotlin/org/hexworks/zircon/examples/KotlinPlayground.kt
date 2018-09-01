package org.hexworks.zircon.examples

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.internal.component.impl.DefaultContainer

object KotlinPlayground {

    val TILESET = BuiltInCP437TilesetResource.CHEEPICUS_16X16

    class LogPanel2(size: Size, position: Position) : DefaultContainer(
            initialSize = size,
            position = position,
            initialTileset = TILESET,
            wrappers = listOf(),
            componentStyleSet = ComponentStyleSet.defaultStyleSet()) {
        private val MAX_HISTORY = 100
        private val lines = mutableListOf<String>()

        private val panel = TileGraphics.newBuilder().size(getEffectiveSize()).build()

        fun addMessage(message: String) {
            lines.add(message)
            if (lines.size > MAX_HISTORY)
                lines.removeAt(0)
            redraw()
        }

        private fun redraw() {
            lines.subList(Math.max(0, lines.size - getEffectiveSize().height()), lines.size).forEachIndexed { idx, line ->
                panel.putText(line, Positions.defaultPosition().withRelativeY(idx))
            }
            panel.drawOnto(this, getEffectivePosition())
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(TILESET)
                .defaultSize(Sizes.create(30, 20))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        screen.addComponent(LogPanel2(Sizes.create(30, 20), Position.defaultPosition()))

        screen.display()
        screen.applyColorTheme(ColorThemes.adriftInDreams())

    }

}

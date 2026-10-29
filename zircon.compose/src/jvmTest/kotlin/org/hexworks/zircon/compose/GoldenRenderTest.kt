package org.hexworks.zircon.compose

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.header
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.builder.grid.tileGrid
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.internal.util.CP437Index
import org.hexworks.zircon.internal.util.CP437_METADATA

import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.TrueTypeFontResources
import org.hexworks.zircon.api.builder.data.graphicalTile
import kotlin.test.Test

class GoldenRenderTest {

    @Test
    fun rendersComponentsWithTheDefaultTileset() {
        val grid = tileGrid {
            size = Size(40, 12)
            tileset = CP437TilesetResources.wanderlust16x16()
        }
        val screen = grid.toScreen()
        screen.theme = ColorThemes.arc()
        val content = vBox {
            preferredSize = Size(40, 12)
            decorations {
                +box(title = "Zircon 2.0")
            }
        }
        screen.addComponent(content)
        content.addComponent(header { +"Hello, Compose!" })
        content.addComponent(label { +"The quick brown fox jumps over it." })
        content.addComponent(button { +"OK" })
        content.addComponent(panel {
            preferredSize = Size(20, 4)
            decorations { +box() }
        })
        screen.display()

        assertMatchesGolden("components-wanderlust", renderToPng(grid))
    }

    @Test
    fun rendersEveryCp437Glyph() {
        val grid = tileGrid {
            size = Size(16, 16)
            tileset = CP437TilesetResources.rexPaint16x16()
        }
        val chars = CP437_METADATA.keys.sortedBy { it.CP437Index() }
        chars.forEachIndexed { index, char ->
            grid.draw(
                characterTile {
                    character = char
                    withStyleSet {
                        foregroundColor = Color.create(255, 255, 255)
                        backgroundColor = Color.create(index % 2 * 40, 0, 60)
                    }
                },
                Position(index % 16, index / 16)
            )
        }

        assertMatchesGolden("cp437-rexpaint", renderToPng(grid))
    }


    @Test
    fun rendersTextWithATrueTypeFont() {
        val grid = tileGrid {
            size = Size(34, 3)
            tileset = TrueTypeFontResources.ibmBios(16)
        }
        "TrueType: The quick brown fox 0123".forEachIndexed { index, char ->
            grid.draw(
                characterTile {
                    character = char
                    withStyleSet {
                        foregroundColor = Color.create(255, 220, 120)
                        backgroundColor = Color.create(20, 30, 50)
                    }
                },
                Position(index, 1)
            )
        }

        assertMatchesGolden("truetype-ibm-bios", renderToPng(grid))
    }

    @Test
    fun rendersGraphicalTilesFromTheNethackTileset() {
        val nethack = GraphicalTilesetResources.nethack16x16()
        val grid = tileGrid {
            size = Size(6, 2)
            tileset = nethack
        }
        listOf("Giant ant", "Killer bee", "Soldier ant", "Fire ant", "Giant beetle", "Queen bee").forEachIndexed { index, name ->
            grid.draw(graphicalTile { this.name = name; tileset = nethack }, Position(index, 0))
        }
        listOf("Acid blob", "Quivering blob", "Gelatinous cube", "Chickatrice", "Cockatrice", "Pyrolisk").forEachIndexed { index, name ->
            grid.draw(graphicalTile { this.name = name; tileset = nethack }, Position(index, 1))
        }

        assertMatchesGolden("graphical-nethack", renderToPng(grid, ticks = 2))
    }
}

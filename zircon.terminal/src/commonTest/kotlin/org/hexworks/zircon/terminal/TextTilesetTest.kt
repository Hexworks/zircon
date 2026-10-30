package org.hexworks.zircon.terminal

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.builder.config.zirconDefaults
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.graphicalTile
import org.hexworks.zircon.api.config.ErrorHandler
import org.hexworks.zircon.api.config.ZirconDefaults
import org.hexworks.zircon.api.data.Position
import kotlin.test.AfterTest
import kotlin.test.Test

class TextTilesetTest {

    private val previousDefaults = ZirconDefaults.current
    private val errors = mutableListOf<String>()
    private val nethack = GraphicalTilesetResources.nethack16x16()

    init {
        zirconDefaults { onError = ErrorHandler { source, _ -> errors += source } }
    }

    @AfterTest
    fun tearDown() {
        ZirconDefaults.current = previousDefaults
    }

    @Test
    fun a_graphical_tile_shows_as_the_replacement_character_and_is_reported_once() {
        val target = TextTileset(CP437TilesetResources.rogueYun16x16())
        val buffer = CellBuffer(2, 1)
        val tile = graphicalTile {
            name = "rat"
            tileset = nethack
        }

        target.drawTile(tile, buffer, Position(0, 0))
        target.drawTile(tile, buffer, Position(1, 0))

        buffer.codes[0] shouldBe TerminalCharacters.REPLACEMENT
        buffer.codes[1] shouldBe TerminalCharacters.REPLACEMENT
        errors shouldHaveSize 1
        errors.single() shouldBe "Tile at ${Position(0, 0)}"
    }

    @Test
    fun a_character_tile_writes_its_code() {
        val target = TextTileset(CP437TilesetResources.rogueYun16x16())
        val buffer = CellBuffer(1, 1)

        target.drawTile(characterTile { character = 'a' }, buffer, Position(0, 0))

        buffer.codes[0] shouldBe 'a'.code
        errors shouldHaveSize 0
    }

    @Test
    fun the_loader_hands_out_a_text_tileset_for_a_graphical_resource_and_reports_it_once() {
        val target = TextTilesetLoader()

        val first = target.loadTilesetFrom(nethack)
        target.loadTilesetFrom(nethack)

        first.shouldBeInstanceOf<TextTileset>()
        target.canLoadTileset(nethack) shouldBe false
        errors shouldHaveSize 1
        errors.single() shouldBe "Tileset '${nethack.path}'"
    }
}

package org.hexworks.zircon

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.graphics.DefaultTileComposite
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CharacterTilesAsStringTest {
    private fun pos(x: Int, y: Int) = Position.create(x, y)
    private fun charTile(char: Char) = Tile.newBuilder().withCharacter(char).buildCharacterTile()

    private fun tileComposite(tiles: Map<Position, Tile>, size: Size = Size.zero()) = DefaultTileComposite(tiles, size)

    @Test
    fun empty() {
        assertTrue("is empty") { tileComposite(emptyMap()).convertCharacterTilesToString().isEmpty() }
    }

    @Test
    fun simple() {
        assertEquals(".", tileComposite(mapOf(pos(0, 0) to charTile('.'))).convertCharacterTilesToString())
    }

    @Test
    fun leadingSpaces() {
        assertEquals(
            "   .", tileComposite(
                mapOf<Position, Tile>(
                    pos(3, 0) to charTile('.')
                )
            ).convertCharacterTilesToString()
        )
    }

    @Test
    fun multipleLines() {
        assertEquals(
            """
                .
                !
            """.trimIndent(), tileComposite(
                mapOf<Position, Tile>(
                    pos(0, 0) to charTile('.'),
                    pos(0, 1) to charTile('!')
                )
            ).convertCharacterTilesToString()
        )
    }

    @Test
    fun skippedLine() {
        assertEquals(
            """
                
                !
            """.trimIndent(),
            tileComposite(
                mapOf<Position, Tile>(
                    pos(0, 1) to charTile('!')
                )
            ).convertCharacterTilesToString()
        )
    }

    @Test
    fun padToWidth() {
        assertEquals(
            "  .  ",
            tileComposite(
                mapOf(
                    pos(2, 0) to charTile('.')
                )
            ).convertCharacterTilesToString(padToSize = Size.create(5, 0))
        )
    }

    @Test
    fun padToHeight() {
        assertEquals(
            ".\n",
            tileComposite(
                mapOf(
                    pos(0, 0) to charTile('.')
                )
            ).convertCharacterTilesToString(padToSize = Size.create(0, 2))
        )
    }

    @Test
    fun padToSize() {
        assertEquals(
            "   \n . \n   ",
            tileComposite(
                mapOf(
                    pos(1, 1) to charTile('.')
                )
            ).convertCharacterTilesToString(padToSize = Size.create(3, 3))
        )
    }

    @Test
    fun words() {
        assertEquals(
            """
                hi to
                
                you
            """.trimIndent(),
            tileComposite(
                mapOf<Position, Tile>(
                    pos(0, 2) to charTile('y'),
                    pos(1, 2) to charTile('o'),
                    pos(2, 2) to charTile('u'),
                    pos(0, 0) to charTile('h'),
                    pos(1, 0) to charTile('i'),
                    pos(2, 0) to charTile(' '),
                    pos(3, 0) to charTile('t'),
                    pos(4, 0) to charTile('o')
                )
            ).convertCharacterTilesToString()
        )
    }
}

package org.hexworks.zircon.api.builder

import org.hexworks.zircon.api.builder.data.StackedTileBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.StackedTile
import org.hexworks.zircon.api.data.Tile
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StackedTileBuilderTest {

    private val t1 = Tile.newBuilder()
        .withCharacter('1')
        .withForegroundColor(ANSITileColor.RED)
        .withBackgroundColor(TileColor.transparent())
        .buildCharacterTile()
    private val t2 = Tile.newBuilder()
        .withCharacter('2')
        .withForegroundColor(ANSITileColor.GREEN)
        .withBackgroundColor(TileColor.transparent())
        .buildCharacterTile()
    private val t3 = Tile.newBuilder()
        .withCharacter('3')
        .withForegroundColor(ANSITileColor.BLUE)
        .withBackgroundColor(TileColor.transparent())
        .buildCharacterTile()

    @Test
    fun shouldBuildProperStack(){
        val builtTile = StackedTileBuilder.newBuilder()
            .withBaseTile(t1)
            .withPushedTile(t2)
            .build()

        builtTile.shouldBeComposedOf(t1, t2)
    }

    @Test
    fun shouldPushProperly(){
        val builtTile = StackedTileBuilder.newBuilder()
            .withBaseTile(t1)
            .withPushedTile(t2)
            .withPushedTile(t3)
            .build()

        builtTile.shouldBeComposedOf(t1, t2, t3)
    }

    @Test
    fun shouldPushAlotTilesProperly(){
        val builtTile = StackedTileBuilder.newBuilder()
            .withBaseTile(t1)
            .withPushedTiles(t3, t2)
            .build()

        builtTile.shouldBeComposedOf(t1, t2, t3)
    }

    private fun StackedTile.shouldBeComposedOf(vararg expectedTiles: Tile) {
        val expectedSize = expectedTiles.size
        assertTrue(expectedSize > 0, "Can not assert a StackedTile without expected tiles!")

        val expectedBaseTile = expectedTiles.first()
        val expectedTopTile = expectedTiles.last()

        assertEquals(expectedSize, this.tiles.size, "$this should contain $expectedSize tiles")
        assertEquals(expectedBaseTile, this.baseTile, "Base tile not equal to $expectedBaseTile")
        assertEquals(expectedTopTile, this.top, "Top tile should be $expectedTopTile")
        expectedTiles.forEachIndexed { index, expectedTile ->
            assertEquals(expectedTile, this.tiles[index], "Tile at stack index $index not equal to the expected tile.")
        }
    }
}
package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultStackedTileTest {

    private val t1 = Tile.newBuilder()
            .withCharacter('1')
            .withForegroundColor(ANSITileColor.RED)
            .withBackgroundColor(TileColor.transparent())
            .buildCharacterTile()
    private val t2 = Tile.newBuilder()
            .withCharacter('2')
            .withForegroundColor(ANSITileColor.BLUE)
            .withBackgroundColor(TileColor.transparent())
            .buildCharacterTile()
    private val t3 = Tile.newBuilder()
            .withCharacter('3')
            .withForegroundColor(ANSITileColor.RED)
            .withBackgroundColor(TileColor.transparent())
            .buildCharacterTile()

    @Test
    fun simpleStack() {
        val simpleStack = StackedTile.create(t1, t2, t3)

        assertStackedTiles(simpleStack, t1, t2, t3)
    }

    @Test
    fun stackedStack() {
        val stack1 = StackedTile.create(t1, t2)
        val stack2 = StackedTile.create(t2, t3)

        val stackedStack = StackedTile.create(stack1, stack2)
        assertStackedTiles(stackedStack, stack1, stack2)
        assertStackedTiles(stackedStack.tiles[0] as StackedTile, t1, t2)
        assertStackedTiles(stackedStack.tiles[1] as StackedTile, t2, t3)
    }

    @Test
    fun withBaseTileOnly() {
        val baseOnly = StackedTile.create(t1)
        assertStackedTiles(baseOnly, t1)
        assertStackedTiles(baseOnly.withBaseTile(t2), t2)
    }

    @Test
    fun withBaseTile() {
        val stack = StackedTile.create(t1, t2)
        assertStackedTiles(stack, t1, t2)
        assertStackedTiles(stack.withBaseTile(t3), t3, t2)
    }

    @Test
    fun withPushedTile() {
        val stack = StackedTile.create(t1, t2)
        assertStackedTiles(stack, t1, t2)
        assertStackedTiles(stack.withPushedTile(t3), t1, t2, t3)
    }

    @Test
    fun withRemovedTile() {
        val stack = StackedTile.create(t1, t2, t3)
        assertStackedTiles(stack, t1, t2, t3)
        assertStackedTiles(stack.withRemovedTile(t2), t1, t3)
    }

    @Test
    fun withRemovedBaseTile() {
        val stack = StackedTile.create(t1, t2, t3)
        assertStackedTiles(stack, t1, t2, t3)
        assertStackedTiles(stack.withRemovedTile(t1), t2, t3)
    }

    private fun assertStackedTiles(simpleStack: StackedTile, vararg expectedTiles: Tile) {
        val expectedSize = expectedTiles.size
        assertEquals(expectedSize, simpleStack.tiles.size, "StackedTile should contain $expectedSize tiles")
        val expectedBaseTile = expectedTiles.first()
        assertEquals(expectedBaseTile, simpleStack.baseTile, "Base tile not equal to $expectedBaseTile")
        expectedTiles.forEachIndexed { index, expectedTile ->
            assertEquals(expectedTile, simpleStack.tiles[index])
        }
    }
}
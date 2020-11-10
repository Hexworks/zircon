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
            .withForegroundColor(ANSITileColor.GREEN)
            .withBackgroundColor(TileColor.transparent())
            .buildCharacterTile()
    private val t3 = Tile.newBuilder()
            .withCharacter('3')
            .withForegroundColor(ANSITileColor.BLUE)
            .withBackgroundColor(TileColor.transparent())
            .buildCharacterTile()

    @Test
    fun simpleStack() {
        val simpleStack = StackedTile.create(t1, t2, t3)

        simpleStack.shouldBeComposedOf(t1, t2, t3)
    }

    @Test
    fun stackedStack() {
        val stack1 = StackedTile.create(t1, t2)
        val stack2 = StackedTile.create(t2, t3)

        val stackedStack = StackedTile.create(stack1, stack2)
        stackedStack.shouldBeComposedOf(stack1, stack2)
        (stackedStack.tiles[0] as StackedTile).shouldBeComposedOf(t1, t2)
        (stackedStack.tiles[1] as StackedTile).shouldBeComposedOf(t2, t3)
    }

    @Test
    fun withBaseTileOnly() {
        val baseOnly = StackedTile.create(t1)
        baseOnly.shouldBeComposedOf(t1)
        baseOnly.withBaseTile(t2).shouldBeComposedOf(t2)
    }

    @Test
    fun withBaseTile() {
        val stack = StackedTile.create(t1, t2)
        stack.shouldBeComposedOf(t1, t2)
        stack.withBaseTile(t3).shouldBeComposedOf(t3, t2)
    }

    @Test
    fun withPushedTile() {
        val stack = StackedTile.create(t1, t2)
        stack.shouldBeComposedOf(t1, t2)
        stack.withPushedTile(t3).shouldBeComposedOf(t1, t2, t3)
    }

    @Test
    fun withRemovedTile() {
        val stack = StackedTile.create(t1, t2, t3)
        stack.shouldBeComposedOf(t1, t2, t3)

        val removedOne = stack.withRemovedTile(t2)
        removedOne.shouldBeComposedOf(t1, t3)

        val removedBoth = removedOne.withRemovedTile(t3)
        removedBoth.shouldBeComposedOf(t1)
    }

    @Test
    fun withRemovedBaseTile() {
        val stack = StackedTile.create(t1, t2, t3)
        stack.shouldBeComposedOf(t1, t2, t3)
        stack.withRemovedTile(t1).shouldBeComposedOf(t1, t2, t3)
    }

    @Test
    fun withRemovedLastTile() {
        val stack = StackedTile.create(t1)
        stack.shouldBeComposedOf(t1)
        stack.withRemovedTile(t3).shouldBeComposedOf(t1)
        val withRemovedTile = stack.withRemovedTile(t1)
        assertEquals(stack, withRemovedTile, "After trying to remove the base tile, the StackedTile should still be the same")
        withRemovedTile.shouldBeComposedOf(t1)
    }

    private fun StackedTile.shouldBeComposedOf(vararg expectedTiles: Tile) {
        val expectedSize = expectedTiles.size
        assertEquals(expectedSize, this.tiles.size, "$this should contain $expectedSize tiles")
        val expectedBaseTile = expectedTiles.first()
        assertEquals(expectedBaseTile, this.baseTile, "Base tile not equal to $expectedBaseTile")
        expectedTiles.forEachIndexed { index, expectedTile ->
            assertEquals(expectedTile, this.tiles[index])
        }
    }
}
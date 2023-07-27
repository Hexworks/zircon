package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.ANSITileColor.GREEN
import org.hexworks.zircon.api.color.ANSITileColor.RED
import org.hexworks.zircon.api.color.TileColor.Companion.transparent
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DefaultStackedTileTest {

    private val t1 = characterTile {
        character = '1'
        withStyleSet {
            foregroundColor = RED
            backgroundColor = transparent()
        }
    }

    private val t2 = characterTile {
        character = '2'
        withStyleSet {
            foregroundColor = GREEN
            backgroundColor = transparent()
        }
    }
    private val t3 = characterTile {
        character = '3'
        withStyleSet {
            foregroundColor = ANSITileColor.BLUE
            backgroundColor = transparent()
        }
    }

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
        assertEquals(
            stack,
            withRemovedTile,
            "After trying to remove the base tile, the StackedTile should still be the same"
        )
        withRemovedTile.shouldBeComposedOf(t1)
    }

    @Test
    fun asOtherTileFromTop() {
        val stack = StackedTile.create(t1, t2)

        stack.assertAsOtherTile(t2)
    }

    @Test
    fun asOtherTileFromTopWithBaseTile() {
        val stack = StackedTile.create(t3)

        stack.assertAsOtherTile(t3)
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

    private fun StackedTile.assertAsOtherTile(expectedCharTile: Tile) {
        val charTile = asCharacterTileOrNull()
        val imageTile = asImageTileOrNull()
        val graphicTile = asGraphicalTileOrNull()
        assertTrue(imageTile == null, "The stack should not be convertible to ${ImageTile::class}")
        assertTrue(graphicTile == null, "The stack should not be convertible to ${GraphicalTile::class}")
        assertTrue(charTile != null, "Stack should be convertible to ${CharacterTile::class}")
        assertEquals(expectedCharTile, charTile, "Conversion to character tile should result in the top tile")
        assertEquals(top, charTile, "Conversion to character tile should result in the top tile")
    }
}

package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.graphics.ArrayBackedTileMap.Entry
import kotlin.test.*

@Suppress("TestFunctionName")
class ArrayBackedTileMapTest {

    lateinit var target: ArrayBackedTileMap

    @BeforeTest
    fun setUp() {
        val arr = arrayOfNulls<Map.Entry<Position, Tile>>(SIZE_2X3.width * SIZE_2X3.height)
        target = ArrayBackedTileMap(
            dimensions = SIZE_2X3,
            arr = arr
        )
        arr[3] = Entry(FILLED_POSITION_1_1, FILLER)
        arr[5] = Entry(FILLED_POSITION_1_2, FILLER)
    }

    @Test
    fun When_accessing_entries_Then_proper_entries_should_be_returned() {
        assertEquals(
            expected = listOf(FILLED_POSITION_1_1 to FILLER, FILLED_POSITION_1_2 to FILLER),
            actual = target.entries.map { it.key to it.value }
        )
    }

    @Test
    fun When_accessing_keys_Then_proper_keys_should_be_returned() {
        assertEquals(
            expected = listOf(FILLED_POSITION_1_1, FILLED_POSITION_1_2),
            actual = target.entries.map { it.key }
        )
    }

    @Test
    fun When_accessing_values_Then_proper_values_should_be_returned() {
        assertEquals(
            expected = listOf(FILLER, FILLER),
            actual = target.entries.map { it.value }
        )
    }

    @Test
    fun When_accessing_size_Then_the_proper_size_should_be_returned() {
        assertEquals(
            expected = SIZE_2X3.width * SIZE_2X3.height,
            actual = target.size
        )
    }

    @Test
    fun When_checking_if_key_is_contained_Then_it_should_return_true_when_key_is_present() {
        assertTrue(target.containsKey(FILLED_POSITION_1_1))
    }

    @Test
    fun When_checking_if_key_is_contained_Then_it_should_return_false_when_key_is_not_present() {
        assertFalse(target.containsKey(EMPTY_POSITION_0_1))
    }

    @Test
    fun When_checking_out_of_bounds_key_Then_it_should_return_false() {
        assertFalse(target.containsKey(OUT_OF_BOUNDS_POSITION_2_2))
    }

    @Test
    fun When_checking_if_value_is_contained_Then_it_should_return_true_when_value_is_present() {
        assertTrue(target.containsValue(FILLER))
    }

    @Test
    fun When_checking_if_is_empty_when_not_Then_it_should_return_false() {
        assertFalse(target.isEmpty())
    }

    @Test
    fun When_checking_if_is_empty_when_empty_Then_it_should_return_true() {
        assertFalse(
            ArrayBackedTileMap(
                dimensions = SIZE_2X3,
                arr = arrayOfNulls(SIZE_2X3.width * SIZE_2X3.height)
            ).isEmpty()
        )
    }

    @Test
    fun When_trying_to_get_value_when_present_Then_it_should_be_returned() {
        assertEquals(
            expected = FILLER,
            actual = target[FILLED_POSITION_1_1]
        )
    }

    @Test
    fun When_trying_to_create_copy_Then_a_proper_copy_should_be_created() {
        val arr = arrayOfNulls<Map.Entry<Position, Tile>>(SIZE_2X3.width * SIZE_2X3.height)
        target = ArrayBackedTileMap(
            dimensions = SIZE_2X3,
            arr = arr
        )
        val copy = target.createCopy()
        arr[0] = Entry(Position.zero(), FILLER)

        assertTrue(copy[Position.zero()] === null)
    }

    companion object {

        val SIZE_2X3 = Size.create(2, 3)
        val FILLED_POSITION_1_1 = Position.create(1, 1)
        val FILLED_POSITION_1_2 = Position.create(1, 2)
        val EMPTY_POSITION_0_1 = Position.create(0, 1)
        val OUT_OF_BOUNDS_POSITION_2_2 = Position.create(2, 2)
        val FILLER = Tile.defaultTile().withCharacter('x')
    }
}

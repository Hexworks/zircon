package org.hexworks.zircon.internal.util

import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertIs
import kotlin.test.expect


class BoundedFifoQueueTest {

    @Test
    fun given_a_queue_when_building_it_then_max_elements_must_be_greater_than_0() {
        val result = assertFails {
            BoundedFifoQueue.create<Int>(
                maxElements = 0
            )
        }
        assertIs<IllegalArgumentException>(result)
    }

    @Test
    fun given_a_queue_when_building_it_then_max_elements_cannot_be_less_than_elements_size() {
        val result = assertFails {
            BoundedFifoQueue.create(
                elements = listOf(1, 2),
                maxElements = 1
            )
        }
        assertIs<IllegalArgumentException>(result)
    }

    @Test
    fun given_a_queue_when_elements_are_queried_then_they_are_returned() {
        val elements = listOf(1, 2)
        val target = BoundedFifoQueue.create(elements)

        expect(elements) { target.elements }
    }

    @Test
    fun given_a_queue_when_size_is_queried_then_it_is_returned() {
        val elements = listOf(1, 2)
        val target = BoundedFifoQueue.create(elements)

        expect(2) { target.size }
    }

    @Test
    fun given_a_queue_when_last_index_is_queried_then_it_is_returned() {
        val elements = listOf(1, 2)
        val target = BoundedFifoQueue.create(elements)

        expect(1) { target.lastIndex }
    }

    @Test
    fun given_a_queue_when_an_element_is_queried_then_it_is_returned() {
        val elements = listOf(1, 2, 3)
        val target = BoundedFifoQueue.create(elements)

        expect(2) { target[1] }
    }

    @Test
    fun given_a_queue_when_a_sub_list_is_queried_then_it_is_returned() {
        val elements = listOf(1, 2, 3, 4, 5, 6)
        val target = BoundedFifoQueue.create(elements)

        expect(listOf(3, 4)) { target.subList(2, 4) }
    }

    @Test
    fun given_a_queue_when_too_many_elements_are_added_then_kick_listeners_are_notified() {
        val elements = listOf(1, 2, 3)
        val target = BoundedFifoQueue.create(
            elements = elements,
            maxElements = elements.size
        )

        val kickedElements0 = mutableListOf<Int>()
        val kickedElements1 = mutableListOf<Int>()

        target.onKick(kickedElements0::add)

        target.add(6)

        target.onKick(kickedElements1::add)

        target.add(7)

        expect(listOf(1, 2)) { kickedElements0 }

        expect(listOf(2)) { kickedElements1 }
    }

    @Test
    fun given_a_queue_when_too_many_elements_are_added_then_the_first_added_elements_are_kicked() {
        val elements = listOf(1, 2, 3)
        val target = BoundedFifoQueue.create(
            elements = elements
        )

        target.add(4)
        target.add(5)

        expect(listOf(3, 4, 5)) { target.elements }
    }


}
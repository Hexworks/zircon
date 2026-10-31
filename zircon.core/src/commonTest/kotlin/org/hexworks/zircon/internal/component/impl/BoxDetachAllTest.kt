package org.hexworks.zircon.internal.component.impl

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import kotlin.test.Test

/**
 * `detachAllComponents` detaches the undecorated attachments, so the boxes have to reset
 * their own layout bookkeeping or nothing fits afterwards (the table fragment reloads this way).
 */
class BoxDetachAllTest {

    @Test
    fun vBoxCanBeRefilledAfterDetachAll() {
        val box = vBox { preferredSize = Size(10, 3) }
        repeat(3) { box.addComponent(label { +"a"; preferredSize = Size(10, 1) }) }
        box.remainingSpace shouldBe 0

        box.detachAllComponents()

        box.remainingSpace shouldBe 3
        val rows = List(3) { label { +"b"; preferredSize = Size(10, 1) } }
        rows.forEach(box::addComponent)
        rows.map { it.position } shouldBe listOf(Position(0, 0), Position(0, 1), Position(0, 2))
    }

    @Test
    fun hBoxCanBeRefilledAfterDetachAll() {
        val box = hBox { preferredSize = Size(3, 1) }
        repeat(3) { box.addComponent(label { +"a"; preferredSize = Size(1, 1) }) }
        box.remainingSpace shouldBe 0

        box.detachAllComponents()

        box.remainingSpace shouldBe 3
        val cells = List(3) { label { +"b"; preferredSize = Size(1, 1) } }
        cells.forEach(box::addComponent)
        cells.map { it.position } shouldBe listOf(Position(0, 0), Position(1, 0), Position(2, 0))
    }
}

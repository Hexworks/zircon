package org.hexworks.zircon.internal.component.impl

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import kotlin.test.Test

class ComponentMoveToTest {

    @Test
    fun movingAComponentOutOfItsParentIsRefusedAndItStaysPut() {
        val parent = panel { preferredSize = Size(10, 10) }
        val child = label { +"x"; position = Position(1, 1) }
        parent.addComponent(child)

        child.moveTo(Position(50, 50)) shouldBe false

        child.position shouldBe Position(1, 1)
    }

    @Test
    fun movingAComponentWithinItsParentSucceeds() {
        val parent = panel { preferredSize = Size(10, 10) }
        val child = label { +"x"; position = Position(1, 1) }
        parent.addComponent(child)

        child.moveTo(Position(5, 5)) shouldBe true

        child.position shouldBe Position(5, 5)
    }
}

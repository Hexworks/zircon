package org.hexworks.zircon.internal.component.impl

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import kotlin.test.Test

/**
 * The horizontal and the vertical box share one layout; each case runs for both axes.
 */
class DefaultLinearBoxTest {

    private class Axis(
        val name: String,
        val box: (Size, Int) -> Container,
        val size: (extent: Int, across: Int) -> Size,
        val at: (extent: Int) -> Position,
        val remaining: (Container) -> Int,
    )

    private val axes = listOf(
        Axis("hBox", { size, spacing -> hBox { preferredSize = size; this.spacing = spacing } },
            { extent, across -> Size(extent, across) }, { Position(it, 0) }, { (it as DefaultHBox).remainingSpace }),
        Axis("vBox", { size, spacing -> vBox { preferredSize = size; this.spacing = spacing } },
            { extent, across -> Size(across, extent) }, { Position(0, it) }, { (it as DefaultVBox).remainingSpace }),
    )

    private fun Axis.child(extent: Int, across: Int = 1): Component = label { +"x"; preferredSize = size(extent, across) }

    private fun forEachAxis(check: Axis.() -> Unit) = axes.forEach { axis ->
        runCatching { axis.check() }.onFailure { throw AssertionError("${axis.name}: ${it.message}", it) }
    }

    @Test
    fun childrenFollowEachOtherAlongTheAxisWithTheSpacingBetweenThem() = forEachAxis {
        val box = box(size(20, 2), 2)
        val a = child(3)
        val b = child(4)
        val c = child(1)
        listOf(a, b, c).forEach(box::addComponent)

        a.position shouldBe at(0)
        b.position shouldBe at(5)
        c.position shouldBe at(11)
        remaining(box) shouldBe 8
    }

    @Test
    fun aChildThatDoesNotFitIsRefused() = forEachAxis {
        val box = box(size(5, 1), 0)
        box.addComponent(child(3))

        shouldThrow<IllegalArgumentException> { box.addComponent(child(3)) }
        shouldThrow<IllegalArgumentException> { box.addComponent(child(1, across = 2)) }
        box.addComponent(child(2))
        remaining(box) shouldBe 0
    }

    @Test
    fun detachingAChildClosesTheGapAndFreesItsSpace() = forEachAxis {
        val box = box(size(10, 1), 1)
        val a = child(2)
        val b = child(3)
        val c = child(1)
        box.addComponent(a)
        val attachedB = box.addComponent(b)
        box.addComponent(c)

        attachedB.detach()

        a.position shouldBe at(0)
        c.position shouldBe at(3)
        remaining(box) shouldBe 6
        val d = child(5)
        box.addComponent(d)
        d.position shouldBe at(5)
    }

    @Test
    fun detachingTheOnlyChildLeavesAnEmptyBox() = forEachAxis {
        val box = box(size(4, 1), 1)
        val attached = box.addComponent(child(2))

        attached.detach()

        remaining(box) shouldBe 4
        val d = child(4)
        box.addComponent(d)
        d.position shouldBe at(0)
    }
}

package org.hexworks.zircon.internal.component.impl

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.builder.component.radioButton
import org.hexworks.zircon.api.builder.component.selectorGroup
import org.hexworks.zircon.api.component.BinarySelector
import org.hexworks.zircon.api.component.ExclusiveSelectionGroup
import kotlin.test.BeforeTest
import kotlin.test.Test

class DefaultExclusiveSelectionGroupTest {

    lateinit var target: ExclusiveSelectionGroup<BinarySelector>

    @BeforeTest
    fun setUp() {
        target = selectorGroup { }
    }

    @Test
    fun shouldSelectButtonWhenClicked() {
        val btn = radioButton { key = "qux"; text = "baz" } as DefaultBinarySelector
        target.addComponent(btn)

        // A click is a press (activation) followed by a release (deactivation) on the button.
        btn.activated()
        btn.deactivated()

        btn.isSelected shouldBe true
        target.selected shouldBe btn
    }

    @Test
    fun shouldProperlyDeselectPreviouslySelectedButtonWhenNewOneIsSelected() {
        val oldBtn = radioButton { key = "old"; text = "old" }
        val newBtn = radioButton { key = "new"; text = "new" }
        oldBtn.isSelected = true
        newBtn.isSelected = true
        target.addComponents(oldBtn, newBtn)

        oldBtn.isSelected shouldBe false
        newBtn.isSelected shouldBe true
        target.selected shouldBe newBtn
    }

    @Test
    fun settingTheSelectionSelectsThatMemberAndClearingItUnselects() {
        val a = radioButton { +"A" }
        val b = radioButton { +"B" }
        target.addComponents(a, b)

        target.selected = a
        a.isSelected shouldBe true
        target.selected = b
        a.isSelected shouldBe false
        b.isSelected shouldBe true
        target.selected = null
        b.isSelected shouldBe false
        target.hasSelection shouldBe false
    }

    @Test
    fun theKeysMustBeUniqueAndPresent() {
        target.addComponent(radioButton { +"Same" })
        shouldThrow<IllegalArgumentException> { target.addComponent(radioButton { +"Same" }) }
        shouldThrow<IllegalArgumentException> { target.addComponent(radioButton { +"Other"; key = "same" }) }
    }

    @Test
    fun aDetachedMemberNoLongerTakesPartAndFreesItsKey() {
        val a = radioButton { +"A" }
        val b = radioButton { +"B" }
        val handle = target.addComponent(a)
        target.addComponent(b)
        a.isSelected = true

        handle.detach()
        target.hasSelection shouldBe false
        b.isSelected = true
        a.isSelected shouldBe true
        target.selected shouldBe b
        target.addComponent(radioButton { +"A" })
    }
}

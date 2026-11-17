package org.hexworks.zircon.internal.fragment.impl

import io.kotest.matchers.shouldBe
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.builder.fragment.selector
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.data.Size
import kotlin.test.Test

/**
 * The selector's label follows the selection wherever it is made: the arrows, the bound
 * property, or a changed value list.
 */
class DefaultSelectorTest {

    private val selected = "b".toProperty()
    private val values = listOf("a", "b", "c").toProperty()
    private val target = selector<String> {
        width = 10
        valuesProperty = values
        selectedProperty = selected
    } as DefaultSelector<String>

    private val label: String
        get() = (target.root.children.single { it is Label } as Label).text.trim()

    @Test
    fun startsOnTheBoundSelection() {
        label shouldBe "b"
    }

    @Test
    fun aSelectionMadeElsewhereMovesTheLabel() {
        selected.value = "c"
        label shouldBe "c"
    }

    @Test
    fun aReplacedValueKeepsTheSelectionInStep() {
        values.set(1, "B")
        selected.value = "B"
        label shouldBe "B"
        values.add(0, "first")
        label shouldBe "B"
        selected.value = "first"
        label shouldBe "first"
    }

    @Test
    fun aValueNotInTheListLeavesTheLabelAlone() {
        selected.value = "nope"
        label shouldBe "b"
    }
}

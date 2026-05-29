package org.hexworks.zircon.api.builder.fragment

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.Selector
import org.hexworks.zircon.internal.fragment.impl.DefaultSelector
import kotlin.reflect.KClass
import kotlin.test.Test

class SelectorBuilderTest {

    @Test
    fun sizeAndMinWidth() {
        for (w in 0..2) {
            val exception = shouldThrow<IllegalArgumentException> {
                buildSelector<String> {
                    width = w
                    valuesProperty = listOf("a", "b").toProperty()
                }
            }
            exception.message?.contains("minimum width") shouldBe true
        }

        val minimalMultiSelect = buildSelector<String> {
            width = 3
            valuesProperty = listOf("a", "b").toProperty()
        }
        minimalMultiSelect.shouldBeInstanceOf<Selector<*>>()

        minimalMultiSelect.root.size shouldBe Size.create(3, 1)
    }

    @Test
    fun noEmptyList() {
        val exception = shouldThrow<IllegalArgumentException> {
            buildSelector<String> {
                width = 10
                valuesProperty = listOf<String>().toProperty()
            }
        }
        exception.message?.contains("No values supplied for Selector.") shouldBe true
    }

    @Test
    fun textTooLong() {
        val multiSelect = buildSelector<String> {
            width = 7
            valueList = listOf("veryLongWord", "b")
        } as DefaultSelector
        val label = getLabel(multiSelect)
        label.text shouldBe "veryL"
    }

    @Test
    fun centeredText() {
        val multiSelect = buildSelector<String> {
            width = 7
            valueList = listOf("6")
        } as DefaultSelector
        val label = getLabel(multiSelect)
        label.text shouldBe "  6  "
    }

    @Test
    fun uncenteredText() {
        val multiSelect = buildSelector<String> {
            width = 7
            valueList = listOf("9")
            centeredText = false
        } as DefaultSelector
        val label = getLabel(multiSelect)
        label.text shouldBe "9"
    }

    @Test
    fun toStringMethod() {
        val multiSelect = buildSelector<TestClass> {
            width = 10
            valueList = listOf(TestClass(5))
            toStringMethod = TestClass::bigger
            centeredText = false
        } as DefaultSelector
        val label = getLabel(multiSelect)
        label.text shouldBe "500"
    }

    @Test
    fun clickableMultiSelect() {
        checkComponentClasses(true, 3, 0)
    }

    @Test
    fun unclickableMultiSelect() {
        checkComponentClasses(false, 2, 1)
    }

    private fun checkComponentClasses(clickable: Boolean, expectedNumberOfButtons: Int, expectedNumberOfLabels: Int) {
        val multiSelect = buildSelector<String> {
            width = 10
            valueList = listOf("one", "two", "three")
            clickableLabel = clickable
        } as DefaultSelector

        val components = multiSelect.root.children.map { it::class }
        components.filter { it.isSubclassOfButton() } shouldHaveSize expectedNumberOfButtons
        components.filter { it.isSubclassOfLabel() } shouldHaveSize expectedNumberOfLabels
    }

    private fun KClass<*>.isSubclassOfButton(): Boolean =
        this == Button::class || this.supertypes.any { it.classifier == Button::class }

    private fun KClass<*>.isSubclassOfLabel(): Boolean =
        this == Label::class || this.supertypes.any { it.classifier == Label::class }

    private fun getLabel(multiSelect: DefaultSelector<out Any>) =
        multiSelect.root.children.first { it is Label } as Label

    private data class TestClass(val number: Int) {
        fun bigger() = "${number}00"
    }
}

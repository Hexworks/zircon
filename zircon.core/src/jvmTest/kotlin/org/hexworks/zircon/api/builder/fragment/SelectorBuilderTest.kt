package org.hexworks.zircon.api.builder.fragment

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.Selector
import org.hexworks.zircon.internal.fragment.impl.DefaultSelector
import org.junit.Test
import kotlin.reflect.full.isSubclassOf

class SelectorBuilderTest {

    @Test
    fun sizeAndMinWidth() {
        for (w in 0..2) {
            assertThatThrownBy {
                buildSelector<String> {
                    width = w
                    valuesProperty = listOf("a", "b").toProperty()
                }
            }.hasMessageContaining("minimum width").isInstanceOf(IllegalArgumentException::class.java)
        }

        val minimalMultiSelect = buildSelector<String> {
            width = 3
            valuesProperty = listOf("a", "b").toProperty()
        }
        assertThat(minimalMultiSelect).isInstanceOf(Selector::class.java)

        assertThat(minimalMultiSelect.root.size).isEqualTo(Size.create(3, 1))
    }

    @Test
    fun noEmptyList() {
        assertThatThrownBy {
            buildSelector<String> {
                width = 10
                valuesProperty = listOf<String>().toProperty()
            }
        }.isInstanceOf(IllegalArgumentException::class.java).hasMessageContaining("No values supplied for Selector.")
    }

    @Test
    fun textTooLong() {
        val multiSelect = buildSelector<String> {
            width = 7
            valueList = listOf("veryLongWord", "b")
        } as DefaultSelector
        val label = getLabel(multiSelect)
        assertThat(label.text).isEqualTo("veryL")
    }

    @Test
    fun centeredText() {
        val multiSelect = buildSelector<String> {
            width = 7
            valueList = listOf("6")
        } as DefaultSelector
        val label = getLabel(multiSelect)
        assertThat(label.text).isEqualTo("  6  ")
    }

    @Test
    fun uncenteredText() {
        val multiSelect = buildSelector<String> {
            width = 7
            valueList = listOf("9")
            centeredText = false
        } as DefaultSelector
        val label = getLabel(multiSelect)
        assertThat(label.text).isEqualTo("9")
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
        assertThat(label.text).isEqualTo("500")
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
        assertThat(components.filter { it.isSubclassOf(Button::class) }).`as`("${if (clickable) "C" else "Unc"}lickable MultiSelect should have $expectedNumberOfButtons Buttons")
            .hasSize(expectedNumberOfButtons)
        assertThat(components.filter { it.isSubclassOf(Label::class) }).`as`("${if (clickable) "C" else "Unc"}lickable MultiSelect should have $expectedNumberOfLabels Labels")
            .hasSize(expectedNumberOfLabels)
    }

    private fun getLabel(multiSelect: DefaultSelector<out Any>) =
        multiSelect.root.children.first { it is Label } as Label

    private data class TestClass(val number: Int) {
        fun bigger() = "${number}00"
    }
}

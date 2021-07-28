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
                SelectorBuilder.newBuilder<String>()
                    .withWidth(w)
                    .withValues(listOf("a", "b").toProperty())
                    .build()
            }.hasMessageContaining("minimum width").isInstanceOf(IllegalArgumentException::class.java)
        }

        val minimalMultiSelect = SelectorBuilder.newBuilder<String>()
            .withWidth(3)
            .withValues(listOf("a", "b").toProperty())
            .build()
        assertThat(minimalMultiSelect).isInstanceOf(Selector::class.java)

        assertThat(minimalMultiSelect.root.size).isEqualTo(Size.create(3, 1))
    }

    @Test
    fun noEmptyList() {
        assertThatThrownBy {
            SelectorBuilder.newBuilder<String>()
                .withWidth(10)
                .withValues(listOf<String>().toProperty())
                .build()
        }.isInstanceOf(IllegalArgumentException::class.java).hasMessageContaining("No values supplied for Selector.")
    }

    @Test
    fun textTooLong() {
        val multiSelect = SelectorBuilder.newBuilder<String>()
            .withWidth(7)
            .withValueList(listOf("veryLongWord", "b"))
            .build() as DefaultSelector
        val label = getLabel(multiSelect)
        assertThat(label.text).isEqualTo("veryL")
    }

    @Test
    fun centeredText() {
        val multiSelect = SelectorBuilder.newBuilder<String>()
            .withWidth(7)
            .withValueList(listOf("6"))
            .build() as DefaultSelector
        val label = getLabel(multiSelect)
        assertThat(label.text).isEqualTo("  6  ")
    }

    @Test
    fun uncenteredText() {
        val multiSelect = SelectorBuilder.newBuilder<String>()
            .withWidth(7)
            .withValueList(listOf("9"))
            .withCenteredText(false).build() as DefaultSelector
        val label = getLabel(multiSelect)
        assertThat(label.text).isEqualTo("9")
    }

    @Test
    fun toStringMethod() {
        val multiSelect = SelectorBuilder.newBuilder<TestClass>()
            .withWidth(10)
            .withValueList(listOf(TestClass(5))).withToStringMethod(TestClass::bigger)
            .withCenteredText(false).build() as DefaultSelector
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
        val multiSelect = SelectorBuilder.newBuilder<String>()
            .withWidth(10)
            .withValueList(listOf("one", "two", "three")).withClickableLabel(clickable)
            .build() as DefaultSelector

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

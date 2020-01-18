package org.hexworks.zircon.api.builder.fragment

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.MultiSelect
import org.hexworks.zircon.internal.fragment.impl.DefaultMultiSelect
import org.junit.Test
import kotlin.reflect.full.isSubclassOf

class MultiSelectBuilderTest {
    @Test
    fun copyBuilder() {
        val builder = MultiSelectBuilder.newBuilder(10, listOf("a")).
                withCallback { _: String, _: String -> println("callback")}.
                withCenteredText(false).
                withClickableLabel(true).
                withToStringMethod { s -> s + s }

        val builderCopy = builder.createCopy()

        assertThat(builderCopy).isEqualToIgnoringGivenFields(builder, "log")
    }

    @Test
    fun sizeAndMinWidth() {
        for(w in -1..2) {
            assertThatThrownBy {
                MultiSelectBuilder.newBuilder(w, listOf("a", "b")).build()
            }.
                hasMessageContaining("minimum width").
                isInstanceOf(IllegalStateException::class.java)
        }

        val minimalMultiSelect = MultiSelectBuilder.newBuilder(3, listOf("a", "b")).build()
        assertThat(minimalMultiSelect).
                isInstanceOf(MultiSelect::class.java)

        assertThat(minimalMultiSelect.root.size).
                isEqualTo(Size.create(3, 1))
    }

    @Test
    fun noEmptyList() {
        assertThatThrownBy {
            MultiSelectBuilder.newBuilder(10, listOf()).build()
        }.
            isInstanceOf(IllegalStateException::class.java).
            hasMessageContaining("may not be empty")
    }

    @Test
    fun textTooLong() {
        val multiSelect = MultiSelectBuilder.
                newBuilder(7, listOf("veryLongWord", "b")).
                build() as DefaultMultiSelect
        val label = getLabel(multiSelect)
        assertThat(label.text).isEqualTo("veryL")
    }

    @Test
    fun centeredText() {
        val multiSelect = MultiSelectBuilder.
                newBuilder(7, listOf("6")).
                build() as DefaultMultiSelect
        val label = getLabel(multiSelect)
        assertThat(label.text).isEqualTo("  6  ")
    }

    @Test
    fun uncenteredText() {
        val multiSelect = MultiSelectBuilder.
                newBuilder(7, listOf("9")).
                withCenteredText(false).
                build() as DefaultMultiSelect
        val label = getLabel(multiSelect)
        assertThat(label.text).isEqualTo("9")
    }

    @Test
    fun toStringMethod() {
        val multiSelect = MultiSelectBuilder.
                newBuilder(10, listOf(TestClass(5))).
                withToStringMethod(TestClass::bigger).
                withCenteredText(false).
                build() as DefaultMultiSelect
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
        val multiSelect = MultiSelectBuilder.
                newBuilder(10, listOf("one", "two", "three")).
                withClickableLabel(clickable).
                build() as DefaultMultiSelect

        val components = multiSelect.root.children.map { it::class }
        assertThat(components.filter { it.isSubclassOf(Button::class) }).
                `as`("${if(clickable) "C" else "Unc"}lickable MultiSelect should have $expectedNumberOfButtons Buttons").
                hasSize(expectedNumberOfButtons)
        assertThat(components.filter { it.isSubclassOf(Label::class) }).
                `as`("${if(clickable) "C" else "Unc"}lickable MultiSelect should have $expectedNumberOfLabels Labels").
                hasSize(expectedNumberOfLabels)
    }

    private fun getLabel(multiSelect: DefaultMultiSelect<out Any>) =
            multiSelect.root.children.first { it is Label } as Label

    private data class TestClass(val number: Int) {
        fun bigger() = "${number}00"
    }
}
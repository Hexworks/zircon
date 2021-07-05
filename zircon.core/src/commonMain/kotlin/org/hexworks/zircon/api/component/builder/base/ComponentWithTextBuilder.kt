package org.hexworks.zircon.api.component.builder.base

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.builder.ComponentBuilder
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.internal.component.withNewLinesStripped

abstract class ComponentWithTextBuilder<T : Component, U : ComponentBuilder<T, U>>(
    initialRenderer: ComponentRenderer<out T>,
    initialText: String = "",
    /**
     * The space that should be reserved for additional mandatory decorations (like a list item prefix: `- `)
     */
    private val reservedSpace: Int = 0
) : BaseComponentBuilder<T, U>(initialRenderer) {

    /**
     * The text of the resulting component.
     * **Note that** anything after a new line character is stripped from the text.
     * If you want to use a component that supports multi-line text, try [TextArea].
     */
    var text: String = ""
        set(value) {
            field = value.withNewLinesStripped()
            fixContentSizeFor(text.length + reservedSpace)
        }

    init {
        text = initialText
    }

    operator fun String.unaryPlus() {
        this@ComponentWithTextBuilder.text = this
    }

    /**
     * Sets the [text] for the component that is being built and returns the builder.
     */
    @Suppress("UNCHECKED_CAST")
    fun withText(text: String): U {
        this.text = text
        return this as U
    }
}

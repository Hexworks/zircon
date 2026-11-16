package org.hexworks.zircon.api.builder.component

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.component.Input
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.extensions.isSizeUnknown
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultInput

/**
 * Builds a single-line [Input]. Without a size the field is [DEFAULT_WIDTH] columns wide.
 */
@ZirconDsl
public class InputBuilder : BaseComponentBuilder<Input>(
    initialRole = ComponentRole.INPUT,
) {

    public val textProperty: Property<String> = "".toProperty()
    public var text: String by textProperty.asDelegate()

    /**
     * The most characters the field accepts; `null` for no limit.
     */
    public var maxLength: Int? = null
        set(value) {
            require(value == null || value > 0) { "maxLength must be positive" }
            field = value
        }

    /**
     * The number of columns of the field. Shorthand for `preferredContentSize = Size(width, 1)`.
     */
    public var width: Int
        get() = contentWidth
        set(value) {
            preferredContentSize = Size(value, 1)
        }

    override fun build(): Input {
        if (preferredSize.isSizeUnknown && preferredContentSize.isSizeUnknown) {
            preferredContentSize = Size(DEFAULT_WIDTH, 1)
        }
        maxLength?.let { if (text.length > it) text = text.take(it) }
        return DefaultInput(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            textProperty = textProperty,
            maxLength = maxLength,
        ).attachListeners()
    }

    public companion object {
        public const val DEFAULT_WIDTH: Int = 10
    }
}

/**
 * Creates a new [Input] using the component builder DSL and returns it.
 */
public fun input(init: InputBuilder.() -> Unit): Input = InputBuilder().apply(init).build()

/**
 * Creates a new [Input] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Input].
 */
public fun <T : BaseContainerBuilder<*>> T.input(init: InputBuilder.() -> Unit): Input =
    buildChildFor(this, InputBuilder(), init)

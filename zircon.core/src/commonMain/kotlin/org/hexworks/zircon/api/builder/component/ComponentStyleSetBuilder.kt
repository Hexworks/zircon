package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.internal.component.impl.DefaultComponentStyleSet

/**
 * Use this to build [StyleSet]s for your [org.hexworks.zircon.api.component.Component]s.
 * They will be used accordingly when the component's state changes.
 */
@ZirconDsl
public class ComponentStyleSetBuilder : Builder<ComponentStyleSet> {

    public var defaultStyle: StyleSet = StyleSet.defaultStyle()
    public var highlightedStyle: StyleSet = StyleSet.defaultStyle()
    public var activeStyle: StyleSet = StyleSet.defaultStyle()
    public var disabledStyle: StyleSet = StyleSet.defaultStyle()
    public var focusedStyle: StyleSet = StyleSet.defaultStyle()

    override fun build(): ComponentStyleSet {
        val styles = mutableMapOf(
            ComponentState.DEFAULT to defaultStyle,
            ComponentState.HIGHLIGHTED to highlightedStyle,
            ComponentState.ACTIVE to activeStyle,
            ComponentState.DISABLED to disabledStyle,
            ComponentState.FOCUSED to focusedStyle
        )
        ComponentState.values()
            .filterNot { it == ComponentState.DEFAULT }
            .forEach {
                // this means that they didn't change the value
                // so we'll use whatever they set for default
                if (styles[it] === StyleSet.defaultStyle()) {
                    styles[it] = styles[ComponentState.DEFAULT]!!
                }
            }
        return DefaultComponentStyleSet(styles)
    }
}

/**
 * Creates a new [ComponentStyleSet] using the component builder DSL and returns it.
 */
public fun componentStyleSet(init: ComponentStyleSetBuilder.() -> Unit): ComponentStyleSet =
    ComponentStyleSetBuilder().apply(init).build()

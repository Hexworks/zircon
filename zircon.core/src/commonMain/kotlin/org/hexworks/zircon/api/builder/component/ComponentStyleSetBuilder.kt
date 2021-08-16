package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.internal.component.impl.DefaultComponentStyleSet
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmStatic

/**
 * Use this to build [StyleSet]s for your [org.hexworks.zircon.api.component.Component]s.
 * They will be used accordingly when the component's state changes.
 */
@ZirconDsl
class ComponentStyleSetBuilder private constructor(
    var defaultStyle: StyleSet = StyleSet.defaultStyle(),
    var highlightedStyle: StyleSet = StyleSet.defaultStyle(),
    var activeStyle: StyleSet = StyleSet.defaultStyle(),
    var disabledStyle: StyleSet = StyleSet.defaultStyle(),
    var focusedStyle: StyleSet = StyleSet.defaultStyle()
) : Builder<ComponentStyleSet> {

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

    fun withDefaultStyle(styleSet: StyleSet) = also {
        defaultStyle = styleSet
    }

    @Deprecated("use withHighlightedStyle instead", ReplaceWith("withHighlightedStyle(styleSet)"))
    fun withMouseOverStyle(styleSet: StyleSet) = also {
        withHighlightedStyle(styleSet)
    }

    fun withHighlightedStyle(styleSet: StyleSet) = also {
        highlightedStyle = styleSet
    }

    fun withActiveStyle(styleSet: StyleSet) = also {
        activeStyle = styleSet
    }

    fun withDisabledStyle(styleSet: StyleSet) = also {
        disabledStyle = styleSet
    }

    fun withFocusedStyle(styleSet: StyleSet) = also {
        focusedStyle = styleSet
    }

    override fun createCopy() = ComponentStyleSetBuilder(
        defaultStyle = defaultStyle,
        highlightedStyle = highlightedStyle,
        activeStyle = activeStyle,
        disabledStyle = disabledStyle,
        focusedStyle = focusedStyle
    )

    companion object {

        @JvmStatic
        fun newBuilder() = ComponentStyleSetBuilder()

    }
}

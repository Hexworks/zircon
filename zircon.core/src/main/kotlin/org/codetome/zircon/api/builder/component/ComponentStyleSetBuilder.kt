package org.codetome.zircon.api.builder.component

import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.component.ComponentState
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.internal.component.impl.DefaultComponentStyleSet

/**
 * Use this to build [StyleSet]s for your [org.codetome.zircon.api.component.Component]s.
 * They will be used accordingly when the component's state changes.
 */
data class ComponentStyleSetBuilder(
        private val styles: MutableMap<ComponentState, StyleSet> = mutableMapOf())
    : Builder<ComponentStyleSet> {

    init {
        ComponentState.values().forEach {
            styles[it] = StyleSet.defaultStyle()
        }
    }

    override fun build(): ComponentStyleSet {
        ComponentState.values()
                .filterNot { it == ComponentState.DEFAULT }
                .forEach {
                    if (styles[it] === StyleSet.defaultStyle()) {
                        styles[it] = styles[ComponentState.DEFAULT]!!
                    }
                }
        return DefaultComponentStyleSet(styles)
    }

    override fun createCopy() = copy(
            styles = styles.map { Pair(it.key, it.value) }
                    .toMap().toMutableMap())

    fun defaultStyle(styleSet: StyleSet) = also {
        styles[ComponentState.DEFAULT] = styleSet
    }

    fun mouseOverStyle(styleSet: StyleSet) = also {
        styles[ComponentState.MOUSE_OVER] = styleSet
    }

    fun activeStyle(styleSet: StyleSet) = also {
        styles[ComponentState.ACTIVE] = styleSet
    }

    fun disabledStyle(styleSet: StyleSet) = also {
        styles[ComponentState.DISABLED] = styleSet
    }

    fun focusedStyle(styleSet: StyleSet) = also {
        styles[ComponentState.FOCUSED] = styleSet
    }

    companion object {

        fun newBuilder() = ComponentStyleSetBuilder()

    }
}

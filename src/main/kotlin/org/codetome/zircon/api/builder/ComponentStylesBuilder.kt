package org.codetome.zircon.api.builder

import org.codetome.zircon.api.component.ComponentState
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.internal.component.impl.DefaultComponentStyles

/**
 * Use this to build [StyleSet]s for your [org.codetome.zircon.api.component.Component]s.
 * They will be used accordingly when the component's state changes.
 */
data class ComponentStylesBuilder(
        private val styles: MutableMap<ComponentState, StyleSet> = mutableMapOf()
) : Builder<ComponentStyles> {

    init {
        ComponentState.values().forEach {
            styles[it] = StyleSetBuilder.DEFAULT_STYLE
        }
    }

    override fun build(): ComponentStyles {
        ComponentState.values()
                .filterNot { it == ComponentState.DEFAULT }
                .forEach {
                    if (styles[it] === StyleSetBuilder.DEFAULT_STYLE) {
                        styles[it] = styles[ComponentState.DEFAULT]!!
                    }
                }
        return DefaultComponentStyles(styles)
    }

    override fun createCopy() = copy(
            styles = styles.map { Pair(it.key, it.value.toStyleSet()) }
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

        @JvmStatic
        fun newBuilder() = ComponentStylesBuilder()

        @JvmField
        val DEFAULT = ComponentStylesBuilder.newBuilder().build()
    }
}
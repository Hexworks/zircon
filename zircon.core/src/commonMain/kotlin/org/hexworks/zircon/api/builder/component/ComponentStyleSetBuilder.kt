package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.internal.component.impl.DefaultComponentStyleSet

/**
 * Use this to build [StyleSet]s for your [org.hexworks.zircon.api.component.Component]s.
 * They will be used accordingly when the component's state changes.
 */
@ZirconDsl
data class ComponentStyleSetBuilder(
    private val styles: MutableMap<ComponentState, StyleSet> = mutableMapOf()
) : Builder<ComponentStyleSet> {

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

    fun withDefaultStyle(styleSet: StyleSet) = also {
        styles[ComponentState.DEFAULT] = styleSet
    }

    fun withMouseOverStyle(styleSet: StyleSet) = also {
        styles[ComponentState.HIGHLIGHTED] = styleSet
    }

    fun withActiveStyle(styleSet: StyleSet) = also {
        styles[ComponentState.ACTIVE] = styleSet
    }

    fun withDisabledStyle(styleSet: StyleSet) = also {
        styles[ComponentState.DISABLED] = styleSet
    }

    fun withFocusedStyle(styleSet: StyleSet) = also {
        styles[ComponentState.FOCUSED] = styleSet
    }

    companion object {

        fun newBuilder() = ComponentStyleSetBuilder()

    }
}

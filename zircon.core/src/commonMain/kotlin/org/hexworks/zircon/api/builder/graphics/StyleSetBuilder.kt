package org.hexworks.zircon.api.builder.graphics

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.internal.graphics.DefaultStyleSet

/**
 * Builder used to create [StyleSet]s. Uses the default colors from
 * [Color]. Modifiers are empty by default.
 */
@ZirconDsl
class StyleSetBuilder : Builder<StyleSet> {

    var foregroundColor: Color = StyleSet.defaultStyle().foregroundColor
    var backgroundColor: Color = StyleSet.defaultStyle().backgroundColor
    var modifiers: Set<Modifier> = StyleSet.defaultStyle().modifiers

    override fun build(): StyleSet = DefaultStyleSet(
        foregroundColor = foregroundColor,
        backgroundColor = backgroundColor,
        modifiers = modifiers.toMutableSet()
    )
}

/**
 * Creates a new [StyleSetBuilder] using the builder DSL and returns it.
 */
fun styleSet(init: StyleSetBuilder.() -> Unit): StyleSet =
    StyleSetBuilder().apply(init).build()

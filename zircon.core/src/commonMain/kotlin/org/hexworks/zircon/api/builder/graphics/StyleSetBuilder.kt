package org.hexworks.zircon.api.builder.graphics

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.graphics.DefaultStyleSet

/**
 * Builder used to create [StyleSet]s. Uses the default colors from
 * [TileColor]. Modifiers are empty by default.
 */
@ZirconDsl
class StyleSetBuilder : Builder<StyleSet> {

    var foregroundColor: TileColor = StyleSet.defaultStyle().foregroundColor
    var backgroundColor: TileColor = StyleSet.defaultStyle().backgroundColor
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

package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.behavior.Styleable
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier

data class DefaultStyleable(private val initialStyle: StyleSet) : Styleable {

    override var foregroundColor = initialStyle.foregroundColor
    override var backgroundColor = initialStyle.backgroundColor
    override var modifiers = initialStyle.modifiers

    override fun enableModifiers(modifiers: Set<Modifier>) {
        this.modifiers = this.modifiers.plus(modifiers)
    }

    override fun disableModifiers(modifiers: Set<Modifier>) {
        this.modifiers = this.modifiers.minus(modifiers)
    }

    override fun clearModifiers() {
        modifiers = setOf()
    }

    override fun toStyleSet() = StyleSetBuilder.newBuilder()
            .withForegroundColor(foregroundColor)
            .withBackgroundColor(backgroundColor)
            .withModifiers(modifiers)
            .build()

    override fun setStyleFrom(source: StyleSet) {
        this.foregroundColor = source.foregroundColor
        this.backgroundColor = source.backgroundColor
        this.modifiers = source.modifiers.toSet()
    }
}

package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.config.ZirconDefaults
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.dsl.AnyContainerBuilder
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.dsl.buildFragmentFor
import org.hexworks.zircon.api.fragment.Badge
import org.hexworks.zircon.api.fragment.BadgeStatus
import org.hexworks.zircon.api.fragment.builder.ThemedFragmentBuilder
import org.hexworks.zircon.internal.fragment.impl.DefaultBadge

/**
 * Builds a [Badge]; its size follows from the text and the parts.
 */
@ZirconDsl
public class BadgeBuilder : ThemedFragmentBuilder<Badge> {

    override var position: Position = Position.ZERO
    override var theme: ComponentTheme = ZirconDefaults.current.componentTheme
    public var text: String = ""
    public var status: BadgeStatus = BadgeStatus.NONE
    public var dismissible: Boolean = false
    public var statusIcon: Char = '■'
    public var dismissIcon: Char = '✕'

    public operator fun String.unaryPlus() {
        text = this
    }

    override fun build(): Badge = DefaultBadge(
        initialText = text,
        status = status,
        dismissible = dismissible,
        statusIcon = statusIcon,
        dismissIcon = dismissIcon,
        theme = theme,
    ).also { it.root.moveTo(position) }
}

public fun badge(init: BadgeBuilder.() -> Unit): Badge = BadgeBuilder().apply(init).build()

public fun AnyContainerBuilder.badge(init: BadgeBuilder.() -> Unit): Badge = buildFragmentFor(this, BadgeBuilder(), init)

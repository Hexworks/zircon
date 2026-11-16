package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.dsl.AnyContainerBuilder
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.dsl.buildFragmentFor
import org.hexworks.zircon.api.fragment.Alert
import org.hexworks.zircon.api.fragment.AlertKind
import org.hexworks.zircon.api.fragment.builder.ThemedFragmentBuilder
import org.hexworks.zircon.internal.fragment.impl.DefaultAlert

/**
 * Builds an [Alert]: [width] is the outer width, the height follows from the wrapped [text].
 */
@ZirconDsl
public class AlertBuilder : ThemedFragmentBuilder<Alert> {

    override var position: Position = Position.ZERO
    override var theme: ComponentTheme? = null
    public var width: Int = 40
    public var kind: AlertKind = AlertKind.INFO
    public var text: String = ""

    /**
     * The glyph on the left; default depends on [kind].
     */
    public var icon: Char? = null

    public operator fun String.unaryPlus() {
        text = this
    }

    override fun build(): Alert {
        require(width >= 5) { "An alert needs at least 5 columns" }
        return DefaultAlert(
            kind = kind,
            initialText = text,
            width = width,
            icon = icon ?: DEFAULT_ICONS.getValue(kind),
            theme = theme,
        ).also { it.root.moveTo(position) }
    }

    public companion object {
        public val DEFAULT_ICONS: Map<AlertKind, Char> = mapOf(
            AlertKind.INFO to 'ⓘ',
            AlertKind.SUCCESS to '✔',
            AlertKind.ERROR to '⚠',
        )
    }
}

public fun alert(init: AlertBuilder.() -> Unit): Alert = AlertBuilder().apply(init).build()

public fun AnyContainerBuilder.alert(init: AlertBuilder.() -> Unit): Alert = buildFragmentFor(this, AlertBuilder(), init)

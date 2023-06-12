package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.builder.component.colorTheme
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.data.ComponentState

/**
 * A [ColorTheme] can be used to give a [Component] context-dependent styles.
 * The current style depends on the [ComponentState] of the [Component].
 * For each [ComponentState] there is a corresponding [ComponentStyleSet]
 * that gets applied whenever the state of the [Component] changes.
 * These states include:
 * - [ComponentState.ACTIVE]
 * - [ComponentState.DEFAULT]
 * - [ComponentState.DISABLED]
 * - [ComponentState.FOCUSED]
 * - [ComponentState.HIGHLIGHTED]
 *
 * @see ComponentState and
 * @see ComponentStyleSet for more info
 */
interface ColorTheme {

    /**
     * A unique name for this [ColorTheme].
     */
    val name: String

    /**
     * This color is typically used for the text of non-interactive components
     * that have emphasis on them (like [Header]s).
     */
    val primaryForegroundColor: TileColor

    /**
     * This color is typically used for the text of non-interactive components
     * that don't have emphasis on them (like [Paragraph]s).
     */
    val secondaryForegroundColor: TileColor

    /**
     * This color is typically used for the background of [Container]s.
     */
    val primaryBackgroundColor: TileColor

    /**
     * This color is typically used for the root container.
     */
    val secondaryBackgroundColor: TileColor

    /**
     * This color is typically used for the text and decorations of interactive components.
     */
    val accentColor: TileColor

    val isUnknown: Boolean
        get() = this === UNKNOWN

    val isNotUnknown: Boolean
        get() = isUnknown.not()

    /**
     * Creates a [ComponentStyleSet] which is intended to be used as a default
     * for interactive components (eg: [Button]s, [ToggleButton]s and so on).
     */
    fun toInteractiveStyle(): ComponentStyleSet = componentStyleSet {
        defaultStyle = styleSet {
            foregroundColor = accentColor
            backgroundColor = TileColor.transparent()
        }
        highlightedStyle = styleSet {
            foregroundColor = secondaryForegroundColor
            backgroundColor = accentColor
        }
        focusedStyle = styleSet {
            foregroundColor = primaryBackgroundColor
            backgroundColor = accentColor
        }
        activeStyle = styleSet {
            foregroundColor = primaryForegroundColor
            backgroundColor = accentColor
        }
        disabledStyle = styleSet {
            foregroundColor = accentColor.desaturate(.85).darkenByPercent(.1)
            backgroundColor = TileColor.transparent()
        }
    }

    /**
     * Creates a [ComponentStyleSet] which is intended to be used as a default
     * for containers (eg: [Panel]s, [HBox]es, [VBox]es.
     */
    fun toContainerStyle(): ComponentStyleSet = componentStyleSet {
        defaultStyle = styleSet {
            foregroundColor = secondaryForegroundColor
            backgroundColor = primaryBackgroundColor
        }
        disabledStyle = styleSet {
            foregroundColor = secondaryForegroundColor.desaturate(.85).darkenByPercent(.1)
            backgroundColor = primaryBackgroundColor.desaturate(.85).darkenByPercent(.1)
        }
    }

    /**
     * Creates a [ComponentStyleSet] which is intended to be used as a default
     * for emphasized content (eg: [Header]s).
     */
    fun toPrimaryContentStyle(): ComponentStyleSet = componentStyleSet {
        defaultStyle = styleSet {
            foregroundColor = primaryForegroundColor
            backgroundColor = TileColor.transparent()
        }
        disabledStyle = styleSet {
            foregroundColor = primaryForegroundColor.desaturate(.85).darkenByPercent(.1)
            backgroundColor = TileColor.transparent()
        }
    }

    /**
     * Creates a [ComponentStyleSet] which is intended to be used as a default
     * for non-emphasized content (eg: [Label]s, [Paragraph]s, and so on).
     */
    fun toSecondaryContentStyle(): ComponentStyleSet = componentStyleSet {
        defaultStyle = styleSet {
            foregroundColor = secondaryForegroundColor
            backgroundColor = TileColor.transparent()
        }
        disabledStyle = styleSet {
            foregroundColor = secondaryForegroundColor.desaturate(.85).darkenByPercent(.1)
            backgroundColor = TileColor.transparent()
        }
    }

    companion object {

        private val UNKNOWN = colorTheme {
            name = "unknown"
        }

        fun unknown() = UNKNOWN

    }
}

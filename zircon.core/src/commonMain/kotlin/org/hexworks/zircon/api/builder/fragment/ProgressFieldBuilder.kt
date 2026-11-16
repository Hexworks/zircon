package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.dsl.AnyContainerBuilder
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.dsl.buildFragmentFor
import org.hexworks.zircon.api.fragment.ProgressField
import org.hexworks.zircon.api.fragment.builder.ThemedFragmentBuilder
import org.hexworks.zircon.internal.fragment.impl.DefaultProgressField

/**
 * Builds a [ProgressField]: a [label] and the percentage above a progress bar of [width]
 * columns (outer).
 */
@ZirconDsl
public class ProgressFieldBuilder : ThemedFragmentBuilder<ProgressField> {

    override var position: Position = Position.ZERO
    override var theme: ComponentTheme? = null
    public var width: Int = 40
    public var label: String = ""
    public var range: Int = 100
    public var progress: Double = 0.0

    /**
     * Whether the percentage is shown at the right end of the header row.
     */
    public var showPercent: Boolean = true

    override fun build(): ProgressField {
        require(width >= 6) { "A progress field needs at least 6 columns" }
        return DefaultProgressField(
            width = width,
            label = label,
            range = range,
            initialProgress = progress,
            showPercent = showPercent,
            theme = theme,
        ).also { it.root.moveTo(position) }
    }
}

public fun progressField(init: ProgressFieldBuilder.() -> Unit): ProgressField = ProgressFieldBuilder().apply(init).build()

public fun AnyContainerBuilder.progressField(init: ProgressFieldBuilder.() -> Unit): ProgressField =
    buildFragmentFor(this, ProgressFieldBuilder(), init)

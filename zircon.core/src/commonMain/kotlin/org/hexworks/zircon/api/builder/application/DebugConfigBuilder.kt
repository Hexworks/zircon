package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.application.DebugConfig
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.dsl.ZirconDsl

/**
 * Builder for [DebugConfig]s.
 * @see DebugConfig for defaults
 */
@ZirconDsl
public class DebugConfigBuilder : Builder<DebugConfig> {

    /**
     * if `true` no bounds check will be performed when adding/moving components
     */
    public var relaxBoundsCheck: Boolean = false

    /**
     * Draws the cell grid over the tiles; handy when positioning components.
     */
    public var displayGrid: Boolean = false

    override fun build(): DebugConfig = DebugConfig(
        relaxBoundsCheck = relaxBoundsCheck,
        displayGrid = displayGrid,
    )

    public companion object {
        public val DEFAULT_DEBUG_CONFIG: DebugConfig = DebugConfigBuilder().build()
    }
}

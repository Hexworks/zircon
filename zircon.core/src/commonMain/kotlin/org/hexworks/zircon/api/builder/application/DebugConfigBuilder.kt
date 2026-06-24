package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.application.DebugConfig
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.dsl.ZirconDsl

/**
 * Builder for [DebugConfig]s.
 * @see DebugConfig for defaults
 */
@ZirconDsl
class DebugConfigBuilder : Builder<DebugConfig> {

    /**
     * if `true` no bounds check will be performed when adding/moving components
     */
    var relaxBoundsCheck: Boolean = false

    override fun build() = DebugConfig(
        relaxBoundsCheck = relaxBoundsCheck
    )

    companion object {
        val DEFAULT_DEBUG_CONFIG = DebugConfigBuilder().build()
    }
}

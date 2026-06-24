package org.hexworks.zircon.api.application

import org.hexworks.zircon.api.builder.application.DebugConfigBuilder

/**
 * Contains the configuration to be used when debug mode is enabled.
 */
class DebugConfig internal constructor(
    /**
     * if `true` no bounds check will be performed when adding/moving components
     */
    val relaxBoundsCheck: Boolean
) {

    companion object {

        val DEFAULT_DEBUG_CONFIG = DebugConfigBuilder().build()
    }
}

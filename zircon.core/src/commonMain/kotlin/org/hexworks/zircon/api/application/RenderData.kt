package org.hexworks.zircon.api.application

/**
 * Contains metadata for a render step in [Application].
 */
public data class RenderData(
    /**
     * The current UNIX timestamp in milliseconds.
     */
    val timestampMs: Long
)

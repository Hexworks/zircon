package org.hexworks.zircon.internal.event

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.events.api.EventScope

/**
 * This is the [EventScope] that Zircon uses.
 */
data class ZirconScope(
    private val id: UUID = UUID.randomUUID()
) : EventScope

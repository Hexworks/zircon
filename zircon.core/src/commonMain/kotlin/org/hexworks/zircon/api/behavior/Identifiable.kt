package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.core.api.UUID

/**
 * Represents an object that has a unique UUID.
 */
interface Identifiable {

    val id: UUID
}

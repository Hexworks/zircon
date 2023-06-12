package org.hexworks.zircon.internal.behavior

import org.hexworks.cobalt.core.api.UUID

/**
 * Represents any object, which has a unique UUID.
 */
interface Identifiable {

    val id: UUID
}

package org.hexworks.zircon.internal.behavior

import org.hexworks.cobalt.core.api.UUID

/**
 * Represents any object, which has an unique UUID.
 */
interface Identifiable {

    val id: UUID
}

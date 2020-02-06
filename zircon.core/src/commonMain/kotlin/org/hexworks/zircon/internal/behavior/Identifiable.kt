package org.hexworks.zircon.internal.behavior

import org.hexworks.cobalt.core.api.Identifier

/**
 * Represents any object, which has an unique identifier.
 */
interface Identifiable {

    val id: Identifier
}

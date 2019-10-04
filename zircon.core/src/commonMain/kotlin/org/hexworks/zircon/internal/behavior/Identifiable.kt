package org.hexworks.zircon.internal.behavior

import org.hexworks.cobalt.Identifier

/**
 * Represents any object, which has an unique identifier.
 */
interface Identifiable {

    val id: Identifier
}

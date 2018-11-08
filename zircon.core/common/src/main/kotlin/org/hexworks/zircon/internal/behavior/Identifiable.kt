package org.hexworks.zircon.internal.behavior

import org.hexworks.cobalt.datatypes.Identifier

/**
 * Represents any object, which has an unique identifier.
 */
interface Identifiable {

    val id: Identifier
}

package org.hexworks.zircon.internal.behavior

import org.hexworks.zircon.api.util.Identifier

/**
 * Represents any object, which has an unique identifier.
 */
interface Identifiable {

    val id: Identifier
}

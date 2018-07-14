package org.codetome.zircon.internal.behavior

import org.codetome.zircon.api.util.Identifier

/**
 * Represents any object, which has an unique identifier.
 */
interface Identifiable {

    fun getId(): Identifier
}

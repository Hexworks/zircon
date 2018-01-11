package org.codetome.zircon.internal.behavior

import java.util.*

/**
 * Represents any object, which has an unique identifier.
 */
interface Identifiable {

    fun getId(): UUID
}
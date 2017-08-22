package org.codetome.zircon.internal.behavior

import java.util.*

/**
 * Represents a component in zircon, which has an unique identifier.
 */
interface Identifiable {

    fun getId(): UUID
}
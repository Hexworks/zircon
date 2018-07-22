package org.codetome.zircon.platform.factory

import org.codetome.zircon.api.util.Identifier

expect object IdentifierFactory {

    fun randomIdentifier(): Identifier

    fun fromString(str: String): Identifier
}

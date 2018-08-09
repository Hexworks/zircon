package org.hexworks.zircon.platform.factory

import org.hexworks.zircon.api.util.Identifier

expect object IdentifierFactory {

    fun randomIdentifier(): Identifier

    fun fromString(str: String): Identifier

}

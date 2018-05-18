package org.codetome.zircon.internal.util

expect object IdentifierFactory {

    fun randomIdentifier(): Identifier

    fun fromString(str: String): Identifier
}

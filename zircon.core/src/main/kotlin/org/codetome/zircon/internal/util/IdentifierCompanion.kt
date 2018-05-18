package org.codetome.zircon.internal.util

interface IdentifierCompanion {

    fun randomIdentifier(): Identifier = IdentifierFactory.randomIdentifier()

    fun fromString(str: String): Identifier = IdentifierFactory.fromString(str)
}

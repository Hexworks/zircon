package org.codetome.zircon.internal.util

import org.codetome.zircon.internal.multiplatform.factory.IdentifierFactory

interface IdentifierCompanion {

    fun randomIdentifier(): Identifier = IdentifierFactory.randomIdentifier()

    fun fromString(str: String): Identifier = IdentifierFactory.fromString(str)
}

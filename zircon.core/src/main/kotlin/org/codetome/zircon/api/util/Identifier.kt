package org.codetome.zircon.api.util

import org.codetome.zircon.platform.factory.IdentifierFactory

interface Identifier : Comparable<Identifier> {

    companion object {

        fun randomIdentifier(): Identifier = IdentifierFactory.randomIdentifier()

        fun fromString(str: String): Identifier = IdentifierFactory.fromString(str)
    }
}

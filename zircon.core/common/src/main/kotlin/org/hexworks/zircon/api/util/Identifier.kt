package org.hexworks.zircon.api.util

import org.hexworks.zircon.platform.factory.IdentifierFactory

interface Identifier : Comparable<Identifier> {

    companion object {

        fun randomIdentifier(): Identifier = IdentifierFactory.randomIdentifier()

        fun fromString(str: String): Identifier = IdentifierFactory.fromString(str)
    }
}

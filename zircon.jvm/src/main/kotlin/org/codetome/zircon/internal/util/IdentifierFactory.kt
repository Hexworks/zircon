package org.codetome.zircon.internal.util

import java.util.*

actual object IdentifierFactory {
    actual fun randomIdentifier(): Identifier {
        return UUIDIdentifier()
    }

    actual fun fromString(str: String): Identifier {
        return UUIDIdentifier(UUID.fromString(str))
    }

}

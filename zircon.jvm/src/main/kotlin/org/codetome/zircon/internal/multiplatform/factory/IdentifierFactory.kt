package org.codetome.zircon.internal.multiplatform.factory

import org.codetome.zircon.internal.multiplatform.api.Identifier
import org.codetome.zircon.internal.multiplatform.impl.UUIDIdentifier
import java.util.*

actual object IdentifierFactory {
    actual fun randomIdentifier(): Identifier {
        return UUIDIdentifier()
    }

    actual fun fromString(str: String): Identifier {
        return UUIDIdentifier(UUID.fromString(str))
    }

}

package org.codetome.zircon.platform.factory

import org.codetome.zircon.api.util.Identifier
import org.codetome.zircon.api.util.DefaultIdentifier
import java.util.*

actual object IdentifierFactory {

    actual fun randomIdentifier(): Identifier {
        return DefaultIdentifier()
    }

    actual fun fromString(str: String): Identifier {
        return DefaultIdentifier(UUID.fromString(str))
    }

}

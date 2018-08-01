package org.codetome.zircon.platform.factory

import org.codetome.zircon.api.util.Identifier
import org.codetome.zircon.api.util.DefaultIdentifier
import java.util.*

object IdentifierFactory {
    fun randomIdentifier(): Identifier {
        return DefaultIdentifier()
    }

    fun fromString(str: String): Identifier {
        return DefaultIdentifier(UUID.fromString(str))
    }

}

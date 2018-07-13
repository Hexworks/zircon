package org.codetome.zircon.internal.multiplatform.impl

import org.codetome.zircon.internal.multiplatform.api.Identifier
import java.util.*

class UUIDIdentifier(private val backend: UUID = UUID.randomUUID()) : Identifier {

    override fun compareTo(other: Identifier): Int {
        return backend.compareTo(fetchBackend(other))
    }

    override fun toString() = backend.toString()

    override fun equals(other: Any?) = backend == fetchBackend(other)

    override fun hashCode() = backend.hashCode()

    private fun fetchBackend(id: Any?): UUID = (id as? UUIDIdentifier?)?.backend ?: throw IllegalArgumentException(
            "Can't compare an UUIDIdentifier with an Identifier of a different class.")
}

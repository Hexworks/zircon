package org.codetome.zircon.internal.event

import java.util.*

class Subscription<T : Any>(val keys: Set<String>,
                            val callback: (Event<T>) -> Unit,
                            val dataType: Class<T>,
                            val eventType: EventType,
                            val id: UUID = UUID.randomUUID()) {

    override fun toString(): String {
        return "Subscription(keys=$keys, dataType=$dataType)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Subscription<*>
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
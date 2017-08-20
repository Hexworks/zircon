package org.codetome.zircon.event

data class Event<out T>(val data: T, val type: EventType, val keys: Set<String>)
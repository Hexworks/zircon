package org.codetome.zircon.internal.event

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.concurrent.atomic.AtomicReference

class EventBusTest {

    val event = AtomicReference<Event<Obj>>()

    @Test
    fun shouldBeProperlyNotifiedWhenSubscribedEventIsEmitted() {
        EventBus.subscribe(EventType.Draw, this::callback, KEYS)

        EventBus.emit(EventType.Draw, OBJ, KEYS)

        assertThat(event.get()).isEqualToComparingFieldByField(EVENT)
    }

    private fun callback(event: Event<Obj>) {
        this.event.set(event)
    }

    data class Obj(val str: String)

    companion object {
        val KEYS = setOf("key")
        val OBJ = Obj("foo")
        val EVENT = Event(OBJ, EventType.Draw, KEYS)
    }
}
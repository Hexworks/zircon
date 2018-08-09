package org.hexworks.zircon.api.event

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.internal.event.InternalEvent
import org.junit.Test

class SubscriptionTest {

    @Test
    fun shouldBeEqualToOtherWhenIdIsTheSame() {
        val subscription = EventBus.subscribe<InternalEvent.Input> {}

        assertThat(subscription).isEqualTo(subscription)
    }

    @Test
    fun shouldNotBeEqualToOtherWhenIdIsNotTheSame() {
        val subscription = EventBus.subscribe<InternalEvent.Input> {}
        val other = EventBus.subscribe<InternalEvent.Input> {}

        assertThat(subscription).isNotEqualTo(other)
    }

    @Test
    fun hashCodeShouldBePure() {
        val subscription = EventBus.subscribe<InternalEvent.Input> {}

        assertThat(subscription.hashCode()).isEqualTo(subscription.hashCode())
    }
}

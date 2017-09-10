package org.codetome.zircon.internal.event

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SubscriptionTest {

    @Test
    fun shouldBeEqualToOtherWhenIdIsTheSame() {
        val subscription = EventBus.subscribe(EventType.Input, {})

        assertThat(subscription).isEqualTo(subscription)
    }

    @Test
    fun shouldNotBeEqualToOtherWhenIdIsNotTheSame() {
        val subscription = EventBus.subscribe(EventType.Input, {})
        val other = EventBus.subscribe(EventType.Input, {})

        assertThat(subscription).isNotEqualTo(other)
    }

    @Test
    fun hashCodeShouldBePure() {
        val subscription = EventBus.subscribe(EventType.Input, {})

        assertThat(subscription.hashCode()).isEqualTo(subscription.hashCode())
    }
}
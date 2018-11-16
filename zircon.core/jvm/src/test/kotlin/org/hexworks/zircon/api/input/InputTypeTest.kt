package org.hexworks.zircon.api.input

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.events.api.subscribe
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconScope
import org.junit.Test
import java.util.concurrent.atomic.AtomicReference

class InputTypeTest {

    @Test
    fun test() {
        InputType.values().forEach { inputType ->
            val result = AtomicReference<Input>()
            val subscription = Zircon.eventBus.subscribe<ZirconEvent.Input>(ZirconScope) { (input) ->
                result.set(input)
            }
            Zircon.eventBus.publish(
                    event = ZirconEvent.Input(KeyStroke(
                            character = ' ',
                            type = inputType)),
                    eventScope = ZirconScope)
            assertThat(result.get().asKeyStroke().get().inputType())
                    .isEqualTo(inputType)
            subscription.cancel()
        }
    }


}

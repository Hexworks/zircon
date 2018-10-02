package org.hexworks.zircon.api.input

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.internal.event.ZirconEvent
import org.junit.Test
import java.util.concurrent.atomic.AtomicReference

class InputTypeTest {

    @Test
    fun test() {
        InputType.values().forEach { inputType ->
            val result = AtomicReference<Input>()
            val subscription = EventBus.subscribe<ZirconEvent.Input> { (input) ->
                result.set(input)
            }
            EventBus.broadcast(ZirconEvent.Input(KeyStroke(
                    character = ' ',
                    type = inputType)))
            assertThat(result.get().asKeyStroke().get().inputType())
                    .isEqualTo(inputType)
            subscription.cancel()
        }
    }


}

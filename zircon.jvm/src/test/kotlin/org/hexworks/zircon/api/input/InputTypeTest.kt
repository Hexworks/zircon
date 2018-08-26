package org.hexworks.zircon.api.input

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.internal.event.ZirconEvent
import org.junit.Test

class InputTypeTest {

    @Test
    fun test() {
        InputType.values().forEach {
            val inputs = mutableListOf<Input>()
            EventBus.subscribe<ZirconEvent.Input> { (input) ->
                inputs.add(input)
            }
            EventBus.broadcast(ZirconEvent.Input(KeyStroke(
                    character = ' ',
                    type = it)))
            assertThat(inputs.map { it.getInputType() }.first())
                    .isEqualTo(it)
        }
    }


}

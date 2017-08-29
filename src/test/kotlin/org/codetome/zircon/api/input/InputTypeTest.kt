package org.codetome.zircon.api.input

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.junit.Test

class InputTypeTest {

    @Test
    fun test() {
        InputType.values().forEach {
            val inputs = mutableListOf<Input>()
            EventBus.subscribe<Input>(EventType.Input, { (input) ->
                inputs.add(input)
            })
            EventBus.emit(EventType.Input, KeyStroke(
                    character = ' ',
                    type = it
            ))
            assertThat(inputs.map { it.getInputType() }.first())
                    .isEqualTo(it)
        }
    }


}
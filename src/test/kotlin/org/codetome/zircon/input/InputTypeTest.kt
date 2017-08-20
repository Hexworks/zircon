package org.codetome.zircon.input

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.event.EventBus
import org.codetome.zircon.event.EventType
import org.junit.Test

class InputTypeTest {

    @Test
    fun test() {
        InputType.values().forEach {
            val inputs = mutableListOf<Input>()
            EventBus.subscribe<Input>(EventType.INPUT, { (input) ->
                inputs.add(input)
            })
            EventBus.emit(EventType.INPUT, KeyStroke(
                    character = ' ',
                    it = it
            ))
            assertThat(inputs.map { it.getInputType() }.first())
                    .isEqualTo(it)
        }
    }


}
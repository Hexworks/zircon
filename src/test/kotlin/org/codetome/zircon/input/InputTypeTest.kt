package org.codetome.zircon.input

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.terminal.virtual.VirtualTerminal
import org.junit.Test

class InputTypeTest {

    @Test
    fun test() {
        val terminal = VirtualTerminal()
        InputType.values().forEach {
            terminal.addInput(KeyStroke(
                    character = ' ',
                    it = it
            ))
            assertThat(terminal.pollInput().get().getInputType())
                    .isEqualTo(it)
        }
    }


}
package org.codetome.zircon.behavior

import org.codetome.zircon.input.Input
import java.util.function.Consumer

interface InputEmitter {

    fun subscribe(inputCallback: Consumer<Input>)
}
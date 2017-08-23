package org.codetome.zircon.api.component

import org.codetome.zircon.api.input.MouseAction
import java.util.function.Consumer

interface Button : Component {

    fun getText(): String

    fun onMousePressed(callback: Consumer<MouseAction>)

    fun onMouseReleased(callback: Consumer<MouseAction>)
}
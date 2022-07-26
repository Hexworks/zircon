package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.examples.base.TwoColumnComponentExample

object ComponentExampleTemplate : TwoColumnComponentExample() {

    @JvmStatic
    fun main(args: Array<String>) {
        ComponentExampleTemplate.show("Padding Example")
    }

    override fun build(box: VBox) {
        TODO("Implement me")
    }
}

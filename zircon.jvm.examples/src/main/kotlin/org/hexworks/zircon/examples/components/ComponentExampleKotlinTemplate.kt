package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.examples.base.TwoColumnComponentExampleKotlin

class ComponentExampleKotlinTemplate : TwoColumnComponentExampleKotlin() {

    override fun build(box: VBox) {

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            ComponentExampleKotlinTemplate().show("Padding Example")
        }
    }
}

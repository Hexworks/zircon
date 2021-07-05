package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position.Companion.create
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.buildPanel
import org.hexworks.zircon.api.dsl.component.button
import org.hexworks.zircon.api.dsl.component.panel
import org.hexworks.zircon.api.dsl.component.plus
import org.hexworks.zircon.examples.base.OneColumnComponentExampleKotlin
import kotlin.concurrent.thread

class ComponentMoveExampleKotlin : OneColumnComponentExampleKotlin() {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            ComponentMoveExampleKotlin().show("Moving a Component")
        }
    }

    lateinit var innerPanel: Panel

    override fun build(box: VBox) {

        box + buildPanel {
            preferredSize = Size.create(20, 10)
            decoration = box()

            innerPanel = panel {
                preferredSize = Size.create(10, 5)
                decoration = box()

                button {
                    text = "Foo"
                    position = Position.create(1, 1)
                }
            }
        }.apply {
            thread {
                Thread.sleep(2000)
                moveBy(create(5, 5))
                Thread.sleep(2000)
                innerPanel.moveBy(create(2, 2))
            }
        }
    }
}

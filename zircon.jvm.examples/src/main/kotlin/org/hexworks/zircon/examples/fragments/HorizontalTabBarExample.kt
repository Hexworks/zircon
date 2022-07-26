package org.hexworks.zircon.examples.fragments

import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.buildHbox
import org.hexworks.zircon.api.dsl.fragment.buildHorizontalTabBar
import org.hexworks.zircon.examples.base.ComponentExample
import org.hexworks.zircon.examples.base.OneColumnComponentExample
import org.hexworks.zircon.examples.components.ButtonsExample
import org.hexworks.zircon.examples.components.DataBindingExample
import org.hexworks.zircon.examples.components.MarginExample

object HorizontalTabBarExample : OneColumnComponentExample() {

    @JvmStatic
    fun main(args: Array<String>) {
        HorizontalTabBarExample.show("Horizontal Tab Bar")
    }

    override fun build(box: VBox) {
        box.addFragment(
            buildHorizontalTabBar {
                size = box.contentSize
                defaultSelected = "buttons"
                val tabContentSize = box.contentSize.withRelativeHeight(-3)
                tab {
                    key = "buttons"
                    label = "Buttons"
                    content = ButtonsExample.wrap(tabContentSize)
                }
                tab {
                    key = "bindings"
                    label = "Bindings"
                    content = DataBindingExample.wrap(tabContentSize)
                }
                tab {
                    key = "margins"
                    label = "Margins"
                    content = MarginExample().wrap(tabContentSize)
                }
            }
        )
    }

    private fun ComponentExample.wrap(size: Size): HBox {
        val example = this
        return buildHbox {
            preferredSize = size
        }.apply {
            example.addExamples(this)
        }
    }


}













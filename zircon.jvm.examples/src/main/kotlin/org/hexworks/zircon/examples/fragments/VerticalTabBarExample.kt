package org.hexworks.zircon.examples.fragments

import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.buildHbox
import org.hexworks.zircon.api.dsl.fragment.buildVerticalTabBar
import org.hexworks.zircon.examples.base.ComponentExample
import org.hexworks.zircon.examples.base.OneColumnComponentExample
import org.hexworks.zircon.examples.components.ButtonsExample
import org.hexworks.zircon.examples.components.DataBindingExample
import org.hexworks.zircon.examples.components.MarginExample

object VerticalTabBarExample : OneColumnComponentExample() {

    @JvmStatic
    fun main(args: Array<String>) {
        VerticalTabBarExample.show("Vertical Tab Bar")
    }

    override fun build(box: VBox) {
        val tabContentSize = box.contentSize.withRelativeWidth(-10)
        box.addFragment(
            buildVerticalTabBar {
                size = box.contentSize
                tabWidth = 10
                defaultSelected = "buttons"
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













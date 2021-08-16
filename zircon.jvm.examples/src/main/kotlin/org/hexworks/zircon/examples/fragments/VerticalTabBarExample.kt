package org.hexworks.zircon.examples.fragments

import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.buildHbox
import org.hexworks.zircon.api.dsl.fragment.buildVerticalTabBar
import org.hexworks.zircon.examples.base.ComponentExampleKotlin
import org.hexworks.zircon.examples.base.OneColumnComponentExampleKotlin
import org.hexworks.zircon.examples.components.ButtonsExampleKotlin
import org.hexworks.zircon.examples.components.DataBindingExampleKotlin
import org.hexworks.zircon.examples.components.MarginExampleKotlin

class VerticalTabBarExample : OneColumnComponentExampleKotlin() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            VerticalTabBarExample().show("Vertical Tab Bar")
        }
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
                    content = ButtonsExampleKotlin().wrap(tabContentSize)
                }
                tab {
                    key = "bindings"
                    label = "Bindings"
                    content = DataBindingExampleKotlin().wrap(tabContentSize)
                }
                tab {
                    key = "margins"
                    label = "Margins"
                    content = MarginExampleKotlin().wrap(tabContentSize)
                }
            }
        )
    }

    private fun ComponentExampleKotlin.wrap(size: Size): HBox {
        val example = this
        return buildHbox {
            preferredSize = size
        }.apply {
            example.addExamples(this)
        }
    }

}













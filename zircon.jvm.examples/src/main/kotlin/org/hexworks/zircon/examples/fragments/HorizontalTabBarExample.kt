package org.hexworks.zircon.examples.fragments

import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.buildHbox
import org.hexworks.zircon.api.dsl.fragment.buildHorizontalTabBar
import org.hexworks.zircon.examples.base.ComponentExampleKotlin
import org.hexworks.zircon.examples.base.OneColumnComponentExampleKotlin
import org.hexworks.zircon.examples.components.ButtonsExampleKotlin
import org.hexworks.zircon.examples.components.DataBindingExampleKotlin
import org.hexworks.zircon.examples.components.MarginExampleKotlin

class HorizontalTabBarExample : OneColumnComponentExampleKotlin() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            HorizontalTabBarExample().show("Horizontal Tab Bar")
        }
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













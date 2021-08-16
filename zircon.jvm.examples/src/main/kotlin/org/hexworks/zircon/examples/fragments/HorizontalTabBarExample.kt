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
import org.hexworks.zircon.internal.fragment.impl.DefaultHorizontalTabBar

class HorizontalTabBarExample : OneColumnComponentExampleKotlin() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            HorizontalTabBarExample().show("Vertical Tab Bar")
        }
    }

    override fun build(box: VBox) {
        val tabContentSize = box.contentSize.withRelativeHeight(-3)
        box.addFragment(
            buildHorizontalTabBar {
                size = box.contentSize
                defaultSelected = "buttons"
                tab {
                    key = "buttons"
                    label = "Buttons"
                    size = tabContentSize
                    content = ButtonsExampleKotlin().wrapWithHbox()
                }
                tab {
                    key = "bindings"
                    label = "Bindings"
                    size = tabContentSize
                    content = DataBindingExampleKotlin().wrapWithHbox()
                }
                tab {
                    key = "margins"
                    label = "Margins"
                    size = tabContentSize
                    content = MarginExampleKotlin().wrapWithHbox()
                }
            }
        )
    }

    private fun ComponentExampleKotlin.wrapWithHbox(): HBox {
        val example = this
        return buildHbox {
            preferredSize = size
        }.apply {
            example.addExamples(this)
        }
    }


}













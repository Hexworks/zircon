package org.hexworks.zircon.examples.fragments

import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.buildHbox
import org.hexworks.zircon.examples.base.ComponentExampleKotlin
import org.hexworks.zircon.examples.base.OneColumnComponentExampleKotlin
import org.hexworks.zircon.examples.components.ButtonsExampleKotlin
import org.hexworks.zircon.examples.components.DataBindingExampleKotlin
import org.hexworks.zircon.examples.components.MarginExampleKotlin
import org.hexworks.zircon.internal.fragment.impl.DefaultVerticalTabBar

class VerticalTabBarExample : OneColumnComponentExampleKotlin() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            VerticalTabBarExample().show("Vertical Tab Bar")
        }
    }

    override fun build(box: VBox) {
        val contentSize = box.contentSize.withRelativeWidth(-10)
        val barSize = box.contentSize.withWidth(10)
        box.addFragment(
            DefaultVerticalTabBar(
                contentSize = contentSize,
                barSize = barSize,
                defaultSelected = "Buttons",
                tabs = mapOf(
                    buildTab(
                        label = "Buttons",
                        name = "Buttons Tab",
                        size = contentSize,
                        example = ButtonsExampleKotlin()
                    ),
                    buildTab(
                        label = "Bindings",
                        name = "Bindings Tab",
                        size = contentSize,
                        example = DataBindingExampleKotlin()
                    ),
                    buildTab(
                        label = "Margins",
                        name = "Margins Tab",
                        size = contentSize,
                        example = MarginExampleKotlin()
                    )
                )
            )
        )
    }

    private fun buildTab(
        label: String,
        name: String,
        size: Size,
        example: ComponentExampleKotlin
    ) = label to buildHbox {
        this.name = name
        preferredSize = size
    }.apply {
        example.addExamples(this)
    }

}













package org.hexworks.zircon.examples.fragments

import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.buildHbox
import org.hexworks.zircon.examples.base.ComponentExampleKotlin
import org.hexworks.zircon.examples.base.OneColumnComponentExampleKotlin
import org.hexworks.zircon.examples.components.ButtonsExampleKotlin
import org.hexworks.zircon.examples.components.DataBindingExampleKotlin
import org.hexworks.zircon.examples.components.MarginExampleKotlin
import org.hexworks.zircon.internal.fragment.impl.DefaultHorizontalTabBar
import org.hexworks.zircon.internal.fragment.impl.DefaultVerticalTabBar
import org.hexworks.zircon.internal.fragment.impl.TabMetadata
import kotlin.math.sign

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
            DefaultHorizontalTabBar(
                size = box.contentSize,
                tabWidth = 10,
                defaultSelected = "buttons",
                tabs = listOf(
                    buildTab(
                        key = "buttons",
                        label = "Buttons",
                        size = tabContentSize,
                        example = ButtonsExampleKotlin()
                    ),
                    buildTab(
                        key = "bindings",
                        label = "Bindings",
                        size = tabContentSize,
                        example = DataBindingExampleKotlin()
                    ),
                    buildTab(
                        key = "margins",
                        label = "Margins",
                        size = tabContentSize,
                        example = MarginExampleKotlin()
                    )
                )
            )
        )
    }

    private fun buildTab(
        key: String,
        label: String,
        size: Size,
        example: ComponentExampleKotlin
    ) = TabMetadata(
        key = key,
        label = label,
        content = buildHbox {
            preferredSize = size
        }.apply {
            example.addExamples(this)
        }
    )


}













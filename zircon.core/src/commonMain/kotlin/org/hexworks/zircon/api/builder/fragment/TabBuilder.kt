package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.fragment.impl.DefaultTab
import org.hexworks.zircon.internal.fragment.impl.TabData
import kotlin.jvm.JvmStatic


@ZirconDsl
class TabBuilder : FragmentBuilder<TabData>, Builder<TabData> {

    override var position: Position
        get() = error("Can't get the position of a tab")
        set(_) = error("Can't set the position of a tab")

    var key: String? = null
    var label: String? = null
    var content: Component? = null

    internal var width: Int? = null

    private val calculatedWidth: Int
        get() = label?.length?.plus(2) ?: 3

    override fun build(): TabData {
        require(key != null && key!!.isNotBlank()) {
            "A Tab must have a non-blank key."
        }
        require(content != null) {
            "A tab must have content to show when clicked"
        }
        require(label !== null) {
            "A tab must have a label"
        }
        return TabData(
            tab = DefaultTab(
                key = key!!,
                initialText = label!!,
                width = width ?: calculatedWidth
            ),
            content = content!!
        )
    }
}

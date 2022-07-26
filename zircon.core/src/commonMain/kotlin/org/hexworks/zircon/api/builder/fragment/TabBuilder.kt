package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.fragment.impl.DefaultTab
import org.hexworks.zircon.internal.fragment.impl.TabData
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic


@ZirconDsl
class TabBuilder private constructor(
    var key: String? = null,
    var label: String? = null,
    var content: Component? = null,
    @JvmSynthetic
    internal var width: Int? = null,
) : FragmentBuilder<TabData, TabBuilder>, Builder<TabData> {

    private val calculatedWidth: Int
        get() = label?.length?.plus(2) ?: 3

    fun withKey(key: String) = also {
        this.key = key
    }

    fun withLabel(label: String) = also {
        this.label = label
    }

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

    override fun createCopy() = TabBuilder(
        key = key,
        label = label,
        width = width,
        content = content
    )

    override fun withPosition(position: Position): TabBuilder {
        error("Can't set the position of a tab")
    }


    companion object {

        @JvmStatic
        fun newBuilder(): TabBuilder = TabBuilder()
    }

}

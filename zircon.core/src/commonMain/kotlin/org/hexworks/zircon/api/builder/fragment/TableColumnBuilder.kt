package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.fragment.impl.TableColumn
import kotlin.jvm.JvmStatic

@ZirconDsl

class TableColumnBuilder<T : Any, V : Any, C : Component> private constructor(
    /**
     * The name of this column. It will be used as table header.
     */
    var name: String = "",
    /**
     * The width of this column. The component created by [cellRenderer]
     * may not be wider than this.
     */
    var width: Int = 0,
    /**
     * This function will be used to extract the column value out of the
     * table model object
     */
    var valueProvider: ((T) -> V)? = null,
    /**
     * This function will be used to create a component to display the
     * cell.
     */
    var cellRenderer: ((V) -> C)? = null
) : FragmentBuilder<TableColumn<T, V, C>, TableColumnBuilder<T, V, C>> {

    override fun build(): TableColumn<T, V, C> {
        require(width > 0) {
            "The minimum column width is 1. $width was provided."
        }
        return TableColumn(
            name = name,
            width = width,
            valueProvider = valueProvider ?: error("Value provider is missing"),
            cellRenderer = cellRenderer ?: error("Cell renderer is missing")
        )
    }

    companion object {

        @JvmStatic
        fun <T : Any, V : Any, C : Component> newBuilder() = TableColumnBuilder<T, V, C>()
    }

    override fun createCopy() = TableColumnBuilder(
        name = name,
        width = width,
        valueProvider = valueProvider,
        cellRenderer = cellRenderer
    )

    override fun withPosition(position: Position): TableColumnBuilder<T, V, C> {
        error("Can't set the position of a table column")
    }
}

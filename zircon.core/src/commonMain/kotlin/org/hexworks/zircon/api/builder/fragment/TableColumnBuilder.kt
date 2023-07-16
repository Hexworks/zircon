package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.fragment.impl.TableColumn

@ZirconDsl
class TableColumnBuilder<T : Any, V : Any, C : Component> : FragmentBuilder<TableColumn<T, V, C>> {

    override var position: Position
        get() = error("Can't get the position of a table column")
        set(value) = error("Can't set the position of a table column")

    /**
     * The name of this column. It will be used as table header.
     */
    var name: String = ""

    /**
     * The width of this column. The component created by [cellRenderer]
     * may not be wider than this.
     */
    var width: Int = 0

    /**
     * This function will be used to extract the column value out of the
     * table model object
     */
    var valueProvider: ((T) -> V)? = null

    /**
     * This function will be used to create a component to display the
     * cell.
     */
    var cellRenderer: ((V) -> C)? = null

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
}

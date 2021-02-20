package org.hexworks.zircon.internal.fragment.impl.table

import org.hexworks.zircon.api.component.Component

/**
 * This class represents the definition of a column in a table. It provides means to get the value
 * from a model object and wrap that value into a [Component]. This component is then displayed
 * as table cell.
 */
class TableColumn<M: Any, V: Any, C: Component>(
    /**
     * The name of this column. Will be used as table header.
     */
    val name: String,
    /**
     * The width of this column. The component created by [componentCreator] may not be wider than this.
     */
    val width: Int,
    private val valueAccessor: (M) -> V,
    private val componentCreator: (V) -> C
) {
    fun newCell(rowElement: M): C =
        componentCreator(
            valueAccessor(rowElement)
        )
}
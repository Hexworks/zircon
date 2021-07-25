package org.hexworks.zircon.api.fragment.table

import org.hexworks.zircon.api.Beta
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Component

/**
 * This class represents the definition of a column in a [Table]. It provides means to get the value
 * from a model object and wrap that value into a [Component]. That component is then displayed
 * as table cell.
 *
 * @param M the type of the model. In other words: Every element/row in the table has this type
 * @param V the type of the value of each cell in this column
 * @param C type of the [Component] used to represent each cell
 */
@Beta
open class TableColumn<M : Any, V : Any, C : Component>(
    /**
     * The name of this column. Will be used as table header.
     */
    val name: String,
    /**
     * The width of this column. The component created by [componentProvider] may not be wider than this.
     */
    val width: Int,
    private val valueProvider: (M) -> V,
    private val componentProvider: (V) -> C
) {
    /**
     * The [Component] that should be used as the column's header. It must have a width of [width] and a height
     * of 1.
     */
    open val header: Component =
        Components.header()
            .withText(name)
            .withPreferredSize(width, 1)
            .build()

    fun newCell(rowElement: M): C =
        componentProvider(
            valueProvider(rowElement)
        )
}

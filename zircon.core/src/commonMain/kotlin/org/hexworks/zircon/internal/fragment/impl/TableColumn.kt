package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.api.Beta
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.buildHeader
import org.hexworks.zircon.api.fragment.TableColumn

/**
 * This class represents the definition of a column in a [Table]. It provides means to get the value
 * from a model object and wrap that value into a [Component]. That component is then displayed
 * as table cell.
 *
 * @param T the type of the model. In other words: Every element/row in the table has this type
 * @param V the type of the value of each cell in this column
 * @param C type of the [Component] used to represent each cell
 */
@Beta
class TableColumn<T : Any, V : Any, C : Component>(
    override val name: String,
    override val width: Int,
    private val valueProvider: (T) -> V,
    private val cellRenderer: (V) -> C
) : TableColumn<T, V, C> {

    /**
     * The [Component] that can be used as the column's header.
     */
    override val header = buildHeader {
        +name
        preferredSize = Size.create(width, 1)
    }

    override fun renderCellFor(model: T) = cellRenderer(valueProvider(model))
}

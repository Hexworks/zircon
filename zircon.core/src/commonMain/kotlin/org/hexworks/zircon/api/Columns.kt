package org.hexworks.zircon.api

import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.internal.fragment.impl.table.TableColumn

/**
 * This object provides different presets for [TableColumn]s.
 */
object Columns {

    /**
     * Creates a [TableColumn] that represents it's cell values with a [Label]. The text of the label
     * is the toString() of the value provided by [valueAccessor].
     *
     * @param name the name of the column to be used as header
     * @param width the width of the column
     * @param valueAccessor returns the value to be used in each cell of the column. It should preferable be a
     *  [String] or similar primitive type so that calling toString on it does not have strange side effects.
     */
    fun <M: Any, V: Any> textColumn(name: String, width: Int, valueAccessor: (M) -> V): TableColumn<M, V, Label> =
        TableColumn(
            name,
            width,
            valueAccessor
        ) { cellValue ->
            Components
                .label()
                .withSize(width, 1)
                .withText(cellValue.toString())
                .build()
        }
}
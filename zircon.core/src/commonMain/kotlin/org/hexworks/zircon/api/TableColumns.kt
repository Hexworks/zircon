package org.hexworks.zircon.api

import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.component.Icon
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.internal.fragment.impl.table.TableColumn

/**
 * This object provides different presets for [TableColumn]s.
 */
object TableColumns {

    /**
     * Creates a [TableColumn] that represents it's cell values with a [Label]. The text of the label
     * is the toString() of the value provided by [valueAccessor].
     *
     * @param name the name of the column to be used as header
     * @param width the width of the column
     * @param valueAccessor returns the value to be used in each cell of the column. It should preferable be a
     *  [String] or similar primitive type so that calling toString on it does not have strange side effects.
     *  When the value returned is an [ObservableValue] or a [Property] the textProperty of the label
     *  will update accordingly.
     */
    fun <M : Any, V : Any> textColumn(name: String, width: Int, valueAccessor: (M) -> V): TableColumn<M, V, Label> =
        TableColumn(
            name,
            width,
            valueAccessor
        ) { cellValue ->
            Components
                .label()
                .withSize(width, 1)
                .build()
                .apply {
                    if (cellValue is ObservableValue<*>) {
                        textProperty.updateFrom(
                            cellValue.bindTransform { it.toString() },
                            updateWhenBound = true
                        )
                    } else {
                        text = cellValue.toString()
                    }
                }
        }

    /**
     * Creates a column of width 1 which represents its values with an [Icon].
     */
    fun <M: Any, V: Any> icon(name: String,
                              valueAccessor: (M) -> V,
                              iconGenerator: (V) -> Icon): TableColumn<M, V, Icon> =
            TableColumn(
                    name,
                    1,
                    valueAccessor,
                    iconGenerator
            )
}
package org.hexworks.zircon.api

import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.component.Icon
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.fragment.table.TableColumn

/**
 * This object provides different presets for [TableColumn]s.
 */
@Beta
object TableColumns {

    /**
     * Creates a [TableColumn] that represents it's cell values with a [Label]. The text of the label
     * is the toString() of the value provided by [valueAccessor].
     *
     * @param name the name of the column to be used as header
     * @param width the width of the column
     * @param valueAccessor returns the value to be used in each cell of the column. It should preferable be a
     *  [String] or similar primitive type so that calling `toString()` on it does not have strange side effects.
     */
    fun <M : Any, V : Any> textColumn(name: String, width: Int, valueAccessor: (M) -> V): TableColumn<M, V, Label> =
        TableColumn(
            name,
            width,
            valueAccessor
        ) { cellValue ->
            Components
                .label()
                .withPreferredSize(width, 1)
                .withText(cellValue.toString())
                .build()
        }

    /**
     * Creates a [TableColumn] that represents it's cell values with a [Label]. The text of the label
     * is the `toString()` of the [ObservableValue]'s value provided by [valueAccessor]. This means the
     * cell values will update when the observed value changes.
     *
     * @param name the name of the column to be used as header
     * @param width the width of the column
     * @param valueAccessor returns an [ObservableValue] that will be bound to the [Label]'s textProperty of a
     * cell. The observed value will simply be converted to a String by calling `toString()` on it so be sure
     * that it's string representation is useful.
     */
    fun <M : Any, V : ObservableValue<*>> textColumnObservable(name: String, width: Int, valueAccessor: (M) -> V): TableColumn<M, V, Label> =
        TableColumn(
            name,
            width,
            valueAccessor
        ) { cellValue ->
            Components
                    .label()
                    .withPreferredSize(width, 1)
                    .build()
                    .apply {
                        textProperty.updateFrom(
                                cellValue.bindTransform { it.toString() },
                                updateWhenBound = true
                        )
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

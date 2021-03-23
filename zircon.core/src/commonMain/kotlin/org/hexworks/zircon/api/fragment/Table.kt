package org.hexworks.zircon.api.fragment

import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.data.Size

/**
 * A table fragment displays data in rows. Each row contains several cells. How a cell is displayed depends
 * on the definition of it's column.
 *
 * A table contains data of type [M] (the model). Every model object represents one row in the table. **A table
 * may not be empty**.
 *
 * @param M the type of the underlying model representing this table's data.
 */
interface Table<M : Any> : Fragment {
    /**
     * The element representing the currently selected row.
     */
    val selectedRow: M

    /**
     * The element representing the currently selected row as [ObservableValue].
     */
    val selectedRowValue: ObservableValue<M>

    /**
     * The size this table consumes based on its configured height and column definition.
     */
    val size: Size
}
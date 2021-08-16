package org.hexworks.zircon.api.fragment

import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.zircon.api.Beta
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.data.Size


/**
 * A table fragment displays data in rows. Each row contains several cells. How a cell is displayed depends
 * on the definition of it's [TableColumn].
 *
 * A table contains data of type [M] (the model). Every model object represents one row in the table.
 *
 * Table rows may be selected and are represented by [selectedRowsValue] and [selectedRows].
 *
 * @param M the type of the underlying model representing this table's data.
 */
@Beta
interface Table<M : Any> : Fragment {

    val size: Size

    /**
     * The elements of the table that are currently selected.
     */
    val selectedRows: List<M>

    /**
     * The elements of the table that are currently selected as [ObservableList].
     */
    val selectedRowsValue: ObservableList<M>

}

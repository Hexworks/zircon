package org.hexworks.zircon.api.builder.fragment

import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.zircon.api.Beta
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.fragment.Table
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.fragment.impl.DefaultTable
import org.hexworks.zircon.api.fragment.table.TableColumn

@Beta
class TableBuilder<M : Any>(private val data: ObservableList<M>) : FragmentBuilder<Table<M>, TableBuilder<M>> {

    private val columns: MutableList<TableColumn<M, *, *>> = mutableListOf()
    private var height: Int = 5
    private var rowSpacing: Int = 0
    private var colSpacing: Int = 0
    private var position: Position? = null

    override fun build(): Table<M> =
        DefaultTable(
            data,
            columns,
            height,
            rowSpacing,
            colSpacing
        )
            .apply {
                position?.let { root.moveTo(it) }
            }

    /**
     * Adds the given [TableColumn]s to the list of columns for this table. Multiple calls
     * add to the already defined columns.
     */
    fun withColumns(vararg columns: TableColumn<M, *, *>) = also {
        this.columns += columns
    }

    /**
     * Sets the fragmentHeight of the resulting [Table].
     */
    fun withHeight(height: Int) = also {
        this.height = height
    }

    /**
     * Sets the spacing between each data row.
     */
    fun withRowSpacing(spacing: Int) = also {
        rowSpacing = spacing
    }

    /**
     * Sets the spacing between the columns.
     */
    fun withColumnSpacing(spacing: Int) = also {
        colSpacing = spacing
    }

    override fun withPosition(position: Position): TableBuilder<M> = also {
        this.position = position
    }

    override fun withPosition(x: Int, y: Int): TableBuilder<M> =
        withPosition(Position.create(x, y))

    override fun createCopy(): Builder<Table<M>> {
        val copy = TableBuilder(data)
            .withColumns(*columns.toTypedArray())
            .withHeight(height)
            .withRowSpacing(rowSpacing)
            .withColumnSpacing(colSpacing)
        position?.let { copy.withPosition(it) }
        return copy
    }
}

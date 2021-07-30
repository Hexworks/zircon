package org.hexworks.zircon.api.builder.fragment

import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.zircon.api.Beta
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.fragment.Table
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.api.fragment.table.TableColumn
import org.hexworks.zircon.internal.fragment.impl.DefaultTable
import kotlin.jvm.JvmStatic

@Beta
class TableBuilder<M : Any> private constructor(
    var data: ObservableList<M>? = null,
    var columns: List<TableColumn<M, *, *>> = listOf(),
    var height: Int = 2,
    var rowSpacing: Int = 0,
    var colSpacing: Int = 0,
    var position: Position = Position.zero(),
) : FragmentBuilder<Table<M>, TableBuilder<M>> {

    override fun build(): Table<M> {
        val minHeight = 2
        require(height >= minHeight) {
            "A table requires a height of at least $minHeight."
        }
        require(columns.isNotEmpty()) {
            "A table must have at least one column."
        }
        require(rowSpacing >= 0) {
            "Row spacing must be greater than equal to 0. Current value is $rowSpacing"
        }
        require(colSpacing >= 0) {
            "Col spacing must be greater than equal to 0. Current value is $colSpacing"
        }
        return DefaultTable(
            position = position,
            height = height,
            data = data ?: error("A table must have an observable list of data associated with its columns"),
            columns = columns,
            rowSpacing = rowSpacing,
            colSpacing = colSpacing
        )
    }

    fun withHeight(height: Int) = also {
        this.height = height
    }

    fun withData(data: ObservableList<M>) = also {
        this.data = data
    }

    fun withColumns(columns: List<TableColumn<M, *, *>>) = also {
        this.columns = columns
    }

    fun withRowSpacing(spacing: Int) = also {
        rowSpacing = spacing
    }

    fun withColumnSpacing(spacing: Int) = also {
        colSpacing = spacing
    }

    override fun withPosition(position: Position): TableBuilder<M> = also {
        this.position = position
    }

    override fun createCopy() = TableBuilder(
        data = data,
        columns = columns,
        height = height,
        rowSpacing = rowSpacing,
        colSpacing = colSpacing,
        position = position
    )

    companion object {

        @JvmStatic
        fun <T : Any> newBuilder() = TableBuilder<T>()
    }
}

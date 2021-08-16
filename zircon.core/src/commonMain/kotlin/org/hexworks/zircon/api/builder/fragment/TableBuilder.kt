package org.hexworks.zircon.api.builder.fragment

import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.Beta
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.Icon
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.dsl.component.buildIcon
import org.hexworks.zircon.api.dsl.component.buildLabel
import org.hexworks.zircon.api.fragment.Table
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.component.renderer.DefaultHBoxRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.fragment.impl.DefaultTable
import org.hexworks.zircon.internal.fragment.impl.TableColumn
import kotlin.jvm.JvmStatic

@ZirconDsl
@Beta
class TableBuilder<T : Any> private constructor(
    var data: ObservableList<T>? = null,
    var columns: List<TableColumn<T, *, *>> = listOf(),
    var height: Int = 2,
    var rowSpacing: Int = 0,
    var colSpacing: Int = 0,
    var position: Position = Position.zero(),
    var addHeader: Boolean = true,
    var selectedRowRenderer: ComponentRenderer<HBox> = DefaultHBoxRenderer(),
) : FragmentBuilder<Table<T>, TableBuilder<T>> {

    override fun build(): Table<T> {
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
            colSpacing = colSpacing,
            addHeader = addHeader,
            selectedRowRenderer = selectedRowRenderer
        )
    }

    fun <V : Any, C : Component> TableBuilder<T>.column(init: TableColumnBuilder<T, V, C>.() -> Unit) {
        columns = columns + TableColumnBuilder.newBuilder<T, V, C>().apply(init).build()
    }

    /**
     * Creates a column that will render a [Label] that will contain the [String] you provide.
     * In order for this to work what you'll need to set is
     * - [TableColumnBuilder.valueProvider]
     * - [TableColumnBuilder.width]
     * The rest will be set for you.
     */
    fun TableBuilder<T>.textColumn(init: TableColumnBuilder<T, String, Label>.() -> Unit) {
        columns = columns + TableColumnBuilder.newBuilder<T, String, Label>()
            .apply(init)
            .also { builder ->
                builder.cellRenderer = {
                    buildLabel {
                        +it
                        preferredSize = Size.create(builder.width, 1)
                    }
                }
            }
            .build()
    }

    /**
     * Creates a column that will render a [Label] that will contain the [Number] you provide.
     * In order for this to work what you'll need to set is
     * - [TableColumnBuilder.valueProvider]
     * - [TableColumnBuilder.width]
     * The rest will be set for you.
     */
    fun TableBuilder<T>.numberColumn(init: TableColumnBuilder<T, Number, Label>.() -> Unit) {
        columns = columns + TableColumnBuilder.newBuilder<T, Number, Label>()
            .apply(init)
            .also { builder ->
                builder.cellRenderer = { number ->
                    buildLabel {
                        +number.toString()
                        preferredSize = Size.create(builder.width, 1)
                    }
                }
            }
            .build()
    }

    /**
     * Creates a column that will render an [Label] and bind its value to the obserable value
     * you provide. In order for this to work what you'll need to set is
     * - a [TableColumnBuilder.valueProvider]
     * The rest will be set for you.
     */
    fun TableBuilder<T>.observableTextColumn(init: TableColumnBuilder<T, ObservableValue<String>, Label>.() -> Unit) {
        columns = columns + TableColumnBuilder.newBuilder<T, ObservableValue<String>, Label>()
            .apply(init)
            .also { builder ->
                builder.cellRenderer = { value ->
                    buildLabel {
                        textProperty.updateFrom(value)
                        preferredSize = Size.create(builder.width, 1)
                    }
                }
            }
            .build()
    }

    /**
     * Creates a column that will render an [Icon]. In order for this to work
     * what you'll need to set is
     * - a [TableColumnBuilder.valueProvider]
     * The rest will be set for you.
     */
    fun TableBuilder<T>.iconColumn(init: TableColumnBuilder<T, Tile, Icon>.() -> Unit) {
        columns = columns +
                TableColumnBuilder.newBuilder<T, Tile, Icon>()
                    .apply(init).also { builder ->
                        builder.width = 1
                        builder.cellRenderer = { tile ->
                            buildIcon {
                                iconTile = tile
                            }
                        }
                    }
                    .build()
    }

    fun withHeight(height: Int) = also {
        this.height = height
    }

    fun withData(data: ObservableList<T>) = also {
        this.data = data
    }

    fun withColumns(columns: List<TableColumn<T, *, *>>) = also {
        this.columns = columns
    }

    fun withRowSpacing(spacing: Int) = also {
        rowSpacing = spacing
    }

    fun withColumnSpacing(spacing: Int) = also {
        colSpacing = spacing
    }

    fun withAddHeader(addHeader: Boolean) = also {
        this.addHeader = addHeader
    }

    fun withSelectedRowRenderer(rowRenderer: ComponentRenderer<HBox>) = also {
        this.selectedRowRenderer = rowRenderer
    }

    override fun withPosition(position: Position): TableBuilder<T> = also {
        this.position = position
    }

    override fun createCopy() = TableBuilder(
        data = data,
        columns = columns,
        height = height,
        rowSpacing = rowSpacing,
        colSpacing = colSpacing,
        position = position,
        addHeader = addHeader,
        selectedRowRenderer = selectedRowRenderer
    )

    companion object {

        @JvmStatic
        fun <T : Any> newBuilder() = TableBuilder<T>()
    }
}

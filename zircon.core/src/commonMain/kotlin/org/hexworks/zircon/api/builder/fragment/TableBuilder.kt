package org.hexworks.zircon.api.builder.fragment

import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.builder.component.buildIcon
import org.hexworks.zircon.api.builder.component.buildLabel
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.Icon
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.dsl.AnyContainerBuilder
import org.hexworks.zircon.api.dsl.buildFragmentFor
import org.hexworks.zircon.api.fragment.Table
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.component.renderer.DefaultHBoxRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.fragment.impl.DefaultTable
import org.hexworks.zircon.internal.fragment.impl.TableColumn

@ZirconDsl
class TableBuilder<T : Any> : FragmentBuilder<Table<T>> {

    override var position: Position = Position.zero()

    var data: ObservableList<T> = listOf<T>().toProperty()
    var columns: List<TableColumn<T, *, *>> = listOf()
    var height: Int = 2
    var rowSpacing: Int = 0
    var colSpacing: Int = 0
    var addHeader: Boolean = true

    /**
     * Renderer that will be used to render the selected row. Override this if
     * you want to differentiate the selected row visually.
     */
    var selectedRowRenderer: ComponentRenderer<HBox> = DefaultHBoxRenderer()

    fun <V : Any, C : Component> TableBuilder<T>.column(init: TableColumnBuilder<T, V, C>.() -> Unit) {
        columns = columns + TableColumnBuilder<T, V, C>().apply(init).build()
    }

    /**
     * Creates a column that will render a [Label] that will contain the [String] you provide.
     * In order for this to work what you'll need to set is
     * - [TableColumnBuilder.valueProvider]
     * - [TableColumnBuilder.width]
     * The rest will be set for you.
     */
    fun TableBuilder<T>.textColumn(init: TableColumnBuilder<T, String, Label>.() -> Unit) {
        columns = columns + TableColumnBuilder<T, String, Label>()
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
        columns = columns + TableColumnBuilder<T, Number, Label>()
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
     * Creates a column that will render an [Label] and bind its value to the observable value
     * you provide. In order for this to work what you'll need to set is
     * - a [TableColumnBuilder.valueProvider]
     * The rest will be set for you.
     */
    fun TableBuilder<T>.observableTextColumn(init: TableColumnBuilder<T, ObservableValue<String>, Label>.() -> Unit) {
        columns = columns + TableColumnBuilder<T, ObservableValue<String>, Label>()
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
                TableColumnBuilder<T, Tile, Icon>()
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
            data = data ?: error("A table must have an observable list of data associated with its columns"),
            height = data!!.size + 1,
            columns = columns,
            rowSpacing = rowSpacing,
            colSpacing = colSpacing,
            addHeader = addHeader,
            selectedRowRenderer = selectedRowRenderer
        )
    }
}

/**
 * Creates a new [Table] using the fragment builder DSL and returns it.
 */

fun <T : Any> buildTable(
    init: TableBuilder<T>.() -> Unit
): Table<T> = TableBuilder<T>().apply(init).build()

/**
 * Creates a new [Table] using the fragment builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Table].
 */

fun <T : Any> AnyContainerBuilder.table(
    init: TableBuilder<T>.() -> Unit
): Table<T> = buildFragmentFor(this, org.hexworks.zircon.api.builder.fragment.TableBuilder(), init)


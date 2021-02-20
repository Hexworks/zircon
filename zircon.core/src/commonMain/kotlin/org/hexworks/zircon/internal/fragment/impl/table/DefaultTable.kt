package org.hexworks.zircon.internal.fragment.impl.table

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.Table

/**
 * The **internal** default implementation of [Table].
 */
class DefaultTable<M: Any>(
    data: List<M>,
    columns: List<TableColumn<M, *, *>>,
    /**
     * The height this fragment will use. Keep in mind that the first row will be used as header row.
     */
    fragmentHeight: Int,
    private val rowSpacing: Int = 0,
    private val colSpacing: Int = 0
): Table<M> {

    init {
        require(data.isNotEmpty()) {
            "A table may not be empty! Please feed it some data to display."
        }
        val minHeight = 2
        require(fragmentHeight >= minHeight) {
            "A table requires a height of at least $minHeight."
        }
    }

    private val selectedElement: Property<M> = data.first().toProperty()

    override val selectedRowValue: ObservableValue<M> = selectedElement

    override val selectedRow: M
        get() = selectedRowValue.value

    override val size: Size = Size.create(
        width = columns.sumBy { it.width } + ((columns.size - 1) * colSpacing),
        height = fragmentHeight
    )

    override val root: VBox

    private val currentRows: MutableList<AttachedComponent> = mutableListOf()

    init {
        root = Components
            .vbox()
            .withSpacing(0)
            .withSize(size)
            .build()

        root
            .addComponents(
                headerRow(columns, size.width),
                dataPanel(data, columns, Size.create(size.width, fragmentHeight - 1))
            )
    }

    private fun dataPanel(data: List<M>, columns: List<TableColumn<M, *, *>>, panelSize: Size): VBox =
        Components
            .vbox()
            .withSize(panelSize)
            .withSpacing(rowSpacing)
            .build()
            .apply {
                data.forEach { model ->
                    val cells: List<Component> = columns
                        .map { it.newCell(model) }
                    val rowHeight = cells.maxOf { it.height }
                    val row = Components
                        .hbox()
                        .withSpacing(rowSpacing)
                        .withSize(panelSize.width, rowHeight)
                        .build()
                    cells.forEach { cell ->
                        row.addComponent(cell)
                    }
                    var remainingHeight = panelSize.height
                    while (remainingHeight >= rowHeight) {
                        currentRows
                            .add(addComponent(row))
                        remainingHeight -= rowHeight
                    }
                }
            }

    private fun headerRow(columns: List<TableColumn<M, *, *>>, fragmentWidth: Int): HBox =
        Components
            .hbox()
            .withSize(fragmentWidth, 1)
            .withSpacing(colSpacing)
            .build()
            .apply {
                columns
                    .forEach { column ->
                        addComponent(
                            Components
                                .header()
                                .withText(column.name.substring(0, column.width))
                                .withSize(column.width, 1))
                    }
            }
}
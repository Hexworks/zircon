package org.hexworks.zircon.internal.fragment.impl.table

import org.hexworks.cobalt.databinding.api.collection.ListProperty
import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.table.Table
import org.hexworks.zircon.api.fragment.table.TableColumn
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.api.uievent.UIEventResponse

/**
 * The **internal** default implementation of [Table].
 */
class DefaultTable<M: Any>(
    private val data: ObservableList<M>,
    private val columns: List<TableColumn<M, *, *>>,
    /**
     * The height this fragment will use. Keep in mind that the first row will be used as header row.
     */
    fragmentHeight: Int,
    private val rowSpacing: Int = 0,
    private val colSpacing: Int = 0
): Table<M> {

    init {
        require(columns.isNotEmpty()) {
            "A table must have at least one column."
        }
        val minHeight = 2
        require(fragmentHeight >= minHeight) {
            "A table requires a height of at least $minHeight."
        }
    }

    private val selectedElements: ListProperty<M> = emptyList<M>().toProperty()

    override val selectedRowsValue: ObservableList<M> = selectedElements

    override val selectedRows: List<M>
        get() = selectedRowsValue.value

    override val size: Size = Size.create(
        width = columns.sumBy { it.width } + ((columns.size - 1) * colSpacing),
        height = fragmentHeight
    )

    override val root: VBox = Components
        .vbox()
        .withSpacing(0)
        .withSize(size)
        .build()

    private val currentRows: MutableList<AttachedComponent> = mutableListOf()

    private val dataPanel: VBox

    init {
        val headerRow = headerRow()
        dataPanel = Components
            .vbox()
            .withSize(size.withRelativeHeight(-headerRow.height))
            .withSpacing(rowSpacing)
            .build()
        root
            .addComponents(
                headerRow,
                dataPanel
            )
        reloadData()
        data.onChange { reloadData() }
    }

    private fun reloadData() {
        currentRows.forEach { it.detach() }
        currentRows.clear()
        dataPanel.apply {
            // TODO: Improve this loop to not loop over all elements
            val modelIterator = data.listIterator()
            val firstRow: Component? = if (modelIterator.hasNext()) {
                newRowFor(modelIterator.next())
                    .also { currentRows.add(addComponent(it)) }
            } else {
                null
            }
            var neededHeight = firstRow?.height ?: 0
            var remainingHeight = contentSize.height - neededHeight
            while (remainingHeight >= neededHeight && modelIterator.hasNext()) {
                val newRow = newRowFor(modelIterator.next())
                currentRows.add(addComponent(newRow))
                neededHeight = newRow.height + rowSpacing
                remainingHeight -= neededHeight
            }
        }
    }

    private fun newRowFor(model: M): Component {
        val cells: List<Component> = columns
            .map { it.newCell(model) }
        val rowHeight = cells.maxOf { it.height }
        val row = Components
            .hbox()
            .withSpacing(colSpacing)
            .withSize(size.width, rowHeight)
            .build()
        cells.forEach { row.addComponent(it) }
        row.handleMouseEvents(MouseEventType.MOUSE_CLICKED) { _, phase ->
            // allow for the cells to implement custom mouse event handling
            if (phase == UIEventPhase.BUBBLE) {
                selectedElements.clear()
                selectedElements.add(model)
                UIEventResponse.processed()
            } else {
                UIEventResponse.pass()
            }
        }
        return row
    }

    /**
     * Builds the [HBox] containing the column headers.
     */
    private fun headerRow(): HBox {
        return Components
            .hbox()
            .withSize(size.width, 1)
            .withSpacing(colSpacing)
            .build()
            .apply {
                columns
                    .forEach { column ->
                        addComponent(column.header)
                    }
            }
    }
}
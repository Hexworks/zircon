package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.cobalt.databinding.api.collection.ListProperty
import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.builder.component.VBoxBuilder
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.buildHbox
import org.hexworks.zircon.api.dsl.component.buildVbox
import org.hexworks.zircon.api.dsl.component.hbox
import org.hexworks.zircon.api.dsl.component.vbox
import org.hexworks.zircon.api.fragment.Table
import org.hexworks.zircon.api.fragment.table.TableColumn
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.api.uievent.UIEventResponse

/**
 * The **internal** default implementation of [Table].
 */
class DefaultTable<M : Any> internal constructor(
    position: Position,
    /**
     * The height this fragment will use. Keep in mind that the first row will be used as header row.
     */
    height: Int,
    private val data: ObservableList<M>,
    private val columns: List<TableColumn<M, *, *>>,
    private val rowSpacing: Int = 0,
    private val colSpacing: Int = 0
) : Table<M> {

    private val selectedElements: ListProperty<M> = emptyList<M>().toProperty()

    override val selectedRowsValue: ObservableList<M> = selectedElements

    override val selectedRows: List<M>
        get() = selectedRowsValue.value

    private val width: Int = columns.sumOf { it.width } + ((columns.size - 1) * colSpacing)

    private lateinit var dataPanel: VBox

    override val root = buildVbox {
        val size = Size.create(
            width = width,
            height = height
        )
        preferredSize = size
        this.position = position

        addHeaderRow(size.width)
        dataPanel = vbox {
            preferredSize = size.withRelativeHeight(-1)
            spacing = rowSpacing
        }
    }

    init {
        reloadData()
        data.onChange { reloadData() }
    }

    private fun reloadData() {
        dataPanel.detachAllComponents()
        dataPanel.apply {
            // TODO: Improve this loop to not loop over all elements
            val elementCount = minOf(data.size, height)
            for (i in 0 until elementCount) {
                addComponent(newRowFor(data[i]))
            }
        }
    }

    private fun newRowFor(model: M): Component {
        val cells: List<Component> = columns
            .map { column -> column.newCell(model) }
        val rowHeight = cells.maxOf { it.height }
        val row = buildHbox {
            spacing = colSpacing
            preferredSize = Size.create(width, rowHeight)
        }
        cells.forEach(row::addComponent)
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

    private fun VBoxBuilder.addHeaderRow(width: Int) = hbox {
        preferredSize = Size.create(width, 1)
        spacing = colSpacing
        columns.forEach { column ->
            withAddedChildren(column.header)
        }
    }

}

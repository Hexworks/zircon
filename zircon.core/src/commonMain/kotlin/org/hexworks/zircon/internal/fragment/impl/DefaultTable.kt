package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.cobalt.databinding.api.collection.ListProperty
import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.builder.component.VBoxBuilder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.buildHbox
import org.hexworks.zircon.api.dsl.component.buildVbox
import org.hexworks.zircon.api.dsl.component.hbox
import org.hexworks.zircon.api.dsl.component.vbox
import org.hexworks.zircon.api.fragment.Table
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.internal.component.renderer.DefaultHBoxRenderer
import kotlin.jvm.Synchronized

/**
 * The **internal** default implementation of [Table].
 */
@Suppress("UNCHECKED_CAST")
class DefaultTable<M : Any> internal constructor(
    position: Position,
    /**
     * The height this fragment will use. Keep in mind that the first row will be used as header row.
     */
    height: Int,
    private val addHeader: Boolean,
    private val data: ObservableList<M>,
    private val columns: List<TableColumn<M, *, *>>,
    private val rowSpacing: Int = 0,
    private val colSpacing: Int = 0,
    private val selectedRowRenderer: ComponentRenderer<HBox> = DefaultHBoxRenderer()
) : Table<M> {

    private val dataPanelHeight = if (addHeader) height - 1 else height
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

        if (addHeader) {
            addHeaderRow(size.width)
        }
        dataPanel = vbox {
            preferredSize = Size.create(width, dataPanelHeight)
            spacing = rowSpacing
        }
    }

    override val size = root.size

    init {
        reloadData()
        data.onChange { reloadData() }
    }

    @Synchronized
    private fun reloadData() {
        dataPanel.detachAllComponents()
        dataPanel.apply {
            // TODO: Improve this loop to not loop over all elements
            val elementCount = minOf(data.size, dataPanelHeight)
            for (i in 0 until elementCount) {
                addComponent(newRowFor(data[i]))
            }
        }
    }

    @Synchronized
    private fun newRowFor(model: M): Component {
        val cells: List<Component> = columns.map { column -> column.renderCellFor(model) }
        val rowHeight = cells.maxOf { it.height }
        val row = buildHbox {
            spacing = colSpacing
            preferredSize = Size.create(width, rowHeight)
            val defaultRenderer: ComponentRenderer<HBox> = componentRenderer as ComponentRenderer<HBox>
            componentRenderer = ComponentRenderer { tileGraphics, context ->
                if (selectedElements.contains(model)) {
                    selectedRowRenderer.render(tileGraphics, context)
                } else defaultRenderer.render(tileGraphics, context)
            }

        }
        cells.forEach(row::addComponent)
        row.handleMouseEvents(MouseEventType.MOUSE_CLICKED) { _, phase ->
            // This allows the cells to implement custom mouse event handling
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

    @Synchronized
    private fun VBoxBuilder.addHeaderRow(width: Int) = hbox {
        preferredSize = Size.create(width, 1)
        spacing = colSpacing
        columns.forEach { column ->
            withAddedChildren(column.header)
        }
    }

}

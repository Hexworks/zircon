package org.hexworks.zircon.internal.component.impl.log

import org.hexworks.zircon.api.behavior.CursorHandler
import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.behavior.impl.DefaultCursorHandler
import org.hexworks.zircon.internal.behavior.impl.DefaultScrollable
import org.hexworks.zircon.internal.component.impl.DefaultContainer
import kotlin.math.max

class DefaultLogArea constructor(
        private val renderingStrategy: ComponentRenderingStrategy<LogArea>,
        tileset: TilesetResource,
        size: Size,
        position: Position,
        componentStyleSet: ComponentStyleSet,
        scrollable: Scrollable = DefaultScrollable(size, size),
        cursorHandler: CursorHandler = DefaultCursorHandler(size),
        override var wrapLogElements: Boolean,
        var logRowHistorySize: Int)
    : LogArea, Scrollable by scrollable, CursorHandler by cursorHandler, DefaultContainer(
        position = position,
        size = size,
        tileset = tileset,
        componentStyles = componentStyleSet,
        renderer = renderingStrategy) {

    private var logElementBuffer = LogElementBuffer(visibleSize(), this,  logRowHistorySize)

    init {
        render()
    }

    override fun addTextElement(text: String, modifiers: Set<Modifier>?) {
        val position = getNewElementPositionX()
        val textElement = LogTextElement(text, position)
        textElement.modifiers = modifiers
        logElementBuffer.addLogElement(textElement)
        render()
    }

    override fun addComponentElement(component: Component) {
        require(component.size().yLength == 1) { "only components with height of 1 are supported" }

        val position = getNewElementPositionX()
        logElementBuffer.addLogElement(LogComponentElement(component, position))
        addComponent(component)
        render()
    }


    private fun getNewElementPositionX(): Int {
        val lastElementInRow = logElementBuffer.currentLogElementRow().logElements.lastOrNull()
        val xPos = if (lastElementInRow != null)
            lastElementInRow.getPosition().x + lastElementInRow.getSize().width()
        else
            0
        return xPos
    }

    override fun addNewRows(numberOfRows: Int) {
        logElementBuffer.addNewRows(numberOfRows)
        render()
    }

    override fun getLogElementBuffer(): LogElementBuffer {
        return logElementBuffer
    }

    override fun clear() {
        logElementBuffer.clear()
        children().forEach { removeComponent(it) }
        setActualSize(size())
        scrollUpBy(visibleOffset().y)
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.secondaryForegroundColor())
                        .backgroundColor(colorTheme.primaryBackgroundColor())
                        .build())
                .build().also { css ->
                    setComponentStyleSet(css)
                    render()
                    children().forEach {
                        it.applyColorTheme(colorTheme)
                    }
                }
    }

    override fun render() {
        renderingStrategy.render(this, tileGraphics())

        val maxWidth = logElementBuffer.getAllLogElements()
                .lastOrNull { it.renderedPositionArea != null }?.renderedPositionArea?.endPosition?.x?.plus(1)
        val maxHeight = logElementBuffer.getAllLogElements()
                .lastOrNull { it.renderedPositionArea != null }?.renderedPositionArea?.endPosition?.y?.plus(1)

        if (maxWidth != null) {
            if (size().width() < maxWidth || size().height() < maxHeight!!) {
                setActualSize(Size.create(max(maxWidth, size().width()), max(maxHeight!!, size().height())))
            }
        }
    }


}

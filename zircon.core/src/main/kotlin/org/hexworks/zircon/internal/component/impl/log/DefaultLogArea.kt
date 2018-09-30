package org.hexworks.zircon.internal.component.impl.log

import org.hexworks.zircon.api.behavior.CursorHandler
import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.behavior.impl.DefaultCursorHandler
import org.hexworks.zircon.internal.behavior.impl.DefaultScrollable
import org.hexworks.zircon.internal.component.impl.DefaultComponent
import org.hexworks.zircon.internal.event.ZirconEvent

class DefaultLogArea constructor(
        private val renderingStrategy: ComponentRenderingStrategy<LogArea>,
        tileset: TilesetResource,
        size: Size,
        position: Position,
        componentStyleSet: ComponentStyleSet,
        scrollable: Scrollable = DefaultScrollable(size, size),
        cursorHandler: CursorHandler = DefaultCursorHandler(size), override var textWrap: TextWrap)
    : LogArea, Scrollable by scrollable, CursorHandler by cursorHandler, DefaultComponent(
        position = position,
        size = size,
        tileset = tileset,
        componentStyles = componentStyleSet,
        renderer = renderingStrategy) {

    private var logElementBuffer = LogElementBuffer()


    init {
        //refreshVirtualSpaceSize()
        render()
    }

    override fun addText(text: String, modifiers: Set<Modifier>?) {
        val position = getNewElementPosition()
        val textElement = TextElement(text, position)
        textElement.modifiers = modifiers
        logElementBuffer.addLogElement(textElement)
        render()
    }

    override fun addHyperLink(linkText: String, linkId: String) {
        val position = getNewElementPosition()
        logElementBuffer.addLogElement(HyperLinkElement(linkText, linkId, position))
        render()
    }

    private fun getNewElementPosition(): Position {
        val lastElementInRow = logElementBuffer.currentLogElementRow().logElements.lastOrNull()
        val xPos = if (lastElementInRow != null)
            lastElementInRow.position.x + lastElementInRow.length()
        else
            0
        return Position.create(xPos, logElementBuffer.currentLogElementRow().yPosition)
    }

    override fun addNewRow() {
        logElementBuffer.addNewRow()
        render()
    }

    override fun getLogElementBuffer(): LogElementBuffer {
        return logElementBuffer
    }

    override fun clear() {
        logElementBuffer.clear()
    }

    override fun mousePressed(action: MouseAction) {
        componentStyleSet().applyActiveStyle()
        render()

        val mouseOnLogElement = logElementBuffer.getLogElementContainingPosition(action.position)
        if (mouseOnLogElement != null && mouseOnLogElement is HyperLinkElement)
            EventBus.broadcast(ZirconEvent.TriggeredHyperLink(mouseOnLogElement.linkId))

    }

    override fun mouseExited(action: MouseAction) {
        componentStyleSet().reset()
        render()
    }


    override fun mouseReleased(action: MouseAction) {
        componentStyleSet().applyMouseOverStyle()
        render()
    }


    override fun acceptsFocus() = false

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.secondaryBackgroundColor())
                        .backgroundColor(colorTheme.secondaryForegroundColor())
                        .build())
                .disabledStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.secondaryForegroundColor())
                        .backgroundColor(colorTheme.secondaryBackgroundColor())
                        .build())
                .focusedStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.primaryBackgroundColor())
                        .backgroundColor(colorTheme.primaryForegroundColor())
                        .build())
                .build().also {
                    setComponentStyleSet(it)
                    render()
                }
    }

    override fun giveFocus(input: Maybe<Input>): Boolean {
        return true
    }

    override fun takeFocus(input: Maybe<Input>) {
    }

    override fun render() {
        renderingStrategy.render(this, tileGraphics())
    }

    private fun enableFocusedComponent() {
        componentStyleSet().applyFocusedStyle()
        render()
    }


}

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
import org.hexworks.zircon.api.modifier.SimpleModifiers
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.behavior.impl.DefaultCursorHandler
import org.hexworks.zircon.internal.behavior.impl.DefaultScrollable
import org.hexworks.zircon.internal.component.impl.DefaultComponent
import org.hexworks.zircon.internal.event.ZirconEvent
import kotlin.math.max

class DefaultLogArea constructor(
        private val renderingStrategy: ComponentRenderingStrategy<LogArea>,
        tileset: TilesetResource,
        size: Size,
        position: Position,
        componentStyleSet: ComponentStyleSet,
        scrollable: Scrollable = DefaultScrollable(size, size),
        cursorHandler: CursorHandler = DefaultCursorHandler(size),
        override var textWrapMode: TextWrap,
        var logRowHistorySize: Int)
    : LogArea, Scrollable by scrollable, CursorHandler by cursorHandler, DefaultComponent(
        position = position,
        size = size,
        tileset = tileset,
        componentStyles = componentStyleSet,
        renderer = renderingStrategy) {

    private var logElementBuffer = LogElementBuffer(visibleSize(), logRowHistorySize)
    private var hyperLinkElementOnMouseOver: HyperLinkElement? = null


    init {
        //refreshVirtualSpaceSize()
        render()
    }

    override fun addText(text: String, modifiers: Set<Modifier>?) {
        val position = getNewElementPositionX()
        val textElement = TextElement(text, position)
        textElement.modifiers = modifiers
        logElementBuffer.addLogElement(textElement)
        render()
    }

    override fun addHyperLink(linkText: String, linkId: String) {
        val position = getNewElementPositionX()
        logElementBuffer.addLogElement(HyperLinkElement(linkText, linkId, position))
        render()
    }

    private fun getNewElementPositionX(): Int {
        val lastElementInRow = logElementBuffer.currentLogElementRow().logElements.lastOrNull()
        val xPos = if (lastElementInRow != null)
            lastElementInRow.getPosition().x + lastElementInRow.length()
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
        setActualSize(size())
        scrollUpBy(visibleOffset().y)
    }


    override fun mousePressed(action: MouseAction) {
        componentStyleSet().applyActiveStyle()
        render()

        val mouseOverLogElement = logElementBuffer.getLogElementContainingPosition(action.position - absolutePosition() - contentPosition())
        if (mouseOverLogElement != null && mouseOverLogElement is HyperLinkElement)
            EventBus.broadcast(ZirconEvent.TriggeredHyperLink(mouseOverLogElement.linkId))

    }

    override fun mouseMoved(action: MouseAction) {
        val mouseOverLogElement = logElementBuffer.getLogElementContainingPosition(action.position - absolutePosition() - contentPosition())
        if (mouseOverLogElement is HyperLinkElement) {
            hyperLinkElementOnMouseOver = mouseOverLogElement
            mouseOverLogElement.modifiers = setOf(SimpleModifiers.Glow)
            render()
        }
        if (mouseOverLogElement !is HyperLinkElement && hyperLinkElementOnMouseOver != null) {
            hyperLinkElementOnMouseOver!!.modifiers = null
            hyperLinkElementOnMouseOver = null
            render()
        }


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

        val maxWidth = logElementBuffer.getAllLogElements().lastOrNull { it.renderedPositionArea != null }?.renderedPositionArea?.endPosition?.x?.plus(1)
        val maxHeight = logElementBuffer.getAllLogElements().lastOrNull { it.renderedPositionArea != null }?.renderedPositionArea?.endPosition?.y?.plus(1)

        if (maxWidth != null) {
            if (size().width() < maxWidth || size().height() < maxHeight!!) {
                setActualSize(Size.create(max(maxWidth, size().width()), max(maxHeight!!, size().height())))
            }
        }
    }

    private fun enableFocusedComponent() {
        componentStyleSet().applyFocusedStyle()
        render()
    }


}

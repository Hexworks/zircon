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
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.behavior.impl.DefaultCursorHandler
import org.hexworks.zircon.internal.behavior.impl.DefaultScrollable
import org.hexworks.zircon.internal.component.impl.DefaultComponent

class DefaultLogArea constructor(
        private val renderingStrategy: ComponentRenderingStrategy<LogArea>,
        tileset: TilesetResource,
        size: Size,
        position: Position,
        componentStyleSet: ComponentStyleSet,
        scrollable: Scrollable = DefaultScrollable(size, size),
        cursorHandler: CursorHandler = DefaultCursorHandler(size))
    : LogArea, Scrollable by scrollable, CursorHandler by cursorHandler, DefaultComponent(
        position = position,
        size = size,
        tileset = tileset,
        componentStyles = componentStyleSet,
        renderer = renderingStrategy) {

    private val logElementBuffer = LogElementBuffer()

    init {
        refreshVirtualSpaceSize()
        render()
    }

    override fun addText(text: String) {
        logElementBuffer.addLogElement(TextElement(text))
    }

    override fun addHyperLink(linkText: String, linkId: String) {
        logElementBuffer.addLogElement(HyperLinkElement(linkText, linkId))
    }

    override fun addNewRow() {
        logElementBuffer.addNewRow()
    }

    override fun getLogElements(): LogElementBuffer {
        return logElementBuffer
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


    private fun refreshVirtualSpaceSize() {
        val (visibleCols, visibleRows) = size()
        val (textCols, textRows) = logElementBuffer.getBoundingBoxSize()
        if (textCols >= visibleCols && textRows >= visibleRows) {
            setActualSize(logElementBuffer.getBoundingBoxSize())
        }
    }
}

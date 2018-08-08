package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.builder.component.ComponentStyleSetBuilder
import org.codetome.zircon.api.builder.graphics.StyleSetBuilder
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.component.Button
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.event.EventBus
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.api.util.Maybe
import org.codetome.zircon.internal.component.WrappingStrategy
import org.codetome.zircon.internal.event.InternalEvent
import org.codetome.zircon.internal.util.ThreadSafeQueue

class DefaultButton(private val text: String,
                    initialTileset: TilesetResource<out Tile>,
                    wrappers: ThreadSafeQueue<WrappingStrategy>,
                    initialSize: Size,
                    position: Position,
                    componentStyleSet: ComponentStyleSet)
    : Button, DefaultComponent(size = initialSize,
        position = position,
        componentStyleSet = componentStyleSet,
        wrappers = wrappers,
        tileset = initialTileset) {

    init {
        getDrawSurface().putText(text, getWrapperOffset())

        EventBus.listenTo<InternalEvent.MousePressed>(id) {
            getDrawSurface().applyStyle(getComponentStyles().activate())
        }
        EventBus.listenTo<InternalEvent.MouseReleased>(id) {
            getDrawSurface().applyStyle(getComponentStyles().mouseOver())
        }
    }

    override fun acceptsFocus(): Boolean {
        return true
    }

    override fun giveFocus(input: Maybe<Input>): Boolean {
        getDrawSurface().applyStyle(getComponentStyles().giveFocus())
        return true
    }

    override fun takeFocus(input: Maybe<Input>) {
        getDrawSurface().applyStyle(getComponentStyles().reset())
    }

    override fun getText() = text

    override fun applyColorTheme(colorTheme: ColorTheme) {
        setComponentStyles(ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getAccentColor())
                        .backgroundColor(TextColor.transparent())
                        .build())
                .mouseOverStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getBrightBackgroundColor())
                        .backgroundColor(colorTheme.getAccentColor())
                        .build())
                .focusedStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getDarkBackgroundColor())
                        .backgroundColor(colorTheme.getAccentColor())
                        .build())
                .activeStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getDarkForegroundColor())
                        .backgroundColor(colorTheme.getAccentColor())
                        .build())
                .build())
    }
}

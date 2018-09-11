package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.component.ComponentDecorationRenderer
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.util.ThreadSafeQueue

class DefaultButton(private val text: String,
                    initialTileset: TilesetResource,
                    wrappers: ThreadSafeQueue<ComponentDecorationRenderer>,
                    initialSize: Size,
                    position: Position,
                    componentStyleSet: ComponentStyleSet)
    : Button, DefaultComponent(size = initialSize,
        position = position,
        componentStyles = componentStyleSet,
        wrappers = wrappers,
        tileset = initialTileset) {

    init {
        tileGraphics().putText(text, wrapperOffset())

        EventBus.listenTo<ZirconEvent.MousePressed>(id) {
            applyStyle(componentStyleSet().applyActiveStyle())
        }
        EventBus.listenTo<ZirconEvent.MouseReleased>(id) {
            applyStyle(componentStyleSet().applyMouseOverStyle())
        }
    }

    override fun acceptsFocus(): Boolean {
        return true
    }

    override fun giveFocus(input: Maybe<Input>): Boolean {
        tileGraphics().applyStyle(componentStyleSet().applyFocusedStyle())
        return true
    }

    override fun takeFocus(input: Maybe<Input>) {
        tileGraphics().applyStyle(componentStyleSet().reset())
    }

    override fun getText() = text

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.accentColor())
                        .backgroundColor(TileColor.transparent())
                        .build())
                .mouseOverStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.primaryBackgroundColor())
                        .backgroundColor(colorTheme.accentColor())
                        .build())
                .focusedStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.secondaryBackgroundColor())
                        .backgroundColor(colorTheme.accentColor())
                        .build())
                .activeStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.secondaryForegroundColor())
                        .backgroundColor(colorTheme.accentColor())
                        .build())
                .build().also {
                    setComponentStyleSet(it)
                }
    }
}

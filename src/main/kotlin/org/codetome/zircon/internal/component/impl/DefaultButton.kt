package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.builder.TextImageBuilder
import org.codetome.zircon.api.component.Button
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.Theme
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.api.shape.FilledRectangleFactory
import org.codetome.zircon.internal.component.WrappingStrategy
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import java.util.function.Consumer

class DefaultButton(private val text: String,
                    initialSize: Size,
                    position: Position,
                    componentStyles: ComponentStyles,
                    wrappers: Iterable<WrappingStrategy>)
    : Button, DefaultComponent(initialSize = initialSize,
        position = position,
        componentStyles = componentStyles,
        wrappers = wrappers) {

    init {
        val image = TextImageBuilder.newBuilder()
                .size(getEffectiveSize())
                .build()
        image.applyStyle(componentStyles.getCurrentStyle())
        image.putText(text, Position.DEFAULT_POSITION)
        image.drawOnto(
                surface = getDrawSurface(),
                offset = getOffset())

        EventBus.subscribe<MouseAction>(EventType.MousePressed(getId()), {
            getDrawSurface().applyStyle(getComponentStyles().activate())
            EventBus.emit(EventType.ComponentChange)
        })
        EventBus.subscribe<MouseAction>(EventType.MouseReleased(getId()), {
            getDrawSurface().applyStyle(getComponentStyles().mouseOver())
            EventBus.emit(EventType.ComponentChange)
        })
    }

    override fun getText() = text

    override fun applyTheme(theme: Theme) {
        setComponentStyles(ComponentStylesBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(theme.getAccentColor())
                        .backgroundColor(theme.getDarkBackgroundColor())
                        .build())
                .mouseOverStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(theme.getAccentColor())
                        .backgroundColor(theme.getBrightBackgroundColor())
                        .build())
                .activeStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(theme.getAccentColor())
                        .backgroundColor(theme.getDarkForegroundColor())
                        .build())
                .build())
    }

    override fun onMousePressed(callback: Consumer<MouseAction>) {
        EventBus.subscribe<MouseAction>(EventType.MousePressed(getId()), { (mouseAction) ->
            callback.accept(mouseAction)
        })
    }

    override fun onMouseReleased(callback: Consumer<MouseAction>) {
        EventBus.subscribe<MouseAction>(EventType.MouseReleased(getId()), { (mouseAction) ->
            callback.accept(mouseAction)
        })
    }
}
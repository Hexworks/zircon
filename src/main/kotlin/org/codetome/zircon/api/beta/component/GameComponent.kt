package org.codetome.zircon.api.beta.component

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.builder.LayerBuilder
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.internal.behavior.Scrollable
import org.codetome.zircon.internal.behavior.impl.DefaultBoundable
import org.codetome.zircon.internal.behavior.impl.DefaultScrollable
import org.codetome.zircon.internal.component.impl.DefaultComponent
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import java.util.*

class GameComponent @JvmOverloads constructor(private val gameArea: GameArea,
                                              visibleSize: Size,
                                              initialFont: Font,
                                              position: Position,
                                              componentStyles: ComponentStyles,
                                              boundable: DefaultBoundable = DefaultBoundable(
                                                      size = visibleSize,
                                                      position = position),
                                              private val scrollable: Scrollable = DefaultScrollable(
                                                      visibleSpaceSize = visibleSize,
                                                      virtualSpaceSize = gameArea.getSize().to2DSize()))

    : Scrollable by scrollable, DefaultComponent(
        initialSize = visibleSize,
        position = position,
        componentStyles = componentStyles,
        wrappers = listOf(),
        initialFont = initialFont,
        boundable = boundable) {

    init {
        refreshVirtualSpaceSize()
    }

    override fun acceptsFocus(): Boolean {
        return true
    }

    override fun giveFocus(input: Optional<Input>): Boolean {
        refreshVirtualSpaceSize()
        EventBus.emit(EventType.ComponentChange)
        return true
    }

    override fun takeFocus(input: Optional<Input>) {
    }

    override fun applyColorTheme(colorTheme: ColorTheme) {
    }

    override fun transformToLayers(): List<Layer> {
        // note that the draw surface which comes from `DefaultComponent` is not used here
        // since the `GameArea` is used as a backend
        return gameArea.getSegmentAt(
                offset = Position3D.from2DPosition(getVisibleOffset()),
                size = getBoundableSize()).layers.map {
            LayerBuilder.newBuilder()
                    .textImage(it)
                    .offset(getPosition())
                    .build()
        }
    }

    private fun refreshVirtualSpaceSize() {
        setVirtualSpaceSize(gameArea.getSize().to2DSize())
    }

    override fun containsBoundable(boundable: Boundable): Boolean {
        return getBoundable().containsBoundable(boundable)
    }

    override fun containsPosition(position: Position): Boolean {
        return getBoundable().containsPosition(position)
    }

    override fun getBoundableSize(): Size {
        return getBoundable().getBoundableSize()
    }

    override fun getPosition(): Position {
        return getBoundable().getPosition()
    }

    override fun intersects(boundable: Boundable): Boolean {
        return getBoundable().intersects(boundable)
    }

}
package org.codetome.zircon.api.beta.component

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.Layerable
import org.codetome.zircon.api.builder.LayerBuilder
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.internal.behavior.Scrollable
import org.codetome.zircon.internal.behavior.impl.DefaultBoundable
import org.codetome.zircon.internal.behavior.impl.DefaultLayerable
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
                                                      cursorSpaceSize = visibleSize,
                                                      virtualSpaceSize = gameArea.getSize()),
                                              private val layerable: Layerable = DefaultLayerable(
                                                      supportedFontSize = initialFont.getSize(),
                                                      size = visibleSize,
                                                      boundable = boundable))

    : Scrollable by scrollable, Layerable by layerable, DefaultComponent(
        initialSize = visibleSize,
        position = position,
        componentStyles = componentStyles,
        wrappers = listOf(),
        initialFont = initialFont,
        boundable = boundable) {

    private val player: Layer = LayerBuilder()
            .filler(TextCharacterBuilder.newBuilder()
                    .character('@')
                    .backgroundColor(TextColorFactory.TRANSPARENT)
                    .foregroundColor(ANSITextColor.WHITE)
                    .build())
            .size(Size.ONE)
            .build()

    init {
        refreshDrawSurface()
        refreshVirtualSpaceSize()
    }

    override fun setPosition(position: Position) {
        super.setPosition(position)
        resetPlayerPosition()
    }

    override fun acceptsFocus(): Boolean {
        return true
    }

    override fun giveFocus(input: Optional<Input>): Boolean {
        refreshDrawSurface()
        refreshVirtualSpaceSize()
        EventBus.emit(EventType.ComponentChange)
        return true
    }

    override fun takeFocus(input: Optional<Input>) {
    }

    override fun applyColorTheme(colorTheme: ColorTheme) {
    }

    override fun transformToLayers(): List<Layer> {
        return mutableListOf(LayerBuilder.newBuilder()
                .textImage(gameArea.getSegmentImage(getVisibleOffset(), getBoundableSize()))
                .offset(getPosition())
                .build()).also {
            it.addAll(layerable.getLayers())
            it.add(player)
        }
    }

    private fun resetPlayerPosition(): Position {
        val newPos = Position
                .of(getBoundableSize().columns / 2, getBoundableSize().rows / 2)
                .plus(getPosition())
        player.moveTo(newPos)
        return newPos
    }

    private fun refreshDrawSurface() {
        getBoundableSize().fetchPositions().forEach { pos ->
            val fixedPos = pos + getVisibleOffset()
            getDrawSurface().setCharacterAt(pos, gameArea.getCharacterAt(fixedPos).get())
        }
    }

    private fun refreshVirtualSpaceSize() {
        setVirtualSpaceSize(gameArea.getSize())
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
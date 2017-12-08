package org.codetome.zircon.api.beta.component

import org.codetome.zircon.api.Beta
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.builder.LayerBuilder
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.sam.TextCharacterTransformer
import org.codetome.zircon.api.util.darkenColorByPercent
import org.codetome.zircon.internal.behavior.Scrollable3D
import org.codetome.zircon.internal.behavior.impl.DefaultBoundable
import org.codetome.zircon.internal.behavior.impl.DefaultScrollable3D
import org.codetome.zircon.internal.component.impl.DefaultComponent
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import java.util.*

@Beta
class GameComponent @JvmOverloads constructor(private val gameArea: GameArea,
                                              visibleSize: Size3D,
                                              initialFont: Font,
                                              position: Position,
                                              componentStyles: ComponentStyles,
                                              boundable: DefaultBoundable = DefaultBoundable(
                                                      size = visibleSize.to2DSize(),
                                                      position = position),
                                              private val scrollable: Scrollable3D = DefaultScrollable3D(
                                                      visibleSpaceSize = visibleSize,
                                                      virtualSpaceSize = gameArea.getSize()))

    : Scrollable3D by scrollable, DefaultComponent(
        initialSize = visibleSize.to2DSize(),
        position = position,
        componentStyles = componentStyles,
        wrappers = listOf(),
        initialFont = initialFont,
        boundable = boundable) {

    private val visibleLevelCount = Math.min(visibleSize.height, MAX_VISIBLE_LEVEL_COUNT)

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
        val allLevelCount = scrollable.getVirtualSpaceSize().height
        val startLevel = scrollable.getVisibleOffset().z
        val percentage: Double = 1.0.div(visibleLevelCount.toDouble())
        var levelCount = 1
        return (startLevel until Math.min(startLevel + visibleLevelCount, allLevelCount)).toList().flatMap { levelIdx ->
            val currPercentage = percentage.times(levelCount)
            levelCount++
            gameArea.getSegmentAt(
                    offset = Position3D.from2DPosition(getVisibleOffset().to2DPosition(), levelIdx),
                    size = getBoundableSize()).layers
        }.map {
            LayerBuilder.newBuilder()
                    .textImage(it)
                    .offset(getPosition())
                    .build()
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

    companion object {

        val MAX_VISIBLE_LEVEL_COUNT = 5
    }
}
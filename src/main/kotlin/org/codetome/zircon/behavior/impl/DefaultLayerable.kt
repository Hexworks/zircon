package org.codetome.zircon.behavior.impl

import org.codetome.zircon.Position
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.behavior.Boundable
import org.codetome.zircon.behavior.Layerable
import org.codetome.zircon.graphics.layer.Layer
import org.codetome.zircon.terminal.Size
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

class DefaultLayerable private constructor(boundable: Boundable) : Layerable, Boundable by boundable {

    constructor(offset: Position, size: Size) : this(DefaultBoundable(offset, size))

    private val overlays: Queue<Layer> = LinkedBlockingQueue()
    private val underlays: Queue<Layer> = LinkedBlockingQueue()

    override fun addOverlay(layer: Layer) {
        overlays.add(layer)
    }

    override fun addBackgroundLayer(layer: Layer) {
        underlays.add(layer)
    }

    override fun popOverlay() = Optional.ofNullable(overlays.poll())

    override fun popBackgroundLayer() = Optional.ofNullable(underlays.poll())

    override fun removeLayer(layer: Layer) {
        overlays.remove(layer)
        underlays.remove(layer)
    }

    override fun fetchOverlayZIntersectionForPosition(absolutePosition: Position): List<TextCharacter> {
        return fetchZIntersectionFor(overlays, absolutePosition)
    }

    override fun fetchBackgroundZIntersectionForPosition(absolutePosition: Position): List<TextCharacter> {
        return fetchZIntersectionFor(underlays, absolutePosition)
    }

    private fun fetchZIntersectionFor(queue: Queue<Layer>, position: Position): List<TextCharacter> {
        return queue.filter { layer ->
            layer.containsPosition(position)
        }.map { layer ->
            layer.getCharacterAt(position)
        }
    }
}
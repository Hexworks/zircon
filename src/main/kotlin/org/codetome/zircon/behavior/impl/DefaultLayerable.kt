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

    constructor(size: Size, position: Position) : this(DefaultBoundable(position, size))

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

    override fun fetchBackgroundZIntersectionForPosition(absolutePosition: Position): List<TextCharacter> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchOverlayZIntersectionForPosition(absolutePosition: Position): List<TextCharacter> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
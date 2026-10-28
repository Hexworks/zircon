package org.hexworks.zircon.compose

import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.tileset.TilesetFactory
import org.hexworks.zircon.api.uievent.UIEvent
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.compose.tileset.TilesetRegistry
import org.hexworks.zircon.internal.renderer.impl.BaseRenderer

/**
 * The tick of the Compose renderer: [BaseRenderer] processes input, animations and
 * blinking, then has every visible tile write itself into the [FrameData]. Drawing the
 * frame is the composable's job ([TileGridView]).
 */
public class ComposeRenderer private constructor(
    tileGrid: TileGrid,
    public val registry: TilesetRegistry,
) : BaseRenderer<FrameData, Unit>(tileGrid, registry) {

    public constructor(
        tileGrid: TileGrid,
        extraTilesetFactories: List<TilesetFactory<FrameData>> = emptyList(),
    ) : this(tileGrid, TilesetRegistry(extraTilesetFactories))

    private val pendingInput = ArrayDeque<Pair<UIEvent, UIEventPhase>>()

    /**
     * Queues an input event; it is delivered to the grid on the next tick, on the thread
     * that renders, so the component tree is never touched concurrently.
     */
    public fun enqueue(event: UIEvent, phase: UIEventPhase = UIEventPhase.TARGET) {
        pendingInput.addLast(event to phase)
    }

    override fun create() {}

    override fun processInputEvents() {
        while (pendingInput.isNotEmpty()) {
            val (event, phase) = pendingInput.removeFirst()
            tileGrid.process(event, phase)
        }
    }

    override fun prepareRender(context: FrameData) {
        context.clear()
    }
}

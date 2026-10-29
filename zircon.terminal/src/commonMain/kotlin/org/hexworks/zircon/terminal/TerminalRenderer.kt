package org.hexworks.zircon.terminal

import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.uievent.UIEvent
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.internal.renderer.impl.BaseRenderer

/**
 * The tick of the terminal renderer: delivers queued input to the grid, runs animations and
 * blink, then draws every layer into a [CellBuffer]. Turning the buffer into bytes is
 * [AnsiEncoder]'s job; [runTerminal] drives both.
 */
public class TerminalRenderer(tileGrid: TileGrid) : BaseRenderer<CellBuffer, Unit>(tileGrid, TextTilesetLoader()) {

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

    override fun prepareRender(context: CellBuffer) {
        context.clear()
    }
}

package org.hexworks.zircon.api.uievent

import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.screen.Screen

/**
 * Represents the possible phases of event propagation for an [UIEvent] which is
 * very similar to how it works in a
 * [browser](https://cdn.discordapp.com/attachments/527956007901724672/532686794798661653/eventflow.png).
 */
enum class UIEventPhase {
    /**
     * The capture phase happens when the ui components are being traversed
     * from the root to the target. Happens before [TARGET] and [BUBBLE].
     */
    CAPTURE,
    /**
     * The target phase is when the target receives the event.
     * Note that for [TileGrid]s and [Screen]s this is the only phase
     * which will happen. Happens after [CAPTURE] and before [BUBBLE]
     */
    TARGET,
    /**
     * The bubbling phase happens when the ui components are being traversed
     * backwards from the target to the root. Happens after [CAPTURE] and [TARGET]
     */
    BUBBLE
}

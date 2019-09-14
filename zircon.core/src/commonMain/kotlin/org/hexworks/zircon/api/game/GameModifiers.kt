package org.hexworks.zircon.api.game

import org.hexworks.zircon.api.modifier.Modifier

/**
 * [Modifier]s for the [GameComponent].
 */
enum class GameModifiers : Modifier {

    BLOCK_TOP,
    BLOCK_BACK,
    BLOCK_FRONT,
    BLOCK_BOTTOM,
    BLOCK_LAYER;

    override fun generateCacheKey(): String {
        return "Modifier.GameModifier.$name"
    }
}

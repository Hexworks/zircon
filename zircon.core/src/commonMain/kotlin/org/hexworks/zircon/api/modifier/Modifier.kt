package org.hexworks.zircon.api.modifier

import org.hexworks.zircon.api.behavior.Cacheable
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.grid.TileGrid

/**
 * A [Modifier] adds an effect to a [Tile] when it is rendered on
 * a [TileGrid]. A [TileModifier] transforms a [Tile] to another one before
 * rendering: use it for tile variations or time-based effects like
 * [FadeIn], [FadeOut] or [Markov].
 */
public interface Modifier : Cacheable

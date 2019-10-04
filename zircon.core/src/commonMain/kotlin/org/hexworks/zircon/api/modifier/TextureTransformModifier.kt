package org.hexworks.zircon.api.modifier

import org.hexworks.zircon.api.data.Tile

/**
 * A [TextureTransformModifier] is a kind of [Modifier]
 * which transforms the actual texture represented by
 * a [Tile] before rendering.
 */
interface TextureTransformModifier : Modifier

package org.hexworks.zircon.api.graphics.extensions

import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.LayerHandle

/**
 * Moves this [Layer] up by one level.
 * @return `true` if the operation was successful `false` if not
 */
fun LayerHandle.moveOneLevelUp(): Boolean = moveByLevel(1)

/**
 * Moves this [Layer] down by one level.
 * @return `true` if the operation was successful `false` if not
 */
fun LayerHandle.moveOneLevelDown(): Boolean = moveByLevel(-1)

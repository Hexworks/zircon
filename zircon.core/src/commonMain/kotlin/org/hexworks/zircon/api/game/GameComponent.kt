@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

package org.hexworks.zircon.api.game

import org.hexworks.zircon.api.Beta
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile

@Beta
interface GameComponent<T: Tile, B : Block<T>> : Component

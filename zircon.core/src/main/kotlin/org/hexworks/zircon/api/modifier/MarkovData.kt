package org.hexworks.zircon.api.modifier

import org.hexworks.zircon.api.behavior.Cacheable
import org.hexworks.zircon.api.data.Tile

interface MarkovData : Cacheable {

    fun first(): MarkovData

    fun next(): MarkovData

    fun tile(): Tile
}

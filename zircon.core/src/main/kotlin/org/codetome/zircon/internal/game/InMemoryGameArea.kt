package org.codetome.zircon.internal.game

import org.codetome.zircon.api.Block
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.game.GameArea
import org.codetome.zircon.api.game.Position3D
import org.codetome.zircon.api.game.Size3D
import org.codetome.zircon.api.graphics.TextImage
import java.util.concurrent.ConcurrentHashMap

class InMemoryGameArea(private val size: Size3D) : GameArea {

    private val blocks: Map<Position3D, Iterable<TextCharacter>> = ConcurrentHashMap()

    override fun getSize() = size

    override fun fetchBlocksAt(offset: Position3D, size: Size3D): Iterable<Block> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayersAt(position: Position3D): Iterable<TextCharacter> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayerAt(level: Int, layerIdx: Int): TextImage {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setCharactersAt(position: Position3D, characters: List<TextCharacter>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setCharacterAt(position: Position3D, layerIdx: Int, character: TextCharacter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

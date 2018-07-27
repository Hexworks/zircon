package org.codetome.zircon.internal.graphics

import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.FontOverride
import org.codetome.zircon.api.builder.graphics.TileImageBuilder
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.graphics.TileImage
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.internal.behavior.impl.DefaultFontOverride
import org.codetome.zircon.internal.behavior.impl.Rectangle

class DefaultLayer(size: Size,
                   offset: Position,
                   initialTileset: Tileset,
                   private val fontOverride: FontOverride = DefaultFontOverride(
                           initialTileset = initialTileset),
                   private val tileImage: TileImage = TileImageBuilder.newBuilder()
                           .size(size)
                           .build())
    : Layer, TileImage by tileImage, FontOverride by fontOverride {


    private var position: Position
    private var rect: Rectangle

    init {
        this.position = offset
        this.rect = refreshRect()
    }

    override fun fill(filler: Tile): Layer {
        tileImage.fill(filler)
        return this
    }

    override fun fetchFilledPositions() = tileImage.fetchFilledPositions().map {
        it + position
    }

    override fun fetchPositions() = getBoundableSize().fetchPositions()
            .map { it + position }
            .toSet()

    override fun getPosition() = position

    override fun moveTo(position: Position) =
            if (this.position == position) {
                false
            } else {
                this.position = position
                this.rect = refreshRect()
                true
            }

    override fun intersects(boundable: Boundable) = rect.intersects(
            Rectangle(
                    boundable.getPosition().x,
                    boundable.getPosition().y,
                    boundable.getBoundableSize().xLength,
                    boundable.getBoundableSize().yLength))

    override fun containsPosition(position: Position): Boolean {
        return rect.contains(position)
    }

    override fun containsBoundable(boundable: Boundable) = rect.contains(
            Rectangle(
                    position.x,
                    position.y,
                    boundable.getBoundableSize().xLength,
                    boundable.getBoundableSize().yLength))

    override fun getTileAt(position: Position) = tileImage.getTileAt(position - this.position)

    override fun getRelativeTileAt(position: Position) = tileImage.getTileAt(position)

    override fun setTileAt(position: Position, tile: Tile) {
        tileImage.setTileAt(position - this.position, tile)
    }

    override fun setRelativeTileAt(position: Position, character: Tile) {
        tileImage.setTileAt(position, character)
    }

    override fun createCopy() = DefaultLayer(
            size = tileImage.getBoundableSize(),
            offset = getPosition(),
            initialTileset = getCurrentFont(),
            tileImage = tileImage)

    private fun refreshRect(): Rectangle {
        return Rectangle(position.x, position.y, getBoundableSize().xLength, getBoundableSize().yLength)
    }
}

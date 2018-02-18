package org.codetome.zircon.internal.graphics

import org.codetome.zircon.api.Cell
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.api.graphics.TextImageBase
import org.codetome.zircon.api.sam.TextCharacterTransformer
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MapLikeTextImage(size: Size,
                       styleSet: StyleSet = StyleSetBuilder.DEFAULT_STYLE)
    : TextImageBase(size = size, styleSet = styleSet) {

    private val backend = ConcurrentHashMap<Position, TextCharacter>()

    override fun getCharacterAt(position: Position): Optional<TextCharacter> {
        return if(getBoundableSize().containsPosition(position)) {
            Optional.of(backend.getOrDefault(position, TextCharacterBuilder.EMPTY))
        } else {
            Optional.empty()
        }
    }

    override fun setCharacterAt(position: Position, character: TextCharacter): Boolean {
        return if(getBoundableSize().containsPosition(position)) {
            backend[position] = character
            true
        } else {
            false
        }
    }

    override fun combineWith(textImage: TextImage, offset: Position): TextImage {
        val columns = Math.max(getBoundableSize().xLength, offset.x + textImage.getBoundableSize().xLength)
        val rows = Math.max(getBoundableSize().yLength, offset.y + textImage.getBoundableSize().yLength)

        val surface = resize(Size.of(columns, rows), EMPTY_POSITION)
        surface.draw(textImage, offset)
        return surface
    }

    override fun drawOnto(surface: DrawSurface, offset: Position) {
        backend.forEach { pos, tc ->
            surface.setCharacterAt(pos + offset, tc)
        }
    }

    override fun fetchCellsBy(offset: Position, size: Size): Iterable<Cell> {
        return size.fetchPositions()
                .map { it + offset }
                .intersect(getBoundableSize().fetchPositions())
                .map { Cell(it, getCharacterAt(it).get()) }
    }

    override fun resize(newSize: Size, filler: TextCharacter): TextImage {
        val result = MapLikeTextImage(newSize, toStyleSet())
        backend.filter { (pos) -> newSize.containsPosition(pos) }
                .forEach { pos, tc ->
                    result.setCharacterAt(pos, tc)
                }
        newSize.fetchPositions().subtract(getBoundableSize().fetchPositions()).forEach {
            result.setCharacterAt(it, filler)
        }
        return result
    }

    override fun toSubImage(offset: Position, size: Size): TextImage {
        val result = MapLikeTextImage(size, toStyleSet())
        size.fetchPositions()
                .map { it + offset }
                .intersect(getBoundableSize().fetchPositions())
                .forEach {
                    result.setCharacterAt(it - offset, getCharacterAt(it).get())
                }
        return result
    }

    override fun transform(transformer: TextCharacterTransformer): TextImage {
        val result = MapLikeTextImage(getBoundableSize(), toStyleSet())
        fetchCells().forEach { (pos, char) ->
            result.setCharacterAt(pos, transformer.transform(char))
        }
        return result
    }

    companion object {
        val EMPTY_POSITION = TextCharacterBuilder.EMPTY
    }
}

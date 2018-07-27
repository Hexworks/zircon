package org.codetome.zircon.api.graphics

import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.behavior.Drawable
import org.codetome.zircon.api.behavior.Styleable
import org.codetome.zircon.api.builder.data.TileBuilder
import org.codetome.zircon.api.data.Cell
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.sam.TextCharacterTransformer
import org.codetome.zircon.api.util.Math
import org.codetome.zircon.api.util.Maybe
import org.codetome.zircon.internal.behavior.impl.DefaultBoundable
import org.codetome.zircon.internal.behavior.impl.DefaultStyleable
import org.codetome.zircon.internal.graphics.ConcurrentTileImage
import org.codetome.zircon.platform.extension.getOrDefault

abstract class TileImageBase private constructor(boundable: Boundable,
                                                 styleable: Styleable,
                                                 tiles: Map<Position, Tile>,
                                                 private val backend: MutableMap<Position, Tile>)
    : TileImage, Boundable by boundable, Styleable by styleable {

    constructor(size: Size,
                styleSet: StyleSet,
                backend: MutableMap<Position, Tile>,
                tiles: Map<Position, Tile>) : this(
            boundable = DefaultBoundable(size = size),
            styleable = DefaultStyleable(styleSet),
            backend = backend,
            tiles = tiles)

    init {
        tiles.entries.forEach {
            backend[it.key] = it.value
        }
    }

    override fun toString(): String {
        return (0 until getBoundableSize().yLength).joinToString("") { y ->
            (0 until getBoundableSize().xLength).map { x ->
                backend.getOrDefault(Position.create(x, y), Tile.empty()).getCharacter()
            }.joinToString("").plus("\n")
        }
    }

    override fun getTileAt(position: Position): Maybe<Tile> {
        return if (containsPosition(position)) {
            Maybe.of(backend[position] ?: Tile.empty())
        } else {
            Maybe.empty()
        }
    }

    override fun setTileAt(position: Position, tile: Tile) {
        if (getBoundableSize().containsPosition(position) && getTileAt(position) != tile) {
            backend[position] = tile
        }
    }

    override fun setCharAt(position: Position, char: Char) {
        if (getBoundableSize().containsPosition(position)) {
            backend[position] = TileBuilder.newBuilder()
                    .character(char)
                    .styleSet(toStyleSet())
                    .build()
        }
    }

    override fun createSnapshot(): List<Cell> {
        return backend.map { Cell(it.key, it.value) }
    }

    override fun draw(drawable: Drawable, offset: Position) {
        drawable.drawOnto(this, offset)
    }

    override fun drawOnto(surface: DrawSurface, offset: Position) {
        fetchCells().filterNot { it.tile == Tile.empty() }.forEach { (pos, tile) ->
            tile.drawOnto(surface, pos + offset)
        }
    }

    override fun fetchFilledPositions() = backend.keys.sorted()

    override fun combineWith(tileImage: TileImage, offset: Position): TileImage {
        val columns = Math.max(getBoundableSize().xLength, offset.x + tileImage.getBoundableSize().xLength)
        val rows = Math.max(getBoundableSize().yLength, offset.y + tileImage.getBoundableSize().yLength)

        val surface = resize(Size.create(columns, rows))
        surface.draw(tileImage, offset)
        return surface
    }

    override fun putText(text: String, position: Position) {
        text.forEachIndexed { col, char ->
            setTileAt(position.withRelativeX(col), TileBuilder
                    .newBuilder()
                    .styleSet(toStyleSet())
                    .character(char)
                    .build())
        }
    }

    override fun applyStyle(styleSet: StyleSet, offset: Position, size: Size) {
        setStyleFrom(styleSet)
        size.fetchPositions().forEach { pos ->
            pos.plus(offset).let { fixedPos ->
                getTileAt(fixedPos).map { char: Tile ->
                    setTileAt(fixedPos, char.withStyle(styleSet))
                }
            }
        }
    }

    override fun fetchCells(): Iterable<Cell> {
        return fetchCellsBy(Position.defaultPosition(), getBoundableSize())
    }

    override fun fetchCellsBy(offset: Position, size: Size): Iterable<Cell> {
        return size.fetchPositions()
                .map { it + offset }
                .map { Cell(it, getTileAt(it).get()) }
    }

    override fun resize(newSize: Size, filler: Tile): TileImage {
        val result = ConcurrentTileImage(
                size = newSize,
                styleSet = toStyleSet())
        backend.filter { (pos) -> newSize.containsPosition(pos) }
                .forEach { (pos, tc) ->
                    result.setTileAt(pos, tc)
                }

        newSize.fetchPositions().subtract(getBoundableSize().fetchPositions()).forEach {
            result.setTileAt(it, filler)
        }
        return result
    }

    override fun fill(filler: Tile): TileImage {
        getBoundableSize().fetchPositions().filter { pos ->
            getTileAt(pos).map { it == Tile.empty() }.orElse(false)
        }.forEach { pos ->
            setTileAt(pos, filler)
        }
        return this
    }

    override fun toSubImage(offset: Position, size: Size): TileImage {
        val result = ConcurrentTileImage(size, toStyleSet())
        size.fetchPositions()
                .map { it + offset }
                .intersect(getBoundableSize().fetchPositions())
                .forEach {
                    result.setTileAt(it - offset, getTileAt(it).get())
                }
        return result
    }

    override fun transform(transformer: TextCharacterTransformer): TileImage {
        val result = ConcurrentTileImage(getBoundableSize(), toStyleSet())
        fetchCells().forEach { (pos, char) ->
            result.setTileAt(pos, transformer.transform(char))
        }
        return result
    }
}

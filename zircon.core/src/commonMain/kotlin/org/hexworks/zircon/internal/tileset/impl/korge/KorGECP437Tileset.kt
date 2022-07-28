package org.hexworks.zircon.internal.tileset.impl.korge

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import kotlin.reflect.KClass

class KorGECP437Tileset(
    private val resource: TilesetResource
) : Tileset<KorGECP437DrawSurface, CharacterTile> {

    override val id: UUID = UUID.randomUUID()

    override val width: Int
        get() = resource.width

    override val height: Int
        get() = resource.height

    override val targetType: KClass<KorGECP437DrawSurface> = KorGECP437DrawSurface::class

    override fun drawTile(tile: CharacterTile, surface: KorGECP437DrawSurface, position: Position) {
        surface.drawTile(tile, position)
    }
}
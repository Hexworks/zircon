package org.hexworks.zircon.internal

import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.internal.resource.BuiltInTrueTypeFontResource

object TilesetResources {

    fun allTextTilesetsCompatibleWith(tileset: TilesetResource): List<TilesetResource> {
        val width = tileset.width
        val height = tileset.height
        val result: MutableList<TilesetResource> = BuiltInCP437TilesetResource.values().filter {
            it.width == width && it.height == height
        }.toMutableList()
        when {
            width * 2 == height -> result.addAll(BuiltInTrueTypeFontResource.narrowFonts(height))
            height * 2 == width -> result.addAll(BuiltInTrueTypeFontResource.wideFonts(height))
            width == height -> result.addAll(BuiltInTrueTypeFontResource.squareFonts(height))
        }
        return result
    }
}

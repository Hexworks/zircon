package org.hexworks.zircon.api.resource

/**
 * Contains the tileset types supported by Zircon.
 */
sealed class TilesetType {

    /**
     * A CP437 tileset is a single image that contains all *256* characters supported by Code Page 437.
     * You can read more about this [here](https://en.wikipedia.org/wiki/Code_page_437).
     */
    object CP437Tileset : TilesetType()

    /**
     * A true type font uses a `ttf` file to find the characters.
     */
    object TrueTypeFont : TilesetType()

    /**
     * A graphical tileset uses Zircon's own tileset format and usually contains sprite sheets.
     */
    object GraphicalTileset : TilesetType()

    /**
     * Use this tileset type when you want to implement your own tileset loading mechanism.
     * Zircon will use the `toString` function internally on the [key] object so make sure
     * that you implement it.
     */
    data class CustomTileset<T>(
        val key: T
    ) : TilesetType()

    companion object
}




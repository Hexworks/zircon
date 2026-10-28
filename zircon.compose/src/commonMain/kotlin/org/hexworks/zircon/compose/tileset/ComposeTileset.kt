package org.hexworks.zircon.compose.tileset

import androidx.compose.ui.graphics.ImageBitmap
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.compose.FrameData
import kotlin.reflect.KClass

/**
 * A tileset for the Compose renderer. "Drawing" a tile means writing a cell record into the
 * [FrameData]; the pixels come from [atlas], a sprite sheet the shader samples. Atlases are
 * loaded asynchronously, and until [atlas] is set the tileset writes backgrounds only.
 */
public abstract class ComposeTileset(
    public val resource: TilesetResource,
) : Tileset<FrameData> {

    override val id: UUID = UUID.randomUUID()
    override val targetType: KClass<FrameData> = FrameData::class
    override val width: Int = resource.width
    override val height: Int = resource.height

    /**
     * Assigned by the [TilesetRegistry]; `0` is reserved for "no atlas".
     */
    public var atlasId: Int = FrameData.NO_ATLAS
        internal set

    /**
     * The sprite sheet, once loaded.
     */
    public var atlas: ImageBitmap? = null
        protected set

    /**
     * Size of one atlas cell in pixels. Defaults to the tile size; a glyph cache may differ.
     */
    public open val atlasCellWidth: Int get() = width
    public open val atlasCellHeight: Int get() = height

    public val isLoaded: Boolean get() = atlas != null

    /**
     * Loads the atlas. Called once by the renderer; must be safe to call again.
     */
    public abstract suspend fun load()
}

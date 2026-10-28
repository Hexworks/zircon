package org.hexworks.zircon.compose.tileset

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.compose.FrameData
import org.hexworks.zircon.compose.shader.AtlasInfo
import kotlin.reflect.KClass

/**
 * A tileset for the Compose renderer. "Drawing" a tile means writing a cell record into the
 * [FrameData]; the pixels come from one or more [atlases], sprite sheets the shader
 * samples. Atlases are loaded asynchronously by [load]; until then a tileset writes
 * backgrounds only.
 */
public abstract class ComposeTileset(
    public val resource: TilesetResource,
) : Tileset<FrameData> {

    override val id: UUID = UUID.randomUUID()
    override val targetType: KClass<FrameData> = FrameData::class
    override val width: Int = resource.width
    override val height: Int = resource.height

    /**
     * Hands out atlas ids; set by the [TilesetRegistry] right after creation.
     */
    internal var allocateAtlasId: () -> Int = { error("Tileset is not registered") }

    private val mutableAtlases = mutableMapOf<Int, AtlasInfo>()

    /**
     * The sprite sheets this tileset samples, by atlas id.
     */
    public val atlases: Map<Int, AtlasInfo> get() = mutableAtlases

    public val isLoaded: Boolean get() = loaded

    protected var loaded: Boolean = false

    /**
     * Registers (or replaces) a sprite sheet and returns its atlas id.
     */
    protected fun putAtlas(info: AtlasInfo, atlasId: Int = allocateAtlasId()): Int {
        mutableAtlases[atlasId] = info
        return atlasId
    }

    /**
     * Loads the atlases. Called by the renderer; must be safe to call again.
     */
    public abstract suspend fun load()
}

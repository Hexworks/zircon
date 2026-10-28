package org.hexworks.zircon.api.tileset

import kotlinx.serialization.json.Json
import org.hexworks.zircon.api.resource.Resource
import org.hexworks.zircon.api.resource.loadBytes
import org.hexworks.zircon.internal.util.compression.ZipArchive

/**
 * The name of the metadata document inside a graphical tileset archive.
 */
public const val GRAPHICAL_TILESET_METADATA_FILE: String = "tileset.json"

/**
 * A graphical tileset archive opened for reading. Core only decodes the metadata and
 * hands out the still-encoded image bytes; the renderer decodes the images.
 */
public class GraphicalTilesetData internal constructor(
    public val metadata: GraphicalTilesetMetadata,
    private val archive: ZipArchive,
) {

    private val textures: Map<String, GraphicalTextureMetadata> = buildMap {
        val size = metadata.tileSize
        for (file in metadata.files) {
            file.tiles.forEachIndexed { index, tile ->
                put(
                    tile.name,
                    GraphicalTextureMetadata(
                        x = index % file.tilesPerRow,
                        y = index / file.tilesPerRow,
                        width = size,
                        height = size,
                        name = tile.name,
                        tags = tile.tags,
                        file = file.name,
                    )
                )
            }
        }
    }

    /** Names of every tile in the tileset. */
    public val tileNames: Set<String> get() = textures.keys

    /**
     * Where the tile called [name] sits in its sprite sheet, or `null` if there is no such tile.
     */
    public fun textureFor(name: String): GraphicalTextureMetadata? = textures[name]

    /**
     * The still-encoded bytes (usually PNG) of the sprite sheet called [fileName].
     */
    public fun imageBytes(fileName: String): ByteArray = archive.read(fileName)
}

/**
 * Parses a `tileset.json` document.
 */
public fun parseGraphicalTilesetMetadata(json: String): GraphicalTilesetMetadata =
    metadataJson.decodeFromString(GraphicalTilesetMetadata.serializer(), json)

/**
 * Opens a graphical tileset archive (a zip with `tileset.json` and its sprite sheets).
 */
public fun loadGraphicalTileset(archiveBytes: ByteArray): GraphicalTilesetData {
    val archive = ZipArchive(archiveBytes)
    require(archive.contains(GRAPHICAL_TILESET_METADATA_FILE)) {
        "Not a graphical tileset archive: no $GRAPHICAL_TILESET_METADATA_FILE in ${archive.names}"
    }
    val metadata = parseGraphicalTilesetMetadata(archive.read(GRAPHICAL_TILESET_METADATA_FILE).decodeToString())
    metadata.files.forEach { file ->
        require(archive.contains(file.name)) { "Sprite sheet '${file.name}' is missing from the archive" }
    }
    return GraphicalTilesetData(metadata, archive)
}

/**
 * Loads a graphical tileset archive through [org.hexworks.zircon.api.resource.Resources.loader].
 */
public suspend fun loadGraphicalTileset(resource: Resource): GraphicalTilesetData =
    loadGraphicalTileset(resource.loadBytes())

internal val metadataJson: Json = Json {
    ignoreUnknownKeys = true
    encodeDefaults = false
}

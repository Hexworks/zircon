package org.hexworks.zircon.api

import kotlinx.serialization.json.Json
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.AnimationMetadata
import org.hexworks.zircon.api.builder.animation.AnimationBuilder
import org.hexworks.zircon.api.resource.Resource
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.resource.loadBytes
import org.hexworks.zircon.internal.util.compression.ZipArchive

/**
 * The name of the metadata document inside a `.zap` animation archive.
 */
public const val ANIMATION_METADATA_FILE: String = "animation.json"

/**
 * Parses an `animation.json` document.
 */
public fun parseAnimationMetadata(json: String): AnimationMetadata =
    animationJson.decodeFromString(AnimationMetadata.serializer(), json)

/**
 * Loads a `.zap` animation archive (see [AnimationMetadata]) and returns an [AnimationBuilder]
 * holding its frames, so the caller can still set the position before building.
 */
public fun loadAnimation(archiveBytes: ByteArray, tileset: TilesetResource): AnimationBuilder {
    val archive = ZipArchive(archiveBytes)
    require(archive.contains(ANIMATION_METADATA_FILE)) {
        "Not an animation archive: no $ANIMATION_METADATA_FILE in ${archive.names}"
    }
    val metadata = parseAnimationMetadata(archive.read(ANIMATION_METADATA_FILE).decodeToString())
    val sequence = metadata.frames.ifEmpty {
        val pattern = Regex("^" + Regex.escape(metadata.baseName) + "(\\d+)\\." + Regex.escape(metadata.extension) + "$")
        archive.names.mapNotNull { pattern.find(it)?.groupValues?.get(1)?.toInt() }
            .sorted()
            .map { org.hexworks.zircon.api.animation.AnimationFrameRef(it) }
    }
    require(sequence.isNotEmpty()) { "The animation archive has no frames" }

    // Frame files are decoded once; the sequence may show the same file several times.
    val layersByFrame = mutableMapOf<Int, List<org.hexworks.zircon.api.graphics.Layer>>()
    fun layersOf(index: Int) = layersByFrame.getOrPut(index) {
        val fileName = "${metadata.baseName}$index.${metadata.extension}"
        require(archive.contains(fileName)) { "Frame file '$fileName' is missing from the archive" }
        loadREXFile(archive.read(fileName)).toLayerList(tileset)
    }

    return AnimationBuilder().apply {
        fps = metadata.frameRate.toLong()
        loopKind = if (metadata.loopCount <= 0) Animation.InfiniteLoop else Animation.FiniteLoop(metadata.loopCount)
        addFrames(
            sequence.map { ref ->
                val layers = layersOf(ref.frame)
                frame {
                    size = layers.maxOf { it.size }
                    repeatCount = ref.repeatCount
                    addLayers(layers)
                }
            }
        )
    }
}

/**
 * Loads a `.zap` animation archive through [org.hexworks.zircon.api.resource.Resources.loader].
 */
public suspend fun loadAnimation(resource: Resource, tileset: TilesetResource): AnimationBuilder =
    loadAnimation(resource.loadBytes(), tileset)

private val animationJson: Json = Json {
    ignoreUnknownKeys = true
    encodeDefaults = false
}

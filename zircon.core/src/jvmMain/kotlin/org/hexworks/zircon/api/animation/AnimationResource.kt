package org.hexworks.zircon.api.animation

import org.hexworks.zircon.api.builder.animation.AnimationBuilder
import org.hexworks.zircon.api.resource.REXPaintResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.animation.AnimationMetadata
import org.hexworks.zircon.internal.animation.DefaultAnimationFrame
import org.hexworks.zircon.internal.util.rex.unZipIt
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import java.io.InputStream

class AnimationResource {


    // TODO: this needs to be refactored to support multiplatform code at some point
    companion object {

        /**
         * Loads the data of an animation from the given animation file returns an
         * [AnimationBuilder] which contains the loaded data.
         */
        @JvmStatic
        fun loadAnimationFromStream(zipStream: InputStream, tileset: TilesetResource): AnimationBuilder {
            val files = unZipIt(zipStream, createTempDir())
            val tileInfoSource = files.first { it.name == "animation.yml" }.bufferedReader().use {
                it.readText()
            }
            val animDataFile = Yaml(Constructor(AnimationMetadata::class.java))
            val (_, animationData) = animDataFile.load(tileInfoSource) as AnimationMetadata
            val frameMap = mutableMapOf<Int, DefaultAnimationFrame>()
            val frames = animationData.frameMap.map { frame ->
                val fileName = "${animationData.baseName}${frame.frame}.xp"
                frameMap.computeIfAbsent(frame.frame) {
                    val frameImage = REXPaintResource.loadREXFile(files.first { it.name == fileName }.inputStream())
                    val size = frameImage.toLayerList(tileset).maxBy { it.size }!!.size
                    DefaultAnimationFrame(
                            size = size,
                            layers = frameImage.toLayerList(tileset),
                            repeatCount = frame.repeatCount)
                }
                // we need this trick to not load the file (and create a frame out of it) if it has already been loaded
                frameMap[frame.frame]!!.copy(repeatCount = frame.repeatCount)
            }
            return AnimationBuilder.newBuilder()
                    .withFps(animationData.frameRate)
                    .withLoopCount(animationData.loopCount)
                    .addFrames(frames)
        }
    }
}

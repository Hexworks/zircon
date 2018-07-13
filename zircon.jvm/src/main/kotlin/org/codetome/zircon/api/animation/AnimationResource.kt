package org.codetome.zircon.api.animation

import org.codetome.zircon.api.resource.REXPaintResource
import org.codetome.zircon.internal.animation.AnimationMetadata
import org.codetome.zircon.internal.animation.DefaultAnimationFrame
import org.codetome.zircon.internal.util.rex.unZipIt
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import java.io.InputStream

/**
 * Note that this class is in **BETA**!
 * It's API is subject to change!
 */
class AnimationResource {


    // TODO: this needs to be refactored to support multiplatform code
    companion object {

        /**
         * Loads the data of an animation from the given animation file returns an
         * [AnimationBuilder] which contains the loaded data.
         */
        @JvmStatic
        fun loadAnimationFromStream(zipStream: InputStream): AnimationBuilder {
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
                    val size = frameImage.toLayerList().maxBy { it.getBoundableSize() }!!.getBoundableSize()
                    DefaultAnimationFrame(
                            size = size,
                            layers = frameImage.toLayerList(),
                            repeatCount = frame.repeatCount)
                }
                // we need this trick to not load the file (and create a frame out of it) if it has already been loaded
                frameMap[frame.frame]!!.copy(repeatCount = frame.repeatCount)
            }
            return AnimationBuilder.newBuilder()
                    .fps(animationData.frameRate)
                    .loopCount(animationData.loopCount)
                    .addFrames(frames)
        }
    }
}

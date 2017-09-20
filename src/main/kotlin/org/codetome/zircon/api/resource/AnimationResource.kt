package org.codetome.zircon.api.resource

import org.codetome.zircon.api.builder.AnimationBuilder
import org.codetome.zircon.internal.graphics.AnimationMetadata
import org.codetome.zircon.internal.graphics.DefaultAnimationFrame
import org.codetome.zircon.internal.util.rex.unZipIt
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor

class AnimationResource {

    companion object {

        /**
         * Loads the data of an animation from the given animation file returns an
         * [AnimationBuilder] which contains the loaded data.
         */
        @JvmStatic
        fun loadAnimationFromFile(sourceZipPath: String): AnimationBuilder {
            val files = unZipIt(sourceZipPath, createTempDir())
            val tileInfoSource = files.filter { it.name == "animation.yml" }.first().bufferedReader().use {
                it.readText()
            }
            val animDataFile = Yaml(Constructor(AnimationMetadata::class.java))
            val (_, animationData) = animDataFile.load(tileInfoSource) as AnimationMetadata
            val frameMap = mutableMapOf<Int, DefaultAnimationFrame>()
            val frames = animationData.frameMap.map { frame ->
                val fileName = "${animationData.baseName}${frame.frame}.xp"
                frameMap.computeIfAbsent(frame.frame, {
                    val frameImage = REXPaintResource.loadREXFile(files.first { it.name == fileName }.inputStream())
                    val size = frameImage.toLayerList().maxBy { it.getBoundableSize() }!!.getBoundableSize()
                    DefaultAnimationFrame(
                            size = size,
                            layers = frameImage.toLayerList(),
                            repeatCount = frame.repeatCount)
                })
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

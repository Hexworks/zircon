package org.codetome.zircon.api.graphics

import org.codetome.zircon.internal.behavior.Identifiable
import java.util.*

interface Animation : Identifiable {

    fun getFrameCount(): Int

    fun getLoopCount(): Int

    fun getLength(): Int

    fun getTick(): Long

    fun getCurrentFrame() : AnimationFrame

    fun fetchNextFrame(): Optional<AnimationFrame>

    fun hasNextFrame(): Boolean

    fun getAllFrames(): List<AnimationFrame>
}
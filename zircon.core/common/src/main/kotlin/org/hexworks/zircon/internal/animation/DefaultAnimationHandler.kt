package org.hexworks.zircon.internal.animation

import org.hexworks.cobalt.Identifier
import org.hexworks.cobalt.factory.IdentifierFactory
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.AnimationInfo
import org.hexworks.zircon.api.animation.AnimationState.FINISHED
import org.hexworks.zircon.api.animation.AnimationState.INFINITE
import org.hexworks.zircon.api.animation.AnimationState.IN_PROGRESS
import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.platform.util.SystemUtils
import kotlin.jvm.Synchronized

internal class DefaultAnimationHandler : InternalAnimationHandler, Closeable {

    private val debug = RuntimeConfig.config.debugMode
    private val id = IdentifierFactory.randomIdentifier()

    private val results = mutableMapOf<Identifier, DefaultAnimationInfo>()
    private val animations = mutableMapOf<Identifier, Animation>()
    private val nextUpdatesForAnimations = mutableMapOf<Identifier, Long>()
    private var running = true

    @Synchronized
    override fun startAnimation(animation: Animation): AnimationInfo {
        if (debug) println("Adding animation to AnimationHandler ($id).")
        val result = DefaultAnimationInfo(
                state = if (animation.isLoopedIndefinitely) INFINITE else IN_PROGRESS,
                animation = animation,
                animationHandler = this)
        results[animation.id] = result
        animations[animation.id] = animation
        nextUpdatesForAnimations[animation.id] = SystemUtils.getCurrentTimeMs()
        return result
    }

    @Synchronized
    override fun stopAnimation(animation: Animation) {
        results[animation.id]?.setState(FINISHED)
        results.remove(animation.id)
        animations.remove(animation.id)
        nextUpdatesForAnimations.remove(animation.id)
        animation.removeCurrentFrame()
    }

    @Synchronized
    override fun updateAnimations(currentTimeMs: Long, layerable: Layerable) {
        if (running) {
            animations.forEach { (key, animation) ->
                val updateTime = nextUpdatesForAnimations.getValue(key)
                if (updateTime <= currentTimeMs) {
                    if (animation.displayNextFrame(layerable)) {
                        nextUpdatesForAnimations[key] = currentTimeMs + animation.tick
                    } else {
                        stopAnimation(animation)
                    }
                }
            }
        }
    }

    @Synchronized
    override fun close() {
        running = false
        animations.forEach { (_, animation) ->
            stopAnimation(animation)
        }
        animations.clear()
    }
}

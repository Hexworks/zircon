package org.hexworks.zircon.internal.animation

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.core.platform.factory.UUIDFactory
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.AnimationHandle
import org.hexworks.zircon.api.animation.AnimationState.*
import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.internal.animation.impl.DefaultAnimationHandle
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.platform.util.SystemUtils
import kotlin.jvm.Synchronized

internal class DefaultAnimationRunner : InternalAnimationRunner, Closeable {

    private val id = UUIDFactory.randomUUID()
    private val debug = RuntimeConfig.config.debugMode
    private val logger = LoggerFactory.getLogger(this::class)

    private val results = mutableMapOf<UUID, DefaultAnimationHandle>()
    private val animations = mutableMapOf<UUID, InternalAnimation>()
    private val nextUpdatesForAnimations = mutableMapOf<UUID, Long>()
    private var running = true

    @Synchronized
    override fun start(animation: Animation): AnimationHandle {
        animation as? InternalAnimation
                ?: error("The supplied animation does not implement required interface: InternalAnimation.")
        if (debug) logger.debug("Adding animation to AnimationHandler ($id).")
        val result = DefaultAnimationHandle(
                state = if (animation.isLoopedIndefinitely) INFINITE else IN_PROGRESS,
                animation = animation,
                animationRunner = this)
        results[animation.id] = result
        animations[animation.id] = animation
        nextUpdatesForAnimations[animation.id] = SystemUtils.getCurrentTimeMs()
        return result
    }

    @Synchronized
    override fun stop(animation: InternalAnimation) {
        results[animation.id]?.state = FINISHED
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
                        stop(animation)
                    }
                }
            }
        }
    }

    @Synchronized
    override fun close() {
        running = false
        animations.forEach { (_, animation) ->
            stop(animation)
        }
        animations.clear()
    }
}

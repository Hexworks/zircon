package org.hexworks.zircon.internal.animation

import org.hexworks.cobalt.Identifier
import org.hexworks.cobalt.factory.IdentifierFactory
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.AnimationInfo
import org.hexworks.zircon.api.animation.AnimationState.FINISHED
import org.hexworks.zircon.api.animation.AnimationState.INFINITE
import org.hexworks.zircon.api.animation.AnimationState.IN_PROGRESS
import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.platform.extension.getOrDefault
import org.hexworks.zircon.platform.factory.PersistentMapFactory

internal class DefaultAnimationHandler : InternalAnimationHandler, Closeable {

    private val nextUpdatesForAnimations = HashMap<Identifier, Long>()
    private val debug = RuntimeConfig.config.debugMode
    private val id = IdentifierFactory.randomIdentifier()

    private var animations = PersistentMapFactory.create<Identifier, Animation>()
    private var results = PersistentMapFactory.create<Identifier, DefaultAnimationInfo>()
    private var running = true

    override fun startAnimation(animation: Animation): AnimationInfo {
        if (debug) println("Adding animation to AnimationHandler ($id).")
        val result = DefaultAnimationInfo(
                state = if (animation.isLoopedIndefinitely()) INFINITE else IN_PROGRESS,
                animation = animation,
                animationHandler = this)
        results = results.put(animation.id, result)
        animations = animations.put(animation.id, animation)
        return result
    }

    override fun stopAnimation(animation: Animation) {
        animations.remove(animation.id).let {
            results[animation.id]?.setState(FINISHED)
        }
        animation.clearCurrentFrame()
    }

    override fun updateAnimations(currentTimeMs: Long, tileGrid: TileGrid) {
        if (running) {
            val currentAnimationKeys = animations.keys
            currentAnimationKeys.forEach { key ->
                val animation = animations[key]!!
                val updateTime = nextUpdatesForAnimations.getOrDefault(key, currentTimeMs)
                if (updateTime <= currentTimeMs) {
                    val currentFrame = animation.fetchCurrentFrame()
                    currentFrame.layers.forEach { tileGrid.removeLayer(it) }
                    animation.fetchNextFrame().map { frame ->
                        frame.layers.forEach { layer ->
                            layer.moveTo(frame.position)
                            tileGrid.addLayer(layer)
                        }
                    }
                    if (animation.hasNextFrame()) {
                        nextUpdatesForAnimations[key] = currentTimeMs + animation.tick
                    } else {
                        animations.remove(key).let {
                            results[key]?.setState(FINISHED)
                        }
                        animation.fetchCurrentFrame().layers.forEach {
                            tileGrid.removeLayer(it)
                        }
                    }
                }
            }
        }
    }

    override fun close() {
        running = false
        animations.clear()
    }
}

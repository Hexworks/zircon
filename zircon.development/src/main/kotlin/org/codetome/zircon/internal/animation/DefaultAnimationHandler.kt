package org.codetome.zircon.internal.animation

import org.codetome.zircon.api.animation.Animation
import org.codetome.zircon.api.animation.AnimationResult
import org.codetome.zircon.api.animation.AnimationState.*
import org.codetome.zircon.api.behavior.Closeable
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.api.util.Identifier
import org.codetome.zircon.internal.config.RuntimeConfig
import org.codetome.zircon.platform.factory.ThreadSafeMapFactory
import java.util.*
import java.util.function.Consumer

class DefaultAnimationHandler : InternalAnimationHandler, Closeable {

    private val animations = ThreadSafeMapFactory.create<Identifier, Animation>()
    private val results = ThreadSafeMapFactory.create<Identifier, DefaultAnimationResult>()
    private val nextUpdatesForAnimations = HashMap<Identifier, Long>()
    private val debug = RuntimeConfig.config.debugMode
    private var running = true
    private val id = Identifier.randomIdentifier()

    override fun addAnimation(animation: Animation): AnimationResult {
        if (debug) println("Adding animation to AnimationHandler ($id).")
        val result = DefaultAnimationResult(
                if (animation.isLoopedIndefinitely()) INFINITE else IN_PROGRESS)
        results[animation.id] = result
        animations[animation.id] = animation
        return result
    }

    override fun updateAnimations(currentTimeMs: Long, tileGrid: TileGrid) {
        if (running) {
            val currentAnimationKeys = animations.keys
            currentAnimationKeys.forEach { key ->
                val animation = animations[key]!!
                val updateTime = nextUpdatesForAnimations.getOrDefault(key, currentTimeMs)
                if (updateTime <= currentTimeMs) {
                    val currentFrame = animation.getCurrentFrame()
                    currentFrame.getLayers().forEach(Consumer<Layer> { tileGrid.removeLayer(it) })
                    animation.fetchNextFrame().map { frame ->
                        frame.getLayers().forEach { layer ->
                            layer.moveTo(frame.getPosition())
                            tileGrid.pushLayer(layer)
                        }
                    }
                    if (animation.hasNextFrame()) {
                        nextUpdatesForAnimations[key] = currentTimeMs + animation.getTick()
                    } else {
                        animations.remove(key)?.let {
                            results[key]?.setState(FINISHED)
                        }
                        animation.getCurrentFrame().getLayers().forEach {
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

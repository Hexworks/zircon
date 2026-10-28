package org.hexworks.zircon.api.animation

/**
 * The [AnimationRunner] can be used to [start] running [Animation]s.
 */
public interface AnimationRunner {

    /**
     * Adds the given [Animation] to this [AnimationRunner] and starts it.
     *
     * @return an [AnimationHandle] which can be used to
     * interact with the running [Animation].
     */
    public fun start(animation: Animation): AnimationHandle
}

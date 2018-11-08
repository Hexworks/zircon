package org.hexworks.zircon.api

import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.builder.animation.AnimationBuilder

object Animations {

    /**
     * Creates a new [AnimationBuilder] to build [Animation]s.
     */
    @JvmStatic
    fun newBuilder() = AnimationBuilder.newBuilder()
}

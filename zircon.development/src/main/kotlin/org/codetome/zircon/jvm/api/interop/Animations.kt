package org.codetome.zircon.jvm.api.interop

import org.codetome.zircon.api.animation.Animation
import org.codetome.zircon.api.builder.animation.AnimationBuilder

object Animations {

    /**
     * Creates a new [AnimationBuilder] to build [Animation]s.
     */
    @JvmStatic
    fun newBuilder() = AnimationBuilder.newBuilder()
}

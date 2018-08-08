package org.codetome.zircon.internal.animation

internal data class AnimationMetadata(var type: String,
                                      var animationData: AnimationData) {
    constructor() : this(
            type = "",
            animationData = AnimationData())


    data class AnimationData(var frameCount: Int,
                             var frameRate: Int,
                             var length: Int,
                             var loopCount: Int,
                             var baseName: String,
                             var extension: String,
                             var frameMap: List<Frame>) {
        constructor() : this(
                frameCount = -1,
                frameRate = -1,
                length = -1,
                loopCount = -1,
                baseName = "",
                extension = "xp",
                frameMap = listOf())
    }

    data class Frame(var frame: Int,
                     var repeatCount: Int = 1) {
        constructor() : this(
                frame = -1,
                repeatCount = 1)
    }
}

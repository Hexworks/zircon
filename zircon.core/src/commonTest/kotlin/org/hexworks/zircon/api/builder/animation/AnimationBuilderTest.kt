package org.hexworks.zircon.api.builder.animation

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.data.Position
import kotlin.test.Test

class AnimationBuilderTest {

    @Test
    fun when_an_animation_is_built_then_its_contents_are_ok() {
        animation {
            fps = 60
            position = Position.offset1x1()
            loopCount = 0
            frame {
                withSize {
                    width = 20
                    height = 20
                }
                repeatCount = 1
                layer {
                    tileset = CP437TilesetResources.bisasam20x20()
                }
            }
        }
    }
}
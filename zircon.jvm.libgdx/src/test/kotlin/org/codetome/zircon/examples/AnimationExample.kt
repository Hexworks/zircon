package org.codetome.zircon.examples

import org.codetome.zircon.api.LibgdxTerminalBuilder
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.animation.AnimationHandler
import org.codetome.zircon.api.animation.AnimationResource
import org.codetome.zircon.api.builder.ScreenBuilder
import org.codetome.zircon.api.component.builder.LabelBuilder
import org.codetome.zircon.api.component.builder.PanelBuilder
import org.codetome.zircon.api.resource.CP437TilesetResource

object AnimationExample {

    private val FONT = CP437TilesetResource.WANDERLUST_16X16
    private val TERMINAL_SIZE = Size.of(50, 30)
    private val LEFT_POS = Position.of(8, 5)
    private val RIGHT_POS = Position.of(29, 5)

    @JvmStatic
    fun main(args: Array<String>) {
        val terminal = LibgdxTerminalBuilder.newBuilder()
                .font(FONT.toFont())
                .initialTerminalSize(TERMINAL_SIZE)
                .build()
        val screen = ScreenBuilder.createScreenFor(terminal)
        screen.setCursorVisibility(false)

        val panel = PanelBuilder.newBuilder()
                .wrapWithBox()
                .title("Animation example")
                .size(TERMINAL_SIZE)
                .build()

        panel.addComponent(LabelBuilder.newBuilder()
                .text("Looped:")
                .position(LEFT_POS.withRelativeY(-3).withRelativeX(-1))
                .build())
        panel.addComponent(LabelBuilder.newBuilder()
                .text("Non-looped:")
                .position(RIGHT_POS.withRelativeY(-3).withRelativeX(-1))
                .build())
        screen.addComponent(panel)

        screen.display()


        val first = AnimationResource.loadAnimationFromStream(AnimationExample::class.java.getResourceAsStream("/animations/skull.zap"))
        val second = first.createCopy()
        first.loopCount(0)
        second.loopCount(1)
        for (i in 0 until first.getLength()) {
            first.addPosition(LEFT_POS)
            second.addPosition(RIGHT_POS)
        }
        val leftAnim = first.build()
        val rightAnim = second.build()

        val animationHandler = AnimationHandler(screen)
        animationHandler.addAnimation(leftAnim)
        animationHandler.addAnimation(rightAnim)

    }
}

package org.codetome.zircon.beta

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.beta.animation.Animation
import org.codetome.zircon.api.beta.animation.AnimationHandler
import org.codetome.zircon.api.beta.animation.AnimationResource
import org.codetome.zircon.api.builder.TerminalBuilder
import org.codetome.zircon.api.component.builder.ColorThemeBuilder
import org.codetome.zircon.api.component.builder.PanelBuilder
import org.codetome.zircon.api.component.builder.TextBoxBuilder
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.resource.ColorThemeResource
import org.codetome.zircon.internal.graphics.BoxType

object Playground {

    @JvmStatic
    fun main(args: Array<String>) {
        val text = """
Look no further, Zircon is a right tool
for the job.

Zircon is a Text GUI library which targets
multiple platforms and designed
specifically for game developers.

It is usable out of the box for all JVM
languages including Java, Kotlin and Scala.
Things Zircon knows:

- Animations
- A component system with built-in
  components for games
- Layering
- Mouse and keyboard input handling
- Shape and box drawing
- Fonts and tilesets
- REXPaint file loading
- Color themes, and more...
                """
        val skullAnim: Animation = AnimationResource
                .loadAnimationFromFile("src/test/resources/animations/skull.zap")
                .loopCount(0)
                .setPositionForAll(Position.OFFSET_1x1)
                .build()


        val screen = TerminalBuilder.newBuilder()
                .font(CP437TilesetResource.ROGUE_YUN_16X16.toFont())
                .initialTerminalSize(Size.of(60, skullAnim.getCurrentFrame().getSize().rows + 2))
                .buildScreen()

        val skullPanel = PanelBuilder.newBuilder()
                .size(skullAnim.getCurrentFrame().getSize() + Size.of(2, 2))
                .wrapWithBox()
                .boxType(BoxType.DOUBLE)
                .build()
        val contentPanel = PanelBuilder.newBuilder()
                .boxType(BoxType.DOUBLE)
                .size(screen.getBoundableSize()
                        .withRelativeColumns(-skullPanel.getBoundableSize().columns))
                .position(skullPanel.getBoundableSize().toRightPosition())
                .title("Do you plan to make a roguelike?")
                .wrapWithBox()
                .build()

        val disabledTextBox = TextBoxBuilder.newBuilder()
                .text(text)
                .size(contentPanel.getEffectiveSize())
                .build()

        screen.addComponent(skullPanel)
        screen.addComponent(contentPanel)
        contentPanel.addComponent(disabledTextBox)

        val theme = ColorThemeResource.SOLARIZED_DARK_CYAN.getTheme()
        skullPanel.applyColorTheme(ColorThemeBuilder.newBuilder()
                .accentColor(theme.getAccentColor())
                .brightForegroundColor(theme.getBrightForegroundColor())
                .darkForegroundColor(theme.getDarkForegroundColor())
                .build())
        contentPanel.applyColorTheme(theme)

        disabledTextBox.disable()

        val animationHandler = AnimationHandler(screen)
        animationHandler.addAnimation(skullAnim)
        screen.display()
    }
}
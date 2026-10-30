package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.radioButton
import org.hexworks.zircon.api.builder.component.radioButtonGroup
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.extensions.relativeToRightOf
import org.hexworks.zircon.api.data.extensions.withRelativeX
import org.hexworks.zircon.api.data.extensions.withRelativeY
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

/**
 * A game's main menu and options view, swapped in and out of the content area. The old
 * version used two screens; a nested app shows one screen, so the views are panels.
 */
public val gameMockupExample: Example = Example("Game mockup") { context ->
    val size = context.contentSize
    val root = panel {
        preferredSize = size
        componentRenderer = NoOpComponentRenderer()
    }
    var shown: AttachedComponent? = null
    fun show(view: Component) {
        shown?.detach()
        shown = root.addComponent(view)
    }

    // ==========
    // MAIN MENU
    // ==========
    val menuView = panel { preferredSize = size; componentRenderer = NoOpComponentRenderer() }
    val menuPosition = Position((size.width - MENU_WIDTH) / 2, (size.height - MENU_HEIGHT) / 2)
    menuView.addComponent(label {
        +"M A I N   M E N U"
        position = menuPosition.withRelativeY(-3).withRelativeX(4)
    })
    val menuPanel = panel {
        decorations { +box(BoxType.LEFT_RIGHT_DOUBLE) }
        position = menuPosition
        preferredSize = Size(MENU_WIDTH, MENU_HEIGHT)
    }
    val newGameButton = button { +"N E W   G A M E"; position = Position(3, 1) }
    val optionsButton = button { +"O P T I O N S"; position = Position(4, 3) }
    val quitButton = button { +"Q U I T"; position = Position(7, 5) }
    menuPanel.addComponent(newGameButton)
    menuPanel.addComponent(optionsButton)
    menuPanel.addComponent(quitButton)
    menuView.addComponent(menuPanel)

    // ==========
    // OPTIONS
    // ==========
    val optionsView = panel { preferredSize = size; componentRenderer = NoOpComponentRenderer() }
    val backButton = button {
        +"${Symbols.ARROW_LEFT} B A C K"
        position = Position(SPACING, size.height - SPACING * 2)
    }
    optionsView.addComponent(backButton)
    optionsView.addComponent(button {
        +"A P P L Y"
        position = Position(SPACING, 0).relativeToRightOf(backButton)
    })
    val difficultyPanel = panel {
        preferredSize = Size((size.width - SPACING) / 3, 9)
        position = Position(SPACING, SPACING)
        decorations { +box(BoxType.LEFT_RIGHT_DOUBLE, "D I F F I C U L T Y") }
    }
    val difficultyBox = vBox { preferredSize = difficultyPanel.size - Size(2, 2) }
    val difficultyGroup = radioButtonGroup { }
    listOf("TINGLE", "ANXIETY", "HORROR").forEach { diff ->
        val btn = radioButton { +diff; key = diff }
        difficultyBox.addComponent(btn)
        difficultyGroup.addComponent(btn)
    }
    difficultyPanel.addComponent(difficultyBox)
    optionsView.addComponent(difficultyPanel)

    // INTERACTIONS: quitting an embedded example only returns to the menu.
    quitButton.onActivated { show(menuView) }
    optionsButton.onActivated { show(optionsView) }
    backButton.onActivated { show(menuView) }

    show(menuView)
    ExampleView(root)
}

private const val MENU_WIDTH = 25
private const val MENU_HEIGHT = 10
private const val SPACING = 2

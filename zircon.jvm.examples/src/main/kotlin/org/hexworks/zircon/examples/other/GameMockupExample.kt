package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.CP437TilesetResources.rexPaint20x20
import org.hexworks.zircon.api.ColorThemes.arc
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components.button
import org.hexworks.zircon.api.Components.label
import org.hexworks.zircon.api.Components.panel
import org.hexworks.zircon.api.Components.radioButton
import org.hexworks.zircon.api.Components.radioButtonGroup
import org.hexworks.zircon.api.Components.vbox
import org.hexworks.zircon.api.SwingApplications.startTileGrid
import org.hexworks.zircon.api.application.AppConfig.Companion.newBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.screen.Screen.Companion.create
import org.hexworks.zircon.api.uievent.ComponentEvent
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.UIEventResponse.Companion.processed
import java.awt.Toolkit
import java.util.*
import java.util.function.Consumer

object GameMockupExample {

    private val MAIN_MENU_LABEL = "M A I N   M E N U"
    private val NEW_GAME_BUTTON_LABEL = "N E W   G A M E"
    private val OPTIONS_BUTTON_LABEL = "O P T I O N S"
    private val QUIT_BUTTON_LABEL = "Q U I T"
    private val DIFFICULTY_LABEL = "D I F F I C U L T Y"
    private val BACK_LABEL = Symbols.ARROW_LEFT.toString() + " B A C K"
    private val APPLY_LABEL = "A P P L Y"

    private val DIFFICULTIES = arrayOf("TINGLE", "ANXIETY", "HORROR")

    private val TILESET = rexPaint20x20()
    private val MAIN_MENU_PANEL_WIDTH = 25
    private val MAIN_MENU_PANEL_HEIGHT = 10
    private val PANEL_SPACING = 2
    private val THEME = arc()

    @JvmStatic
    fun main(args: Array<String>) {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        val columns = screenSize.getWidth() / TILESET.width
        val rows = screenSize.getHeight() / TILESET.height
        val gridSize = Size.create(columns.toInt(), rows.toInt())
        val tileGrid = startTileGrid(
            newBuilder()
                .withDefaultTileset(TILESET)
                .withSize(gridSize)
                .withDebugMode(true)
                .withFullScreen(true)
                .build()
        )

        // ==========
        // MAIN MENU
        // ==========
        val mainMenuScreen = create(tileGrid)
        val menuPosition = Position.create(
            (gridSize.width - MAIN_MENU_PANEL_WIDTH) / 2,
            (gridSize.height - MAIN_MENU_PANEL_HEIGHT) / 2
        )
        val mainMenuLabel = label()
            .withText(MAIN_MENU_LABEL)
            .withPosition(menuPosition.withRelativeY(-3).withRelativeX(4))
            .build()
        mainMenuScreen.addComponent(mainMenuLabel)
        val menuPanel = panel()
            .withDecorations(box(BoxType.LEFT_RIGHT_DOUBLE))
            .withPosition(menuPosition)
            .withPreferredSize(Size.create(MAIN_MENU_PANEL_WIDTH, MAIN_MENU_PANEL_HEIGHT))
            .build()
        val newGameButton = button()
            .withText(NEW_GAME_BUTTON_LABEL)
            .withPosition(Position.create(3, 1))
            .build()
        menuPanel.addComponent(newGameButton)
        val optionsButton = button()
            .withText(OPTIONS_BUTTON_LABEL)
            .withPosition(Position.create(4, 3))
            .build()
        menuPanel.addComponent(optionsButton)
        val quitButton = button()
            .withText(QUIT_BUTTON_LABEL)
            .withPosition(Position.create(7, 5))
            .build()
        menuPanel.addComponent(quitButton)
        mainMenuScreen.addComponent(menuPanel)
        mainMenuScreen.theme = THEME

        // ==========
        // OPTIONS
        // ==========
        val optionsScreen = create(tileGrid)
        val backButton = button()
            .withText(BACK_LABEL)
            .withPosition(
                Position.create(
                    PANEL_SPACING,
                    gridSize.height - PANEL_SPACING * 2
                )
            )
            .build()
        optionsScreen.addComponent(backButton)
        val applyButton = button()
            .withText(APPLY_LABEL)
            .withPosition(Position.create(PANEL_SPACING, 0).relativeToRightOf(backButton))
            .build()
        optionsScreen.addComponent(applyButton)
        val difficultyPanel = panel()
            .withPreferredSize(Size.create((gridSize.width - PANEL_SPACING) / 3, 9))
            .withPosition(Position.create(PANEL_SPACING, PANEL_SPACING))
            .withDecorations(box(BoxType.LEFT_RIGHT_DOUBLE, DIFFICULTY_LABEL))
            .build()
        val difficultyBox = vbox()
            .withPreferredSize(difficultyPanel.size.minus(Size.create(2, 2)))
            .build()
        val difficultyGroup = radioButtonGroup().build()
        Arrays.asList(*DIFFICULTIES).forEach(Consumer { diff: String? ->
            val btn = radioButton()
                .withText(diff!!)
                .withKey(diff)
                .build()
            difficultyBox.addComponent(btn)
            difficultyGroup.addComponent(btn)
        })
        difficultyPanel.addComponent(difficultyBox)
        optionsScreen.addComponent(difficultyPanel)
        optionsScreen.theme = THEME

        // INTERACTIONS
        quitButton.handleComponentEvents(ComponentEventType.ACTIVATED) { event: ComponentEvent? ->
            System.exit(0)
            processed()
        }
        optionsButton.handleComponentEvents(ComponentEventType.ACTIVATED) { event: ComponentEvent? ->
            optionsScreen.display()
            processed()
        }
        backButton.handleComponentEvents(ComponentEventType.ACTIVATED) { event: ComponentEvent? ->
            mainMenuScreen.display()
            processed()
        }

        // START IT UP
        mainMenuScreen.display()
    }
}
package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.AppConfigs;
import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.ColorThemes;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.LibgdxApplications;
import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.Screens;
import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.UIEventResponses;
import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.component.ColorTheme;
import org.hexworks.zircon.api.component.Label;
import org.hexworks.zircon.api.component.Panel;
import org.hexworks.zircon.api.component.RadioButtonGroup;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.graphics.Symbols;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.screen.Screen;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Arrays;

import static org.hexworks.zircon.api.uievent.ComponentEventType.ACTIVATED;

public class GameMockupExample {

    private static final String MAIN_MENU_LABEL = "M A I N   M E N U";
    private static final String NEW_GAME_BUTTON_LABEL = "N E W   G A M E";
    private static final String OPTIONS_BUTTON_LABEL = "O P T I O N S";
    private static final String QUIT_BUTTON_LABEL = "Q U I T";
    private static final String DIFFICULTY_LABEL = "D I F F I C U L T Y";
    private static final String BACK_LABEL = Symbols.ARROW_LEFT + " B A C K";
    private static final String APPLY_LABEL = "A P P L Y";

    private static final String[] DIFFICULTIES = new String[]{"TINGLE", "ANXIETY", "HORROR"};

    private static final TilesetResource TILESET = CP437TilesetResources.rexPaint20x20();
    private static final int MAIN_MENU_PANEL_WIDTH = 25;
    private static final int MAIN_MENU_PANEL_HEIGHT = 10;
    private static final int PANEL_SPACING = 2;
    private static final ColorTheme THEME = ColorThemes.arc();

    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double columns = screenSize.getWidth() / TILESET.getWidth();
        double rows = screenSize.getHeight() / TILESET.getHeight();
        Size terminalSize = Sizes.create((int) columns, (int) rows);

        final TileGrid tileGrid = LibgdxApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(TILESET)
                .withSize(terminalSize)
                .withDebugMode(true)
                .fullScreen()
                .build());

        // ==========
        // MAIN MENU
        // ==========

        Screen mainMenuScreen = Screens.createScreenFor(tileGrid);
        Position menuPosition = Positions.create(
                (terminalSize.getWidth() - MAIN_MENU_PANEL_WIDTH) / 2,
                (terminalSize.getHeight() - MAIN_MENU_PANEL_HEIGHT) / 2);
        Label mainMenuLabel = Components.label()
                .withText(MAIN_MENU_LABEL)
                .withPosition(menuPosition.withRelativeY(-3).withRelativeX(4))
                .build();
        mainMenuScreen.addComponent(mainMenuLabel);

        Panel menuPanel = Components.panel()
                .withBoxType(BoxType.LEFT_RIGHT_DOUBLE)
                .wrapWithBox(true)
                .withPosition(menuPosition)
                .withSize(Sizes.create(MAIN_MENU_PANEL_WIDTH, MAIN_MENU_PANEL_HEIGHT))
                .build();

        Button newGameButton = Components.button()
                .withText(NEW_GAME_BUTTON_LABEL)
                .withPosition(Positions.create(3, 1))
                .build();
        menuPanel.addComponent(newGameButton);

        Button optionsButton = Components.button()
                .withText(OPTIONS_BUTTON_LABEL)
                .withPosition(Positions.create(4, 3))
                .build();
        menuPanel.addComponent(optionsButton);

        Button quitButton = Components.button()
                .withText(QUIT_BUTTON_LABEL)
                .withPosition(Positions.create(7, 5))
                .build();
        menuPanel.addComponent(quitButton);

        mainMenuScreen.addComponent(menuPanel);
        mainMenuScreen.applyColorTheme(THEME);

        // ==========
        // OPTIONS
        // ==========

        Screen optionsScreen = Screens.createScreenFor(tileGrid);

        Button backButton = Components.button()
                .withText(BACK_LABEL)
                .withPosition(Positions.create(
                        PANEL_SPACING,
                        terminalSize.getHeight() - (PANEL_SPACING * 2)))
                .build();
        optionsScreen.addComponent(backButton);

        Button applyButton = Components.button()
                .withText(APPLY_LABEL)
                // TODO: FIX CAST
                .withPosition((Positions.create(PANEL_SPACING, 0)).relativeToRightOf(backButton))
                .build();
        optionsScreen.addComponent(applyButton);

        Panel difficultyPanel = Components.panel()
                .withSize(Sizes.create((terminalSize.getWidth() - PANEL_SPACING) / 3, 9))
                .withPosition(Positions.create(PANEL_SPACING, PANEL_SPACING))
                .wrapWithBox(true)
                .withBoxType(BoxType.LEFT_RIGHT_DOUBLE)
                .withTitle(DIFFICULTY_LABEL)
                .build();

        RadioButtonGroup difficultyRadio = Components.radioButtonGroup()
                .withSize(difficultyPanel.getSize().minus(Sizes.create(2, 2)))
                .build();

        Arrays.asList(DIFFICULTIES).forEach((diff) -> {
            difficultyRadio.addOption(diff, diff);
        });

        difficultyPanel.addComponent(difficultyRadio);
        optionsScreen.addComponent(difficultyPanel);


        optionsScreen.applyColorTheme(THEME);

        // INTERACTIONS

        quitButton.handleComponentEvents(ACTIVATED, (event) -> {
            System.exit(0);
            return UIEventResponses.processed();
        });

        optionsButton.handleComponentEvents(ACTIVATED, (event) -> {
            optionsScreen.display();
            return UIEventResponses.processed();
        });

        backButton.handleComponentEvents(ACTIVATED, (event) -> {
            mainMenuScreen.display();
            return UIEventResponses.processed();
        });

        // START IT UP
        mainMenuScreen.display();

    }
}

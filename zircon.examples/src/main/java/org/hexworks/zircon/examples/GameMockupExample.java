package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.application.Application;
import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.component.*;
import org.hexworks.zircon.api.component.Label;
import org.hexworks.zircon.api.component.Panel;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.BoxType;
import org.hexworks.zircon.api.graphics.Symbols;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.CP437TilesetResource;
import org.hexworks.zircon.api.resource.ColorThemeResource;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.screen.Screen;

import java.awt.*;
import java.util.Arrays;

public class GameMockupExample {

    private static final String MAIN_MENU_LABEL = "M A I N   M E N U";
    private static final String NEW_GAME_BUTTON_LABEL = "N E W   G A M E";
    private static final String OPTIONS_BUTTON_LABEL = "O P T I O N S";
    private static final String QUIT_BUTTON_LABEL = "Q U I T";
    private static final String DIFFICULTY_LABEL = "D I F F I C U L T Y";
    private static final String BACK_LABEL = Symbols.ARROW_LEFT + " B A C K";
    private static final String APPLY_LABEL = "A P P L Y";

    private static final String[] DIFFICULTIES = new String[]{"TINGLE", "ANXIETY", "HORROR"};

    private static final TilesetResource TILESET = CP437TilesetResource.REX_PAINT_20X20;
    private static final int MAIN_MENU_PANEL_WIDTH = 25;
    private static final int MAIN_MENU_PANEL_HEIGHT = 10;
    private static final int PANEL_SPACING = 2;
    private static final ColorTheme THEME = ColorThemeResource.SOLARIZED_DARK_YELLOW.getTheme();

    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double columns = screenSize.getWidth() / TILESET.getWidth();
        double rows = screenSize.getHeight() / TILESET.getHeight();
        Size terminalSize = Sizes.create((int) columns, (int) rows);

        Application app = SwingApplications.startApplication(AppConfigs.newBuilder()
                .defaultTileset(TILESET)
                .defaultSize(terminalSize)
                .debugMode(true)
                .build());

        final TileGrid tileGrid = app.getTileGrid();

        app.start();

        // ==========
        // MAIN MENU
        // ==========

        Screen mainMenuScreen = Screens.createScreenFor(tileGrid);
        Position menuPosition = Positions.create(
                (terminalSize.getXLength() - MAIN_MENU_PANEL_WIDTH) / 2,
                (terminalSize.getYLength() - MAIN_MENU_PANEL_HEIGHT) / 2);
        Label mainMenuLabel = Components.newLabelBuilder()
                .text(MAIN_MENU_LABEL)
                .position(menuPosition.withRelativeY(-3).withRelativeX(4))
                .build();
        mainMenuScreen.addComponent(mainMenuLabel);

        Panel menuPanel = Components.newPanelBuilder()
                .boxType(BoxType.LEFT_RIGHT_DOUBLE)
                .wrapWithBox()
                .position(menuPosition)
                .size(Sizes.create(MAIN_MENU_PANEL_WIDTH, MAIN_MENU_PANEL_HEIGHT))
                .build();

        Button newGameButton = Components.newButtonBuilder()
                .text(NEW_GAME_BUTTON_LABEL)
                .position(Positions.create(3, 1))
                .build();
        menuPanel.addComponent(newGameButton);

        Button optionsButton = Components.newButtonBuilder()
                .text(OPTIONS_BUTTON_LABEL)
                .position(Positions.create(4, 3))
                .build();
        menuPanel.addComponent(optionsButton);

        Button quitButton = Components.newButtonBuilder()
                .text(QUIT_BUTTON_LABEL)
                .position(Positions.create(7, 5))
                .build();
        menuPanel.addComponent(quitButton);

        mainMenuScreen.addComponent(menuPanel);
        mainMenuScreen.applyColorTheme(THEME);

        // ==========
        // OPTIONS
        // ==========

        Screen optionsScreen = Screens.createScreenFor(tileGrid);

        Button backButton = Components.newButtonBuilder()
                .text(BACK_LABEL)
                .position(Positions.create(
                        PANEL_SPACING,
                        terminalSize.getYLength() - (PANEL_SPACING * 2)))
                .build();
        optionsScreen.addComponent(backButton);

        Button applyButton = Components.newButtonBuilder()
                .text(APPLY_LABEL)
                // TODO: FIX CAST
                .position((Positions.create(PANEL_SPACING, 0)).relativeToRightOf(backButton))
                .build();
        optionsScreen.addComponent(applyButton);

        Panel difficultyPanel = Components.newPanelBuilder()
                .size(Sizes.create((terminalSize.getXLength() - PANEL_SPACING) / 3, 9))
                .position(Positions.create(PANEL_SPACING, PANEL_SPACING))
                .wrapWithBox()
                .boxType(BoxType.LEFT_RIGHT_DOUBLE)
                .title(DIFFICULTY_LABEL)
                .build();

        RadioButtonGroup difficultyRadio = Components.newRadioButtonGroupBuilder()
                .position(Positions.create(1, 1))
                .size(difficultyPanel.size().minus(Sizes.create(2, 2)))
                .build();

        Arrays.asList(DIFFICULTIES).forEach((diff) -> {
            difficultyRadio.addOption(diff, diff);
        });

        difficultyPanel.addComponent(difficultyRadio);
        optionsScreen.addComponent(difficultyPanel);


        optionsScreen.applyColorTheme(THEME);

        // INTERACTIONS

        quitButton.onMouseReleased((mouseAction -> {
            System.exit(0);
        }));

        optionsButton.onMouseReleased((mouseAction -> optionsScreen.display()));

        backButton.onMouseReleased((mouseAction -> mainMenuScreen.display()));

        // START IT UP
        mainMenuScreen.display();

    }
}

package org.codetome.zircon.examples;

import org.codetome.zircon.api.builder.grid.AppConfigBuilder;
import org.codetome.zircon.api.color.TextColor;
import org.codetome.zircon.api.component.Button;
import org.codetome.zircon.api.component.*;
import org.codetome.zircon.api.component.Label;
import org.codetome.zircon.api.component.Panel;
import org.codetome.zircon.api.data.Position;
import org.codetome.zircon.api.data.Size;
import org.codetome.zircon.api.graphics.BoxType;
import org.codetome.zircon.api.graphics.Symbols;
import org.codetome.zircon.api.grid.AppConfig;
import org.codetome.zircon.api.grid.CursorStyle;
import org.codetome.zircon.api.grid.TileGrid;
import org.codetome.zircon.api.interop.Components;
import org.codetome.zircon.api.interop.Positions;
import org.codetome.zircon.api.interop.Screens;
import org.codetome.zircon.api.interop.Sizes;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.resource.ColorThemeResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.internal.application.SwingApplication;

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

    private static final int MAIN_MENU_PANEL_WIDTH = 25;
    private static final int MAIN_MENU_PANEL_HEIGHT = 10;
    private static final int PANEL_SPACING = 2;
    private static final int FONT_SIZE = 16;
    private static final ColorTheme THEME = ColorThemeResource.SOLARIZED_DARK_YELLOW.getTheme();

    private static boolean headless = false;

    public static void main(String[] args) {
        if (args.length > 0) {
            headless = true;
        }
        Dimension screenSize = headless ? new Dimension(1920, 1080) : Toolkit.getDefaultToolkit().getScreenSize();
        double columns = screenSize.getWidth() / FONT_SIZE;
        double rows = screenSize.getHeight() / FONT_SIZE;
        Size terminalSize = Sizes.create((int) columns, (int) rows);

        final AppConfig config = AppConfigBuilder.Companion.newBuilder()
                .defaultSize(terminalSize)
                .defaultTileset(CP437TilesetResource.CHEEPICUS_16X16)
                .cursorBlinking(true)
                .cursorStyle(CursorStyle.FIXED_BACKGROUND)
                .cursorColor(TextColor.Companion.fromString("#ff00ff"))
                .build();

        final SwingApplication app = SwingApplication.Companion.create(config);

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
                .size(difficultyPanel.getBoundableSize().minus(Sizes.create(2, 2)))
                .build();

        Arrays.asList(DIFFICULTIES).forEach((diff) -> {
            difficultyRadio.addOption(diff, diff);
        });

        difficultyPanel.addComponent(difficultyRadio);
        optionsScreen.addComponent(difficultyPanel);


        optionsScreen.applyColorTheme(THEME);

        // INTERACTIONS

        quitButton.onMouseReleased((mouseAction -> {
            if (!headless) {
                System.exit(0);
            }
        }));

        optionsButton.onMouseReleased((mouseAction -> optionsScreen.display()));

        backButton.onMouseReleased((mouseAction -> mainMenuScreen.display()));

        // START IT UP
        mainMenuScreen.display();

    }
}

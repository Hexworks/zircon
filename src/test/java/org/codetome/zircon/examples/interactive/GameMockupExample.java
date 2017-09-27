package org.codetome.zircon.examples.interactive;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.Symbols;
import org.codetome.zircon.api.builder.DeviceConfigurationBuilder;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.component.*;
import org.codetome.zircon.api.component.Button;
import org.codetome.zircon.api.component.Label;
import org.codetome.zircon.api.component.Panel;
import org.codetome.zircon.api.component.builder.ButtonBuilder;
import org.codetome.zircon.api.component.builder.LabelBuilder;
import org.codetome.zircon.api.component.builder.PanelBuilder;
import org.codetome.zircon.api.component.builder.RadioButtonGroupBuilder;
import org.codetome.zircon.api.font.Font;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.resource.ColorThemeResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.api.terminal.Terminal;
import org.codetome.zircon.api.terminal.config.CursorStyle;
import org.codetome.zircon.internal.graphics.BoxType;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
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
    private static final Size TERMINAL_SIZE;
    private static final ColorTheme THEME = ColorThemeResource.SOLARIZED_DARK_YELLOW.getTheme();
    private static final Font<BufferedImage> FONT = CP437TilesetResource.ROGUE_YUN_16X16.toFont();

    private static boolean headless = false;

    static {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double columns = screenSize.getWidth() / FONT_SIZE;
        double rows = screenSize.getHeight() / FONT_SIZE;
        TERMINAL_SIZE = Size.of((int) columns, (int) rows);
    }

    @Test
    public void checkSetup() {
        main(new String[]{"test"});
    }

    public static void main(String[] args) {
        if(args.length > 0) {
            headless = true;
        }
        // for this example we only need a default terminal (no extra config)
        final Terminal terminal = TerminalBuilder.newBuilder()
                .initialTerminalSize(TERMINAL_SIZE)
                .fullScreen()
                .font(FONT)
                .deviceConfiguration(DeviceConfigurationBuilder.newBuilder()
                        .cursorBlinking(true)
                        .cursorStyle(CursorStyle.USE_CHARACTER_FOREGROUND)
                        .build())
                .buildTerminal(args.length > 0);

        // ==========
        // MAIN MENU
        // ==========

        Screen mainMenuScreen = buildScreen(terminal);
        Position menuPosition = Position.of(
                (TERMINAL_SIZE.getColumns() - MAIN_MENU_PANEL_WIDTH) / 2,
                (TERMINAL_SIZE.getRows() - MAIN_MENU_PANEL_HEIGHT) / 2);
        Label mainMenuLabel = LabelBuilder.newBuilder()
                .text(MAIN_MENU_LABEL)
                .position(menuPosition.withRelativeRow(-3).withRelativeColumn(4))
                .build();
        mainMenuScreen.addComponent(mainMenuLabel);

        Panel menuPanel = PanelBuilder.newBuilder()
                .boxType(BoxType.LEFT_RIGHT_DOUBLE)
                .wrapInBox()
                .position(menuPosition)
                .size(Size.of(MAIN_MENU_PANEL_WIDTH, MAIN_MENU_PANEL_HEIGHT))
                .build();

        Button newGameButton = ButtonBuilder.newBuilder()
                .text(NEW_GAME_BUTTON_LABEL)
                .position(Position.of(3, 1))
                .build();
        menuPanel.addComponent(newGameButton);

        Button optionsButton = ButtonBuilder.newBuilder()
                .text(OPTIONS_BUTTON_LABEL)
                .position(Position.of(4, 3))
                .build();
        menuPanel.addComponent(optionsButton);

        Button quitButton = ButtonBuilder.newBuilder()
                .text(QUIT_BUTTON_LABEL)
                .position(Position.of(7, 5))
                .build();
        menuPanel.addComponent(quitButton);

        mainMenuScreen.addComponent(menuPanel);
        mainMenuScreen.applyTheme(THEME);

        // ==========
        // OPTIONS
        // ==========

        Screen optionsScreen = buildScreen(terminal);

        Button backButton = ButtonBuilder.newBuilder()
                .text(BACK_LABEL)
                .position(Position.of(
                        PANEL_SPACING,
                        TERMINAL_SIZE.getRows() - PANEL_SPACING))
                .build();
        optionsScreen.addComponent(backButton);

        Button applyButton = ButtonBuilder.newBuilder()
                .text(APPLY_LABEL)
                .position(Position.of(PANEL_SPACING, 0).relativeToRightOf(backButton))
                .build();
        optionsScreen.addComponent(applyButton);

        Panel difficultyPanel = PanelBuilder.newBuilder()
                .size(Size.of((TERMINAL_SIZE.getColumns() - PANEL_SPACING) / 3, 9))
                .position(Position.of(PANEL_SPACING, PANEL_SPACING))
                .wrapInBox()
                .boxType(BoxType.LEFT_RIGHT_DOUBLE)
                .title(DIFFICULTY_LABEL)
                .build();

        RadioButtonGroup difficultyRadio = RadioButtonGroupBuilder.newBuilder()
                .position(Position.of(1, 1))
                .spacing(1)
                .size(difficultyPanel.getBoundableSize().minus(Size.of(2, 2)))
                .build();

        Arrays.asList(DIFFICULTIES).forEach((diff) -> {
            difficultyRadio.addOption(diff, diff);
        });

        difficultyPanel.addComponent(difficultyRadio);
        optionsScreen.addComponent(difficultyPanel);


        optionsScreen.applyTheme(THEME);

        // INTERACTIONS

        quitButton.onMouseReleased((mouseAction -> {
            if(!headless) {
                System.exit(0);
            }
        }));

        optionsButton.onMouseReleased((mouseAction -> optionsScreen.display()));

        backButton.onMouseReleased((mouseAction -> mainMenuScreen.display()));

        // START IT UP
        mainMenuScreen.display();

    }

    private static Screen buildScreen(Terminal terminal) {
        Screen result = TerminalBuilder.createScreenFor(terminal);
        return result;
    }
}

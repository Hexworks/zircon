package org.codetome.zircon.examples;

import org.codetome.zircon.api.builder.grid.AppConfigBuilder;
import org.codetome.zircon.api.color.TextColor;
import org.codetome.zircon.api.data.CharacterTile;
import org.codetome.zircon.api.data.Position;
import org.codetome.zircon.api.grid.AppConfig;
import org.codetome.zircon.api.grid.CursorStyle;
import org.codetome.zircon.api.grid.TileGrid;
import org.codetome.zircon.api.input.InputType;
import org.codetome.zircon.api.input.KeyStroke;
import org.codetome.zircon.api.interop.Screens;
import org.codetome.zircon.api.interop.Sizes;
import org.codetome.zircon.api.interop.TextColors;
import org.codetome.zircon.api.interop.Tiles;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.internal.application.SwingApplication;

import java.util.ArrayList;
import java.util.List;

import static org.codetome.zircon.api.color.ANSITextColor.BLACK;
import static org.codetome.zircon.api.color.ANSITextColor.RED;
import static org.codetome.zircon.api.input.InputType.Enter;

public class TypingExample {

    private static final int TERMINAL_WIDTH = 40;

    private static final List<InputType> EXIT_CONDITIONS = new ArrayList<>();
    private static final CharacterTile TEXT_CHAR_TEMPLATE = Tiles.newBuilder()
            .foregroundColor(TextColors.fromString("#F7923A"))
            .backgroundColor(BLACK)
            .buildCharacterTile();

    private static boolean headless = false;

    static {
        EXIT_CONDITIONS.add(InputType.Escape);
        EXIT_CONDITIONS.add(InputType.EOF);
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            headless = true;
        }

        final AppConfig config = AppConfigBuilder.Companion.newBuilder()
                .defaultSize(Sizes.create(TERMINAL_WIDTH, 10))
                .defaultTileset(CP437TilesetResource.BISASAM_20X20)
                .cursorBlinking(true)
                .cursorStyle(CursorStyle.FIXED_BACKGROUND)
                .cursorColor(TextColor.Companion.fromString("#ff00ff"))
                .build();

        final SwingApplication app = SwingApplication.Companion.create(config);

        final TileGrid tileGrid = app.getTileGrid();

        app.start();

        final Screen screen = Screens.createScreenFor(tileGrid);

        startTypingSupportForScreen(screen);
//        startTypingSupportForTerminal(grid);
    }

    private static void startTypingSupportForScreen(Screen screen) {
        screen.onInput((input) -> {
            final Position pos = screen.getCursorPosition();
            if (EXIT_CONDITIONS.contains(input.getInputType()) && !headless) {
                System.exit(0);
            } else if (input.inputTypeIs(Enter)) {
                screen.putCursorAt(pos.withRelativeY(1).withX(0));
            } else {
                if (input.isKeyStroke()) {
                    final KeyStroke ks = input.asKeyStroke();
                    screen.setTileAt(pos, TEXT_CHAR_TEMPLATE.withCharacter(ks.getCharacter()));
                    screen.moveCursorForward();
                }
            }
        });
    }

    private static void startTypingSupportForTerminal(TileGrid tileGrid) {
        tileGrid.onInput((input) -> {
            final Position pos = tileGrid.getCursorPosition();
            if (EXIT_CONDITIONS.contains(input.getInputType()) && !headless) {
                System.exit(0);
            } else if (input.inputTypeIs(Enter)) {
                tileGrid.putCursorAt(pos.withRelativeY(1).withX(0));
            } else {
                if (input.isKeyStroke()) {
                    final KeyStroke ks = input.asKeyStroke();
                    tileGrid.setBackgroundColor(BLACK);
                    tileGrid.setForegroundColor(RED);
                    tileGrid.putCharacter(ks.getCharacter());
                    tileGrid.resetColorsAndModifiers();
                }
            }
        });
    }
}

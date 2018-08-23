package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.application.CursorStyle;
import org.hexworks.zircon.api.builder.application.AppConfigBuilder;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.data.CharacterTile;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.input.InputType;
import org.hexworks.zircon.api.resource.BuiltInCP437Tileset;
import org.hexworks.zircon.api.screen.Screen;

import java.util.ArrayList;
import java.util.List;

public class TypingExample {

    private static final int TERMINAL_WIDTH = 40;

    private static final List<InputType> EXIT_CONDITIONS = new ArrayList<>();
    private static final CharacterTile TEXT_CHAR_TEMPLATE = Tiles.newBuilder()
            .foregroundColor(TileColors.fromString("#F7923A"))
            .backgroundColor(ANSITileColor.BLACK)
            .buildCharacterTile();

    static {
        EXIT_CONDITIONS.add(InputType.Escape);
        EXIT_CONDITIONS.add(InputType.EOF);
    }

    public static void main(String[] args) {

        final TileGrid tileGrid = SwingApplications.startTileGrid(AppConfigBuilder.Companion.newBuilder()
                .defaultTileset(BuiltInCP437Tileset.BISASAM_16X16)
                .defaultSize(Sizes.create(TERMINAL_WIDTH, 10))
                .cursorBlinking(true)
                .cursorStyle(CursorStyle.FIXED_BACKGROUND)
                .cursorColor(TileColors.fromString("#ff00ff"))
                .build());

        final Screen screen = Screens.createScreenFor(tileGrid);

        startTypingSupportForScreen(screen);

        screen.display();
//        startTypingSupportForTerminal(grid);
    }

    private static void startTypingSupportForScreen(Screen screen) {
        screen.onInput((input) -> {
            final Position pos = screen.cursorPosition();
            if (EXIT_CONDITIONS.contains(input.getInputType())) {
                System.exit(0);
            } else if (input.inputTypeIs(InputType.Enter)) {
                screen.putCursorAt(pos.withRelativeY(1).withX(0));
            } else {
                input.asKeyStroke().ifPresent(ks -> {
                    screen.setTileAt(pos, TEXT_CHAR_TEMPLATE.withCharacter(ks.getCharacter()));
                    screen.moveCursorForward();
                });
            }
        });
    }

    private static void startTypingSupportForTerminal(TileGrid tileGrid) {
        tileGrid.onInput((input) -> {
            final Position pos = tileGrid.cursorPosition();
            if (EXIT_CONDITIONS.contains(input.getInputType())) {
                System.exit(0);
            } else if (input.inputTypeIs(InputType.Enter)) {
                tileGrid.putCursorAt(pos.withRelativeY(1).withX(0));
            } else {
                input.asKeyStroke().ifPresent(ks -> {
                    tileGrid.setBackgroundColor(ANSITileColor.BLACK);
                    tileGrid.setForegroundColor(ANSITileColor.RED);
                    tileGrid.putCharacter(ks.getCharacter());
                    tileGrid.resetColorsAndModifiers();
                });
            }
        });
    }
}

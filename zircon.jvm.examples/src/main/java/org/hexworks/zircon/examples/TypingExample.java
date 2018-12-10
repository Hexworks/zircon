package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.TileColors;
import org.hexworks.zircon.api.application.CursorStyle;
import org.hexworks.zircon.api.builder.application.AppConfigBuilder;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.input.InputType;
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource;

import java.util.ArrayList;
import java.util.List;

public class TypingExample {

    private static final int TERMINAL_WIDTH = 40;

    private static final List<InputType> EXIT_CONDITIONS = new ArrayList<>();

    static {
        EXIT_CONDITIONS.add(InputType.Escape);
        EXIT_CONDITIONS.add(InputType.EOF);
    }

    public static void main(String[] args) {

        final TileGrid tileGrid = SwingApplications.startTileGrid(AppConfigBuilder.Companion.newBuilder()
                .withDefaultTileset(BuiltInCP437TilesetResource.BISASAM_16X16)
                .withSize(Sizes.create(TERMINAL_WIDTH, 10))
                .withCursorBlinking(true)
                .withCursorStyle(CursorStyle.FIXED_BACKGROUND)
                .withCursorColor(TileColors.fromString("#ff00ff"))
                .build());

        startTypingSupport(tileGrid);
    }


    private static void startTypingSupport(TileGrid tileGrid) {
        tileGrid.onInput((input) -> {
            final Position pos = tileGrid.cursorPosition();
            if (EXIT_CONDITIONS.contains(input.inputType())) {
                System.exit(0);
            } else if (input.inputTypeIs(InputType.Enter)) {
                tileGrid.putCursorAt(pos.withRelativeY(1).withX(0));
            } else {
                input.asKeyStroke().ifPresent(ks -> {
                    tileGrid.setBackgroundColor(ANSITileColor.BLACK);
                    tileGrid.setForegroundColor(ANSITileColor.RED);
                    tileGrid.putCharacter(ks.getCharacter());
                });
            }
        });
    }
}

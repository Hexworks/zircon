package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.TileColors;
import org.hexworks.zircon.api.UIEventResponses;
import org.hexworks.zircon.api.application.CursorStyle;
import org.hexworks.zircon.api.builder.application.AppConfigBuilder;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.uievent.KeyCode;
import org.hexworks.zircon.api.uievent.KeyboardEventType;

public class TypingExample {

    private static final int TERMINAL_WIDTH = 40;

    public static void main(String[] args) {

        // TODO: doesn't seem to work with libgdx
        final TileGrid tileGrid = SwingApplications.startTileGrid(AppConfigBuilder.Companion.newBuilder()
                .withDefaultTileset(CP437TilesetResources.bisasam16x16())
                .withSize(Sizes.create(TERMINAL_WIDTH, 10))
                .withCursorBlinking(true)
                .withCursorStyle(CursorStyle.FIXED_BACKGROUND)
                .withCursorColor(TileColors.fromString("#ff00ff"))
                .build());

        startTypingSupport(tileGrid);
    }


    private static void startTypingSupport(TileGrid tileGrid) {
        tileGrid.setBackgroundColor(ANSITileColor.BLACK);
        tileGrid.setForegroundColor(ANSITileColor.RED);
        tileGrid.onKeyboardEvent(KeyboardEventType.KEY_PRESSED, (event, phase) -> {
            final Position pos = tileGrid.cursorPosition();
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                System.exit(0);
            } else if (event.getCode().equals(KeyCode.ENTER)) {
                tileGrid.putCursorAt(pos.withRelativeY(1).withX(0));
            } else {
                tileGrid.putCharacter(event.getKey().charAt(0));
            }
            return UIEventResponses.processed();
        });
    }
}

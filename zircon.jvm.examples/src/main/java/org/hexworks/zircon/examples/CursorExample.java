package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.AppConfigs;
import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.TileColors;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.application.Application;
import org.hexworks.zircon.api.application.CursorStyle;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.grid.TileGrid;

public class CursorExample {

    private static final int TERMINAL_WIDTH = 30;
    private static final int TERMINAL_HEIGHT = 10;
    private static final Size SIZE = Size.Companion.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);

    public static void main(String[] args) {

        AppConfig config = AppConfigs.newConfig()
                .withCursorColor(TileColors.fromString("#ff8844"))
                .withBlinkLengthInMilliSeconds(500)
                .withCursorStyle(CursorStyle.FIXED_BACKGROUND)
                .withCursorBlinking(true)
                .withSize(SIZE)
                .withDefaultTileset(CP437TilesetResources.taffer20x20())
                .build();

        Application app = SwingApplications.startApplication(config);

        TileGrid grid = app.getTileGrid();

        // for this example we need the cursor to be visible
        grid.setCursorVisibility(true);

        String text = "Cursor example...";
        for (int i = 0; i < text.length(); i++) {
//            grid.putCharacter(text.charAt(i));
        }


    }
}

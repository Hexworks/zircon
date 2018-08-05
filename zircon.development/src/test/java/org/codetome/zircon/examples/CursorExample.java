package org.codetome.zircon.examples;

import org.codetome.zircon.api.builder.grid.AppConfigBuilder;
import org.codetome.zircon.api.color.TextColor;
import org.codetome.zircon.api.data.Size;
import org.codetome.zircon.api.grid.AppConfig;
import org.codetome.zircon.api.grid.CursorStyle;
import org.codetome.zircon.api.grid.TileGrid;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.gui.swing.application.SwingApplication;

public class CursorExample {

    private static final int TERMINAL_WIDTH = 30;
    private static final int TERMINAL_HEIGHT = 10;
    private static final Size SIZE = Size.Companion.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);

    public static void main(String[] args) {

        AppConfig config = AppConfigBuilder.Companion.newBuilder()
                .cursorColor(TextColor.Companion.fromString("#ff8844"))
                .blinkLengthInMilliSeconds(500)
                .cursorStyle(CursorStyle.FIXED_BACKGROUND)
                .cursorBlinking(true)
                .defaultSize(SIZE)
                .defaultTileset(CP437TilesetResource.TAFFER_20X20)
                .build();

        SwingApplication app = SwingApplication.Companion.create(config);

        app.start();

        TileGrid grid = app.getTileGrid();

        // for this example we need the cursor to be visible
        grid.setCursorVisibility(true);

        String text = "Cursor example...";
        for (int i = 0; i < text.length(); i++) {
            grid.putCharacter(text.charAt(i));
        }


    }
}

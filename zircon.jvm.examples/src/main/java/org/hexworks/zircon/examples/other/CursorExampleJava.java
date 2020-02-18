package org.hexworks.zircon.examples.other;

;
import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.CharacterTileStrings;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.application.Application;
import org.hexworks.zircon.api.application.CursorStyle;
import org.hexworks.zircon.api.color.TileColor;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.grid.TileGrid;

public class CursorExampleJava {

    private static final int TERMINAL_WIDTH = 30;
    private static final int TERMINAL_HEIGHT = 10;
    private static final Size SIZE = Size.Companion.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);

    public static void main(String[] args) {

        AppConfig config = AppConfig.newBuilder()
                .withCursorColor(TileColor.fromString("#ff8844"))
                .withBlinkLengthInMilliSeconds(500)
                .withCursorStyle(CursorStyle.FIXED_BACKGROUND)
                .withCursorBlinking(true)
                .withSize(SIZE)
                .withDefaultTileset(CP437TilesetResources.taffer20x20())
                .build();

        Application app = SwingApplications.startApplication(config);

        TileGrid grid = app.getTileGrid();

        // for this example we need the cursor to be visible
        grid.setCursorVisible(true);

        String text = "Cursor example...";
        grid.draw(CharacterTileStrings.newBuilder().withText(text).build());
        grid.setCursorPosition(Position.create(text.length(), 0));


    }
}

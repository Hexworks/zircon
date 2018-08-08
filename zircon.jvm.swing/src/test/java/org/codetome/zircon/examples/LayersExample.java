package org.codetome.zircon.examples;

import org.codetome.zircon.api.builder.graphics.LayerBuilder;
import org.codetome.zircon.api.builder.grid.AppConfigBuilder;
import org.codetome.zircon.api.color.TextColor;
import org.codetome.zircon.api.data.Position;
import org.codetome.zircon.api.data.Size;
import org.codetome.zircon.api.data.Tile;
import org.codetome.zircon.api.grid.AppConfig;
import org.codetome.zircon.api.grid.CursorStyle;
import org.codetome.zircon.api.grid.TileGrid;
import org.codetome.zircon.api.interop.*;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.internal.application.SwingApplication;
import org.jetbrains.annotations.NotNull;

public class LayersExample {

    private static final int TERMINAL_WIDTH = 45;
    private static final int TERMINAL_HEIGHT = 5;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);

    public static void main(String[] args) {

        final AppConfig config = AppConfigBuilder.Companion.newBuilder()
                .defaultSize(SIZE)
                .defaultTileset(CP437TilesetResource.JOLLY_12X12)
                .cursorBlinking(true)
                .cursorStyle(CursorStyle.FIXED_BACKGROUND)
                .cursorColor(TextColor.Companion.fromString("#ff00ff"))
                .build();

        final SwingApplication app = SwingApplication.Companion.create(config);

        final TileGrid tileGrid = app.getTileGrid();

        app.start();

        final Screen screen = Screens.createScreenFor(tileGrid);
        screen.setCursorVisibility(false); // we don't want the cursor right now

        final String firstRow = "This is white title on black";
        for (int x = 0; x < firstRow.length(); x++) {
            screen.setTileAt(
                    Positions.create(x + 1, 1),
                    buildWhiteOnBlack(firstRow.charAt(x)));
        }

        final String secondRow = "Like the row above but with blue overlay.";
        for (int x = 0; x < secondRow.length(); x++) {
            screen.setTileAt(
                    Positions.create(x + 1, 2),
                    buildWhiteOnBlack(secondRow.charAt(x)));
        }

        addOverlayAt(screen,
                Positions.create(1, 2),
                Sizes.create(secondRow.length(), 1),
                TextColors.create(50, 50, 200, 127));

        screen.display();
    }

    private static void addOverlayAt(Screen screen, Position offset, Size size, TextColor color) {
        screen.pushLayer(new LayerBuilder()
                .offset(offset)
                .size(size)
                .build()
                .fill(Tiles.newBuilder()
                        .backgroundColor(color)
                        .character(' ')
                        .build()));
    }

    @NotNull
    private static Tile buildWhiteOnBlack(char c) {
        return Tiles.newBuilder()
                .character(c)
                .backgroundColor(TextColors.create(0, 0, 0, 255))
                .foregroundColor(TextColors.create(255, 255, 255, 255))
                .build();
    }

}

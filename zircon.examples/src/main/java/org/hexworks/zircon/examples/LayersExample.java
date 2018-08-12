package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.application.Application;
import org.hexworks.zircon.api.builder.graphics.LayerBuilder;
import org.hexworks.zircon.api.color.TileColor;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.data.Tile;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.CP437TilesetResource;
import org.hexworks.zircon.api.screen.Screen;
import org.jetbrains.annotations.NotNull;

public class LayersExample {

    private static final int TERMINAL_WIDTH = 45;
    private static final int TERMINAL_HEIGHT = 5;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);

    public static void main(String[] args) {

        Application app = SwingApplications.startApplication(AppConfigs.newConfig()
                .defaultTileset(CP437TilesetResource.ROGUE_YUN_16X16)
                .defaultSize(SIZE)
                .debugMode(true)
                .build());

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
                TileColors.create(50, 50, 200, 127));

        screen.display();
    }

    private static void addOverlayAt(Screen screen, Position offset, Size size, TileColor color) {
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
                .backgroundColor(TileColors.create(0, 0, 0, 255))
                .foregroundColor(TileColors.create(255, 255, 255, 255))
                .build();
    }

}

package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.application.Application;
import org.hexworks.zircon.api.builder.graphics.LayerBuilder;
import org.hexworks.zircon.api.color.TileColor;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.data.Tile;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource;
import org.jetbrains.annotations.NotNull;

public class LayersExample {

    private static final int TERMINAL_WIDTH = 45;
    private static final int TERMINAL_HEIGHT = 5;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);

    public static void main(String[] args) {

        Application app = SwingApplications.startApplication(AppConfigs.newConfig()
                .withDefaultTileset(BuiltInCP437TilesetResource.ROGUE_YUN_16X16)
                .withSize(SIZE)
                .withDebugMode(true)
                .build());

        final TileGrid tileGrid = app.getTileGrid();

        app.start();

        final String firstRow = "This is white title on black";
        for (int x = 0; x < firstRow.length(); x++) {
            tileGrid.setTileAt(
                    Positions.create(x + 1, 1),
                    buildWhiteOnBlack(firstRow.charAt(x)));
        }

        final String secondRow = "Like the row above but with blue overlay.";
        for (int x = 0; x < secondRow.length(); x++) {
            tileGrid.setTileAt(
                    Positions.create(x + 1, 2),
                    buildWhiteOnBlack(secondRow.charAt(x)));
        }

        addOverlayAt(tileGrid,
                Positions.create(1, 2),
                Sizes.create(secondRow.length(), 1),
                TileColors.create(50, 50, 200, 127));

    }

    private static void addOverlayAt(TileGrid tileGrid, Position offset, Size size, TileColor color) {
        tileGrid.pushLayer(new LayerBuilder()
                .withOffset(offset)
                .withSize(size)
                .build()
                .fill(Tiles.newBuilder()
                        .withBackgroundColor(color)
                        .withCharacter(' ')
                        .build()));
    }

    @NotNull
    private static Tile buildWhiteOnBlack(char c) {
        return Tiles.newBuilder()
                .withCharacter(c)
                .withBackgroundColor(TileColors.create(0, 0, 0, 255))
                .withForegroundColor(TileColors.create(255, 255, 255, 255))
                .build();
    }

}

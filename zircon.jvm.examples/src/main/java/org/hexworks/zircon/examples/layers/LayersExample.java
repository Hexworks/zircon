package org.hexworks.zircon.examples.layers;

import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.LibgdxApplications;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.builder.graphics.LayerBuilder;
import org.hexworks.zircon.api.color.TileColor;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.data.Tile;
import org.hexworks.zircon.api.grid.TileGrid;
import org.jetbrains.annotations.NotNull;

public class LayersExample {

    private static final int TERMINAL_WIDTH = 45;
    private static final int TERMINAL_HEIGHT = 5;
    private static final Size SIZE = Size.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);

    public static void main(String[] args) {

        final TileGrid tileGrid = LibgdxApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(CP437TilesetResources.rogueYun16x16())
                .withSize(SIZE)
                .withDebugMode(true)
                .build());

        final String firstRow = "This is white title on black";
        for (int x = 0; x < firstRow.length(); x++) {
            tileGrid.draw(
                    buildWhiteOnBlack(firstRow.charAt(x)),
                    Position.create(x + 1, 1));
        }

        final String secondRow = "Like the row above but with blue overlay.";
        for (int x = 0; x < secondRow.length(); x++) {
            tileGrid.draw(
                    buildWhiteOnBlack(secondRow.charAt(x)),
                    Position.create(x + 1, 2));
        }

        addOverlayAt(tileGrid,
                Position.create(1, 2),
                Size.create(secondRow.length(), 1),
                TileColor.create(50, 50, 200, 127));

    }

    private static void addOverlayAt(TileGrid tileGrid, Position offset, Size size, TileColor color) {
        tileGrid.addLayer(new LayerBuilder()
                .withOffset(offset)
                .withSize(size)
                .withFiller(Tile.newBuilder()
                        .withBackgroundColor(color)
                        .withCharacter(' ')
                        .build())
                .build());
    }

    @NotNull
    private static Tile buildWhiteOnBlack(char c) {
        return Tile.newBuilder()
                .withCharacter(c)
                .withBackgroundColor(TileColor.create(0, 0, 0, 255))
                .withForegroundColor(TileColor.create(255, 255, 255, 255))
                .build();
    }

}

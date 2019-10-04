package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.grid.TileGrid;

public class CreatingATileGrid {

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid(
                AppConfigs.newConfig()
                        .withSize(Sizes.create(10, 10))
                        .withDefaultTileset(CP437TilesetResources.rexPaint16x16())
                        .build());

        tileGrid.draw(
                Tiles.newBuilder()
                        .withBackgroundColor(ANSITileColor.CYAN)
                        .withForegroundColor(ANSITileColor.WHITE)
                        .withCharacter('x')
                        .build(),
                Positions.create(2, 3));

        tileGrid.draw(
                Tiles.newBuilder()
                        .withBackgroundColor(ANSITileColor.RED)
                        .withForegroundColor(ANSITileColor.GREEN)
                        .withCharacter('y')
                        .build(),
                Positions.create(3, 4));

        tileGrid.draw(
                Tiles.newBuilder()
                        .withBackgroundColor(ANSITileColor.BLUE)
                        .withForegroundColor(ANSITileColor.MAGENTA)
                        .withCharacter('z')
                        .build(),
                Positions.create(4, 5));
    }
}

package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Tile;
import org.hexworks.zircon.api.grid.TileGrid;

public class CreatingAnApplication {

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid(
                AppConfig.newBuilder()
                        .withSize(10, 10)
                        .withDefaultTileset(CP437TilesetResources.rexPaint16x16())
                        .build());

        tileGrid.draw(
                Tile.newBuilder()
                        .withBackgroundColor(ANSITileColor.CYAN)
                        .withForegroundColor(ANSITileColor.WHITE)
                        .withCharacter('x')
                        .build(),
                Position.create(2, 3));

        tileGrid.draw(
                Tile.newBuilder()
                        .withBackgroundColor(ANSITileColor.RED)
                        .withForegroundColor(ANSITileColor.GREEN)
                        .withCharacter('y')
                        .build(),
                Position.create(3, 4));

        tileGrid.draw(
                Tile.newBuilder()
                        .withBackgroundColor(ANSITileColor.BLUE)
                        .withForegroundColor(ANSITileColor.MAGENTA)
                        .withCharacter('z')
                        .build(),
                Position.create(4, 5));
    }
}

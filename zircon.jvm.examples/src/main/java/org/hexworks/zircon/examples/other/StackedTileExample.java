package org.hexworks.zircon.examples.other;

import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.color.TileColor;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.data.StackedTile;
import org.hexworks.zircon.api.data.Tile;
import org.hexworks.zircon.api.grid.TileGrid;

import static org.hexworks.zircon.api.color.ANSITileColor.*;

public class StackedTileExample {

    private static final int GRID_WIDTH = 60;
    private static final int GRID_HEIGHT = 30;
    private static final Size SIZE = Size.create(GRID_WIDTH, GRID_HEIGHT);

    public static void main(String[] args) {
        TileGrid app = SwingApplications.startApplication(AppConfig.newBuilder()
                .withDefaultTileset(CP437TilesetResources.taffer20x20())
                .withSize(SIZE)
                .build())
                .getTileGrid();

        StackedTile tile = StackedTile.create(
                Tile.newBuilder()
                        .withCharacter('x')
                        .withBackgroundColor(BLUE)
                        .withForegroundColor(BRIGHT_YELLOW)
                        .buildCharacterTile(),
                Tile.newBuilder()
                        .withCharacter('+')
                        .withBackgroundColor(TileColor.transparent())
                        .withForegroundColor(BRIGHT_MAGENTA)
                        .buildCharacterTile()
        );

        app.draw(tile, Position.create(4, 5));

    }
}

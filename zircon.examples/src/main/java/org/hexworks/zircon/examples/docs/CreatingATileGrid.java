package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.AppConfigs;
import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.Tiles;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.grid.TileGrid;

public class CreatingATileGrid {

    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid(
                AppConfigs.newBuilder()
                        .defaultSize(Sizes.create(10, 10))
                        .defaultTileset(CP437TilesetResources.rexPaint16x16())
                        .build());

        tileGrid.setTileAt(
                Positions.create(2, 3),
                Tiles.newBuilder()
                        .backgroundColor(ANSITileColor.CYAN)
                        .foregroundColor(ANSITileColor.WHITE)
                        .character('x')
                        .build());

        tileGrid.setTileAt(
                Positions.create(3, 4),
                Tiles.newBuilder()
                        .backgroundColor(ANSITileColor.RED)
                        .foregroundColor(ANSITileColor.GREEN)
                        .character('y')
                        .build());

        tileGrid.setTileAt(
                Positions.create(4, 5),
                Tiles.newBuilder()
                        .backgroundColor(ANSITileColor.BLUE)
                        .foregroundColor(ANSITileColor.MAGENTA)
                        .character('z')
                        .build());
    }
}

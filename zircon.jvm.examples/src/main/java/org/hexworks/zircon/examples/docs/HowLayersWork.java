package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.DrawSurfaces;
import org.hexworks.zircon.api.LibgdxApplications;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.color.TileColor;
import org.hexworks.zircon.api.data.Tile;
import org.hexworks.zircon.api.graphics.Layer;
import org.hexworks.zircon.api.grid.TileGrid;

public class HowLayersWork {

    public static void main(String[] args) {
        TileGrid tileGrid = LibgdxApplications.startTileGrid(AppConfig.newBuilder()
                .withSize(20, 10)
                .build());

        Layer layer0 = Layer.newBuilder()
                .withTileGraphics(DrawSurfaces.tileGraphicsBuilder()
                        .withSize(3, 3)
                        .withFiller(Tile.newBuilder()
                                .withForegroundColor(ANSITileColor.GREEN)
                                .withBackgroundColor(TileColor.transparent())
                                .withCharacter('X')
                                .build())
                        .build())
                .withOffset(1, 1)
                .build();

        Layer layer1 = Layer.newBuilder()
                .withTileGraphics(DrawSurfaces.tileGraphicsBuilder()
                        .withSize(3, 3)
                        .withFiller(Tile.newBuilder()
                                .withForegroundColor(ANSITileColor.RED)
                                .withBackgroundColor(TileColor.transparent())
                                .withCharacter('+')
                                .build())
                        .build())
                .withOffset(3, 3)
                .build();

        tileGrid.addLayer(layer0);
        tileGrid.addLayer(layer1);
    }
}

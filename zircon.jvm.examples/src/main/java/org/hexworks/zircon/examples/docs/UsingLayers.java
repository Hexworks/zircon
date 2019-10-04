package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.graphics.Layer;
import org.hexworks.zircon.api.grid.TileGrid;

public class UsingLayers {

    public static void main(String[] args) {

        TileGrid tileGrid = LibgdxApplications.startTileGrid(AppConfigs.newConfig()
                .withSize(20, 10)
                .build());

        Layer layer0 = Layers.newBuilder()
                .withTileGraphics(DrawSurfaces.tileGraphicsBuilder()
                        .withSize(3, 3)
                        .withFiller(Tiles.newBuilder()
                                .withForegroundColor(ANSITileColor.GREEN)
                                .withBackgroundColor(TileColors.transparent())
                                .withCharacter('X')
                                .build())
                        .build())
                .withOffset(1, 1)
                .build();

        Layer layer1 = Layers.newBuilder()
                .withTileGraphics(DrawSurfaces.tileGraphicsBuilder()
                        .withSize(3, 3)
                        .withFiller(Tiles.newBuilder()
                                .withForegroundColor(ANSITileColor.RED)
                                .withBackgroundColor(TileColors.transparent())
                                .withCharacter('+')
                                .build())
                        .build())
                .withOffset(3, 3)
                .build();

        tileGrid.addLayer(layer0);
        tileGrid.addLayer(layer1);

    }
}

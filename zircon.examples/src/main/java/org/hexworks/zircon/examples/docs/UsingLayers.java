package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.Layers;
import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.TileColors;
import org.hexworks.zircon.api.DrawSurfaces;
import org.hexworks.zircon.api.Tiles;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.graphics.Layer;
import org.hexworks.zircon.api.grid.TileGrid;

public class UsingLayers {

    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid();

        Layer layer0 = Layers.newBuilder()
                .withTileGraphic(DrawSurfaces.tileGraphicsBuilder()
                        .withSize(Sizes.create(3, 3))
                        .build()
                        .fill(Tiles.newBuilder()
                                .withForegroundColor(ANSITileColor.GREEN)
                                .withBackgroundColor(TileColors.transparent())
                                .withCharacter('X')
                                .build()))
                .withOffset(Positions.offset1x1())
                .build();

        Layer layer1 = Layers.newBuilder()
                .withTileGraphic(DrawSurfaces.tileGraphicsBuilder()
                        .withSize(Sizes.create(3, 3))
                        .build()
                        .fill(Tiles.newBuilder()
                                .withForegroundColor(ANSITileColor.RED)
                                .withBackgroundColor(TileColors.transparent())
                                .withCharacter('+')
                                .build()))
                .withOffset(Positions.create(3, 3))
                .build();

        tileGrid.pushLayer(layer0);
        tileGrid.pushLayer(layer1);


//        tileGrid.popLayer();
//        tileGrid.removeLayer(layer0);

    }
}

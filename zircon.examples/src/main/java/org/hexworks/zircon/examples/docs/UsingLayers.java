package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.Layers;
import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.TileColors;
import org.hexworks.zircon.api.TileGraphics;
import org.hexworks.zircon.api.Tiles;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.graphics.Layer;
import org.hexworks.zircon.api.grid.TileGrid;

public class UsingLayers {

    public static void main(String[] args) {

        TileGrid tileGrid = SwingApplications.startTileGrid();

        Layer layer0 = Layers.newBuilder()
                .tileGraphic(TileGraphics.newBuilder()
                        .size(Sizes.create(3, 3))
                        .build()
                        .withFiller(Tiles.newBuilder()
                                .foregroundColor(ANSITileColor.GREEN)
                                .backgroundColor(TileColors.transparent())
                                .character('X')
                                .build()))
                .offset(Positions.offset1x1())
                .build();

        Layer layer1 = Layers.newBuilder()
                .tileGraphic(TileGraphics.newBuilder()
                        .size(Sizes.create(3, 3))
                        .build()
                        .withFiller(Tiles.newBuilder()
                                .foregroundColor(ANSITileColor.RED)
                                .backgroundColor(TileColors.transparent())
                                .character('+')
                                .build()))
                .offset(Positions.create(3, 3))
                .build();

        tileGrid.pushLayer(layer0);
        tileGrid.pushLayer(layer1);


//        tileGrid.popLayer();
//        tileGrid.removeLayer(layer0);

    }
}

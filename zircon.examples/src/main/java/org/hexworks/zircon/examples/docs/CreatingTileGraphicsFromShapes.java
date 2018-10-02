package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.graphics.Symbols;
import org.hexworks.zircon.api.graphics.TileGraphics;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.TilesetResource;

public class CreatingTileGraphicsFromShapes {

    private static final TilesetResource TILESET = CP437TilesetResources.rexPaint16x16();

    public static void main(String[] args) {

        AppConfig config = AppConfigs.newConfig()
                .defaultTileset(TILESET)
                .build();

        TileGrid tileGrid = SwingApplications.startTileGrid(
                config);

        final TileGraphics background = DrawSurfaces.tileGraphicsBuilder()
                .size(tileGrid.getSize()) // you can fetch the size of a TileGrid like this
                .build()
                .fill(Tiles.newBuilder()
                        .character(Symbols.BULLET)
                        .backgroundColor(ANSITileColor.BLUE)
                        .foregroundColor(ANSITileColor.CYAN)
                        .build());

        final TileGraphics rectangle = Shapes.buildRectangle(
                Positions.defaultPosition(),
                tileGrid.getSize())
                .toTileGraphics(Tiles.newBuilder()
                                .character(Symbols.BLOCK_DENSE)
                                .backgroundColor(TileColors.transparent())
                                .foregroundColor(ANSITileColor.RED)
                                .build(),
                        config.getDefaultTileset());

        background.draw(rectangle, Positions.defaultPosition());

        // the default position is (0x0) which is the top left corner
        tileGrid.draw(background, Positions.defaultPosition());

    }
}

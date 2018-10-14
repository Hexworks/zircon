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
                .withDefaultTileset(TILESET)
                .build();

        TileGrid tileGrid = SwingApplications.startTileGrid(
                config);

        final TileGraphics background = DrawSurfaces.tileGraphicsBuilder()
                .withSize(tileGrid.getSize()) // you can fetch the size of a TileGrid like this
                .build()
                .fill(Tiles.newBuilder()
                        .withCharacter(Symbols.BULLET)
                        .withBackgroundColor(ANSITileColor.BLUE)
                        .withForegroundColor(ANSITileColor.CYAN)
                        .build());

        final TileGraphics rectangle = Shapes.buildRectangle(
                Positions.zero(),
                tileGrid.getSize())
                .toTileGraphics(Tiles.newBuilder()
                                .withCharacter(Symbols.BLOCK_DENSE)
                                .withBackgroundColor(TileColors.transparent())
                                .withForegroundColor(ANSITileColor.RED)
                                .build(),
                        config.getDefaultTileset());

        background.draw(rectangle, Positions.zero());

        // the default position is (0x0) which is the top left corner
        tileGrid.draw(background, Positions.zero());

    }
}

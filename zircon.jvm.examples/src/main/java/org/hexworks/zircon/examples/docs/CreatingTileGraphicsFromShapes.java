package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.color.TileColor;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Tile;
import org.hexworks.zircon.api.graphics.Symbols;
import org.hexworks.zircon.api.graphics.TileGraphics;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.TilesetResource;

public class CreatingTileGraphicsFromShapes {

    private static final TilesetResource TILESET = CP437TilesetResources.rexPaint16x16();

    public static void main(String[] args) {

        AppConfig config = AppConfig.newBuilder()
                .withDefaultTileset(TILESET)
                .build();

        TileGrid tileGrid = SwingApplications.startTileGrid(
                config);

        final TileGraphics background = DrawSurfaces.tileGraphicsBuilder()
                .withSize(tileGrid.getSize()) // you can fetch the size of a TileGrid like this
                .withFiller(Tile.newBuilder()
                        .withCharacter(Symbols.BULLET)
                        .withBackgroundColor(ANSITileColor.BLUE)
                        .withForegroundColor(ANSITileColor.CYAN)
                        .build())
                .build();

        final TileGraphics rectangle = Shapes.buildRectangle(
                Position.zero(),
                tileGrid.getSize())
                .toTileGraphics(Tile.newBuilder()
                                .withCharacter(Symbols.BLOCK_DENSE)
                                .withBackgroundColor(TileColor.transparent())
                                .withForegroundColor(ANSITileColor.RED)
                                .build(),
                        config.getDefaultTileset());

        background.draw(rectangle, Position.zero());

        // the default position is (0x0) which is the top left corner
        tileGrid.draw(background, Position.zero());

    }
}

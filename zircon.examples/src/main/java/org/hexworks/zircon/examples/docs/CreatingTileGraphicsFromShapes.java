package org.hexworks.zircon.examples.docs;

import org.hexworks.zircon.api.AppConfigs;
import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.Shapes;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.TileColors;
import org.hexworks.zircon.api.TileGraphics;
import org.hexworks.zircon.api.Tiles;
import org.hexworks.zircon.api.application.AppConfig;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.graphics.Symbols;
import org.hexworks.zircon.api.graphics.TileGraphic;
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

        final TileGraphic background = TileGraphics.newBuilder()
                .size(tileGrid.size()) // you can fetch the size of a TileGrid like this
                .build()
                .fill(Tiles.newBuilder()
                        .character(Symbols.BULLET)
                        .backgroundColor(ANSITileColor.BLUE)
                        .foregroundColor(ANSITileColor.CYAN)
                        .build());

        final TileGraphic rectangle = Shapes.buildRectangle(
                Positions.defaultPosition(),
                tileGrid.size())
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

package org.codetome.zircon.examples;

import org.codetome.zircon.api.data.Size;
import org.codetome.zircon.api.grid.AppConfig;
import org.codetome.zircon.api.grid.TileGrid;
import org.codetome.zircon.api.resource.GraphicalTilesetResource;
import org.codetome.zircon.gui.swing.internal.application.SwingApplication;
import org.codetome.zircon.jvm.api.interop.AppConfigs;
import org.codetome.zircon.jvm.api.interop.Positions;
import org.codetome.zircon.jvm.api.interop.Sizes;
import org.codetome.zircon.jvm.api.interop.Tiles;

import java.util.Random;

public class GraphicTilesetExample {

    private static final int TERMINAL_WIDTH = 50;
    private static final int TERMINAL_HEIGHT = 24;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static final GraphicalTilesetResource TILESET = GraphicalTilesetResource.NETHACK_16X16;
    private static final String[] NAMES = new String[]{
            "Giant ant",
            "Killer bee",
            "Fire ant",
            "Werewolf",
            "Dingo",
            "Hell hound pup",
            "Tiger",
            "Gremlin"
    };
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {

        AppConfig config = AppConfigs.newBuilder()
                .defaultSize(SIZE)
                .defaultTileset(TILESET)
                .build();

        SwingApplication app = SwingApplication.Companion.create(config);

        app.start();

        TileGrid tileGrid = app.getTileGrid();

        for (int row = 0; row < TERMINAL_HEIGHT; row++) {
            for (int col = 0; col < TERMINAL_WIDTH; col++) {
                final String name = NAMES[RANDOM.nextInt(NAMES.length)];
                tileGrid.setTileAt(Positions.create(col, row), Tiles.newBuilder()
                        .name(name)
                        .tileset(TILESET)
                        .buildImageTile());
            }
        }

    }

}

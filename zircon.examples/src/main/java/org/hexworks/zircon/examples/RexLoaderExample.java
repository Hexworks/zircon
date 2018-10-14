package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.application.Application;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.graphics.Layer;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource;
import org.hexworks.zircon.api.resource.REXPaintResource;
import org.hexworks.zircon.api.resource.TilesetResource;
import org.hexworks.zircon.api.screen.Screen;

import java.io.InputStream;
import java.util.List;

public class RexLoaderExample {
    private static final int TERMINAL_WIDTH = 16;
    private static final int TERMINAL_HEIGHT = 16;
    private static final TilesetResource TILESET = BuiltInCP437TilesetResource.YOBBO_20X20;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static final InputStream RESOURCE = RexLoaderExample.class.getResourceAsStream("/rex_files/cp437_table.xp");

    public static void main(String[] args) {
        REXPaintResource rex = REXPaintResource.loadREXFile(RESOURCE);

        Application app = SwingApplications.startApplication(AppConfigs.newConfig()
                .withDefaultTileset(BuiltInCP437TilesetResource.TAFFER_20X20)
                .withSize(SIZE)
                .withDebugMode(true)
                .build());

        final TileGrid tileGrid = app.getTileGrid();

        app.start();

        final Screen screen = Screens.createScreenFor(tileGrid);
        screen.setCursorVisibility(false);
        List<Layer> layers = rex.toLayerList(TILESET);
        for (Layer layer : layers) {
            screen.pushLayer(layer);
        }
        screen.display();
    }
}

package org.codetome.zircon.examples;

import org.codetome.zircon.api.builder.grid.AppConfigBuilder;
import org.codetome.zircon.api.color.TextColor;
import org.codetome.zircon.api.data.CharacterTile;
import org.codetome.zircon.api.data.Size;
import org.codetome.zircon.api.graphics.Layer;
import org.codetome.zircon.api.grid.AppConfig;
import org.codetome.zircon.api.grid.CursorStyle;
import org.codetome.zircon.api.grid.TileGrid;
import org.codetome.zircon.api.interop.Screens;
import org.codetome.zircon.api.interop.Sizes;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.resource.REXPaintResource;
import org.codetome.zircon.api.resource.TilesetResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.internal.application.SwingApplication;

import java.io.InputStream;
import java.util.List;

public class RexLoaderExample {
    private static final int TERMINAL_WIDTH = 16;
    private static final int TERMINAL_HEIGHT = 16;
    private static final TilesetResource<CharacterTile> TILESET = CP437TilesetResource.YOBBO_20X20;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static final InputStream RESOURCE = RexLoaderExample.class.getResourceAsStream("/rex_files/cp437_table.xp");

    public static void main(String[] args) {
        REXPaintResource rex = REXPaintResource.loadREXFile(RESOURCE);

        final AppConfig config = AppConfigBuilder.Companion.newBuilder()
                .defaultSize(SIZE)
                .defaultTileset(TILESET)
                .cursorBlinking(true)
                .cursorStyle(CursorStyle.FIXED_BACKGROUND)
                .cursorColor(TextColor.Companion.fromString("#ff00ff"))
                .build();

        final SwingApplication app = SwingApplication.Companion.create(config);

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

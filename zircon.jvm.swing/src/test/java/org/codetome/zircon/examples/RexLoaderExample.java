package org.codetome.zircon.examples;

import org.codetome.zircon.TerminalUtils;
import org.codetome.zircon.api.data.Size;
import org.codetome.zircon.api.graphics.Layer;
import org.codetome.zircon.api.interop.Screens;
import org.codetome.zircon.api.interop.Sizes;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.resource.REXPaintResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.api.terminal.Terminal;

import java.io.InputStream;
import java.util.List;

public class RexLoaderExample {
    private static final int TERMINAL_WIDTH = 16;
    private static final int TERMINAL_HEIGHT = 16;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static final InputStream RESOURCE = RexLoaderExample.class.getResourceAsStream("/rex_files/cp437_table.xp");

    public static void main(String[] args) {
        REXPaintResource rex = REXPaintResource.loadREXFile(RESOURCE);
        final Terminal terminal = TerminalUtils.fetchTerminalBuilder(args)
                .font(CP437TilesetResource.TAFFER_20X20.toFont())
                .initialTerminalSize(SIZE)
                .build();
        final Screen screen = Screens.createScreenFor(terminal);
        screen.setCursorVisibility(false);
        List<Layer> layers = rex.toLayerList();
        for (Layer layer: layers) {
            screen.pushLayer(layer);
        }
        screen.display();
    }
}

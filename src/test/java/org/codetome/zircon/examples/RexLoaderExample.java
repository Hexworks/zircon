package org.codetome.zircon.examples;

import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.font.Font;
import org.codetome.zircon.api.graphics.Layer;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.resource.REXPaintResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.api.terminal.Terminal;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.List;

public class RexLoaderExample {
    private static final int TERMINAL_WIDTH = 16;
    private static final int TERMINAL_HEIGHT = 16;
    private static final Size SIZE = Size.of(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static final Font<BufferedImage> FONT = CP437TilesetResource.TAFFER_20X20.toFont();
    private static final InputStream RESOURCE = RexLoaderExample.class.getResourceAsStream("/rex_files/cp437_table.xp");

    @Test
    public void checkSetup() {
        main(new String[]{"test"});
    }

    public static void main(String[] args) {
        REXPaintResource rex = REXPaintResource.loadREXFile(RESOURCE);
        final Terminal terminal = TerminalBuilder.newBuilder()
                .font(FONT)
                .initialTerminalSize(SIZE)
                .buildTerminal(args.length > 0);
        final Screen screen = TerminalBuilder.createScreenFor(terminal);
        screen.setCursorVisibility(false);
        List<Layer> layers = rex.toLayerList();
        for (Layer layer: layers) {
            screen.addLayer(layer);
        }
        screen.display();
    }
}

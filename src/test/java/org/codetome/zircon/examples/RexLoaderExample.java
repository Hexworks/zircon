package org.codetome.zircon.examples;

import org.codetome.zircon.Size;
import org.codetome.zircon.api.PhysicalFontResource;
import org.codetome.zircon.api.REXPaintResource;
import org.codetome.zircon.api.TerminalBuilder;
import org.codetome.zircon.graphics.layer.Layer;
import org.codetome.zircon.screen.Screen;

import java.io.InputStream;
import java.util.List;

public class RexLoaderExample {
    private static final int TERMINAL_WIDTH = 40;
    private static final int TERMINAL_HEIGHT = 23;
    private static final InputStream RESOURCE = RexLoaderExample.class.getResourceAsStream("/rex_files/xptest.xp");

    public static void main(String[] args) {
        REXPaintResource rex = REXPaintResource.loadREXFile(RESOURCE);
        rex.info();

        final Screen screen = TerminalBuilder.newBuilder()
                .font(PhysicalFontResource.ROBOTO_MONO.asPhysicalFont())
                .initialTerminalSize(Size.of(TERMINAL_WIDTH, TERMINAL_HEIGHT))
                .buildScreen();
        screen.setCursorVisible(false);
        List<Layer> layers = rex.toLayerList();
        for (Layer layer: layers) {
            screen.addOverlay(layer);
        }
        screen.display();
    }
}

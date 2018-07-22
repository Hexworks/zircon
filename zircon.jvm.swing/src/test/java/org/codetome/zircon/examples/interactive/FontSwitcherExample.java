package org.codetome.zircon.examples.interactive;

import org.codetome.zircon.TerminalUtils;
import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.font.Font;
import org.codetome.zircon.api.graphics.Layer;
import org.codetome.zircon.api.input.InputType;
import org.codetome.zircon.api.interop.Layers;
import org.codetome.zircon.api.interop.Positions;
import org.codetome.zircon.api.interop.Sizes;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.terminal.Terminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.codetome.zircon.api.resource.CP437TilesetResource.WANDERLUST_16X16;

public class FontSwitcherExample {

    private static final int TERMINAL_WIDTH = 30;
    private static final int TERMINAL_HEIGHT = 8;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static final List<Font> FONTS = new ArrayList<>();

    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)
        final Terminal terminal = TerminalUtils.fetchTerminalBuilder(args)
                .font(WANDERLUST_16X16.toFont())
                .initialTerminalSize(SIZE)
                .build();
        terminal.setCursorVisibility(false); // we don't want the cursor right now

        FONTS.add(CP437TilesetResource.ADU_DHABI_16X16.toFont());
        FONTS.add(CP437TilesetResource.ROGUE_YUN_16X16.toFont());
        FONTS.add(CP437TilesetResource.REX_PAINT_16X16.toFont());
        FONTS.add(CP437TilesetResource.WANDERLUST_16X16.toFont());
        FONTS.add(CP437TilesetResource.BISASAM_16X16.toFont());

        final String switchFont = "Press '->' to switch Font!";
        final String switchLayer = "Press '<-' to switch Layer!";

        final Random random = new Random();

        refreshText(terminal, switchFont, Positions.defaultPosition());
        refreshLayer(terminal, switchLayer, random);

        terminal.onInput(input -> {
            if (input.isKeyStroke()) {
                if (input.asKeyStroke().inputTypeIs(InputType.ArrowRight)) {
                    terminal.useFont(FONTS.get(random.nextInt(FONTS.size())));
                    // this is needed because terminal can't be forced to redraw
                    refreshText(terminal, switchFont, Positions.defaultPosition());
                    terminal.flush();
                }
                if (input.asKeyStroke().inputTypeIs(InputType.ArrowLeft)) {
                    refreshLayer(terminal, switchLayer, random);
                    terminal.flush();
                }
            }
        });

        terminal.flush();
    }

    private static void refreshText(Terminal terminal, String text, Position position) {
        terminal.putCursorAt(position);
        for (int i = 0; i < text.length(); i++) {
            terminal.putCharacter(text.charAt(i));
        }
    }

    private static void refreshLayer(Terminal terminal, String text, Random random) {
        terminal.drainLayers();
        Layer layer = Layers.newBuilder()
                .font(FONTS.get(random.nextInt(FONTS.size())))
                .offset(Positions.create(0, 1))
                .size(Sizes.create(text.length(), 1))
                .build();
        layer.putText(text, Positions.defaultPosition());
        terminal.pushLayer(layer);
    }

}

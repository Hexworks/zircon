package org.codetome.zircon.examples.interactive;

import org.codetome.zircon.TerminalUtils;
import org.codetome.zircon.api.data.Position;
import org.codetome.zircon.api.data.Size;
import org.codetome.zircon.api.tileset.Tileset;
import org.codetome.zircon.api.graphics.Layer;
import org.codetome.zircon.api.input.InputType;
import org.codetome.zircon.api.interop.Layers;
import org.codetome.zircon.api.interop.Positions;
import org.codetome.zircon.api.interop.Sizes;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.grid.TileGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.codetome.zircon.api.resource.CP437TilesetResource.WANDERLUST_16X16;

public class FontSwitcherExample {

    private static final int TERMINAL_WIDTH = 30;
    private static final int TERMINAL_HEIGHT = 8;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static final List<Tileset> TILESETS = new ArrayList<>();

    public static void main(String[] args) {
        // for this example we only need a default grid (no extra config)
        final TileGrid tileGrid = TerminalUtils.fetchTerminalBuilder(args)
                .font(WANDERLUST_16X16.toFont())
                .initialTerminalSize(SIZE)
                .build();
        tileGrid.setCursorVisibility(false); // we don't want the cursor right now

        TILESETS.add(CP437TilesetResource.ADU_DHABI_16X16.toFont());
        TILESETS.add(CP437TilesetResource.ROGUE_YUN_16X16.toFont());
        TILESETS.add(CP437TilesetResource.REX_PAINT_16X16.toFont());
        TILESETS.add(CP437TilesetResource.WANDERLUST_16X16.toFont());
        TILESETS.add(CP437TilesetResource.BISASAM_16X16.toFont());

        final String switchFont = "Press '->' to switch Tileset!";
        final String switchLayer = "Press '<-' to switch Layer!";

        final Random random = new Random();

        refreshText(tileGrid, switchFont, Positions.defaultPosition());
        refreshLayer(tileGrid, switchLayer, random);

        tileGrid.onInput(input -> {
            if (input.isKeyStroke()) {
                if (input.asKeyStroke().inputTypeIs(InputType.ArrowRight)) {
                    tileGrid.useFont(TILESETS.get(random.nextInt(TILESETS.size())));
                    // this is needed because grid can't be forced to redraw
                    refreshText(tileGrid, switchFont, Positions.defaultPosition());
                    tileGrid.flush();
                }
                if (input.asKeyStroke().inputTypeIs(InputType.ArrowLeft)) {
                    refreshLayer(tileGrid, switchLayer, random);
                    tileGrid.flush();
                }
            }
        });

        tileGrid.flush();
    }

    private static void refreshText(TileGrid tileGrid, String text, Position position) {
        tileGrid.putCursorAt(position);
        for (int i = 0; i < text.length(); i++) {
            tileGrid.putCharacter(text.charAt(i));
        }
    }

    private static void refreshLayer(TileGrid tileGrid, String text, Random random) {
        tileGrid.drainLayers();
        Layer layer = Layers.newBuilder()
                .font(TILESETS.get(random.nextInt(TILESETS.size())))
                .offset(Positions.create(0, 1))
                .size(Sizes.create(text.length(), 1))
                .build();
        layer.putText(text, Positions.defaultPosition());
        tileGrid.pushLayer(layer);
    }

}

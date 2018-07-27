package org.codetome.zircon.examples;

import org.codetome.zircon.TerminalUtils;
import org.codetome.zircon.api.data.Size;
import org.codetome.zircon.api.interop.Positions;
import org.codetome.zircon.api.interop.Sizes;
import org.codetome.zircon.api.interop.Tiles;
import org.codetome.zircon.api.resource.GraphicTilesetResource;
import org.codetome.zircon.api.grid.TileGrid;
import org.codetome.zircon.internal.tileset.impl.PickRandomMetaStrategy;

import java.util.Random;

public class GraphicTilesetExample {

    private static final int TERMINAL_WIDTH = 50;
    private static final int TERMINAL_HEIGHT = 24;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static final PickRandomMetaStrategy RANDOM_STRATEGY = new PickRandomMetaStrategy();
    private static final GraphicTilesetResource FONT = GraphicTilesetResource.NETHACK_16X16;
    private static final char[] CHARS = new char[]{'a', 'b', 'c'};
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        // for this example we only need a default grid (no extra config)

        final TileGrid tileGrid = TerminalUtils.fetchTerminalBuilder(args)
                .font(FONT.toFont(RANDOM_STRATEGY))
                .initialTerminalSize(SIZE)
                .build();
        tileGrid.setCursorVisibility(false); // we don't want the cursor right now

        for (int row = 0; row < TERMINAL_HEIGHT; row++) {
            for (int col = 0; col < TERMINAL_WIDTH; col++) {
                final char c = CHARS[RANDOM.nextInt(CHARS.length)];
                tileGrid.setCharacterAt(Positions.create(col, row), Tiles.newBuilder()
                        .character(c)
                        .tags(RANDOM_STRATEGY.pickMetadata(tileGrid.getCurrentFont().fetchMetadataForChar(c)).getTags())
                        .build());
            }
        }

        tileGrid.flush();
    }

}

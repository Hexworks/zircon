package org.codetome.zircon.examples;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.builder.TextCharacterBuilder;
import org.codetome.zircon.api.font.Font;
import org.codetome.zircon.api.interop.Positions;
import org.codetome.zircon.api.resource.GraphicTilesetResource;
import org.codetome.zircon.api.terminal.Terminal;
import org.codetome.zircon.internal.font.impl.PickRandomMetaStrategy;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.util.Random;

public class GraphicTilesetExample {

    private static final int TERMINAL_WIDTH = 50;
    private static final int TERMINAL_HEIGHT = 24;
    private static final Size SIZE = Size.of(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static final PickRandomMetaStrategy RANDOM_STRATEGY = new PickRandomMetaStrategy();
    private static final GraphicTilesetResource FONT = GraphicTilesetResource.NETHACK_16X16;
    private static final char[] CHARS = new char[]{'a', 'b', 'c'};
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)

        final Terminal terminal = TerminalUtils.fetchTerminalBuilder(args)
                .font(FONT.toFont(RANDOM_STRATEGY))
                .initialTerminalSize(SIZE)
                .build();
        terminal.setCursorVisibility(false); // we don't want the cursor right now

        for (int row = 0; row < TERMINAL_HEIGHT; row++) {
            for (int col = 0; col < TERMINAL_WIDTH; col++) {
                final char c = CHARS[RANDOM.nextInt(CHARS.length)];
                terminal.setCharacterAt(Positions.create(col, row), TextCharacterBuilder.newBuilder()
                        .character(c)
                        .tags(RANDOM_STRATEGY.pickMetadata(terminal.getCurrentFont().fetchMetadataForChar(c)).getTags())
                        .build());
            }
        }

        terminal.flush();
    }

}

package org.codetome.zircon.examples;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.builder.TextCharacterBuilder;
import org.codetome.zircon.api.font.Font;
import org.codetome.zircon.api.resource.GraphicTilesetResource;
import org.codetome.zircon.api.terminal.Terminal;
import org.codetome.zircon.internal.font.impl.PickRandomMetaStrategy;

import java.awt.image.BufferedImage;
import java.util.Random;

public class GraphicTilesetExample {

    private static final int ROWS = 24;
    private static final int COLS = 80;
    private static final char[] CHARS = new char[]{'a', 'b', 'c'};
    private static final Random RANDOM = new Random();


    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)
        final PickRandomMetaStrategy randomStrategy = new PickRandomMetaStrategy();
        final Font<BufferedImage> tileFont = GraphicTilesetResource.NETHACK_16X16.toFont(randomStrategy);
        final Terminal terminal = TerminalBuilder.newBuilder()
                .initialTerminalSize(Size.of(COLS, ROWS))
                .font(tileFont)
                .buildTerminal();
        terminal.setCursorVisible(false); // we don't want the cursor right now

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                final char c = CHARS[RANDOM.nextInt(CHARS.length)];
                terminal.setCharacterAt(Position.of(col, row), TextCharacterBuilder.newBuilder()
                        .character(c)
                        .tags(randomStrategy.pickMetadata(tileFont.fetchMetadataForChar(c)).getTags())
                        .build());
            }
        }

        terminal.flush();
    }

}

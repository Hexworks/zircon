package org.codetome.zircon.examples;

import org.codetome.zircon.TerminalUtils;
import org.codetome.zircon.api.builder.graphics.LayerBuilder;
import org.codetome.zircon.api.color.ANSITextColor;
import org.codetome.zircon.api.color.TextColor;
import org.codetome.zircon.api.data.Size;
import org.codetome.zircon.api.data.Tile;
import org.codetome.zircon.api.graphics.Layer;
import org.codetome.zircon.api.graphics.Symbols;
import org.codetome.zircon.api.grid.TileGrid;
import org.codetome.zircon.api.interop.Positions;
import org.codetome.zircon.api.interop.Screens;
import org.codetome.zircon.api.interop.Sizes;
import org.codetome.zircon.api.interop.TextColors;
import org.codetome.zircon.api.interop.Tiles;
import org.codetome.zircon.api.screen.Screen;

import java.util.Random;

import static org.codetome.zircon.api.resource.CP437TilesetResource.WANDERLUST_16X16;

public class TilesetExample {

    private static final int RANDOM_CHAR_COUNT = 50;
    private static final char[] RANDOM_CHARS =
            new char[]
                    {
                            Symbols.FACE_BLACK,
                            Symbols.FACE_WHITE,
                            Symbols.CLUB,
                            Symbols.TRIANGLE_UP_POINTING_BLACK,
                            Symbols.SPADES,
                            Symbols.BULLET,
                            Symbols.DIAMOND
                    };

    private static final int TERMINAL_WIDTH = 40;
    private static final int TERMINAL_HEIGHT = 40;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static final Tile GRASS_0 = Tiles.newBuilder()
            .character(',')
            .foregroundColor(TextColors.fromString("#33cc44"))
            .backgroundColor(TextColors.fromString("#114911"))
            .build();
    private static final Tile GRASS_1 = Tiles.newBuilder()
            .character('`')
            .foregroundColor(TextColors.fromString("#33bb44"))
            .backgroundColor(TextColors.fromString("#114511"))
            .build();
    private static final Tile GRASS_2 = Tiles.newBuilder()
            .character('\'')
            .foregroundColor(TextColors.fromString("#33aa44"))
            .backgroundColor(TextColors.fromString("#114011"))
            .build();
    private static final Tile[] GRASSES = new Tile[]{GRASS_0, GRASS_1, GRASS_2};
    private static final TextColor TEXT_COLOR = TextColors.fromString("#dd6644");
    private static final TextColor TEXT_BG_COLOR = TextColors.fromString("#00ff00");

    public static void main(String[] args) {
        final TileGrid tileGrid = TerminalUtils.fetchTerminalBuilder(args)
                .font(WANDERLUST_16X16.toFont())
                .initialTerminalSize(SIZE)
                .build();
        final Screen screen = Screens.createScreenFor(tileGrid);
        screen.setCursorVisibility(false);

        final Random random = new Random();
        for (int y = 0; y < TERMINAL_HEIGHT; y++) {
            for (int x = 0; x < TERMINAL_WIDTH; x++) {
                screen.setTileAt(Positions.create(x, y), GRASSES[random.nextInt(3)]);
            }
        }
        final String text = "Tileset Example";
        for (int i = 0; i < text.length(); i++) {
            screen.setTileAt(Positions.create(i + 2, 1),
                    Tiles.newBuilder()
                            .character(text.charAt(i))
                            .foregroundColor(TEXT_COLOR)
                            .backgroundColor(TEXT_BG_COLOR)
                            .build());
        }

        final int charCount = RANDOM_CHARS.length;
        final int ansiCount = ANSITextColor.values().length;

        final Layer overlay = new LayerBuilder()
                .size(screen.getBoundableSize())
                .build()
                .fill(Tiles.empty()
                        .withBackgroundColor(TextColors.create(0, 0, 0, 50)));

        for (int i = 0; i < RANDOM_CHAR_COUNT; i++) {
            overlay.setTileAt(
                    Positions.create(
                            random.nextInt(TERMINAL_WIDTH),
                            random.nextInt(TERMINAL_HEIGHT - 2) + 2),
                    Tiles.newBuilder()
                            .character(RANDOM_CHARS[random.nextInt(charCount)])
                            .foregroundColor(ANSITextColor.values()[random.nextInt(ansiCount)])
                            .backgroundColor(TextColors.transparent())
                            .build());
        }
        screen.pushLayer(overlay);
        screen.display();
    }
}

package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.*;
import org.hexworks.zircon.api.application.Application;
import org.hexworks.zircon.api.builder.application.AppConfigBuilder;
import org.hexworks.zircon.api.builder.graphics.LayerBuilder;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.color.TileColor;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.data.Tile;
import org.hexworks.zircon.api.graphics.Layer;
import org.hexworks.zircon.api.graphics.Symbols;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.resource.CP437TilesetResource;
import org.hexworks.zircon.api.screen.Screen;

import java.util.Random;

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
            .foregroundColor(TileColors.fromString("#33cc44"))
            .backgroundColor(TileColors.fromString("#114911"))
            .build();
    private static final Tile GRASS_1 = Tiles.newBuilder()
            .character('`')
            .foregroundColor(TileColors.fromString("#33bb44"))
            .backgroundColor(TileColors.fromString("#114511"))
            .build();
    private static final Tile GRASS_2 = Tiles.newBuilder()
            .character('\'')
            .foregroundColor(TileColors.fromString("#33aa44"))
            .backgroundColor(TileColors.fromString("#114011"))
            .build();
    private static final Tile[] GRASSES = new Tile[]{GRASS_0, GRASS_1, GRASS_2};
    private static final TileColor TEXT_COLOR = TileColors.fromString("#dd6644");
    private static final TileColor TEXT_BG_COLOR = TileColors.fromString("#00ff00");

    public static void main(String[] args) {

        Application app = SwingApplications.startApplication(AppConfigBuilder.Companion.newBuilder()
                .defaultTileset(CP437TilesetResource.WANDERLUST_16X16)
                .defaultSize(SIZE)
                .debugMode(true)
                .build());

        final TileGrid tileGrid = app.getTileGrid();

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
        final int ansiCount = ANSITileColor.values().length;

        final Layer overlay = new LayerBuilder()
                .size(screen.size())
                .build()
                .fill(Tiles.empty()
                        .withBackgroundColor(TileColors.create(0, 0, 0, 50)));

        for (int i = 0; i < RANDOM_CHAR_COUNT; i++) {
            overlay.setTileAt(
                    Positions.create(
                            random.nextInt(TERMINAL_WIDTH),
                            random.nextInt(TERMINAL_HEIGHT - 2) + 2),
                    Tiles.newBuilder()
                            .character(RANDOM_CHARS[random.nextInt(charCount)])
                            .foregroundColor(ANSITileColor.values()[random.nextInt(ansiCount)])
                            .backgroundColor(TileColors.transparent())
                            .build());
        }
        screen.pushLayer(overlay);
        screen.display();
    }
}

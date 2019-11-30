package org.hexworks.zircon.examples;

import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.application.Application;
import org.hexworks.zircon.api.builder.application.AppConfigBuilder;
import org.hexworks.zircon.api.builder.graphics.LayerBuilder;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.color.TileColor;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.Size;
import org.hexworks.zircon.api.data.Tile;
import org.hexworks.zircon.api.graphics.Layer;
import org.hexworks.zircon.api.graphics.Symbols;
import org.hexworks.zircon.api.grid.TileGrid;

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
    private static final Size SIZE = Size.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static final Tile GRASS_0 = Tile.newBuilder()
            .withCharacter(',')
            .withForegroundColor(TileColor.fromString("#33cc44"))
            .withBackgroundColor(TileColor.fromString("#114911"))
            .build();
    private static final Tile GRASS_1 = Tile.newBuilder()
            .withCharacter('`')
            .withForegroundColor(TileColor.fromString("#33bb44"))
            .withBackgroundColor(TileColor.fromString("#114511"))
            .build();
    private static final Tile GRASS_2 = Tile.newBuilder()
            .withCharacter('\'')
            .withForegroundColor(TileColor.fromString("#33aa44"))
            .withBackgroundColor(TileColor.fromString("#114011"))
            .build();
    private static final Tile[] GRASSES = new Tile[]{GRASS_0, GRASS_1, GRASS_2};
    private static final TileColor TEXT_COLOR = TileColor.fromString("#dd6644");
    private static final TileColor TEXT_BG_COLOR = TileColor.fromString("#00ff00");

    public static void main(String[] args) {

        // Libgdx doesn't support graphic tilesets yet
        Application app = SwingApplications.startApplication(AppConfigBuilder.Companion.newBuilder()
                .withDefaultTileset(CP437TilesetResources.wanderlust16x16())
                .withSize(SIZE)
                .withDebugMode(true)
                .build());

        final TileGrid tileGrid = app.getTileGrid();

        final Random random = new Random();
        for (int y = 0; y < TERMINAL_HEIGHT; y++) {
            for (int x = 0; x < TERMINAL_WIDTH; x++) {
                tileGrid.draw(GRASSES[random.nextInt(3)], Position.create(x, y));
            }
        }
        final String text = "Tileset Example";
        for (int i = 0; i < text.length(); i++) {
            tileGrid.draw(Tile.newBuilder()
                            .withCharacter(text.charAt(i))
                            .withForegroundColor(TEXT_COLOR)
                            .withBackgroundColor(TEXT_BG_COLOR)
                            .build(),
                    Position.create(i + 2, 1));
        }

        final int charCount = RANDOM_CHARS.length;
        final int ansiCount = ANSITileColor.values().length;

        final Layer overlay = new LayerBuilder()
                .withSize(tileGrid.getSize())
                .withFiller(Tile.empty().withBackgroundColor(TileColor.create(0, 0, 0, 50)))
                .build();

        for (int i = 0; i < RANDOM_CHAR_COUNT; i++) {
            overlay.draw(Tile.newBuilder()
                            .withCharacter(RANDOM_CHARS[random.nextInt(charCount)])
                            .withForegroundColor(ANSITileColor.values()[random.nextInt(ansiCount)])
                            .withBackgroundColor(TileColor.transparent())
                            .build(),
                    Position.create(
                            random.nextInt(TERMINAL_WIDTH),
                            random.nextInt(TERMINAL_HEIGHT - 2) + 2));
        }
        tileGrid.addLayer(overlay);
    }
}

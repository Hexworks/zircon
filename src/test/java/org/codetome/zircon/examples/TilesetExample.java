package org.codetome.zircon.examples;

import org.codetome.zircon.*;
import org.codetome.zircon.api.TerminalBuilder;
import org.codetome.zircon.api.TextColorFactory;
import org.codetome.zircon.api.CP437TilesetResource;
import org.codetome.zircon.graphics.layer.DefaultLayer;
import org.codetome.zircon.graphics.layer.Layer;
import org.codetome.zircon.screen.Screen;
import org.codetome.zircon.terminal.Size;

import java.util.HashSet;
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
    private static final TextCharacter GRASS_0 = new TextCharacter(
            ',',
            TextColorFactory.fromString("#22bb33"),
            TextColorFactory.fromString("#114911"),
            new HashSet<>());
    private static final TextCharacter GRASS_1 = new TextCharacter(
            '`',
            TextColorFactory.fromString("#22aa33"),
            TextColorFactory.fromString("#114511"),
            new HashSet<>());
    private static final TextCharacter GRASS_2 = new TextCharacter(
            '\'',
            TextColorFactory.fromString("#229933"),
            TextColorFactory.fromString("#114011"),
            new HashSet<>());
    private static final TextCharacter[] GRASSES = new TextCharacter[]{GRASS_0, GRASS_1, GRASS_2};
    private static final TextColor TEXT_COLOR = TextColorFactory.fromString("#bb4422");
    private static final TextColor TEXT_BG_COLOR = TextColorFactory.fromString("#00ff00");

    public static void main(String[] args) {
        final Screen screen = TerminalBuilder.newBuilder()
                .font(CP437TilesetResource.WANDERLUST_16X16.asJava2DFont())
                .initialTerminalSize(new Size(TERMINAL_WIDTH, TERMINAL_HEIGHT))
                .buildScreen();
        screen.setCursorVisible(false);

        final Random random = new Random();
        for (int y = 0; y < TERMINAL_HEIGHT; y++) {
            for (int x = 0; x < TERMINAL_WIDTH; x++) {
                screen.setCharacter(new Position(x, y), GRASSES[random.nextInt(3)]);
            }
        }
        final String text = "Tileset Example";
        for (int i = 0; i < text.length(); i++) {
            screen.setCharacter(new Position(i + 2, 1),
                    TextCharacter.builder()
                            .character(text.charAt(i))
                            .foregroundColor(TEXT_COLOR)
                            .backgroundColor(TEXT_BG_COLOR)
                            .build());
        }

        final int charCount = RANDOM_CHARS.length;
        final int ansiCount = ANSITextColor.values().length;

        final Layer overlay = new DefaultLayer(screen.getSize(), TextCharacter.EMPTY
                .withBackgroundColor(TextColorFactory.fromRGB(0, 0, 0, 50)), new Position(0, 0));

        for (int i = 0; i < RANDOM_CHAR_COUNT; i++) {
            overlay.setCharacterAt(
                    new Position(
                            random.nextInt(TERMINAL_WIDTH),
                            random.nextInt(TERMINAL_HEIGHT - 2) + 2),
                    TextCharacter.builder()
                            .character(RANDOM_CHARS[random.nextInt(charCount)])
                            .foregroundColor(ANSITextColor.values()[random.nextInt(ansiCount)])
                            .backgroundColor(TextColorFactory.TRANSPARENT)
                            .build());
        }
        screen.addOverlay(overlay);
        screen.display();
    }
}

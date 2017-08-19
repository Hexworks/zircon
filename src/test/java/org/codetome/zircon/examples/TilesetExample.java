package org.codetome.zircon.examples;

import org.codetome.zircon.*;
import org.codetome.zircon.api.TerminalBuilder;
import org.codetome.zircon.api.TextCharacterBuilder;
import org.codetome.zircon.api.TextColorFactory;
import org.codetome.zircon.api.CP437TilesetResource;
import org.codetome.zircon.color.impl.ANSITextColor;
import org.codetome.zircon.color.TextColor;
import org.codetome.zircon.graphics.layer.DefaultLayer;
import org.codetome.zircon.graphics.layer.Layer;
import org.codetome.zircon.screen.Screen;
import org.codetome.zircon.Size;

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
    private static final TextCharacter GRASS_0 =TextCharacter.of(
            ',',
            TextColorFactory.fromString("#33cc44"),
            TextColorFactory.fromString("#114911"),
            new HashSet<>());
    private static final TextCharacter GRASS_1 = TextCharacter.of(
            '`',
            TextColorFactory.fromString("#33bb44"),
            TextColorFactory.fromString("#114511"),
            new HashSet<>());
    private static final TextCharacter GRASS_2 = TextCharacter.of(
            '\'',
            TextColorFactory.fromString("#33aa44"),
            TextColorFactory.fromString("#114011"),
            new HashSet<>());
    private static final TextCharacter[] GRASSES = new TextCharacter[]{GRASS_0, GRASS_1, GRASS_2};
    private static final TextColor TEXT_COLOR = TextColorFactory.fromString("#dd6644");
    private static final TextColor TEXT_BG_COLOR = TextColorFactory.fromString("#00ff00");

    public static void main(String[] args) {
        final Screen screen = TerminalBuilder.newBuilder()
                .font(CP437TilesetResource.WANDERLUST_16X16.asJava2DFont())
                .initialTerminalSize(Size.of(TERMINAL_WIDTH, TERMINAL_HEIGHT))
                .buildScreen();
        screen.setCursorVisible(false);

        final Random random = new Random();
        for (int y = 0; y < TERMINAL_HEIGHT; y++) {
            for (int x = 0; x < TERMINAL_WIDTH; x++) {
                screen.setCharacterAt(Position.of(x, y), GRASSES[random.nextInt(3)]);
            }
        }
        final String text = "Tileset Example";
        for (int i = 0; i < text.length(); i++) {
            screen.setCharacterAt(Position.of(i + 2, 1),
                    TextCharacter.builder()
                            .character(text.charAt(i))
                            .foregroundColor(TEXT_COLOR)
                            .backgroundColor(TEXT_BG_COLOR)
                            .build());
        }

        final int charCount = RANDOM_CHARS.length;
        final int ansiCount = ANSITextColor.values().length;

        final Layer overlay = new DefaultLayer(screen.getBoundableSize(), TextCharacterBuilder.EMPTY
                .withBackgroundColor(TextColorFactory.fromRGB(0, 0, 0, 50)), Position.of(0, 0));

        for (int i = 0; i < RANDOM_CHAR_COUNT; i++) {
            overlay.setCharacterAt(
                    Position.of(
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

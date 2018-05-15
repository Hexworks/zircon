package org.codetome.zircon.examples;

import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.Symbols;
import org.codetome.zircon.api.TextCharacter;
import org.codetome.zircon.api.builder.LayerBuilder;
import org.codetome.zircon.api.builder.ScreenBuilder;
import org.codetome.zircon.api.color.ANSITextColor;
import org.codetome.zircon.api.color.TextColor;
import org.codetome.zircon.api.graphics.Layer;
import org.codetome.zircon.api.interop.Positions;
import org.codetome.zircon.api.interop.Sizes;
import org.codetome.zircon.api.interop.TextCharacters;
import org.codetome.zircon.api.interop.TextColors;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.api.terminal.Terminal;

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
    private static final TextCharacter GRASS_0 = TextCharacters.newBuilder()
            .character(',')
            .foregroundColor(TextColors.fromString("#33cc44"))
            .backgroundColor(TextColors.fromString("#114911"))
            .build();
    private static final TextCharacter GRASS_1 = TextCharacters.newBuilder()
            .character('`')
            .foregroundColor(TextColors.fromString("#33bb44"))
            .backgroundColor(TextColors.fromString("#114511"))
            .build();
    private static final TextCharacter GRASS_2 = TextCharacters.newBuilder()
            .character('\'')
            .foregroundColor(TextColors.fromString("#33aa44"))
            .backgroundColor(TextColors.fromString("#114011"))
            .build();
    private static final TextCharacter[] GRASSES = new TextCharacter[]{GRASS_0, GRASS_1, GRASS_2};
    private static final TextColor TEXT_COLOR = TextColors.fromString("#dd6644");
    private static final TextColor TEXT_BG_COLOR = TextColors.fromString("#00ff00");

    public static void main(String[] args) {
        final Terminal terminal = TerminalUtils.fetchTerminalBuilder(args)
                .font(WANDERLUST_16X16.toFont())
                .initialTerminalSize(SIZE)
                .build();
        final Screen screen = ScreenBuilder.createScreenFor(terminal);
        screen.setCursorVisibility(false);

        final Random random = new Random();
        for (int y = 0; y < TERMINAL_HEIGHT; y++) {
            for (int x = 0; x < TERMINAL_WIDTH; x++) {
                screen.setCharacterAt(Positions.create(x, y), GRASSES[random.nextInt(3)]);
            }
        }
        final String text = "Tileset Example";
        for (int i = 0; i < text.length(); i++) {
            screen.setCharacterAt(Positions.create(i + 2, 1),
                    TextCharacters.newBuilder()
                            .character(text.charAt(i))
                            .foregroundColor(TEXT_COLOR)
                            .backgroundColor(TEXT_BG_COLOR)
                            .build());
        }

        final int charCount = RANDOM_CHARS.length;
        final int ansiCount = ANSITextColor.values().length;

        final Layer overlay = new LayerBuilder()
                .size(screen.getBoundableSize())
                .filler(TextCharacters.EMPTY
                        .withBackgroundColor(TextColors.fromRGB(0, 0, 0, 50)))
                .build();

        for (int i = 0; i < RANDOM_CHAR_COUNT; i++) {
            overlay.setCharacterAt(
                    Positions.create(
                            random.nextInt(TERMINAL_WIDTH),
                            random.nextInt(TERMINAL_HEIGHT - 2) + 2),
                    TextCharacters.newBuilder()
                            .character(RANDOM_CHARS[random.nextInt(charCount)])
                            .foregroundColor(ANSITextColor.values()[random.nextInt(ansiCount)])
                            .backgroundColor(TextColors.TRANSPARENT)
                            .build());
        }
        screen.pushLayer(overlay);
        screen.display();
    }
}

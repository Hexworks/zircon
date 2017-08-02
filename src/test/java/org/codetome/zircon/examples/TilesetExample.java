package org.codetome.zircon.examples;

import org.codetome.zircon.Symbols;
import org.codetome.zircon.TerminalPosition;
import org.codetome.zircon.TextCharacter;
import org.codetome.zircon.TextColor;
import org.codetome.zircon.screen.TerminalScreen;
import org.codetome.zircon.terminal.DefaultTerminalFactory;
import org.codetome.zircon.terminal.Terminal;
import org.codetome.zircon.terminal.TerminalSize;
import org.codetome.zircon.terminal.config.FontConfiguration;
import org.codetome.zircon.tileset.DFTilesetResource;

import java.util.HashSet;
import java.util.Random;

import static org.codetome.zircon.tileset.DFTilesetResource.*;

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
            TextColor.Companion.fromString("#22bb33"),
            TextColor.Companion.fromString("#114911"),
            new HashSet<>());
    private static final TextCharacter GRASS_1 = new TextCharacter(
            '`',
            TextColor.Companion.fromString("#22aa33"),
            TextColor.Companion.fromString("#114511"),
            new HashSet<>());
    private static final TextCharacter GRASS_2 = new TextCharacter(
            '\'',
            TextColor.Companion.fromString("#229933"),
            TextColor.Companion.fromString("#114011"),
            new HashSet<>());
    private static final TextCharacter[] GRASSES = new TextCharacter[]{GRASS_0, GRASS_1, GRASS_2};
    private static final TextColor TEXT_COLOR = TextColor.Companion.fromString("#bb4422");
    private static final TextColor TEXT_BG_COLOR = TextColor.Companion.fromString("#114011");

    public static void main(String[] args) {

        final DefaultTerminalFactory factory = new DefaultTerminalFactory();
        factory.setFontRenderer(FontConfiguration.createSwingFontRendererForTileset(WANDERLUST_16X16));
        factory.setInitialTerminalSize(new TerminalSize(TERMINAL_WIDTH, TERMINAL_HEIGHT));
        final Terminal terminal = factory.createTerminal();

        final TerminalScreen screen = new TerminalScreen(terminal);
        final Random random = new Random();
        for (int y = 0; y < TERMINAL_HEIGHT; y++) {
            for (int x = 0; x < TERMINAL_WIDTH; x++) {
                screen.setCharacter(new TerminalPosition(x, y), GRASSES[random.nextInt(3)]);
            }
        }
        final String text = "Tileset Example";
        for (int i = 0; i < text.length(); i++) {
            screen.setCharacter(new TerminalPosition(i + 2, 1),
                    new TextCharacter(text.charAt(i), TEXT_COLOR, TEXT_BG_COLOR, new HashSet<>()));
        }

        final int charCount = RANDOM_CHARS.length;
        final int ansiCount = TextColor.ANSI.values().length;

        for (int i = 0; i < RANDOM_CHAR_COUNT; i++) {
            screen.setCharacter(
                    new TerminalPosition(
                            random.nextInt(TERMINAL_WIDTH),
                            random.nextInt(TERMINAL_HEIGHT - 2) + 2),
                    new TextCharacter(
                            RANDOM_CHARS[random.nextInt(charCount)],
                            TextColor.ANSI.values()[random.nextInt(ansiCount)],
                            TextColor.Companion.fromString("#114011"),
                            new HashSet<>()));
        }
        screen.display();
    }
}

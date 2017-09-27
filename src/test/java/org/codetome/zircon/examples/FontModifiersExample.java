package org.codetome.zircon.examples;

import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.font.Font;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.screen.Screen;
import org.codetome.zircon.api.terminal.Terminal;

import java.awt.image.BufferedImage;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.codetome.zircon.api.Modifiers.*;
import static org.codetome.zircon.api.Modifiers.BorderPosition.*;
import static org.codetome.zircon.api.Modifiers.BorderType.SOLID;
import static org.codetome.zircon.api.color.ANSITextColor.*;
import static org.codetome.zircon.api.resource.CP437TilesetResource.WANDERLUST_16X16;

public class FontModifiersExample {

    private static final int TERMINAL_WIDTH = 40;
    private static final int TERMINAL_HEIGHT = 40;
    private static final Size SIZE = Size.of(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static final Font<BufferedImage> FONT = WANDERLUST_16X16.toFont();

    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)
        final Terminal terminal = TerminalBuilder.newBuilder()
                .font(FONT)
                .initialTerminalSize(SIZE)
                .buildTerminal();
        terminal.setCursorVisible(false); // we don't want the cursor right now

        terminal.enableModifier(VERTICAL_FLIP);
        terminal.setBackgroundColor(BLUE);
        terminal.setForegroundColor(YELLOW);
        terminal.putCharacter('A');

        putEmptySpace(terminal);

        terminal.enableModifier(CROSSED_OUT);
        terminal.setBackgroundColor(RED);
        terminal.setForegroundColor(GREEN);
        terminal.putCharacter('B');

        putEmptySpace(terminal);

        terminal.enableModifier(BLINK);
        terminal.setBackgroundColor(RED);
        terminal.setForegroundColor(WHITE);
        terminal.putCharacter('C');

        putEmptySpace(terminal);

        terminal.enableModifier(UNDERLINE);
        terminal.setBackgroundColor(BLUE);
        terminal.setForegroundColor(CYAN);
        terminal.putCharacter('D');

        putEmptySpace(terminal);

        terminal.enableModifier(HORIZONTAL_FLIP);
        terminal.setBackgroundColor(BLACK);
        terminal.setForegroundColor(YELLOW);
        terminal.putCharacter('E');

        putEmptySpace(terminal);

        terminal.enableModifier(Blink.INSTANCE);
        terminal.setBackgroundColor(CYAN);
        terminal.setForegroundColor(YELLOW);
        terminal.putCharacter('F');

        putEmptySpace(terminal);

        terminal.enableModifiers(Stream.of(HORIZONTAL_FLIP, VERTICAL_FLIP, BLINK).collect(Collectors.toSet()));
        terminal.setBackgroundColor(BLUE);
        terminal.setForegroundColor(WHITE);
        terminal.putCharacter('G');

        putEmptySpace(terminal);

        terminal.enableModifier(BORDER.of(SOLID, TOP, LEFT, RIGHT, BOTTOM));
        terminal.setBackgroundColor(WHITE);
        terminal.setForegroundColor(BLUE);
        terminal.putCharacter('H');

        terminal.flush();
    }

    private static void putEmptySpace(Terminal terminal) {
        terminal.resetColorsAndModifiers();
        terminal.setForegroundColor(BLACK);
        terminal.putCharacter(' ');
    }

}

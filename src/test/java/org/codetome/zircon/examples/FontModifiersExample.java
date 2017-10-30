package org.codetome.zircon.examples;

import org.codetome.zircon.api.Modifiers;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.font.Font;
import org.codetome.zircon.api.modifier.RayShade;
import org.codetome.zircon.api.terminal.Terminal;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.codetome.zircon.api.Modifiers.*;
import static org.codetome.zircon.api.color.ANSITextColor.*;
import static org.codetome.zircon.api.resource.CP437TilesetResource.WANDERLUST_16X16;

public class FontModifiersExample {

    private static final int TERMINAL_WIDTH = 16;
    private static final int TERMINAL_HEIGHT = 3;
    private static final Size SIZE = Size.of(TERMINAL_WIDTH, TERMINAL_HEIGHT);
    private static final Font FONT = WANDERLUST_16X16.toFont();

    @Test
    public void checkSetup() {
        main(new String[]{"test"});
    }

    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)
        final Terminal terminal = TerminalBuilder.newBuilder()
                .font(FONT)
                .initialTerminalSize(SIZE)
                .buildTerminal(args.length > 0);
        terminal.setCursorVisibility(false); // we don't want the cursor right now

        terminal.enableModifiers(VERTICAL_FLIP);
        terminal.setBackgroundColor(BLUE);
        terminal.setForegroundColor(YELLOW);
        terminal.putCharacter('A');

        putEmptySpace(terminal);

        terminal.enableModifiers(CROSSED_OUT);
        terminal.setBackgroundColor(RED);
        terminal.setForegroundColor(GREEN);
        terminal.putCharacter('B');

        putEmptySpace(terminal);

        terminal.enableModifiers(BLINK);
        terminal.setBackgroundColor(RED);
        terminal.setForegroundColor(WHITE);
        terminal.putCharacter('C');

        putEmptySpace(terminal);

        terminal.enableModifiers(UNDERLINE);
        terminal.setBackgroundColor(BLUE);
        terminal.setForegroundColor(CYAN);
        terminal.putCharacter('D');

        putEmptySpace(terminal);

        terminal.enableModifiers(HORIZONTAL_FLIP);
        terminal.setBackgroundColor(BLACK);
        terminal.setForegroundColor(YELLOW);
        terminal.putCharacter('E');

        putEmptySpace(terminal);

        terminal.enableModifiers(BLINK);
        terminal.setBackgroundColor(CYAN);
        terminal.setForegroundColor(YELLOW);
        terminal.putCharacter('F');

        putEmptySpace(terminal);

        terminal.enableModifiers(Stream.of(HORIZONTAL_FLIP, VERTICAL_FLIP, BLINK).collect(Collectors.toSet()));
        terminal.setBackgroundColor(BLUE);
        terminal.setForegroundColor(WHITE);
        terminal.putCharacter('G');

        putEmptySpace(terminal);

        terminal.enableModifiers(Modifiers.BORDER);
        terminal.setBackgroundColor(WHITE);
        terminal.setForegroundColor(BLUE);
        terminal.putCharacter('H');

        putEmptySpace(terminal);

        terminal.enableModifiers(new RayShade());
        terminal.setBackgroundColor(WHITE);
        terminal.setForegroundColor(BLUE);
        terminal.putCharacter('I');

        putEmptySpace(terminal);

        terminal.enableModifiers(Modifiers.GLOW);
        terminal.setBackgroundColor(WHITE);
        terminal.setForegroundColor(BLUE);
        terminal.putCharacter('J');

        terminal.flush();
    }

    private static void putEmptySpace(Terminal terminal) {
        terminal.resetColorsAndModifiers();
        terminal.setForegroundColor(BLACK);
        terminal.putCharacter(' ');
    }

}

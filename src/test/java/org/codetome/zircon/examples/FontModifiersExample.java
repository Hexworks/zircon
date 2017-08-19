package org.codetome.zircon.examples;

import org.codetome.zircon.api.CP437TilesetResource;
import org.codetome.zircon.api.TerminalBuilder;
import org.codetome.zircon.terminal.Terminal;

import static org.codetome.zircon.Modifier.*;
import static org.codetome.zircon.color.impl.ANSITextColor.*;

public class FontModifiersExample {

    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)
        final Terminal terminal = TerminalBuilder.newBuilder()
                .font(CP437TilesetResource.WANDERLUST_16X16.asJava2DFont())
                .buildTerminal();
        terminal.setCursorVisible(false); // we don't want the cursor right now

        terminal.enableModifier(VERTICAL_FLIP);
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

        terminal.enableModifiers(HIDDEN);
        terminal.setBackgroundColor(CYAN);
        terminal.setForegroundColor(YELLOW);
        terminal.putCharacter('F');

        putEmptySpace(terminal);

        terminal.enableModifiers(HORIZONTAL_FLIP, VERTICAL_FLIP, BLINK);
        terminal.setBackgroundColor(BLUE);
        terminal.setForegroundColor(WHITE);
        terminal.putCharacter('G');

        terminal.flush();
    }

    private static void putEmptySpace(Terminal terminal) {
        terminal.resetColorsAndModifiers();
        terminal.setForegroundColor(BLACK);
        terminal.putCharacter(' ');
    }

}

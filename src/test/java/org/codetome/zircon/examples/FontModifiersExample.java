package org.codetome.zircon.examples;

import org.codetome.zircon.builder.TerminalBuilder;
import org.codetome.zircon.terminal.Terminal;

import static org.codetome.zircon.ANSITextColor.*;
import static org.codetome.zircon.Modifier.*;

public class FontModifiersExample {

    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)
        final Terminal terminal = TerminalBuilder.buildDefault();
        terminal.setCursorVisible(false); // we don't want the cursor right now

        terminal.enableModifier(CROSSED_OUT);
        terminal.setBackgroundColor(BLUE);
        terminal.setForegroundColor(YELLOW);
        terminal.putCharacter('A');

        putEmptySpace(terminal);

        terminal.enableModifiers(BOLD, ITALIC);
        terminal.setBackgroundColor(GREEN);
        terminal.setForegroundColor(YELLOW);
        terminal.putCharacter('B');

        putEmptySpace(terminal);

        terminal.enableModifiers(BLINK);
        terminal.setBackgroundColor(RED);
        terminal.setForegroundColor(WHITE);
        terminal.putCharacter('C');

        terminal.flush();
        // note that anti alias can be enabled when building a font renderer
    }

    private static void putEmptySpace(Terminal terminal) {
        terminal.resetColorsAndModifiers();
        terminal.setForegroundColor(BLACK);
        terminal.putCharacter(' ');
    }

}

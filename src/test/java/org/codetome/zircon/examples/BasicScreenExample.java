package org.codetome.zircon.examples;

import org.codetome.zircon.TerminalPosition;
import org.codetome.zircon.TextColor;
import org.codetome.zircon.graphics.TextGraphics;
import org.codetome.zircon.screen.Screen;
import org.codetome.zircon.screen.TerminalScreen;
import org.codetome.zircon.terminal.DefaultTerminalFactory;
import org.codetome.zircon.terminal.Terminal;
import org.codetome.zircon.terminal.TerminalSize;
import org.codetome.zircon.terminal.config.TerminalFontConfiguration;

import java.util.Collections;

public class BasicScreenExample {

    private static final int TERMINAL_WIDTH = 100;
    private static final int TERMINAL_HEIGHT = 40;
    private static final TextColor BACKGROUND_COLOR = TextColor.Companion.fromString("#223344");

    public static void main(String[] args) {
        final DefaultTerminalFactory factory = new DefaultTerminalFactory();
        factory.setTerminalFontConfiguration(TerminalFontConfiguration.Companion.getDefault());
        factory.setInitialTerminalSize(new TerminalSize(TERMINAL_WIDTH, TERMINAL_HEIGHT));
        final Terminal terminal = factory.createTerminal();

        final TerminalScreen screen = new TerminalScreen(terminal);
        drawBackground(screen);
        drawButton(screen, new TerminalPosition(10, 10), "OK");

        screen.display();
    }

    private static void drawBackground(Screen screen) {
        final TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(BACKGROUND_COLOR);
        graphics.fill(' ');
    }

    private static void drawButton(TerminalScreen screen, TerminalPosition position, String text) {
        final TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setBackgroundColor(TextColor.ANSI.BLUE);
        textGraphics.setForegroundColor(TextColor.ANSI.CYAN);
        textGraphics.putString(position, String.format("<%s>", text), Collections.emptySet());
    }
}

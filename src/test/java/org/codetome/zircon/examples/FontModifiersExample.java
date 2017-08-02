package org.codetome.zircon.examples;

import org.codetome.zircon.font.PhysicalFontRenderer;
import org.codetome.zircon.terminal.DefaultTerminalFactory;
import org.codetome.zircon.terminal.Terminal;
import org.codetome.zircon.terminal.TerminalSize;
import org.codetome.zircon.terminal.config.FontConfiguration;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.codetome.zircon.Modifier.*;
import static org.codetome.zircon.TextColor.ANSI.*;

public class FontModifiersExample {

    private static final int TERMINAL_WIDTH = 60;
    private static final int TERMINAL_HEIGHT = 30;

    public static void main(String[] args) {
        final DefaultTerminalFactory factory = new DefaultTerminalFactory();
        factory.setInitialTerminalSize(new TerminalSize(TERMINAL_WIDTH, TERMINAL_HEIGHT));
        final PhysicalFontRenderer<BufferedImage, Graphics> renderer =
                FontConfiguration.createSwingFontRendererForPhysicalFonts(true);
        factory.setFontRenderer(renderer);
        final Terminal terminal = factory.createTerminal();

        terminal.enableModifier(CROSSED_OUT);
        terminal.setBackgroundColor(BLUE);
        terminal.setForegroundColor(YELLOW);
        terminal.putCharacter('A');

        terminal.resetColorsAndModifiers();
        terminal.putCharacter(' ');

        terminal.enableModifiers(BOLD, ITALIC);
        terminal.setBackgroundColor(GREEN);
        terminal.setForegroundColor(YELLOW);
        terminal.putCharacter('B');

        terminal.resetColorsAndModifiers();
        terminal.putCharacter(' ');

        terminal.enableModifiers(BLINK);
        terminal.setBackgroundColor(RED);
        terminal.setForegroundColor(WHITE);
        terminal.putCharacter('C');

        terminal.setForegroundColor(RED);
        terminal.setBackgroundColor(BLUE);
        terminal.putCharacter((char)1);
        terminal.putCharacter((char)2);
        terminal.putCharacter((char)3);

        terminal.flush();
    }

}

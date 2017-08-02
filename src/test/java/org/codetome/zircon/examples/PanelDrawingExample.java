package org.codetome.zircon.examples;

import org.codetome.zircon.TerminalPosition;
import org.codetome.zircon.TextColor;
import org.codetome.zircon.font.MonospaceFontRenderer;
import org.codetome.zircon.graphics.TextGraphics;
import org.codetome.zircon.graphics.TextImage;
import org.codetome.zircon.graphics.impl.BasicTextImage;
import org.codetome.zircon.screen.Screen;
import org.codetome.zircon.screen.TerminalScreen;
import org.codetome.zircon.terminal.DefaultTerminalFactory;
import org.codetome.zircon.terminal.Terminal;
import org.codetome.zircon.terminal.TerminalSize;
import org.codetome.zircon.terminal.config.FontConfiguration;

import java.util.Collections;

import static org.codetome.zircon.Symbols.*;

public class PanelDrawingExample {

    private static final int TERMINAL_WIDTH = 100;
    private static final int TERMINAL_HEIGHT = 40;
    private static final TextColor BACKGROUND_COLOR = TextColor.Companion.fromString("#223344");

    public static void main(String[] args) {
        final DefaultTerminalFactory factory = new DefaultTerminalFactory();
        final MonospaceFontRenderer fontConfig = FontConfiguration.createSwingFontRendererForPhysicalFonts(true);
        factory.setFontRenderer(fontConfig);
        factory.setInitialTerminalSize(new TerminalSize(TERMINAL_WIDTH, TERMINAL_HEIGHT));
        final Terminal terminal = factory.createTerminal();

        final TerminalScreen screen = new TerminalScreen(terminal);

        drawBackground(screen);

        screen.newTextGraphics().drawImage(
                new TerminalPosition(2, 2),
                createPanel(20, 30, "Foo and Bar"));

        screen.newTextGraphics().drawImage(
                new TerminalPosition(24, 2),
                createBackgroundForPanel(createPanel(30, 20, "Wom and Bat")));

        screen.display();
    }

    private static TextImage createBackgroundForPanel(TextImage panel) {
        final TextImage bg = new BasicTextImage(
                new TerminalSize(panel.getSize().getColumns() + 1, panel.getSize().getRows() + 1));
        final TerminalSize bgSize = bg.getSize();
        final TextGraphics panelWithBg = bg.newTextGraphics();
        panelWithBg.setBackgroundColor(TextColor.Companion.fromString("#111111"));
        panelWithBg.fill(' ');
        panelWithBg.setBackgroundColor(BACKGROUND_COLOR);
        panelWithBg.setCharacter(new TerminalPosition(bgSize.getColumns() - 1, 0), ' ');
        panelWithBg.setCharacter(new TerminalPosition(0, bgSize.getRows() - 1), ' ');
        panel.copyTo(bg);
        return bg;
    }

    private static void drawBackground(Screen screen) {
        final TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(BACKGROUND_COLOR);
        graphics.fill(' ');
    }

    private static TextImage createPanel(int width, int height, String title) {
        final TerminalPosition topLeft = TerminalPosition.getTOP_LEFT_CORNER();
        final TerminalPosition topRight = topLeft.withRelativeColumn(width - 1);
        final TerminalPosition bottomLeft = topLeft.withRelativeRow(height - 1);
        final TerminalPosition bottomRight = topRight.withRelativeRow(height - 1);

        final TextImage image = new BasicTextImage(new TerminalSize(width, height));
        final TextGraphics graphics = image.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Companion.fromString("#666666"));
        graphics.setCharacter(topLeft, DOUBLE_LINE_TOP_LEFT_CORNER);
        graphics.setCharacter(topRight, DOUBLE_LINE_TOP_RIGHT_CORNER);
        graphics.setCharacter(bottomLeft, DOUBLE_LINE_BOTTOM_LEFT_CORNER);
        graphics.setCharacter(bottomRight, DOUBLE_LINE_BOTTOM_RIGHT_CORNER);
        graphics.drawLine(topLeft.withRelativeColumn(1), topRight.withRelativeColumn(-1), DOUBLE_LINE_HORIZONTAL);
        graphics.drawLine(bottomLeft.withRelativeColumn(1), bottomRight.withRelativeColumn(-1), DOUBLE_LINE_HORIZONTAL);
        graphics.drawLine(topLeft.withRelativeRow(1), bottomLeft.withRelativeRow(-1), DOUBLE_LINE_VERTICAL);
        graphics.drawLine(topRight.withRelativeRow(1), bottomRight.withRelativeRow(-1), DOUBLE_LINE_VERTICAL);
        graphics.fillRectangle(
                topLeft.withRelative(new TerminalPosition(1, 1)),
                new TerminalSize(width - 2, height - 2),
                ' ');
        graphics.putString(
                topLeft.withRelativeColumn(width / 2 - title.length() / 2),
                title,
                Collections.emptySet());
        return image;
    }
}
